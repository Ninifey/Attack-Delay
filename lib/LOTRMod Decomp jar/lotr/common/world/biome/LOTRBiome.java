// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraftforge.common.util.EnumHelper;
import lotr.common.entity.animal.LOTRAmbientCreature;
import net.minecraft.util.Vec3;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import lotr.common.entity.npc.LOTREntityWickedDwarf;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.block.material.Material;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.World;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.StatCollector;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.entity.passive.EntityBat;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityFish;
import lotr.common.entity.animal.LOTREntityAurochs;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.awt.Color;
import lotr.common.world.spawning.LOTRBiomeInvasionSpawns;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.world.spawning.LOTREventSpawner;
import java.util.List;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.biome.variant.LOTRBiomeVariantList;
import java.util.Random;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import lotr.common.LOTRDimension;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTRBiome extends BiomeGenBase
{
    private static Class[][] correctCreatureTypeParams;
    public static EnumCreatureType creatureType_LOTRAmbient;
    public static LOTRBiome river;
    public static LOTRBiome rohan;
    public static LOTRBiome mistyMountains;
    public static LOTRBiome shire;
    public static LOTRBiome shireWoodlands;
    public static LOTRBiome mordor;
    public static LOTRBiome mordorMountains;
    public static LOTRBiome gondor;
    public static LOTRBiome whiteMountains;
    public static LOTRBiome lothlorien;
    public static LOTRBiome celebrant;
    public static LOTRBiome ironHills;
    public static LOTRBiome deadMarshes;
    public static LOTRBiome trollshaws;
    public static LOTRBiome woodlandRealm;
    public static LOTRBiome mirkwoodCorrupted;
    public static LOTRBiome rohanUrukHighlands;
    public static LOTRBiome emynMuil;
    public static LOTRBiome ithilien;
    public static LOTRBiome pelargir;
    public static LOTRBiome loneLands;
    public static LOTRBiome loneLandsHills;
    public static LOTRBiome dunland;
    public static LOTRBiome fangorn;
    public static LOTRBiome angle;
    public static LOTRBiome ettenmoors;
    public static LOTRBiome oldForest;
    public static LOTRBiome harondor;
    public static LOTRBiome eriador;
    public static LOTRBiome eriadorDowns;
    public static LOTRBiome erynVorn;
    public static LOTRBiome greyMountains;
    public static LOTRBiome midgewater;
    public static LOTRBiome brownLands;
    public static LOTRBiome ocean;
    public static LOTRBiome anduinHills;
    public static LOTRBiome meneltarma;
    public static LOTRBiome gladdenFields;
    public static LOTRBiome lothlorienEdge;
    public static LOTRBiome forodwaith;
    public static LOTRBiome enedwaith;
    public static LOTRBiome angmar;
    public static LOTRBiome eregion;
    public static LOTRBiome lindon;
    public static LOTRBiome lindonWoodlands;
    public static LOTRBiome eastBight;
    public static LOTRBiome blueMountains;
    public static LOTRBiome mirkwoodMountains;
    public static LOTRBiome wilderland;
    public static LOTRBiome dagorlad;
    public static LOTRBiome nurn;
    public static LOTRBiome nurnen;
    public static LOTRBiome nurnMarshes;
    public static LOTRBiome angmarMountains;
    public static LOTRBiome anduinMouth;
    public static LOTRBiome entwashMouth;
    public static LOTRBiome dorEnErnil;
    public static LOTRBiome dorEnErnilHills;
    public static LOTRBiome fangornWasteland;
    public static LOTRBiome rohanWoodlands;
    public static LOTRBiome gondorWoodlands;
    public static LOTRBiome lake;
    public static LOTRBiome lindonCoast;
    public static LOTRBiome barrowDowns;
    public static LOTRBiome longMarshes;
    public static LOTRBiome fangornClearing;
    public static LOTRBiome ithilienHills;
    public static LOTRBiome ithilienWasteland;
    public static LOTRBiome nindalf;
    public static LOTRBiome coldfells;
    public static LOTRBiome nanCurunir;
    public static LOTRBiome adornland;
    public static LOTRBiome whiteDowns;
    public static LOTRBiome swanfleet;
    public static LOTRBiome pelennor;
    public static LOTRBiome minhiriath;
    public static LOTRBiome erebor;
    public static LOTRBiome mirkwoodNorth;
    public static LOTRBiome woodlandRealmHills;
    public static LOTRBiome nanUngol;
    public static LOTRBiome pinnathGelin;
    public static LOTRBiome island;
    public static LOTRBiome forodwaithMountains;
    public static LOTRBiome mistyMountainsFoothills;
    public static LOTRBiome greyMountainsFoothills;
    public static LOTRBiome blueMountainsFoothills;
    public static LOTRBiome tundra;
    public static LOTRBiome taiga;
    public static LOTRBiome breeland;
    public static LOTRBiome chetwood;
    public static LOTRBiome forodwaithGlacier;
    public static LOTRBiome whiteMountainsFoothills;
    public static LOTRBiome beach;
    public static LOTRBiome beachGravel;
    public static LOTRBiome nearHarad;
    public static LOTRBiome farHarad;
    public static LOTRBiome haradMountains;
    public static LOTRBiome umbar;
    public static LOTRBiome farHaradJungle;
    public static LOTRBiome umbarHills;
    public static LOTRBiome nearHaradHills;
    public static LOTRBiome farHaradJungleLake;
    public static LOTRBiome lostladen;
    public static LOTRBiome farHaradForest;
    public static LOTRBiome nearHaradFertile;
    public static LOTRBiome pertorogwaith;
    public static LOTRBiome umbarForest;
    public static LOTRBiome farHaradJungleEdge;
    public static LOTRBiome tauredainClearing;
    public static LOTRBiome gulfHarad;
    public static LOTRBiome dorwinionHills;
    public static LOTRBiome tolfalas;
    public static LOTRBiome lebennin;
    public static LOTRBiome rhun;
    public static LOTRBiome rhunForest;
    public static LOTRBiome redMountains;
    public static LOTRBiome redMountainsFoothills;
    public static LOTRBiome dolGuldur;
    public static LOTRBiome nearHaradSemiDesert;
    public static LOTRBiome farHaradArid;
    public static LOTRBiome farHaradAridHills;
    public static LOTRBiome farHaradSwamp;
    public static LOTRBiome farHaradCloudForest;
    public static LOTRBiome farHaradBushland;
    public static LOTRBiome farHaradBushlandHills;
    public static LOTRBiome farHaradMangrove;
    public static LOTRBiome nearHaradFertileForest;
    public static LOTRBiome anduinVale;
    public static LOTRBiome wold;
    public static LOTRBiome shireMoors;
    public static LOTRBiome shireMarshes;
    public static LOTRBiome nearHaradRedDesert;
    public static LOTRBiome farHaradVolcano;
    public static LOTRBiome udun;
    public static LOTRBiome gorgoroth;
    public static LOTRBiome morgulVale;
    public static LOTRBiome easternDesolation;
    public static LOTRBiome dale;
    public static LOTRBiome dorwinion;
    public static LOTRBiome towerHills;
    public static LOTRBiome gulfHaradForest;
    public static LOTRBiome wilderlandNorth;
    public static LOTRBiome forodwaithCoast;
    public static LOTRBiome farHaradCoast;
    public static LOTRBiome nearHaradRiverbank;
    public static LOTRBiome lossarnach;
    public static LOTRBiome imlothMelui;
    public static LOTRBiome nearHaradOasis;
    public static LOTRBiome beachWhite;
    public static LOTRBiome harnedor;
    public static LOTRBiome lamedon;
    public static LOTRBiome lamedonHills;
    public static LOTRBiome blackrootVale;
    public static LOTRBiome andrast;
    public static LOTRBiome pukel;
    public static LOTRBiome rhunLand;
    public static LOTRBiome rhunLandSteppe;
    public static LOTRBiome rhunLandHills;
    public static LOTRBiome rhunRedForest;
    public static LOTRBiome rhunIsland;
    public static LOTRBiome rhunIslandForest;
    public static LOTRBiome lastDesert;
    public static LOTRBiome windMountains;
    public static LOTRBiome windMountainsFoothills;
    public static LOTRBiome rivendell;
    public static LOTRBiome rivendellHills;
    public static LOTRBiome farHaradJungleMountains;
    public static LOTRBiome halfTrollForest;
    public static LOTRBiome farHaradKanuka;
    public static LOTRBiome utumno;
    public LOTRDimension biomeDimension;
    public LOTRBiomeDecorator decorator;
    public int topBlockMeta;
    public int fillerBlockMeta;
    public float heightBaseParameter;
    public static NoiseGeneratorPerlin biomeTerrainNoise;
    protected static Random terrainRand;
    protected boolean enablePodzol;
    protected boolean enableRocky;
    private LOTRBiomeVariantList biomeVariantsSmall;
    private LOTRBiomeVariantList biomeVariantsLarge;
    private static final float defaultVariantChance = 0.4f;
    public float variantChance;
    public LOTRBiomeSpawnList npcSpawnList;
    protected List spawnableLOTRAmbientList;
    private List spawnableTraders;
    private LOTREventSpawner.EventChance banditChance;
    private Class<? extends LOTREntityBandit> banditEntityClass;
    public final LOTRBiomeInvasionSpawns invasionSpawns;
    public BiomeColors biomeColors;
    public BiomeTerrain biomeTerrain;
    private boolean initDwarven;
    private boolean isDwarven;
    private static Color waterColorNorth;
    private static Color waterColorSouth;
    private static int waterLimitNorth;
    private static int waterLimitSouth;
    
    public static void initBiomes() {
        LOTRBiome.river = new LOTRBiomeGenRiver(0, false).setMinMaxHeight(-0.5f, 0.0f).setColor(3570869).setBiomeName("river");
        LOTRBiome.rohan = new LOTRBiomeGenRohan(1, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.2f, 0.15f).setColor(7384389).setBiomeName("rohan");
        LOTRBiome.mistyMountains = new LOTRBiomeGenMistyMountains(2, true).setTemperatureRainfall(0.2f, 0.5f).setMinMaxHeight(2.0f, 2.0f).setColor(15263713).setBiomeName("mistyMountains");
        LOTRBiome.shire = new LOTRBiomeGenShire(3, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.15f, 0.3f).setColor(6794549).setBiomeName("shire");
        LOTRBiome.shireWoodlands = new LOTRBiomeGenShireWoodlands(4, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.3f, 0.5f).setColor(4486966).setBiomeName("shireWoodlands");
        LOTRBiome.mordor = new LOTRBiomeGenMordor(5, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.3f, 0.5f).setColor(1118222).setBiomeName("mordor");
        LOTRBiome.mordorMountains = new LOTRBiomeGenMordorMountains(6, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(2.0f, 3.0f).setColor(5328200).setBiomeName("mordorMountains");
        LOTRBiome.gondor = new LOTRBiomeGenGondor(7, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.1f, 0.15f).setColor(8959045).setBiomeName("gondor");
        LOTRBiome.whiteMountains = new LOTRBiomeGenWhiteMountains(8, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(1.5f, 2.0f).setColor(15066600).setBiomeName("whiteMountains");
        LOTRBiome.lothlorien = new LOTRBiomeGenLothlorien(9, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.3f).setColor(16504895).setBiomeName("lothlorien");
        LOTRBiome.celebrant = new LOTRBiomeGenCelebrant(10, true).setTemperatureRainfall(1.1f, 1.1f).setMinMaxHeight(0.1f, 0.05f).setColor(7647046).setBiomeName("celebrant");
        LOTRBiome.ironHills = new LOTRBiomeGenIronHills(11, true).setTemperatureRainfall(0.27f, 0.4f).setMinMaxHeight(0.3f, 1.4f).setColor(9142093).setBiomeName("ironHills");
        LOTRBiome.deadMarshes = new LOTRBiomeGenDeadMarshes(12, true).setTemperatureRainfall(0.4f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(7303999).setBiomeName("deadMarshes");
        LOTRBiome.trollshaws = new LOTRBiomeGenTrollshaws(13, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.15f, 1.0f).setColor(5798959).setBiomeName("trollshaws");
        LOTRBiome.woodlandRealm = new LOTRBiomeGenWoodlandRealm(14, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.3f).setColor(4089126).setBiomeName("woodlandRealm");
        LOTRBiome.mirkwoodCorrupted = new LOTRBiomeGenMirkwoodCorrupted(15, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.2f, 0.4f).setColor(3032091).setBiomeName("mirkwoodCorrupted");
        LOTRBiome.rohanUrukHighlands = new LOTRBiomeGenRohanUruk(16, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.8f, 0.3f).setColor(8295258).setBiomeName("rohanUrukHighlands");
        LOTRBiome.emynMuil = new LOTRBiomeGenEmynMuil(17, true).setTemperatureRainfall(0.5f, 0.9f).setMinMaxHeight(0.2f, 0.8f).setColor(9866354).setBiomeName("emynMuil");
        LOTRBiome.ithilien = new LOTRBiomeGenIthilien(18, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.15f, 0.5f).setColor(7710516).setBiomeName("ithilien");
        LOTRBiome.pelargir = new LOTRBiomeGenPelargir(19, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.08f, 0.2f).setColor(11256145).setBiomeName("pelargir");
        LOTRBiome.loneLands = new LOTRBiomeGenLoneLands(21, true).setTemperatureRainfall(0.6f, 0.5f).setMinMaxHeight(0.15f, 0.4f).setColor(8562762).setBiomeName("loneLands");
        LOTRBiome.loneLandsHills = new LOTRBiomeGenLoneLandsHills(22, false).setTemperatureRainfall(0.6f, 0.5f).setMinMaxHeight(0.6f, 0.8f).setColor(8687182).setBiomeName("loneLandsHills");
        LOTRBiome.dunland = new LOTRBiomeGenDunland(23, true).setTemperatureRainfall(0.4f, 0.7f).setMinMaxHeight(0.3f, 0.5f).setColor(6920524).setBiomeName("dunland");
        LOTRBiome.fangorn = new LOTRBiomeGenFangorn(24, true).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(0.2f, 0.4f).setColor(4355353).setBiomeName("fangorn");
        LOTRBiome.angle = new LOTRBiomeGenAngle(25, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.15f, 0.3f).setColor(9416527).setBiomeName("angle");
        LOTRBiome.ettenmoors = new LOTRBiomeGenEttenmoors(26, true).setTemperatureRainfall(0.2f, 0.6f).setMinMaxHeight(0.5f, 0.6f).setColor(8161626).setBiomeName("ettenmoors");
        LOTRBiome.oldForest = new LOTRBiomeGenOldForest(27, true).setTemperatureRainfall(0.5f, 1.0f).setMinMaxHeight(0.2f, 0.3f).setColor(4551995).setBiomeName("oldForest");
        LOTRBiome.harondor = new LOTRBiomeGenHarondor(28, true).setTemperatureRainfall(1.0f, 0.6f).setMinMaxHeight(0.2f, 0.3f).setColor(10663238).setBiomeName("harondor");
        LOTRBiome.eriador = new LOTRBiomeGenEriador(29, true).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.1f, 0.4f).setColor(7054916).setBiomeName("eriador");
        LOTRBiome.eriadorDowns = new LOTRBiomeGenEriadorDowns(30, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.5f, 0.5f).setColor(7638087).setBiomeName("eriadorDowns");
        LOTRBiome.erynVorn = new LOTRBiomeGenErynVorn(31, false).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.1f, 0.4f).setColor(4357965).setBiomeName("erynVorn");
        LOTRBiome.greyMountains = new LOTRBiomeGenGreyMountains(32, true).setTemperatureRainfall(0.28f, 0.2f).setMinMaxHeight(1.8f, 2.0f).setColor(13290689).setBiomeName("greyMountains");
        LOTRBiome.midgewater = new LOTRBiomeGenMidgewater(33, true).setTemperatureRainfall(0.6f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(6001495).setBiomeName("midgewater");
        LOTRBiome.brownLands = new LOTRBiomeGenBrownLands(34, true).setTemperatureRainfall(1.0f, 0.2f).setMinMaxHeight(0.2f, 0.2f).setColor(8552016).setBiomeName("brownLands");
        LOTRBiome.ocean = new LOTRBiomeGenOcean(35, false).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(-1.0f, 0.3f).setColor(153997).setBiomeName("ocean");
        LOTRBiome.anduinHills = new LOTRBiomeGenAnduin(36, true).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.6f, 0.4f).setColor(7058012).setBiomeName("anduinHills");
        LOTRBiome.meneltarma = new LOTRBiomeGenMeneltarma(37, false).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.1f, 0.2f).setColor(9549658).setBiomeName("meneltarma");
        LOTRBiome.gladdenFields = new LOTRBiomeGenGladdenFields(38, true).setTemperatureRainfall(0.6f, 1.2f).setMinMaxHeight(0.0f, 0.1f).setColor(5020505).setBiomeName("gladdenFields");
        LOTRBiome.lothlorienEdge = new LOTRBiomeGenLothlorienEdge(39, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.2f).setColor(13944387).setBiomeName("lothlorienEdge");
        LOTRBiome.forodwaith = new LOTRBiomeGenForodwaith(40, true).setTemperatureRainfall(0.0f, 0.2f).setMinMaxHeight(0.1f, 0.1f).setColor(14211282).setBiomeName("forodwaith");
        LOTRBiome.enedwaith = new LOTRBiomeGenEnedwaith(41, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.2f, 0.3f).setColor(8038479).setBiomeName("enedwaith");
        LOTRBiome.angmar = new LOTRBiomeGenAngmar(42, true).setTemperatureRainfall(0.2f, 0.2f).setMinMaxHeight(0.2f, 0.6f).setColor(5523247).setBiomeName("angmar");
        LOTRBiome.eregion = new LOTRBiomeGenEregion(43, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.2f, 0.3f).setColor(6656072).setBiomeName("eregion");
        LOTRBiome.lindon = new LOTRBiomeGenLindon(44, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.15f, 0.2f).setColor(7646533).setBiomeName("lindon");
        LOTRBiome.lindonWoodlands = new LOTRBiomeGenLindonWoodlands(45, false).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.2f, 0.5f).setColor(1996591).setBiomeName("lindonWoodlands");
        LOTRBiome.eastBight = new LOTRBiomeGenEastBight(46, true).setTemperatureRainfall(0.8f, 0.3f).setMinMaxHeight(0.15f, 0.05f).setColor(9082205).setBiomeName("eastBight");
        LOTRBiome.blueMountains = new LOTRBiomeGenBlueMountains(47, true).setTemperatureRainfall(0.22f, 0.8f).setMinMaxHeight(1.0f, 2.5f).setColor(13228770).setBiomeName("blueMountains");
        LOTRBiome.mirkwoodMountains = new LOTRBiomeGenMirkwoodMountains(48, true).setTemperatureRainfall(0.28f, 0.9f).setMinMaxHeight(1.2f, 1.5f).setColor(2632989).setBiomeName("mirkwoodMountains");
        LOTRBiome.wilderland = new LOTRBiomeGenWilderland(49, true).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(0.2f, 0.4f).setColor(9612368).setBiomeName("wilderland");
        LOTRBiome.dagorlad = new LOTRBiomeGenDagorlad(50, true).setTemperatureRainfall(1.0f, 0.2f).setMinMaxHeight(0.1f, 0.05f).setColor(7036741).setBiomeName("dagorlad");
        LOTRBiome.nurn = new LOTRBiomeGenNurn(51, true).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(0.1f, 0.2f).setColor(2630683).setBiomeName("nurn");
        LOTRBiome.nurnen = new LOTRBiomeGenNurnen(52, false).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(-1.0f, 0.3f).setColor(931414).setBiomeName("nurnen");
        LOTRBiome.nurnMarshes = new LOTRBiomeGenNurnMarshes(53, true).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(0.0f, 0.1f).setColor(4012843).setBiomeName("nurnMarshes");
        LOTRBiome.adornland = new LOTRBiomeGenAdornland(54, true).setTemperatureRainfall(0.7f, 0.6f).setMinMaxHeight(0.2f, 0.2f).setColor(7838543).setBiomeName("adornland");
        LOTRBiome.angmarMountains = new LOTRBiomeGenAngmarMountains(55, true).setTemperatureRainfall(0.25f, 0.1f).setMinMaxHeight(1.6f, 1.5f).setColor(13619147).setBiomeName("angmarMountains");
        LOTRBiome.anduinMouth = new LOTRBiomeGenAnduinMouth(56, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(5089363).setBiomeName("anduinMouth");
        LOTRBiome.entwashMouth = new LOTRBiomeGenEntwashMouth(57, true).setTemperatureRainfall(1.0f, 0.5f).setMinMaxHeight(0.0f, 0.1f).setColor(5612358).setBiomeName("entwashMouth");
        LOTRBiome.dorEnErnil = new LOTRBiomeGenDorEnErnil(58, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.07f, 0.2f).setColor(9355077).setBiomeName("dorEnErnil");
        LOTRBiome.dorEnErnilHills = new LOTRBiomeGenDorEnErnilHills(59, false).setTemperatureRainfall(0.8f, 0.7f).setMinMaxHeight(0.5f, 0.5f).setColor(8560707).setBiomeName("dorEnErnilHills");
        LOTRBiome.fangornWasteland = new LOTRBiomeGenFangornWasteland(60, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.2f, 0.4f).setColor(6782028).setBiomeName("fangornWasteland");
        LOTRBiome.rohanWoodlands = new LOTRBiomeGenRohanWoodlands(61, false).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.2f, 0.4f).setColor(5736246).setBiomeName("rohanWoodlands");
        LOTRBiome.gondorWoodlands = new LOTRBiomeGenGondorWoodlands(62, false).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.2f).setColor(5867307).setBiomeName("gondorWoodlands");
        LOTRBiome.lake = new LOTRBiomeGenLake(63, false).setColor(3433630).setBiomeName("lake");
        LOTRBiome.lindonCoast = new LOTRBiomeGenLindonCoast(64, false).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.0f, 0.5f).setColor(9278870).setBiomeName("lindonCoast");
        LOTRBiome.barrowDowns = new LOTRBiomeGenBarrowDowns(65, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.3f, 0.4f).setColor(8097362).setBiomeName("barrowDowns");
        LOTRBiome.longMarshes = new LOTRBiomeGenLongMarshes(66, true).setTemperatureRainfall(0.6f, 0.9f).setMinMaxHeight(0.0f, 0.1f).setColor(7178054).setBiomeName("longMarshes");
        LOTRBiome.fangornClearing = new LOTRBiomeGenFangornClearing(67, false).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(0.2f, 0.1f).setColor(5877050).setBiomeName("fangornClearing");
        LOTRBiome.ithilienHills = new LOTRBiomeGenIthilienHills(68, false).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.6f, 0.6f).setColor(6985792).setBiomeName("ithilienHills");
        LOTRBiome.ithilienWasteland = new LOTRBiomeGenIthilienWasteland(69, true).setTemperatureRainfall(0.6f, 0.6f).setMinMaxHeight(0.15f, 0.2f).setColor(8030031).setBiomeName("ithilienWasteland");
        LOTRBiome.nindalf = new LOTRBiomeGenNindalf(70, true).setTemperatureRainfall(0.4f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(7111750).setBiomeName("nindalf");
        LOTRBiome.coldfells = new LOTRBiomeGenColdfells(71, true).setTemperatureRainfall(0.25f, 0.8f).setMinMaxHeight(0.4f, 0.8f).setColor(8296018).setBiomeName("coldfells");
        LOTRBiome.nanCurunir = new LOTRBiomeGenNanCurunir(72, true).setTemperatureRainfall(0.6f, 0.4f).setMinMaxHeight(0.2f, 0.1f).setColor(7109714).setBiomeName("nanCurunir");
        LOTRBiome.whiteDowns = new LOTRBiomeGenWhiteDowns(74, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.6f, 0.6f).setColor(10210937).setBiomeName("whiteDowns");
        LOTRBiome.swanfleet = new LOTRBiomeGenSwanfleet(75, true).setTemperatureRainfall(0.8f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(6265945).setBiomeName("swanfleet");
        LOTRBiome.pelennor = new LOTRBiomeGenPelennor(76, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.1f, 0.02f).setColor(11258955).setBiomeName("pelennor");
        LOTRBiome.minhiriath = new LOTRBiomeGenMinhiriath(77, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.1f, 0.2f).setColor(7380550).setBiomeName("minhiriath");
        LOTRBiome.erebor = new LOTRBiomeGenErebor(78, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.4f, 0.6f).setColor(7499093).setBiomeName("erebor");
        LOTRBiome.mirkwoodNorth = new LOTRBiomeGenMirkwoodNorth(79, true).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.2f, 0.4f).setColor(3822115).setBiomeName("mirkwoodNorth");
        LOTRBiome.woodlandRealmHills = new LOTRBiomeGenWoodlandRealmHills(80, false).setTemperatureRainfall(0.8f, 0.6f).setMinMaxHeight(0.9f, 0.7f).setColor(3624991).setBiomeName("woodlandRealmHills");
        LOTRBiome.nanUngol = new LOTRBiomeGenNanUngol(81, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.1f, 0.4f).setColor(656641).setBiomeName("nanUngol");
        LOTRBiome.pinnathGelin = new LOTRBiomeGenPinnathGelin(82, false).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.5f, 0.5f).setColor(9946693).setBiomeName("pinnathGelin");
        LOTRBiome.island = new LOTRBiomeGenOcean(83, false).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.0f, 0.3f).setColor(10138963).setBiomeName("island");
        LOTRBiome.forodwaithMountains = new LOTRBiomeGenForodwaithMountains(84, true).setTemperatureRainfall(0.0f, 0.2f).setMinMaxHeight(2.0f, 2.0f).setColor(15592942).setBiomeName("forodwaithMountains");
        LOTRBiome.mistyMountainsFoothills = new LOTRBiomeGenMistyMountainsFoothills(85, true).setTemperatureRainfall(0.25f, 0.6f).setMinMaxHeight(0.7f, 0.9f).setColor(12501430).setBiomeName("mistyMountainsFoothills");
        LOTRBiome.greyMountainsFoothills = new LOTRBiomeGenGreyMountainsFoothills(86, true).setTemperatureRainfall(0.5f, 0.7f).setMinMaxHeight(0.5f, 0.9f).setColor(9148000).setBiomeName("greyMountainsFoothills");
        LOTRBiome.blueMountainsFoothills = new LOTRBiomeGenBlueMountainsFoothills(87, true).setTemperatureRainfall(0.5f, 0.8f).setMinMaxHeight(0.5f, 0.9f).setColor(11253170).setBiomeName("blueMountainsFoothills");
        LOTRBiome.tundra = new LOTRBiomeGenTundra(88, true).setTemperatureRainfall(0.1f, 0.3f).setMinMaxHeight(0.1f, 0.2f).setColor(12366486).setBiomeName("tundra");
        LOTRBiome.taiga = new LOTRBiomeGenTaiga(89, true).setTemperatureRainfall(0.1f, 0.7f).setMinMaxHeight(0.1f, 0.5f).setColor(6526543).setBiomeName("taiga");
        LOTRBiome.breeland = new LOTRBiomeGenBreeland(90, true).setTemperatureRainfall(0.8f, 0.7f).setMinMaxHeight(0.1f, 0.2f).setColor(6861625).setBiomeName("breeland");
        LOTRBiome.chetwood = new LOTRBiomeGenChetwood(91, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.4f).setColor(4424477).setBiomeName("chetwood");
        LOTRBiome.forodwaithGlacier = new LOTRBiomeGenForodwaithGlacier(92, true).setTemperatureRainfall(0.0f, 0.1f).setMinMaxHeight(1.0f, 0.1f).setColor(9424096).setBiomeName("forodwaithGlacier");
        LOTRBiome.whiteMountainsFoothills = new LOTRBiomeGenWhiteMountainsFoothills(93, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.5f, 0.9f).setColor(12635575).setBiomeName("whiteMountainsFoothills");
        LOTRBiome.beach = new LOTRBiomeGenBeach(94, false).setBeachBlock((Block)Blocks.sand, 0).setColor(14404247).setBiomeName("beach");
        LOTRBiome.beachGravel = new LOTRBiomeGenBeach(95, false).setBeachBlock(Blocks.gravel, 0).setColor(9868704).setBiomeName("beachGravel");
        LOTRBiome.nearHarad = new LOTRBiomeGenNearHarad(96, true).setTemperatureRainfall(1.5f, 0.1f).setMinMaxHeight(0.2f, 0.1f).setColor(14205815).setBiomeName("nearHarad");
        LOTRBiome.farHarad = new LOTRBiomeGenFarHaradSavannah(97, true).setTemperatureRainfall(1.2f, 0.2f).setMinMaxHeight(0.1f, 0.1f).setColor(9740353).setBiomeName("farHarad");
        LOTRBiome.haradMountains = new LOTRBiomeGenHaradMountains(98, true).setTemperatureRainfall(0.9f, 0.5f).setMinMaxHeight(1.8f, 2.0f).setColor(9867381).setBiomeName("haradMountains");
        LOTRBiome.umbar = new LOTRBiomeGenUmbar(99, true).setTemperatureRainfall(0.9f, 0.6f).setMinMaxHeight(0.1f, 0.2f).setColor(9542740).setBiomeName("umbar");
        LOTRBiome.farHaradJungle = new LOTRBiomeGenFarHaradJungle(100, true).setTemperatureRainfall(1.2f, 0.9f).setMinMaxHeight(0.2f, 0.4f).setColor(4944931).setBiomeName("farHaradJungle");
        LOTRBiome.umbarHills = new LOTRBiomeGenUmbar(101, false).setTemperatureRainfall(0.8f, 0.5f).setMinMaxHeight(1.2f, 0.8f).setColor(8226378).setBiomeName("umbarHills");
        LOTRBiome.nearHaradHills = new LOTRBiomeGenNearHaradHills(102, false).setTemperatureRainfall(1.2f, 0.3f).setMinMaxHeight(0.5f, 0.8f).setColor(12167010).setBiomeName("nearHaradHills");
        LOTRBiome.farHaradJungleLake = new LOTRBiomeGenFarHaradJungleLake(103, false).setTemperatureRainfall(1.2f, 0.9f).setMinMaxHeight(-0.5f, 0.2f).setColor(2271948).setBiomeName("farHaradJungleLake");
        LOTRBiome.lostladen = new LOTRBiomeGenLostladen(104, true).setTemperatureRainfall(1.2f, 0.2f).setMinMaxHeight(0.2f, 0.1f).setColor(10658661).setBiomeName("lostladen");
        LOTRBiome.farHaradForest = new LOTRBiomeGenFarHaradForest(105, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.3f, 0.4f).setColor(3703325).setBiomeName("farHaradForest");
        LOTRBiome.nearHaradFertile = new LOTRBiomeGenNearHaradFertile(106, true).setTemperatureRainfall(1.2f, 0.7f).setMinMaxHeight(0.2f, 0.1f).setColor(10398286).setBiomeName("nearHaradFertile");
        LOTRBiome.pertorogwaith = new LOTRBiomeGenPertorogwaith(107, true).setTemperatureRainfall(0.7f, 0.1f).setMinMaxHeight(0.2f, 0.5f).setColor(8879706).setBiomeName("pertorogwaith");
        LOTRBiome.umbarForest = new LOTRBiomeGenUmbarForest(108, false).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.2f, 0.3f).setColor(7178042).setBiomeName("umbarForest");
        LOTRBiome.farHaradJungleEdge = new LOTRBiomeGenFarHaradJungleEdge(109, true).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.2f, 0.2f).setColor(7440430).setBiomeName("farHaradJungleEdge");
        LOTRBiome.tauredainClearing = new LOTRBiomeGenTauredainClearing(110, true).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.2f, 0.2f).setColor(10796101).setBiomeName("tauredainClearing");
        LOTRBiome.gulfHarad = new LOTRBiomeGenGulfHarad(111, true).setTemperatureRainfall(1.0f, 0.5f).setMinMaxHeight(0.15f, 0.1f).setColor(9152592).setBiomeName("gulfHarad");
        LOTRBiome.dorwinionHills = new LOTRBiomeGenDorwinionHills(112, true).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.8f, 0.8f).setColor(13357993).setBiomeName("dorwinionHills");
        LOTRBiome.tolfalas = new LOTRBiomeGenTolfalas(113, true).setTemperatureRainfall(0.8f, 0.4f).setMinMaxHeight(0.3f, 1.0f).setColor(10199149).setBiomeName("tolfalas");
        LOTRBiome.lebennin = new LOTRBiomeGenLebennin(114, true).setTemperatureRainfall(1.0f, 0.9f).setMinMaxHeight(0.1f, 0.3f).setColor(7845418).setBiomeName("lebennin");
        LOTRBiome.rhun = new LOTRBiomeGenRhun(115, true).setTemperatureRainfall(0.9f, 0.3f).setMinMaxHeight(0.3f, 0.0f).setColor(10465880).setBiomeName("rhun");
        LOTRBiome.rhunForest = new LOTRBiomeGenRhunForest(116, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.3f, 0.5f).setColor(7505723).setBiomeName("rhunForest");
        LOTRBiome.redMountains = new LOTRBiomeGenRedMountains(117, true).setTemperatureRainfall(0.3f, 0.4f).setMinMaxHeight(1.5f, 2.0f).setColor(9662796).setBiomeName("redMountains");
        LOTRBiome.redMountainsFoothills = new LOTRBiomeGenRedMountainsFoothills(118, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.5f, 0.9f).setColor(10064978).setBiomeName("redMountainsFoothills");
        LOTRBiome.dolGuldur = new LOTRBiomeGenDolGuldur(119, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.2f, 0.5f).setColor(2371343).setBiomeName("dolGuldur");
        LOTRBiome.nearHaradSemiDesert = new LOTRBiomeGenNearHaradSemiDesert(120, true).setTemperatureRainfall(1.5f, 0.2f).setMinMaxHeight(0.2f, 0.1f).setColor(12434282).setBiomeName("nearHaradSemiDesert");
        LOTRBiome.farHaradArid = new LOTRBiomeGenFarHaradArid(121, true).setTemperatureRainfall(1.5f, 0.3f).setMinMaxHeight(0.2f, 0.15f).setColor(11185749).setBiomeName("farHaradArid");
        LOTRBiome.farHaradAridHills = new LOTRBiomeGenFarHaradArid(122, false).setTemperatureRainfall(1.5f, 0.3f).setMinMaxHeight(1.0f, 0.6f).setColor(10063195).setBiomeName("farHaradAridHills");
        LOTRBiome.farHaradSwamp = new LOTRBiomeGenFarHaradSwamp(123, true).setTemperatureRainfall(0.8f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(5608267).setBiomeName("farHaradSwamp");
        LOTRBiome.farHaradCloudForest = new LOTRBiomeGenFarHaradCloudForest(124, true).setTemperatureRainfall(1.2f, 1.2f).setMinMaxHeight(0.7f, 0.4f).setColor(3046208).setBiomeName("farHaradCloudForest");
        LOTRBiome.farHaradBushland = new LOTRBiomeGenFarHaradBushland(125, true).setTemperatureRainfall(1.0f, 0.4f).setMinMaxHeight(0.2f, 0.1f).setColor(10064190).setBiomeName("farHaradBushland");
        LOTRBiome.farHaradBushlandHills = new LOTRBiomeGenFarHaradBushland(126, false).setTemperatureRainfall(0.8f, 0.4f).setMinMaxHeight(0.8f, 0.8f).setColor(8354100).setBiomeName("farHaradBushlandHills");
        LOTRBiome.farHaradMangrove = new LOTRBiomeGenFarHaradMangrove(127, true).setTemperatureRainfall(1.0f, 0.9f).setMinMaxHeight(-0.05f, 0.05f).setColor(8883789).setBiomeName("farHaradMangrove");
        LOTRBiome.nearHaradFertileForest = new LOTRBiomeGenNearHaradFertileForest(128, false).setTemperatureRainfall(1.2f, 1.0f).setMinMaxHeight(0.2f, 0.4f).setColor(6915122).setBiomeName("nearHaradFertileForest");
        LOTRBiome.anduinVale = new LOTRBiomeGenAnduinVale(129, true).setTemperatureRainfall(0.9f, 1.1f).setMinMaxHeight(0.05f, 0.05f).setColor(7447880).setBiomeName("anduinVale");
        LOTRBiome.wold = new LOTRBiomeGenWold(130, true).setTemperatureRainfall(0.9f, 0.1f).setMinMaxHeight(0.4f, 0.3f).setColor(9483599).setBiomeName("wold");
        LOTRBiome.shireMoors = new LOTRBiomeGenShireMoors(131, true).setTemperatureRainfall(0.6f, 1.6f).setMinMaxHeight(0.4f, 0.6f).setColor(6921036).setBiomeName("shireMoors");
        LOTRBiome.shireMarshes = new LOTRBiomeGenShireMarshes(132, true).setTemperatureRainfall(0.8f, 1.2f).setMinMaxHeight(0.0f, 0.1f).setColor(4038751).setBiomeName("shireMarshes");
        LOTRBiome.nearHaradRedDesert = new LOTRBiomeGenNearHaradRed(133, true).setTemperatureRainfall(1.5f, 0.1f).setMinMaxHeight(0.2f, 0.0f).setColor(13210447).setBiomeName("nearHaradRedDesert");
        LOTRBiome.farHaradVolcano = new LOTRBiomeGenFarHaradVolcano(134, true).setTemperatureRainfall(1.5f, 0.0f).setMinMaxHeight(0.6f, 1.2f).setColor(6838068).setBiomeName("farHaradVolcano");
        LOTRBiome.udun = new LOTRBiomeGenUdun(135, true).setTemperatureRainfall(1.5f, 0.0f).setMinMaxHeight(0.2f, 0.7f).setColor(65536).setBiomeName("udun");
        LOTRBiome.gorgoroth = new LOTRBiomeGenGorgoroth(136, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.6f, 0.2f).setColor(2170141).setBiomeName("gorgoroth");
        LOTRBiome.morgulVale = new LOTRBiomeGenMorgulVale(137, true).setTemperatureRainfall(1.0f, 0.0f).setMinMaxHeight(0.2f, 0.1f).setColor(1387801).setBiomeName("morgulVale");
        LOTRBiome.easternDesolation = new LOTRBiomeGenEasternDesolation(138, true).setTemperatureRainfall(1.0f, 0.3f).setMinMaxHeight(0.2f, 0.2f).setColor(6052935).setBiomeName("easternDesolation");
        LOTRBiome.dale = new LOTRBiomeGenDale(139, true).setTemperatureRainfall(0.8f, 0.7f).setMinMaxHeight(0.1f, 0.2f).setColor(8233807).setBiomeName("dale");
        LOTRBiome.dorwinion = new LOTRBiomeGenDorwinion(140, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.1f, 0.3f).setColor(7120197).setBiomeName("dorwinion");
        LOTRBiome.towerHills = new LOTRBiomeGenTowerHills(141, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.5f, 0.5f).setColor(6854209).setBiomeName("towerHills");
        LOTRBiome.gulfHaradForest = new LOTRBiomeGenGulfHaradForest(142, false).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.2f, 0.4f).setColor(5868590).setBiomeName("gulfHaradForest");
        LOTRBiome.wilderlandNorth = new LOTRBiomeGenWilderlandNorth(143, true).setTemperatureRainfall(0.6f, 0.6f).setMinMaxHeight(0.2f, 0.5f).setColor(9676396).setBiomeName("wilderlandNorth");
        LOTRBiome.forodwaithCoast = new LOTRBiomeGenForodwaithCoast(144, false).setTemperatureRainfall(0.0f, 0.4f).setMinMaxHeight(0.0f, 0.5f).setColor(9214637).setBiomeName("forodwaithCoast");
        LOTRBiome.farHaradCoast = new LOTRBiomeGenFarHaradCoast(145, false).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.0f, 0.5f).setColor(8356472).setBiomeName("farHaradCoast");
        LOTRBiome.nearHaradRiverbank = new LOTRBiomeGenNearHaradRiverbank(146, false).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.1f, 0.1f).setColor(7183952).setBiomeName("nearHaradRiverbank");
        LOTRBiome.lossarnach = new LOTRBiomeGenLossarnach(147, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.1f, 0.2f).setColor(8439086).setBiomeName("lossarnach");
        LOTRBiome.imlothMelui = new LOTRBiomeGenImlothMelui(148, true).setTemperatureRainfall(1.0f, 1.2f).setMinMaxHeight(0.1f, 0.2f).setColor(14517608).setBiomeName("imlothMelui");
        LOTRBiome.nearHaradOasis = new LOTRBiomeGenNearHaradOasis(149, false).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.1f, 0.1f).setColor(832768).setBiomeName("nearHaradOasis");
        LOTRBiome.beachWhite = new LOTRBiomeGenBeach(150, false).setBeachBlock(LOTRMod.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
        LOTRBiome.harnedor = new LOTRBiomeGenHarnedor(151, true).setTemperatureRainfall(1.0f, 0.3f).setMinMaxHeight(0.1f, 0.3f).setColor(11449173).setBiomeName("harnedor");
        LOTRBiome.lamedon = new LOTRBiomeGenLamedon(152, true).setTemperatureRainfall(0.9f, 0.5f).setMinMaxHeight(0.2f, 0.2f).setColor(10927460).setBiomeName("lamedon");
        LOTRBiome.lamedonHills = new LOTRBiomeGenLamedonHills(153, true).setTemperatureRainfall(0.6f, 0.4f).setMinMaxHeight(0.6f, 0.9f).setColor(13555369).setBiomeName("lamedonHills");
        LOTRBiome.blackrootVale = new LOTRBiomeGenBlackrootVale(154, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.12f).setColor(7183921).setBiomeName("blackrootVale");
        LOTRBiome.andrast = new LOTRBiomeGenAndrast(155, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.2f, 0.2f).setColor(8885856).setBiomeName("andrast");
        LOTRBiome.pukel = new LOTRBiomeGenPukel(156, true).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.2f, 0.4f).setColor(5667394).setBiomeName("pukel");
        LOTRBiome.rhunLand = new LOTRBiomeGenRhunLand(157, true).setTemperatureRainfall(1.0f, 0.8f).setMinMaxHeight(0.1f, 0.3f).setColor(11381583).setBiomeName("rhunLand");
        LOTRBiome.rhunLandSteppe = new LOTRBiomeGenRhunLandSteppe(158, true).setTemperatureRainfall(1.0f, 0.3f).setMinMaxHeight(0.2f, 0.05f).setColor(11712354).setBiomeName("rhunLandSteppe");
        LOTRBiome.rhunLandHills = new LOTRBiomeGenRhunLandHills(159, true).setTemperatureRainfall(1.0f, 0.5f).setMinMaxHeight(0.6f, 0.8f).setColor(9342286).setBiomeName("rhunLandHills");
        LOTRBiome.rhunRedForest = new LOTRBiomeGenRhunRedForest(160, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.3f).setColor(9530430).setBiomeName("rhunRedForest");
        LOTRBiome.rhunIsland = new LOTRBiomeGenRhunIsland(161, false).setTemperatureRainfall(1.0f, 0.8f).setMinMaxHeight(0.1f, 0.4f).setColor(10858839).setBiomeName("rhunIsland");
        LOTRBiome.rhunIslandForest = new LOTRBiomeGenRhunIslandForest(162, false).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.4f).setColor(9533758).setBiomeName("rhunIslandForest");
        LOTRBiome.lastDesert = new LOTRBiomeGenLastDesert(163, true).setTemperatureRainfall(0.7f, 0.0f).setMinMaxHeight(0.2f, 0.05f).setColor(13878151).setBiomeName("lastDesert");
        LOTRBiome.windMountains = new LOTRBiomeGenWindMountains(164, true).setTemperatureRainfall(0.28f, 0.2f).setMinMaxHeight(2.0f, 2.0f).setColor(13882323).setBiomeName("windMountains");
        LOTRBiome.windMountainsFoothills = new LOTRBiomeGenWindMountainsFoothills(165, true).setTemperatureRainfall(0.4f, 0.6f).setMinMaxHeight(0.5f, 0.6f).setColor(10133354).setBiomeName("windMountainsFoothills");
        LOTRBiome.rivendell = new LOTRBiomeGenRivendell(166, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.15f, 0.3f).setColor(8828714).setBiomeName("rivendell");
        LOTRBiome.rivendellHills = new LOTRBiomeGenRivendellHills(167, true).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(2.0f, 0.5f).setColor(14210481).setBiomeName("rivendellHills");
        LOTRBiome.farHaradJungleMountains = new LOTRBiomeGenFarHaradJungleMountains(168, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(1.8f, 1.5f).setColor(6511174).setBiomeName("farHaradJungleMountains");
        LOTRBiome.halfTrollForest = new LOTRBiomeGenHalfTrollForest(169, true).setTemperatureRainfall(0.8f, 0.2f).setMinMaxHeight(0.3f, 0.4f).setColor(5992500).setBiomeName("halfTrollForest");
        LOTRBiome.farHaradKanuka = new LOTRBiomeGenKanuka(170, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.3f, 0.5f).setColor(5142552).setBiomeName("farHaradKanuka");
        LOTRBiome.utumno = new LOTRBiomeGenUtumno(0).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.0f, 0.0f).setColor(0).setBiomeName("utumno");
    }
    
    public LOTRBiome(final int i, final boolean major) {
        this(i, major, LOTRDimension.MIDDLE_EARTH);
    }
    
    public LOTRBiome(final int i, final boolean major, final LOTRDimension dim) {
        super(i, false);
        this.topBlockMeta = 0;
        this.fillerBlockMeta = 0;
        this.enablePodzol = true;
        this.enableRocky = true;
        this.biomeVariantsSmall = new LOTRBiomeVariantList();
        this.biomeVariantsLarge = new LOTRBiomeVariantList();
        this.variantChance = 0.4f;
        this.npcSpawnList = new LOTRBiomeSpawnList(this);
        this.spawnableLOTRAmbientList = new ArrayList();
        this.spawnableTraders = new ArrayList();
        this.biomeColors = new BiomeColors(this);
        this.biomeTerrain = new BiomeTerrain(this);
        this.initDwarven = false;
        this.biomeDimension = dim;
        if (this.biomeDimension.biomeList[i] != null) {
            throw new IllegalArgumentException("LOTR biome already exists at index " + i + " for dimension " + this.biomeDimension.dimensionName + "!");
        }
        this.biomeDimension.biomeList[i] = this;
        if (major) {
            this.biomeDimension.majorBiomes.add(this);
        }
        super.waterColorMultiplier = BiomeColors.DEFAULT_WATER;
        this.decorator = new LOTRBiomeDecorator(this);
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableMonsterList.clear();
        super.spawnableCaveCreatureList.clear();
        if (this.hasDomesticAnimals()) {
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySheep.class, 12, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityPig.class, 10, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityChicken.class, 10, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityCow.class, 8, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 5, 4, 4));
        }
        else {
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySheep.class, 12, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityWildBoar.class, 10, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityChicken.class, 8, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 10, 4, 4));
            super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityAurochs.class, 6, 4, 4));
        }
        super.spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityFish.class, 10, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 8, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityRabbit.class, 8, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 10, 4, 4));
        super.spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityBat.class, 10, 8, 8));
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        this.invasionSpawns = new LOTRBiomeInvasionSpawns(this);
    }
    
    protected void addBiomeVariant(final LOTRBiomeVariant v) {
        this.addBiomeVariant(v, 1.0f);
    }
    
    protected void addBiomeVariant(final LOTRBiomeVariant v, final float f) {
        if (v.variantScale == LOTRBiomeVariant.VariantScale.ALL) {
            this.biomeVariantsLarge.add(v, f);
            this.biomeVariantsSmall.add(v, f);
        }
        else if (v.variantScale == LOTRBiomeVariant.VariantScale.LARGE) {
            this.biomeVariantsLarge.add(v, f);
        }
        else if (v.variantScale == LOTRBiomeVariant.VariantScale.SMALL) {
            this.biomeVariantsSmall.add(v, f);
        }
    }
    
    protected void addBiomeVariantSet(final LOTRBiomeVariant[] set) {
        for (final LOTRBiomeVariant v : set) {
            this.addBiomeVariant(v);
        }
    }
    
    protected void clearBiomeVariants() {
        this.biomeVariantsLarge.clear();
        this.biomeVariantsSmall.clear();
        this.variantChance = 0.4f;
    }
    
    public LOTRBiomeVariantList getBiomeVariantsLarge() {
        return this.biomeVariantsLarge;
    }
    
    public LOTRBiomeVariantList getBiomeVariantsSmall() {
        return this.biomeVariantsSmall;
    }
    
    public LOTRBiome setTemperatureRainfall(final float f, final float f1) {
        super.setTemperatureRainfall(f, f1);
        return this;
    }
    
    public boolean hasSeasonalGrass() {
        return super.temperature > 0.3f && super.temperature < 1.0f;
    }
    
    public LOTRBiome setMinMaxHeight(float f, final float f1) {
        this.heightBaseParameter = f;
        f -= 2.0f;
        f += 0.2f;
        super.minHeight = f;
        super.maxHeight = f1 / 2.0f;
        return this;
    }
    
    public boolean isWateryBiome() {
        return this.heightBaseParameter < 0.0f;
    }
    
    public LOTRBiome setColor(int color) {
        color |= 0xFF000000;
        final Integer existingBiomeID = this.biomeDimension.colorsToBiomeIDs.get(color);
        if (existingBiomeID != null) {
            throw new RuntimeException("LOTR biome (ID " + super.biomeID + ") is duplicating the color of another LOTR biome (ID " + (int)existingBiomeID + ")");
        }
        this.biomeDimension.colorsToBiomeIDs.put(color, super.biomeID);
        return (LOTRBiome)super.setColor(color);
    }
    
    public LOTRBiome setBiomeName(final String s) {
        return (LOTRBiome)super.setBiomeName(s);
    }
    
    public final String getBiomeDisplayName() {
        return StatCollector.translateToLocal("lotr.biome." + super.biomeName + ".name");
    }
    
    public BiomeGenBase.FlowerEntry getRandomFlower(final Random random) {
        return (BiomeGenBase.FlowerEntry)WeightedRandom.getRandomItem(random, (Collection)this.flowers);
    }
    
    protected void registerPlainsFlowers() {
        this.flowers.clear();
        this.addFlower((Block)Blocks.red_flower, 4, 3);
        this.addFlower((Block)Blocks.red_flower, 5, 3);
        this.addFlower((Block)Blocks.red_flower, 6, 3);
        this.addFlower((Block)Blocks.red_flower, 7, 3);
        this.addFlower((Block)Blocks.red_flower, 0, 20);
        this.addFlower((Block)Blocks.red_flower, 3, 20);
        this.addFlower((Block)Blocks.red_flower, 8, 20);
        this.addFlower((Block)Blocks.yellow_flower, 0, 30);
        this.addFlower(LOTRMod.bluebell, 0, 5);
        this.addFlower(LOTRMod.marigold, 0, 10);
    }
    
    protected void registerRhunPlainsFlowers() {
        this.registerPlainsFlowers();
        this.addFlower(LOTRMod.marigold, 0, 10);
        this.addFlower(LOTRMod.rhunFlower, 0, 10);
        this.addFlower(LOTRMod.rhunFlower, 1, 10);
        this.addFlower(LOTRMod.rhunFlower, 2, 10);
        this.addFlower(LOTRMod.rhunFlower, 3, 10);
        this.addFlower(LOTRMod.rhunFlower, 4, 10);
    }
    
    protected void registerForestFlowers() {
        this.flowers.clear();
        this.addDefaultFlowers();
        this.addFlower(LOTRMod.bluebell, 0, 5);
        this.addFlower(LOTRMod.marigold, 0, 10);
    }
    
    protected void registerRhunForestFlowers() {
        this.registerForestFlowers();
        this.addFlower(LOTRMod.marigold, 0, 10);
        this.addFlower(LOTRMod.rhunFlower, 0, 10);
        this.addFlower(LOTRMod.rhunFlower, 1, 10);
        this.addFlower(LOTRMod.rhunFlower, 2, 10);
        this.addFlower(LOTRMod.rhunFlower, 3, 10);
        this.addFlower(LOTRMod.rhunFlower, 4, 10);
    }
    
    protected void registerJungleFlowers() {
        this.flowers.clear();
        this.addDefaultFlowers();
        this.addFlower(LOTRMod.haradFlower, 2, 20);
        this.addFlower(LOTRMod.haradFlower, 3, 20);
    }
    
    protected void registerMountainsFlowers() {
        this.flowers.clear();
        this.addDefaultFlowers();
        this.addFlower((Block)Blocks.red_flower, 1, 10);
        this.addFlower(LOTRMod.bluebell, 0, 5);
    }
    
    protected void registerTaigaFlowers() {
        this.flowers.clear();
        this.addDefaultFlowers();
        this.addFlower((Block)Blocks.red_flower, 1, 10);
        this.addFlower(LOTRMod.bluebell, 0, 5);
    }
    
    protected void registerSavannaFlowers() {
        this.flowers.clear();
        this.addDefaultFlowers();
    }
    
    protected void registerSwampFlowers() {
        this.flowers.clear();
        this.addDefaultFlowers();
    }
    
    protected void registerHaradFlowers() {
        this.flowers.clear();
        this.addFlower(LOTRMod.haradFlower, 0, 10);
        this.addFlower(LOTRMod.haradFlower, 1, 10);
        this.addFlower(LOTRMod.haradFlower, 2, 5);
        this.addFlower(LOTRMod.haradFlower, 3, 5);
    }
    
    protected void registerTravellingTrader(final Class entityClass) {
        this.spawnableTraders.add(entityClass);
        LOTREventSpawner.createTraderSpawner(entityClass);
    }
    
    protected final void clearTravellingTraders() {
        this.spawnableTraders.clear();
    }
    
    public final boolean canSpawnTravellingTrader(final Class entityClass) {
        return this.spawnableTraders.contains(entityClass);
    }
    
    protected final void setBanditChance(final LOTREventSpawner.EventChance c) {
        this.banditChance = c;
    }
    
    public final LOTREventSpawner.EventChance getBanditChance() {
        return this.banditChance;
    }
    
    protected final void setBanditEntityClass(final Class<? extends LOTREntityBandit> c) {
        this.banditEntityClass = c;
    }
    
    public final Class<? extends LOTREntityBandit> getBanditEntityClass() {
        if (this.banditEntityClass == null) {
            return LOTREntityBandit.class;
        }
        return this.banditEntityClass;
    }
    
    public void addBiomeF3Info(final List info, final World world, final LOTRBiomeVariant variant, final int i, final int j, final int k) {
        final int colorRGB = super.color & 0xFFFFFF;
        String colorString;
        for (colorString = Integer.toHexString(colorRGB); colorString.length() < 6; colorString = "0" + colorString) {}
        info.add("Middle-earth biome: " + this.getBiomeDisplayName() + ", ID: " + super.biomeID + ", c: #" + colorString);
        info.add("Variant: " + variant.variantName + ", loaded: " + LOTRBiomeVariantStorage.getSize(world));
    }
    
    protected boolean hasDomesticAnimals() {
        return false;
    }
    
    public boolean hasSky() {
        return true;
    }
    
    public LOTRAchievement getBiomeAchievement() {
        return null;
    }
    
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return null;
    }
    
    public abstract LOTRMusicRegion.Sub getBiomeMusic();
    
    public boolean isHiddenBiome() {
        return false;
    }
    
    public boolean isRiver() {
        return false;
    }
    
    public boolean getEnableRiver() {
        return true;
    }
    
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.PATH;
    }
    
    public LOTRRoadType.BridgeType getBridgeBlock() {
        return LOTRRoadType.BridgeType.DEFAULT;
    }
    
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, int height, final LOTRBiomeVariant variant) {
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        final int seaLevel = 63;
        final double stoneNoiseFiller = this.modifyStoneNoiseForFiller(stoneNoise);
        final int fillerDepthBase = (int)(stoneNoiseFiller / 4.0 + 5.0 + random.nextDouble() * 0.25);
        int fillerDepth = -1;
        Block top = super.topBlock;
        byte topMeta = (byte)this.topBlockMeta;
        Block filler = super.fillerBlock;
        byte fillerMeta = (byte)this.fillerBlockMeta;
        if (this.enableRocky && height >= 90) {
            final float hFactor = (height - 90) / 10.0f;
            float thresh = 1.2f - hFactor * 0.2f;
            thresh = Math.max(thresh, 0.0f);
            final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.03, k * 0.03);
            final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3);
            if (d1 + d2 > thresh) {
                if (random.nextInt(5) == 0) {
                    top = Blocks.gravel;
                    topMeta = 0;
                }
                else {
                    top = Blocks.stone;
                    topMeta = 0;
                }
                filler = Blocks.stone;
                fillerMeta = 0;
                final int prevHeight = height;
                if (random.nextInt(20) == 0) {
                    ++height;
                }
                for (int j = height; j >= prevHeight; --j) {
                    final int index = xzIndex * ySize + j;
                    blocks[index] = Blocks.stone;
                    meta[index] = 0;
                }
            }
        }
        if (this.enablePodzol) {
            boolean podzol = false;
            if (super.topBlock == Blocks.grass) {
                float trees = this.decorator.treesPerChunk + this.getTreeIncreaseChance();
                trees = Math.max(trees, variant.treeFactor * 0.5f);
                if (trees >= 1.0f) {
                    float thresh2 = 0.8f;
                    thresh2 -= trees * 0.15f;
                    thresh2 = Math.max(thresh2, 0.0f);
                    final double d3 = 0.06;
                    final double randNoise = LOTRBiome.biomeTerrainNoise.func_151601_a(i * d3, k * d3);
                    if (randNoise > thresh2) {
                        podzol = true;
                    }
                }
            }
            if (podzol) {
                LOTRBiome.terrainRand.setSeed(world.getSeed());
                LOTRBiome.terrainRand.setSeed(LOTRBiome.terrainRand.nextLong() + i * 4668095025L + k * 1387590552L ^ world.getSeed());
                final float pdzRand = LOTRBiome.terrainRand.nextFloat();
                if (pdzRand < 0.35f) {
                    top = Blocks.dirt;
                    topMeta = 2;
                }
                else if (pdzRand < 0.5f) {
                    top = Blocks.dirt;
                    topMeta = 1;
                }
                else if (pdzRand < 0.51f) {
                    top = Blocks.gravel;
                    topMeta = 0;
                }
            }
        }
        final boolean marsh = variant.hasMarsh;
        if (marsh) {
            final double d4 = LOTRBiomeVariant.marshNoise.func_151601_a(i * 0.1, k * 0.1);
            if (d4 > -0.1) {
                int l = ySize - 1;
                while (l >= 0) {
                    final int index2 = xzIndex * ySize + l;
                    if (blocks[index2] == null || blocks[index2].getMaterial() != Material.air) {
                        if (l == seaLevel - 1 && blocks[index2] != Blocks.water) {
                            blocks[index2] = Blocks.water;
                            break;
                        }
                        break;
                    }
                    else {
                        --l;
                    }
                }
            }
        }
        for (int m = ySize - 1; m >= 0; --m) {
            final int index3 = xzIndex * ySize + m;
            if (m <= 0 + random.nextInt(5)) {
                blocks[index3] = Blocks.bedrock;
            }
            else {
                final Block block = blocks[index3];
                if (block == Blocks.air) {
                    fillerDepth = -1;
                }
                else if (block == Blocks.stone) {
                    if (fillerDepth == -1) {
                        if (fillerDepthBase <= 0) {
                            top = Blocks.air;
                            topMeta = 0;
                            filler = Blocks.stone;
                            fillerMeta = 0;
                        }
                        else if (m >= seaLevel - 4 && m <= seaLevel + 1) {
                            top = super.topBlock;
                            topMeta = (byte)this.topBlockMeta;
                            filler = super.fillerBlock;
                            fillerMeta = (byte)this.fillerBlockMeta;
                        }
                        if (m < seaLevel && top == Blocks.air) {
                            top = Blocks.water;
                            topMeta = 0;
                        }
                        fillerDepth = fillerDepthBase;
                        if (m >= seaLevel - 1) {
                            blocks[index3] = top;
                            meta[index3] = topMeta;
                        }
                        else {
                            blocks[index3] = filler;
                            meta[index3] = fillerMeta;
                        }
                    }
                    else if (fillerDepth > 0) {
                        --fillerDepth;
                        blocks[index3] = filler;
                        meta[index3] = fillerMeta;
                        if (fillerDepth == 0) {
                            boolean sand = false;
                            if (filler == Blocks.sand) {
                                if (fillerMeta == 1) {
                                    filler = LOTRMod.redSandstone;
                                    fillerMeta = 0;
                                }
                                else {
                                    filler = Blocks.sandstone;
                                    fillerMeta = 0;
                                }
                                sand = true;
                            }
                            if (filler == LOTRMod.whiteSand) {
                                filler = LOTRMod.whiteSandstone;
                                fillerMeta = 0;
                                sand = true;
                            }
                            if (sand) {
                                fillerDepth = 10 + random.nextInt(4);
                            }
                        }
                        if ((this instanceof LOTRBiomeGenGondor || this instanceof LOTRBiomeGenIthilien || this instanceof LOTRBiomeGenDorEnErnil) && fillerDepth == 0 && filler == super.fillerBlock) {
                            fillerDepth = 8 + random.nextInt(3);
                            filler = LOTRMod.rock;
                            fillerMeta = 1;
                        }
                        else if ((this instanceof LOTRBiomeGenRohan || this instanceof LOTRBiomeGenAdornland) && fillerDepth == 0 && filler == super.fillerBlock) {
                            fillerDepth = 8 + random.nextInt(3);
                            filler = LOTRMod.rock;
                            fillerMeta = 2;
                        }
                        else if (this instanceof LOTRBiomeGenDorwinion && fillerDepth == 0 && super.fillerBlock != LOTRMod.rock && filler == super.fillerBlock) {
                            fillerDepth = 6 + random.nextInt(3);
                            filler = LOTRMod.rock;
                            fillerMeta = 5;
                        }
                    }
                }
            }
        }
        final int rockDepth = (int)(stoneNoise * 6.0 + 2.0 + random.nextDouble() * 0.25);
        this.generateMountainTerrain(world, random, blocks, meta, i, k, xzIndex, ySize, height, rockDepth, variant);
        variant.generateVariantTerrain(world, random, blocks, meta, i, k, height, this);
    }
    
    protected double modifyStoneNoiseForFiller(final double stoneNoise) {
        return stoneNoise;
    }
    
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
    }
    
    public void decorate(final World world, final Random random, final int i, final int k) {
        this.decorator.decorate(world, random, i, k);
    }
    
    public LOTRBiomeSpawnList getNPCSpawnList(final World world, final Random random, final int i, final int j, final int k, final LOTRBiomeVariant variant) {
        return this.npcSpawnList;
    }
    
    public final boolean isDwarvenBiome(final World world) {
        if (this.initDwarven) {
            return this.isDwarven;
        }
        this.initDwarven = true;
        return this.isDwarven = (this.npcSpawnList.containsEntityClassByDefault((Class<? extends EntityLivingBase>)LOTREntityDwarf.class, world) && !this.npcSpawnList.containsEntityClassByDefault((Class<? extends EntityLivingBase>)LOTREntityWickedDwarf.class, world));
    }
    
    public List getSpawnableList(final EnumCreatureType creatureType) {
        if (creatureType == LOTRBiome.creatureType_LOTRAmbient) {
            return this.spawnableLOTRAmbientList;
        }
        return super.getSpawnableList(creatureType);
    }
    
    public float getChanceToSpawnAnimals() {
        return 1.0f;
    }
    
    public boolean canSpawnHostilesInDay() {
        return false;
    }
    
    public final WorldGenAbstractTree func_150567_a(final Random random) {
        final LOTRTreeType tree = this.decorator.getRandomTree(random);
        return tree.create(false, random);
    }
    
    public final WorldGenAbstractTree getTreeGen(final World world, final Random random, final int i, final int j, final int k) {
        final LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)world.getWorldChunkManager();
        final LOTRBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
        final LOTRTreeType tree = this.decorator.getRandomTreeForVariant(random, variant);
        return tree.create(false, random);
    }
    
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
    
    public final WorldGenerator getRandomWorldGenForGrass(final Random random) {
        final GrassBlockAndMeta obj = this.getRandomGrass(random);
        return (WorldGenerator)new WorldGenTallGrass(obj.block, obj.meta);
    }
    
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        final boolean fern = this.decorator.enableFern;
        final boolean special = this.decorator.enableSpecialGrasses;
        if (fern && random.nextInt(3) == 0) {
            return new GrassBlockAndMeta((Block)Blocks.tallgrass, 2);
        }
        if (special && random.nextInt(500) == 0) {
            return new GrassBlockAndMeta(LOTRMod.flaxPlant, 0);
        }
        if (random.nextInt(4) > 0) {
            if (special) {
                if (random.nextInt(200) == 0) {
                    return new GrassBlockAndMeta(LOTRMod.tallGrass, 3);
                }
                if (random.nextInt(16) == 0) {
                    return new GrassBlockAndMeta(LOTRMod.tallGrass, 1);
                }
                if (random.nextInt(10) == 0) {
                    return new GrassBlockAndMeta(LOTRMod.tallGrass, 2);
                }
            }
            if (random.nextInt(80) == 0) {
                return new GrassBlockAndMeta(LOTRMod.tallGrass, 4);
            }
            return new GrassBlockAndMeta(LOTRMod.tallGrass, 0);
        }
        else {
            if (random.nextInt(3) == 0) {
                return new GrassBlockAndMeta(LOTRMod.clover, 0);
            }
            return new GrassBlockAndMeta((Block)Blocks.tallgrass, 1);
        }
    }
    
    public WorldGenerator getRandomWorldGenForDoubleGrass(final Random random) {
        final WorldGenDoublePlant generator = new WorldGenDoublePlant();
        if (this.decorator.enableFern && random.nextInt(4) == 0) {
            generator.func_150548_a(3);
        }
        else {
            generator.func_150548_a(2);
        }
        return (WorldGenerator)generator;
    }
    
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        final WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
        final int i = random.nextInt(3);
        switch (i) {
            case 0: {
                doubleFlowerGen.func_150548_a(1);
                break;
            }
            case 1: {
                doubleFlowerGen.func_150548_a(4);
                break;
            }
            case 2: {
                doubleFlowerGen.func_150548_a(5);
                break;
            }
        }
        return (WorldGenerator)doubleFlowerGen;
    }
    
    public int spawnCountMultiplier() {
        return 1;
    }
    
    public BiomeGenBase func_150566_k() {
        return this;
    }
    
    public boolean canSpawnLightningBolt() {
        return !this.getEnableSnow() && super.canSpawnLightningBolt();
    }
    
    public boolean getEnableRain() {
        return super.enableRain;
    }
    
    public boolean getEnableSnow() {
        return (LOTRMod.isChristmas() && LOTRMod.proxy.isClient()) || super.getEnableSnow();
    }
    
    public int getSnowHeight() {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(final int i, final int j, final int k) {
        int grassColor;
        if (this.biomeColors.grass != null) {
            grassColor = this.biomeColors.grass.getRGB();
        }
        else {
            grassColor = this.getBaseGrassColor(i, j, k);
        }
        return grassColor;
    }
    
    @SideOnly(Side.CLIENT)
    public final int getBaseGrassColor(final int i, final int j, final int k) {
        final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)LOTRMod.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
        float temp = this.getFloatTemperature(i, j, k) + variant.tempBoost;
        float rain = super.rainfall + variant.rainBoost;
        temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
        rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
        return ColorizerGrass.getGrassColor((double)temp, (double)rain);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(final int i, final int j, final int k) {
        int folgColor;
        if (this.biomeColors.foliage != null) {
            folgColor = this.biomeColors.foliage.getRGB();
        }
        else {
            folgColor = this.getBaseFoliageColor(i, j, k);
        }
        return folgColor;
    }
    
    @SideOnly(Side.CLIENT)
    public final int getBaseFoliageColor(final int i, final int j, final int k) {
        final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)LOTRMod.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
        float temp = this.getFloatTemperature(i, j, k) + variant.tempBoost;
        float rain = super.rainfall + variant.rainBoost;
        temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
        rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
        return ColorizerFoliage.getFoliageColor((double)temp, (double)rain);
    }
    
    @SideOnly(Side.CLIENT)
    public final int getSkyColorByTemp(final float f) {
        if (LOTRTickHandlerClient.scrapTraderMisbehaveTick > 0) {
            return 0;
        }
        if (this.biomeColors.sky != null) {
            return this.biomeColors.sky.getRGB();
        }
        return super.getSkyColorByTemp(f);
    }
    
    @SideOnly(Side.CLIENT)
    public final int getBaseSkyColorByTemp(final int i, final int j, final int k) {
        return super.getSkyColorByTemp(this.getFloatTemperature(i, j, k));
    }
    
    public final Vec3 getCloudColor(final Vec3 clouds) {
        if (this.biomeColors.clouds != null) {
            final float[] colors = this.biomeColors.clouds.getColorComponents(null);
            clouds.xCoord *= colors[0];
            clouds.yCoord *= colors[1];
            clouds.zCoord *= colors[2];
        }
        return clouds;
    }
    
    public final Vec3 getFogColor(final Vec3 fog) {
        if (this.biomeColors.fog != null) {
            final float[] colors = this.biomeColors.fog.getColorComponents(null);
            fog.xCoord *= colors[0];
            fog.yCoord *= colors[1];
            fog.zCoord *= colors[2];
        }
        return fog;
    }
    
    public final boolean hasFog() {
        return this.biomeColors.foggy;
    }
    
    public static void updateWaterColor(final int i, final int j, final int k) {
        final int min = 0;
        final int max = LOTRBiome.waterLimitSouth - LOTRBiome.waterLimitNorth;
        final float latitude = MathHelper.clamp_int(k - LOTRBiome.waterLimitNorth, min, max) / (float)max;
        final float[] northColors = LOTRBiome.waterColorNorth.getColorComponents(null);
        final float[] southColors = LOTRBiome.waterColorSouth.getColorComponents(null);
        final float dR = southColors[0] - northColors[0];
        final float dG = southColors[1] - northColors[1];
        final float dB = southColors[2] - northColors[2];
        float r = dR * latitude;
        float g = dG * latitude;
        float b = dB * latitude;
        r += northColors[0];
        g += northColors[1];
        b += northColors[2];
        final Color water = new Color(r, g, b);
        final int waterRGB = water.getRGB();
        for (final LOTRDimension dimension : LOTRDimension.values()) {
            for (final LOTRBiome biome : dimension.biomeList) {
                if (biome != null && !biome.biomeColors.hasCustomWater()) {
                    biome.biomeColors.updateWater(waterRGB);
                }
            }
        }
    }
    
    static {
        LOTRBiome.correctCreatureTypeParams = new Class[][] { { EnumCreatureType.class, Class.class, Integer.TYPE, Material.class, Boolean.TYPE, Boolean.TYPE } };
        LOTRBiome.creatureType_LOTRAmbient = (EnumCreatureType)EnumHelper.addEnum(LOTRBiome.correctCreatureTypeParams, (Class)EnumCreatureType.class, "LOTRAmbient", new Object[] { LOTRAmbientCreature.class, 45, Material.air, true, false });
        LOTRBiome.biomeTerrainNoise = new NoiseGeneratorPerlin(new Random(1955L), 1);
        LOTRBiome.terrainRand = new Random();
        LOTRBiome.waterColorNorth = new Color(602979);
        LOTRBiome.waterColorSouth = new Color(4973293);
        LOTRBiome.waterLimitNorth = -40000;
        LOTRBiome.waterLimitSouth = 160000;
    }
    
    public static class BiomeColors
    {
        private LOTRBiome theBiome;
        private Color grass;
        private Color foliage;
        private Color sky;
        private Color clouds;
        private Color fog;
        private boolean foggy;
        private boolean hasCustomWater;
        private static int DEFAULT_WATER;
        
        public BiomeColors(final LOTRBiome biome) {
            this.hasCustomWater = false;
            this.theBiome = biome;
        }
        
        public void setGrass(final int rgb) {
            this.grass = new Color(rgb);
        }
        
        public void resetGrass() {
            this.grass = null;
        }
        
        public void setFoliage(final int rgb) {
            this.foliage = new Color(rgb);
        }
        
        public void resetFoliage() {
            this.foliage = null;
        }
        
        public void setSky(final int rgb) {
            this.sky = new Color(rgb);
        }
        
        public void resetSky() {
            this.sky = null;
        }
        
        public void setClouds(final int rgb) {
            this.clouds = new Color(rgb);
        }
        
        public void resetClouds() {
            this.clouds = null;
        }
        
        public void setFog(final int rgb) {
            this.fog = new Color(rgb);
        }
        
        public void resetFog() {
            this.fog = null;
        }
        
        public void setFoggy(final boolean flag) {
            this.foggy = flag;
        }
        
        public void setWater(final int rgb) {
            this.theBiome.waterColorMultiplier = rgb;
            if (rgb != BiomeColors.DEFAULT_WATER) {
                this.hasCustomWater = true;
            }
        }
        
        public void resetWater() {
            this.setWater(BiomeColors.DEFAULT_WATER);
        }
        
        public boolean hasCustomWater() {
            return this.hasCustomWater;
        }
        
        public void updateWater(final int rgb) {
            this.theBiome.waterColorMultiplier = rgb;
        }
        
        static {
            BiomeColors.DEFAULT_WATER = 7186907;
        }
    }
    
    public static class BiomeTerrain
    {
        private LOTRBiome theBiome;
        private double xzScale;
        private double heightStretchFactor;
        
        public BiomeTerrain(final LOTRBiome biome) {
            this.xzScale = -1.0;
            this.heightStretchFactor = -1.0;
            this.theBiome = biome;
        }
        
        public void setXZScale(final double d) {
            this.xzScale = d;
        }
        
        public void resetXZScale() {
            this.setXZScale(-1.0);
        }
        
        public boolean hasXZScale() {
            return this.xzScale != -1.0;
        }
        
        public double getXZScale() {
            return this.xzScale;
        }
        
        public void setHeightStretchFactor(final double d) {
            this.heightStretchFactor = d;
        }
        
        public void resetHeightStretchFactor() {
            this.setHeightStretchFactor(-1.0);
        }
        
        public boolean hasHeightStretchFactor() {
            return this.heightStretchFactor != -1.0;
        }
        
        public double getHeightStretchFactor() {
            return this.heightStretchFactor;
        }
    }
    
    public static class GrassBlockAndMeta
    {
        public final Block block;
        public final int meta;
        
        public GrassBlockAndMeta(final Block b, final int i) {
            this.block = b;
            this.meta = i;
        }
    }
}
