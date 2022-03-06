// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.entity.Entity;

public class LOTREntityStoneTroll extends Entity implements LOTRBannerProtectable
{
    private float trollHealth;
    public boolean placedByPlayer;
    private int entityAge;
    
    public LOTREntityStoneTroll(final World world) {
        super(world);
        this.trollHealth = 40.0f;
        this.setSize(1.6f, 3.2f);
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(16, (Object)0);
        super.dataWatcher.addObject(17, (Object)0);
    }
    
    public int getTrollOutfit() {
        return super.dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setTrollOutfit(final int i) {
        super.dataWatcher.updateObject(16, (Object)(byte)i);
    }
    
    public boolean hasTwoHeads() {
        return super.dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setHasTwoHeads(final boolean flag) {
        super.dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setFloat("TrollHealth", this.trollHealth);
        nbt.setByte("TrollOutfit", (byte)this.getTrollOutfit());
        nbt.setBoolean("PlacedByPlayer", this.placedByPlayer);
        nbt.setBoolean("TwoHeads", this.hasTwoHeads());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.trollHealth = nbt.getFloat("TrollHealth");
        this.setTrollOutfit(nbt.getByte("TrollOutfit"));
        this.placedByPlayer = nbt.getBoolean("PlacedByPlayer");
        this.setHasTwoHeads(nbt.getBoolean("TwoHeads"));
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return super.boundingBox;
    }
    
    public void onUpdate() {
        super.onUpdate();
        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;
        super.motionY -= 0.03999999910593033;
        this.func_145771_j(super.posX, (super.boundingBox.minY + super.boundingBox.maxY) / 2.0, super.posZ);
        this.moveEntity(super.motionX, super.motionY, super.motionZ);
        float f = 0.98f;
        if (super.onGround) {
            f = 0.58800006f;
            final Block i = super.worldObj.getBlock(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.boundingBox.minY) - 1, MathHelper.floor_double(super.posZ));
            if (i.getMaterial() != Material.air) {
                f = i.slipperiness * 0.98f;
            }
        }
        super.motionX *= f;
        super.motionY *= 0.98;
        super.motionZ *= f;
        if (super.onGround) {
            super.motionY *= -0.5;
        }
        if (!super.worldObj.isClient && !this.placedByPlayer) {
            ++this.entityAge;
            final EntityPlayer entityplayer = super.worldObj.getClosestPlayerToEntity((Entity)this, -1.0);
            if (entityplayer != null) {
                final double d = ((Entity)entityplayer).posX - super.posX;
                final double d2 = ((Entity)entityplayer).posY - super.posY;
                final double d3 = ((Entity)entityplayer).posZ - super.posZ;
                final double distanceSq = d * d + d2 * d2 + d3 * d3;
                if (distanceSq > 16384.0) {
                    this.setDead();
                }
                if (this.entityAge > 600 && super.rand.nextInt(800) == 0 && distanceSq > 1024.0) {
                    this.setDead();
                }
                else if (distanceSq < 1024.0) {
                    this.entityAge = 0;
                }
            }
        }
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        if (super.worldObj.isClient || super.isDead) {
            return false;
        }
        if (!this.placedByPlayer) {
            boolean drops = true;
            boolean dropStatue = false;
            if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)damagesource.getSourceOfDamage();
                if (entityplayer.capabilities.isCreativeMode) {
                    drops = false;
                    f = this.trollHealth;
                }
                else {
                    drops = true;
                    final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                    if (itemstack != null && itemstack.getItem() instanceof ItemPickaxe) {
                        dropStatue = true;
                        f = 1.0f + (float)entityplayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                    }
                    else {
                        dropStatue = false;
                        f = 1.0f;
                    }
                    if (itemstack != null) {
                        itemstack.damageItem(1, (EntityLivingBase)entityplayer);
                        if (itemstack.stackSize <= 0) {
                            entityplayer.destroyCurrentEquippedItem();
                        }
                    }
                }
            }
            this.trollHealth -= f;
            if (this.trollHealth <= 0.0f) {
                super.worldObj.playSoundAtEntity((Entity)this, Blocks.stone.stepSound.getDigResourcePath(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getFrequency() * 0.8f);
                super.worldObj.setEntityState((Entity)this, (byte)17);
                if (drops) {
                    if (dropStatue) {
                        if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
                            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getSourceOfDamage();
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getTrollStatue);
                        }
                        this.dropAsStatue();
                    }
                    else {
                        for (int stone = 6 + super.rand.nextInt(7), l = 0; l < stone; ++l) {
                            this.func_145779_a(Item.getItemFromBlock(Blocks.cobblestone), 1);
                        }
                    }
                }
                this.setDead();
            }
            else {
                super.worldObj.playSoundAtEntity((Entity)this, Blocks.stone.stepSound.getDigResourcePath(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getFrequency() * 0.5f);
                super.worldObj.setEntityState((Entity)this, (byte)16);
            }
            return true;
        }
        if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
            super.worldObj.playSoundAtEntity((Entity)this, Blocks.stone.stepSound.getDigResourcePath(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getFrequency() * 0.8f);
            super.worldObj.setEntityState((Entity)this, (byte)17);
            this.setDead();
            final EntityPlayer entityplayer2 = (EntityPlayer)damagesource.getSourceOfDamage();
            if (!entityplayer2.capabilities.isCreativeMode) {
                this.dropAsStatue();
            }
            return true;
        }
        return false;
    }
    
    private ItemStack getStatueItem() {
        final ItemStack itemstack = new ItemStack(LOTRMod.trollStatue);
        itemstack.setItemDamage(this.getTrollOutfit());
        itemstack.setTagCompound(new NBTTagCompound());
        itemstack.getTagCompound().setBoolean("TwoHeads", this.hasTwoHeads());
        return itemstack;
    }
    
    private void dropAsStatue() {
        this.entityDropItem(this.getStatueItem(), 0.0f);
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            for (int l = 0; l < 16; ++l) {
                super.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0", super.posX + (super.rand.nextDouble() - 0.5) * super.width, super.posY + super.rand.nextDouble() * super.height, super.posZ + (super.rand.nextDouble() - 0.5) * super.width, 0.0, 0.0, 0.0);
            }
        }
        else if (b == 17) {
            for (int l = 0; l < 64; ++l) {
                super.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0", super.posX + (super.rand.nextDouble() - 0.5) * super.width, super.posY + super.rand.nextDouble() * super.height, super.posZ + (super.rand.nextDouble() - 0.5) * super.width, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return this.getStatueItem();
    }
}
