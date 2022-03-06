// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRCapes;
import net.minecraft.world.World;

public class LOTREntityDunlendingBerserker extends LOTREntityDunlendingWarrior
{
    public LOTREntityDunlendingBerserker(final World world) {
        super(world);
        super.npcShield = null;
        super.npcCape = LOTRCapes.DUNLENDING_BERSERKER;
    }
    
    @Override
    public EntityAIBase getDunlendingAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamageExtra).setBaseValue(2.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(2);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsFur));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsFur));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBone));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDunlending));
        return data;
    }
}
