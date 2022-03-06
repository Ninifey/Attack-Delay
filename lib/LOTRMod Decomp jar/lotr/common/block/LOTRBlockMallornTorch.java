// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.block.Block;

public class LOTRBlockMallornTorch extends LOTRBlockTorch
{
    private int torchColor;
    
    public LOTRBlockMallornTorch(final int color) {
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setLightLevel(0.875f);
        this.torchColor = color;
    }
    
    @Override
    public TorchParticle createTorchParticle(final Random random) {
        if (random.nextInt(3) == 0) {
            return new TorchParticle("elvenGlow_" + Integer.toHexString(this.torchColor), 0.0, -0.1, 0.0, 0.0, 0.0, 0.0);
        }
        return null;
    }
}
