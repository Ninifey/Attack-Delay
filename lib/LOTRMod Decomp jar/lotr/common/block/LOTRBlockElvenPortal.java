// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import java.util.Random;
import lotr.common.tileentity.LOTRTileEntityElvenPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lotr.common.world.LOTRTeleporterElvenPortal;
import lotr.common.fac.LOTRFaction;

public class LOTRBlockElvenPortal extends LOTRBlockPortal
{
    public LOTRBlockElvenPortal() {
        super(new LOTRFaction[] { LOTRFaction.GALADHRIM, LOTRFaction.HIGH_ELF }, LOTRTeleporterElvenPortal.class);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityElvenPortal();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(3) == 0) {
            final double d = i + random.nextFloat();
            final double d2 = j + 0.8;
            final double d3 = k + random.nextFloat();
            LOTRMod.proxy.spawnParticle("elvenGlow", d, d2, d3, 0.0, 0.3, 0.0);
        }
        super.randomDisplayTick(world, i, j, k, random);
    }
    
    @Override
    public void setPlayerInPortal(final EntityPlayer entityplayer) {
        LOTRMod.proxy.setInElvenPortal(entityplayer);
        if (!((Entity)entityplayer).worldObj.isClient) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useElvenPortal);
        }
    }
    
    @Override
    public boolean isValidPortalLocation(final World world, final int i, final int j, final int k, final boolean portalAlreadyMade) {
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                if (Math.abs(i2 - i) != 2 || Math.abs(k2 - k) != 2) {
                    if (Math.abs(i2 - i) == 2 || Math.abs(k2 - k) == 2) {
                        if (world.getBlock(i2, j, k2) != LOTRMod.quenditeGrass) {
                            return false;
                        }
                    }
                    else if (world.getBlock(i2, j, k2) != (portalAlreadyMade ? LOTRMod.elvenPortal : Blocks.water) || !LOTRMod.isOpaque(world, i2, j - 1, k2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
