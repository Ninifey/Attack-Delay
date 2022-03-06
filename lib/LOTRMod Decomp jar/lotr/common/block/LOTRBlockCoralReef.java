// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockCoralReef extends Block
{
    private IIcon[] plantIcons;
    private static final String[] plantNames;
    private static final Random iconRand;
    
    public LOTRBlockCoralReef() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.plantIcons = new IIcon[LOTRBlockCoralReef.plantNames.length];
        for (int i = 0; i < LOTRBlockCoralReef.plantNames.length; ++i) {
            this.plantIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockCoralReef.plantNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return super.getIcon(i, j);
    }
    
    public IIcon getRandomPlantIcon(final int i, final int j, final int k) {
        final int hash = i * 25799626 ^ k * 6879038 ^ j;
        LOTRBlockCoralReef.iconRand.setSeed(hash);
        LOTRBlockCoralReef.iconRand.setSeed(LOTRBlockCoralReef.iconRand.nextLong());
        return this.plantIcons[LOTRBlockCoralReef.iconRand.nextInt(this.plantIcons.length)];
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getCoralRenderID();
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return LOTRMod.coral;
    }
    
    public int quantityDropped(final Random random) {
        return 1 + random.nextInt(2);
    }
    
    public int quantityDroppedWithBonus(final int i, final Random random) {
        int drops = this.quantityDropped(random);
        if (i > 0) {
            int factor = random.nextInt(i + 2) - 1;
            factor = Math.max(factor, 0);
            drops *= factor + 1;
        }
        return drops;
    }
    
    public void dropBlockAsItemWithChance(final World world, final int i, final int j, final int k, final int meta, final float f, final int fortune) {
        super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
        final int amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
        this.dropXpOnBlockBreak(world, i, j, k, amountXp);
    }
    
    public void onEntityWalking(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob)) {
            entity.attackEntityFrom(DamageSource.cactus, 0.5f);
        }
    }
    
    static {
        plantNames = new String[] { "purple", "yellow", "blue", "red", "green" };
        iconRand = new Random();
    }
}
