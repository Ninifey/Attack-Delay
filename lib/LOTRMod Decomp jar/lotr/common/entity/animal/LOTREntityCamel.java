// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.block.BlockColored;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRReflection;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;

public class LOTREntityCamel extends LOTREntityHorse implements LOTRBiomeGenNearHarad.ImmuneToHeat
{
    public LOTREntityCamel(final World world) {
        super(world);
        this.setSize(1.6f, 1.8f);
    }
    
    public int getHorseType() {
        return ((Entity)this).worldObj.isClient ? 0 : 1;
    }
    
    @Override
    protected void onLOTRHorseSpawn() {
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        jumpStrength *= 0.5;
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
    }
    
    @Override
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 12.0, 36.0);
    }
    
    @Override
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.1, 0.6);
    }
    
    @Override
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.1, 0.35);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.wheat;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && ((Entity)this).riddenByEntity instanceof EntityPlayer && this.isMountSaddled()) {
            LOTRLevelData.getData((EntityPlayer)((Entity)this).riddenByEntity).addAchievement(LOTRAchievement.rideCamel);
        }
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int hides = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < hides; ++l) {
            this.func_145779_a(Items.leather, 1);
        }
        for (int meat = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), j = 0; j < meat; ++j) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.camelCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.camelRaw, 1);
            }
        }
    }
    
    public boolean func_110259_cr() {
        return true;
    }
    
    @Override
    public boolean isMountArmorValid(final ItemStack itemstack) {
        return (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet)) || super.isMountArmorValid(itemstack);
    }
    
    public boolean isCamelWearingCarpet() {
        final ItemStack itemstack = this.getMountArmor();
        return itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet);
    }
    
    public int getCamelCarpetColor() {
        final ItemStack itemstack = this.getMountArmor();
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet)) {
            final int meta = itemstack.getItemDamage();
            int dyeMeta = BlockColored.func_150031_c(meta);
            final int[] colors = ItemDye.field_150922_c;
            dyeMeta = MathHelper.clamp_int(dyeMeta, 0, colors.length);
            return colors[dyeMeta];
        }
        return -1;
    }
    
    public void setNomadChestAndCarpet() {
        this.setChestedForWorldGen();
        final ItemStack carpet = new ItemStack(Blocks.carpet, 1, ((Entity)this).rand.nextInt(16));
        this.setMountArmor(carpet);
    }
}
