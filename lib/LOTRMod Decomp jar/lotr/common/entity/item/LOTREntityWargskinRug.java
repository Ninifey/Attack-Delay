// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.world.World;

public class LOTREntityWargskinRug extends LOTREntityRugBase
{
    public LOTREntityWargskinRug(final World world) {
        super(world);
        this.setSize(1.8f, 0.3f);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        super.dataWatcher.addObject(18, (Object)0);
    }
    
    public LOTREntityWarg.WargType getRugType() {
        final int i = super.dataWatcher.getWatchableObjectByte(18);
        return LOTREntityWarg.WargType.forID(i);
    }
    
    public void setRugType(final LOTREntityWarg.WargType w) {
        super.dataWatcher.updateObject(18, (Object)(byte)w.wargID);
    }
    
    @Override
    protected String getRugNoise() {
        return "lotr:warg.say";
    }
    
    @Override
    protected ItemStack getRugItem() {
        return new ItemStack(LOTRMod.wargskinRug, 1, this.getRugType().wargID);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("RugType", (byte)this.getRugType().wargID);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setRugType(LOTREntityWarg.WargType.forID(nbt.getByte("RugType")));
    }
}
