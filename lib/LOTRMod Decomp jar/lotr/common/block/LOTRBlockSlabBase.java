// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemSlab;
import net.minecraft.world.World;
import net.minecraft.entity.EnumCreatureType;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;

public abstract class LOTRBlockSlabBase extends BlockSlab
{
    public Block singleSlab;
    public Block doubleSlab;
    private int subtypes;
    
    public LOTRBlockSlabBase(final boolean flag, final Material material, final int n) {
        super(flag, material);
        this.subtypes = n;
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        ((Block)this).field_149783_u = true;
        if (material == Material.wood) {
            this.setHardness(2.0f);
            this.setResistance(5.0f);
            this.setStepSound(Block.soundTypeWood);
        }
    }
    
    public static void registerSlabs(final Block block, final Block block1) {
        ((LOTRBlockSlabBase)block).singleSlab = block;
        ((LOTRBlockSlabBase)block).doubleSlab = block1;
        ((LOTRBlockSlabBase)block1).singleSlab = block;
        ((LOTRBlockSlabBase)block1).doubleSlab = block1;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(this.singleSlab);
    }
    
    protected ItemStack createStackedBlock(final int i) {
        return new ItemStack(this.singleSlab, 2, i & 0x7);
    }
    
    public String func_150002_b(final int i) {
        return super.getUnlocalizedName() + "." + i;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int i, final int j, final int k, final int l) {
        if (this == this.doubleSlab) {
            return super.shouldSideBeRendered(world, i, j, k, l);
        }
        if (l != 1 && l != 0 && !super.shouldSideBeRendered(world, i, j, k, l)) {
            return false;
        }
        final int i2 = i + Facing.offsetsXForSide[Facing.oppositeSide[l]];
        final int j2 = j + Facing.offsetsYForSide[Facing.oppositeSide[l]];
        final int k2 = k + Facing.offsetsZForSide[Facing.oppositeSide[l]];
        final boolean flag = (world.getBlockMetadata(i2, j2, k2) & 0x8) != 0x0;
        return flag ? (l == 0 || (l == 1 && super.shouldSideBeRendered(world, i, j, k, l)) || (world.getBlock(i, j, k) != this.singleSlab || (world.getBlockMetadata(i, j, k) & 0x8) == 0x0)) : (l == 1 || (l == 0 && super.shouldSideBeRendered(world, i, j, k, l)) || (world.getBlock(i, j, k) != this.singleSlab || (world.getBlockMetadata(i, j, k) & 0x8) != 0x0));
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        if (item != Item.getItemFromBlock(this.doubleSlab)) {
            for (int j = 0; j < this.subtypes; ++j) {
                list.add(new ItemStack(item, 1, j));
            }
        }
    }
    
    public boolean canCreatureSpawn(final EnumCreatureType type, final IBlockAccess world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        return (meta & 0x8) == 0x8 || this.isOpaqueCube();
    }
    
    public boolean isBlockSolid(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        return ((world.getBlockMetadata(x, y, z) & 0x8) == 0x8 && side == 1) || this.isOpaqueCube();
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return Item.getItemFromBlock(this.singleSlab);
    }
    
    public static class SlabItems
    {
        public static class WoodSlab1Single extends ItemSlab
        {
            public WoodSlab1Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle, (BlockSlab)LOTRMod.woodSlabDouble, false);
            }
        }
        
        public static class WoodSlab1Double extends ItemSlab
        {
            public WoodSlab1Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle, (BlockSlab)LOTRMod.woodSlabDouble, true);
            }
        }
        
        public static class WoodSlab2Single extends ItemSlab
        {
            public WoodSlab2Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle2, (BlockSlab)LOTRMod.woodSlabDouble2, false);
            }
        }
        
        public static class WoodSlab2Double extends ItemSlab
        {
            public WoodSlab2Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle2, (BlockSlab)LOTRMod.woodSlabDouble2, true);
            }
        }
        
        public static class WoodSlab3Single extends ItemSlab
        {
            public WoodSlab3Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle3, (BlockSlab)LOTRMod.woodSlabDouble3, false);
            }
        }
        
        public static class WoodSlab3Double extends ItemSlab
        {
            public WoodSlab3Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle3, (BlockSlab)LOTRMod.woodSlabDouble3, true);
            }
        }
        
        public static class WoodSlab4Single extends ItemSlab
        {
            public WoodSlab4Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle4, (BlockSlab)LOTRMod.woodSlabDouble4, false);
            }
        }
        
        public static class WoodSlab4Double extends ItemSlab
        {
            public WoodSlab4Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle4, (BlockSlab)LOTRMod.woodSlabDouble4, true);
            }
        }
        
        public static class WoodSlab5Single extends ItemSlab
        {
            public WoodSlab5Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle5, (BlockSlab)LOTRMod.woodSlabDouble5, false);
            }
        }
        
        public static class WoodSlab5Double extends ItemSlab
        {
            public WoodSlab5Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.woodSlabSingle5, (BlockSlab)LOTRMod.woodSlabDouble5, true);
            }
        }
        
        public static class RottenSlabSingle extends ItemSlab
        {
            public RottenSlabSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.rottenSlabSingle, (BlockSlab)LOTRMod.rottenSlabDouble, false);
            }
        }
        
        public static class RottenSlabDouble extends ItemSlab
        {
            public RottenSlabDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.rottenSlabSingle, (BlockSlab)LOTRMod.rottenSlabDouble, true);
            }
        }
        
        public static class Slab1Single extends ItemSlab
        {
            public Slab1Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle, (BlockSlab)LOTRMod.slabDouble, false);
            }
        }
        
        public static class Slab1Double extends ItemSlab
        {
            public Slab1Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle, (BlockSlab)LOTRMod.slabDouble, true);
            }
        }
        
        public static class Slab2Single extends ItemSlab
        {
            public Slab2Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle2, (BlockSlab)LOTRMod.slabDouble2, false);
            }
        }
        
        public static class Slab2Double extends ItemSlab
        {
            public Slab2Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle2, (BlockSlab)LOTRMod.slabDouble2, true);
            }
        }
        
        public static class Slab3Single extends ItemSlab
        {
            public Slab3Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle3, (BlockSlab)LOTRMod.slabDouble3, false);
            }
        }
        
        public static class Slab3Double extends ItemSlab
        {
            public Slab3Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle3, (BlockSlab)LOTRMod.slabDouble3, true);
            }
        }
        
        public static class Slab4Single extends ItemSlab
        {
            public Slab4Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle4, (BlockSlab)LOTRMod.slabDouble4, false);
            }
        }
        
        public static class Slab4Double extends ItemSlab
        {
            public Slab4Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle4, (BlockSlab)LOTRMod.slabDouble4, true);
            }
        }
        
        public static class Slab5Single extends ItemSlab
        {
            public Slab5Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle5, (BlockSlab)LOTRMod.slabDouble5, false);
            }
        }
        
        public static class Slab5Double extends ItemSlab
        {
            public Slab5Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle5, (BlockSlab)LOTRMod.slabDouble5, true);
            }
        }
        
        public static class Slab6Single extends ItemSlab
        {
            public Slab6Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle6, (BlockSlab)LOTRMod.slabDouble6, false);
            }
        }
        
        public static class Slab6Double extends ItemSlab
        {
            public Slab6Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle6, (BlockSlab)LOTRMod.slabDouble6, true);
            }
        }
        
        public static class Slab7Single extends ItemSlab
        {
            public Slab7Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle7, (BlockSlab)LOTRMod.slabDouble7, false);
            }
        }
        
        public static class Slab7Double extends ItemSlab
        {
            public Slab7Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle7, (BlockSlab)LOTRMod.slabDouble7, true);
            }
        }
        
        public static class Slab8Single extends ItemSlab
        {
            public Slab8Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle8, (BlockSlab)LOTRMod.slabDouble8, false);
            }
        }
        
        public static class Slab8Double extends ItemSlab
        {
            public Slab8Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle8, (BlockSlab)LOTRMod.slabDouble8, true);
            }
        }
        
        public static class Slab9Single extends ItemSlab
        {
            public Slab9Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle9, (BlockSlab)LOTRMod.slabDouble9, false);
            }
        }
        
        public static class Slab9Double extends ItemSlab
        {
            public Slab9Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle9, (BlockSlab)LOTRMod.slabDouble9, true);
            }
        }
        
        public static class Slab10Single extends ItemSlab
        {
            public Slab10Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle10, (BlockSlab)LOTRMod.slabDouble10, false);
            }
        }
        
        public static class Slab10Double extends ItemSlab
        {
            public Slab10Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle10, (BlockSlab)LOTRMod.slabDouble10, true);
            }
        }
        
        public static class Slab11Single extends ItemSlab
        {
            public Slab11Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle11, (BlockSlab)LOTRMod.slabDouble11, false);
            }
        }
        
        public static class Slab11Double extends ItemSlab
        {
            public Slab11Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle11, (BlockSlab)LOTRMod.slabDouble11, true);
            }
        }
        
        public static class Slab12Single extends ItemSlab
        {
            public Slab12Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle12, (BlockSlab)LOTRMod.slabDouble12, false);
            }
        }
        
        public static class Slab12Double extends ItemSlab
        {
            public Slab12Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle12, (BlockSlab)LOTRMod.slabDouble12, true);
            }
        }
        
        public static class Slab13Single extends ItemSlab
        {
            public Slab13Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle13, (BlockSlab)LOTRMod.slabDouble13, false);
            }
        }
        
        public static class Slab13Double extends ItemSlab
        {
            public Slab13Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle13, (BlockSlab)LOTRMod.slabDouble13, true);
            }
        }
        
        public static class Slab14Single extends ItemSlab
        {
            public Slab14Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle14, (BlockSlab)LOTRMod.slabDouble14, false);
            }
        }
        
        public static class Slab14Double extends ItemSlab
        {
            public Slab14Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingle14, (BlockSlab)LOTRMod.slabDouble14, true);
            }
        }
        
        public static class SlabVSingle extends ItemSlab
        {
            public SlabVSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleV, (BlockSlab)LOTRMod.slabDoubleV, false);
            }
        }
        
        public static class SlabVDouble extends ItemSlab
        {
            public SlabVDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleV, (BlockSlab)LOTRMod.slabDoubleV, true);
            }
        }
        
        public static class UtumnoSingle extends ItemSlab
        {
            public UtumnoSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabUtumnoSingle, (BlockSlab)LOTRMod.slabUtumnoDouble, false);
            }
        }
        
        public static class UtumnoDouble extends ItemSlab
        {
            public UtumnoDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabUtumnoSingle, (BlockSlab)LOTRMod.slabUtumnoDouble, true);
            }
        }
        
        public static class Utumno2Single extends ItemSlab
        {
            public Utumno2Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabUtumnoSingle2, (BlockSlab)LOTRMod.slabUtumnoDouble2, false);
            }
        }
        
        public static class Utumno2Double extends ItemSlab
        {
            public Utumno2Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabUtumnoSingle2, (BlockSlab)LOTRMod.slabUtumnoDouble2, true);
            }
        }
        
        public static class ScorchedSingle extends ItemSlab
        {
            public ScorchedSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.scorchedSlabSingle, (BlockSlab)LOTRMod.scorchedSlabDouble, false);
            }
        }
        
        public static class ScorchedDouble extends ItemSlab
        {
            public ScorchedDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.scorchedSlabSingle, (BlockSlab)LOTRMod.scorchedSlabDouble, true);
            }
        }
        
        public static class ClayTileSingle extends ItemSlab
        {
            public ClayTileSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabClayTileSingle, (BlockSlab)LOTRMod.slabClayTileDouble, false);
            }
        }
        
        public static class ClayTileDouble extends ItemSlab
        {
            public ClayTileDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabClayTileSingle, (BlockSlab)LOTRMod.slabClayTileDouble, true);
            }
        }
        
        public static class ClayTileDyedSingle extends ItemSlab
        {
            public ClayTileDyedSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabClayTileDyedSingle, (BlockSlab)LOTRMod.slabClayTileDyedDouble, false);
            }
        }
        
        public static class ClayTileDyedDouble extends ItemSlab
        {
            public ClayTileDyedDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabClayTileDyedSingle, (BlockSlab)LOTRMod.slabClayTileDyedDouble, true);
            }
        }
        
        public static class ClayTileDyed2Single extends ItemSlab
        {
            public ClayTileDyed2Single(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabClayTileDyedSingle2, (BlockSlab)LOTRMod.slabClayTileDyedDouble2, false);
            }
        }
        
        public static class ClayTileDyed2Double extends ItemSlab
        {
            public ClayTileDyed2Double(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabClayTileDyedSingle2, (BlockSlab)LOTRMod.slabClayTileDyedDouble2, true);
            }
        }
        
        public static class ThatchSingle extends ItemSlab
        {
            public ThatchSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleThatch, (BlockSlab)LOTRMod.slabDoubleThatch, false);
            }
        }
        
        public static class ThatchDouble extends ItemSlab
        {
            public ThatchDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleThatch, (BlockSlab)LOTRMod.slabDoubleThatch, true);
            }
        }
        
        public static class DirtSingle extends ItemSlab
        {
            public DirtSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleDirt, (BlockSlab)LOTRMod.slabDoubleDirt, false);
            }
        }
        
        public static class DirtDouble extends ItemSlab
        {
            public DirtDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleDirt, (BlockSlab)LOTRMod.slabDoubleDirt, true);
            }
        }
        
        public static class SandSingle extends ItemSlab
        {
            public SandSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleSand, (BlockSlab)LOTRMod.slabDoubleSand, false);
            }
        }
        
        public static class SandDouble extends ItemSlab
        {
            public SandDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleSand, (BlockSlab)LOTRMod.slabDoubleSand, true);
            }
        }
        
        public static class GravelSingle extends ItemSlab
        {
            public GravelSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleGravel, (BlockSlab)LOTRMod.slabDoubleGravel, false);
            }
        }
        
        public static class GravelDouble extends ItemSlab
        {
            public GravelDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabSingleGravel, (BlockSlab)LOTRMod.slabDoubleGravel, true);
            }
        }
        
        public static class BoneSingle extends ItemSlab
        {
            public BoneSingle(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabBoneSingle, (BlockSlab)LOTRMod.slabBoneDouble, false);
            }
        }
        
        public static class BoneDouble extends ItemSlab
        {
            public BoneDouble(final Block block) {
                super(block, (BlockSlab)LOTRMod.slabBoneSingle, (BlockSlab)LOTRMod.slabBoneDouble, true);
            }
        }
    }
}
