// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;

public class LOTREntityHarnedhrim extends LOTREntityNearHaradrimBase
{
    public LOTREntityHarnedhrim(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.HARNEDOR;
    }
    
    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.HARNEDOR_DRINK;
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getHarnedorName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    public String getNPCFormattedName(final String npcName, final String entityName) {
        if (this.getClass() == LOTREntityHarnedhrim.class) {
            return StatCollector.translateToLocalFormatted("entity.lotr.Harnedhrim.entityName", new Object[] { npcName });
        }
        return super.getNPCFormattedName(npcName, entityName);
    }
    
    @Override
    protected void dropHaradrimItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(5) == 0) {
            this.dropChestContents(LOTRChestContents.HARNEDOR_HOUSE, 1, 2 + i);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/harnedor/haradrim/friendly";
        }
        return "nearHarad/harnedor/haradrim/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.HARNEDOR.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.HARNEDOR;
    }
}
