// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.LOTRCapes;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntitySouthronChampion extends LOTREntityNearHaradrimWarrior
{
    private static ItemStack[] weaponsChampion;
    
    public LOTREntitySouthronChampion(final World world) {
        super(world);
        super.spawnRidingHorse = true;
        super.npcCape = LOTRCapes.SOUTHRON_CHAMPION;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
        this.getEntityAttribute(LOTREntityNPC.horseAttackSpeed).setBaseValue(1.9);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntitySouthronChampion.weaponsChampion.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntitySouthronChampion.weaponsChampion[i].copy());
        super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetNearHaradWarlord));
        return data;
    }
    
    static {
        LOTREntitySouthronChampion.weaponsChampion = new ItemStack[] { new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad) };
    }
}
