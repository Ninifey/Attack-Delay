// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityBlackrootArcher extends LOTREntityBlackrootSoldier
{
    public LOTREntityBlackrootArcher(final World world) {
        super(world);
        super.spawnRidingHorse = false;
    }
    
    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.45, 30, 40, 24.0f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.blackrootBow));
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
