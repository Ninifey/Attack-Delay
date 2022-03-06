// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRPlayerQuestData
{
    private LOTRPlayerData playerData;
    private boolean givenFirstPouches;
    
    public LOTRPlayerQuestData(final LOTRPlayerData pd) {
        this.givenFirstPouches = false;
        this.playerData = pd;
    }
    
    public void save(final NBTTagCompound questData) {
        questData.setBoolean("Pouches", this.givenFirstPouches);
    }
    
    public void load(final NBTTagCompound questData) {
        this.givenFirstPouches = questData.getBoolean("Pouches");
    }
    
    public void onUpdate(final EntityPlayerMP entityplayer, final WorldServer world) {
    }
    
    private void markDirty() {
        this.playerData.markDirty();
    }
    
    public boolean getGivenFirstPouches() {
        return this.givenFirstPouches;
    }
    
    public void setGivenFirstPouches(final boolean flag) {
        this.givenFirstPouches = flag;
        this.markDirty();
    }
}
