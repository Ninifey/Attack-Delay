// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.init.Items;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockOre extends Block
{
    public LOTRBlockOre() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        if (this == LOTRMod.oreNaurite) {
            return LOTRMod.nauriteGem;
        }
        if (this == LOTRMod.oreQuendite) {
            return LOTRMod.quenditeCrystal;
        }
        if (this == LOTRMod.oreGlowstone) {
            return Items.glowstone_dust;
        }
        if (this == LOTRMod.oreGulduril) {
            return LOTRMod.guldurilCrystal;
        }
        if (this == LOTRMod.oreSulfur) {
            return LOTRMod.sulfur;
        }
        if (this == LOTRMod.oreSaltpeter) {
            return LOTRMod.saltpeter;
        }
        return Item.getItemFromBlock((Block)this);
    }
    
    public int quantityDropped(final Random random) {
        if (this == LOTRMod.oreNaurite) {
            return 1 + random.nextInt(2);
        }
        if (this == LOTRMod.oreGlowstone) {
            return 2 + random.nextInt(4);
        }
        if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter) {
            return 1 + random.nextInt(2);
        }
        return 1;
    }
    
    public int quantityDroppedWithBonus(final int i, final Random random) {
        if (i > 0 && Item.getItemFromBlock((Block)this) != this.getItemDropped(0, random, i)) {
            int factor = random.nextInt(i + 2) - 1;
            factor = Math.max(factor, 0);
            int drops = this.quantityDropped(random) * (factor + 1);
            if (this == LOTRMod.oreGlowstone) {
                drops = Math.min(drops, 8);
            }
            return drops;
        }
        return this.quantityDropped(random);
    }
    
    public void dropBlockAsItemWithChance(final World world, final int i, final int j, final int k, final int meta, final float f, final int fortune) {
        super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
        if (this.getItemDropped(meta, world.rand, fortune) != Item.getItemFromBlock((Block)this)) {
            int amountXp = 0;
            if (this == LOTRMod.oreNaurite) {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreQuendite) {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreGlowstone) {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreGulduril) {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter) {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            this.dropXpOnBlockBreak(world, i, j, k, amountXp);
        }
    }
    
    public void harvestBlock(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k, final int l) {
        super.harvestBlock(world, entityplayer, i, j, k, l);
        if (!world.isClient) {
            if (this == LOTRMod.oreMithril) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineMithril);
            }
            if (this == LOTRMod.oreQuendite) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineQuendite);
            }
            if (this == LOTRMod.oreGlowstone) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineGlowstone);
            }
            if (this == LOTRMod.oreNaurite) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineNaurite);
            }
            if (this == LOTRMod.oreGulduril) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineGulduril);
            }
        }
    }
}
