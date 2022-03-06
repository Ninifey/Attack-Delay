// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import net.minecraft.entity.EntityLivingBase;
import lotr.client.model.LOTRModelDwarf;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarfCommander extends LOTRRenderDwarf
{
    private static ResourceLocation cloak;
    private static ResourceLocation blueCloak;
    private LOTRModelDwarf cloakModel;
    
    public LOTRRenderDwarfCommander() {
        this.cloakModel = new LOTRModelDwarf(1.5f);
    }
    
    protected ResourceLocation getCloakTexture(final EntityLivingBase entity) {
        return (entity instanceof LOTREntityBlueDwarf) ? LOTRRenderDwarfCommander.blueCloak : LOTRRenderDwarfCommander.cloak;
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        if (pass == 0) {
            this.bindTexture(this.getCloakTexture((EntityLivingBase)entity));
            this.cloakModel.bipedHead.showModel = false;
            this.cloakModel.bipedHeadwear.showModel = false;
            this.cloakModel.bipedBody.showModel = true;
            this.cloakModel.bipedRightArm.showModel = true;
            this.cloakModel.bipedLeftArm.showModel = true;
            this.cloakModel.bipedRightLeg.showModel = false;
            this.cloakModel.bipedLeftLeg.showModel = false;
            this.setRenderPassModel((ModelBase)this.cloakModel);
            ((ModelBase)this.cloakModel).onGround = ((RendererLivingEntity)this).mainModel.onGround;
            ((ModelBase)this.cloakModel).isRiding = ((RendererLivingEntity)this).mainModel.isRiding;
            ((ModelBase)this.cloakModel).isChild = ((RendererLivingEntity)this).mainModel.isChild;
            this.cloakModel.heldItemRight = super.modelBipedMain.heldItemRight;
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderDwarfCommander.cloak = new ResourceLocation("lotr:mob/dwarf/commander_cloak.png");
        LOTRRenderDwarfCommander.blueCloak = new ResourceLocation("lotr:mob/dwarf/blueMountains_commander_cloak.png");
    }
}
