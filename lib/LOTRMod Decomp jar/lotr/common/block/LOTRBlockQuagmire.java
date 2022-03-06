// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockQuagmire extends Block
{
    public LOTRBlockQuagmire() {
        super(Material.ground);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        entity.setInWeb();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
}
