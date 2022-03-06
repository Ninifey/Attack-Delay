// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityMoredainWarrior;
import lotr.common.item.LOTRItemBanner;
import lotr.common.entity.npc.LOTREntityTauredainBlowgunner;
import lotr.common.entity.animal.LOTREntityLioness;
import lotr.common.entity.animal.LOTREntityLion;
import lotr.common.entity.animal.LOTREntityCrocodile;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.npc.LOTREntitySwanKnight;
import lotr.common.entity.npc.LOTREntityLossarnachAxeman;
import lotr.common.entity.npc.LOTREntityDolAmrothSoldier;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityMordorWarg;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import lotr.common.entity.npc.LOTREntityGondorRenegade;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityUrukWarg;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityIsengardSnaga;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityGaladhrimElf;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import net.minecraft.item.Item;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityDarkHuorn;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.fac.LOTRFaction;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.npc.LOTREntityNPC;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import lotr.common.LOTRLore;
import lotr.common.LOTRAchievement;
import java.util.List;
import java.util.Map;
import java.util.Random;

public enum LOTRMiniQuestFactory
{
    HOBBIT("hobbit"), 
    RANGER_NORTH("rangerNorth"), 
    RANGER_NORTH_ARNOR_RELIC("rangerNorthArnorRelic"), 
    BLUE_MOUNTAINS("blueMountains"), 
    HIGH_ELF("highElf"), 
    RIVENDELL("rivendell"), 
    GUNDABAD("gundabad"), 
    ANGMAR("angmar"), 
    ANGMAR_HILLMAN("angmarHillman"), 
    WOOD_ELF("woodElf"), 
    DOL_GULDUR("dolGuldur"), 
    DALE("dale"), 
    DURIN("durin"), 
    GALADHRIM("galadhrim"), 
    DUNLAND("dunland"), 
    ISENGARD("isengard"), 
    ENT("ent"), 
    ROHAN("rohan"), 
    ROHAN_SHIELDMAIDEN("rohanShieldmaiden"), 
    GONDOR("gondor"), 
    GONDOR_KILL_RENEGADE("gondorKillRenegade"), 
    MORDOR("mordor"), 
    DORWINION("dorwinion"), 
    DORWINION_ELF("dorwinionElf"), 
    RHUN("rhun"), 
    HARNEDOR("harnedor"), 
    NEAR_HARAD("nearHarad"), 
    UMBAR("umbar"), 
    CORSAIR("corsair"), 
    GONDOR_RENEGADE("gondorRenegade"), 
    NOMAD("nomad"), 
    GULF_HARAD("gulfHarad"), 
    MOREDAIN("moredain"), 
    TAUREDAIN("tauredain"), 
    HALF_TROLL("halfTroll");
    
    private static Random rand;
    private static Map<Class<? extends LOTRMiniQuest>, Integer> questClassWeights;
    private String baseName;
    private LOTRMiniQuestFactory baseSpeechGroup;
    private Map<Class<? extends LOTRMiniQuest>, List<LOTRMiniQuest.QuestFactoryBase>> questFactories;
    private LOTRAchievement questAchievement;
    private List<LOTRLore.LoreCategory> loreCategories;
    
    private LOTRMiniQuestFactory(final String s) {
        this.questFactories = new HashMap<Class<? extends LOTRMiniQuest>, List<LOTRMiniQuest.QuestFactoryBase>>();
        this.loreCategories = new ArrayList<LOTRLore.LoreCategory>();
        this.baseName = s;
    }
    
    public String getBaseName() {
        return this.baseName;
    }
    
    public LOTRMiniQuestFactory getBaseSpeechGroup() {
        if (this.baseSpeechGroup != null) {
            return this.baseSpeechGroup;
        }
        return this;
    }
    
    private void setBaseSpeechGroup(final LOTRMiniQuestFactory qf) {
        this.baseSpeechGroup = qf;
    }
    
    private void addQuest(final LOTRMiniQuest.QuestFactoryBase factory) {
        final Class questClass = factory.getQuestClass();
        Class<? extends LOTRMiniQuest> registryClass = null;
        for (final Class c : LOTRMiniQuestFactory.questClassWeights.keySet()) {
            if (questClass.equals(c)) {
                registryClass = (Class<? extends LOTRMiniQuest>)c;
                break;
            }
        }
        if (registryClass == null) {
            for (final Class c : LOTRMiniQuestFactory.questClassWeights.keySet()) {
                if (c.isAssignableFrom(questClass)) {
                    registryClass = (Class<? extends LOTRMiniQuest>)c;
                    break;
                }
            }
        }
        if (registryClass == null) {
            throw new IllegalArgumentException("Could not find registered quest class for " + questClass.toString());
        }
        factory.setFactoryGroup(this);
        List list = this.questFactories.get(registryClass);
        if (list == null) {
            list = new ArrayList();
            this.questFactories.put(registryClass, list);
        }
        list.add(factory);
    }
    
    public LOTRMiniQuest createQuest(final LOTREntityNPC npc) {
        final int totalWeight = getTotalQuestClassWeight(this);
        if (totalWeight <= 0) {
            FMLLog.warning("LOTR: No quests registered for %s!", new Object[] { this.baseName });
            return null;
        }
        int i;
        final int randomWeight = i = LOTRMiniQuestFactory.rand.nextInt(totalWeight);
        final Iterator<Map.Entry<Class<? extends LOTRMiniQuest>, List<LOTRMiniQuest.QuestFactoryBase>>> iterator = this.questFactories.entrySet().iterator();
        List<LOTRMiniQuest.QuestFactoryBase> chosenFactoryList = null;
        while (true) {
            while (iterator.hasNext()) {
                final Map.Entry<Class<? extends LOTRMiniQuest>, List<LOTRMiniQuest.QuestFactoryBase>> next = iterator.next();
                chosenFactoryList = next.getValue();
                i -= getQuestClassWeight(next.getKey());
                if (i < 0) {
                    final LOTRMiniQuest.QuestFactoryBase factory = chosenFactoryList.get(LOTRMiniQuestFactory.rand.nextInt(chosenFactoryList.size()));
                    final LOTRMiniQuest quest = factory.createQuest(npc, LOTRMiniQuestFactory.rand);
                    if (quest != null) {
                        quest.questGroup = this;
                    }
                    return quest;
                }
            }
            continue;
        }
    }
    
    private void setAchievement(final LOTRAchievement a) {
        if (this.questAchievement != null) {
            throw new IllegalArgumentException("Miniquest achievement is already registered");
        }
        this.questAchievement = a;
    }
    
    public LOTRAchievement getAchievement() {
        return this.questAchievement;
    }
    
    private void setLore(final LOTRLore.LoreCategory... categories) {
        this.loreCategories = Arrays.asList(categories);
    }
    
    public List<LOTRLore.LoreCategory> getLoreCategories() {
        return this.loreCategories;
    }
    
    private static void registerQuestClass(final Class<? extends LOTRMiniQuest> questClass, final int weight) {
        LOTRMiniQuestFactory.questClassWeights.put(questClass, weight);
    }
    
    private static int getQuestClassWeight(final Class<? extends LOTRMiniQuest> questClass) {
        final Integer i = LOTRMiniQuestFactory.questClassWeights.get(questClass);
        if (i == null) {
            throw new RuntimeException("Encountered a registered quest class " + questClass.toString() + " which is not assigned a weight");
        }
        return i;
    }
    
    private static int getTotalQuestClassWeight(final LOTRMiniQuestFactory factory) {
        final Set<Class<? extends LOTRMiniQuest>> registeredQuestTypes = new HashSet<Class<? extends LOTRMiniQuest>>();
        for (final Map.Entry<Class<? extends LOTRMiniQuest>, List<LOTRMiniQuest.QuestFactoryBase>> entry : factory.questFactories.entrySet()) {
            final Class questType = entry.getKey();
            registeredQuestTypes.add(questType);
        }
        int totalWeight = 0;
        for (final Class<? extends LOTRMiniQuest> c : registeredQuestTypes) {
            totalWeight += getQuestClassWeight(c);
        }
        return totalWeight;
    }
    
    public static void createMiniQuests() {
        registerQuestClass(LOTRMiniQuestCollect.class, 10);
        registerQuestClass(LOTRMiniQuestKill.class, 8);
        registerQuestClass(LOTRMiniQuestBounty.class, 4);
        LOTRMiniQuestFactory.HOBBIT.setAchievement(LOTRAchievement.doMiniquestHobbit);
        LOTRMiniQuestFactory.HOBBIT.setLore(LOTRLore.LoreCategory.SHIRE);
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("pipeweed").setCollectItem(new ItemStack(LOTRMod.pipeweed), 20, 40).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugAle), 1, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugCider), 1, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugPerry), 1, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugMead), 1, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugCherryLiqueur), 1, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(Items.apple), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.appleGreen), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.pear), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.plum), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.cherry), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(LOTRMod.berryPieItem), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(Items.bread), 4, 12).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(Items.cooked_chicken), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(Items.cooked_porkchop), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("uncleBirthday").setCollectItem(new ItemStack(LOTRMod.deerCooked), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("farmingTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 3).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("farmingTool").setCollectItem(new ItemStack(LOTRMod.hoeBronze), 1, 3).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("farmingTool").setCollectItem(new ItemStack(Items.bucket), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("firewood").setCollectItem(new ItemStack(Blocks.log, 1, 0), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("firewood").setCollectItem(new ItemStack(LOTRMod.wood, 1, 0), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("firewood").setCollectItem(new ItemStack(LOTRMod.wood4, 1, 0), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("firewood").setCollectItem(new ItemStack(LOTRMod.wood6, 1, 1), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("kitchenware").setCollectItem(new ItemStack(LOTRMod.plate), 5, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("kitchenware").setCollectItem(new ItemStack(LOTRMod.clayPlate), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("kitchenware").setCollectItem(new ItemStack(LOTRMod.mug), 5, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("books").setCollectItem(new ItemStack(Items.book), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("pastries").setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("pastries").setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("pastries").setCollectItem(new ItemStack(LOTRMod.berryPieItem), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HOBBIT.addQuest(new LOTRMiniQuestCollect.QFCollect("pastries").setCollectItem(new ItemStack(Items.cake), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.setAchievement(LOTRAchievement.doMiniquestRanger);
        LOTRMiniQuestFactory.RANGER_NORTH.setLore(LOTRLore.LoreCategory.ERIADOR);
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 1), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(LOTRMod.wood2, 1, 1), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(LOTRMod.wood4, 1, 0), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(LOTRMod.wood6, 1, 1), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBricks").setCollectItem(new ItemStack(Blocks.stonebrick), 40, 100).setRewardFactor(0.2f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBricks").setCollectItem(new ItemStack(LOTRMod.brick2, 1, 3), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bread), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 5, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 5, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 5, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.mugAle), 5, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.mugCider), 5, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectAthelas").setCollectItem(new ItemStack(LOTRMod.athelas), 2, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGondorItem").setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGondorItem").setCollectItem(new ItemStack(LOTRMod.helmetGondor), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGondorItem").setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("craftRangerItem").setCollectItem(new ItemStack(LOTRMod.helmetRanger), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("craftRangerItem").setCollectItem(new ItemStack(LOTRMod.bodyRanger), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(Items.iron_sword), 2, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.daggerIron), 2, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.rangerBow), 3, 7).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(Items.arrow), 20, 40).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Items.leather), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEnemyBones").setCollectItem(new ItemStack(LOTRMod.orcBone), 10, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEnemyBones").setCollectItem(new ItemStack(LOTRMod.wargBone), 10, 30).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 10, 40));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killAngmar").setKillFaction(LOTRFaction.ANGMAR, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killTroll").setKillEntity(LOTREntityTroll.class, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killMountainTroll").setKillEntity(LOTREntityMountainTroll.class, 20, 40));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 40));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityAngmarWarg.class, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killDarkHuorn").setKillEntity(LOTREntityDarkHuorn.class, 20, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("avengeBrother").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("avengeBrother").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killHillmen").setKillEntity(LOTREntityAngmarHillman.class, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.setAchievement(LOTRAchievement.doMiniquestRanger);
        LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.setBaseSpeechGroup(LOTRMiniQuestFactory.RANGER_NORTH);
        LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.setLore(LOTRLore.LoreCategory.ERIADOR);
        LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("arnorRelicKill").setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("arnorRelicKill").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("arnorRelicKill").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        for (final List<LOTRMiniQuest.QuestFactoryBase> qfList : LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.questFactories.values()) {
            for (final LOTRMiniQuest.QuestFactoryBase qf : qfList) {
                qf.setRewardFactor(0.0f);
                qf.setRewardItems(new ItemStack[] { new ItemStack(LOTRMod.helmetArnor), new ItemStack(LOTRMod.bodyArnor), new ItemStack(LOTRMod.legsArnor), new ItemStack(LOTRMod.bootsArnor), new ItemStack(LOTRMod.swordArnor), new ItemStack(LOTRMod.daggerArnor), new ItemStack(LOTRMod.spearArnor) });
            }
        }
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.setAchievement(LOTRAchievement.doMiniquestBlueMountains);
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.setLore(LOTRLore.LoreCategory.BLUE_MOUNTAINS);
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("mineMithril").setCollectItem(new ItemStack(LOTRMod.mithril), 1, 2).setRewardFactor(50.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 15).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 15).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.glowstone_dust), 5, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.sapphire), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.amethyst), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon").setCollectItem(new ItemStack(LOTRMod.hammerBlueDwarven), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeBlueDwarven), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon").setCollectItem(new ItemStack(LOTRMod.throwingAxeBlueDwarven), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugDwarvenAle), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 20, 40));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.HIGH_ELF.setAchievement(LOTRAchievement.doMiniquestHighElf);
        LOTRMiniQuestFactory.HIGH_ELF.setLore(LOTRLore.LoreCategory.LINDON);
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(Blocks.sapling, 1, 2), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(LOTRMod.sapling2, 1, 1), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBirchWood").setCollectItem(new ItemStack(Blocks.log, 1, 2), 10, 50).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGoldenLeaves").setCollectItem(new ItemStack(LOTRMod.leaves, 1, 1), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMallornSapling").setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMallornNut").setCollectItem(new ItemStack(LOTRMod.mallornNut), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectCrystal").setCollectItem(new ItemStack(LOTRMod.quenditeCrystal), 4, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.swordHighElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.polearmHighElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.longspearHighElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.spearHighElven), 1, 4).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.helmetHighElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.bodyHighElven), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 10).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.sapphire), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.pearl), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDwarven").setCollectItem(new ItemStack(LOTRMod.brick3, 1, 12), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.helmetRanger), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick, 1, 1), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick, 1, 5), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick2, 1, 3), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick2, 1, 6), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 0), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 1), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 2), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack((Block)Blocks.red_flower, 1, 0), 2, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack((Block)Blocks.yellow_flower, 1, 0), 2, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectString").setCollectItem(new ItemStack(Items.string), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 40));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityAngmarOrc.class, 10, 40));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 10, 40));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killAngmar").setKillFaction(LOTRFaction.ANGMAR, 10, 30));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityAngmarWarg.class, 10, 30));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killTroll").setKillEntity(LOTREntityTroll.class, 10, 30));
        LOTRMiniQuestFactory.HIGH_ELF.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.RIVENDELL.setAchievement(LOTRAchievement.doMiniquestRivendell);
        LOTRMiniQuestFactory.RIVENDELL.setLore(LOTRLore.LoreCategory.RIVENDELL, LOTRLore.LoreCategory.EREGION);
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(Blocks.sapling, 1, 2), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(LOTRMod.sapling2, 1, 1), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBirchWood").setCollectItem(new ItemStack(Blocks.log, 1, 2), 10, 50).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGoldenLeaves").setCollectItem(new ItemStack(LOTRMod.leaves, 1, 1), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMallornSapling").setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMallornNut").setCollectItem(new ItemStack(LOTRMod.mallornNut), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectCrystal").setCollectItem(new ItemStack(LOTRMod.quenditeCrystal), 4, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.swordRivendell), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.polearmRivendell), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.longspearRivendell), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.spearRivendell), 1, 4).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.helmetRivendell), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.bodyRivendell), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 10).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.pearl), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDwarven").setCollectItem(new ItemStack(LOTRMod.brick3, 1, 12), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.helmetRanger), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick, 1, 1), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick, 1, 5), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick2, 1, 3), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectNumenorItem").setCollectItem(new ItemStack(LOTRMod.brick2, 1, 6), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 0), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 1), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 2), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack((Block)Blocks.red_flower, 1, 0), 2, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack((Block)Blocks.yellow_flower, 1, 0), 2, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectString").setCollectItem(new ItemStack(Items.string), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 40));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityAngmarOrc.class, 10, 40));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 10, 40));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killAngmar").setKillFaction(LOTRFaction.ANGMAR, 10, 30));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityAngmarWarg.class, 10, 30));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killTroll").setKillEntity(LOTREntityTroll.class, 10, 30));
        LOTRMiniQuestFactory.RIVENDELL.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.GUNDABAD.setAchievement(LOTRAchievement.doMiniquestGundabad);
        LOTRMiniQuestFactory.GUNDABAD.setLore(LOTRLore.LoreCategory.GUNDABAD);
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.daggerIron), 1, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bone), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack((Item)Items.iron_helmet), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack((Item)Items.iron_chestplate), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetBronze), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyBronze), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.HOBBIT, 10, 30));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.RANGER_NORTH, 10, 40));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.HIGH_ELF, 10, 40));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.GALADHRIM, 10, 40));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRanger").setKillEntity(LOTREntityRangerNorth.class, 10, 30));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityHighElf.class, 10, 30));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityRivendellElf.class, 10, 30));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityGaladhrimElf.class, 10, 30));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killDwarf").setKillEntity(LOTREntityDwarf.class, 10, 30));
        LOTRMiniQuestFactory.GUNDABAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.ANGMAR.setAchievement(LOTRAchievement.doMiniquestAngmar);
        LOTRMiniQuestFactory.ANGMAR.setLore(LOTRLore.LoreCategory.ANGMAR);
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.swordAngmar), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeAngmar), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.axeAngmar), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.daggerAngmar), 1, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.polearmAngmar), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bone), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyAngmar), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.legsAngmar), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bootsAngmar), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.guldurilCrystal), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.RANGER_NORTH, 10, 40));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.HIGH_ELF, 10, 40));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRanger").setKillEntity(LOTREntityRangerNorth.class, 10, 30));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityHighElf.class, 10, 30));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityRivendellElf.class, 10, 30));
        LOTRMiniQuestFactory.ANGMAR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.setAchievement(LOTRAchievement.doMiniquestAngmar);
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.setLore(LOTRLore.LoreCategory.ANGMAR);
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMeat").setCollectItem(new ItemStack(Items.cooked_porkchop), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMeat").setCollectItem(new ItemStack(Items.cooked_beef), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMeat").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMeat").setCollectItem(new ItemStack(LOTRMod.deerCooked), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMeat").setCollectItem(new ItemStack(Items.cooked_chicken), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyAngmar), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.legsAngmar), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bootsAngmar), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetBronze), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyBronze), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.legsBronze), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bootsBronze), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack((Item)Items.iron_helmet), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack((Item)Items.iron_chestplate), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack((Item)Items.iron_leggings), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack((Item)Items.iron_boots), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMetal").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMetal").setCollectItem(new ItemStack(LOTRMod.bronze), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMetal").setCollectItem(new ItemStack(LOTRMod.copper), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMetal").setCollectItem(new ItemStack(LOTRMod.orcSteel), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcBrew").setCollectItem(new ItemStack(LOTRMod.mugOrcDraught), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.swordAngmar), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeAngmar), 1, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.polearmAngmar), 1, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.swordBronze), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeBronze), 1, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeIron), 1, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.pikeIron), 1, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectThrowingAxes").setCollectItem(new ItemStack(LOTRMod.throwingAxeIron), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectThrowingAxes").setCollectItem(new ItemStack(LOTRMod.throwingAxeBronze), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectTreasure").setCollectItem(new ItemStack(Items.gold_ingot), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectTreasure").setCollectItem(new ItemStack(LOTRMod.silver), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killRanger").setKillFaction(LOTRFaction.RANGER_NORTH, 10, 30));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killRangerMany").setKillFaction(LOTRFaction.RANGER_NORTH, 40, 60));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killHighElf").setKillFaction(LOTRFaction.HIGH_ELF, 10, 30));
        LOTRMiniQuestFactory.ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.WOOD_ELF.setAchievement(LOTRAchievement.doMiniquestWoodElf);
        LOTRMiniQuestFactory.WOOD_ELF.setLore(LOTRLore.LoreCategory.WOODLAND_REALM);
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGoldenLeaves").setCollectItem(new ItemStack(LOTRMod.leaves, 1, 1), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMallornSapling").setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMallornNut").setCollectItem(new ItemStack(LOTRMod.mallornNut), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectLorienFlowers").setCollectItem(new ItemStack(LOTRMod.elanor), 2, 7).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectLorienFlowers").setCollectItem(new ItemStack(LOTRMod.niphredil), 2, 7).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.swordWoodElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.polearmWoodElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.longspearWoodElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.spearWoodElven), 1, 4).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.helmetWoodElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.bodyWoodElven), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(LOTRMod.sapling7, 1, 1), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(LOTRMod.sapling, 1, 3), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectString").setCollectItem(new ItemStack(Items.string), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetDolGuldur), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killDolGuldur").setKillFaction(LOTRFaction.DOL_GULDUR, 10, 40));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityDolGuldurOrc.class, 10, 40));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killSpider").setKillEntity(LOTREntityMirkwoodSpider.class, 10, 40));
        LOTRMiniQuestFactory.WOOD_ELF.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.DOL_GULDUR.setAchievement(LOTRAchievement.doMiniquestDolGuldur);
        LOTRMiniQuestFactory.DOL_GULDUR.setLore(LOTRLore.LoreCategory.DOL_GULDUR);
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.swordDolGuldur), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeDolGuldur), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.axeDolGuldur), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.daggerDolGuldur), 1, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.pikeDolGuldur), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bone), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetDolGuldur), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyDolGuldur), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.legsDolGuldur), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bootsDolGuldur), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.guldurilCrystal), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.amethyst), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.GALADHRIM, 10, 40));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.WOOD_ELF, 10, 40));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.DALE, 10, 40));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityGaladhrimElf.class, 10, 30));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killElf").setKillEntity(LOTREntityWoodElf.class, 10, 30));
        LOTRMiniQuestFactory.DOL_GULDUR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.DALE.setAchievement(LOTRAchievement.doMiniquestDale);
        LOTRMiniQuestFactory.DALE.setLore(LOTRLore.LoreCategory.DALE);
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("pastries").setCollectItem(new ItemStack(LOTRMod.dalishPastryItem), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.mugRedWine), 2, 5).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 2, 5).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArrows").setCollectItem(new ItemStack(Items.arrow), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("foreignItem").setCollectItem(new ItemStack(LOTRMod.oliveBread), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("foreignItem").setCollectItem(new ItemStack(LOTRMod.banana), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("foreignItem").setCollectItem(new ItemStack(LOTRMod.mango), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("foreignItem").setCollectItem(new ItemStack(LOTRMod.daggerGondor), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("foreignItem").setCollectItem(new ItemStack(LOTRMod.daggerRohan), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("foreignItem").setCollectItem(new ItemStack(LOTRMod.daggerBlueDwarven), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(Items.gold_ingot), 3, 10).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(Items.iron_ingot), 5, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(LOTRMod.bronze), 5, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(Items.wheat), 20, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(Items.bread), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("dwarfTrade").setCollectItem(new ItemStack(Blocks.log, 1, 1), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("smithyItem").setCollectItem(new ItemStack(Items.coal), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("smithyItem").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("smithyItem").setCollectItem(new ItemStack(LOTRMod.bronze), 3, 10).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("smithyItem").setCollectItem(new ItemStack(Items.bucket), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("smithyItem").setCollectItem(new ItemStack(Items.lava_bucket), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.swordDale), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.daggerDale), 2, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.spearDale), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.pikeDale), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.helmetDale), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.bodyDale), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.legsDale), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.bootsDale), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.daleBow), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.arrow), 10, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.leather), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.apple), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.appleGreen), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 2, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_chicken), 2, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killDolGuldur").setKillFaction(LOTRFaction.DOL_GULDUR, 10, 40));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killMordor").setKillFaction(LOTRFaction.MORDOR, 10, 40));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityDolGuldurOrc.class, 10, 30));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.DALE.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.DURIN.setAchievement(LOTRAchievement.doMiniquestDwarf);
        LOTRMiniQuestFactory.DURIN.setLore(LOTRLore.LoreCategory.DURIN);
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("mineMithril").setCollectItem(new ItemStack(LOTRMod.mithril), 1, 2).setRewardFactor(50.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 15).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 15).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.glowstone_dust), 5, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon").setCollectItem(new ItemStack(LOTRMod.hammerDwarven), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeDwarven), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon").setCollectItem(new ItemStack(LOTRMod.throwingAxeDwarven), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugDwarvenAle), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 20, 40));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killSpider").setKillEntity(LOTREntityMirkwoodSpider.class, 20, 30));
        LOTRMiniQuestFactory.DURIN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.GALADHRIM.setAchievement(LOTRAchievement.doMiniquestGaladhrim);
        LOTRMiniQuestFactory.GALADHRIM.setLore(LOTRLore.LoreCategory.LOTHLORIEN);
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 5, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(LOTRMod.elanor), 5, 30).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(LOTRMod.niphredil), 5, 30).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collect").setCollectItem(new ItemStack(LOTRMod.mallornNut), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFangorn").setCollectItem(new ItemStack(LOTRMod.fangornPlant, 1, 0), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFangorn").setCollectItem(new ItemStack(LOTRMod.fangornPlant, 1, 2), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFangorn").setCollectItem(new ItemStack(LOTRMod.fangornPlant, 1, 5), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectCrystal").setCollectItem(new ItemStack(LOTRMod.quenditeCrystal), 4, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.swordElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.polearmElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.longspearElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.spearElven), 1, 4).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.helmetElven), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.bodyElven), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 10).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.amber), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDwarven").setCollectItem(new ItemStack(LOTRMod.brick3, 1, 12), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 1), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack(Blocks.sapling, 1, 2), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack((Block)Blocks.red_flower, 1, 0), 2, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPlants").setCollectItem(new ItemStack((Block)Blocks.yellow_flower, 1, 0), 2, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectString").setCollectItem(new ItemStack(Items.string), 5, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetDolGuldur), 1, 1).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killDolGuldur").setKillFaction(LOTRFaction.DOL_GULDUR, 10, 30));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityDolGuldurOrc.class, 10, 30));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad").setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.GALADHRIM.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.DUNLAND.setAchievement(LOTRAchievement.doMiniquestDunland);
        LOTRMiniQuestFactory.DUNLAND.setLore(LOTRLore.LoreCategory.DUNLAND);
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectResources").setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectResources").setCollectItem(new ItemStack(Blocks.log, 1, 1), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectResources").setCollectItem(new ItemStack(Items.coal), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectResources").setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectResources").setCollectItem(new ItemStack(Items.leather), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectResources").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugAle), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugMead), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugCider), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugRum), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRohanItem").setCollectItem(new ItemStack(LOTRMod.helmetRohan), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRohanItem").setCollectItem(new ItemStack(LOTRMod.bodyRohan), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRohanItem").setCollectItem(new ItemStack(LOTRMod.swordRohan), 1, 3).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_chicken), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bread), 5, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killRohirrim").setKillFaction(LOTRFaction.ROHAN, 10, 40));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("avengeKin").setKillFaction(LOTRFaction.ROHAN, 30, 60));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killHorse").setKillEntity(LOTREntityHorse.class, 10, 20));
        LOTRMiniQuestFactory.DUNLAND.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.ISENGARD.setAchievement(LOTRAchievement.doMiniquestIsengard);
        LOTRMiniQuestFactory.ISENGARD.setLore(LOTRLore.LoreCategory.ISENGARD);
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.scimitarUruk), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.pikeUruk), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeUruk), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.axeUruk), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.daggerUruk), 1, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bone), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetUruk), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyUruk), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.legsUruk), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bootsUruk), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Items.gold_ingot), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeSteel").setCollectItem(new ItemStack(LOTRMod.urukSteel), 3, 10).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeSteel").setCollectItem(new ItemStack(LOTRMod.orcSteel), 3, 10).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestCollect.QFCollect("forgeSteel").setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.ROHAN, 10, 40));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.GONDOR, 10, 40));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killMen").setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killMen").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.ISENGARD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.ROHAN.setAchievement(LOTRAchievement.doMiniquestRohan);
        LOTRMiniQuestFactory.ROHAN.setLore(LOTRLore.LoreCategory.ROHAN);
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_chicken), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bread), 5, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMead").setCollectItem(new ItemStack(LOTRMod.mugMead), 3, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.log, 1, 1), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.planks, 1, 0), 80, 160).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.planks, 1, 1), 80, 160).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringWeapon").setCollectItem(new ItemStack(LOTRMod.swordRohan), 1, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringWeapon").setCollectItem(new ItemStack(Items.iron_ingot), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("stealUruk").setCollectItem(new ItemStack(LOTRMod.urukTable), 1, 2).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBones").setCollectItem(new ItemStack(LOTRMod.orcBone), 15, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBones").setCollectItem(new ItemStack(LOTRMod.wargBone), 15, 30).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityIsengardSnaga.class, 10, 30));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityUrukHai.class, 10, 30));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityMordorOrc.class, 10, 30));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("avengeRiders").setKillEntity(LOTREntityUrukWarg.class, 10, 20));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killDunland").setKillFaction(LOTRFaction.DUNLAND, 10, 40));
        LOTRMiniQuestFactory.ROHAN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.setAchievement(LOTRAchievement.doMiniquestRohanShieldmaiden);
        LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.setLore(LOTRLore.LoreCategory.ROHAN);
        LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemies").setKillFaction(LOTRFaction.DUNLAND, 5, 20));
        LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemies").setKillFaction(LOTRFaction.URUK_HAI, 5, 20));
        for (final List<LOTRMiniQuest.QuestFactoryBase> qfList : LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.questFactories.values()) {
            for (final LOTRMiniQuest.QuestFactoryBase qf : qfList) {
                qf.setRewardFactor(0.0f);
                qf.setHiring(150.0f);
            }
        }
        LOTRMiniQuestFactory.GONDOR.setAchievement(LOTRAchievement.doMiniquestGondor);
        LOTRMiniQuestFactory.GONDOR.setLore(LOTRLore.LoreCategory.GONDOR);
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(Blocks.planks, 1, 0), 80, 160).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(LOTRMod.rock, 1, 1), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(LOTRMod.brick, 1, 1), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("defences").setCollectItem(new ItemStack(LOTRMod.brick5, 1, 8), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("lebethron").setCollectItem(new ItemStack(LOTRMod.wood2, 1, 0), 10, 30).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectIron").setCollectItem(new ItemStack(Blocks.iron_ore), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectIron").setCollectItem(new ItemStack(Items.iron_ingot), 6, 15).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("blackMarble").setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 25, 35).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.spearGondor), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.pikeGondor), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.helmetGondor), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("forge").setCollectItem(new ItemStack(LOTRMod.bodyGondor), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRohanItem").setCollectItem(new ItemStack(LOTRMod.swordRohan), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRohanItem").setCollectItem(new ItemStack(LOTRMod.helmetRohan), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRohanItem").setCollectItem(new ItemStack(LOTRMod.helmetRohanMarshal), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPipeweed").setCollectItem(new ItemStack(LOTRMod.pipeweedPlant), 2, 4).setRewardFactor(6.0f));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killMordor").setKillFaction(LOTRFaction.MORDOR, 10, 40));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.MORDOR, 30, 40));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.DUNLAND, 30, 40));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.URUK_HAI, 30, 40));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killMordorMany").setKillFaction(LOTRFaction.MORDOR, 60, 90));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killHarad").setKillFaction(LOTRFaction.NEAR_HARAD, 10, 40));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killHarnedor").setKillEntity(LOTREntityHarnedorWarrior.class, 20, 30));
        LOTRMiniQuestFactory.GONDOR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.setAchievement(LOTRAchievement.doMiniquestGondorKillRenegade);
        LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.setBaseSpeechGroup(LOTRMiniQuestFactory.GONDOR);
        LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.setLore(LOTRLore.LoreCategory.GONDOR);
        LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRenegades").setKillEntity(LOTREntityGondorRenegade.class, 2, 6).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.MORDOR.setAchievement(LOTRAchievement.doMiniquestMordor);
        LOTRMiniQuestFactory.MORDOR.setLore(LOTRLore.LoreCategory.MORDOR);
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.scimitarOrc), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.polearmOrc), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.battleaxeOrc), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.axeOrc), 1, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapon").setCollectItem(new ItemStack(LOTRMod.daggerOrc), 1, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_porkchop), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bone), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.helmetOrc), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bodyOrc), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.legsOrc), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectArmour").setCollectItem(new ItemStack(LOTRMod.bootsOrc), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.orcSteel), 3, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.nauriteGem), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.guldurilCrystal), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0f));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.ROHAN, 10, 40));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy").setKillFaction(LOTRFaction.GONDOR, 10, 40));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killMen").setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killMen").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRanger").setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
        LOTRMiniQuestFactory.MORDOR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.DORWINION.setAchievement(LOTRAchievement.doMiniquestDorwinion);
        LOTRMiniQuestFactory.DORWINION.setLore(LOTRLore.LoreCategory.DORWINION);
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBarrel").setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.grapeRed), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.grapeWhite), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.raisins), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.mugRedWine), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.mugRedGrapeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.mugWhiteGrapeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.olive), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("feast").setCollectItem(new ItemStack(Items.cooked_beef), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWater").setCollectItem(new ItemStack(Items.bucket), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWater").setCollectItem(new ItemStack(Items.water_bucket), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectHoe").setCollectItem(new ItemStack(Items.iron_hoe), 1, 3).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectHoe").setCollectItem(new ItemStack(LOTRMod.hoeBronze), 1, 3).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectHoe").setCollectItem(new ItemStack(Items.stone_hoe), 2, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectHoe").setCollectItem(new ItemStack(Items.wooden_hoe), 3, 8).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBonemeal").setCollectItem(new ItemStack(Items.dye, 1, 15), 12, 40).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_sword), 2, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.daggerIron), 2, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.pikeIron), 2, 4).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.helmetDorwinion), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.bodyDorwinion), 2, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.legsDorwinion), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.bootsDorwinion), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestCollect.QFCollect("kineHorn").setCollectItem(new ItemStack(LOTRMod.kineArawHorn), 1, 3).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRabbits").setKillEntity(LOTREntityRabbit.class, 10, 20));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killBirds").setKillEntity(LOTREntityBird.class, 10, 20));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killBandit").setKillEntity(LOTREntityBandit.class, 1, 3).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOddmentCollector").setKillEntity(LOTREntityScrapTrader.class, 1, 2).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.DORWINION.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.DORWINION_ELF.setAchievement(LOTRAchievement.doMiniquestDorwinion);
        LOTRMiniQuestFactory.DORWINION_ELF.setLore(LOTRLore.LoreCategory.DORWINION);
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 2), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(LOTRMod.wood6, 1, 2), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(LOTRMod.wood6, 1, 3), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.grapeRed), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.grapeWhite), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.mugRedWine), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWine").setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBarrel").setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGreenOak").setCollectItem(new ItemStack(LOTRMod.wood7, 1, 1), 20, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGreenOak").setCollectItem(new ItemStack(LOTRMod.leaves7, 1, 1), 40, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGreenOak").setCollectItem(new ItemStack(LOTRMod.sapling7, 1, 1), 5, 10).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("collectGreenOak").setCollectItem(new ItemStack(LOTRMod.planks2, 1, 13), 40, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("brewing").setCollectItem(new ItemStack(LOTRMod.grapeRed), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("brewing").setCollectItem(new ItemStack(LOTRMod.grapeWhite), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("brewing").setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("brewing").setCollectItem(new ItemStack(LOTRMod.mug), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("brewing").setCollectItem(new ItemStack(Items.bucket), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestCollect.QFCollect("brewing").setCollectItem(new ItemStack(Items.water_bucket), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killOrc").setKillEntity(LOTREntityMordorOrc.class, 10, 30));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killWarg").setKillEntity(LOTREntityMordorWarg.class, 10, 30));
        LOTRMiniQuestFactory.DORWINION_ELF.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.RHUN.setAchievement(LOTRAchievement.doMiniquestRhun);
        LOTRMiniQuestFactory.RHUN.setLore(LOTRLore.LoreCategory.RHUN);
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.pomegranate), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.date), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 0), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 1), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 2), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 3), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 4), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringRhunThing").setCollectItem(new ItemStack(LOTRMod.sapling8, 1, 1), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.scimitarOrc), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.spearOrc), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.orcSteel), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.helmetOrc), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.bodyOrc), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.legsOrc), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectOrcItem").setCollectItem(new ItemStack(LOTRMod.bootsOrc), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("goHunting").setCollectItem(new ItemStack(LOTRMod.rabbitRaw), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("goHunting").setCollectItem(new ItemStack(LOTRMod.deerRaw), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("goHunting").setCollectItem(new ItemStack(Items.beef), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("goHunting").setCollectItem(new ItemStack(Items.leather), 8, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("goHunting").setCollectItem(new ItemStack(Items.feather), 8, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("goHunting").setCollectItem(new ItemStack(LOTRMod.kineArawHorn), 1, 2).setRewardFactor(10.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFire").setCollectItem(new ItemStack(LOTRMod.nauriteGem), 4, 8).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFire").setCollectItem(new ItemStack(LOTRMod.sulfur), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFire").setCollectItem(new ItemStack(LOTRMod.saltpeter), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(LOTRMod.oliveBread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(Items.cooked_fished), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRations").setCollectItem(new ItemStack(LOTRMod.raisins), 6, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(LOTRMod.wood8, 1, 1), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(LOTRMod.wood6, 1, 2), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(LOTRMod.brick5, 1, 11), 40, 100).setRewardFactor(0.2f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.cobblestone, 1, 0), 40, 100).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectKineHorn").setCollectItem(new ItemStack(LOTRMod.kineArawHorn), 1, 1).setRewardFactor(30.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDorwinionWine").setCollectItem(new ItemStack(LOTRMod.mugRedWine), 3, 8).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDorwinionWine").setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 3, 8).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringPoison").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestCollect.QFCollect("bringPoison").setCollectItem(new ItemStack(LOTRMod.sulfur), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killThings").setKillEntity(LOTREntityGondorMan.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killThings").setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killThings").setKillEntity(LOTREntityDaleMan.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killThings").setKillEntity(LOTREntityRabbit.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killThings").setKillEntity(LOTREntityDeer.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies").setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies").setKillEntity(LOTREntityDaleSoldier.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies").setKillEntity(LOTREntityDolAmrothSoldier.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies").setKillEntity(LOTREntityLossarnachAxeman.class, 10, 30));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killDale").setKillFaction(LOTRFaction.DALE, 20, 40));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killNorthmen").setKillFaction(LOTRFaction.DALE, 10, 40));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killDwarves").setKillFaction(LOTRFaction.DWARF, 10, 40));
        LOTRMiniQuestFactory.RHUN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.HARNEDOR.setAchievement(LOTRAchievement.doMiniquestNearHarad);
        LOTRMiniQuestFactory.HARNEDOR.setLore(LOTRLore.LoreCategory.HARNEDOR);
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("bringWater").setCollectItem(new ItemStack(Items.water_bucket), 3, 5).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBlackRock").setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 30, 50).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDates").setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("orangeJuice").setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("lemonLiqueur").setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPoison").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRangedWeapon").setCollectItem(new ItemStack(LOTRMod.nearHaradBow), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectRangedWeapon").setCollectItem(new ItemStack(Items.arrow), 20, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectTradeGoods").setCollectItem(new ItemStack(Items.dye, 1, 4), 3, 8).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectTradeGoods").setCollectItem(new ItemStack(LOTRMod.lionFur), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectTradeGoods").setCollectItem(new ItemStack(LOTRMod.doubleFlower, 1, 3), 5, 15).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectTradeGoods").setCollectItem(new ItemStack(LOTRMod.olive), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMarketFood").setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMarketFood").setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMarketFood").setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMarketFood").setCollectItem(new ItemStack(LOTRMod.orange), 4, 8).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectMarketFood").setCollectItem(new ItemStack(LOTRMod.lemon), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(LOTRMod.wood4, 1, 2), 20, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(Blocks.planks, 1, 0), 80, 160).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 80, 160).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(Blocks.sandstone, 1, 1), 15, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("buildMaterials").setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 20, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("specialFood").setCollectItem(new ItemStack(LOTRMod.orange), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("specialFood").setCollectItem(new ItemStack(LOTRMod.lemon), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("specialFood").setCollectItem(new ItemStack(LOTRMod.mango), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("specialFood").setCollectItem(new ItemStack(LOTRMod.banana), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestCollect.QFCollect("specialFood").setCollectItem(new ItemStack(LOTRMod.lionCooked), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("conquerGondor").setKillFaction(LOTRFaction.GONDOR, 20, 50));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGondor").setKillFaction(LOTRFaction.GONDOR, 20, 50));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("avengeRangers").setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldier").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("reclaimHarondor").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRohirrim").setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
        LOTRMiniQuestFactory.HARNEDOR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.NEAR_HARAD.setAchievement(LOTRAchievement.doMiniquestNearHarad);
        LOTRMiniQuestFactory.NEAR_HARAD.setLore(LOTRLore.LoreCategory.SOUTHRON);
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringWater").setCollectItem(new ItemStack(Items.water_bucket), 3, 5).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBlackRock").setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 30, 50).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDates").setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("orangeJuice").setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("lemonLiqueur").setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPoison").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("gulfSword").setCollectItem(new ItemStack(LOTRMod.swordGulfHarad), 1, 1).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("ringTax").setCollectItem(new ItemStack(LOTRMod.goldRing), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("ringTax").setCollectItem(new ItemStack(LOTRMod.silverRing), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("saveFromVenom").setCollectItem(new ItemStack(LOTRMod.pearl), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringReedsRoof").setCollectItem(new ItemStack(LOTRMod.driedReeds), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringReedsRoof").setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringReedsRoof").setCollectItem(new ItemStack(LOTRMod.slabSingleThatch, 1, 1), 20, 40).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("cedarWood").setCollectItem(new ItemStack(LOTRMod.wood4, 1, 2), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("cedarWood").setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 60, 120).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(LOTRMod.brick, 1, 15), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(LOTRMod.brick3, 1, 13), 30, 60).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringMeat").setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 5, 10).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringMeat").setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 10).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringMeat").setCollectItem(new ItemStack(Items.cooked_beef), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringMeat").setCollectItem(new ItemStack(Items.cooked_porkchop), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringMeat").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("bringMeat").setCollectItem(new ItemStack(LOTRMod.kebab), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("lionSteak").setCollectItem(new ItemStack(LOTRMod.lionCooked), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarArmor").setCollectItem(new ItemStack(LOTRMod.helmetUmbar), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarArmor").setCollectItem(new ItemStack(LOTRMod.bodyUmbar), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarArmor").setCollectItem(new ItemStack(LOTRMod.legsUmbar), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarArmor").setCollectItem(new ItemStack(LOTRMod.bootsUmbar), 1, 1).setRewardFactor(15.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("defendCorsair").setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("defendCorsair").setCollectItem(new ItemStack(LOTRMod.spearNearHarad), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("defendCorsair").setCollectItem(new ItemStack(LOTRMod.maceNearHarad), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGondor").setKillFaction(LOTRFaction.GONDOR, 20, 50));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killForUmbar").setKillFaction(LOTRFaction.GONDOR, 10, 40));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killRohirrim").setKillFaction(LOTRFaction.ROHAN, 10, 30));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRangers").setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("greenDemons").setKillEntity(LOTREntityRangerIthilien.class, 10, 20));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldiers").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.NEAR_HARAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.UMBAR.setAchievement(LOTRAchievement.doMiniquestNearHarad);
        LOTRMiniQuestFactory.UMBAR.setLore(LOTRLore.LoreCategory.UMBAR);
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBlackRock").setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 30, 50).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDates").setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("orangeJuice").setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("lemonLiqueur").setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPoison").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("gulfSword").setCollectItem(new ItemStack(LOTRMod.swordGulfHarad), 1, 1).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("ringTax").setCollectItem(new ItemStack(LOTRMod.goldRing), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("ringTax").setCollectItem(new ItemStack(LOTRMod.silverRing), 2, 5).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("saveFromVenom").setCollectItem(new ItemStack(LOTRMod.pearl), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("bringReedsRoof").setCollectItem(new ItemStack(LOTRMod.driedReeds), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("bringReedsRoof").setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("bringReedsRoof").setCollectItem(new ItemStack(LOTRMod.slabSingleThatch, 1, 1), 20, 40).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("cedarWood").setCollectItem(new ItemStack(LOTRMod.wood4, 1, 2), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("cedarWood").setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 60, 120).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(Blocks.stone, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(LOTRMod.brick, 1, 15), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("repairWall").setCollectItem(new ItemStack(LOTRMod.brick6, 1, 6), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("lionSteak").setCollectItem(new ItemStack(LOTRMod.lionCooked), 2, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("defendCorsair").setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("defendCorsair").setCollectItem(new ItemStack(LOTRMod.spearNearHarad), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("defendCorsair").setCollectItem(new ItemStack(LOTRMod.maceNearHarad), 1, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(LOTRMod.helmetGondor), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(LOTRMod.bodyGondor), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(LOTRMod.swordArnor), 1, 1).setRewardFactor(40.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(LOTRMod.helmetArnor), 1, 1).setRewardFactor(40.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(Items.iron_ingot), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(Items.gold_ingot), 3, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarCraft").setCollectItem(new ItemStack(Items.lava_bucket), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("travelSupplies").setCollectItem(new ItemStack(LOTRMod.kebab), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("travelSupplies").setCollectItem(new ItemStack(Items.cooked_beef), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("travelSupplies").setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("travelSupplies").setCollectItem(new ItemStack(LOTRMod.mugAraq), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("travelSupplies").setCollectItem(new ItemStack(LOTRMod.waterskin), 8, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestCollect.QFCollect("findOldDagger").setCollectItem(new ItemStack(LOTRMod.daggerAncientHarad), 1, 2).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGondor").setKillFaction(LOTRFaction.GONDOR, 10, 30));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("revengeGondor").setKillFaction(LOTRFaction.GONDOR, 20, 40));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killForAnadune").setKillEntity(LOTREntityGondorSoldier.class, 20, 50));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRangers").setKillEntity(LOTREntityRangerIthilien.class, 10, 40));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killSwanKnights").setKillEntity(LOTREntitySwanKnight.class, 10, 30));
        LOTRMiniQuestFactory.UMBAR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.CORSAIR.setAchievement(LOTRAchievement.doMiniquestNearHarad);
        LOTRMiniQuestFactory.CORSAIR.setLore(LOTRLore.LoreCategory.UMBAR);
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("whipMaterial").setCollectItem(new ItemStack(Items.string), 5, 12).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("whipMaterial").setCollectItem(new ItemStack(LOTRMod.rope), 5, 12).setRewardFactor(1.1f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("whipMaterial").setCollectItem(new ItemStack(Items.leather), 10, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("scurvy").setCollectItem(new ItemStack(LOTRMod.orange), 6, 12).setRewardFactor(1.75f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("scurvy").setCollectItem(new ItemStack(LOTRMod.lemon), 6, 12).setRewardFactor(1.75f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("scurvy").setCollectItem(new ItemStack(LOTRMod.lemon), 6, 12).setRewardFactor(1.75f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugAraq), 4, 10).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugRum), 4, 10).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugCactusLiqueur), 4, 10).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugCarrotWine), 4, 10).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugBananaBeer), 4, 10).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDrink").setCollectItem(new ItemStack(LOTRMod.mugCornLiquor), 4, 10).setRewardFactor(2.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectChests").setCollectItem(new ItemStack((Block)Blocks.chest), 8, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectChests").setCollectItem(new ItemStack(LOTRMod.chestBasket), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("collectChests").setCollectItem(new ItemStack(LOTRMod.pouch, 1, 0), 3, 5).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("poisonCaptain").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixSails").setCollectItem(new ItemStack(Items.string), 5, 12).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixSails").setCollectItem(new ItemStack(LOTRMod.rope), 5, 12).setRewardFactor(1.1f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixSails").setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixSails").setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixSails").setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixSails").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixShip").setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 60, 120).setRewardFactor(0.2f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixShip").setCollectItem(new ItemStack(LOTRMod.planks3, 1, 3), 60, 120).setRewardFactor(0.2f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestCollect.QFCollect("fixShip").setCollectItem(new ItemStack(LOTRMod.planks2, 1, 11), 60, 120).setRewardFactor(0.2f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondor").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRangers").setKillEntity(LOTREntityRangerIthilien.class, 10, 20).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killTaurethrim").setKillFaction(LOTRFaction.TAUREDAIN, 10, 30));
        LOTRMiniQuestFactory.CORSAIR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.GONDOR_RENEGADE.setAchievement(LOTRAchievement.doMiniquestGondorRenegade);
        LOTRMiniQuestFactory.GONDOR_RENEGADE.setLore(LOTRLore.LoreCategory.UMBAR);
        LOTRMiniQuestFactory.GONDOR_RENEGADE.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldiers").setKillEntity(LOTREntityGondorSoldier.class, 3, 8));
        for (final List<LOTRMiniQuest.QuestFactoryBase> qfList : LOTRMiniQuestFactory.GONDOR_RENEGADE.questFactories.values()) {
            for (final LOTRMiniQuest.QuestFactoryBase qf : qfList) {
                qf.setRewardFactor(0.0f);
                qf.setHiring(50.0f);
            }
        }
        LOTRMiniQuestFactory.NOMAD.setAchievement(LOTRAchievement.doMiniquestNearHarad);
        LOTRMiniQuestFactory.NOMAD.setLore(LOTRLore.LoreCategory.NOMAD);
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDates").setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFruit").setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("orangeJuice").setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("lemonLiqueur").setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPoison").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 14), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 4), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 13), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 11), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 10), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 5), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 4), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 3), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("camelCarpets").setCollectItem(new ItemStack(Blocks.carpet, 1), 8, 15).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("waterskins").setCollectItem(new ItemStack(LOTRMod.waterskin), 8, 16).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("gulfEquipment").setCollectItem(new ItemStack(LOTRMod.swordGulfHarad), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("gulfEquipment").setCollectItem(new ItemStack(LOTRMod.helmetGulfHarad), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("gulfEquipment").setCollectItem(new ItemStack(LOTRMod.bodyGulfHarad), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarEquipment").setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarEquipment").setCollectItem(new ItemStack(LOTRMod.helmetUmbar), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestCollect.QFCollect("umbarEquipment").setCollectItem(new ItemStack(LOTRMod.bodyUmbar), 1, 2).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killScorpions").setKillEntity(LOTREntityDesertScorpion.class, 10, 30));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killManyScorpions").setKillEntity(LOTREntityDesertScorpion.class, 40, 80));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killBandits").setKillEntity(LOTREntityBanditHarad.class, 1, 3).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRangers").setKillEntity(LOTREntityRangerIthilien.class, 10, 20));
        LOTRMiniQuestFactory.NOMAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.GULF_HARAD.setAchievement(LOTRAchievement.doMiniquestNearHarad);
        LOTRMiniQuestFactory.GULF_HARAD.setLore(LOTRLore.LoreCategory.GULF);
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDates").setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("orangeJuice").setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("lemonLiqueur").setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectPoison").setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectIronWeapon").setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 2, 3).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectIronWeapon").setCollectItem(new ItemStack(LOTRMod.spearNearHarad), 2, 3).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectIronWeapon").setCollectItem(new ItemStack(LOTRMod.poleaxeNearHarad), 2, 3).setRewardFactor(6.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectIronWeapon").setCollectItem(new ItemStack(LOTRMod.maceNearHarad), 2, 3).setRewardFactor(6.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("findOldDagger").setCollectItem(new ItemStack(LOTRMod.daggerAncientHarad), 1, 2).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("morwaithStuff").setCollectItem(new ItemStack(LOTRMod.helmetMoredain), 1, 1).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("morwaithStuff").setCollectItem(new ItemStack(LOTRMod.bodyMoredain), 1, 1).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("morwaithStuff").setCollectItem(new ItemStack(LOTRMod.legsMoredain), 1, 1).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("morwaithStuff").setCollectItem(new ItemStack(LOTRMod.bootsMoredain), 1, 1).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("easterlingStuff").setCollectItem(new ItemStack(LOTRMod.helmetRhunGold), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("easterlingStuff").setCollectItem(new ItemStack(LOTRMod.bodyRhunGold), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("easterlingStuff").setCollectItem(new ItemStack(LOTRMod.legsRhunGold), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("easterlingStuff").setCollectItem(new ItemStack(LOTRMod.bootsRhunGold), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBones").setCollectItem(new ItemStack(Items.bone), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.brick, 1, 15), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.brick3, 1, 13), 30, 60).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.wood8, 1, 3), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.planks3, 1, 3), 60, 120).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.wood9, 1, 0), 30, 60).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.planks3, 1, 4), 60, 120).setRewardFactor(0.125f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestCollect.QFCollect("templeBuild").setCollectItem(new ItemStack(LOTRMod.boneBlock, 1, 0), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killTaurethrim").setKillFaction(LOTRFaction.TAUREDAIN, 10, 30));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGondor").setKillFaction(LOTRFaction.GONDOR, 20, 40));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killScorpions").setKillEntity(LOTREntityDesertScorpion.class, 10, 30));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRangers").setKillEntity(LOTREntityRangerIthilien.class, 20, 40));
        LOTRMiniQuestFactory.GULF_HARAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.MOREDAIN.setAchievement(LOTRAchievement.doMiniquestMoredain);
        LOTRMiniQuestFactory.MOREDAIN.setLore(LOTRLore.LoreCategory.FAR_HARAD);
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectLionFur").setCollectItem(new ItemStack(LOTRMod.lionFur), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.lionCooked), 4, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.zebraCooked), 4, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.rhinoCooked), 4, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectFood").setCollectItem(new ItemStack(LOTRMod.yamRoast), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectHide").setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 4, 12).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBananas").setCollectItem(new ItemStack(LOTRMod.banana), 4, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.battleaxeMoredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.daggerMoredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.spearMoredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.swordMoredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.clubMoredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("huntRhino").setCollectItem(new ItemStack(LOTRMod.rhinoHorn), 1, 3).setRewardFactor(8.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDates").setCollectItem(new ItemStack(LOTRMod.date), 3, 5).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warResources").setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 60).setRewardFactor(0.3f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warResources").setCollectItem(new ItemStack(Blocks.log2, 1, 0), 30, 60).setRewardFactor(0.3f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warResources").setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 6, 15).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warResources").setCollectItem(new ItemStack(Items.stick), 80, 200).setRewardFactor(0.05f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("wallMaterials").setCollectItem(new ItemStack(LOTRMod.brick3, 1, 10), 40, 60).setRewardFactor(0.2f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("wallMaterials").setCollectItem(new ItemStack(Blocks.hardened_clay), 20, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("roofMaterials").setCollectItem(new ItemStack(LOTRMod.thatch), 10, 20).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("jungleWood").setCollectItem(new ItemStack(Blocks.log, 1, 3), 40, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("jungleWood").setCollectItem(new ItemStack(LOTRMod.wood6, 1, 0), 40, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGondor").setKillFaction(LOTRFaction.GONDOR, 20, 50));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldier").setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRanger").setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killCrocodile").setKillEntity(LOTREntityCrocodile.class, 10, 20));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killLion").setKillEntity(LOTREntityLion.class, 10, 20));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killLion").setKillEntity(LOTREntityLioness.class, 10, 20));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killTauredain").setKillFaction(LOTRFaction.TAUREDAIN, 20, 50));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killTauredainBlowgunner").setKillEntity(LOTREntityTauredainBlowgunner.class, 10, 30));
        LOTRMiniQuestFactory.MOREDAIN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.TAUREDAIN.setAchievement(LOTRAchievement.doMiniquestTauredain);
        LOTRMiniQuestFactory.TAUREDAIN.setLore(LOTRLore.LoreCategory.FAR_HARAD_JUNGLE);
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.swordTauredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.daggerTauredain), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.daggerTauredainPoisoned), 1, 3).setRewardFactor(6.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.axeTauredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.spearTauredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.hammerTauredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.battleaxeTauredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWeapons").setCollectItem(new ItemStack(LOTRMod.pikeTauredain), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectObsidian").setCollectItem(new ItemStack(LOTRMod.obsidianShard), 10, 30).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectCocoa").setCollectItem(new ItemStack(Items.dye, 1, 3), 8, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.bananaBread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.cornBread), 5, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.banana), 4, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.mango), 4, 6).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(Items.melon), 10, 20).setRewardFactor(0.75f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.melonSoup), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.corn), 6, 12).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("warriorFood").setCollectItem(new ItemStack(LOTRMod.cornCooked), 5, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDarts").setCollectItem(new ItemStack(LOTRMod.tauredainDart), 20, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectDarts").setCollectItem(new ItemStack(LOTRMod.tauredainDartPoisoned), 10, 20).setRewardFactor(1.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("collectBanners").setCollectItem(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.TAUREDAIN.bannerID), 5, 15).setRewardFactor(1.5f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestCollect.QFCollect("amulet").setCollectItem(new ItemStack(LOTRMod.tauredainAmulet), 1, 4).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killMoredain").setKillFaction(LOTRFaction.MOREDAIN, 20, 50));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killMoredainWarrior").setKillEntity(LOTREntityMoredainWarrior.class, 10, 30));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killHalfTrolls").setKillFaction(LOTRFaction.HALF_TROLL, 10, 40));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killNearHaradrim").setKillFaction(LOTRFaction.NEAR_HARAD, 20, 50));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killNearHaradWarrior").setKillEntity(LOTREntityNearHaradrimWarrior.class, 10, 30));
        LOTRMiniQuestFactory.TAUREDAIN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
        LOTRMiniQuestFactory.HALF_TROLL.setAchievement(LOTRAchievement.doMiniquestHalfTroll);
        LOTRMiniQuestFactory.HALF_TROLL.setLore(LOTRLore.LoreCategory.HALF_TROLL);
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.scimitarHalfTroll), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.maceHalfTroll), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.pikeHalfTroll), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.daggerHalfTroll), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.battleaxeHalfTroll), 2, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.helmetHalfTroll), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.bodyHalfTroll), 1, 4).setRewardFactor(5.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.legsHalfTroll), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(LOTRMod.bootsHalfTroll), 1, 4).setRewardFactor(4.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("flesh").setCollectItem(new ItemStack(LOTRMod.lionRaw), 2, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("flesh").setCollectItem(new ItemStack(LOTRMod.zebraRaw), 2, 6).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("flesh").setCollectItem(new ItemStack(LOTRMod.rhinoRaw), 2, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("flesh").setCollectItem(new ItemStack(Items.rotten_flesh), 3, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 60).setRewardFactor(0.3f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log2, 1, 0), 30, 60).setRewardFactor(0.3f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(LOTRMod.wood4, 1, 1), 20, 40).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("manTrophy").setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("manTrophy").setCollectItem(new ItemStack(LOTRMod.gondorBow), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("manTrophy").setCollectItem(new ItemStack(LOTRMod.beacon), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("resources").setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("resources").setCollectItem(new ItemStack(Items.coal), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("resources").setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("resources").setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 10, 30).setRewardFactor(0.5f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("huntItems").setCollectItem(new ItemStack(LOTRMod.lionRaw), 4, 8).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("huntItems").setCollectItem(new ItemStack(LOTRMod.rhinoHorn), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("huntItems").setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 4, 10).setRewardFactor(2.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("huntItems").setCollectItem(new ItemStack(LOTRMod.gemsbokHorn), 3, 6).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("mordorItems").setCollectItem(new ItemStack(LOTRMod.orcSteel), 4, 8).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("mordorItems").setCollectItem(new ItemStack(LOTRMod.scimitarOrc), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("mordorItems").setCollectItem(new ItemStack(LOTRMod.battleaxeOrc), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("mordorItems").setCollectItem(new ItemStack(LOTRMod.hammerOrc), 3, 5).setRewardFactor(3.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("tribeItem").setCollectItem(new ItemStack(LOTRMod.swordTauredain), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestCollect.QFCollect("tribeItem").setCollectItem(new ItemStack(LOTRMod.daggerTauredain), 1, 1).setRewardFactor(20.0f));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killGondor").setKillFaction(LOTRFaction.GONDOR, 20, 50));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldier").setKillEntity(LOTREntityGondorSoldier.class, 20, 40));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRanger").setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestKillEntity.QFKillEntity("killRohirrim").setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestKillFaction.QFKillFaction("killTauredain").setKillFaction(LOTRFaction.TAUREDAIN, 20, 50));
        LOTRMiniQuestFactory.HALF_TROLL.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
    }
    
    public static LOTRMiniQuestFactory forName(final String name) {
        for (final LOTRMiniQuestFactory group : values()) {
            if (group.getBaseName().equals(name)) {
                return group;
            }
        }
        return null;
    }
    
    static {
        LOTRMiniQuestFactory.rand = new Random();
        LOTRMiniQuestFactory.questClassWeights = new HashMap<Class<? extends LOTRMiniQuest>, Integer>();
    }
}
