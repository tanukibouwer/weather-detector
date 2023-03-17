package nl.qudical.weatherdetector.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nl.qudical.weatherdetector.WeatherDetector;

public class WeatherDetectorEntity extends BlockEntity {
    public WeatherDetectorEntity(BlockPos pos, BlockState state) {
        super(WeatherDetector.WEATHER_DETECTOR_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, WeatherDetectorEntity be) {
        if (!world.isClient) {
            int power = 0;
            if (world.getBiome(pos).value().getPrecipitation(pos) != Biome.Precipitation.NONE) {
                // Block outputs a redstone signal of strength 1-8 if it's raining...
                if (world.getRainGradient(1.0F) > 0.2) {
                    power = (int)(world.getRainGradient(1.0F) * 8.0F);
                }
                // ...and a redstone signal of strength 9-15 if it's thundering.
                if (world.getThunderGradient(1.0F) > 0.2) {
                    power = 8 + (int)(world.getThunderGradient(1.0F) * 7.0F);
                }
            }
            if (state.get(Properties.INVERTED)) {
                power = 15 - power;
            }
            world.setBlockState(pos, state.with(Properties.POWER, power));
        }
    }
}
