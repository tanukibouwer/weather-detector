package nl.qudical.weatherdetector;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(WeatherDetector.MODID)
public class WeatherDetector {
    public static final String MODID = "weatherdetector";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<Block> WEATHER_DETECTOR = BLOCKS.register("weather_detector", () -> new WeatherDetectorBlock(BlockBehaviour.Properties.of().strength(2.0f, 6.0f).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY)));
    public static final DeferredItem<BlockItem> WEATHER_DETECTOR_ITEM = ITEMS.registerItem("weather_detector", WeatherDetectorBlockItem::new, new Item.Properties());
    public static final Supplier<BlockEntityType<WeatherDetectorBlockEntity>> WEATHER_DETECTOR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("weather_detector_blockentity", () -> BlockEntityType.Builder.of(WeatherDetectorBlockEntity::new, WEATHER_DETECTOR.get()).build(null));

    public WeatherDetector(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(WEATHER_DETECTOR_ITEM);
        }
    }
}
