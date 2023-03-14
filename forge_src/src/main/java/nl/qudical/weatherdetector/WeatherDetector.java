package nl.qudical.weatherdetector;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nl.qudical.weatherdetector.block.WeatherDetectorBlock;
import nl.qudical.weatherdetector.block.WeatherDetectorBlockEntity;
import org.slf4j.Logger;

@Mod(WeatherDetector.MODID)
public class WeatherDetector
{
    public static final String MODID = "weatherdetector";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);


    public static final RegistryObject<Block> WEATHER_DETECTOR = BLOCKS.register("weather_detector", () -> new WeatherDetectorBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(2.0f, 6.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<BlockEntityType<WeatherDetectorBlockEntity>> WEATHER_DETECTOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("weatherdetectorblockentity", () -> BlockEntityType.Builder.of(WeatherDetectorBlockEntity::new, WEATHER_DETECTOR.get()).build(null));
    public static final RegistryObject<Item> WEATHER_DETECTOR_ITEM = ITEMS.register("weather_detector", () -> new BlockItem(WEATHER_DETECTOR.get(), new Item.Properties()));

    public WeatherDetector()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);

        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS)
            event.getEntries().putAfter(new ItemStack(Items.DAYLIGHT_DETECTOR), new ItemStack(WEATHER_DETECTOR_ITEM.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
}
