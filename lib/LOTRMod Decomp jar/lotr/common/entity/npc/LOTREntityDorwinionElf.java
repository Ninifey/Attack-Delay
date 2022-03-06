// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemMug;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;

public class LOTREntityDorwinionElf extends LOTREntityElf
{
    public LOTREntityDorwinionElf(final World world) {
        super(world);
    }
    
    @Override
    protected LOTRFoods getElfDrinks() {
        return LOTRFoods.DORWINION_DRINK;
    }
    
    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return this.createElfMeleeAttackAI();
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getSindarinName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDorwinionElf));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(super.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(super.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            ((EntityLiving)this).tasks.removeTask(super.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(super.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(2, super.meleeAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DORWINION;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDorwinionElf;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected void dropElfItems(final boolean flag, final int i) {
        super.dropElfItems(flag, i);
        if (flag) {
            int dropChance = 20 - i * 4;
            dropChance = Math.max(dropChance, 1);
            if (((Entity)this).rand.nextInt(dropChance) == 0) {
                final ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(((Entity)this).rand);
                LOTRItemMug.setStrengthMeta(drink, 1 + ((Entity)this).rand.nextInt(3));
                this.entityDropItem(drink, 0.0f);
            }
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.DORWINION_HOUSE, 1, 1 + i);
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
        if (biome instanceof LOTRBiomeGenDorwinion) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "dorwinion/elf/friendly";
        }
        return "dorwinion/elf/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DORWINION_ELF.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DORWINION_ELF;
    }
}
