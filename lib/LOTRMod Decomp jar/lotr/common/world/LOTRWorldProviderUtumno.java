// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.NetworkRegistry;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBlockFX;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import java.awt.Color;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import lotr.common.LOTRDimension;

public class LOTRWorldProviderUtumno extends LOTRWorldProvider
{
    public LOTRWorldProviderUtumno() {
        super.hasNoSky = true;
    }
    
    @Override
    public LOTRDimension getLOTRDimension() {
        return LOTRDimension.UTUMNO;
    }
    
    public IChunkProvider createChunkGenerator() {
        return (IChunkProvider)new LOTRChunkProviderUtumno(super.worldObj, super.worldObj.getSeed());
    }
    
    protected void generateLightBrightnessTable() {
        for (int i = 0; i <= 15; ++i) {
            final float f = i / 15.0f;
            super.lightBrightnessTable[i] = (float)Math.pow(f, 4.0);
        }
    }
    
    @Override
    public float calculateCelestialAngle(final long time, final float f) {
        return 0.5f;
    }
    
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
        return 1.0;
    }
    
    @Override
    public float[] handleFinalFogColors(final EntityLivingBase viewer, final double tick, float[] rgb) {
        final double y = ((Entity)viewer).prevPosY + (((Entity)viewer).posY - ((Entity)viewer).prevPosY) * tick;
        final LOTRUtumnoLevel level = LOTRUtumnoLevel.forY((int)y);
        if (level == LOTRUtumnoLevel.FIRE) {
            final int min = level.getLowestCorridorFloor();
            int max = level.getHighestCorridorRoof();
            max -= (int)((max - min) * 0.3);
            double yFactor = (y - min) / (max - min);
            yFactor = MathHelper.clamp_double(yFactor, 0.0, 1.0);
            yFactor = 1.0 - yFactor;
            final Color clr = new Color(rgb[0], rgb[1], rgb[2]);
            final float[] hsb = Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), null);
            float br = hsb[2];
            if (br > 0.0) {
                br += (float)((1.0f - br) * yFactor);
                hsb[2] = br;
            }
            rgb = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])).getRGBComponents(null);
        }
        return rgb;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    public int getRespawnDimension(final EntityPlayerMP entityplayer) {
        return LOTRDimension.MIDDLE_EARTH.dimensionID;
    }
    
    public boolean isSurfaceWorld() {
        return false;
    }
    
    public int getActualHeight() {
        return this.getHeight();
    }
    
    public void setSpawnPoint(final int i, final int j, final int k) {
    }
    
    public static void doEvaporateFX(final World world, final int i, final int j, final int k) {
        if (!world.isClient) {
            world.playSoundEffect((double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            final LOTRPacketBlockFX pkt = new LOTRPacketBlockFX(LOTRPacketBlockFX.Type.UTUMNO_EVAPORATE, i, j, k);
            LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)pkt, new NetworkRegistry.TargetPoint(world.provider.dimensionId, i + 0.5, j + 0.5, k + 0.5, 32.0));
        }
    }
    
    public interface UtumnoBlock
    {
    }
}
