// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.projectile.EntityArrow;

public class LOTREntityArrowPoisoned extends EntityArrow implements IEntityAdditionalSpawnData
{
    public LOTREntityArrowPoisoned(final World world) {
        super(world);
    }
    
    public LOTREntityArrowPoisoned(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    public LOTREntityArrowPoisoned(final World world, final EntityLivingBase shooter, final EntityLivingBase target, final float charge, final float inaccuracy) {
        super(world, shooter, target, charge, inaccuracy);
    }
    
    public LOTREntityArrowPoisoned(final World world, final EntityLivingBase shooter, final float charge) {
        super(world, shooter, charge);
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeDouble(((Entity)this).motionX);
        data.writeDouble(((Entity)this).motionY);
        data.writeDouble(((Entity)this).motionZ);
        data.writeInt((super.shootingEntity == null) ? -1 : super.shootingEntity.getEntityId());
    }
    
    public void readSpawnData(final ByteBuf data) {
        ((Entity)this).motionX = data.readDouble();
        ((Entity)this).motionY = data.readDouble();
        ((Entity)this).motionZ = data.readDouble();
        final int id = data.readInt();
        if (id >= 0) {
            final Entity entity = ((Entity)this).worldObj.getEntityByID(id);
            if (entity != null) {
                super.shootingEntity = entity;
            }
        }
    }
    
    public void onCollideWithPlayer(final EntityPlayer entityplayer) {
        final NBTTagCompound nbt = new NBTTagCompound();
        this.writeEntityToNBT(nbt);
        final boolean isInGround = nbt.getByte("inGround") == 1;
        if (!((Entity)this).worldObj.isClient && isInGround && super.arrowShake <= 0) {
            boolean pickup = super.canBePickedUp == 1 || (super.canBePickedUp == 2 && entityplayer.capabilities.isCreativeMode);
            if (super.canBePickedUp == 1 && !entityplayer.inventory.addItemStackToInventory(new ItemStack(LOTRMod.arrowPoisoned, 1))) {
                pickup = false;
            }
            if (pickup) {
                this.playSound("random.pop", 0.2f, ((((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityplayer.onItemPickup((Entity)this, 1);
                this.setDead();
            }
        }
    }
}
