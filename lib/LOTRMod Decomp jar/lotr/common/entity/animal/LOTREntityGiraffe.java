// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRReflection;
import net.minecraft.world.World;

public class LOTREntityGiraffe extends LOTREntityHorse
{
    public LOTREntityGiraffe(final World world) {
        super(world);
        this.setSize(1.7f, 4.0f);
    }
    
    public int getHorseType() {
        return 0;
    }
    
    @Override
    protected void onLOTRHorseSpawn() {
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        jumpStrength *= 0.8;
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
    }
    
    @Override
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 12.0, 34.0);
    }
    
    @Override
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.2, 1.0);
    }
    
    @Override
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.35);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack != null && Block.getBlockFromItem(itemstack.getItem()) instanceof BlockLeavesBase;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && ((Entity)this).riddenByEntity instanceof EntityPlayer && this.isMountSaddled()) {
            final EntityPlayer entityplayer = (EntityPlayer)((Entity)this).riddenByEntity;
            final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).posZ));
            if (biome instanceof LOTRBiomeGenShire) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideGiraffeShire);
            }
        }
    }
    
    protected Item func_146068_u() {
        return Items.leather;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (flag) {
            int rugChance = 30 - i * 5;
            rugChance = Math.max(rugChance, 1);
            if (((Entity)this).rand.nextInt(rugChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.giraffeRug), 0.0f);
            }
        }
    }
}
