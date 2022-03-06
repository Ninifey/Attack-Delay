// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityHobbitBartender;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHobbit;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHobbit extends LOTRRenderBiped
{
    private static LOTRRandomSkins hobbitSkinsMale;
    private static LOTRRandomSkins hobbitSkinsFemale;
    private static LOTRRandomSkins hobbitSkinsMaleChild;
    private static LOTRRandomSkins hobbitSkinsFemaleChild;
    private static ResourceLocation apron;
    private static ResourceLocation ringTexture;
    private ModelBiped standardRenderPassModel;
    
    public LOTRRenderHobbit() {
        super(new LOTRModelHobbit(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.standardRenderPassModel = new LOTRModelHobbit(0.5f, 64, 64)));
        LOTRRenderHobbit.hobbitSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_male");
        LOTRRenderHobbit.hobbitSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_female");
        LOTRRenderHobbit.hobbitSkinsMaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_male");
        LOTRRenderHobbit.hobbitSkinsFemaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_female");
    }
    
    @Override
    protected void func_82421_b() {
        super.field_82423_g = new LOTRModelHobbit(1.0f);
        super.field_82425_h = new LOTRModelHobbit(0.5f);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
        final boolean child = hobbit.isChild();
        if (hobbit.familyInfo.isMale()) {
            if (child) {
                return LOTRRenderHobbit.hobbitSkinsMaleChild.getRandomSkin(hobbit);
            }
            return LOTRRenderHobbit.hobbitSkinsMale.getRandomSkin(hobbit);
        }
        else {
            if (child) {
                return LOTRRenderHobbit.hobbitSkinsFemaleChild.getRandomSkin(hobbit);
            }
            return LOTRRenderHobbit.hobbitSkinsFemale.getRandomSkin(hobbit);
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
        this.standardRenderPassModel.bipedRightArm.showModel = true;
        if (pass == 1 && entity instanceof LOTREntityHobbitBartender) {
            this.bindTexture(LOTRRenderHobbit.apron);
            this.setRenderPassModel((ModelBase)this.standardRenderPassModel);
            return 1;
        }
        if (pass == 1 && hobbit.getClass() == hobbit.familyInfo.marriageEntityClass && hobbit.getEquipmentInSlot(4) != null && hobbit.getEquipmentInSlot(4).getItem() == hobbit.familyInfo.marriageRing) {
            this.bindTexture(LOTRRenderHobbit.ringTexture);
            this.standardRenderPassModel.bipedRightArm.showModel = false;
            this.setRenderPassModel((ModelBase)this.standardRenderPassModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        if (LOTRMod.isAprilFools()) {
            GL11.glScalef(2.0f, 2.0f, 2.0f);
        }
        else {
            GL11.glScalef(0.75f, 0.75f, 0.75f);
        }
    }
    
    @Override
    public float getHeldItemYTranslation() {
        return 0.075f;
    }
    
    static {
        LOTRRenderHobbit.apron = new ResourceLocation("lotr:mob/hobbit/bartender_apron.png");
        LOTRRenderHobbit.ringTexture = new ResourceLocation("lotr:mob/hobbit/ring.png");
    }
}
