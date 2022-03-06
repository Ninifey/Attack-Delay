// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityNearHaradrimWarrior extends LOTREntityNearHaradrim
{
    private static ItemStack[] weaponsIron;
    private static ItemStack[] weaponsBronze;
    private static int[] turbanColors;
    
    public LOTREntityNearHaradrimWarrior(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.spawnRidingHorse = false;
        super.npcShield = LOTRShields.ALIGNMENT_NEAR_HARAD;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(3) == 0) {
            final int i = ((Entity)this).rand.nextInt(LOTREntityNearHaradrimWarrior.weaponsBronze.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityNearHaradrimWarrior.weaponsBronze[i].copy());
            if (((Entity)this).rand.nextInt(5) == 0) {
                super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
            }
        }
        else {
            final int i = ((Entity)this).rand.nextInt(LOTREntityNearHaradrimWarrior.weaponsIron.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityNearHaradrimWarrior.weaponsIron[i].copy());
            if (((Entity)this).rand.nextInt(5) == 0) {
                super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
            }
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        if (((Entity)this).rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else if (((Entity)this).rand.nextInt(5) == 0) {
            final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            final int robeColor = LOTREntityNearHaradrimWarrior.turbanColors[((Entity)this).rand.nextInt(LOTREntityNearHaradrimWarrior.turbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetNearHarad));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "nearHarad/coast/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/coast/warrior/hired";
        }
        return "nearHarad/coast/warrior/friendly";
    }
    
    static {
        LOTREntityNearHaradrimWarrior.weaponsIron = new ItemStack[] { new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad) };
        LOTREntityNearHaradrimWarrior.weaponsBronze = new ItemStack[] { new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad) };
        LOTREntityNearHaradrimWarrior.turbanColors = new int[] { 1643539, 6309443, 7014914, 7809314, 5978155 };
    }
}
