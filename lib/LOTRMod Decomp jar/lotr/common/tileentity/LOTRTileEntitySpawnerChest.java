// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import net.minecraft.tileentity.TileEntityChest;

public class LOTRTileEntitySpawnerChest extends TileEntityChest
{
    private String entityClassName;
    
    public LOTRTileEntitySpawnerChest() {
        this.entityClassName = "";
    }
    
    public void setMobID(final Class entityClass) {
        this.entityClassName = LOTREntities.getStringFromClass(entityClass);
    }
    
    public Entity createMob() {
        return EntityList.createEntityByName(this.entityClassName, ((TileEntity)this).worldObj);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.entityClassName = nbt.getString("MobID");
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("MobID", this.entityClassName);
    }
}
