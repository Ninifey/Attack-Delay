// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockTorch;

public abstract class LOTRBlockTorch extends BlockTorch
{
    public LOTRBlockTorch() {
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k);
        final double d = i + 0.5;
        final double d2 = j + 0.7;
        final double d3 = k + 0.5;
        final double particleY = 0.2;
        final double particleX = 0.27;
        final TorchParticle particle = this.createTorchParticle(random);
        if (particle != null) {
            if (meta == 1) {
                particle.spawn(d - particleX, d2 + particleY, d3);
            }
            else if (meta == 2) {
                particle.spawn(d + particleX, d2 + particleY, d3);
            }
            else if (meta == 3) {
                particle.spawn(d, d2 + particleY, d3 - particleX);
            }
            else if (meta == 4) {
                particle.spawn(d, d2 + particleY, d3 + particleX);
            }
            else {
                particle.spawn(d, d2, d3);
            }
        }
    }
    
    public abstract TorchParticle createTorchParticle(final Random p0);
    
    public class TorchParticle
    {
        public String name;
        public double posX;
        public double posY;
        public double posZ;
        public double motionX;
        public double motionY;
        public double motionZ;
        
        public TorchParticle(final String s, final double x, final double y, final double z, final double mx, final double my, final double mz) {
            this.name = s;
            this.posX = x;
            this.posY = y;
            this.posZ = z;
            this.motionX = mx;
            this.motionY = my;
            this.motionZ = mz;
        }
        
        public void spawn(final double x, final double y, final double z) {
            LOTRMod.proxy.spawnParticle(this.name, x + this.posX, y + this.posY, z + this.posZ, this.motionX, this.motionY, this.motionZ);
        }
    }
}
