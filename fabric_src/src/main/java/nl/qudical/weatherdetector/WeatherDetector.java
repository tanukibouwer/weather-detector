package nl.qudical.weatherdetector;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;

public class WeatherDetector implements ModInitializer {
	public static final String MOD_ID = "weatherdetector";

	public static final ResourceLocation BLOCK_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "weather_detector");
	public static final Block WEATHER_DETECTOR = Registry.register(
			BuiltInRegistries.BLOCK, BLOCK_ID,
			new WeatherDetectorBlock(Block.Properties.of().strength(2.0f, 6.0f).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY)));
	public static final BlockItem WEATHER_DETECTOR_ITEM = Registry.register(
			BuiltInRegistries.ITEM, BLOCK_ID,
			new WeatherDetectorBlockItem(new BlockItem.Properties()));
	public static final BlockEntityType<WeatherDetectorBlockEntity> WEATHER_DETECTOR_BLOCK_ENTITY = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE, BLOCK_ID,
			BlockEntityType.Builder.of(WeatherDetectorBlockEntity::new, WEATHER_DETECTOR).build());

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS)
				.register((itemGroup) -> itemGroup.accept(WEATHER_DETECTOR_ITEM));
	}
}