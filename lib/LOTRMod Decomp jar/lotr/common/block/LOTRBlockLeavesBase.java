// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.BlockLeavesBase;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.LOTRDate;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import java.util.List;
import net.minecraft.block.BlockLeaves;

public class LOTRBlockLeavesBase extends BlockLeaves
{
    public static List allLeafBlocks;
    @SideOnly(Side.CLIENT)
    private IIcon[][] leafIcons;
    private String[] leafNames;
    private boolean[] seasonal;
    private String vanillaTextureName;
    
    public LOTRBlockLeavesBase() {
        this(false, null);
    }
    
    public LOTRBlockLeavesBase(final boolean vanilla, final String vname) {
        if (vanilla) {
            this.setcreativeTab(CreativeTabs.tabDecorations);
            this.vanillaTextureName = vname;
        }
        else {
            this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        }
        LOTRBlockLeavesBase.allLeafBlocks.add(this);
    }
    
    protected void setLeafNames(final String... s) {
        this.leafNames = s;
        this.setSeasonal(new boolean[s.length]);
    }
    
    public String[] func_150125_e() {
        return this.leafNames;
    }
    
    public String[] getAllLeafNames() {
        return this.leafNames;
    }
    
    protected void setSeasonal(final boolean... b) {
        if (b.length != this.leafNames.length) {
            throw new IllegalArgumentException("Leaf seasons length must match number of types");
        }
        this.seasonal = b;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int i) {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return 16777215;
    }
    
    protected static int getBiomeLeafColor(final IBlockAccess world, final int i, final int j, final int k) {
        int r = 0;
        int g = 0;
        int b = 0;
        int count = 0;
        for (int range = 1, i2 = -range; i2 <= range; ++i2) {
            for (int k2 = -range; k2 <= range; ++k2) {
                final int biomeColor = world.getBiomeGenForCoords(i + i2, k + k2).getBiomeFoliageColor(i + i2, j, k + k2);
                r += (biomeColor & 0xFF0000) >> 16;
                g += (biomeColor & 0xFF00) >> 8;
                b += (biomeColor & 0xFF);
                ++count;
            }
        }
        return (r / count & 0xFF) << 16 | (g / count & 0xFF) << 8 | (b / count & 0xFF);
    }
    
    protected boolean shouldOakUseBiomeColor() {
        final LOTRDate.Season season = LOTRDate.ShireReckoning.getSeason();
        return season == LOTRDate.Season.SPRING || season == LOTRDate.Season.SUMMER || !(LOTRMod.proxy.getClientWorld().provider instanceof LOTRWorldProvider);
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList drops = new ArrayList();
        final int saplingChanceBase = this.getSaplingChance(meta & 0x3);
        final int saplingChance = this.calcFortuneModifiedDropChance(saplingChanceBase, fortune);
        if (world.rand.nextInt(saplingChance) == 0) {
            drops.add(new ItemStack(this.getItemDropped(meta, world.rand, fortune), 1, this.damageDropped(meta)));
        }
        this.addSpecialLeafDrops(drops, world, i, j, k, meta, fortune);
        return (ArrayList<ItemStack>)drops;
    }
    
    protected int getSaplingChance(final int meta) {
        return 20;
    }
    
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
    }
    
    protected int calcFortuneModifiedDropChance(final int baseChance, final int fortune) {
        int chance = baseChance;
        if (fortune > 0) {
            chance -= 2 << fortune;
            chance = Math.max(chance, baseChance / 2);
            chance = Math.max(chance, 1);
        }
        return chance;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        int meta = j & 0x3;
        if (meta >= this.leafNames.length) {
            meta = 0;
        }
        return this.leafIcons[meta][!((BlockLeavesBase)this).field_150121_P];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.leafIcons = new IIcon[this.leafNames.length][2];
        for (int i = 0; i < this.leafNames.length; ++i) {
            final IIcon fancy = iconregister.registerIcon(this.getTextureName() + "_" + this.leafNames[i] + "_fancy");
            final IIcon fast = iconregister.registerIcon(this.getTextureName() + "_" + this.leafNames[i] + "_fast");
            this.leafIcons[i][0] = fancy;
            this.leafIcons[i][1] = fast;
        }
    }
    
    public String getTextureName() {
        if (this.vanillaTextureName != null) {
            return this.vanillaTextureName;
        }
        return super.getTextureName();
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < this.leafNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    public static void setAllGraphicsLevels(final boolean flag) {
        for (int i = 0; i < LOTRBlockLeavesBase.allLeafBlocks.size(); ++i) {
            LOTRBlockLeavesBase.allLeafBlocks.get(i).func_150122_b(flag);
        }
    }
    
    static {
        LOTRBlockLeavesBase.allLeafBlocks = new ArrayList();
    }
}
