// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.item.LOTRItemRing;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.enchantment.EnchantmentHelper;
import lotr.common.LOTRReflection;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityFishHook;

public class LOTREntityFishHook extends EntityFishHook
{
    public LOTREntityFishHook(final World world) {
        super(world);
    }
    
    public LOTREntityFishHook(final World world, final EntityPlayer entityplayer) {
        super(world, entityplayer);
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    public int getPlayerID() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(16);
    }
    
    public void setPlayerID(final int id) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)id);
    }
    
    public void onUpdate() {
        if (!((Entity)this).worldObj.isClient) {
            if (super.field_146042_b == null) {
                this.setDead();
                return;
            }
        }
        else if (super.field_146042_b == null) {
            final int playerID = this.getPlayerID();
            final Entity player = ((Entity)this).worldObj.getEntityByID(playerID);
            if (!(player instanceof EntityPlayer)) {
                this.setDead();
                return;
            }
            final EntityPlayer entityplayer = (EntityPlayer)player;
            entityplayer.fishEntity = this;
            super.field_146042_b = entityplayer;
        }
        super.onUpdate();
    }
    
    public int func_146034_e() {
        if (((Entity)this).worldObj.isClient) {
            return 0;
        }
        int damage = 0;
        if (super.field_146043_c != null) {
            final double d0 = ((Entity)super.field_146042_b).posX - ((Entity)this).posX;
            final double d2 = ((Entity)super.field_146042_b).posY - ((Entity)this).posY;
            final double d3 = ((Entity)super.field_146042_b).posZ - ((Entity)this).posZ;
            final double dist = MathHelper.sqrt_double(d0 * d0 + d2 * d2 + d3 * d3);
            final double motion = 0.1;
            final Entity field_146043_c = super.field_146043_c;
            field_146043_c.motionX += d0 * motion;
            final Entity field_146043_c2 = super.field_146043_c;
            field_146043_c2.motionY += d2 * motion + MathHelper.sqrt_double(dist) * 0.08;
            final Entity field_146043_c3 = super.field_146043_c;
            field_146043_c3.motionZ += d3 * motion;
            damage = 3;
        }
        else if (LOTRReflection.getFishHookBobTime(this) > 0) {
            final float chance = ((Entity)this).worldObj.rand.nextFloat();
            final int luck = EnchantmentHelper.func_151386_g((EntityLivingBase)super.field_146042_b);
            final int speed = EnchantmentHelper.func_151387_h((EntityLivingBase)super.field_146042_b);
            final LOTRFishing.FishResult result = LOTRFishing.getFishResult(((Entity)this).rand, chance, luck, speed, true);
            final ItemStack item = result.fishedItem;
            final EntityItem entityitem = new EntityItem(((Entity)this).worldObj, ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, item);
            final double d4 = ((Entity)super.field_146042_b).posX - ((Entity)this).posX;
            final double d5 = ((Entity)super.field_146042_b).posY - ((Entity)this).posY;
            final double d6 = ((Entity)super.field_146042_b).posZ - ((Entity)this).posZ;
            final double dist2 = MathHelper.sqrt_double(d4 * d4 + d5 * d5 + d6 * d6);
            final double motion2 = 0.1;
            ((Entity)entityitem).motionX = d4 * motion2;
            ((Entity)entityitem).motionY = d5 * motion2 + MathHelper.sqrt_double(dist2) * 0.08;
            ((Entity)entityitem).motionZ = d6 * motion2;
            ((Entity)this).worldObj.spawnEntityInWorld((Entity)entityitem);
            ((Entity)this).worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(((Entity)super.field_146042_b).worldObj, ((Entity)super.field_146042_b).posX, ((Entity)super.field_146042_b).posY + 0.5, ((Entity)super.field_146042_b).posZ + 0.5, ((Entity)this).rand.nextInt(6) + 1));
            super.field_146042_b.addStat(result.category.stat, 1);
            if (item.getItem() instanceof LOTRItemRing) {
                LOTRLevelData.getData(super.field_146042_b).addAchievement(LOTRAchievement.fishRing);
            }
            damage = 1;
        }
        if (LOTRReflection.isFishHookInGround(this)) {
            damage = 2;
        }
        this.setDead();
        super.field_146042_b.fishEntity = null;
        return damage;
    }
}
