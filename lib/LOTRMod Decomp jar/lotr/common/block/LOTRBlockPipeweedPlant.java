// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockPipeweedPlant extends LOTRBlockFlower
{
    public LOTRBlockPipeweedPlant() {
        this.setFlowerBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(4) == 0) {
            final double d = i + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
            final double d2 = j + MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
            final double d3 = k + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
            world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
        }
    }
}
