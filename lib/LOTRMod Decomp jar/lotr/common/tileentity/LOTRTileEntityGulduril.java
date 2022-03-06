// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityGulduril extends TileEntity
{
    public int ticksExisted;
    
    public void setWorldObj(final World world) {
        super.setWorldObj(world);
        this.ticksExisted = world.rand.nextInt(200);
    }
    
    public void updateEntity() {
        ++this.ticksExisted;
    }
}
