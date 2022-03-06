// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import java.util.HashMap;
import net.minecraft.client.resources.IResourceManager;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import javax.imageio.ImageIO;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import java.awt.image.BufferedImage;
import java.util.Map;

public class LOTRDrinkIcons
{
    private static int maskColor;
    private static Map<String, BufferedImage> vesselIcons;
    private static Map<Item, BufferedImage> liquidIcons;
    
    public static IIcon registerDrinkIcon(final IIconRegister iconregister, final Item item, final String itemName, final String vessel) {
        final Minecraft mc = Minecraft.getMinecraft();
        final IResourceManager resourceManager = mc.getResourceManager();
        final TextureMap textureMap = (TextureMap)iconregister;
        final String baseIconName = itemName.substring("lotr:".length());
        try {
            BufferedImage vesselIcon = LOTRDrinkIcons.vesselIcons.get(vessel);
            if (vesselIcon == null) {
                final ResourceLocation res = new ResourceLocation("lotr", "textures/items/drink_" + vessel + ".png");
                vesselIcon = ImageIO.read(resourceManager.getResource(res).getInputStream());
                LOTRDrinkIcons.vesselIcons.put(vessel, vesselIcon);
            }
            BufferedImage liquidIcon = LOTRDrinkIcons.liquidIcons.get(item);
            if (liquidIcon == null) {
                final ResourceLocation res2 = new ResourceLocation("lotr", "textures/items/" + baseIconName + "_liquid.png");
                liquidIcon = ImageIO.read(resourceManager.getResource(res2).getInputStream());
                LOTRDrinkIcons.liquidIcons.put(item, liquidIcon);
            }
            final String iconName = "lotr:textures/items/" + baseIconName + "_" + vessel;
            final int iconWidth = vesselIcon.getWidth();
            final int iconHeight = vesselIcon.getHeight();
            final BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);
            for (int i = 0; i < iconImage.getWidth(); ++i) {
                for (int j = 0; j < iconImage.getHeight(); ++j) {
                    int rgb = vesselIcon.getRGB(i, j);
                    if ((rgb & 0xFFFFFF) == LOTRDrinkIcons.maskColor) {
                        rgb = liquidIcon.getRGB(i, j);
                    }
                    iconImage.setRGB(i, j, rgb);
                }
            }
            final LOTRBufferedImageIcon icon = new LOTRBufferedImageIcon(iconName, iconImage);
            icon.setIconWidth(iconImage.getWidth());
            icon.setIconHeight(iconImage.getHeight());
            textureMap.setTextureEntry(iconName, (TextureAtlasSprite)icon);
            return (IIcon)icon;
        }
        catch (IOException e) {
            FMLLog.severe("Failed to load mug textures for %s", new Object[] { item.getUnlocalizedName() });
            e.printStackTrace();
            return (IIcon)Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("");
        }
    }
    
    public static IIcon registerLiquidIcon(final IIconRegister iconregister, final Item item, final String itemName) {
        return iconregister.registerIcon(itemName + "_liquid");
    }
    
    static {
        LOTRDrinkIcons.maskColor = 16711935;
        LOTRDrinkIcons.vesselIcons = new HashMap<String, BufferedImage>();
        LOTRDrinkIcons.liquidIcons = new HashMap<Item, BufferedImage>();
    }
}
