// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import lotr.common.item.LOTRItemMug;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public abstract class LOTREntityHighElfBase extends LOTREntityElf
{
    public LOTREntityHighElfBase(final World world) {
        super(world);
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HIGH_ELF;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected void dropElfItems(final boolean flag, final int i) {
        super.dropElfItems(flag, i);
        if (flag) {
            int dropChance = 20 - i * 4;
            dropChance = Math.max(dropChance, 1);
            if (((Entity)this).rand.nextInt(dropChance) == 0) {
                final ItemStack elfDrink = new ItemStack(LOTRMod.mugMiruvor);
                elfDrink.setItemDamage(1 + ((Entity)this).rand.nextInt(3));
                LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(((Entity)this).rand), true);
                this.entityDropItem(elfDrink, 0.0f);
            }
        }
    }
    
    @Override
    public boolean canElfSpawnHere() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == Blocks.grass;
    }
}
