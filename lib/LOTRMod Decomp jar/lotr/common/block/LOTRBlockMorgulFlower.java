// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import java.util.Random;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;

public class LOTRBlockMorgulFlower extends LOTRBlockFlower
{
    public LOTRBlockMorgulFlower() {
        final float f = 0.125f;
        this.setFlowerBounds(f, 0.0f, f, 1.0f - f, 0.8f, 1.0f - f);
        this.setTickRandomly(true);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return super.canBlockStay(world, i, j, k) || LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        super.updateTick(world, i, j, k, random);
        if (!world.isClient) {
            final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
            if (biome instanceof LOTRBiomeGenMorgulVale) {
                final double range = 5.0;
                final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).expand(range, range, range);
                final List entities = world.getEntitiesWithinAABB((Class)EntityLivingBase.class, aabb);
                for (final Object obj : entities) {
                    final EntityLivingBase entity = (EntityLivingBase)obj;
                    if (this.isEntityVulnerable(entity)) {
                        final int dur = 200;
                        entity.addPotionEffect(new PotionEffect(Potion.confusion.id, dur));
                    }
                }
            }
        }
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        super.onEntityCollidedWithBlock(world, i, j, k, entity);
        if (!world.isClient && entity instanceof EntityLivingBase) {
            final EntityLivingBase living = (EntityLivingBase)entity;
            if (this.isEntityVulnerable(living)) {
                final int dur = 100;
                living.addPotionEffect(new PotionEffect(Potion.poison.id, dur));
                living.addPotionEffect(new PotionEffect(Potion.blindness.id, dur * 2));
            }
        }
    }
    
    private boolean isEntityVulnerable(final EntityLivingBase entity) {
        if (LOTRMod.getNPCFaction((Entity)entity) == LOTRFaction.MORDOR) {
            return false;
        }
        if (!(entity instanceof EntityPlayer)) {
            return true;
        }
        final EntityPlayer entityplayer = (EntityPlayer)entity;
        if (entityplayer.capabilities.isCreativeMode) {
            return false;
        }
        final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR);
        final float max = 250.0f;
        if (alignment >= max) {
            return false;
        }
        if (alignment > 0.0f) {
            float f = alignment / max;
            f = 1.0f - f;
            return entity.getRNG().nextFloat() < f;
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(4) == 0) {
            final double d = i + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
            final double d2 = j + MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
            final double d3 = k + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
            if (random.nextBoolean()) {
                LOTRMod.proxy.spawnParticle("morgulWater", d, d2, d3, 0.0, 0.0, 0.0);
            }
            else {
                LOTRMod.proxy.spawnParticle("whiteSmoke", d, d2, d3, 0.0, 0.0, 0.0);
            }
        }
    }
}
