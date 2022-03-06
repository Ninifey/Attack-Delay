// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockMarshLights extends Block
{
    public LOTRBlockMarshLights() {
        super(Material.circuits);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean isCollidable() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return this.canBlockStay(world, i, j, k);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).getMaterial() == Material.water;
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            world.setBlock(i, j, k, Blocks.air, 0, 2);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(3) > 0) {
            if (random.nextInt(3) == 0) {
                LOTRMod.proxy.spawnParticle("marshFlame", i + random.nextFloat(), j - 0.5, k + random.nextFloat(), 0.0, 0.05f + random.nextFloat() * 0.1f, 0.0);
            }
            else {
                LOTRMod.proxy.spawnParticle("marshLight", i + random.nextFloat(), j - 0.5, k + random.nextFloat(), 0.0, 0.05f + random.nextFloat() * 0.1f, 0.0);
            }
        }
    }
}
