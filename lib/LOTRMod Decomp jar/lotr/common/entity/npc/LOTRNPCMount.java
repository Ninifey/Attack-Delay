// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;

public interface LOTRNPCMount
{
    boolean isMountSaddled();
    
    boolean getBelongsToNPC();
    
    void setBelongsToNPC(final boolean p0);
    
    void super_moveEntityWithHeading(final float p0, final float p1);
    
    String getMountArmorTexture();
    
    boolean isMountArmorValid(final ItemStack p0);
}
