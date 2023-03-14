package nl.qudical.weatherdetector.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nl.qudical.weatherdetector.WeatherDetector;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == WeatherDetector.WEATHER_DETECTOR_BLOCK_ENTITY.get() && !pLevel.isClientSide ? WeatherDetectorBlockEntity::tick : null;
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

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.setBlockAndUpdate(pPos, pState.setValue(BlockStateProperties.INVERTED, !pState.getValue(BlockStateProperties.INVERTED)));
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.box(0F, 0F, 0F, 1F, 0.375F, 1F);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("block.weatherdetector.weather_detector.tooltip_1").withStyle(ChatFormatting.GRAY));
        pTooltip.add(Component.translatable("block.weatherdetector.weather_detector.tooltip_2").withStyle(ChatFormatting.GRAY));
        pTooltip.add(Component.translatable("block.weatherdetector.weather_detector.tooltip_3").withStyle(ChatFormatting.GRAY));
    }
}