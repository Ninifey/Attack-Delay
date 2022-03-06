// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityLamedonArcher extends LOTREntityLamedonSoldier
{
    public LOTREntityLamedonArcher(final World world) {
        super(world);
        super.spawnRidingHorse = false;
    }
    
    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 30, 50, 16.0f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.gondorBow));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getRangedWeapon());
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        this.dropNPCArrows(i);
    }
}
