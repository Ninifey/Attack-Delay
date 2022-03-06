// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.Item;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import lotr.common.LOTRAchievement;
import net.minecraft.world.World;

public class LOTREntityBlueDwarf extends LOTREntityDwarf
{
    public LOTREntityBlueDwarf(final World world) {
        super(world);
        super.familyInfo.marriageEntityClass = LOTREntityBlueDwarf.class;
        super.familyInfo.marriageAchievement = LOTRAchievement.marryBlueDwarf;
    }
    
    @Override
    protected LOTRFoods getDwarfFoods() {
        return LOTRFoods.BLUE_DWARF;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlueDwarven));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.BLUE_MOUNTAINS;
    }
    
    @Override
    protected Item getDwarfSteelDrop() {
        return LOTRMod.blueDwarfSteel;
    }
    
    @Override
    protected LOTRChestContents getLarderDrops() {
        return LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
    }
    
    @Override
    protected LOTRChestContents getGenericDrops() {
        return LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBlueDwarf;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return this.isChild() ? "blueDwarf/child/hostile" : "blueDwarf/dwarf/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "blueDwarf/dwarf/hired";
        }
        return this.isChild() ? "blueDwarf/child/friendly" : "blueDwarf/dwarf/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.BLUE_MOUNTAINS.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.BLUE_MOUNTAINS;
    }
}
