package nl.qudical.weatherdetector.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import nl.qudical.weatherdetector.WeatherDetector;

public class WeatherDetectorBlockEntity extends BlockEntity {

    public WeatherDetectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(WeatherDetector.WEATHER_DETECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T t) {
        int power = 0;
        if (level.getBiome(pos).value().getPrecipitation() != Biome.Precipitation.NONE) {
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
}
