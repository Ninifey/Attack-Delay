// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.client.LOTRSpeechClient;
import net.minecraft.client.Minecraft;
import lotr.common.entity.npc.LOTREntityGandalf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelWizardHat;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGandalf extends LOTRRenderBiped
{
    private static ResourceLocation skin;
    private static ResourceLocation hat;
    private static ResourceLocation cloak;
    private ModelBiped hatModel;
    private ModelBiped cloakModel;
    
    public LOTRRenderGandalf() {
        super(new LOTRModelHuman(), 0.5f);
        this.hatModel = new LOTRModelWizardHat(1.0f);
        this.cloakModel = new LOTRModelHuman(0.6f, false);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderGandalf.skin;
    }
    
    @Override
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityGandalf gandalf = (LOTREntityGandalf)entity;
        super.doRender((EntityLiving)gandalf, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && !LOTRSpeechClient.hasSpeech(gandalf)) {
            this.func_147906_a((Entity)gandalf, gandalf.getCommandSenderName(), d, d1 + 0.75, d2, 64);
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityGandalf gandalf = (LOTREntityGandalf)entity;
        if (pass == 0 && gandalf.getEquipmentInSlot(4) == null) {
            this.setRenderPassModel((ModelBase)this.hatModel);
            this.bindTexture(LOTRRenderGandalf.hat);
            return 1;
        }
        if (pass == 1 && gandalf.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)this.cloakModel);
            this.bindTexture(LOTRRenderGandalf.cloak);
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)gandalf, pass, f);
    }
    
    static {
        LOTRRenderGandalf.skin = new ResourceLocation("lotr:mob/char/gandalf.png");
        LOTRRenderGandalf.hat = new ResourceLocation("lotr:mob/char/gandalf_hat.png");
        LOTRRenderGandalf.cloak = new ResourceLocation("lotr:mob/char/gandalf_cloak.png");
    }
}
