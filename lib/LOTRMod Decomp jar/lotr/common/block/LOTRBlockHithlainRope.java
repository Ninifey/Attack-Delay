// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.DamageSource;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRBlockHithlainRope extends LOTRBlockRope
{
    public LOTRBlockHithlainRope() {
        super(true);
        this.setLightLevel(0.375f);
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isOnLadder()) {
            final LOTRFaction ropeFaction = LOTRFaction.GALADHRIM;
            boolean harm = false;
            if (entity instanceof EntityPlayer) {
                harm = (LOTRLevelData.getData((EntityPlayer)entity).getAlignment(ropeFaction) < 0.0f);
            }
            else {
                harm = LOTRMod.getNPCFaction(entity).isBadRelation(ropeFaction);
            }
            if (harm) {
                entity.attackEntityFrom(DamageSource.magic, 1.0f);
            }
        }
    }
}
