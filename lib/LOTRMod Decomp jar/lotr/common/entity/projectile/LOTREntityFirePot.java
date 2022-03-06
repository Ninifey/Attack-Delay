// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityFirePot extends EntityThrowable
{
    public LOTREntityFirePot(final World world) {
        super(world);
    }
    
    public LOTREntityFirePot(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
    }
    
    public LOTREntityFirePot(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (!((Entity)this).worldObj.isClient) {
            final EntityLivingBase thrower = this.getThrower();
            final Entity hitEntity = m.entityHit;
            final double range = 3.0;
            final List entities = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, ((Entity)this).boundingBox.expand(range, range, range));
            if (hitEntity instanceof EntityLivingBase && !entities.contains(hitEntity)) {
                entities.add(hitEntity);
            }
            for (final Object obj : entities) {
                final EntityLivingBase entity = (EntityLivingBase)obj;
                float damage = 1.0f;
                if (entity == hitEntity) {
                    damage = 3.0f;
                }
                if (entity == hitEntity && thrower instanceof EntityPlayer && hitEntity instanceof LOTREntityBird && !((LOTREntityBird)hitEntity).isBirdStill()) {
                    LOTRLevelData.getData((EntityPlayer)thrower).addAchievement(LOTRAchievement.hitBirdFirePot);
                }
                if (entity.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)thrower), damage)) {
                    int fire = 2 + ((Entity)this).rand.nextInt(3);
                    if (entity == hitEntity) {
                        fire += 2 + ((Entity)this).rand.nextInt(3);
                    }
                    entity.setFire(fire);
                }
            }
            if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final Block block = ((Entity)this).worldObj.getBlock(m.blockX, m.blockY, m.blockZ);
                if (block instanceof LOTRBlockRhunFireJar) {
                    ((LOTRBlockRhunFireJar)block).explode(((Entity)this).worldObj, m.blockX, m.blockY, m.blockZ);
                }
            }
            ((Entity)this).worldObj.playSoundAtEntity((Entity)this, LOTRBlockPlate.soundTypePlate.getDigResourcePath(), 1.0f, (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.2f + 1.0f);
            this.setDead();
        }
        for (int i = 0; i < 8; ++i) {
            final double d = ((Entity)this).posX + MathHelper.randomFloatClamp(((Entity)this).rand, -0.25f, 0.25f);
            final double d2 = ((Entity)this).posY + MathHelper.randomFloatClamp(((Entity)this).rand, -0.25f, 0.25f);
            final double d3 = ((Entity)this).posZ + MathHelper.randomFloatClamp(((Entity)this).rand, -0.25f, 0.25f);
            ((Entity)this).worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(LOTRMod.rhunFireJar) + "_0", d, d2, d3, 0.0, 0.0, 0.0);
        }
        for (int l = 0; l < 16; ++l) {
            final String s = ((Entity)this).rand.nextBoolean() ? "flame" : "smoke";
            final double d4 = ((Entity)this).posX;
            final double d5 = ((Entity)this).posY;
            final double d6 = ((Entity)this).posZ;
            final double d7 = MathHelper.randomFloatClamp(((Entity)this).rand, -0.1f, 0.1f);
            final double d8 = MathHelper.randomFloatClamp(((Entity)this).rand, 0.2f, 0.3f);
            final double d9 = MathHelper.randomFloatClamp(((Entity)this).rand, -0.1f, 0.1f);
            ((Entity)this).worldObj.spawnParticle(s, d4, d5, d6, d7, d8, d9);
        }
    }
    
    protected float func_70182_d() {
        return 1.2f;
    }
    
    protected float getGravityVelocity() {
        return 0.04f;
    }
}
