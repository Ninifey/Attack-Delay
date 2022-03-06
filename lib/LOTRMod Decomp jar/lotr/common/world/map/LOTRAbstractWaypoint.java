// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface LOTRAbstractWaypoint
{
    int getX();
    
    int getY();
    
    int getXCoord();
    
    int getZCoord();
    
    int getYCoord(final World p0, final int p1, final int p2);
    
    int getYCoordDisplay();
    
    String getCodeName();
    
    String getDisplayName();
    
    String getLoreText(final EntityPlayer p0);
    
    boolean hasPlayerUnlocked(final EntityPlayer p0);
    
    boolean isUnlockable(final EntityPlayer p0);
    
    boolean isHidden();
    
    int getID();
}
