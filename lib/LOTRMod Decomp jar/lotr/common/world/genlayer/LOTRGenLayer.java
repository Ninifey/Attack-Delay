// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;
import net.minecraft.world.gen.layer.GenLayer;

public abstract class LOTRGenLayer extends GenLayer
{
    protected LOTRGenLayer lotrParent;
    
    public LOTRGenLayer(final long l) {
        super(l);
    }
    
    public void initWorldGenSeed(final long l) {
        super.initWorldGenSeed(l);
        if (this.lotrParent != null) {
            this.lotrParent.initWorldGenSeed(l);
        }
    }
    
    public final int[] getInts(final int i, final int k, final int xSize, final int zSize) {
        throw new RuntimeException("Do not use this method!");
    }
    
    public abstract int[] getInts(final World p0, final int p1, final int p2, final int p3, final int p4);
}
