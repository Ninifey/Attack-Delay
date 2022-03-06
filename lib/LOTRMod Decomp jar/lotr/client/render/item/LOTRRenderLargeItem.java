// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import java.util.HashMap;
import lotr.client.LOTRClientProxy;
import lotr.common.item.LOTRItemLance;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.item.LOTRItemPike;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import net.minecraft.client.resources.IResource;
import java.util.Iterator;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderLargeItem implements IItemRenderer
{
    private static Map<String, Float> sizeFolders;
    private static Map<Pair<Item, String>, ResourceLocation> largeItemTextures;
    private final Item theItem;
    private final ResourceLocation largeTexture;
    private final String folderName;
    private final float largeIconScale;
    private final int largeIconSize;
    
    private static ResourceLocation createLargeItemTexture(final Item item, final String folder) {
        return createLargeItemTexture(item, "", folder);
    }
    
    private static ResourceLocation createLargeItemTexture(final Item item, final String extra, final String folder) {
        final String prefix = "lotr:";
        String itemName = item.getUnlocalizedName();
        itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
        String s = prefix + "textures/items/" + folder + "/" + itemName;
        if (!StringUtils.isNullOrEmpty(extra)) {
            s = s + "_" + extra;
        }
        s += ".png";
        return new ResourceLocation(s);
    }
    
    public static ResourceLocation getOrCreateLargeItemTexture(final Item item, final String folder) {
        return getOrCreateLargeItemTexture(item, "", folder);
    }
    
    public static ResourceLocation getOrCreateLargeItemTexture(final Item item, String extra, final String folder) {
        if (StringUtils.isNullOrEmpty(extra)) {
            extra = "";
        }
        final Pair<Item, String> key = (Pair<Item, String>)Pair.of((Object)item, (Object)extra);
        ResourceLocation texture = LOTRRenderLargeItem.largeItemTextures.get(key);
        if (texture == null) {
            texture = createLargeItemTexture(item, extra, folder);
            LOTRRenderLargeItem.largeItemTextures.put(key, texture);
        }
        return texture;
    }
    
    public static LOTRRenderLargeItem getLargeIconSize(final Item item) {
        for (final String folder : LOTRRenderLargeItem.sizeFolders.keySet()) {
            final float iconScale = LOTRRenderLargeItem.sizeFolders.get(folder);
            try {
                final ResourceLocation resLoc = createLargeItemTexture(item, folder);
                final IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resLoc);
                if (res == null) {
                    continue;
                }
                try {
                    final InputStream is = res.getInputStream();
                    final BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(is));
                    if (img == null) {
                        continue;
                    }
                    final int width = img.getWidth();
                    final int height = img.getHeight();
                    if (width == height) {
                        return new LOTRRenderLargeItem(item, resLoc, folder, iconScale, width);
                    }
                    System.out.println("LOTR: Large item texture " + item.getUnlocalizedName() + " width =/= height!");
                }
                catch (IOException e) {
                    System.out.println("LOTR: Error loading large item texture");
                    e.printStackTrace();
                }
            }
            catch (IOException ex) {}
        }
        return null;
    }
    
    public LOTRRenderLargeItem(final Item item, final ResourceLocation res, final String dir, final float f, final int i) {
        this.theItem = item;
        this.largeTexture = res;
        this.folderName = dir;
        this.largeIconScale = f;
        this.largeIconSize = i;
    }
    
    private ResourceLocation getSubtypeTexture(final String extra) {
        return getOrCreateLargeItemTexture(this.theItem, extra, this.folderName);
    }
    
    private void doTransformations() {
        GL11.glTranslatef(-(this.largeIconScale - 1.0f) / 2.0f, -(this.largeIconScale - 1.0f) / 2.0f, 0.0f);
        GL11.glScalef(this.largeIconScale, this.largeIconScale, 1.0f);
    }
    
    public void renderLargeItem() {
        this.renderLargeItem(null);
    }
    
    public void renderLargeItem(final String extra) {
        this.doTransformations();
        final ResourceLocation res = (extra == null) ? this.largeTexture : this.getSubtypeTexture(extra);
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final Tessellator tessellator = Tessellator.instance;
        ItemRenderer.renderItemIn2D(tessellator, 1.0f, 0.0f, 0.0f, 1.0f, this.largeIconSize, this.largeIconSize, 0.0625f);
    }
    
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        GL11.glPushMatrix();
        final Entity holder = (Entity)data[1];
        final boolean isFirstPerson = holder == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemSpear && holder instanceof EntityPlayer && ((EntityPlayer)holder).getItemInUse() == itemstack) {
            GL11.glRotatef(260.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        }
        if (item instanceof LOTRItemPike && holder instanceof EntityLivingBase) {
            final EntityLivingBase entityliving = (EntityLivingBase)holder;
            if (entityliving.getHeldItem() == itemstack && entityliving.swingProgress <= 0.0f) {
                if (entityliving.isSneaking()) {
                    if (isFirstPerson) {
                        GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
                    }
                    else {
                        GL11.glTranslatef(0.0f, -0.1f, 0.0f);
                        GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
                    }
                }
                else if (!isFirstPerson) {
                    GL11.glTranslatef(0.0f, -0.3f, 0.0f);
                    GL11.glRotatef(40.0f, 0.0f, 0.0f, 1.0f);
                }
            }
        }
        if (item instanceof LOTRItemLance && holder instanceof EntityLivingBase) {
            final EntityLivingBase entityliving = (EntityLivingBase)holder;
            if (entityliving.getHeldItem() == itemstack) {
                if (isFirstPerson) {
                    GL11.glRotatef(260.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
                }
                else {
                    GL11.glTranslatef(0.7f, 0.0f, 0.0f);
                    GL11.glRotatef(-30.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
                }
            }
        }
        this.renderLargeItem();
        if (itemstack != null && itemstack.hasEffect(0)) {
            LOTRClientProxy.renderEnchantmentEffect();
        }
        GL11.glPopMatrix();
    }
    
    static {
        (LOTRRenderLargeItem.sizeFolders = new HashMap<String, Float>()).put("large", 2.0f);
        LOTRRenderLargeItem.sizeFolders.put("large2", 3.0f);
        LOTRRenderLargeItem.largeItemTextures = new HashMap<Pair<Item, String>, ResourceLocation>();
    }
}
