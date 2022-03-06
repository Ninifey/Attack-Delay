// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.entity.Entity;
import java.util.LinkedHashMap;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.LOTRConfig;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import lotr.common.world.mapgen.tpyr.LOTRMapGenTauredainPyramid;
import lotr.common.world.mapgen.dwarvenmine.LOTRMapGenDwarvenMine;
import lotr.common.world.structure2.LOTRWorldGenTicketBooth;
import lotr.common.world.structure2.LOTRWorldGenHalfTrollWarlordHouse;
import lotr.common.world.structure2.LOTRWorldGenHalfTrollHouse;
import lotr.common.world.structure2.LOTRWorldGenTauredainSmithy;
import lotr.common.world.village.LOTRVillageGenTauredain;
import lotr.common.world.structure2.LOTRWorldGenTauredainVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenTauredainVillageTree;
import lotr.common.world.structure2.LOTRWorldGenTauredainChieftainPyramid;
import lotr.common.world.structure2.LOTRWorldGenTauredainHouseLarge;
import lotr.common.world.structure2.LOTRWorldGenTauredainWatchtower;
import lotr.common.world.structure2.LOTRWorldGenTauredainHouseStilts;
import lotr.common.world.structure2.LOTRWorldGenTauredainHouseSimple;
import lotr.common.world.structure2.LOTRWorldGenTauredainPyramid;
import lotr.common.world.structure2.LOTRWorldGenMoredainHutHunter;
import lotr.common.world.structure2.LOTRWorldGenMoredainHutTrader;
import lotr.common.world.structure2.LOTRWorldGenMoredainHutChieftain;
import lotr.common.world.structure2.LOTRWorldGenMoredainHutVillage;
import lotr.common.world.structure2.LOTRWorldGenGulfPasture;
import lotr.common.world.village.LOTRVillageGenGulfHarad;
import lotr.common.world.structure2.LOTRWorldGenGulfVillageLight;
import lotr.common.world.structure2.LOTRWorldGenGulfVillageSign;
import lotr.common.world.structure2.LOTRWorldGenGulfTavern;
import lotr.common.world.structure2.LOTRWorldGenGulfTower;
import lotr.common.world.structure2.LOTRWorldGenGulfFarm;
import lotr.common.world.structure2.LOTRWorldGenGulfPyramid;
import lotr.common.world.structure2.LOTRWorldGenGulfTotem;
import lotr.common.world.structure2.LOTRWorldGenGulfBazaar;
import lotr.common.world.structure2.LOTRWorldGenGulfSmithy;
import lotr.common.world.structure2.LOTRWorldGenGulfAltar;
import lotr.common.world.structure2.LOTRWorldGenGulfHouse;
import lotr.common.world.structure2.LOTRWorldGenGulfWarCamp;
import lotr.common.world.structure2.LOTRWorldGenNomadBazaarTent;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import lotr.common.world.structure2.LOTRWorldGenNomadWell;
import lotr.common.world.structure2.LOTRWorldGenNomadChieftainTent;
import lotr.common.world.structure2.LOTRWorldGenNomadTentLarge;
import lotr.common.world.structure2.LOTRWorldGenNomadTent;
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import lotr.common.world.structure2.LOTRWorldGenCorsairTent;
import lotr.common.world.structure2.LOTRWorldGenCorsairCove;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownCorner;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownGate;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownFlowers;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownTree;
import lotr.common.world.structure2.LOTRWorldGenUmbarLamp;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortGate;
import lotr.common.world.structure2.LOTRWorldGenUmbarTraining;
import lotr.common.world.structure2.LOTRWorldGenUmbarBarracks;
import lotr.common.world.structure2.LOTRWorldGenUmbarStatue;
import lotr.common.world.village.LOTRVillageGenUmbar;
import lotr.common.world.structure2.LOTRWorldGenUmbarVillageSign;
import lotr.common.world.structure2.LOTRWorldGenUmbarPasture;
import lotr.common.world.structure2.LOTRWorldGenUmbarBazaar;
import lotr.common.world.structure2.LOTRWorldGenUmbarWell;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortress;
import lotr.common.world.structure2.LOTRWorldGenUmbarFarm;
import lotr.common.world.structure2.LOTRWorldGenUmbarStables;
import lotr.common.world.structure2.LOTRWorldGenUmbarMansion;
import lotr.common.world.structure2.LOTRWorldGenUmbarTower;
import lotr.common.world.structure2.LOTRWorldGenUmbarSmithy;
import lotr.common.world.structure2.LOTRWorldGenUmbarTavern;
import lotr.common.world.structure2.LOTRWorldGenUmbarHouse;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercTent;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownCorner;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownGate;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownFlowers;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownTree;
import lotr.common.world.structure2.LOTRWorldGenSouthronLamp;
import lotr.common.world.structure2.LOTRWorldGenSouthronFortGate;
import lotr.common.world.structure2.LOTRWorldGenSouthronTraining;
import lotr.common.world.structure2.LOTRWorldGenSouthronBarracks;
import lotr.common.world.structure2.LOTRWorldGenSouthronStatue;
import lotr.common.world.village.LOTRVillageGenSouthron;
import lotr.common.world.structure2.LOTRWorldGenSouthronVillageSign;
import lotr.common.world.structure2.LOTRWorldGenSouthronPasture;
import lotr.common.world.structure2.LOTRWorldGenSouthronBazaar;
import lotr.common.world.structure2.LOTRWorldGenSouthronWell;
import lotr.common.world.structure2.LOTRWorldGenSouthronFortress;
import lotr.common.world.structure2.LOTRWorldGenSouthronFarm;
import lotr.common.world.structure2.LOTRWorldGenSouthronStables;
import lotr.common.world.structure2.LOTRWorldGenSouthronMansion;
import lotr.common.world.structure2.LOTRWorldGenSouthronTower;
import lotr.common.world.structure2.LOTRWorldGenSouthronSmithy;
import lotr.common.world.structure2.LOTRWorldGenSouthronTavern;
import lotr.common.world.structure2.LOTRWorldGenSouthronHouse;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTavernRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorHouseRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorVillageSign;
import lotr.common.world.structure2.LOTRWorldGenHarnedorStables;
import lotr.common.world.village.LOTRVillageGenHarnedor;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPasture;
import lotr.common.world.structure2.LOTRWorldGenHarnedorFarm;
import lotr.common.world.structure2.LOTRWorldGenNearHaradTent;
import lotr.common.world.structure2.LOTRWorldGenHarnedorFort;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTower;
import lotr.common.world.structure2.LOTRWorldGenHarnedorMarket;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTavern;
import lotr.common.world.structure2.LOTRWorldGenHarnedorSmithy;
import lotr.common.world.structure2.LOTRWorldGenHarnedorHouse;
import lotr.common.world.structure2.LOTRWorldGenHaradRuinedFort;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenHaradPyramid;
import lotr.common.world.village.LOTRVillageGenRhun;
import lotr.common.world.structure2.LOTRWorldGenEasterlingLamp;
import lotr.common.world.structure2.LOTRWorldGenEasterlingGatehouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenEasterlingWell;
import lotr.common.world.structure2.LOTRWorldGenEasterlingVillageSign;
import lotr.common.world.structure2.LOTRWorldGenEasterlingGarden;
import lotr.common.world.structure2.LOTRWorldGenEasterlingStatue;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTavernTown;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTavern;
import lotr.common.world.structure2.LOTRWorldGenEasterlingMarketStall;
import lotr.common.world.structure2.LOTRWorldGenEasterlingSmithy;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTower;
import lotr.common.world.structure2.LOTRWorldGenEasterlingFortress;
import lotr.common.world.structure2.LOTRWorldGenEasterlingLargeTownHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTownHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingStables;
import lotr.common.world.structure2.LOTRWorldGenEasterlingHouse;
import lotr.common.world.structure2.LOTRWorldGenDorwinionBath;
import lotr.common.world.structure2.LOTRWorldGenDorwinionElfHouse;
import lotr.common.world.structure2.LOTRWorldGenDorwinionBrewery;
import lotr.common.world.structure2.LOTRWorldGenDorwinionHouse;
import lotr.common.world.structure2.LOTRWorldGenDorwinionCaptainTent;
import lotr.common.world.structure2.LOTRWorldGenDorwinionTent;
import lotr.common.world.structure2.LOTRWorldGenDorwinionGarden;
import lotr.common.world.structure2.LOTRWorldGenMordorSpiderPit;
import lotr.common.world.structure2.LOTRWorldGenBlackUrukFort;
import lotr.common.world.structure2.LOTRWorldGenMordorCamp;
import lotr.common.world.structure2.LOTRWorldGenMordorWargPit;
import lotr.common.world.structure2.LOTRWorldGenMordorForgeTent;
import lotr.common.world.structure2.LOTRWorldGenMordorTent;
import lotr.common.world.structure2.LOTRWorldGenLamedonWatchfort;
import lotr.common.world.structure2.LOTRWorldGenLamedonWatchtower;
import lotr.common.world.structure2.LOTRWorldGenLamedonFortress;
import lotr.common.world.structure2.LOTRWorldGenBlackrootWatchfort;
import lotr.common.world.structure2.LOTRWorldGenBlackrootWatchtower;
import lotr.common.world.structure2.LOTRWorldGenBlackrootFortress;
import lotr.common.world.structure2.LOTRWorldGenPinnathGelinWatchfort;
import lotr.common.world.structure2.LOTRWorldGenPinnathGelinWatchtower;
import lotr.common.world.structure2.LOTRWorldGenPinnathGelinFortress;
import lotr.common.world.structure2.LOTRWorldGenPelargirWatchfort;
import lotr.common.world.structure2.LOTRWorldGenPelargirWatchtower;
import lotr.common.world.structure2.LOTRWorldGenPelargirFortress;
import lotr.common.world.structure2.LOTRWorldGenLebenninWatchfort;
import lotr.common.world.structure2.LOTRWorldGenLebenninWatchtower;
import lotr.common.world.structure2.LOTRWorldGenLebenninFortress;
import lotr.common.world.structure2.LOTRWorldGenLossarnachWatchfort;
import lotr.common.world.structure2.LOTRWorldGenLossarnachWatchtower;
import lotr.common.world.structure2.LOTRWorldGenLossarnachFortress;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothWatchfort;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothWatchtower;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothStables;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenRuinedBeaconTower;
import lotr.common.world.village.LOTRVillageGenGondor;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;
import lotr.common.world.structure2.LOTRWorldGenGondorTownBench;
import lotr.common.world.structure2.LOTRWorldGenGondorTownTrees;
import lotr.common.world.structure2.LOTRWorldGenGondorTownGarden;
import lotr.common.world.structure2.LOTRWorldGenGondorLampPost;
import lotr.common.world.structure2.LOTRWorldGenGondorGatehouse;
import lotr.common.world.structure2.LOTRWorldGenGondorBath;
import lotr.common.world.structure2.LOTRWorldGenGondorVillageSign;
import lotr.common.world.structure2.LOTRWorldGenGondorMarketStall;
import lotr.common.world.structure2.LOTRWorldGenGondorVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenGondorWell;
import lotr.common.world.structure2.LOTRWorldGenGondorTavern;
import lotr.common.world.structure2.LOTRWorldGenGondorFortress;
import lotr.common.world.structure2.LOTRWorldGenGondorBarn;
import lotr.common.world.structure2.LOTRWorldGenGondorStables;
import lotr.common.world.structure2.LOTRWorldGenGondorWatchtower;
import lotr.common.world.structure2.LOTRWorldGenGondorStoneHouse;
import lotr.common.world.structure2.LOTRWorldGenGondorCottage;
import lotr.common.world.structure2.LOTRWorldGenGondorHouse;
import lotr.common.world.structure2.LOTRWorldGenIthilienHideout;
import lotr.common.world.structure2.LOTRWorldGenGondorTurret;
import lotr.common.world.structure2.LOTRWorldGenGondorSmithy;
import lotr.common.world.structure2.LOTRWorldGenGondorWatchfort;
import lotr.common.world.structure2.LOTRWorldGenBeaconTower;
import lotr.common.world.structure2.LOTRWorldGenDunlandHillFort;
import lotr.common.world.structure2.LOTRWorldGenDunlendingTavern;
import lotr.common.world.structure2.LOTRWorldGenDunlendingHouse;
import lotr.common.world.structure2.LOTRWorldGenUrukCamp;
import lotr.common.world.structure2.LOTRWorldGenUrukWargPit;
import lotr.common.world.structure2.LOTRWorldGenUrukForgeTent;
import lotr.common.world.structure2.LOTRWorldGenUrukTent;
import lotr.common.world.village.LOTRVillageGenRohan;
import lotr.common.world.structure2.LOTRWorldGenRohanGatehouse;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageSign;
import lotr.common.world.structure2.LOTRWorldGenRohanVillagePasture;
import lotr.common.world.structure2.LOTRWorldGenRohanMarketStall;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageGarden;
import lotr.common.world.structure2.LOTRWorldGenRohanWell;
import lotr.common.world.structure2.LOTRWorldGenRohanBarn;
import lotr.common.world.structure2.LOTRWorldGenRohanStables;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenRohanSmithy;
import lotr.common.world.structure2.LOTRWorldGenRohanHouse;
import lotr.common.world.structure2.LOTRWorldGenRohanFortress;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.world.structure2.LOTRWorldGenMeadHall;
import lotr.common.world.structure2.LOTRWorldGenGaladhrimForge;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import lotr.common.world.structure2.LOTRWorldGenRuinedDwarvenTower;
import lotr.common.world.structure2.LOTRWorldGenDwarfSmithy;
import lotr.common.world.structure2.LOTRWorldGenDwarvenMineEntranceRuined;
import lotr.common.world.structure2.LOTRWorldGenDwarfHouse;
import lotr.common.world.structure2.LOTRWorldGenDwarvenTower;
import lotr.common.world.structure2.LOTRWorldGenDwarvenMineEntrance;
import lotr.common.world.structure2.LOTRWorldGenDaleBakery;
import lotr.common.world.structure2.LOTRWorldGenDaleVillageTower;
import lotr.common.world.structure2.LOTRWorldGenDaleSmithy;
import lotr.common.world.structure2.LOTRWorldGenDaleHouse;
import lotr.common.world.structure2.LOTRWorldGenDaleFortress;
import lotr.common.world.structure2.LOTRWorldGenDaleWatchtower;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurCamp;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurForgeTent;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurTent;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurSpiderPit;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurTower;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurAltar;
import lotr.common.world.structure2.LOTRWorldGenWoodElvenForge;
import lotr.common.world.structure2.LOTRWorldGenWoodElfHouse;
import lotr.common.world.structure2.LOTRWorldGenRhudaurCastle;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanChieftainHouse;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanHouse;
import lotr.common.world.structure2.LOTRWorldGenAngmarCamp;
import lotr.common.world.structure2.LOTRWorldGenAngmarForgeTent;
import lotr.common.world.structure2.LOTRWorldGenAngmarTent;
import lotr.common.world.structure2.LOTRWorldGenAngmarWargPit;
import lotr.common.world.structure2.LOTRWorldGenGundabadCamp;
import lotr.common.world.structure2.LOTRWorldGenGundabadForgeTent;
import lotr.common.world.structure2.LOTRWorldGenGundabadTent;
import lotr.common.world.structure2.LOTRWorldGenRangerCamp;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenDunedain;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure2.LOTRWorldGenRangerVillageLight;
import lotr.common.world.structure2.LOTRWorldGenRangerWell;
import lotr.common.world.structure2.LOTRWorldGenRangerSmithy;
import lotr.common.world.structure2.LOTRWorldGenRangerStables;
import lotr.common.world.structure2.LOTRWorldGenRangerLodge;
import lotr.common.world.structure2.LOTRWorldGenRangerHouse;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.structure2.LOTRWorldGenBurntHouse;
import lotr.common.world.structure2.LOTRWorldGenRangerWatchtower;
import lotr.common.world.structure2.LOTRWorldGenBDBarrow;
import lotr.common.world.structure2.LOTRWorldGenNumenorRuin;
import lotr.common.world.structure2.LOTRWorldGenRangerTent;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenRivendellForge;
import lotr.common.world.structure2.LOTRWorldGenRivendellHouse;
import lotr.common.world.structure2.LOTRWorldGenHighElfHouse;
import lotr.common.world.structure2.LOTRWorldGenTowerHillsTower;
import lotr.common.world.structure2.LOTRWorldGenHighElvenTower;
import lotr.common.world.structure2.LOTRWorldGenRuinedEregionForge;
import lotr.common.world.structure2.LOTRWorldGenHighElvenForge;
import lotr.common.world.structure2.LOTRWorldGenBlueMountainsSmithy;
import lotr.common.world.structure2.LOTRWorldGenBlueMountainsHouse;
import lotr.common.world.structure2.LOTRWorldGenHobbitHouse;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenHobbitHole;
import java.util.HashMap;

public class LOTRStructures
{
    private static HashMap<Integer, IStructureProvider> idToClassMapping;
    private static HashMap<Integer, String> idToStringMapping;
    public static HashMap<Integer, StructureColorInfo> structureItemSpawners;
    
    private static void registerStructure(final int id, final IStructureProvider str, final String name, final int colorBG, final int colorFG, final boolean hide) {
        LOTRStructures.idToClassMapping.put(id, str);
        LOTRStructures.idToStringMapping.put(id, name);
        LOTRStructures.structureItemSpawners.put(id, new StructureColorInfo(id, colorBG, colorFG, str.isVillage(), hide));
    }
    
    public static IStructureProvider getStructureForID(final int ID) {
        return LOTRStructures.idToClassMapping.get(ID);
    }
    
    public static String getNameFromID(final int ID) {
        return LOTRStructures.idToStringMapping.get(ID);
    }
    
    public static void registerStructures() {
        registerStructure(1, LOTRWorldGenHobbitHole.class, "HobbitHole", 2727977, 8997164);
        registerStructure(2, LOTRWorldGenHobbitTavern.class, "HobbitTavern", 9324081, 15975807);
        registerStructure(3, LOTRWorldGenHobbitPicnicBench.class, "HobbitPicnicBench", 7032622, 13882323);
        registerStructure(4, LOTRWorldGenHobbitWindmill.class, "HobbitWindmill", 9324081, 15975807);
        registerStructure(5, LOTRWorldGenHobbitFarm.class, "HobbitFarm", 9324081, 15975807);
        registerStructure(6, LOTRWorldGenHayBales.class, "HayBale", 14863437, 11499334);
        registerStructure(7, LOTRWorldGenHobbitHouse.class, "HobbitHouse", 9324081, 15975807);
        registerStructure(20, LOTRWorldGenBlueMountainsHouse.class, "BlueMountainsHouse", 10397380, 7633815);
        registerStructure(21, LOTRWorldGenBlueMountainsStronghold.class, "BlueMountainsStronghold", 10397380, 7633815);
        registerStructure(22, LOTRWorldGenBlueMountainsSmithy.class, "BlueMountainsSmithy", 10397380, 7633815);
        registerStructure(30, LOTRWorldGenHighElvenTurret.class, "HighElvenTurret", 13419962, 11380637);
        registerStructure(31, LOTRWorldGenRuinedHighElvenTurret.class, "RuinedHighElvenTurret", 13419962, 11380637);
        registerStructure(32, LOTRWorldGenHighElvenHall.class, "HighElvenHall", 13419962, 11380637);
        registerStructure(33, LOTRWorldGenUnderwaterElvenRuin.class, "UnderwaterElvenRuin", 13419962, 11380637);
        registerStructure(34, LOTRWorldGenHighElvenForge.class, "HighElvenForge", 13419962, 11380637);
        registerStructure(35, LOTRWorldGenRuinedEregionForge.class, "RuinedEregionForge", 13419962, 11380637);
        registerStructure(36, LOTRWorldGenHighElvenTower.class, "HighElvenTower", 13419962, 11380637);
        registerStructure(37, LOTRWorldGenTowerHillsTower.class, "TowerHillsTower", 16250346, 14211019);
        registerStructure(38, LOTRWorldGenHighElfHouse.class, "HighElfHouse", 13419962, 11380637);
        registerStructure(39, LOTRWorldGenRivendellHouse.class, "RivendellHouse", 13419962, 11380637);
        registerStructure(40, LOTRWorldGenRivendellHall.class, "RivendellHall", 13419962, 11380637);
        registerStructure(41, LOTRWorldGenRivendellForge.class, "RivendellForge", 13419962, 11380637);
        registerStructure(50, LOTRWorldGenRuinedDunedainTower.class, "RuinedDunedainTower", 8947848, 6052956);
        registerStructure(51, LOTRWorldGenRuinedHouse.class, "RuinedHouse", 8355197, 6838845);
        registerStructure(52, LOTRWorldGenRangerTent.class, "RangerTent", 3755037, 4142111);
        registerStructure(53, LOTRWorldGenNumenorRuin.class, "NumenorRuin", 8947848, 6052956);
        registerStructure(54, LOTRWorldGenBDBarrow.class, "BDBarrow", 6586202, 6505786);
        registerStructure(55, LOTRWorldGenRangerWatchtower.class, "RangerWatchtower", 5982252, 13411436);
        registerStructure(56, LOTRWorldGenBurntHouse.class, "BurntHouse", 1117449, 3288357);
        registerStructure(57, LOTRWorldGenRottenHouse.class, "RottenHouse", 3026204, 5854007);
        registerStructure(58, LOTRWorldGenRangerHouse.class, "RangerHouse", 5982252, 13411436);
        registerStructure(59, LOTRWorldGenRangerLodge.class, "RangerLodge", 5982252, 13411436);
        registerStructure(60, LOTRWorldGenRangerStables.class, "RangerStables", 5982252, 13411436);
        registerStructure(61, LOTRWorldGenRangerSmithy.class, "RangerSmithy", 5982252, 13411436);
        registerStructure(62, LOTRWorldGenRangerWell.class, "RangerWell", 5982252, 13411436);
        registerStructure(63, LOTRWorldGenRangerVillageLight.class, "RangerVillageLight", 5982252, 13411436);
        registerVillage(64, new LOTRVillageGenDunedain(LOTRBiome.angle, 1.0f), "DunedainVillage", 5982252, 13411436, new IVillageProperties<LOTRVillageGenDunedain.Instance>() {
            @Override
            public void apply(final LOTRVillageGenDunedain.Instance instance) {
                instance.villageType = LOTRVillageGenDunedain.VillageType.VILLAGE;
            }
        });
        registerStructure(65, LOTRWorldGenRangerCamp.class, "RangerCamp", 3755037, 4142111);
        registerStructure(80, LOTRWorldGenOrcDungeon.class, "OrcDungeon", 8947848, 6052956);
        registerStructure(81, LOTRWorldGenGundabadTent.class, "GundabadTent", 2301210, 131586);
        registerStructure(82, LOTRWorldGenGundabadForgeTent.class, "GundabadForgeTent", 2301210, 131586);
        registerStructure(83, LOTRWorldGenGundabadCamp.class, "GundabadCamp", 2301210, 131586);
        registerStructure(100, LOTRWorldGenAngmarTower.class, "AngmarTower", 3815994, 1644825);
        registerStructure(101, LOTRWorldGenAngmarShrine.class, "AngmarShrine", 3815994, 1644825);
        registerStructure(102, LOTRWorldGenAngmarWargPit.class, "AngmarWargPit", 3815994, 1644825);
        registerStructure(103, LOTRWorldGenAngmarTent.class, "AngmarTent", 2301210, 131586);
        registerStructure(104, LOTRWorldGenAngmarForgeTent.class, "AngmarForgeTent", 3815994, 1644825);
        registerStructure(105, LOTRWorldGenAngmarCamp.class, "AngmarCamp", 2301210, 131586);
        registerStructure(110, LOTRWorldGenAngmarHillmanHouse.class, "AngmarHillmanHouse", 6705465, 3813154);
        registerStructure(111, LOTRWorldGenAngmarHillmanChieftainHouse.class, "AngmarHillmanChieftainHouse", 6705465, 3813154);
        registerStructure(112, LOTRWorldGenRhudaurCastle.class, "RhudaurCastle", 3815994, 1644825);
        registerStructure(120, LOTRWorldGenWoodElfPlatform.class, "WoodElfLookoutPlatform", 2498840, 4932405);
        registerStructure(121, LOTRWorldGenWoodElfHouse.class, "WoodElfHouse", 2498840, 1004574);
        registerStructure(122, LOTRWorldGenWoodElfTower.class, "WoodElfTower", 12692892, 9733494);
        registerStructure(123, LOTRWorldGenRuinedWoodElfTower.class, "RuinedWoodElfTower", 12692892, 9733494);
        registerStructure(124, LOTRWorldGenWoodElvenForge.class, "WoodElvenForge", 12692892, 9733494);
        registerStructure(130, LOTRWorldGenDolGuldurAltar.class, "DolGuldurAltar", 4408654, 2040101);
        registerStructure(131, LOTRWorldGenDolGuldurTower.class, "DolGuldurTower", 4408654, 2040101);
        registerStructure(132, LOTRWorldGenDolGuldurSpiderPit.class, "DolGuldurSpiderPit", 4408654, 2040101);
        registerStructure(133, LOTRWorldGenDolGuldurTent.class, "DolGuldurTent", 2301210, 131586);
        registerStructure(134, LOTRWorldGenDolGuldurForgeTent.class, "DolGuldurForgeTent", 4408654, 2040101);
        registerStructure(135, LOTRWorldGenDolGuldurCamp.class, "DolGuldurCamp", 2301210, 131586);
        registerStructure(140, LOTRWorldGenDaleWatchtower.class, "DaleWatchtower", 13278568, 6836795);
        registerStructure(141, LOTRWorldGenDaleFortress.class, "DaleFortress", 13278568, 6836795);
        registerStructure(142, LOTRWorldGenDaleHouse.class, "DaleHouse", 13278568, 6836795);
        registerStructure(143, LOTRWorldGenDaleSmithy.class, "DaleSmithy", 13278568, 6836795);
        registerStructure(144, LOTRWorldGenDaleVillageTower.class, "DaleVillageTower", 13278568, 6836795);
        registerStructure(145, LOTRWorldGenDaleBakery.class, "DaleBakery", 13278568, 6836795);
        registerStructure(150, LOTRWorldGenDwarvenMineEntrance.class, "DwarvenMineEntrance", 4935761, 2961971);
        registerStructure(151, LOTRWorldGenDwarvenTower.class, "DwarvenTower", 4935761, 2961971);
        registerStructure(152, LOTRWorldGenDwarfHouse.class, "DwarfHouse", 4935761, 2961971);
        registerStructure(153, LOTRWorldGenDwarvenMineEntranceRuined.class, "DwarvenMineEntranceRuined", 4935761, 2961971);
        registerStructure(154, LOTRWorldGenDwarfSmithy.class, "DwarfSmithy", 4935761, 2961971);
        registerStructure(155, LOTRWorldGenRuinedDwarvenTower.class, "DwarvenTowerRuined", 4935761, 2961971);
        registerStructure(200, LOTRWorldGenElfHouse.class, "ElfHouse", 15325615, 2315809);
        registerStructure(201, LOTRWorldGenElfLordHouse.class, "ElfLordHouse", 15325615, 2315809);
        registerStructure(202, LOTRWorldGenGaladhrimForge.class, "GaladhrimForge", 14407118, 10854552);
        registerStructure(300, LOTRWorldGenMeadHall.class, "RohanMeadHall", 5982252, 13411436);
        registerStructure(301, LOTRWorldGenRohanWatchtower.class, "RohanWatchtower", 5982252, 13411436);
        registerStructure(302, LOTRWorldGenRohanBarrow.class, "RohanBarrow", 9016133, 16775901);
        registerStructure(303, LOTRWorldGenRohanFortress.class, "RohanFortress", 5982252, 13411436);
        registerStructure(304, LOTRWorldGenRohanHouse.class, "RohanHouse", 5982252, 13411436);
        registerStructure(305, LOTRWorldGenRohanSmithy.class, "RohanSmithy", 5982252, 13411436);
        registerStructure(306, LOTRWorldGenRohanVillageFarm.class, "RohanVillageFarm", 7648578, 8546111);
        registerStructure(307, LOTRWorldGenRohanStables.class, "RohanStables", 5982252, 13411436);
        registerStructure(308, LOTRWorldGenRohanBarn.class, "RohanBarn", 5982252, 13411436);
        registerStructure(309, LOTRWorldGenRohanWell.class, "RohanWell", 5982252, 13411436);
        registerStructure(310, LOTRWorldGenRohanVillageGarden.class, "RohanVillageGarden", 7648578, 8546111);
        registerStructure(311, LOTRWorldGenRohanMarketStall.Blacksmith.class, "RohanMarketBlacksmith", 2960684, 13411436);
        registerStructure(312, LOTRWorldGenRohanMarketStall.Farmer.class, "RohanMarketFarmer", 15066597, 13411436);
        registerStructure(313, LOTRWorldGenRohanMarketStall.Lumber.class, "RohanMarketLumber", 5981994, 13411436);
        registerStructure(314, LOTRWorldGenRohanMarketStall.Builder.class, "RohanMarketBuilder", 7693401, 13411436);
        registerStructure(315, LOTRWorldGenRohanMarketStall.Brewer.class, "RohanMarketBrewer", 13874218, 13411436);
        registerStructure(316, LOTRWorldGenRohanMarketStall.Butcher.class, "RohanMarketButcher", 16358066, 13411436);
        registerStructure(317, LOTRWorldGenRohanMarketStall.Fish.class, "RohanMarketFish", 9882879, 13411436);
        registerStructure(318, LOTRWorldGenRohanMarketStall.Baker.class, "RohanMarketBaker", 14725995, 13411436);
        registerStructure(319, LOTRWorldGenRohanMarketStall.Orcharder.class, "RohanMarketOrcharder", 9161006, 13411436);
        registerStructure(320, LOTRWorldGenRohanVillagePasture.class, "RohanVillagePasture", 7648578, 8546111);
        registerStructure(321, LOTRWorldGenRohanVillageSign.class, "RohanVillageSign", 5982252, 13411436);
        registerStructure(322, LOTRWorldGenRohanGatehouse.class, "RohanGatehouse", 5982252, 13411436);
        registerVillage(323, new LOTRVillageGenRohan(LOTRBiome.rohan, 1.0f), "RohanVillage", 5982252, 13411436, new IVillageProperties<LOTRVillageGenRohan.Instance>() {
            @Override
            public void apply(final LOTRVillageGenRohan.Instance instance) {
                instance.villageType = LOTRVillageGenRohan.VillageType.VILLAGE;
            }
        });
        registerVillage(324, new LOTRVillageGenRohan(LOTRBiome.rohan, 1.0f), "RohanFortVillage", 5982252, 13411436, new IVillageProperties<LOTRVillageGenRohan.Instance>() {
            @Override
            public void apply(final LOTRVillageGenRohan.Instance instance) {
                instance.villageType = LOTRVillageGenRohan.VillageType.FORT;
            }
        });
        registerStructure(350, LOTRWorldGenUrukTent.class, "UrukTent", 2301210, 131586);
        registerStructure(351, LOTRWorldGenRuinedRohanWatchtower.class, "RuinedRohanWatchtower", 1117449, 3288357);
        registerStructure(352, LOTRWorldGenUrukForgeTent.class, "UrukForgeTent", 3682596, 2038547);
        registerStructure(353, LOTRWorldGenUrukWargPit.class, "UrukWargPit", 3682596, 2038547);
        registerStructure(354, LOTRWorldGenUrukCamp.class, "UrukCamp", 2301210, 131586);
        registerStructure(380, LOTRWorldGenDunlendingHouse.class, "DunlendingHouse", 6705465, 3813154);
        registerStructure(381, LOTRWorldGenDunlendingTavern.class, "DunlendingTavern", 6705465, 3813154);
        registerStructure(382, LOTRWorldGenDunlendingCampfire.class, "DunlendingCampfire", 9539472, 6837299);
        registerStructure(383, LOTRWorldGenDunlandHillFort.class, "DunlandHillFort", 6705465, 3813154);
        registerStructure(400, LOTRWorldGenBeaconTower.class, "BeaconTower", 14869218, 11513775);
        registerStructure(401, LOTRWorldGenGondorWatchfort.class, "GondorWatchfort", 14869218, 2367263);
        registerStructure(402, LOTRWorldGenGondorSmithy.class, "GondorSmithy", 14869218, 2367263);
        registerStructure(403, LOTRWorldGenGondorTurret.class, "GondorTurret", 14869218, 11513775);
        registerStructure(404, LOTRWorldGenIthilienHideout.class, "IthilienHideout", 8882055, 7365464);
        registerStructure(405, LOTRWorldGenGondorHouse.class, "GondorHouse", 14869218, 9861961);
        registerStructure(406, LOTRWorldGenGondorCottage.class, "GondorCottage", 14869218, 9861961);
        registerStructure(407, LOTRWorldGenGondorStoneHouse.class, "GondorStoneHouse", 14869218, 2367263);
        registerStructure(408, LOTRWorldGenGondorWatchtower.class, "GondorWatchtower", 14869218, 11513775);
        registerStructure(409, LOTRWorldGenGondorStables.class, "GondorStables", 14869218, 9861961);
        registerStructure(410, LOTRWorldGenGondorBarn.class, "GondorBarn", 14869218, 9861961);
        registerStructure(411, LOTRWorldGenGondorFortress.class, "GondorFortress", 14869218, 2367263);
        registerStructure(412, LOTRWorldGenGondorTavern.class, "GondorTavern", 14869218, 9861961);
        registerStructure(413, LOTRWorldGenGondorWell.class, "GondorWell", 14869218, 11513775);
        registerStructure(414, LOTRWorldGenGondorVillageFarm.Crops.class, "GondorFarmCrops", 7047232, 15066597);
        registerStructure(415, LOTRWorldGenGondorVillageFarm.Animals.class, "GondorFarmAnimals", 7047232, 15066597);
        registerStructure(416, LOTRWorldGenGondorVillageFarm.Tree.class, "GondorFarmTree", 7047232, 15066597);
        registerStructure(417, LOTRWorldGenGondorMarketStall.Greengrocer.class, "GondorMarketGreengrocer", 8567851, 9861961);
        registerStructure(418, LOTRWorldGenGondorMarketStall.Lumber.class, "GondorMarketLumber", 5981994, 9861961);
        registerStructure(419, LOTRWorldGenGondorMarketStall.Mason.class, "GondorMarketMason", 10526621, 9861961);
        registerStructure(420, LOTRWorldGenGondorMarketStall.Brewer.class, "GondorMarketBrewer", 13874218, 9861961);
        registerStructure(421, LOTRWorldGenGondorMarketStall.Flowers.class, "GondorMarketFlowers", 16243515, 9861961);
        registerStructure(422, LOTRWorldGenGondorMarketStall.Butcher.class, "GondorMarketButcher", 14521508, 9861961);
        registerStructure(423, LOTRWorldGenGondorMarketStall.Fish.class, "GondorMarketFish", 6862591, 9861961);
        registerStructure(424, LOTRWorldGenGondorMarketStall.Farmer.class, "GondorMarketFarmer", 14401433, 9861961);
        registerStructure(425, LOTRWorldGenGondorMarketStall.Blacksmith.class, "GondorMarketBlacksmith", 2960684, 9861961);
        registerStructure(426, LOTRWorldGenGondorMarketStall.Baker.class, "GondorMarketBaker", 13543009, 9861961);
        registerStructure(427, LOTRWorldGenGondorVillageSign.class, "GondorVillageSign", 5982252, 13411436);
        registerStructure(428, LOTRWorldGenGondorBath.class, "GondorBath", 14869218, 2367263);
        registerStructure(429, LOTRWorldGenGondorGatehouse.class, "GondorGatehouse", 14869218, 2367263);
        registerStructure(430, LOTRWorldGenGondorLampPost.class, "GondorLampPost", 14869218, 11513775);
        registerStructure(431, LOTRWorldGenGondorTownGarden.class, "GondorTownGarden", 7047232, 15066597);
        registerStructure(432, LOTRWorldGenGondorTownTrees.class, "GondorTownTrees", 7047232, 15066597);
        registerStructure(433, LOTRWorldGenGondorTownBench.class, "GondorTownBench", 14869218, 11513775);
        registerVillage(434, new LOTRVillageGenGondor(LOTRBiome.gondor, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f), "GondorVillage", 14869218, 2367263, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(435, new LOTRVillageGenGondor(LOTRBiome.gondor, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f), "GondorTown", 14869218, 2367263, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(436, new LOTRVillageGenGondor(LOTRBiome.gondor, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f), "GondorFortVillage", 14869218, 2367263, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(450, LOTRWorldGenRuinedBeaconTower.class, "RuinedBeaconTower", 14869218, 11513775);
        registerStructure(451, LOTRWorldGenRuinedGondorTower.class, "RuinedGondorTower", 14869218, 11513775);
        registerStructure(452, LOTRWorldGenGondorObelisk.class, "GondorObelisk", 14869218, 11513775);
        registerStructure(453, LOTRWorldGenGondorRuin.class, "GondorRuin", 14869218, 11513775);
        registerStructure(500, LOTRWorldGenDolAmrothStables.class, "DolAmrothStables", 15002613, 2709918);
        registerStructure(501, LOTRWorldGenDolAmrothWatchtower.class, "DolAmrothWatchtower", 14869218, 11513775);
        registerStructure(502, LOTRWorldGenDolAmrothWatchfort.class, "DolAmrothWatchfort", 15002613, 2709918);
        registerVillage(503, new LOTRVillageGenGondor(LOTRBiome.dorEnErnil, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 1.0f), "DolAmrothVillage", 15002613, 2709918, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(504, new LOTRVillageGenGondor(LOTRBiome.dorEnErnil, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 1.0f), "DolAmrothTown", 15002613, 2709918, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(505, new LOTRVillageGenGondor(LOTRBiome.dorEnErnil, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 1.0f), "DolAmrothFortVillage", 15002613, 2709918, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(510, LOTRWorldGenLossarnachFortress.class, "LossarnachFortress", 14869218, 15138816);
        registerStructure(511, LOTRWorldGenLossarnachWatchtower.class, "LossarnachWatchtower", 14869218, 11513775);
        registerStructure(512, LOTRWorldGenLossarnachWatchfort.class, "LossarnachWatchfort", 14869218, 15138816);
        registerVillage(513, new LOTRVillageGenGondor(LOTRBiome.lossarnach, LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH, 1.0f), "LossarnachVillage", 14869218, 15138816, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(514, new LOTRVillageGenGondor(LOTRBiome.lossarnach, LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH, 1.0f), "LossarnachTown", 14869218, 15138816, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(515, new LOTRVillageGenGondor(LOTRBiome.lossarnach, LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH, 1.0f), "LossarnachFortVillage", 14869218, 15138816, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(520, LOTRWorldGenLebenninFortress.class, "LebenninFortress", 14869218, 621750);
        registerStructure(521, LOTRWorldGenLebenninWatchtower.class, "LebenninWatchtower", 14869218, 11513775);
        registerStructure(522, LOTRWorldGenLebenninWatchfort.class, "LebenninWatchfort", 14869218, 621750);
        registerVillage(523, new LOTRVillageGenGondor(LOTRBiome.lebennin, LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN, 1.0f), "LebenninVillage", 14869218, 621750, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(524, new LOTRVillageGenGondor(LOTRBiome.lebennin, LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN, 1.0f), "LebenninTown", 14869218, 621750, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(525, new LOTRVillageGenGondor(LOTRBiome.lebennin, LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN, 1.0f), "LebenninFortVillage", 14869218, 621750, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(530, LOTRWorldGenPelargirFortress.class, "PelargirFortress", 14869218, 2917253);
        registerStructure(531, LOTRWorldGenPelargirWatchtower.class, "PelargirWatchtower", 14869218, 11513775);
        registerStructure(532, LOTRWorldGenPelargirWatchfort.class, "PelargirWatchfort", 14869218, 2917253);
        registerVillage(533, new LOTRVillageGenGondor(LOTRBiome.pelargir, LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR, 1.0f), "PelargirVillage", 14869218, 2917253, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(534, new LOTRVillageGenGondor(LOTRBiome.pelargir, LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR, 1.0f), "PelargirTown", 14869218, 2917253, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(535, new LOTRVillageGenGondor(LOTRBiome.pelargir, LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR, 1.0f), "PelargirFortVillage", 14869218, 2917253, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(540, LOTRWorldGenPinnathGelinFortress.class, "PinnathGelinFortress", 14869218, 1401651);
        registerStructure(541, LOTRWorldGenPinnathGelinWatchtower.class, "PinnathGelinWatchtower", 14869218, 11513775);
        registerStructure(542, LOTRWorldGenPinnathGelinWatchfort.class, "PinnathGelinWatchfort", 14869218, 1401651);
        registerVillage(543, new LOTRVillageGenGondor(LOTRBiome.pinnathGelin, LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN, 1.0f), "PinnathGelinVillage", 14869218, 1401651, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(544, new LOTRVillageGenGondor(LOTRBiome.pinnathGelin, LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN, 1.0f), "PinnathGelinTown", 14869218, 1401651, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(545, new LOTRVillageGenGondor(LOTRBiome.pinnathGelin, LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN, 1.0f), "PinnathGelinFortVillage", 14869218, 1401651, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(550, LOTRWorldGenBlackrootFortress.class, "BlackrootFortress", 14869218, 2367263);
        registerStructure(551, LOTRWorldGenBlackrootWatchtower.class, "BlackrootWatchtower", 14869218, 11513775);
        registerStructure(552, LOTRWorldGenBlackrootWatchfort.class, "BlackrootWatchfort", 14869218, 2367263);
        registerVillage(553, new LOTRVillageGenGondor(LOTRBiome.blackrootVale, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 1.0f), "BlackrootVillage", 14869218, 2367263, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(554, new LOTRVillageGenGondor(LOTRBiome.blackrootVale, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 1.0f), "BlackrootTown", 14869218, 2367263, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(555, new LOTRVillageGenGondor(LOTRBiome.blackrootVale, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 1.0f), "BlackrootFortVillage", 14869218, 2367263, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(560, LOTRWorldGenLamedonFortress.class, "LamedonFortress", 14869218, 1784649);
        registerStructure(561, LOTRWorldGenLamedonWatchtower.class, "LamedonWatchtower", 14869218, 11513775);
        registerStructure(562, LOTRWorldGenLamedonWatchfort.class, "LamedonWatchfort", 14869218, 1784649);
        registerVillage(563, new LOTRVillageGenGondor(LOTRBiome.lamedon, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 1.0f), "LamedonVillage", 14869218, 1784649, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE;
            }
        });
        registerVillage(564, new LOTRVillageGenGondor(LOTRBiome.lamedon, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 1.0f), "LamedonTown", 14869218, 1784649, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.TOWN;
            }
        });
        registerVillage(565, new LOTRVillageGenGondor(LOTRBiome.lamedon, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 1.0f), "LamedonFortVillage", 14869218, 1784649, new IVillageProperties<LOTRVillageGenGondor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGondor.Instance instance) {
                instance.villageType = LOTRVillageGenGondor.VillageType.FORT;
            }
        });
        registerStructure(600, LOTRWorldGenMordorTower.class, "MordorTower", 2631720, 328965);
        registerStructure(601, LOTRWorldGenMordorTent.class, "MordorTent", 2301210, 131586);
        registerStructure(602, LOTRWorldGenMordorForgeTent.class, "MordorForgeTent", 2631720, 328965);
        registerStructure(603, LOTRWorldGenMordorWargPit.class, "MordorWargPit", 2631720, 328965);
        registerStructure(604, LOTRWorldGenMordorCamp.class, "MordorCamp", 2301210, 131586);
        registerStructure(605, LOTRWorldGenBlackUrukFort.class, "BlackUrukFort", 2631720, 328965);
        registerStructure(650, LOTRWorldGenNurnWheatFarm.class, "NurnWheatFarm", 4469796, 328965);
        registerStructure(651, LOTRWorldGenOrcSlaverTower.class, "OrcSlaverTower", 1117449, 3288357);
        registerStructure(670, LOTRWorldGenMordorSpiderPit.class, "MordorSpiderPit", 1511181, 12917534);
        registerStructure(700, LOTRWorldGenDorwinionGarden.class, "DorwinionGarden", 16572875, 13418417);
        registerStructure(701, LOTRWorldGenDorwinionTent.class, "DorwinionTent", 6706573, 15058766);
        registerStructure(702, LOTRWorldGenDorwinionCaptainTent.class, "DorwinionCaptainTent", 6706573, 15058766);
        registerStructure(703, LOTRWorldGenDorwinionHouse.class, "DorwinionHouse", 7167128, 15390149);
        registerStructure(704, LOTRWorldGenDorwinionBrewery.class, "DorwinionBrewery", 7167128, 15390149);
        registerStructure(705, LOTRWorldGenDorwinionElfHouse.class, "DorwinionElfHouse", 7167128, 15390149);
        registerStructure(706, LOTRWorldGenDorwinionBath.class, "DorwinionBath", 7167128, 15390149);
        registerStructure(750, LOTRWorldGenEasterlingHouse.class, "EasterlingHouse", 12693373, 7689786);
        registerStructure(751, LOTRWorldGenEasterlingStables.class, "EasterlingStables", 12693373, 7689786);
        registerStructure(752, LOTRWorldGenEasterlingTownHouse.class, "EasterlingTownHouse", 6304287, 12693373);
        registerStructure(753, LOTRWorldGenEasterlingLargeTownHouse.class, "EasterlingLargeTownHouse", 6304287, 12693373);
        registerStructure(754, LOTRWorldGenEasterlingFortress.class, "EasterlingFortress", 6304287, 12693373);
        registerStructure(755, LOTRWorldGenEasterlingTower.class, "EasterlingTower", 6304287, 12693373);
        registerStructure(756, LOTRWorldGenEasterlingSmithy.class, "EasterlingSmithy", 6304287, 12693373);
        registerStructure(757, LOTRWorldGenEasterlingMarketStall.Blacksmith.class, "EasterlingMarketBlacksmith", 2960684, 12693373);
        registerStructure(758, LOTRWorldGenEasterlingMarketStall.Lumber.class, "EasterlingMarketLumber", 5981994, 12693373);
        registerStructure(759, LOTRWorldGenEasterlingMarketStall.Mason.class, "EasterlingMarketMason", 7039594, 12693373);
        registerStructure(760, LOTRWorldGenEasterlingMarketStall.Butcher.class, "EasterlingMarketButcher", 12544103, 12693373);
        registerStructure(761, LOTRWorldGenEasterlingMarketStall.Brewer.class, "EasterlingMarketBrewer", 11891243, 12693373);
        registerStructure(762, LOTRWorldGenEasterlingMarketStall.Fish.class, "EasterlingMarketFish", 4882395, 12693373);
        registerStructure(763, LOTRWorldGenEasterlingMarketStall.Baker.class, "EasterlingMarketBaker", 14725995, 12693373);
        registerStructure(764, LOTRWorldGenEasterlingMarketStall.Hunter.class, "EasterlingMarketHunter", 4471854, 12693373);
        registerStructure(765, LOTRWorldGenEasterlingMarketStall.Farmer.class, "EasterlingMarketFarmer", 8893759, 12693373);
        registerStructure(766, LOTRWorldGenEasterlingMarketStall.Gold.class, "EasterlingMarketGold", 16237060, 12693373);
        registerStructure(767, LOTRWorldGenEasterlingTavern.class, "EasterlingTavern", 12693373, 7689786);
        registerStructure(768, LOTRWorldGenEasterlingTavernTown.class, "EasterlingTavernTown", 6304287, 12693373);
        registerStructure(769, LOTRWorldGenEasterlingStatue.class, "EasterlingStatue", 12693373, 7689786);
        registerStructure(770, LOTRWorldGenEasterlingGarden.class, "EasterlingGarden", 4030994, 12693373);
        registerStructure(771, LOTRWorldGenEasterlingVillageSign.class, "EasterlingVillageSign", 12693373, 7689786);
        registerStructure(772, LOTRWorldGenEasterlingWell.class, "EasterlingWell", 12693373, 7689786);
        registerStructure(773, LOTRWorldGenEasterlingVillageFarm.Crops.class, "EasterlingFarmCrops", 4030994, 12693373);
        registerStructure(774, LOTRWorldGenEasterlingVillageFarm.Animals.class, "EasterlingFarmAnimals", 4030994, 12693373);
        registerStructure(775, LOTRWorldGenEasterlingVillageFarm.Tree.class, "EasterlingFarmTree", 4030994, 12693373);
        registerStructure(776, LOTRWorldGenEasterlingGatehouse.class, "EasterlingGatehouse", 6304287, 12693373);
        registerStructure(777, LOTRWorldGenEasterlingLamp.class, "EasterlingLamp", 6304287, 12693373);
        registerVillage(778, new LOTRVillageGenRhun(LOTRBiome.rhunLand, 1.0f, true), "EasterlingVillage", 6304287, 12693373, new IVillageProperties<LOTRVillageGenRhun.Instance>() {
            @Override
            public void apply(final LOTRVillageGenRhun.Instance instance) {
                instance.villageType = LOTRVillageGenRhun.VillageType.VILLAGE;
            }
        });
        registerVillage(779, new LOTRVillageGenRhun(LOTRBiome.rhunLand, 1.0f, true), "EasterlingTown", 6304287, 12693373, new IVillageProperties<LOTRVillageGenRhun.Instance>() {
            @Override
            public void apply(final LOTRVillageGenRhun.Instance instance) {
                instance.villageType = LOTRVillageGenRhun.VillageType.TOWN;
            }
        });
        registerVillage(780, new LOTRVillageGenRhun(LOTRBiome.rhunLand, 1.0f, true), "EasterlingFortVillage", 6304287, 12693373, new IVillageProperties<LOTRVillageGenRhun.Instance>() {
            @Override
            public void apply(final LOTRVillageGenRhun.Instance instance) {
                instance.villageType = LOTRVillageGenRhun.VillageType.FORT;
            }
        });
        registerStructure(1000, LOTRWorldGenHaradObelisk.class, "HaradObelisk", 10854007, 15590575);
        registerStructure(1001, LOTRWorldGenHaradPyramid.class, "HaradPyramid", 10854007, 15590575);
        registerStructure(1002, LOTRWorldGenMumakSkeleton.class, "MumakSkeleton", 14737111, 16250349);
        registerStructure(1003, LOTRWorldGenHaradRuinedFort.class, "HaradRuinedFort", 10854007, 15590575);
        registerStructure(1050, LOTRWorldGenHarnedorHouse.class, "HarnedorHouse", 4994339, 12814421);
        registerStructure(1051, LOTRWorldGenHarnedorSmithy.class, "HarnedorSmithy", 4994339, 12814421);
        registerStructure(1052, LOTRWorldGenHarnedorTavern.class, "HarnedorTavern", 4994339, 12814421);
        registerStructure(1053, LOTRWorldGenHarnedorMarket.class, "HarnedorMarket", 4994339, 12814421);
        registerStructure(1054, LOTRWorldGenHarnedorTower.class, "HarnedorTower", 4994339, 12814421);
        registerStructure(1055, LOTRWorldGenHarnedorFort.class, "HarnedorFort", 4994339, 12814421);
        registerStructure(1056, LOTRWorldGenNearHaradTent.class, "NearHaradTent", 13519170, 1775897);
        registerStructure(1057, LOTRWorldGenHarnedorFarm.class, "HarnedorFarm", 10073953, 12814421);
        registerStructure(1058, LOTRWorldGenHarnedorPasture.class, "HarnedorPasture", 10073953, 12814421);
        registerVillage(1059, new LOTRVillageGenHarnedor(LOTRBiome.harnedor, 1.0f), "HarnedorVillage", 4994339, 12814421, new IVillageProperties<LOTRVillageGenHarnedor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenHarnedor.Instance instance) {
                instance.villageType = LOTRVillageGenHarnedor.VillageType.VILLAGE;
            }
        });
        registerStructure(1060, LOTRWorldGenHarnedorStables.class, "HarnedorStables", 4994339, 12814421);
        registerStructure(1061, LOTRWorldGenHarnedorVillageSign.class, "HarnedorVillageSign", 4994339, 12814421);
        registerVillage(1062, new LOTRVillageGenHarnedor(LOTRBiome.harnedor, 1.0f), "HarnedorFortVillage", 4994339, 12814421, new IVillageProperties<LOTRVillageGenHarnedor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenHarnedor.Instance instance) {
                instance.villageType = LOTRVillageGenHarnedor.VillageType.FORTRESS;
            }
        });
        registerStructure(1080, LOTRWorldGenHarnedorHouseRuined.class, "HarnedorHouseRuined", 5519919, 10059372);
        registerStructure(1081, LOTRWorldGenHarnedorTavernRuined.class, "HarnedorTavernRuined", 5519919, 10059372);
        registerVillage(1082, new LOTRVillageGenHarnedor(LOTRBiome.harondor, 1.0f).setRuined(), "HarnedorVillageRuined", 5519919, 10059372, new IVillageProperties<LOTRVillageGenHarnedor.Instance>() {
            @Override
            public void apply(final LOTRVillageGenHarnedor.Instance instance) {
                instance.villageType = LOTRVillageGenHarnedor.VillageType.VILLAGE;
            }
        });
        registerStructure(1100, LOTRWorldGenSouthronHouse.class, "SouthronHouse", 15063989, 10052655);
        registerStructure(1101, LOTRWorldGenSouthronTavern.class, "SouthronTavern", 15063989, 10052655);
        registerStructure(1102, LOTRWorldGenSouthronSmithy.class, "SouthronSmithy", 15063989, 10052655);
        registerStructure(1103, LOTRWorldGenSouthronTower.class, "SouthronTower", 15063989, 10052655);
        registerStructure(1104, LOTRWorldGenSouthronMansion.class, "SouthronMansion", 15063989, 10052655);
        registerStructure(1105, LOTRWorldGenSouthronStables.class, "SouthronStables", 15063989, 10052655);
        registerStructure(1106, LOTRWorldGenSouthronFarm.class, "SouthronFarm", 9547581, 10052655);
        registerStructure(1107, LOTRWorldGenSouthronFortress.class, "SouthronFortress", 15063989, 10052655);
        registerStructure(1108, LOTRWorldGenSouthronWell.class, "SouthronWell", 15063989, 10052655);
        registerStructure(1109, LOTRWorldGenSouthronBazaar.class, "SouthronBazaar", 15063989, 10052655);
        registerStructure(1110, LOTRWorldGenSouthronPasture.class, "SouthronPasture", 9547581, 10052655);
        registerStructure(1111, LOTRWorldGenSouthronVillageSign.class, "SouthronVillageSign", 15063989, 10052655);
        registerVillage(1112, new LOTRVillageGenSouthron(LOTRBiome.nearHaradFertile, 1.0f), "SouthronVillage", 15063989, 10052655, new IVillageProperties<LOTRVillageGenSouthron.Instance>() {
            @Override
            public void apply(final LOTRVillageGenSouthron.Instance instance) {
                instance.villageType = LOTRVillageGenSouthron.VillageType.VILLAGE;
            }
        });
        registerStructure(1113, LOTRWorldGenSouthronStatue.class, "SouthronStatue", 15063989, 10052655);
        registerStructure(1114, LOTRWorldGenSouthronBarracks.class, "SouthronBarracks", 15063989, 10052655);
        registerStructure(1115, LOTRWorldGenSouthronTraining.class, "SouthronTraining", 15063989, 10052655);
        registerStructure(1116, LOTRWorldGenSouthronFortGate.class, "SouthronFortGate", 15063989, 10052655);
        registerVillage(1117, new LOTRVillageGenSouthron(LOTRBiome.nearHaradFertile, 1.0f), "SouthronFortVillage", 15063989, 10052655, new IVillageProperties<LOTRVillageGenSouthron.Instance>() {
            @Override
            public void apply(final LOTRVillageGenSouthron.Instance instance) {
                instance.villageType = LOTRVillageGenSouthron.VillageType.FORT;
            }
        });
        registerStructure(1118, LOTRWorldGenSouthronLamp.class, "SouthronLamp", 15063989, 10052655);
        registerStructure(1119, LOTRWorldGenSouthronTownTree.class, "SouthronTownTree", 9547581, 10052655);
        registerStructure(1120, LOTRWorldGenSouthronTownFlowers.class, "SouthronTownFlowers", 9547581, 10052655);
        registerVillage(1121, new LOTRVillageGenSouthron(LOTRBiome.nearHaradFertile, 1.0f), "SouthronTown", 15063989, 10052655, new IVillageProperties<LOTRVillageGenSouthron.Instance>() {
            @Override
            public void apply(final LOTRVillageGenSouthron.Instance instance) {
                instance.villageType = LOTRVillageGenSouthron.VillageType.TOWN;
            }
        });
        registerStructure(1122, LOTRWorldGenSouthronTownGate.class, "SouthronTownGate", 15063989, 10052655);
        registerStructure(1123, LOTRWorldGenSouthronTownCorner.class, "SouthronTownCorner", 15063989, 10052655);
        registerStructure(1140, LOTRWorldGenMoredainMercTent.class, "MoredainMercTent", 12845056, 2949120);
        registerStructure(1141, LOTRWorldGenMoredainMercCamp.class, "MoredainMercCamp", 12845056, 2949120);
        registerStructure(1150, LOTRWorldGenUmbarHouse.class, "UmbarHouse", 14407104, 3354926);
        registerStructure(1151, LOTRWorldGenUmbarTavern.class, "UmbarTavern", 14407104, 3354926);
        registerStructure(1152, LOTRWorldGenUmbarSmithy.class, "UmbarSmithy", 14407104, 3354926);
        registerStructure(1153, LOTRWorldGenUmbarTower.class, "UmbarTower", 14407104, 3354926);
        registerStructure(1154, LOTRWorldGenUmbarMansion.class, "UmbarMansion", 14407104, 3354926);
        registerStructure(1155, LOTRWorldGenUmbarStables.class, "UmbarStables", 14407104, 3354926);
        registerStructure(1156, LOTRWorldGenUmbarFarm.class, "UmbarFarm", 9547581, 3354926);
        registerStructure(1157, LOTRWorldGenUmbarFortress.class, "UmbarFortress", 14407104, 3354926);
        registerStructure(1158, LOTRWorldGenUmbarWell.class, "UmbarWell", 14407104, 3354926);
        registerStructure(1159, LOTRWorldGenUmbarBazaar.class, "UmbarBazaar", 14407104, 3354926);
        registerStructure(1160, LOTRWorldGenUmbarPasture.class, "UmbarPasture", 9547581, 3354926);
        registerStructure(1161, LOTRWorldGenUmbarVillageSign.class, "UmbarVillageSign", 14407104, 3354926);
        registerVillage(1162, new LOTRVillageGenUmbar(LOTRBiome.umbar, 1.0f), "UmbarVillage", 14407104, 3354926, new IVillageProperties<LOTRVillageGenUmbar.InstanceUmbar>() {
            @Override
            public void apply(final LOTRVillageGenUmbar.InstanceUmbar instance) {
                ((LOTRVillageGenSouthron.Instance)instance).villageType = LOTRVillageGenSouthron.VillageType.VILLAGE;
            }
        });
        registerStructure(1163, LOTRWorldGenUmbarStatue.class, "UmbarStatue", 14407104, 3354926);
        registerStructure(1164, LOTRWorldGenUmbarBarracks.class, "UmbarBarracks", 14407104, 3354926);
        registerStructure(1165, LOTRWorldGenUmbarTraining.class, "UmbarTraining", 14407104, 3354926);
        registerStructure(1166, LOTRWorldGenUmbarFortGate.class, "UmbarFortGate", 14407104, 3354926);
        registerVillage(1167, new LOTRVillageGenUmbar(LOTRBiome.umbar, 1.0f), "UmbarFortVillage", 14407104, 3354926, new IVillageProperties<LOTRVillageGenSouthron.Instance>() {
            @Override
            public void apply(final LOTRVillageGenSouthron.Instance instance) {
                instance.villageType = LOTRVillageGenSouthron.VillageType.FORT;
            }
        });
        registerStructure(1168, LOTRWorldGenUmbarLamp.class, "UmbarLamp", 14407104, 3354926);
        registerStructure(1169, LOTRWorldGenUmbarTownTree.class, "UmbarTownTree", 9547581, 3354926);
        registerStructure(1170, LOTRWorldGenUmbarTownFlowers.class, "UmbarTownFlowers", 9547581, 3354926);
        registerVillage(1171, new LOTRVillageGenUmbar(LOTRBiome.umbar, 1.0f), "UmbarTown", 14407104, 3354926, new IVillageProperties<LOTRVillageGenSouthron.Instance>() {
            @Override
            public void apply(final LOTRVillageGenSouthron.Instance instance) {
                instance.villageType = LOTRVillageGenSouthron.VillageType.TOWN;
            }
        });
        registerStructure(1172, LOTRWorldGenUmbarTownGate.class, "UmbarTownGate", 14407104, 3354926);
        registerStructure(1173, LOTRWorldGenUmbarTownCorner.class, "UmbarTownCorner", 14407104, 3354926);
        registerStructure(1180, LOTRWorldGenCorsairCove.class, "CorsairCove", 8355711, 1644825);
        registerStructure(1181, LOTRWorldGenCorsairTent.class, "CorsairTent", 5658198, 657930);
        registerStructure(1182, LOTRWorldGenCorsairCamp.class, "CorsairCamp", 5658198, 657930);
        registerStructure(1200, LOTRWorldGenNomadTent.class, "NomadTent", 16775927, 8345150);
        registerStructure(1201, LOTRWorldGenNomadTentLarge.class, "NomadTentLarge", 16775927, 8345150);
        registerStructure(1202, LOTRWorldGenNomadChieftainTent.class, "NomadChieftainTent", 16775927, 8345150);
        registerStructure(1203, LOTRWorldGenNomadWell.class, "NomadWell", 5478114, 15391151);
        registerVillage(1204, new LOTRVillageGenHaradNomad(LOTRBiome.nearHaradSemiDesert, 1.0f), "NomadVillageSmall", 16775927, 8345150, new IVillageProperties<LOTRVillageGenHaradNomad.Instance>() {
            @Override
            public void apply(final LOTRVillageGenHaradNomad.Instance instance) {
                instance.villageType = LOTRVillageGenHaradNomad.VillageType.SMALL;
            }
        });
        registerVillage(1205, new LOTRVillageGenHaradNomad(LOTRBiome.nearHaradSemiDesert, 1.0f), "NomadVillageBig", 16775927, 8345150, new IVillageProperties<LOTRVillageGenHaradNomad.Instance>() {
            @Override
            public void apply(final LOTRVillageGenHaradNomad.Instance instance) {
                instance.villageType = LOTRVillageGenHaradNomad.VillageType.BIG;
            }
        });
        registerStructure(1206, LOTRWorldGenNomadBazaarTent.class, "NomadBazaarTent", 16775927, 8345150);
        registerStructure(1250, LOTRWorldGenGulfWarCamp.class, "GulfWarCamp", 12849937, 4275226);
        registerStructure(1251, LOTRWorldGenGulfHouse.class, "GulfHouse", 9335899, 5654831);
        registerStructure(1252, LOTRWorldGenGulfAltar.class, "GulfAltar", 12849937, 4275226);
        registerStructure(1253, LOTRWorldGenGulfSmithy.class, "GulfSmithy", 9335899, 5654831);
        registerStructure(1254, LOTRWorldGenGulfBazaar.class, "GulfBazaar", 9335899, 5654831);
        registerStructure(1255, LOTRWorldGenGulfTotem.class, "GulfTotem", 12849937, 4275226);
        registerStructure(1256, LOTRWorldGenGulfPyramid.class, "GulfPyramid", 15721151, 12873038);
        registerStructure(1257, LOTRWorldGenGulfFarm.class, "GulfFarm", 9547581, 12849937);
        registerStructure(1258, LOTRWorldGenGulfTower.class, "GulfTower", 12849937, 4275226);
        registerStructure(1259, LOTRWorldGenGulfTavern.class, "GulfTavern", 9335899, 5654831);
        registerStructure(1260, LOTRWorldGenGulfVillageSign.class, "GulfVillageSign", 14737111, 16250349);
        registerStructure(1261, LOTRWorldGenGulfVillageLight.class, "GulfVillageLight", 14737111, 16250349);
        registerVillage(1262, new LOTRVillageGenGulfHarad(LOTRBiome.gulfHarad, 1.0f), "GulfVillage", 9335899, 5654831, new IVillageProperties<LOTRVillageGenGulfHarad.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGulfHarad.Instance instance) {
                instance.villageType = LOTRVillageGenGulfHarad.VillageType.VILLAGE;
            }
        });
        registerStructure(1263, LOTRWorldGenGulfPasture.class, "GulfPasture", 9547581, 12849937);
        registerVillage(1264, new LOTRVillageGenGulfHarad(LOTRBiome.gulfHarad, 1.0f), "GulfTown", 15721151, 12873038, new IVillageProperties<LOTRVillageGenGulfHarad.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGulfHarad.Instance instance) {
                instance.villageType = LOTRVillageGenGulfHarad.VillageType.TOWN;
            }
        });
        registerVillage(1265, new LOTRVillageGenGulfHarad(LOTRBiome.gulfHarad, 1.0f), "GulfFortVillage", 12849937, 4275226, new IVillageProperties<LOTRVillageGenGulfHarad.Instance>() {
            @Override
            public void apply(final LOTRVillageGenGulfHarad.Instance instance) {
                instance.villageType = LOTRVillageGenGulfHarad.VillageType.FORT;
            }
        });
        registerStructure(1500, LOTRWorldGenMoredainHutVillage.class, "MoredainHutVillage", 8873812, 12891279);
        registerStructure(1501, LOTRWorldGenMoredainHutChieftain.class, "MoredainHutChieftain", 8873812, 12891279);
        registerStructure(1502, LOTRWorldGenMoredainHutTrader.class, "MoredainHutTrader", 8873812, 12891279);
        registerStructure(1503, LOTRWorldGenMoredainHutHunter.class, "MoredainHutHunter", 8873812, 12891279);
        registerStructure(1550, LOTRWorldGenTauredainPyramid.class, "TauredainPyramid", 6513746, 4803646);
        registerStructure(1551, LOTRWorldGenTauredainHouseSimple.class, "TauredainHouseSimple", 4796447, 8021303);
        registerStructure(1552, LOTRWorldGenTauredainHouseStilts.class, "TauredainHouseStilts", 4796447, 8021303);
        registerStructure(1553, LOTRWorldGenTauredainWatchtower.class, "TauredainWatchtower", 4796447, 8021303);
        registerStructure(1554, LOTRWorldGenTauredainHouseLarge.class, "TauredainHouseLarge", 4796447, 14593598);
        registerStructure(1555, LOTRWorldGenTauredainChieftainPyramid.class, "TauredainChieftainPyramid", 6513746, 4803646);
        registerStructure(1556, LOTRWorldGenTauredainVillageTree.class, "TauredainVillageTree", 9285414, 4796447);
        registerStructure(1557, LOTRWorldGenTauredainVillageFarm.class, "TauredainVillageFarm", 9285414, 4796447);
        registerVillage(1558, new LOTRVillageGenTauredain(LOTRBiome.tauredainClearing, 1.0f), "TauredainVillage", 6840658, 5979708, new IVillageProperties<LOTRVillageGenTauredain.Instance>() {
            @Override
            public void apply(final LOTRVillageGenTauredain.Instance instance) {
            }
        });
        registerStructure(1559, LOTRWorldGenTauredainSmithy.class, "TauredainSmithy", 4796447, 8021303);
        registerStructure(1700, LOTRWorldGenHalfTrollHouse.class, "HalfTrollHouse", 10058344, 5325111);
        registerStructure(1701, LOTRWorldGenHalfTrollWarlordHouse.class, "HalfTrollWarlordHouse", 10058344, 5325111);
        registerStructure(1994, LOTRWorldGenTicketBooth.class, "TicketBooth", 15313961, 1118481, true);
        LOTRMapGenDwarvenMine.register();
        LOTRMapGenTauredainPyramid.register();
    }
    
    private static void registerStructure(final int id, final Class<? extends WorldGenerator> strClass, final String name, final int colorBG, final int colorFG) {
        registerStructure(id, strClass, name, colorBG, colorFG, false);
    }
    
    private static void registerStructure(final int id, final Class<? extends WorldGenerator> strClass, final String name, final int colorBG, final int colorFG, final boolean hide) {
        final IStructureProvider strProvider = new IStructureProvider() {
            @Override
            public boolean generateStructure(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k) {
                WorldGenerator generator = null;
                try {
                    generator = strClass.getConstructor(Boolean.TYPE).newInstance(true);
                }
                catch (Exception e) {
                    FMLLog.warning("Failed to build LOTR structure " + strClass.getName(), new Object[0]);
                    e.printStackTrace();
                }
                if (generator != null) {
                    final boolean timelapse = LOTRConfig.strTimelapse;
                    if (generator instanceof LOTRWorldGenStructureBase2) {
                        final LOTRWorldGenStructureBase2 strGen = (LOTRWorldGenStructureBase2)generator;
                        strGen.restrictions = false;
                        strGen.usingPlayer = entityplayer;
                        if (timelapse) {
                            LOTRStructureTimelapse.start(strGen, world, i, j, k);
                            return true;
                        }
                        return strGen.generateWithSetRotation(world, world.rand, i, j, k, strGen.usingPlayerRotation());
                    }
                    else if (generator instanceof LOTRWorldGenStructureBase) {
                        final LOTRWorldGenStructureBase strGen2 = (LOTRWorldGenStructureBase)generator;
                        strGen2.restrictions = false;
                        strGen2.usingPlayer = entityplayer;
                        if (timelapse) {
                            LOTRStructureTimelapse.start(strGen2, world, i, j, k);
                            return true;
                        }
                        return strGen2.generate(world, world.rand, i, j, k);
                    }
                }
                return false;
            }
            
            @Override
            public boolean isVillage() {
                return false;
            }
        };
        registerStructure(id, strProvider, name, colorBG, colorFG, hide);
    }
    
    private static void registerVillage(final int id, final LOTRVillageGen village, final String name, final int colorBG, final int colorFG, final IVillageProperties properties) {
        final IStructureProvider strProvider = new IStructureProvider() {
            @Override
            public boolean generateStructure(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k) {
                final LOTRVillageGen.AbstractInstance instance = village.createAndSetupVillageInstance(world, i, k, world.rand);
                instance.setRotation((LOTRStructures.getRotationFromPlayer(entityplayer) + 2) % 4);
                properties.apply(instance);
                village.generateCompleteVillageInstance(instance, world, i, k);
                return true;
            }
            
            @Override
            public boolean isVillage() {
                return true;
            }
        };
        registerStructure(id, strProvider, name, colorBG, colorFG, false);
    }
    
    public static int getRotationFromPlayer(final EntityPlayer entityplayer) {
        return MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
    }
    
    static {
        LOTRStructures.idToClassMapping = new HashMap<Integer, IStructureProvider>();
        LOTRStructures.idToStringMapping = new HashMap<Integer, String>();
        LOTRStructures.structureItemSpawners = new LinkedHashMap<Integer, StructureColorInfo>();
    }
    
    public static class StructureColorInfo
    {
        public final int spawnedID;
        public final int colorBackground;
        public final int colorForeground;
        public final boolean isVillage;
        public final boolean isHidden;
        
        public StructureColorInfo(final int i, final int colorBG, final int colorFG, final boolean vill, final boolean hide) {
            this.spawnedID = i;
            this.colorBackground = colorBG;
            this.colorForeground = colorFG;
            this.isVillage = vill;
            this.isHidden = hide;
        }
    }
    
    private interface IVillageProperties<V>
    {
        void apply(final V p0);
    }
    
    public interface IStructureProvider
    {
        boolean generateStructure(final World p0, final EntityPlayer p1, final int p2, final int p3, final int p4);
        
        boolean isVillage();
    }
}
