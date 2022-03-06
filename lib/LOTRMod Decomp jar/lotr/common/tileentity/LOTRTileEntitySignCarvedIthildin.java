// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRTileEntitySignCarvedIthildin extends LOTRTileEntitySignCarved
{
    private LOTRDwarvenGlowLogic glowLogic;
    
    public LOTRTileEntitySignCarvedIthildin() {
        this.glowLogic = new LOTRDwarvenGlowLogic().setPlayerRange(8);
    }
    
    public void updateEntity() {
        super.updateEntity();
        this.glowLogic.update(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
    }
    
    public float getGlowBrightness(final float f) {
        if (super.isFakeGuiSign) {
            return 1.0f;
        }
        return this.glowLogic.getGlowBrightness(super.worldObj, super.xCoord, super.yCoord, super.zCoord, f);
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 1024.0;
    }
}
