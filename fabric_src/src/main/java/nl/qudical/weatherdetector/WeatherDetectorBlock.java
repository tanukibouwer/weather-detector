package nl.qudical.weatherdetector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WeatherDetectorBlock extends Block implements EntityBlock {
    public WeatherDetectorBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(getStateDefinition().any()
                .setValue(BlockStateProperties.POWER, 0)
                .setValue(BlockStateProperties.INVERTED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WeatherDetectorBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.POWER, BlockStateProperties.INVERTED);
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        return pState.getValue(BlockStateProperties.POWER);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.mayBuild()) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockState blockstate = state.cycle(BlockStateProperties.INVERTED);
                level.setBlock(pos, blockstate, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
                updateSignalStrength(blockstate, level, pos);
                return InteractionResult.CONSUME;
            }
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    private static void updateSignalStrength(BlockState state, Level level, BlockPos pos) {
        int power = 0;
        if (level.getBiome(pos).value().getPrecipitationAt(pos) != Biome.Precipitation.NONE) {
            // Block outputs a redstone signal of strength 1-8 if it's raining...
            if (level.getRainLevel(1.0F) > 0.2) {
                power = (int) (level.getRainLevel(1.0F) * 8.0F);
            }
            // ...and a redstone signal of strength 9-15 if it's thundering.
            if (level.getThunderLevel(1.0F) > 0.2) {
                power = 8 + (int) (level.getThunderLevel(1.0F) * 7.0F);
            }
        }
        if (state.getValue(BlockStateProperties.INVERTED)) {
            power = 15 - power;
        }
        level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWER, power));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.box(0F, 0F, 0F, 1F, 0.375F, 1F);
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ? createTickerHelper(blockEntityType, WeatherDetector.WEATHER_DETECTOR_BLOCK_ENTITY, WeatherDetectorBlock::tickEntity) : null;
    }

    private static void tickEntity(Level level, BlockPos pos, BlockState state, WeatherDetectorBlockEntity blockEntity) {
        if (level.getGameTime() % 20L == 0L) {
            updateSignalStrength(state, level, pos);
        }
    }
}