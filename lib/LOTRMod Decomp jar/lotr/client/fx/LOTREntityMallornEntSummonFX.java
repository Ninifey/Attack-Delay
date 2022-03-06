// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityDiggingFX;

public class LOTREntityMallornEntSummonFX extends EntityDiggingFX
{
    private Entity summoner;
    private Entity summoned;
    private int summonedID;
    private float arcParam;
    
    public LOTREntityMallornEntSummonFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final int summonerID, final int summonedID, final float t, final Block block, final int meta, final int color) {
        super(world, d, d1, d2, d3, d4, d5, block, meta);
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
        this.summoner = ((Entity)this).worldObj.getEntityByID(summonerID);
        this.summoned = ((Entity)this).worldObj.getEntityByID(summonedID);
        this.arcParam = t;
        ((EntityFX)this).particleMaxAge = (int)(40.0f * this.arcParam);
        ((EntityFX)this).particleRed *= (color >> 16 & 0xFF) / 255.0f;
        ((EntityFX)this).particleGreen *= (color >> 8 & 0xFF) / 255.0f;
        ((EntityFX)this).particleBlue *= (color & 0xFF) / 255.0f;
        ((EntityFX)this).particleScale *= 2.0f;
        ((EntityFX)this).particleGravity = 0.0f;
        ((Entity)this).renderDistanceWeight = 10.0;
        ((Entity)this).noClip = true;
        this.updateArcPos();
    }
    
    public void onUpdate() {
        super.onUpdate();
        this.updateArcPos();
    }
    
    private void updateArcPos() {
        if (this.summoner == null || this.summoned == null || !this.summoner.isEntityAlive() || !this.summoned.isEntityAlive()) {
            this.setDead();
            return;
        }
        final double[] posA = { this.summoner.posX, this.summoner.boundingBox.minY + this.summoner.height * 0.7, this.summoner.posZ };
        final double[] posC = { this.summoned.posX, this.summoned.boundingBox.minY + this.summoned.height * 0.7, this.summoned.posZ };
        final double[] posB = { (posA[0] + posC[0]) / 2.0, (posA[1] + posC[1]) / 2.0 + 20.0, (posA[2] + posC[2]) / 2.0 };
        final double[] ab = this.lerp(posA, posB, this.arcParam);
        final double[] bc = this.lerp(posB, posC, this.arcParam);
        final double[] abbc = this.lerp(ab, bc, this.arcParam);
        ((Entity)this).posX = abbc[0];
        ((Entity)this).posY = abbc[1];
        ((Entity)this).posZ = abbc[2];
    }
    
    private double[] lerp(final double[] a, final double[] b, final float t) {
        final double[] ab = new double[a.length];
        for (int i = 0; i < ab.length; ++i) {
            ab[i] = a[i] + (b[i] - a[i]) * t;
        }
        return ab;
    }
}
