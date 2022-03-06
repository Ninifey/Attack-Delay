// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockBush;

public class LOTRBlockFlower extends BlockBush
{
    public LOTRBlockFlower() {
        this(Material.plants);
    }
    
    public LOTRBlockFlower(final Material material) {
        super(material);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    public Block setFlowerBounds(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return (Block)this;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getFlowerRenderID();
    }
}
