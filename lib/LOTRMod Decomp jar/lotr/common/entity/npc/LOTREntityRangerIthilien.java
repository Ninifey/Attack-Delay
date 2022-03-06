// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenIthilien;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import lotr.common.LOTRCapes;
import net.minecraft.world.World;

public class LOTREntityRangerIthilien extends LOTREntityRanger
{
    public LOTREntityRangerIthilien(final World world) {
        super(world);
        super.npcCape = LOTRCapes.RANGER_ITHILIEN;
    }
    
    @Override
    protected LOTRFoods getDunedainFoods() {
        return LOTRFoods.GONDOR;
    }
    
    @Override
    protected LOTRFoods getDunedainDrinks() {
        return LOTRFoods.GONDOR_DRINK;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(4);
        if (i == 0 || i == 1 || i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerGondor));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        }
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.gondorBow));
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRangerIthilien));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRangerIthilien));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRangerIthilien));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRangerIthilien));
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GONDOR;
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = super.getBlockPathWeight(i, j, k);
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenIthilien) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    protected void dropDunedainItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.GONDOR_HOUSE, 1, 2 + i);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killRangerIthilien;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "gondor/ranger/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "gondor/ranger/hired";
        }
        return "gondor/ranger/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GONDOR.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GONDOR;
    }
}
