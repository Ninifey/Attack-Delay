// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenLindon;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRAchievement;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.world.World;

public class LOTREntityHighElf extends LOTREntityHighElfBase
{
    public LOTREntityHighElf(final World world) {
        super(world);
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorHighElven));
        return horse;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHighElven));
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.highElvenBow));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killHighElf;
    }
    
    @Override
    protected void dropElfItems(final boolean flag, final int i) {
        super.dropElfItems(flag, i);
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.HIGH_ELVEN_HALL, 1, 1 + i);
        }
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenLindon) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "highElf/elf/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "highElf/elf/hired";
        }
        return "highElf/elf/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.HIGH_ELF.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.HIGH_ELF;
    }
}
