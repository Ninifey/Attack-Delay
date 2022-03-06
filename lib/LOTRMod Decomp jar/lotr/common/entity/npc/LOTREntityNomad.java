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
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;

public class LOTREntityNomad extends LOTREntityNearHaradrimBase implements LOTRBiomeGenNearHarad.ImmuneToHeat
{
    protected static int[] nomadTurbanColors;
    
    public LOTREntityNomad(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.NOMAD;
    }
    
    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.NOMAD_DRINK;
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getNomadName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
        super.npcItemsInv.setIdleItem(null);
        if (((Entity)this).rand.nextInt(4) == 0) {
            final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            final int robeColor = LOTREntityNomad.nomadTurbanColors[((Entity)this).rand.nextInt(LOTREntityNomad.nomadTurbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityCamel camel = new LOTREntityCamel(((Entity)this).worldObj);
        camel.setNomadChestAndCarpet();
        return camel;
    }
    
    @Override
    protected void dropHaradrimItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(5) == 0) {
            this.dropChestContents(LOTRChestContents.NOMAD_TENT, 1, 2 + i);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killNearHaradrim;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/nomad/nomad/friendly";
        }
        return "nearHarad/nomad/nomad/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.NOMAD.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.NOMAD;
    }
    
    static {
        LOTREntityNomad.nomadTurbanColors = new int[] { 15392448, 13550476, 10063441, 8354400, 8343622 };
    }
}
