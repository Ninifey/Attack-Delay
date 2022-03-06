// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityPelargirMarine extends LOTREntityGondorSoldier
{
    public LOTREntityPelargirMarine(final World world) {
        super(world);
        super.spawnRidingHorse = false;
        super.npcShield = LOTRShields.ALIGNMENT_PELARGIR;
    }
    
    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.45, false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextBoolean()) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.tridentPelargir));
        }
        else {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordPelargir));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsPelargir));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsPelargir));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyPelargir));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetPelargir));
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
}
