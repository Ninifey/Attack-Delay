// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;

public class LOTRBlockElvenTorch extends LOTRBlockTorch
{
    @Override
    public TorchParticle createTorchParticle(final Random random) {
        if (random.nextInt(3) == 0) {
            return new TorchParticle("elvenGlow", 0.0, -0.1, 0.0, 0.0, 0.0, 0.0);
        }
        return null;
    }
}
