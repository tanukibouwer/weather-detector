package nl.qudical.weatherdetector;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import nl.qudical.weatherdetector.block.WeatherDetectorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherDetector implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("weatherdetector");
    public static final Block WEATHER_DETECTOR = new nl.qudical.weatherdetector.block.WeatherDetector(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).requiresTool());
    public static final BlockEntityType<WeatherDetectorEntity> WEATHER_DETECTOR_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier("weatherdetector", "weather_detector_entity"),
            FabricBlockEntityTypeBuilder.create(WeatherDetectorEntity::new, WEATHER_DETECTOR).build()
    );

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("weatherdetector", "weather_detector"), WEATHER_DETECTOR);
        Registry.register(Registry.ITEM, new Identifier("weatherdetector", "weather_detector"), new BlockItem(WEATHER_DETECTOR, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    }
}
