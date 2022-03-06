// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.ITextureObject;
import java.util.Collection;
import net.minecraft.client.renderer.texture.LayeredTexture;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderHorse;

public class LOTRRenderHorse extends RenderHorse
{
    private static Map<String, ResourceLocation> layeredMountTextures;
    
    public LOTRRenderHorse() {
        super((ModelBase)new ModelHorse(), 0.75f);
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityHorse horse = (LOTREntityHorse)entity;
        final boolean fools = LOTRMod.isAprilFools();
        final int horseType = horse.getHorseType();
        if (fools) {
            horse.setHorseType(1);
        }
        super.doRender(entity, d, d1, d2, f, f1);
        if (fools) {
            horse.setHorseType(horseType);
        }
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityHorse horse = (LOTREntityHorse)entity;
        final ResourceLocation horseSkin = super.getEntityTexture(entity);
        return getLayeredMountTexture(horse, horseSkin);
    }
    
    public static ResourceLocation getLayeredMountTexture(final LOTRNPCMount mount, final ResourceLocation mountSkin) {
        final String skinPath = mountSkin.toString();
        final String armorPath = mount.getMountArmorTexture();
        if (armorPath == null || StringUtils.isBlank((CharSequence)armorPath)) {
            return mountSkin;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        final String path = "lotr_" + skinPath + "_" + armorPath;
        ResourceLocation texture = LOTRRenderHorse.layeredMountTextures.get(path);
        if (texture == null) {
            texture = new ResourceLocation(path);
            final List<String> layers = new ArrayList<String>();
            final ITextureObject skinTexture = mc.getTextureManager().getTexture(mountSkin);
            if (skinTexture instanceof LayeredTexture) {
                final LayeredTexture skinTextureLayered = (LayeredTexture)skinTexture;
                layers.addAll(skinTextureLayered.layeredTextureNames);
            }
            else {
                layers.add(skinPath);
            }
            layers.add(armorPath);
            mc.getTextureManager().loadTexture(texture, (ITextureObject)new LayeredTexture((String[])layers.toArray(new String[0])));
            LOTRRenderHorse.layeredMountTextures.put(path, texture);
        }
        return texture;
    }
    
    static {
        LOTRRenderHorse.layeredMountTextures = new HashMap<String, ResourceLocation>();
    }
}
