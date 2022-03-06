// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityMoredainMercenary extends LOTREntityMoredain implements LOTRMercenary
{
    private static ItemStack[] weaponsIron;
    private static ItemStack[] weaponsBronze;
    private static int[] turbanColors;
    
    public LOTREntityMoredainMercenary(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
        super.spawnRidingHorse = false;
    }
    
    @Override
    protected EntityAIBase createHaradrimAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, true);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.NEAR_HARAD;
    }
    
    @Override
    public LOTRFaction getHiringFaction() {
        return LOTRFaction.NEAR_HARAD;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(3) == 0) {
            final int i = ((Entity)this).rand.nextInt(LOTREntityMoredainMercenary.weaponsBronze.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityMoredainMercenary.weaponsBronze[i].copy());
            if (((Entity)this).rand.nextInt(5) == 0) {
                super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
            }
        }
        else {
            final int i = ((Entity)this).rand.nextInt(LOTREntityMoredainMercenary.weaponsIron.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityMoredainMercenary.weaponsIron[i].copy());
            if (((Entity)this).rand.nextInt(5) == 0) {
                super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
            }
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        if (((Entity)this).rand.nextInt(8) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
        }
        else if (((Entity)this).rand.nextInt(5) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHarnedor));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHarnedor));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHarnedor));
        }
        else if (((Entity)this).rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUmbar));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUmbar));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUmbar));
        }
        else {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        }
        if (((Entity)this).rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            final int robeColor = LOTREntityMoredainMercenary.turbanColors[((Entity)this).rand.nextInt(LOTREntityMoredainMercenary.turbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public int getMercBaseCost() {
        return 20;
    }
    
    @Override
    public float getMercAlignmentRequired() {
        return 0.0f;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireMoredainMercenary);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "nearHarad/mercenary/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/mercenary/hired";
        }
        return "nearHarad/mercenary/friendly";
    }
    
    static {
        LOTREntityMoredainMercenary.weaponsIron = new ItemStack[] { new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad) };
        LOTREntityMoredainMercenary.weaponsBronze = new ItemStack[] { new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad) };
        LOTREntityMoredainMercenary.turbanColors = new int[] { 10487808, 5976610, 14864579, 10852752, 11498561, 12361037 };
    }
}
