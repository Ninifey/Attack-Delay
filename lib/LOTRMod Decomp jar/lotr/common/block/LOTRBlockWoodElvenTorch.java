// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;

public class LOTRBlockWoodElvenTorch extends LOTRBlockTorch
{
    @Override
    public TorchParticle createTorchParticle(final Random random) {
        final String s = "leafRed_" + (20 + random.nextInt(30));
        final double x = -0.01 + random.nextFloat() * 0.02f;
        final double y = -0.01 + random.nextFloat() * 0.02f;
        final double z = -0.01 + random.nextFloat() * 0.02f;
        return new TorchParticle(s, 0.0, 0.0, 0.0, x, y, z);
    }
}
