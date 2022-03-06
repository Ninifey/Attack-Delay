// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityGondorRenegade extends LOTREntityGondorSoldier
{
    private static ItemStack[] weaponsUmbar;
    
    public LOTREntityGondorRenegade(final World world) {
        super(world);
        super.npcShield = null;
        super.spawnRidingHorse = false;
        super.questInfo.setOfferChance(4000);
        super.questInfo.setMinAlignment(50.0f);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityGondorRenegade.weaponsUmbar.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityGondorRenegade.weaponsUmbar[i].copy());
        super.npcItemsInv.setMeleeWeaponMounted(super.npcItemsInv.getMeleeWeapon());
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItemMounted(super.npcItemsInv.getMeleeWeaponMounted());
        if (((Entity)this).rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsPelargir));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsPelargir));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyPelargir));
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.NEAR_HARAD;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "nearHarad/renegade/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/renegade/hired";
        }
        return "nearHarad/renegade/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GONDOR_RENEGADE.createQuest(this);
    }
    
    static {
        LOTREntityGondorRenegade.weaponsUmbar = new ItemStack[] { new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad) };
    }
}
