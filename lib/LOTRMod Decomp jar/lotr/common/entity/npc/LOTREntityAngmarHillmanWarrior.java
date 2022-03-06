// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityAngmarHillmanWarrior extends LOTREntityAngmarHillman
{
    private static ItemStack[] weapons;
    private static ItemStack[] helmets;
    private static ItemStack[] bodies;
    private static ItemStack[] legs;
    private static ItemStack[] boots;
    
    public LOTREntityAngmarHillmanWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_ANGMAR;
    }
    
    @Override
    public EntityAIBase getHillmanAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityAngmarHillmanWarrior.weapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityAngmarHillmanWarrior.weapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        i = ((Entity)this).rand.nextInt(LOTREntityAngmarHillmanWarrior.boots.length);
        this.setCurrentItemOrArmor(1, LOTREntityAngmarHillmanWarrior.boots[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityAngmarHillmanWarrior.legs.length);
        this.setCurrentItemOrArmor(2, LOTREntityAngmarHillmanWarrior.legs[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityAngmarHillmanWarrior.bodies.length);
        this.setCurrentItemOrArmor(3, LOTREntityAngmarHillmanWarrior.bodies[i].copy());
        if (((Entity)this).rand.nextInt(5) != 0) {
            i = ((Entity)this).rand.nextInt(LOTREntityAngmarHillmanWarrior.helmets.length);
            this.setCurrentItemOrArmor(4, LOTREntityAngmarHillmanWarrior.helmets[i].copy());
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public void dropHillmanItems(final boolean flag, final int i) {
    }
    
    static {
        LOTREntityAngmarHillmanWarrior.weapons = new ItemStack[] { new ItemStack(LOTRMod.swordAngmar), new ItemStack(LOTRMod.battleaxeAngmar), new ItemStack(LOTRMod.hammerAngmar), new ItemStack(LOTRMod.daggerAngmar), new ItemStack(LOTRMod.polearmAngmar), new ItemStack(LOTRMod.spearAngmar) };
        LOTREntityAngmarHillmanWarrior.helmets = new ItemStack[] { new ItemStack(LOTRMod.helmetBone), new ItemStack(LOTRMod.helmetFur) };
        LOTREntityAngmarHillmanWarrior.bodies = new ItemStack[] { new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyFur) };
        LOTREntityAngmarHillmanWarrior.legs = new ItemStack[] { new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsFur) };
        LOTREntityAngmarHillmanWarrior.boots = new ItemStack[] { new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsFur) };
    }
}
