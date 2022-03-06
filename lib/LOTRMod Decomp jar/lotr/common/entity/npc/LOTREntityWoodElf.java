// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenWoodlandRealm;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemMug;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRFoods;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetWoodElf;
import net.minecraft.world.World;

public class LOTREntityWoodElf extends LOTREntityElf
{
    public LOTREntityWoodElf(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(2, super.rangedAttackAI);
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetWoodElf.class);
    }
    
    @Override
    protected LOTRFoods getElfDrinks() {
        return LOTRFoods.WOOD_ELF_DRINK;
    }
    
    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return this.createElfRangedAttackAI();
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 30, 50, 16.0f);
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getSindarinName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
        super.npcItemsInv.setMeleeWeapon(super.npcItemsInv.getRangedWeapon());
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.WOOD_ELF;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killWoodElf;
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
                final ItemStack elfDrink = new ItemStack(LOTRMod.mugRedWine);
                elfDrink.setItemDamage(1 + ((Entity)this).rand.nextInt(3));
                LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(((Entity)this).rand), true);
                this.entityDropItem(elfDrink, 0.0f);
            }
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.WOOD_ELF_HOUSE, 1, 1 + i);
        }
    }
    
    @Override
    public boolean canElfSpawnHere() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == Blocks.grass;
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenWoodlandRealm) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "woodElf/elf/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "woodElf/elf/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= getWoodlandTrustLevel()) {
            return "woodElf/elf/friendly";
        }
        return "woodElf/elf/neutral";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.WOOD_ELF.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.WOOD_ELF;
    }
    
    public static float getWoodlandTrustLevel() {
        return LOTRFaction.WOOD_ELF.getFirstRank().alignment;
    }
}
