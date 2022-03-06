// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.boss.BossStatus;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMountainTrollChieftain extends LOTRRenderMountainTroll
{
    private static ResourceLocation armorTexture;
    private LOTRModelTroll helmetModel;
    private LOTRModelTroll chestplateModel;
    
    public LOTRRenderMountainTrollChieftain() {
        this.helmetModel = new LOTRModelTroll(1.5f, 2);
        this.chestplateModel = new LOTRModelTroll(1.5f, 3);
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        final LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain)entity;
        if (((Entity)troll).addedToChunk) {
            BossStatus.setBossStatus((IBossDisplayData)troll, false);
        }
    }
    
    @Override
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        final LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain)entity;
        this.bindTexture(LOTRRenderMountainTrollChieftain.armorTexture);
        if (pass == 2 && troll.getTrollArmorLevel() >= 2) {
            this.helmetModel.onGround = ((RendererLivingEntity)this).mainModel.onGround;
            this.setRenderPassModel((ModelBase)this.helmetModel);
            return 1;
        }
        if (pass == 3 && troll.getTrollArmorLevel() >= 1) {
            this.chestplateModel.onGround = ((RendererLivingEntity)this).mainModel.onGround;
            this.setRenderPassModel((ModelBase)this.chestplateModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        GL11.glTranslatef(0.0f, -((LOTREntityMountainTrollChieftain)entity).getSpawningOffset(f), 0.0f);
    }
    
    static {
        LOTRRenderMountainTrollChieftain.armorTexture = new ResourceLocation("lotr:mob/troll/mountainTrollChieftain_armor.png");
    }
}
