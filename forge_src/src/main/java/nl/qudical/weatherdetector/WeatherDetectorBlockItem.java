package nl.qudical.weatherdetector;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WeatherDetectorBlockItem extends BlockItem {
    public WeatherDetectorBlockItem(Properties properties) {
        super(WeatherDetector.WEATHER_DETECTOR.get(), properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.weatherdetector.weather_detector.tooltip_1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("block.weatherdetector.weather_detector.tooltip_2").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("block.weatherdetector.weather_detector.tooltip_3").withStyle(ChatFormatting.GRAY));
    }
}
