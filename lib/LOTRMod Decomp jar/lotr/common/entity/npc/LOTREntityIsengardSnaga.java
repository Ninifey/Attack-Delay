// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityIsengardSnaga extends LOTREntityOrc
{
    private static ItemStack[] weapons;
    private static ItemStack[] spears;
    private static ItemStack[] helmets;
    private static ItemStack[] bodies;
    private static ItemStack[] legs;
    private static ItemStack[] boots;
    
    public LOTREntityIsengardSnaga(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityIsengardSnaga.weapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityIsengardSnaga.weapons[i].copy());
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            i = ((Entity)this).rand.nextInt(LOTREntityIsengardSnaga.spears.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityIsengardSnaga.spears[i].copy());
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        i = ((Entity)this).rand.nextInt(LOTREntityIsengardSnaga.boots.length);
        this.setCurrentItemOrArmor(1, LOTREntityIsengardSnaga.boots[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityIsengardSnaga.legs.length);
        this.setCurrentItemOrArmor(2, LOTREntityIsengardSnaga.legs[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityIsengardSnaga.bodies.length);
        this.setCurrentItemOrArmor(3, LOTREntityIsengardSnaga.bodies[i].copy());
        if (((Entity)this).rand.nextInt(3) != 0) {
            i = ((Entity)this).rand.nextInt(LOTREntityIsengardSnaga.helmets.length);
            this.setCurrentItemOrArmor(4, LOTREntityIsengardSnaga.helmets[i].copy());
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.URUK_HAI;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killIsengardSnaga;
    }
    
    @Override
    protected void dropOrcItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.URUK_TENT, 1, 2 + i);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "isengard/orc/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "isengard/orc/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
            return "isengard/orc/friendly";
        }
        return "isengard/orc/neutral";
    }
    
    @Override
    protected String getOrcSkirmishSpeech() {
        return "isengard/orc/skirmish";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ISENGARD.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.ISENGARD;
    }
    
    static {
        LOTREntityIsengardSnaga.weapons = new ItemStack[] { new ItemStack(Items.stone_sword), new ItemStack(Items.stone_axe), new ItemStack(Items.stone_pickaxe), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(Items.iron_pickaxe), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerIronPoisoned), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.pickaxeBronze), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerBronzePoisoned), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.scimitarUruk), new ItemStack(LOTRMod.axeUruk), new ItemStack(LOTRMod.pickaxeUruk), new ItemStack(LOTRMod.daggerUruk), new ItemStack(LOTRMod.daggerUrukPoisoned), new ItemStack(LOTRMod.battleaxeUruk), new ItemStack(LOTRMod.hammerUruk), new ItemStack(LOTRMod.pikeUruk) };
        LOTREntityIsengardSnaga.spears = new ItemStack[] { new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.spearUruk) };
        LOTREntityIsengardSnaga.helmets = new ItemStack[] { new ItemStack((Item)Items.leather_helmet), new ItemStack(LOTRMod.helmetBronze), new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.helmetBone) };
        LOTREntityIsengardSnaga.bodies = new ItemStack[] { new ItemStack((Item)Items.leather_chestplate), new ItemStack(LOTRMod.bodyBronze), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyUruk) };
        LOTREntityIsengardSnaga.legs = new ItemStack[] { new ItemStack((Item)Items.leather_leggings), new ItemStack(LOTRMod.legsBronze), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsUruk) };
        LOTREntityIsengardSnaga.boots = new ItemStack[] { new ItemStack((Item)Items.leather_boots), new ItemStack(LOTRMod.bootsBronze), new ItemStack(LOTRMod.bootsFur), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsUruk) };
    }
}
