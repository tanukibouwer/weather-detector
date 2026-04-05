package nl.qudical.weatherdetector;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WeatherDetectorBlockEntity extends BlockEntity {

    public WeatherDetectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(WeatherDetector.WEATHER_DETECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
}
