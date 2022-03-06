// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderAngmarHillman extends LOTRRenderBiped
{
    private static LOTRRandomSkins hillmanSkinsMale;
    private static LOTRRandomSkins hillmanSkinsFemale;
    private static LOTRRandomSkins hillmanOutfits;
    private ModelBiped outfitModel;
    private boolean useOutfits;
    
    public LOTRRenderAngmarHillman(final boolean outfit) {
        super(new LOTRModelHuman(), 0.5f);
        this.outfitModel = new LOTRModelHuman(0.6f, false);
        this.useOutfits = outfit;
        this.setRenderPassModel((ModelBase)this.outfitModel);
        LOTRRenderAngmarHillman.hillmanSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_male");
        LOTRRenderAngmarHillman.hillmanSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_female");
        LOTRRenderAngmarHillman.hillmanOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/outfit");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman)entity;
        if (hillman.familyInfo.isMale()) {
            return LOTRRenderAngmarHillman.hillmanSkinsMale.getRandomSkin(hillman);
        }
        return LOTRRenderAngmarHillman.hillmanSkinsFemale.getRandomSkin(hillman);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman)entity;
        if (this.useOutfits && pass == 1 && hillman.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderAngmarHillman.hillmanOutfits.getRandomSkin(hillman));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)hillman, pass, f);
    }
}
