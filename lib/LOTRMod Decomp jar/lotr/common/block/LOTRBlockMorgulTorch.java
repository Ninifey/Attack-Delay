// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;

public class LOTRBlockMorgulTorch extends LOTRBlockTorch
{
    @Override
    public TorchParticle createTorchParticle(final Random random) {
        final double d3 = -0.05 + random.nextFloat() * 0.1;
        final double d4 = 0.1 + random.nextFloat() * 0.1;
        final double d5 = -0.05 + random.nextFloat() * 0.1;
        return new TorchParticle("morgulPortal", 0.0, 0.0, 0.0, d3, d4, d5);
    }
}
