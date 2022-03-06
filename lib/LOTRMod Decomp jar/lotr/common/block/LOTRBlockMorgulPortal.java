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
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lotr.common.world.LOTRTeleporterMorgulPortal;
import lotr.common.fac.LOTRFaction;

public class LOTRBlockMorgulPortal extends LOTRBlockPortal
{
    public LOTRBlockMorgulPortal() {
        super(new LOTRFaction[] { LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR }, LOTRTeleporterMorgulPortal.class);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityMorgulPortal();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        final double d = i + random.nextFloat();
        final double d2 = j + 0.8f;
        final double d3 = k + random.nextFloat();
        final double d4 = -0.05 + random.nextFloat() * 0.1;
        final double d5 = 0.1 + random.nextFloat() * 0.1;
        final double d6 = -0.05 + random.nextFloat() * 0.1;
        LOTRMod.proxy.spawnParticle("morgulPortal", d, d2, d3, d4, d5, d6);
    }
    
    @Override
    public void setPlayerInPortal(final EntityPlayer entityplayer) {
        LOTRMod.proxy.setInMorgulPortal(entityplayer);
        if (!((Entity)entityplayer).worldObj.isClient) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMorgulPortal);
        }
    }
    
    @Override
    public boolean isValidPortalLocation(final World world, final int i, final int j, final int k, final boolean portalAlreadyMade) {
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                if (Math.abs(i2 - i) == 2 && Math.abs(k2 - k) == 2) {
                    for (int j2 = j + 1; j2 <= j + 3; ++j2) {
                        if (world.getBlock(i2, j2, k2) != LOTRMod.guldurilBrick) {
                            return false;
                        }
                    }
                }
                else if (Math.abs(i2 - i) == 2 || Math.abs(k2 - k) == 2) {
                    if (!LOTRMod.isOpaque(world, i2, j, k2)) {
                        return false;
                    }
                }
                else if (world.getBlock(i2, j, k2) != (portalAlreadyMade ? LOTRMod.morgulPortal : Blocks.lava) || !LOTRMod.isOpaque(world, i2, j - 1, k2)) {
                    return false;
                }
            }
        }
        return true;
    }
}
