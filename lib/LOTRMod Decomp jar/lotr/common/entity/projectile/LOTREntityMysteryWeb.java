// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.inventory.IInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityMysteryWeb extends EntityThrowable
{
    public LOTREntityMysteryWeb(final World world) {
        super(world);
    }
    
    public LOTREntityMysteryWeb(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
    }
    
    public LOTREntityMysteryWeb(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (this.getThrower() != null && m.entityHit == this.getThrower()) {
            return;
        }
        if (!((Entity)this).worldObj.isClient) {
            boolean spawnedSpider = false;
            if (((Entity)this).rand.nextInt(4) == 0) {
                final LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(((Entity)this).worldObj);
                spider.setSpiderScale(0);
                spider.liftSpawnRestrictions = true;
                for (int i = -2; i <= -2 && !spawnedSpider; ++i) {
                    for (int j = 0; j <= 3 && !spawnedSpider; ++j) {
                        for (int k = -2; k <= -2 && !spawnedSpider; ++k) {
                            spider.setLocationAndAngles(((Entity)this).posX + i / 2.0, ((Entity)this).posY + j / 3.0, ((Entity)this).posZ + k / 2.0, ((Entity)this).rand.nextFloat() * 360.0f, 0.0f);
                            if (spider.getCanSpawnHere()) {
                                spider.liftSpawnRestrictions = false;
                                spider.onSpawnWithEgg(null);
                                ((Entity)this).worldObj.spawnEntityInWorld((Entity)spider);
                                if (this.getThrower() != null) {
                                    spider.setAttackTarget(this.getThrower());
                                }
                                spawnedSpider = true;
                            }
                        }
                    }
                }
            }
            if (!spawnedSpider) {
                final IInventory tempInventory = (IInventory)new InventoryBasic("mysteryWeb", true, 1);
                LOTRChestContents.fillInventory(tempInventory, ((Entity)this).rand, LOTRChestContents.MIRKWOOD_LOOT, 1);
                ItemStack item = tempInventory.getStackInSlot(0);
                if (((Entity)this).rand.nextInt(500) == 0) {
                    item = new ItemStack(Items.melon, 64);
                }
                if (item != null) {
                    final EntityItem entityitem = new EntityItem(((Entity)this).worldObj, ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, item);
                    entityitem.delayBeforeCanPickup = 10;
                    ((Entity)this).worldObj.spawnEntityInWorld((Entity)entityitem);
                }
            }
            this.playSound("random.pop", 0.2f, ((((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            this.setDead();
        }
    }
    
    protected float func_70182_d() {
        return 0.5f;
    }
    
    protected float getGravityVelocity() {
        return 0.01f;
    }
}
