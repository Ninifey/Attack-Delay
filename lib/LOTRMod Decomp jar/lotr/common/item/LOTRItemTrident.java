// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraftforge.common.FishingHooks;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.item.EntityItem;
import lotr.common.entity.projectile.LOTRFishing;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class LOTRItemTrident extends LOTRItemPolearm
{
    public LOTRItemTrident(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemTrident(final Item.ToolMaterial material) {
        super(material);
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final int useTick) {
        final int usageTime = this.getMaxItemUseDuration(itemstack) - useTick;
        if (usageTime <= 5) {
            return;
        }
        entityplayer.swingItem();
        final MovingObjectPosition m = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);
        if (m != null && m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int i = m.blockX;
            final int j = m.blockY;
            final int k = m.blockZ;
            if (this.canFishAt(world, i, j, k)) {
                for (int l = 0; l < 20; ++l) {
                    final double d = i + world.rand.nextFloat();
                    final double d2 = j + world.rand.nextFloat();
                    final double d3 = k + world.rand.nextFloat();
                    final String s = world.rand.nextBoolean() ? "bubble" : "splash";
                    world.spawnParticle(s, d, d2, d3, 0.0, (double)(world.rand.nextFloat() * 0.2f), 0.0);
                }
                if (!world.isClient) {
                    entityplayer.addExhaustion(0.06f);
                    if (world.rand.nextInt(3) == 0) {
                        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.splash", 0.5f, 1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4f);
                        itemstack.damageItem(1, (EntityLivingBase)entityplayer);
                        if (world.rand.nextInt(3) == 0) {
                            final float chance = world.rand.nextFloat();
                            final int luck = EnchantmentHelper.func_151386_g((EntityLivingBase)entityplayer);
                            final int speed = EnchantmentHelper.func_151387_h((EntityLivingBase)entityplayer);
                            final LOTRFishing.FishResult result = LOTRFishing.getFishResult(world.rand, chance, luck, speed, false);
                            final EntityItem fish = new EntityItem(world, i + 0.5, j + 0.5, k + 0.5, result.fishedItem);
                            final double d4 = ((Entity)entityplayer).posX - ((Entity)fish).posX;
                            final double d5 = ((Entity)entityplayer).posY - ((Entity)fish).posY;
                            final double d6 = ((Entity)entityplayer).posZ - ((Entity)fish).posZ;
                            final double dist = MathHelper.sqrt_double(d4 * d4 + d5 * d5 + d6 * d6);
                            final double motion = 0.1;
                            ((Entity)fish).motionX = d4 * motion;
                            ((Entity)fish).motionY = d5 * motion + MathHelper.sqrt_double(dist) * 0.08;
                            ((Entity)fish).motionZ = d6 * motion;
                            world.spawnEntityInWorld((Entity)fish);
                            entityplayer.addStat(result.category.stat, 1);
                            world.spawnEntityInWorld((Entity)new EntityXPOrb(world, ((Entity)fish).posX, ((Entity)fish).posY, ((Entity)fish).posZ, world.rand.nextInt(3) + 1));
                            if (result.category == FishingHooks.FishableCategory.FISH) {
                                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDunlendingTrident);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean canFishAt(final World world, final int i, final int j, final int k) {
        final double d = i + 0.5;
        final double d2 = j + 0.5;
        final double d3 = k + 0.5;
        final double d4 = 0.125;
        final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(d - d4, d2 - d4, d3 - d4, d + d4, d2 + d4, d3 + d4);
        final byte range = 5;
        for (int l = 0; l < range; ++l) {
            final double d5 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (l + 0) / range - d4 + d4;
            final double d6 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (l + 1) / range - d4 + d4;
            final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(boundingBox.minX, d5, boundingBox.minZ, boundingBox.maxX, d6, boundingBox.maxZ);
            if (world.isAABBInMaterial(aabb, Material.water)) {
                return true;
            }
        }
        return false;
    }
}
