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

public class LOTREntityHarnedorWarrior extends LOTREntityHarnedhrim
{
    private static ItemStack[] weaponsBronze;
    private static int[] turbanColors;
    
    public LOTREntityHarnedorWarrior(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(8) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_HARNEDOR;
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
        final int i = ((Entity)this).rand.nextInt(LOTREntityHarnedorWarrior.weaponsBronze.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityHarnedorWarrior.weaponsBronze[i].copy());
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHarnedor));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHarnedor));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHarnedor));
        if (((Entity)this).rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else if (((Entity)this).rand.nextInt(5) == 0) {
            final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            final int robeColor = LOTREntityHarnedorWarrior.turbanColors[((Entity)this).rand.nextInt(LOTREntityHarnedorWarrior.turbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHarnedor));
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
            return "nearHarad/harnedor/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/harnedor/warrior/hired";
        }
        return "nearHarad/harnedor/warrior/friendly";
    }
    
    static {
        LOTREntityHarnedorWarrior.weaponsBronze = new ItemStack[] { new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad) };
        LOTREntityHarnedorWarrior.turbanColors = new int[] { 1643539, 6309443, 7014914, 7809314, 5978155 };
    }
}
