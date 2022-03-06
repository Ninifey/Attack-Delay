// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.EnumPlantType;
import lotr.common.LOTRMod;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.IGrowable;
import net.minecraftforge.common.IPlantable;
import net.minecraft.block.Block;

public class LOTRBlockBerryBush extends Block implements IPlantable, IGrowable
{
    public LOTRBlockBerryBush() {
        super(Material.plants);
        this.setTickRandomly(true);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.4f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        final int berryType = getBerryType(j);
        final BushType type = BushType.forMeta(berryType);
        if (hasBerries(j)) {
            return type.iconGrown;
        }
        return type.iconBare;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        for (final BushType type : BushType.values()) {
            type.iconBare = iconregister.registerIcon(this.getTextureName() + "_" + type.bushName + "_bare");
            type.iconGrown = iconregister.registerIcon(this.getTextureName() + "_" + type.bushName);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (final BushType type : BushType.values()) {
            final int meta = type.bushMeta;
            list.add(new ItemStack(item, 1, setHasBerries(meta, true)));
            list.add(new ItemStack(item, 1, setHasBerries(meta, false)));
        }
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public static boolean hasBerries(final int meta) {
        return (meta & 0x8) != 0x0;
    }
    
    public static int getBerryType(final int meta) {
        return meta & 0x7;
    }
    
    public static int setHasBerries(final int meta, final boolean flag) {
        if (flag) {
            return getBerryType(meta) | 0x8;
        }
        return getBerryType(meta);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (!world.isClient && !hasBerries(meta)) {
            final float growth = this.getGrowthFactor(world, i, j, k);
            if (random.nextFloat() < growth) {
                this.growBerries(world, i, j, k);
            }
        }
    }
    
    private void growBerries(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        world.setBlockMetadata(i, j, k, setHasBerries(meta, true), 3);
    }
    
    private float getGrowthFactor(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        if (below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)this) && world.getBlockLightValue(i, j + 1, k) >= 9) {
            float growth = 1.0f;
            boolean bushAdjacent = false;
            final boolean bushAdjacentCorner = false;
        Label_0132:
            for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    if (i2 != i || k2 != k) {
                        if (world.getBlock(i2, j, k2) instanceof LOTRBlockBerryBush) {
                            bushAdjacent = true;
                            break Label_0132;
                        }
                    }
                }
            }
            for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    float growthBonus = 0.0f;
                    if (world.getBlock(i2, j - 1, k2).canSustainPlant((IBlockAccess)world, i2, j - 1, k2, ForgeDirection.UP, (IPlantable)this)) {
                        growthBonus = 1.0f;
                        if (world.getBlock(i2, j - 1, k2).isFertile(world, i2, j - 1, k2)) {
                            growthBonus = 3.0f;
                        }
                    }
                    if (i2 != i || k2 != k) {
                        growthBonus /= 4.0f;
                    }
                    growth += growthBonus;
                }
            }
            if (growth > 0.0f) {
                if (bushAdjacent) {
                    growth /= 2.0f;
                }
                if (world.isRaining()) {
                    growth *= 3.0f;
                }
                return growth / 150.0f;
            }
        }
        if (below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            float growth = world.getBlockLightValue(i, j + 1, k) / 2000.0f;
            if (world.isRaining()) {
                growth *= 3.0f;
            }
            return growth;
        }
        return 0.0f;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (hasBerries(meta)) {
            world.setBlockMetadata(i, j, k, setHasBerries(meta, false), 3);
            if (!world.isClient) {
                final List<ItemStack> drops = this.getBerryDrops(world, i, j, k, meta);
                for (final ItemStack berry : drops) {
                    this.dropBlockAsItem_do(world, i, j, k, berry);
                }
            }
            return true;
        }
        return false;
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(new ItemStack((Block)this, 1, setHasBerries(meta, false)));
        drops.addAll(this.getBerryDrops(world, i, j, k, meta));
        return drops;
    }
    
    private ArrayList<ItemStack> getBerryDrops(final World world, final int i, final int j, final int k, final int meta) {
        final ArrayList drops = new ArrayList();
        if (hasBerries(meta)) {
            final int berryType = getBerryType(meta);
            Item berry = null;
            final int berries = 1 + world.rand.nextInt(4);
            switch (berryType) {
                case 0: {
                    berry = LOTRMod.blueberry;
                    break;
                }
                case 1: {
                    berry = LOTRMod.blackberry;
                    break;
                }
                case 2: {
                    berry = LOTRMod.raspberry;
                    break;
                }
                case 3: {
                    berry = LOTRMod.cranberry;
                    break;
                }
                case 4: {
                    berry = LOTRMod.elderberry;
                    break;
                }
                case 5: {
                    berry = LOTRMod.wildberry;
                    break;
                }
            }
            if (berry != null) {
                for (int l = 0; l < berries; ++l) {
                    drops.add(new ItemStack(berry));
                }
            }
        }
        return (ArrayList<ItemStack>)drops;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
    
    public Block getPlant(final IBlockAccess world, final int i, final int j, final int k) {
        return this;
    }
    
    public int getPlantMetadata(final IBlockAccess world, final int i, final int j, final int k) {
        return world.getBlockMetadata(i, j, k);
    }
    
    public boolean func_149851_a(final World world, final int i, final int j, final int k, final boolean isRemote) {
        return !hasBerries(world.getBlockMetadata(i, j, k));
    }
    
    public boolean func_149852_a(final World world, final Random random, final int i, final int j, final int k) {
        return true;
    }
    
    public void func_149853_b(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(3) == 0) {
            this.growBerries(world, i, j, k);
        }
    }
    
    public enum BushType
    {
        BLUEBERRY(0, "blueberry", false), 
        BLACKBERRY(1, "blackberry", false), 
        RASPBERRY(2, "raspberry", false), 
        CRANBERRY(3, "cranberry", false), 
        ELDERBERRY(4, "elderberry", false), 
        WILDBERRY(5, "wildberry", true);
        
        public final int bushMeta;
        public final String bushName;
        public final boolean poisonous;
        @SideOnly(Side.CLIENT)
        public IIcon iconBare;
        @SideOnly(Side.CLIENT)
        public IIcon iconGrown;
        
        private BushType(final int i, final String s, final boolean flag) {
            this.bushMeta = i;
            this.bushName = s;
            this.poisonous = flag;
        }
        
        public static BushType randomType(final Random rand) {
            return values()[rand.nextInt(values().length)];
        }
        
        public static BushType forMeta(final int i) {
            for (final BushType type : values()) {
                if (type.bushMeta == i) {
                    return type;
                }
            }
            return values()[0];
        }
    }
}
