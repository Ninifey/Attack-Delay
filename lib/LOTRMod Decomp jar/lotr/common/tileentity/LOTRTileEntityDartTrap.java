// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.item.ItemStack;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.item.LOTRItemDart;
import net.minecraft.tileentity.TileEntityDispenser;

public class LOTRTileEntityDartTrap extends TileEntityDispenser
{
    private int fireCooldown;
    
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? super.field_146020_a : "container.lotr.dartTrap";
    }
    
    public void updateEntity() {
        super.updateEntity();
        if (!((TileEntity)this).worldObj.isClient) {
            if (this.fireCooldown > 0) {
                --this.fireCooldown;
            }
            else {
                final int slot = this.func_146017_i();
                if (slot >= 0) {
                    final ItemStack itemstack = this.getStackInSlot(slot);
                    if (itemstack.getItem() instanceof LOTRItemDart) {
                        final AxisAlignedBB range = this.getTriggerRange();
                        final List entities = ((TileEntity)this).worldObj.selectEntitiesWithinAABB((Class)EntityLivingBase.class, range, LOTRMod.selectLivingExceptCreativePlayers());
                        if (!entities.isEmpty()) {
                            final IBehaviorDispenseItem dispense = (IBehaviorDispenseItem)BlockDispenser.dispenseBehaviorRegistry.getObject((Object)itemstack.getItem());
                            final ItemStack result = dispense.dispense((IBlockSource)new BlockSourceImpl(((TileEntity)this).worldObj, ((TileEntity)this).xCoord, ((TileEntity)this).yCoord, ((TileEntity)this).zCoord), itemstack);
                            this.setInventorySlotContents(slot, (result.stackSize == 0) ? null : result);
                            this.fireCooldown = 20;
                        }
                    }
                }
            }
        }
    }
    
    public AxisAlignedBB getTriggerRange() {
        final BlockSourceImpl blocksource = new BlockSourceImpl(((TileEntity)this).worldObj, ((TileEntity)this).xCoord, ((TileEntity)this).yCoord, ((TileEntity)this).zCoord);
        final EnumFacing facing = BlockDispenser.func_149937_b(this.getBlockMetadata());
        final float front = 0.55f;
        final float range = 16.0f;
        final Vec3 vecPos = Vec3.createVectorHelper(((TileEntity)this).xCoord + 0.5, ((TileEntity)this).yCoord + 0.5, ((TileEntity)this).zCoord + 0.5);
        final Vec3 vecFront = vecPos.addVector((double)(facing.getFrontOffsetX() * front), (double)(facing.getFrontOffsetY() * front), (double)(facing.getFrontOffsetZ() * front));
        Vec3 vecTarget = vecPos.addVector((double)(facing.getFrontOffsetX() * range), (double)(facing.getFrontOffsetY() * range), (double)(facing.getFrontOffsetZ() * range));
        final MovingObjectPosition hitBlock = ((TileEntity)this).worldObj.func_147447_a(vecFront, vecTarget, true, true, false);
        if (hitBlock != null) {
            vecTarget = Vec3.createVectorHelper(hitBlock.blockX + 0.5 - facing.getFrontOffsetX(), hitBlock.blockY + 0.5 - facing.getFrontOffsetY(), hitBlock.blockZ + 0.5 - facing.getFrontOffsetZ());
        }
        final float f = 0.0f;
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox((double)(((TileEntity)this).xCoord + f), (double)(((TileEntity)this).yCoord + f), (double)(((TileEntity)this).zCoord + f), (double)(((TileEntity)this).xCoord + 1 - f), (double)(((TileEntity)this).yCoord + 1 - f), (double)(((TileEntity)this).zCoord + 1 - f));
        bb = bb.addCoord(vecTarget.xCoord - vecPos.xCoord, vecTarget.yCoord - vecPos.yCoord, vecTarget.zCoord - vecPos.zCoord);
        return bb;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return this.getTriggerRange();
    }
}
