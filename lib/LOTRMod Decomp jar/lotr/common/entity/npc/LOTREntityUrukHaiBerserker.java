// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityUrukHaiBerserker extends LOTREntityUrukHai
{
    public static float BERSERKER_SCALE;
    
    public LOTREntityUrukHaiBerserker(final World world) {
        super(world);
        this.setSize(super.npcWidth * LOTREntityUrukHaiBerserker.BERSERKER_SCALE, super.npcHeight * LOTREntityUrukHaiBerserker.BERSERKER_SCALE);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamageExtra).setBaseValue(1.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarUrukBerserker));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUrukBerserker));
        return data;
    }
    
    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.8f;
    }
    
    static {
        LOTREntityUrukHaiBerserker.BERSERKER_SCALE = 1.15f;
    }
}
