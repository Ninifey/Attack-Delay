// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityWeaponRack extends TileEntity
{
    private ItemStack weaponItem;
    private EntityLivingBase rackEntity;
    
    public boolean canAcceptItem(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
                return true;
            }
            if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
                return true;
            }
            if (item instanceof ItemHoe) {
                return true;
            }
            if (item instanceof ItemFishingRod) {
                return true;
            }
        }
        return false;
    }
    
    public ItemStack getWeaponItem() {
        return this.weaponItem;
    }
    
    public void setWeaponItem(ItemStack item) {
        if (item != null && item.stackSize <= 0) {
            item = null;
        }
        this.weaponItem = item;
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public EntityLivingBase getEntityForRender() {
        if (this.rackEntity == null) {
            this.rackEntity = (EntityLivingBase)new EntityLiving(super.worldObj) {};
        }
        return this.rackEntity;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("HasWeapon", this.weaponItem != null);
        if (this.weaponItem != null) {
            nbt.setTag("WeaponItem", (NBTBase)this.weaponItem.writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        final boolean hasWeapon = nbt.getBoolean("HasWeapon");
        if (hasWeapon) {
            this.weaponItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WeaponItem"));
        }
        else {
            this.weaponItem = null;
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
    }
}
