// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemLionRug;
import net.minecraft.world.World;

public class LOTREntityLionRug extends LOTREntityRugBase
{
    public LOTREntityLionRug(final World world) {
        super(world);
        this.setSize(1.8f, 0.3f);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        super.dataWatcher.addObject(18, (Object)0);
    }
    
    public LOTRItemLionRug.LionRugType getRugType() {
        final int i = super.dataWatcher.getWatchableObjectByte(18);
        return LOTRItemLionRug.LionRugType.forID(i);
    }
    
    public void setRugType(final LOTRItemLionRug.LionRugType t) {
        super.dataWatcher.updateObject(18, (Object)(byte)t.lionID);
    }
    
    @Override
    protected String getRugNoise() {
        return "lotr:lion.say";
    }
    
    @Override
    protected ItemStack getRugItem() {
        return new ItemStack(LOTRMod.lionRug, 1, this.getRugType().lionID);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("RugType", (byte)this.getRugType().lionID);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setRugType(LOTRItemLionRug.LionRugType.forID(nbt.getByte("RugType")));
    }
}
