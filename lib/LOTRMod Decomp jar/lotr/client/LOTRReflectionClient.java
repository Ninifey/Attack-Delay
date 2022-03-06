// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.gui.FontRenderer;
import java.lang.reflect.Method;
import lotr.common.LOTRReflection;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class LOTRReflectionClient
{
    private static int[] colorCodes;
    
    public static void testAll(final World world, final Minecraft mc) {
        setCameraRoll(mc.entityRenderer, getCameraRoll(mc.entityRenderer));
        setHandFOV(mc.entityRenderer, getHandFOV(mc.entityRenderer));
        getColorCodes(mc.fontRenderer);
        setHighlightedItemTicks(mc.ingameGUI, getHighlightedItemTicks(mc.ingameGUI));
        getHighlightedItemStack(mc.ingameGUI);
    }
    
    public static void setCameraRoll(final EntityRenderer renderer, final float roll) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)renderer, (Object)roll, new String[] { "camRoll", "field_78495_O" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
        }
    }
    
    public static float getCameraRoll(final EntityRenderer renderer) {
        try {
            return (float)ObfuscationReflectionHelper.getPrivateValue((Class)EntityRenderer.class, (Object)renderer, new String[] { "camRoll", "field_78495_O" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0.0f;
        }
    }
    
    public static void setHandFOV(final EntityRenderer renderer, final float fov) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)renderer, (Object)fov, new String[] { "fovModifierHand", "field_78507_R" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
        }
    }
    
    public static float getHandFOV(final EntityRenderer renderer) {
        try {
            return (float)ObfuscationReflectionHelper.getPrivateValue((Class)EntityRenderer.class, (Object)renderer, new String[] { "fovModifierHand", "field_78507_R" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0.0f;
        }
    }
    
    public static float getFOVModifier(final EntityRenderer renderer, final float tick, final boolean flag) {
        try {
            final Method method = LOTRReflection.getPrivateMethod(EntityRenderer.class, renderer, new Class[] { Float.TYPE, Boolean.TYPE }, "getFOVModifier", "func_78481_a");
            return (float)method.invoke(renderer, tick, flag);
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0.0f;
        }
    }
    
    private static int[] getColorCodes(final FontRenderer fontRenderer) {
        if (LOTRReflectionClient.colorCodes == null) {
            try {
                LOTRReflectionClient.colorCodes = (int[])ObfuscationReflectionHelper.getPrivateValue((Class)FontRenderer.class, (Object)fontRenderer, new String[] { "colorCode", "field_78285_g" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
            }
        }
        return LOTRReflectionClient.colorCodes;
    }
    
    public static int getFormattingColor(final EnumChatFormatting ecf) {
        final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        final int colorIndex = ecf.ordinal();
        return getColorCodes(fr)[colorIndex];
    }
    
    public static void setHighlightedItemTicks(final GuiIngame gui, final int ticks) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)GuiIngame.class, (Object)gui, (Object)ticks, new String[] { "remainingHighlightTicks", "field_92017_k" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
        }
    }
    
    public static int getHighlightedItemTicks(final GuiIngame gui) {
        try {
            return (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiIngame.class, (Object)gui, new String[] { "remainingHighlightTicks", "field_92017_k" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }
    
    public static ItemStack getHighlightedItemStack(final GuiIngame gui) {
        try {
            return (ItemStack)ObfuscationReflectionHelper.getPrivateValue((Class)GuiIngame.class, (Object)gui, new String[] { "highlightingItemStack", "field_92016_l" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return null;
        }
    }
    
    public static int getGuiLeft(final GuiContainer gui) {
        try {
            return (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiContainer.class, (Object)gui, new String[] { "guiLeft", "field_147003_i" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }
    
    public static int getGuiTop(final GuiContainer gui) {
        try {
            return (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiContainer.class, (Object)gui, new String[] { "guiTop", "field_147009_r" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }
    
    public static int getGuiXSize(final GuiContainer gui) {
        try {
            return (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiContainer.class, (Object)gui, new String[] { "xSize", "field_146999_f" });
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }
}
