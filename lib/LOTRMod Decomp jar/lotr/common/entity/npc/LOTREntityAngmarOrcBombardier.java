// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIOrcPlaceBomb;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityAngmarOrcBombardier extends LOTREntityAngmarOrc
{
    public LOTREntityAngmarOrcBombardier(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIOrcPlaceBomb(this, 1.4));
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    public boolean isOrcBombardier() {
        return true;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setBombingItem(new ItemStack(LOTRMod.orcTorchItem));
        super.npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, 0));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetAngmar));
        return data;
    }
}
