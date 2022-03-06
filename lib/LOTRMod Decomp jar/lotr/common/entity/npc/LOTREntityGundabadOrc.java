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
import net.minecraft.init.Blocks;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityGundabadOrc extends LOTREntityOrc
{
    private static ItemStack[] weapons;
    private static ItemStack[] spears;
    private static ItemStack[] helmets;
    private static ItemStack[] bodies;
    private static ItemStack[] legs;
    private static ItemStack[] boots;
    
    public LOTREntityGundabadOrc(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityGundabadOrc.weapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityGundabadOrc.weapons[i].copy());
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            i = ((Entity)this).rand.nextInt(LOTREntityGundabadOrc.spears.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityGundabadOrc.spears[i].copy());
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        i = ((Entity)this).rand.nextInt(LOTREntityGundabadOrc.boots.length);
        this.setCurrentItemOrArmor(1, LOTREntityGundabadOrc.boots[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityGundabadOrc.legs.length);
        this.setCurrentItemOrArmor(2, LOTREntityGundabadOrc.legs[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityGundabadOrc.bodies.length);
        this.setCurrentItemOrArmor(3, LOTREntityGundabadOrc.bodies[i].copy());
        if (((Entity)this).rand.nextInt(3) != 0) {
            i = ((Entity)this).rand.nextInt(LOTREntityGundabadOrc.helmets.length);
            this.setCurrentItemOrArmor(4, LOTREntityGundabadOrc.helmets[i].copy());
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GUNDABAD;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killGundabadOrc;
    }
    
    @Override
    protected void dropOrcItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.GUNDABAD_TENT, 1, 2 + i);
        }
        if (((Entity)this).rand.nextInt(4000) == 0) {
            final ItemStack dirt = new ItemStack(Blocks.dirt);
            dirt.setStackDisplayName("Such Wealth");
            this.entityDropItem(dirt, 0.0f);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "gundabad/orc/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "gundabad/orc/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
            return "gundabad/orc/friendly";
        }
        return "gundabad/orc/neutral";
    }
    
    @Override
    protected String getOrcSkirmishSpeech() {
        return "gundabad/orc/skirmish";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GUNDABAD.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GUNDABAD;
    }
    
    static {
        LOTREntityGundabadOrc.weapons = new ItemStack[] { new ItemStack(Items.stone_sword), new ItemStack(Items.stone_axe), new ItemStack(Items.stone_pickaxe), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(Items.iron_pickaxe), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerIronPoisoned), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.pickaxeBronze), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerBronzePoisoned), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.swordAngmar), new ItemStack(LOTRMod.axeAngmar), new ItemStack(LOTRMod.pickaxeAngmar), new ItemStack(LOTRMod.daggerAngmar), new ItemStack(LOTRMod.daggerAngmarPoisoned), new ItemStack(LOTRMod.battleaxeAngmar), new ItemStack(LOTRMod.hammerAngmar), new ItemStack(LOTRMod.scimitarOrc), new ItemStack(LOTRMod.axeOrc), new ItemStack(LOTRMod.pickaxeOrc), new ItemStack(LOTRMod.daggerOrc), new ItemStack(LOTRMod.daggerOrcPoisoned), new ItemStack(LOTRMod.battleaxeOrc), new ItemStack(LOTRMod.hammerOrc), new ItemStack(LOTRMod.polearmOrc), new ItemStack(LOTRMod.swordDolGuldur), new ItemStack(LOTRMod.axeDolGuldur), new ItemStack(LOTRMod.pickaxeDolGuldur), new ItemStack(LOTRMod.daggerDolGuldur), new ItemStack(LOTRMod.daggerDolGuldurPoisoned), new ItemStack(LOTRMod.battleaxeDolGuldur), new ItemStack(LOTRMod.hammerDolGuldur), new ItemStack(LOTRMod.swordGundabadUruk), new ItemStack(LOTRMod.battleaxeGundabadUruk), new ItemStack(LOTRMod.hammerGundabadUruk), new ItemStack(LOTRMod.pikeGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUrukPoisoned), new ItemStack(LOTRMod.polearmAngmar), new ItemStack(LOTRMod.pikeDolGuldur) };
        LOTREntityGundabadOrc.spears = new ItemStack[] { new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.spearAngmar), new ItemStack(LOTRMod.spearOrc), new ItemStack(LOTRMod.spearDolGuldur), new ItemStack(LOTRMod.spearGundabadUruk) };
        LOTREntityGundabadOrc.helmets = new ItemStack[] { new ItemStack((Item)Items.leather_helmet), new ItemStack(LOTRMod.helmetBronze), new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.helmetBone), new ItemStack(LOTRMod.helmetAngmar), new ItemStack(LOTRMod.helmetOrc), new ItemStack(LOTRMod.helmetDolGuldur) };
        LOTREntityGundabadOrc.bodies = new ItemStack[] { new ItemStack((Item)Items.leather_chestplate), new ItemStack(LOTRMod.bodyBronze), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyOrc), new ItemStack(LOTRMod.bodyDolGuldur), new ItemStack(LOTRMod.bodyGundabadUruk) };
        LOTREntityGundabadOrc.legs = new ItemStack[] { new ItemStack((Item)Items.leather_leggings), new ItemStack(LOTRMod.legsBronze), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsOrc), new ItemStack(LOTRMod.legsDolGuldur), new ItemStack(LOTRMod.legsGundabadUruk) };
        LOTREntityGundabadOrc.boots = new ItemStack[] { new ItemStack((Item)Items.leather_boots), new ItemStack(LOTRMod.bootsBronze), new ItemStack(LOTRMod.bootsFur), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsOrc), new ItemStack(LOTRMod.bootsDolGuldur), new ItemStack(LOTRMod.bootsGundabadUruk) };
    }
}
