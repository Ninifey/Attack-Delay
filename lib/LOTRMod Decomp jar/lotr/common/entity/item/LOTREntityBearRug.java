// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.World;

public class LOTREntityBearRug extends LOTREntityRugBase
{
    public LOTREntityBearRug(final World world) {
        super(world);
        this.setSize(1.8f, 0.3f);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        super.dataWatcher.addObject(18, (Object)0);
    }
    
    public LOTREntityBear.BearType getRugType() {
        final int i = super.dataWatcher.getWatchableObjectByte(18);
        return LOTREntityBear.BearType.forID(i);
    }
    
    public void setRugType(final LOTREntityBear.BearType t) {
        super.dataWatcher.updateObject(18, (Object)(byte)t.bearID);
    }
    
    @Override
    protected String getRugNoise() {
        return "lotr:bear.say";
    }
    
    @Override
    protected ItemStack getRugItem() {
        return new ItemStack(LOTRMod.bearRug, 1, this.getRugType().bearID);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("RugType", (byte)this.getRugType().bearID);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setRugType(LOTREntityBear.BearType.forID(nbt.getByte("RugType")));
    }
}
