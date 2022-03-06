package ru.indieplay.ariamis.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ru.indieplay.ariamis.common.Reflection;

import java.lang.reflect.Method;

public class ReflectionClient {

    private static int[] colorCodes;


    public static void setHighlightedItemTicks(final GuiIngame gui, final int ticks) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class) GuiIngame.class, (Object) gui, (Object) ticks, new String[]{"remainingHighlightTicks", "field_92017_k"});
        } catch (Exception e) {
            Reflection.logFailure(e);
        }
    }

    public static int getHighlightedItemTicks(final GuiIngame gui) {
        try {
            return (Integer) ObfuscationReflectionHelper.getPrivateValue((Class) GuiIngame.class, (Object) gui, new String[]{"remainingHighlightTicks", "field_92017_k"});
        } catch (Exception e) {
            Reflection.logFailure(e);
            return 0;
        }
    }

    public static ItemStack getHighlightedItemStack(final GuiIngame gui) {
        try {
            return (ItemStack) ObfuscationReflectionHelper.getPrivateValue((Class) GuiIngame.class, (Object) gui, new String[]{"highlightingItemStack", "field_92016_l"});
        } catch (Exception e) {
            Reflection.logFailure(e);
            return null;
        }
    }

    public static int getGuiLeft(final GuiContainer gui) {
        try {
            return (Integer) ObfuscationReflectionHelper.getPrivateValue((Class) GuiContainer.class, (Object) gui, new String[]{"guiLeft", "field_147003_i"});
        } catch (Exception e) {
            Reflection.logFailure(e);
            return 0;
        }
    }

    public static int getGuiTop(final GuiContainer gui) {
        try {
            return (Integer) ObfuscationReflectionHelper.getPrivateValue((Class) GuiContainer.class, (Object) gui, new String[]{"guiTop", "field_147009_r"});
        } catch (Exception e) {
            Reflection.logFailure(e);
            return 0;
        }
    }

    public static int getGuiXSize(final GuiContainer gui) {
        try {
            return (Integer) ObfuscationReflectionHelper.getPrivateValue((Class) GuiContainer.class, (Object) gui, new String[]{"xSize", "field_146999_f"});
        } catch (Exception e) {
            Reflection.logFailure(e);
            return 0;
        }
    }

}
