// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;

public class LOTRBlockMudFarmland extends BlockFarmland
{
    public LOTRBlockMudFarmland() {
        this.setHardness(0.6f);
        this.setStepSound(Block.soundTypeGravel);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return super.getIcon(i, j);
        }
        return LOTRMod.mud.getBlockTextureFromSide(i);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        super.updateTick(world, i, j, k, random);
        if (world.getBlock(i, j, k) == Blocks.dirt) {
            world.setBlock(i, j, k, LOTRMod.mud);
        }
    }
    
    public boolean canSustainPlant(final IBlockAccess world, final int i, final int j, final int k, final ForgeDirection direction, final IPlantable plantable) {
        return Blocks.farmland.canSustainPlant(world, i, j, k, direction, plantable);
    }
    
    public void onPlantGrow(final World world, final int i, final int j, final int k, final int sourceX, final int sourceY, final int sourceZ) {
        world.setBlock(i, j, k, LOTRMod.mud, 0, 2);
    }
    
    public boolean isFertile(final World world, final int i, final int j, final int k) {
        return Blocks.farmland.isFertile(world, i, j, k);
    }
    
    public void onFallenUpon(final World world, final int i, final int j, final int k, final Entity entity, final float f) {
        super.onFallenUpon(world, i, j, k, entity, f);
        if (world.getBlock(i, j, k) == Blocks.dirt) {
            world.setBlock(i, j, k, LOTRMod.mud);
        }
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        super.onNeighborBlockChange(world, i, j, k, block);
        if (world.getBlock(i, j, k) == Blocks.dirt) {
            world.setBlock(i, j, k, LOTRMod.mud);
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return LOTRMod.mud.getItemDropped(0, random, j);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return Item.getItemFromBlock(LOTRMod.mud);
    }
}
