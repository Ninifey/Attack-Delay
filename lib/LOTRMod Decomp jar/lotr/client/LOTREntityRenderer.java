// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.Vec3;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

public class LOTREntityRenderer extends EntityRenderer
{
    private Minecraft theMC;
    private Entity thePointedEntity;
    
    public LOTREntityRenderer(final Minecraft mc, final IResourceManager irm) {
        super(mc, irm);
        this.theMC = mc;
    }
    
    public void getMouseOver(final float partialTick) {
        if (this.theMC.renderViewEntity != null && this.theMC.theWorld != null) {
            this.theMC.pointedEntity = null;
            this.thePointedEntity = null;
            double blockReach = this.theMC.playerController.getBlockReachDistance();
            final float meleeReachFactor = LOTRWeaponStats.getMeleeReachFactor(this.theMC.thePlayer.getHeldItem());
            blockReach *= meleeReachFactor;
            this.theMC.objectMouseOver = this.theMC.renderViewEntity.rayTrace(blockReach, partialTick);
            double maxDist;
            final double reach = maxDist = LOTRWeaponStats.getMeleeReachDistance((EntityPlayer)this.theMC.thePlayer);
            final Vec3 posVec = this.theMC.renderViewEntity.getPosition(partialTick);
            if (this.theMC.objectMouseOver != null) {
                maxDist = this.theMC.objectMouseOver.hitVec.distanceTo(posVec);
            }
            final Vec3 lookVec = this.theMC.renderViewEntity.getLook(partialTick);
            final Vec3 sightVec = posVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
            Vec3 targetVec = null;
            final float lookWidth = LOTRWeaponStats.getMeleeExtraLookWidth();
            final List entities = this.theMC.theWorld.getEntitiesWithinAABBExcludingEntity((Entity)this.theMC.renderViewEntity, ((Entity)this.theMC.renderViewEntity).boundingBox.addCoord(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach).expand((double)lookWidth, (double)lookWidth, (double)lookWidth));
            double leastDist = maxDist;
            for (int i = 0; i < entities.size(); ++i) {
                final Entity entity = entities.get(i);
                if (entity.canBeCollidedWith()) {
                    final float f = entity.getCollisionBorderSize();
                    final AxisAlignedBB entityBB = entity.boundingBox.expand((double)f, (double)f, (double)f);
                    final MovingObjectPosition movingobjectposition = entityBB.calculateIntercept(posVec, sightVec);
                    if (entityBB.isVecInside(posVec)) {
                        if (0.0 < leastDist || leastDist == 0.0) {
                            this.thePointedEntity = entity;
                            targetVec = ((movingobjectposition == null) ? posVec : movingobjectposition.hitVec);
                            leastDist = 0.0;
                        }
                    }
                    else if (movingobjectposition != null) {
                        final double entityDist = posVec.distanceTo(movingobjectposition.hitVec);
                        if (entityDist < leastDist || leastDist == 0.0) {
                            if (entity == ((Entity)this.theMC.renderViewEntity).ridingEntity && !entity.canRiderInteract()) {
                                if (leastDist == 0.0) {
                                    this.thePointedEntity = entity;
                                    targetVec = movingobjectposition.hitVec;
                                }
                            }
                            else {
                                this.thePointedEntity = entity;
                                targetVec = movingobjectposition.hitVec;
                                leastDist = entityDist;
                            }
                        }
                    }
                }
            }
            if (this.thePointedEntity != null && (leastDist < maxDist || this.theMC.objectMouseOver == null)) {
                this.theMC.objectMouseOver = new MovingObjectPosition(this.thePointedEntity, targetVec);
                if (this.thePointedEntity instanceof EntityLivingBase || this.thePointedEntity instanceof EntityItemFrame) {
                    this.theMC.pointedEntity = this.thePointedEntity;
                }
            }
        }
    }
    
    public void updateRenderer() {
        super.updateRenderer();
        if (Minecraft.isGuiEnabled()) {
            final float wight = LOTRClientProxy.tickHandler.getWightLookFactor();
            final float hand = LOTRReflectionClient.getHandFOV(this);
            LOTRReflectionClient.setHandFOV(this, hand + wight * 0.3f);
        }
    }
}
