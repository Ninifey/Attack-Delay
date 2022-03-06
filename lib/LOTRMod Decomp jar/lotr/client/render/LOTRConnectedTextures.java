// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Iterator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import javax.imageio.ImageIO;
import net.minecraft.util.ResourceLocation;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.IIconRegister;
import java.util.Set;
import java.util.HashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;
import lotr.common.block.LOTRConnectedBlock;
import net.minecraft.util.IIcon;
import java.util.Map;

public class LOTRConnectedTextures
{
    private static Map<String, Map<Integer, IIcon>> blockIconsMap;
    
    public static IIcon getConnectedIconBlock(final LOTRConnectedBlock block, final IBlockAccess world, final int i, final int j, final int k, final int side, final boolean noBase) {
        final int meta = world.getBlockMetadata(i, j, k);
        final String blockName = block.getConnectedName(meta);
        final boolean[][] flags = new boolean[3][3];
        for (int x = -1; x <= 1; ++x) {
            for (int y = -1; y <= 1; ++y) {
                boolean match = false;
                if (x == 0 && y == 0) {
                    match = true;
                }
                else {
                    int i2 = i;
                    int j2 = j;
                    int k2 = k;
                    if (side == 0) {
                        i2 += x;
                        k2 += y;
                    }
                    else if (side == 1) {
                        i2 += x;
                        k2 += y;
                    }
                    else if (side == 2) {
                        i2 -= x;
                        j2 -= y;
                    }
                    else if (side == 3) {
                        i2 += x;
                        j2 -= y;
                    }
                    else if (side == 4) {
                        k2 += x;
                        j2 -= y;
                    }
                    else if (side == 5) {
                        k2 -= x;
                        j2 -= y;
                    }
                    match = block.areBlocksConnected(world, i, j, k, i2, j2, k2);
                }
                flags[x + 1][y + 1] = match;
            }
        }
        return getConnectedIcon(blockName, flags, noBase);
    }
    
    public static IIcon getConnectedIconItem(final LOTRConnectedBlock block, final int meta) {
        final String blockName = block.getConnectedName(meta);
        final boolean[][] flags = { { false, false, false }, { false, true, false }, { false, false, false } };
        return getConnectedIcon(blockName, flags, false);
    }
    
    private static IIcon getConnectedIcon(final String blockName, final boolean[][] flags, final boolean noBase) {
        if (!LOTRConnectedTextures.blockIconsMap.containsKey(blockName) || LOTRConnectedTextures.blockIconsMap.get(blockName).isEmpty()) {
            return (IIcon)Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("");
        }
        final Set<IconElement> set = new HashSet<IconElement>();
        if (!noBase) {
            set.add(IconElement.BASE);
        }
        if (flags != null) {
            final boolean topLeft = flags[0][0];
            final boolean top = flags[1][0];
            final boolean topRight = flags[2][0];
            final boolean left = flags[0][1];
            final boolean mid = flags[1][1];
            final boolean right = flags[2][1];
            final boolean bottomLeft = flags[0][2];
            final boolean bottom = flags[1][2];
            final boolean bottomRight = flags[2][2];
            if (!left) {
                set.add(IconElement.SIDE_LEFT);
            }
            if (!right) {
                set.add(IconElement.SIDE_RIGHT);
            }
            if (!top) {
                set.add(IconElement.SIDE_TOP);
            }
            if (!bottom) {
                set.add(IconElement.SIDE_BOTTOM);
            }
            if (!left && !top) {
                set.add(IconElement.CORNER_TOPLEFT);
            }
            if (!right && !top) {
                set.add(IconElement.CORNER_TOPRIGHT);
            }
            if (!left && !bottom) {
                set.add(IconElement.CORNER_BOTTOMLEFT);
            }
            if (!right && !bottom) {
                set.add(IconElement.CORNER_BOTTOMRIGHT);
            }
            if (left && top && !topLeft) {
                set.add(IconElement.INVCORNER_TOPLEFT);
            }
            if (right && top && !topRight) {
                set.add(IconElement.INVCORNER_TOPRIGHT);
            }
            if (left && bottom && !bottomLeft) {
                set.add(IconElement.INVCORNER_BOTTOMLEFT);
            }
            if (right && bottom && !bottomRight) {
                set.add(IconElement.INVCORNER_BOTTOMRIGHT);
            }
        }
        final int key = IconElement.getIconSetKey(set);
        return LOTRConnectedTextures.blockIconsMap.get(blockName).get(key);
    }
    
    public static void registerConnectedIcons(final IIconRegister iconregister, final LOTRConnectedBlock block, final int meta, final boolean includeNoBase) {
        final String iconName = block.getConnectedName(meta);
        final Map<IconElement, BufferedImage> iconElementMap = getConnectedIconElements(iconName);
        createConnectedIcons(iconregister, block, meta, includeNoBase, iconElementMap);
    }
    
    private static String getBaseIconName(final String blockName) {
        String s = blockName;
        final int pathIndex = s.indexOf(":");
        if (pathIndex >= 0) {
            s = s.substring(pathIndex + 1);
        }
        return s;
    }
    
    private static String getModID(final String blockName) {
        final String s = blockName;
        final int pathIndex = s.indexOf(":");
        if (pathIndex >= 0) {
            return s.substring(0, pathIndex);
        }
        return "";
    }
    
    private static Map<IconElement, BufferedImage> getConnectedIconElements(final String iconName) {
        final Minecraft mc = Minecraft.getMinecraft();
        final IResourceManager resourceManager = mc.getResourceManager();
        final String baseIconName = getBaseIconName(iconName);
        final String modID = getModID(iconName);
        final Map<IconElement, BufferedImage> iconElementMap = new HashMap<IconElement, BufferedImage>();
        try {
            for (final IconElement e : IconElement.values()) {
                final ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + "_" + e.iconName + ".png");
                final BufferedImage image = ImageIO.read(resourceManager.getResource(res).getInputStream());
                iconElementMap.put(e, image);
            }
        }
        catch (IOException e2) {
            FMLLog.severe("Failed to load connected textures for %s", new Object[] { modID + ":" + baseIconName });
            e2.printStackTrace();
        }
        return iconElementMap;
    }
    
    private static void createConnectedIcons(final IIconRegister iconregister, final LOTRConnectedBlock block, final int meta, final boolean includeNoBase, final Map<IconElement, BufferedImage> iconElementMap) {
        final String blockName = block.getConnectedName(meta);
        LOTRConnectedTextures.blockIconsMap.remove(blockName);
        final Minecraft mc = Minecraft.getMinecraft();
        final IResourceManager resourceManager = mc.getResourceManager();
        final TextureMap textureMap = (TextureMap)iconregister;
        final String baseIconName = getBaseIconName(blockName);
        final String modID = getModID(blockName);
        final BufferedImage iconElementBase = iconElementMap.get(IconElement.BASE);
        final int iconWidth = iconElementBase.getWidth();
        final int iconHeight = iconElementBase.getHeight();
        for (final Map.Entry<IconElement, BufferedImage> entry : iconElementMap.entrySet()) {
            final IconElement elemt = entry.getKey();
            final BufferedImage img = entry.getValue();
            if (elemt != IconElement.BASE && (img.getWidth() != iconWidth || img.getHeight() != iconHeight)) {
                FMLLog.severe("LOTR: All connected texture icons for " + baseIconName + " must have the same dimensions!", new Object[0]);
                final BufferedImage errored = new BufferedImage(iconWidth, iconHeight, 2);
                for (int i = 0; i < errored.getWidth(); ++i) {
                    for (int j = 0; j < errored.getHeight(); ++j) {
                        int rgb = 0;
                        if ((i + j) % 2 == 0) {
                            rgb = 16711680;
                        }
                        else {
                            rgb = 0;
                        }
                        errored.setRGB(i, j, 0xFF000000 | rgb);
                    }
                }
                entry.setValue(errored);
            }
        }
        final Map<Integer, IIcon> iconsMap = new HashMap<Integer, IIcon>();
        for (final Map.Entry<Integer, Set<IconElement>> entry2 : IconElement.allCombos.entrySet()) {
            final int key = entry2.getKey();
            final Set<IconElement> set = entry2.getValue();
            final List<IconElement> list = IconElement.sortIconSet(set);
            if (!includeNoBase && !list.contains(IconElement.BASE)) {
                continue;
            }
            final String iconName = modID + ":textures/blocks/" + baseIconName + "_" + key;
            if (textureMap.getTextureExtry(iconName) != null) {
                FMLLog.severe("Icon is already registered for %s", new Object[] { iconName });
            }
            else {
                final BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);
                for (final IconElement e : list) {
                    final BufferedImage baseIconImage = iconElementMap.get(e);
                    for (int k = 0; k < iconImage.getWidth(); ++k) {
                        for (int l = 0; l < iconImage.getHeight(); ++l) {
                            final int rgb2 = baseIconImage.getRGB(k, l);
                            final int alpha = rgb2 & 0xFF000000;
                            if (alpha != 0) {
                                iconImage.setRGB(k, l, rgb2);
                            }
                        }
                    }
                }
                final LOTRBufferedImageIcon icon = new LOTRBufferedImageIcon(iconName, iconImage);
                icon.setIconWidth(iconImage.getWidth());
                icon.setIconHeight(iconImage.getHeight());
                textureMap.setTextureEntry(iconName, (TextureAtlasSprite)icon);
                iconsMap.put(key, (IIcon)icon);
            }
        }
        LOTRConnectedTextures.blockIconsMap.put(blockName, iconsMap);
    }
    
    public static void registerNonConnectedGateIcons(final IIconRegister iconregister, final LOTRConnectedBlock block, final int meta) {
        registerNonConnectedGateIcons(iconregister, block, meta, block.getConnectedName(meta));
    }
    
    public static void registerNonConnectedGateIcons(final IIconRegister iconregister, final LOTRConnectedBlock block, final int meta, final String iconName) {
        final Minecraft mc = Minecraft.getMinecraft();
        final IResourceManager resourceManager = mc.getResourceManager();
        final String baseIconName = getBaseIconName(iconName);
        final String modID = getModID(iconName);
        final Map<IconElement, BufferedImage> iconElementMap = new HashMap<IconElement, BufferedImage>();
        try {
            final ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + ".png");
            final BufferedImage blockIconImage = ImageIO.read(resourceManager.getResource(res).getInputStream());
            final int iconWidth = blockIconImage.getWidth();
            final int iconHeight = blockIconImage.getHeight();
            final int sideWidth = Math.max(Math.round(iconWidth / 16.0f * 3.0f), 1);
            final int sideHeight = Math.max(Math.round(iconHeight / 16.0f * 3.0f), 1);
            final BufferedImage emptyBase = new BufferedImage(iconWidth, iconHeight, 2);
            iconElementMap.put(IconElement.BASE, emptyBase);
            iconElementMap.put(IconElement.SIDE_LEFT, getSubImageIcon(blockIconImage, 0, 0, sideWidth, iconHeight));
            iconElementMap.put(IconElement.SIDE_RIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, iconHeight));
            iconElementMap.put(IconElement.SIDE_TOP, getSubImageIcon(blockIconImage, 0, 0, iconWidth, sideHeight));
            iconElementMap.put(IconElement.SIDE_BOTTOM, getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, iconWidth, sideHeight));
            iconElementMap.put(IconElement.CORNER_TOPLEFT, getSubImageIcon(blockIconImage, 0, 0, sideWidth, sideHeight));
            iconElementMap.put(IconElement.CORNER_TOPRIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, sideHeight));
            iconElementMap.put(IconElement.CORNER_BOTTOMLEFT, getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, sideWidth, sideHeight));
            iconElementMap.put(IconElement.CORNER_BOTTOMRIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, iconHeight - sideHeight, sideWidth, sideHeight));
            iconElementMap.put(IconElement.INVCORNER_TOPLEFT, iconElementMap.get(IconElement.CORNER_TOPLEFT));
            iconElementMap.put(IconElement.INVCORNER_TOPRIGHT, iconElementMap.get(IconElement.CORNER_TOPRIGHT));
            iconElementMap.put(IconElement.INVCORNER_BOTTOMLEFT, iconElementMap.get(IconElement.CORNER_BOTTOMLEFT));
            iconElementMap.put(IconElement.INVCORNER_BOTTOMRIGHT, iconElementMap.get(IconElement.CORNER_BOTTOMRIGHT));
        }
        catch (IOException e) {
            FMLLog.severe("Failed to load connected textures for %s", new Object[] { modID + ":" + baseIconName });
            e.printStackTrace();
        }
        createConnectedIcons(iconregister, block, meta, false, iconElementMap);
    }
    
    private static BufferedImage getSubImageIcon(final BufferedImage base, final int x, final int y, final int width, final int height) {
        final BufferedImage subpart = base.getSubimage(x, y, width, height);
        final BufferedImage img = new BufferedImage(base.getWidth(), base.getHeight(), 2);
        for (int subX = 0; subX < width; ++subX) {
            for (int subY = 0; subY < height; ++subY) {
                img.setRGB(x + subX, y + subY, subpart.getRGB(subX, subY));
            }
        }
        return img;
    }
    
    static {
        LOTRConnectedTextures.blockIconsMap = new HashMap<String, Map<Integer, IIcon>>();
    }
    
    private enum IconElement
    {
        BASE("base", 0), 
        SIDE_LEFT("left", 1), 
        SIDE_RIGHT("right", 1), 
        SIDE_TOP("top", 1), 
        SIDE_BOTTOM("bottom", 1), 
        CORNER_TOPLEFT("topLeft", 2), 
        CORNER_TOPRIGHT("topRight", 2), 
        CORNER_BOTTOMLEFT("bottomLeft", 2), 
        CORNER_BOTTOMRIGHT("bottomRight", 2), 
        INVCORNER_TOPLEFT("topLeftInv", 2), 
        INVCORNER_TOPRIGHT("topRightInv", 2), 
        INVCORNER_BOTTOMLEFT("bottomLeftInv", 2), 
        INVCORNER_BOTTOMRIGHT("bottomRightInv", 2);
        
        public final String iconName;
        private final int bitFlag;
        private final int priority;
        private static EnumSet<IconElement> allSides;
        private static EnumSet<IconElement> allCorners;
        private static EnumSet<IconElement> allInvCorners;
        public static Map<Integer, Set<IconElement>> allCombos;
        private static Comparator<IconElement> comparator;
        
        private IconElement(final String s, final int i) {
            this.iconName = s;
            this.bitFlag = 1 << this.ordinal();
            this.priority = i;
        }
        
        public static int getIconSetKey(final Set<IconElement> set) {
            int i = 0;
            for (final IconElement e : values()) {
                if (set.contains(e)) {
                    i |= e.bitFlag;
                }
            }
            return i;
        }
        
        public static List<IconElement> sortIconSet(final Set<IconElement> set) {
            final List<IconElement> list = new ArrayList<IconElement>();
            list.addAll(set);
            Collections.sort(list, IconElement.comparator);
            return list;
        }
        
        static {
            IconElement.allSides = EnumSet.of(IconElement.SIDE_LEFT, IconElement.SIDE_RIGHT, IconElement.SIDE_TOP, IconElement.SIDE_BOTTOM);
            IconElement.allCorners = EnumSet.of(IconElement.CORNER_TOPLEFT, IconElement.CORNER_TOPRIGHT, IconElement.CORNER_BOTTOMLEFT, IconElement.CORNER_BOTTOMRIGHT);
            IconElement.allInvCorners = EnumSet.of(IconElement.INVCORNER_TOPLEFT, IconElement.INVCORNER_TOPRIGHT, IconElement.INVCORNER_BOTTOMLEFT, IconElement.INVCORNER_BOTTOMRIGHT);
            IconElement.allCombos = new HashMap<Integer, Set<IconElement>>();
            final List<Set<IconElement>> permutations = new ArrayList<Set<IconElement>>();
            final boolean[] array;
            final boolean[] trueOrFalse = array = new boolean[] { false, true };
            for (final boolean base : array) {
                for (final boolean left : trueOrFalse) {
                    for (final boolean right : trueOrFalse) {
                        for (final boolean top : trueOrFalse) {
                            for (final boolean bottom : trueOrFalse) {
                                for (final boolean topLeft : trueOrFalse) {
                                    for (final boolean topRight : trueOrFalse) {
                                        for (final boolean bottomLeft : trueOrFalse) {
                                            for (final boolean bottomRight : trueOrFalse) {
                                                for (final boolean topLeftInv : trueOrFalse) {
                                                    for (final boolean topRightInv : trueOrFalse) {
                                                        for (final boolean bottomLeftInv : trueOrFalse) {
                                                            for (final boolean bottomRightInv : trueOrFalse) {
                                                                final Set set = new HashSet();
                                                                if (base) {
                                                                    set.add(IconElement.BASE);
                                                                }
                                                                final boolean addLeft = left && (!top || topLeft) && (!bottom || bottomLeft);
                                                                final boolean addRight = right && (!top || topRight) && (!bottom || bottomRight);
                                                                final boolean addTop = top && (!left || topLeft) && (!right || topRight);
                                                                final boolean addBottom = bottom && (!left || bottomLeft) && (!right || bottomRight);
                                                                if (addLeft) {
                                                                    set.add(IconElement.SIDE_LEFT);
                                                                }
                                                                if (addRight) {
                                                                    set.add(IconElement.SIDE_RIGHT);
                                                                }
                                                                if (addTop) {
                                                                    set.add(IconElement.SIDE_TOP);
                                                                }
                                                                if (addBottom) {
                                                                    set.add(IconElement.SIDE_BOTTOM);
                                                                }
                                                                if (topLeft && addTop && addLeft) {
                                                                    set.add(IconElement.CORNER_TOPLEFT);
                                                                }
                                                                if (topRight && addTop && addRight) {
                                                                    set.add(IconElement.CORNER_TOPRIGHT);
                                                                }
                                                                if (bottomLeft && addBottom && addLeft) {
                                                                    set.add(IconElement.CORNER_BOTTOMLEFT);
                                                                }
                                                                if (bottomRight && addBottom && addRight) {
                                                                    set.add(IconElement.CORNER_BOTTOMRIGHT);
                                                                }
                                                                if (topLeftInv && !topLeft && !addTop && !addLeft) {
                                                                    set.add(IconElement.INVCORNER_TOPLEFT);
                                                                }
                                                                if (topRightInv && !topRight && !addTop && !addRight) {
                                                                    set.add(IconElement.INVCORNER_TOPRIGHT);
                                                                }
                                                                if (bottomLeftInv && !bottomLeft && !addBottom && !addLeft) {
                                                                    set.add(IconElement.INVCORNER_BOTTOMLEFT);
                                                                }
                                                                if (bottomRightInv && !bottomRight && !addBottom && !addRight) {
                                                                    set.add(IconElement.INVCORNER_BOTTOMRIGHT);
                                                                }
                                                                permutations.add(set);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (final Set<IconElement> iconSet : permutations) {
                final int key = getIconSetKey(iconSet);
                if (!IconElement.allCombos.containsKey(key)) {
                    IconElement.allCombos.put(key, iconSet);
                }
            }
            IconElement.comparator = new Comparator<IconElement>() {
                @Override
                public int compare(final IconElement e1, final IconElement e2) {
                    if (e1.priority == e2.priority) {
                        return e1.compareTo(e2);
                    }
                    return Integer.valueOf(e1.priority).compareTo(e2.priority);
                }
            };
        }
    }
}
