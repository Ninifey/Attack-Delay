// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemMug;
import lotr.common.LOTRFoods;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.world.World;

public class LOTREntityGaladhrimElf extends LOTREntityElf
{
    public LOTREntityGaladhrimElf(final World world) {
        super(world);
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorGaladhrim));
        return horse;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GALADHRIM;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killElf;
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
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.ELF_HOUSE, 1, 1 + i);
        }
    }
    
    @Override
    public boolean canElfSpawnHere() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        return j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == biome.topBlock;
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenLothlorien) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "galadhrim/elf/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "galadhrim/elf/hired";
        }
        return "galadhrim/elf/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GALADHRIM.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GALADHRIM;
    }
}
