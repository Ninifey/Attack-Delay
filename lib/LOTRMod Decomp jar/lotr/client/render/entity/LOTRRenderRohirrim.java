// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityRohanShieldmaiden;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderRohirrim extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins skinsSoldier;
    private static LOTRRandomSkins skinsShieldmaiden;
    protected ModelBiped outfitModel;
    
    public LOTRRenderRohirrim() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderRohirrim.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_male");
        LOTRRenderRohirrim.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_female");
        LOTRRenderRohirrim.skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/warrior");
        LOTRRenderRohirrim.skinsShieldmaiden = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/shieldmaiden");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)entity;
        if (rohirrim.familyInfo.isMale()) {
            if (rohirrim instanceof LOTREntityRohirrimWarrior) {
                return LOTRRenderRohirrim.skinsSoldier.getRandomSkin(rohirrim);
            }
            return LOTRRenderRohirrim.skinsMale.getRandomSkin(rohirrim);
        }
        else {
            if (rohirrim instanceof LOTREntityRohanShieldmaiden) {
                return LOTRRenderRohirrim.skinsShieldmaiden.getRandomSkin(rohirrim);
            }
            return LOTRRenderRohirrim.skinsFemale.getRandomSkin(rohirrim);
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)entity;
        return super.shouldRenderPass((EntityLiving)rohirrim, pass, f);
    }
}
