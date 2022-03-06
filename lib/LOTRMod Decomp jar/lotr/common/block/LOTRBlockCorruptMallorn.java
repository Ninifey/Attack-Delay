// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.tileentity.LOTRTileEntityCorruptMallorn;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRBlockCorruptMallorn extends LOTRBlockFlower
{
    public static int ENT_KILLS;
    
    public LOTRBlockCorruptMallorn() {
        final float f = 0.4f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setLightLevel(0.625f);
    }
    
    public boolean hasTileEntity(final int meta) {
        return true;
    }
    
    public TileEntity createTileEntity(final World world, final int meta) {
        return new LOTRTileEntityCorruptMallorn();
    }
    
    public static void summonEntBoss(final World world, final int i, final int j, final int k) {
        world.setBlockToAir(i, j, k);
        final LOTREntityMallornEnt ent = new LOTREntityMallornEnt(world);
        ent.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
        ent.onSpawnWithEgg(null);
        world.spawnEntityInWorld((Entity)ent);
        ent.sendEntBossSpeech("summon");
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isClient) {
            super.updateTick(world, i, j, k, random);
        }
    }
    
    public ArrayList getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList drops = new ArrayList();
        drops.add(new ItemStack(LOTRMod.sapling, 1, 1));
        return drops;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        for (int l = 0; l < 2; ++l) {
            final double d = i + 0.25 + random.nextFloat() * 0.5f;
            final double d2 = j + 0.5;
            final double d3 = k + 0.25 + random.nextFloat() * 0.5f;
            final double d4 = -0.05 + random.nextFloat() * 0.1;
            final double d5 = 0.1 + random.nextFloat() * 0.1;
            final double d6 = -0.05 + random.nextFloat() * 0.1;
            LOTRMod.proxy.spawnParticle("morgulPortal", d, d2, d3, d4, d5, d6);
        }
    }
    
    @Override
    public int getRenderType() {
        return 1;
    }
    
    static {
        LOTRBlockCorruptMallorn.ENT_KILLS = 3;
    }
}
