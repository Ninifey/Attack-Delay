// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemBlock;

public class LOTRItemPlantableBlock extends ItemBlock implements IPlantable
{
    private IPlantable plantableBlock;
    
    public LOTRItemPlantableBlock(final Block block) {
        super(block);
        this.plantableBlock = (IPlantable)block;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return this.plantableBlock.getPlantType(world, i, j, k);
    }
    
    public Block getPlant(final IBlockAccess world, final int i, final int j, final int k) {
        return this.plantableBlock.getPlant(world, i, j, k);
    }
    
    public int getPlantMetadata(final IBlockAccess world, final int i, final int j, final int k) {
        return this.plantableBlock.getPlantMetadata(world, i, j, k);
    }
}
