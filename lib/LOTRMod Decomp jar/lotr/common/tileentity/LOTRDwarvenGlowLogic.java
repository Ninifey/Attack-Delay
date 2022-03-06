// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class LOTRDwarvenGlowLogic
{
    private static final float[] lightValueSqrts;
    private static final float minSunBrightness = 0.2f;
    private boolean playersNearby;
    private int glowTick;
    private int prevGlowTick;
    private int maxGlowTick;
    private int playerRange;
    private float fullGlow;
    
    public LOTRDwarvenGlowLogic() {
        this.maxGlowTick = 120;
        this.playerRange = 8;
        this.fullGlow = 0.7f;
    }
    
    public LOTRDwarvenGlowLogic setGlowTime(final int i) {
        this.maxGlowTick = i;
        return this;
    }
    
    public LOTRDwarvenGlowLogic setPlayerRange(final int i) {
        this.playerRange = i;
        return this;
    }
    
    public LOTRDwarvenGlowLogic setFullGlow(final float f) {
        this.fullGlow = f;
        return this;
    }
    
    public void update(final World world, final int i, final int j, final int k) {
        this.prevGlowTick = this.glowTick;
        if (world.isClient) {
            this.playersNearby = (world.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, (double)this.playerRange) != null);
            if (this.playersNearby && this.glowTick < this.maxGlowTick) {
                ++this.glowTick;
            }
            else if (!this.playersNearby && this.glowTick > 0) {
                --this.glowTick;
            }
        }
    }
    
    public float getGlowBrightness(final World world, final int i, final int j, final int k, final float tick) {
        float glow = (this.prevGlowTick + (this.glowTick - this.prevGlowTick) * tick) / this.maxGlowTick;
        glow *= this.fullGlow;
        final float sun = world.getSunBrightness(tick);
        final float sunNorml = (sun - 0.2f) / 0.8f;
        float night = 1.0f - sunNorml;
        night -= 0.5f;
        if (night < 0.0f) {
            night = 0.0f;
        }
        night *= 2.0f;
        final float skylight = LOTRDwarvenGlowLogic.lightValueSqrts[world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, i, j, k)];
        return glow * night * skylight;
    }
    
    public int getGlowTick() {
        return this.glowTick;
    }
    
    public void setGlowTick(final int i) {
        this.prevGlowTick = i;
        this.glowTick = i;
    }
    
    public void resetGlowTick() {
        final int n = 0;
        this.prevGlowTick = n;
        this.glowTick = n;
    }
    
    static {
        lightValueSqrts = new float[16];
        for (int i = 0; i <= 15; ++i) {
            LOTRDwarvenGlowLogic.lightValueSqrts[i] = MathHelper.sqrt_float(i / 15.0f);
        }
    }
}
