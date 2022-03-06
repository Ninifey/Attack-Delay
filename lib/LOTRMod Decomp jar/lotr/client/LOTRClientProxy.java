// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import lotr.client.fx.LOTREntityWhiteSmokeFX;
import lotr.client.fx.LOTREntityWaveFX;
import lotr.client.fx.LOTREntityUtumnoKillFX;
import lotr.client.fx.LOTREntityQuenditeSmokeFX;
import lotr.client.fx.LOTREntityMusicFX;
import lotr.client.fx.LOTREntityMTCHealFX;
import lotr.client.fx.LOTREntityMorgulPortalFX;
import lotr.client.fx.LOTREntityRiverWaterFX;
import lotr.common.world.biome.LOTRBiome;
import lotr.client.fx.LOTREntityMallornEntSummonFX;
import lotr.client.fx.LOTREntityBossSpawnFX;
import lotr.client.fx.LOTREntityMallornEntHealFX;
import net.minecraft.block.Block;
import lotr.client.fx.LOTREntityMarshLightFX;
import lotr.client.fx.LOTREntityMarshFlameFX;
import lotr.client.fx.LOTREntityLeafFX;
import lotr.client.fx.LOTREntityLargeBlockFX;
import net.minecraft.init.Blocks;
import lotr.client.fx.LOTREntityGandalfFireballExplodeFX;
import lotr.client.fx.LOTREntityElvenGlowFX;
import lotr.client.fx.LOTREntityChillFX;
import net.minecraft.client.particle.EntityFX;
import lotr.client.fx.LOTREntityBlueFlameFX;
import lotr.common.LOTRTickHandlerServer;
import lotr.common.world.map.LOTRConquestZone;
import java.util.List;
import lotr.client.gui.LOTRGuiAlignmentChoices;
import lotr.client.gui.LOTRGuiFactions;
import lotr.client.gui.LOTRGuiMiniquestOffer;
import lotr.common.quest.LOTRMiniQuest;
import lotr.client.gui.LOTRGuiBanner;
import com.mojang.authlib.GameProfile;
import lotr.client.gui.LOTRGuiFastTravel;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.client.gui.LOTRGuiHiredFarmer;
import lotr.client.gui.LOTRGuiHiredWarrior;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.gui.GuiScreen;
import lotr.client.gui.LOTRGuiMessage;
import lotr.common.LOTRGuiMessageTypes;
import net.minecraft.util.IChatComponent;
import lotr.common.LOTRAchievement;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRAlignmentBonusMap;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketClientInfo;
import lotr.client.gui.LOTRGuiMap;
import lotr.common.LOTRDimension;
import java.util.Map;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.ItemRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import lotr.common.LOTRReflection;
import net.minecraft.world.World;
import lotr.common.LOTRConfig;
import lotr.client.render.tileentity.LOTRRenderSignCarvedIthildin;
import lotr.common.tileentity.LOTRTileEntitySignCarvedIthildin;
import lotr.client.render.tileentity.LOTRRenderSignCarved;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import lotr.client.render.tileentity.LOTRRenderKebabStand;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import lotr.client.render.tileentity.LOTRRenderWeaponRack;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import lotr.client.render.tileentity.LOTRRenderChest;
import lotr.common.tileentity.LOTRTileEntityChest;
import lotr.client.render.tileentity.LOTRRenderDartTrap;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import lotr.client.render.tileentity.LOTRRenderUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import lotr.client.render.tileentity.LOTRRenderCommandTable;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.client.render.tileentity.LOTRRenderUtumnoReturnPortal;
import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import lotr.client.render.tileentity.LOTRRenderUtumnoPortal;
import lotr.common.tileentity.LOTRTileEntityUtumnoPortal;
import lotr.client.render.tileentity.LOTRRenderTrollTotem;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import lotr.client.render.tileentity.LOTRRenderEntJar;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import lotr.client.render.tileentity.LOTRRenderMug;
import lotr.common.tileentity.LOTRTileEntityMug;
import lotr.client.render.tileentity.LOTRRenderArmorStand;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import lotr.client.render.tileentity.LOTRRenderMorgulPortal;
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import lotr.client.render.tileentity.LOTRRenderDwarvenDoor;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import lotr.client.render.tileentity.LOTRRenderGuldurilGlow;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import lotr.client.render.tileentity.LOTRRenderSpawnerChest;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import lotr.client.render.tileentity.LOTRRenderElvenPortal;
import lotr.common.tileentity.LOTRTileEntityElvenPortal;
import lotr.client.render.tileentity.LOTRRenderPlateFood;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import lotr.client.render.tileentity.LOTRRenderBeacon;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import lotr.client.render.LOTRRenderBlocks;
import net.minecraft.init.Items;
import net.minecraft.entity.projectile.EntityPotion;
import lotr.client.render.entity.LOTRRenderWickedDwarf;
import lotr.common.entity.npc.LOTREntityWickedDwarf;
import lotr.client.render.entity.LOTRRenderGandalf;
import lotr.common.entity.npc.LOTREntityGandalf;
import lotr.client.render.entity.LOTRRenderWhiteOryx;
import lotr.common.entity.animal.LOTREntityWhiteOryx;
import lotr.common.entity.npc.LOTREntityGulfBartender;
import lotr.common.entity.npc.LOTREntityUmbarBartender;
import lotr.common.entity.npc.LOTREntitySouthronBartender;
import lotr.client.render.entity.LOTRRenderHaradrimTrader;
import lotr.common.entity.npc.LOTREntityHarnedorBartender;
import lotr.client.render.entity.LOTRRenderGondorRenegade;
import lotr.common.entity.npc.LOTREntityGondorRenegade;
import lotr.client.render.entity.LOTRRenderHaradSlave;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import lotr.client.render.entity.LOTRRenderGiraffeRug;
import lotr.common.entity.item.LOTREntityGiraffeRug;
import lotr.client.render.entity.LOTRRenderBearRug;
import lotr.common.entity.item.LOTREntityBearRug;
import lotr.client.render.entity.LOTRRenderLionRug;
import lotr.common.entity.item.LOTREntityLionRug;
import lotr.client.render.entity.LOTRRenderSnowTroll;
import lotr.common.entity.npc.LOTREntitySnowTroll;
import lotr.client.render.entity.LOTRRenderNearHaradTrader;
import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.client.render.entity.LOTRRenderArrowPoisoned;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.client.render.entity.LOTRRenderFish;
import lotr.common.entity.animal.LOTREntityFish;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityRivendellSmith;
import lotr.common.entity.projectile.LOTREntityFirePot;
import lotr.client.render.entity.LOTRRenderFallingFireJar;
import lotr.common.entity.LOTREntityFallingFireJar;
import lotr.client.render.entity.LOTRRenderEasterlingTrader;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.client.render.entity.LOTRRenderEasterling;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.client.render.entity.LOTRRenderBear;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.npc.LOTREntityRohanOrcharder;
import lotr.common.entity.npc.LOTREntityRohanBaker;
import lotr.common.entity.npc.LOTREntityRohanButcher;
import lotr.common.entity.npc.LOTREntityRohanBrewer;
import lotr.common.entity.npc.LOTREntityRohanBuilder;
import lotr.client.render.entity.LOTRRenderDunedainTrader;
import lotr.common.entity.npc.LOTREntityDunedainBlacksmith;
import lotr.client.render.entity.LOTRRenderDunedain;
import lotr.common.entity.npc.LOTREntityDunedain;
import lotr.common.entity.npc.LOTREntityGondorBaker;
import lotr.common.entity.npc.LOTREntityGondorButcher;
import lotr.common.entity.npc.LOTREntityGondorFlorist;
import lotr.common.entity.npc.LOTREntityGondorBrewer;
import lotr.common.entity.npc.LOTREntityGondorMason;
import lotr.common.entity.npc.LOTREntityGondorGreengrocer;
import lotr.common.entity.npc.LOTREntityGondorBartender;
import lotr.client.render.entity.LOTRRenderFallingCoinPile;
import lotr.common.entity.item.LOTREntityFallingTreasure;
import lotr.client.render.entity.LOTRRenderGondorMan;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.client.render.entity.LOTRRenderKineAraw;
import lotr.common.entity.animal.LOTREntityKineAraw;
import lotr.client.render.entity.LOTRRenderAurochs;
import lotr.common.entity.animal.LOTREntityAurochs;
import lotr.client.render.entity.LOTRRenderDorwinionElfVintner;
import lotr.common.entity.npc.LOTREntityDorwinionElfVintner;
import lotr.common.entity.npc.LOTREntityDaleBaker;
import lotr.client.render.entity.LOTRRenderDorwinionMan;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import lotr.client.render.entity.LOTRRenderNPCRespawner;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.client.render.entity.LOTRRenderDaleTrader;
import lotr.common.entity.npc.LOTREntityDaleBlacksmith;
import lotr.client.render.entity.LOTRRenderDaleMan;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.client.render.entity.LOTRRenderDeer;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.npc.LOTREntityBlueMountainsSmith;
import lotr.client.render.entity.LOTRRenderDwarfSmith;
import lotr.common.entity.npc.LOTREntityDwarfSmith;
import lotr.client.render.entity.LOTRRenderGaladhrimWarden;
import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import lotr.client.render.entity.LOTRRenderTauredainShaman;
import lotr.common.entity.npc.LOTREntityTauredainShaman;
import lotr.client.render.entity.LOTRRenderBarrowWight;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.client.render.entity.LOTRRenderDart;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.client.render.entity.LOTRRenderTauredain;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.client.render.entity.LOTRRenderScrapTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.client.render.entity.LOTRRenderMallornLeafBomb;
import lotr.common.entity.projectile.LOTREntityMallornLeafBomb;
import lotr.client.render.entity.LOTRRenderMallornEnt;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.client.render.entity.LOTRRenderBossTrophy;
import lotr.common.entity.item.LOTREntityBossTrophy;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityAngmarHillmanWarrior;
import lotr.client.render.entity.LOTRRenderAngmarHillman;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.client.render.entity.LOTRRenderMoredain;
import lotr.common.entity.npc.LOTREntityMoredain;
import lotr.client.render.entity.LOTRRenderSwan;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.client.render.entity.LOTRRenderSwanKnight;
import lotr.common.entity.npc.LOTREntityDolAmrothSoldier;
import lotr.common.entity.npc.LOTREntityWoodElfSmith;
import lotr.common.entity.npc.LOTREntityHighElfSmith;
import lotr.client.render.entity.LOTRRenderElvenSmith;
import lotr.common.entity.npc.LOTREntityGaladhrimSmith;
import lotr.client.render.entity.LOTRRenderHalfTrollScavenger;
import lotr.common.entity.npc.LOTREntityHalfTrollScavenger;
import lotr.client.render.entity.LOTRRenderHalfTroll;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.client.render.entity.LOTRRenderBalrog;
import lotr.common.entity.npc.LOTREntityBalrog;
import lotr.client.render.entity.LOTRRenderUtumnoTroll;
import lotr.common.entity.npc.LOTREntityUtumnoTroll;
import lotr.common.entity.projectile.LOTREntityConker;
import lotr.client.render.entity.LOTRRenderUtumnoIceSpider;
import lotr.common.entity.npc.LOTREntityUtumnoIceSpider;
import lotr.client.render.entity.LOTRRenderDikDik;
import lotr.common.entity.animal.LOTREntityDikDik;
import lotr.common.entity.projectile.LOTREntityThrownTermite;
import lotr.client.render.entity.LOTRRenderTermite;
import lotr.common.entity.animal.LOTREntityTermite;
import lotr.client.render.entity.LOTRRenderMirkTroll;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import lotr.client.render.entity.LOTRRenderElk;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.client.render.entity.LOTRRenderInvasionSpawner;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.client.render.entity.LOTRRenderSaruman;
import lotr.common.entity.npc.LOTREntitySaruman;
import lotr.client.render.entity.LOTRRenderBandit;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.client.render.entity.LOTRRenderCamel;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.client.render.entity.LOTRRenderBird;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.client.render.entity.LOTRRenderScorpion;
import lotr.common.entity.animal.LOTREntityScorpion;
import lotr.client.render.entity.LOTRRenderFlamingo;
import lotr.common.entity.animal.LOTREntityFlamingo;
import lotr.client.render.entity.LOTRRenderGemsbok;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.client.render.entity.LOTRRenderNearHaradrimWarlord;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarlord;
import lotr.client.render.entity.LOTRRenderNearHaradrim;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.client.render.entity.LOTRRenderCrocodile;
import lotr.common.entity.animal.LOTREntityCrocodile;
import lotr.client.render.entity.LOTRRenderRhino;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.client.render.entity.LOTRRenderZebra;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.client.render.entity.LOTRRenderGiraffe;
import lotr.common.entity.animal.LOTREntityGiraffe;
import lotr.client.render.entity.LOTRRenderLion;
import lotr.common.entity.animal.LOTREntityLionBase;
import lotr.client.render.entity.LOTRRenderBannerWall;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.client.render.entity.LOTRRenderBanner;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.client.render.entity.LOTRRenderMordorSpider;
import lotr.common.entity.npc.LOTREntityMordorSpider;
import lotr.client.render.entity.LOTRRenderWildBoar;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.client.render.entity.LOTRRenderRabbit;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.client.render.entity.LOTRRenderNurnSlave;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import lotr.client.render.entity.LOTRRenderDeadMarshFace;
import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.client.render.entity.LOTRRenderMidges;
import lotr.common.entity.animal.LOTREntityMidges;
import lotr.client.render.entity.LOTRRenderEntityBarrel;
import lotr.common.entity.item.LOTREntityBarrel;
import lotr.client.render.entity.LOTRRenderButterfly;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.LOTREntityRohanMeadhost;
import lotr.client.render.entity.LOTRRenderHuorn;
import lotr.common.entity.npc.LOTREntityHuornBase;
import lotr.client.render.entity.LOTRRenderMountainTrollChieftain;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import lotr.client.render.entity.LOTRRenderThrownRock;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import lotr.client.render.entity.LOTRRenderMountainTroll;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.client.render.entity.LOTRRenderTraderRespawn;
import lotr.common.entity.item.LOTREntityTraderRespawn;
import lotr.client.render.entity.LOTRRenderEnt;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.client.render.entity.LOTRRenderDunlendingBase;
import lotr.common.entity.npc.LOTREntityDunlendingWarrior;
import lotr.client.render.entity.LOTRRenderDunlending;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.client.render.entity.LOTRRenderRanger;
import lotr.common.entity.npc.LOTREntityRanger;
import lotr.client.render.entity.LOTRRenderRohanTrader;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import lotr.common.entity.projectile.LOTREntityMysteryWeb;
import net.minecraft.client.renderer.entity.RenderSnowball;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.client.render.entity.LOTRRenderRohirrim;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.client.render.entity.LOTRRenderMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.client.render.entity.LOTRRenderGollum;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.client.render.entity.LOTRRenderStoneTroll;
import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.client.render.entity.LOTRRenderOlogHai;
import lotr.common.entity.npc.LOTREntityOlogHai;
import lotr.client.render.entity.LOTRRenderTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.client.render.entity.LOTRRenderCrossbowBolt;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.client.render.entity.LOTRRenderThrowingAxe;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityBlueDwarfCommander;
import lotr.client.render.entity.LOTRRenderDwarfCommander;
import lotr.common.entity.npc.LOTREntityDwarfCommander;
import lotr.client.render.entity.LOTRRenderWraithBall;
import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import lotr.client.render.entity.LOTRRenderMarshWraith;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import lotr.client.render.entity.LOTRRenderDwarf;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.client.render.entity.LOTRRenderAlignmentBonus;
import lotr.client.fx.LOTREntityAlignmentBonus;
import lotr.client.render.entity.LOTRRenderElvenTrader;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.client.render.entity.LOTRRenderGondorTrader;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.client.render.entity.LOTRRenderSkeleton;
import lotr.common.entity.npc.LOTREntitySkeletalWraith;
import lotr.client.render.entity.LOTRRenderWargskinRug;
import lotr.common.entity.item.LOTREntityWargskinRug;
import lotr.client.render.entity.LOTRRenderPlate;
import lotr.common.entity.projectile.LOTREntityPlate;
import lotr.client.render.entity.LOTRRenderElf;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.client.render.entity.LOTRRenderSauron;
import lotr.common.entity.npc.LOTREntitySauron;
import lotr.client.render.entity.LOTRRenderSpear;
import lotr.common.entity.projectile.LOTREntitySpear;
import lotr.client.render.entity.LOTRRenderGandalfFireball;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import lotr.client.render.entity.LOTRRenderWarg;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.client.render.entity.LOTRRenderOrcBomb;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.client.render.entity.LOTRRenderShirePony;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.client.render.entity.LOTRRenderOrc;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.client.render.entity.LOTRRenderSmokeRing;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import lotr.client.render.entity.LOTRRenderHobbit;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.client.render.entity.LOTRRenderHorse;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.client.renderer.entity.Render;
import cpw.mods.fml.client.registry.RenderingRegistry;
import lotr.client.render.entity.LOTRRenderPortal;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import java.lang.reflect.Field;
import lotr.client.model.LOTRArmorModels;
import lotr.client.render.tileentity.LOTRRenderAnimalJar;
import lotr.common.item.LOTRItemAnimalJar;
import lotr.client.render.item.LOTRRenderSkullStaff;
import lotr.client.render.item.LOTRRenderBannerItem;
import lotr.client.render.item.LOTRRenderBlownItem;
import lotr.client.render.item.LOTRRenderInvTableCommand;
import lotr.client.render.item.LOTRRenderElvenBlade;
import lotr.common.item.LOTRItemSword;
import lotr.client.render.item.LOTRRenderBow;
import lotr.common.item.LOTRItemBow;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import lotr.client.render.item.LOTRRenderCrossbow;
import lotr.common.item.LOTRItemCrossbow;
import lotr.client.render.item.LOTRRenderLargeItem;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import lotr.client.sound.LOTRMusic;
import lotr.client.render.entity.LOTRSwingHandler;
import lotr.client.render.LOTRRenderPlayer;
import lotr.client.fx.LOTREffectRenderer;
import net.minecraft.util.ResourceLocation;
import lotr.common.LOTRCommonProxy;

public class LOTRClientProxy extends LOTRCommonProxy
{
    public static ResourceLocation enchantmentTexture;
    public static ResourceLocation alignmentTexture;
    public static ResourceLocation particlesTexture;
    public static ResourceLocation customEffectsTexture;
    public static int TESSELLATOR_MAX_BRIGHTNESS;
    public static int FONTRENDERER_ALPHA_MIN;
    public static LOTREffectRenderer customEffectRenderer;
    public static LOTRRenderPlayer specialPlayerRenderer;
    public static LOTRSwingHandler swingHandler;
    public static LOTRTickHandlerClient tickHandler;
    public static LOTRKeyHandler keyHandler;
    private static LOTRGuiHandler guiHandler;
    public static LOTRMusic musicHandler;
    private int beaconRenderID;
    private int barrelRenderID;
    private int orcBombRenderID;
    private int doubleTorchRenderID;
    private int mobSpawnerRenderID;
    private int plateRenderID;
    private int stalactiteRenderID;
    private int flowerPotRenderID;
    private int cloverRenderID;
    private int entJarRenderID;
    private int trollTotemRenderID;
    private int fenceRenderID;
    private int grassRenderID;
    private int fallenLeavesRenderID;
    private int commandTableRenderID;
    private int butterflyJarRenderID;
    private int unsmelteryRenderID;
    private int chestRenderID;
    private int reedsRenderID;
    private int wasteRenderID;
    private int beamRenderID;
    private int vCauldronRenderID;
    private int grapevineRenderID;
    private int thatchFloorRenderID;
    private int treasureRenderID;
    private int flowerRenderID;
    private int doublePlantRenderID;
    private int birdCageRenderID;
    private int rhunFireJarRenderID;
    private int coralRenderID;
    private int doorRenderID;
    private int ropeRenderID;
    private int orcChainRenderID;
    private int guldurilRenderID;
    
    @Override
    public void onPreload() {
        System.setProperty("fml.skipFirstTextureLoad", "false");
        try {
            for (final Field field : LOTRMod.class.getFields()) {
                if (field.get(null) instanceof Item) {
                    final Item item = (Item)field.get(null);
                    final LOTRRenderLargeItem largeItemRenderer = LOTRRenderLargeItem.getLargeIconSize(item);
                    final boolean large = largeItemRenderer != null;
                    if (item instanceof LOTRItemCrossbow) {
                        MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)new LOTRRenderCrossbow());
                    }
                    else if (item instanceof LOTRItemBow) {
                        MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)new LOTRRenderBow(largeItemRenderer));
                    }
                    else if (item instanceof LOTRItemSword && ((LOTRItemSword)item).isElvenBlade()) {
                        double d = 24.0;
                        if (item == LOTRMod.sting) {
                            d = 40.0;
                        }
                        MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)new LOTRRenderElvenBlade(d, largeItemRenderer));
                    }
                    else if (large) {
                        MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)largeItemRenderer);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(LOTRMod.commandTable), (IItemRenderer)new LOTRRenderInvTableCommand());
        MinecraftForgeClient.registerItemRenderer(LOTRMod.hobbitPipe, (IItemRenderer)new LOTRRenderBlownItem());
        MinecraftForgeClient.registerItemRenderer(LOTRMod.commandHorn, (IItemRenderer)new LOTRRenderBlownItem());
        MinecraftForgeClient.registerItemRenderer(LOTRMod.conquestHorn, (IItemRenderer)new LOTRRenderBlownItem());
        MinecraftForgeClient.registerItemRenderer(LOTRMod.banner, (IItemRenderer)new LOTRRenderBannerItem());
        MinecraftForgeClient.registerItemRenderer(LOTRMod.orcSkullStaff, (IItemRenderer)new LOTRRenderSkullStaff());
        for (final Item item2 : Item.itemRegistry) {
            if (item2 instanceof LOTRItemAnimalJar) {
                MinecraftForgeClient.registerItemRenderer(item2, (IItemRenderer)new LOTRRenderAnimalJar());
            }
        }
        LOTRArmorModels.setupArmorModels();
    }
    
    @Override
    public void onLoad() {
        LOTRClientProxy.customEffectRenderer = new LOTREffectRenderer(Minecraft.getMinecraft());
        LOTRTextures.load();
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityPortal.class, (Render)new LOTRRenderPortal());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHorse.class, (Render)new LOTRRenderHorse());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHobbit.class, (Render)new LOTRRenderHobbit());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySmokeRing.class, (Render)new LOTRRenderSmokeRing());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityOrc.class, (Render)new LOTRRenderOrc());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityShirePony.class, (Render)new LOTRRenderShirePony());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityOrcBomb.class, (Render)new LOTRRenderOrcBomb());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityWarg.class, (Render)new LOTRRenderWarg());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGandalfFireball.class, (Render)new LOTRRenderGandalfFireball());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySpear.class, (Render)new LOTRRenderSpear());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySauron.class, (Render)new LOTRRenderSauron());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityElf.class, (Render)new LOTRRenderElf());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityPlate.class, (Render)new LOTRRenderPlate());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityWargskinRug.class, (Render)new LOTRRenderWargskinRug());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySkeletalWraith.class, (Render)new LOTRRenderSkeleton());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorBlacksmith.class, (Render)new LOTRRenderGondorTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGaladhrimTrader.class, (Render)new LOTRRenderElvenTrader("galadhrimTrader_cloak"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityAlignmentBonus.class, (Render)new LOTRRenderAlignmentBonus());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDwarf.class, (Render)new LOTRRenderDwarf());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMarshWraith.class, (Render)new LOTRRenderMarshWraith());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMarshWraithBall.class, (Render)new LOTRRenderWraithBall());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDwarfCommander.class, (Render)new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBlueDwarfCommander.class, (Render)new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBlueDwarfMerchant.class, (Render)new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityThrowingAxe.class, (Render)new LOTRRenderThrowingAxe());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityCrossbowBolt.class, (Render)new LOTRRenderCrossbowBolt());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityTroll.class, (Render)new LOTRRenderTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityOlogHai.class, (Render)new LOTRRenderOlogHai());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityStoneTroll.class, (Render)new LOTRRenderStoneTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGollum.class, (Render)new LOTRRenderGollum());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMirkwoodSpider.class, (Render)new LOTRRenderMirkwoodSpider());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanMan.class, (Render)new LOTRRenderRohirrim());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityPebble.class, (Render)new RenderSnowball(LOTRMod.pebble));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMysteryWeb.class, (Render)new RenderSnowball(LOTRMod.mysteryWeb));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanBlacksmith.class, (Render)new LOTRRenderRohanTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRanger.class, (Render)new LOTRRenderRanger());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDunlending.class, (Render)new LOTRRenderDunlending());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDunlendingWarrior.class, (Render)new LOTRRenderDunlendingBase());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityEnt.class, (Render)new LOTRRenderEnt());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityTraderRespawn.class, (Render)new LOTRRenderTraderRespawn());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMountainTroll.class, (Render)new LOTRRenderMountainTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityThrownRock.class, (Render)new LOTRRenderThrownRock());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMountainTrollChieftain.class, (Render)new LOTRRenderMountainTrollChieftain());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHuornBase.class, (Render)new LOTRRenderHuorn());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanMeadhost.class, (Render)new LOTRRenderRohanTrader("outfit_meadhost"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityButterfly.class, (Render)new LOTRRenderButterfly());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBarrel.class, (Render)new LOTRRenderEntityBarrel());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMidges.class, (Render)new LOTRRenderMidges());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDeadMarshFace.class, (Render)new LOTRRenderDeadMarshFace());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityNurnSlave.class, (Render)new LOTRRenderNurnSlave());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRabbit.class, (Render)new LOTRRenderRabbit());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityWildBoar.class, (Render)new LOTRRenderWildBoar());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMordorSpider.class, (Render)new LOTRRenderMordorSpider());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBanner.class, (Render)new LOTRRenderBanner());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBannerWall.class, (Render)new LOTRRenderBannerWall());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityLionBase.class, (Render)new LOTRRenderLion());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGiraffe.class, (Render)new LOTRRenderGiraffe());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityZebra.class, (Render)new LOTRRenderZebra());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRhino.class, (Render)new LOTRRenderRhino());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityCrocodile.class, (Render)new LOTRRenderCrocodile());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityNearHaradrimBase.class, (Render)new LOTRRenderNearHaradrim());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityNearHaradrimWarlord.class, (Render)new LOTRRenderNearHaradrimWarlord());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGemsbok.class, (Render)new LOTRRenderGemsbok());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityFlamingo.class, (Render)new LOTRRenderFlamingo());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityScorpion.class, (Render)new LOTRRenderScorpion());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBird.class, (Render)new LOTRRenderBird());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityCamel.class, (Render)new LOTRRenderCamel());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBandit.class, (Render)new LOTRRenderBandit("bandit"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySaruman.class, (Render)new LOTRRenderSaruman());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityInvasionSpawner.class, (Render)new LOTRRenderInvasionSpawner());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityElk.class, (Render)new LOTRRenderElk());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMirkTroll.class, (Render)new LOTRRenderMirkTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityTermite.class, (Render)new LOTRRenderTermite());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityThrownTermite.class, (Render)new RenderSnowball(LOTRMod.termite));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDikDik.class, (Render)new LOTRRenderDikDik());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityUtumnoIceSpider.class, (Render)new LOTRRenderUtumnoIceSpider());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityConker.class, (Render)new RenderSnowball(LOTRMod.chestnut));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityUtumnoTroll.class, (Render)new LOTRRenderUtumnoTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBalrog.class, (Render)new LOTRRenderBalrog());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHalfTroll.class, (Render)new LOTRRenderHalfTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHalfTrollScavenger.class, (Render)new LOTRRenderHalfTrollScavenger());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGaladhrimSmith.class, (Render)new LOTRRenderElvenSmith("galadhrimSmith_cloak", "galadhrimSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHighElfSmith.class, (Render)new LOTRRenderElvenSmith("highElfSmith_cloak", "highElfSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityWoodElfSmith.class, (Render)new LOTRRenderElvenSmith("woodElfSmith_cloak", "woodElfSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDolAmrothSoldier.class, (Render)new LOTRRenderSwanKnight());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySwan.class, (Render)new LOTRRenderSwan());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMoredain.class, (Render)new LOTRRenderMoredain());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityAngmarHillman.class, (Render)new LOTRRenderAngmarHillman(true));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityAngmarHillmanWarrior.class, (Render)new LOTRRenderAngmarHillman(false));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityIronHillsMerchant.class, (Render)new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBossTrophy.class, (Render)new LOTRRenderBossTrophy());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMallornEnt.class, (Render)new LOTRRenderMallornEnt());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityMallornLeafBomb.class, (Render)new LOTRRenderMallornLeafBomb());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityScrapTrader.class, (Render)new LOTRRenderScrapTrader());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityTauredain.class, (Render)new LOTRRenderTauredain());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDart.class, (Render)new LOTRRenderDart());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBarrowWight.class, (Render)new LOTRRenderBarrowWight());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityTauredainShaman.class, (Render)new LOTRRenderTauredainShaman());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGaladhrimWarden.class, (Render)new LOTRRenderGaladhrimWarden());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDwarfSmith.class, (Render)new LOTRRenderDwarfSmith());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBlueMountainsSmith.class, (Render)new LOTRRenderDwarfSmith());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBanditHarad.class, (Render)new LOTRRenderBandit("harad"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDeer.class, (Render)new LOTRRenderDeer());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDaleMan.class, (Render)new LOTRRenderDaleMan());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDaleBlacksmith.class, (Render)new LOTRRenderDaleTrader("blacksmith_apron"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityNPCRespawner.class, (Render)new LOTRRenderNPCRespawner());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDorwinionMan.class, (Render)new LOTRRenderDorwinionMan());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDaleBaker.class, (Render)new LOTRRenderDaleTrader("baker_apron"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDorwinionElfVintner.class, (Render)new LOTRRenderDorwinionElfVintner());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityAurochs.class, (Render)new LOTRRenderAurochs());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityKineAraw.class, (Render)new LOTRRenderKineAraw());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorMan.class, (Render)new LOTRRenderGondorMan());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityFallingTreasure.class, (Render)new LOTRRenderFallingCoinPile());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorBartender.class, (Render)new LOTRRenderGondorTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorGreengrocer.class, (Render)new LOTRRenderGondorTrader("outfit_greengrocer"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorMason.class, (Render)new LOTRRenderGondorTrader("outfit_mason"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorBrewer.class, (Render)new LOTRRenderGondorTrader("outfit_brewer"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorFlorist.class, (Render)new LOTRRenderGondorTrader("outfit_florist"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorButcher.class, (Render)new LOTRRenderGondorTrader("outfit_butcher"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorBaker.class, (Render)new LOTRRenderGondorTrader("outfit_baker"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDunedain.class, (Render)new LOTRRenderDunedain());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityDunedainBlacksmith.class, (Render)new LOTRRenderDunedainTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanBuilder.class, (Render)new LOTRRenderRohanTrader("outfit_builder"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanBrewer.class, (Render)new LOTRRenderRohanTrader("outfit_brewer"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanButcher.class, (Render)new LOTRRenderRohanTrader("outfit_butcher"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanBaker.class, (Render)new LOTRRenderRohanTrader("outfit_baker"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRohanOrcharder.class, (Render)new LOTRRenderRohanTrader("outfit_orcharder"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBear.class, (Render)new LOTRRenderBear());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityEasterling.class, (Render)new LOTRRenderEasterling());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityEasterlingBlacksmith.class, (Render)new LOTRRenderEasterlingTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityFallingFireJar.class, (Render)new LOTRRenderFallingFireJar());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityFirePot.class, (Render)new RenderSnowball(LOTRMod.rhunFirePot));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRivendellSmith.class, (Render)new LOTRRenderElvenSmith("rivendellSmith_cloak", "rivendellSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityRivendellTrader.class, (Render)new LOTRRenderElvenTrader("rivendellTrader_cloak"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityFish.class, (Render)new LOTRRenderFish());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityArrowPoisoned.class, (Render)new LOTRRenderArrowPoisoned());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityNearHaradBlacksmith.class, (Render)new LOTRRenderNearHaradTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySnowTroll.class, (Render)new LOTRRenderSnowTroll());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityLionRug.class, (Render)new LOTRRenderLionRug());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityBearRug.class, (Render)new LOTRRenderBearRug());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGiraffeRug.class, (Render)new LOTRRenderGiraffeRug());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHaradSlave.class, (Render)new LOTRRenderHaradSlave());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGondorRenegade.class, (Render)new LOTRRenderGondorRenegade());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityHarnedorBartender.class, (Render)new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntitySouthronBartender.class, (Render)new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityUmbarBartender.class, (Render)new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGulfBartender.class, (Render)new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityWhiteOryx.class, (Render)new LOTRRenderWhiteOryx());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityGandalf.class, (Render)new LOTRRenderGandalf());
        RenderingRegistry.registerEntityRenderingHandler((Class)LOTREntityWickedDwarf.class, (Render)new LOTRRenderWickedDwarf());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityPotion.class, (Render)new RenderSnowball((Item)Items.potionitem, 16384));
        this.beaconRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.orcBombRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.doubleTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.mobSpawnerRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.plateRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.stalactiteRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.flowerPotRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.cloverRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.entJarRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.trollTotemRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.fenceRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.grassRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.fallenLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.commandTableRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.butterflyJarRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.unsmelteryRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.chestRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.reedsRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.wasteRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.beamRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.vCauldronRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.grapevineRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.thatchFloorRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.treasureRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.flowerRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.doublePlantRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.birdCageRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.rhunFireJarRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.coralRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.doorRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.ropeRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.orcChainRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.guldurilRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(this.beaconRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.barrelRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.orcBombRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.doubleTorchRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.mobSpawnerRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.plateRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.stalactiteRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.flowerPotRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.cloverRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.entJarRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.trollTotemRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.fenceRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.grassRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.fallenLeavesRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.commandTableRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.butterflyJarRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.unsmelteryRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.chestRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.reedsRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.wasteRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.beamRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.vCauldronRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.grapevineRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.thatchFloorRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.treasureRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.flowerRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.doublePlantRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.birdCageRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.rhunFireJarRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.coralRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.doorRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.ropeRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.orcChainRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.guldurilRenderID, (ISimpleBlockRenderingHandler)new LOTRRenderBlocks(true));
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityBeacon.class, (TileEntitySpecialRenderer)new LOTRRenderBeacon());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityMobSpawner.class, (TileEntitySpecialRenderer)new LOTRTileEntityMobSpawnerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityPlate.class, (TileEntitySpecialRenderer)new LOTRRenderPlateFood());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityElvenPortal.class, (TileEntitySpecialRenderer)new LOTRRenderElvenPortal());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntitySpawnerChest.class, (TileEntitySpecialRenderer)new LOTRRenderSpawnerChest());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityGulduril.class, (TileEntitySpecialRenderer)new LOTRRenderGuldurilGlow());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityDwarvenDoor.class, (TileEntitySpecialRenderer)new LOTRRenderDwarvenDoor());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityMorgulPortal.class, (TileEntitySpecialRenderer)new LOTRRenderMorgulPortal());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityArmorStand.class, (TileEntitySpecialRenderer)new LOTRRenderArmorStand());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityMug.class, (TileEntitySpecialRenderer)new LOTRRenderMug());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityEntJar.class, (TileEntitySpecialRenderer)new LOTRRenderEntJar());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityTrollTotem.class, (TileEntitySpecialRenderer)new LOTRRenderTrollTotem());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityUtumnoPortal.class, (TileEntitySpecialRenderer)new LOTRRenderUtumnoPortal());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityUtumnoReturnPortal.class, (TileEntitySpecialRenderer)new LOTRRenderUtumnoReturnPortal());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityCommandTable.class, (TileEntitySpecialRenderer)new LOTRRenderCommandTable());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityAnimalJar.class, (TileEntitySpecialRenderer)new LOTRRenderAnimalJar());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityUnsmeltery.class, (TileEntitySpecialRenderer)new LOTRRenderUnsmeltery());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityDartTrap.class, (TileEntitySpecialRenderer)new LOTRRenderDartTrap());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityChest.class, (TileEntitySpecialRenderer)new LOTRRenderChest());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityWeaponRack.class, (TileEntitySpecialRenderer)new LOTRRenderWeaponRack());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntityKebabStand.class, (TileEntitySpecialRenderer)new LOTRRenderKebabStand());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntitySignCarved.class, (TileEntitySpecialRenderer)new LOTRRenderSignCarved());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)LOTRTileEntitySignCarvedIthildin.class, (TileEntitySpecialRenderer)new LOTRRenderSignCarvedIthildin());
    }
    
    @Override
    public void onPostload() {
        if (LOTRConfig.updateLangFiles) {
            LOTRLang.runUpdateThread();
        }
        LOTRClientProxy.musicHandler = new LOTRMusic();
    }
    
    @Override
    public void testReflection(final World world) {
        LOTRReflection.testAll(world);
        LOTRReflectionClient.testAll(world, Minecraft.getMinecraft());
    }
    
    public static void renderEnchantmentEffect() {
        final Tessellator tessellator = Tessellator.instance;
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        GL11.glDepthFunc(514);
        GL11.glDisable(2896);
        texturemanager.bindTexture(LOTRClientProxy.enchantmentTexture);
        GL11.glEnable(3042);
        GL11.glBlendFunc(768, 1);
        final float shade = 0.76f;
        GL11.glColor4f(0.5f * shade, 0.25f * shade, 0.8f * shade, 1.0f);
        GL11.glMatrixMode(5890);
        GL11.glPushMatrix();
        final float scale = 0.125f;
        GL11.glScalef(scale, scale, scale);
        float randomShift = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
        GL11.glTranslatef(randomShift, 0.0f, 0.0f);
        GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
        ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        randomShift = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
        GL11.glTranslatef(-randomShift, 0.0f, 0.0f);
        GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
        ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glDepthFunc(515);
    }
    
    public static void sendClientInfoPacket(final LOTRFaction viewingFaction, final Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap) {
        final boolean showWP = LOTRGuiMap.showWP;
        final boolean showCWP = LOTRGuiMap.showCWP;
        final boolean showHiddenSWP = LOTRGuiMap.showHiddenSWP;
        final LOTRPacketClientInfo packet = new LOTRPacketClientInfo(viewingFaction, changedRegionMap, showWP, showCWP, showHiddenSWP);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    public static int getAlphaInt(final float alphaF) {
        int alphaI = (int)(alphaF * 255.0f);
        alphaI = MathHelper.clamp_int(alphaI, LOTRClientProxy.FONTRENDERER_ALPHA_MIN, 255);
        return alphaI;
    }
    
    @Override
    public boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }
    
    @Override
    public boolean isSingleplayer() {
        return Minecraft.getMinecraft().isSingleplayer();
    }
    
    @Override
    public World getClientWorld() {
        return (World)Minecraft.getMinecraft().theWorld;
    }
    
    @Override
    public EntityPlayer getClientPlayer() {
        return (EntityPlayer)Minecraft.getMinecraft().thePlayer;
    }
    
    public static boolean doesClientChunkExist(final World world, final int i, final int k) {
        final int chunkX = i >> 4;
        final int chunkZ = k >> 4;
        final Chunk chunk = world.getChunkProvider().provideChunk(chunkX, chunkZ);
        return !(chunk instanceof EmptyChunk);
    }
    
    @Override
    public boolean isPaused() {
        return Minecraft.getMinecraft().func_147113_T();
    }
    
    @Override
    public void setClientDifficulty(final EnumDifficulty difficulty) {
        Minecraft.getMinecraft().gameSettings.difficulty = difficulty;
    }
    
    @Override
    public void setWaypointModes(final boolean showWP, final boolean showCWP, final boolean showHiddenSWP) {
        LOTRGuiMap.showWP = showWP;
        LOTRGuiMap.showCWP = showCWP;
        LOTRGuiMap.showHiddenSWP = showHiddenSWP;
    }
    
    @Override
    public void spawnAlignmentBonus(final LOTRFaction faction, final float prevMainAlignment, final LOTRAlignmentBonusMap factionBonusMap, final String name, final boolean isKill, final float conquestBonus, final double posX, final double posY, final double posZ) {
        final World world = this.getClientWorld();
        if (world != null) {
            final LOTREntityAlignmentBonus entity = new LOTREntityAlignmentBonus(world, posX, posY, posZ, name, faction, prevMainAlignment, factionBonusMap, isKill, conquestBonus);
            world.spawnEntityInWorld((Entity)entity);
        }
    }
    
    @Override
    public void displayAlignDrain(final int numFactions) {
        LOTRTickHandlerClient.alignDrainTick = 200;
        LOTRTickHandlerClient.alignDrainNum = numFactions;
    }
    
    @Override
    public void queueAchievement(final LOTRAchievement achievement) {
        LOTRTickHandlerClient.notificationDisplay.queueAchievement(achievement);
    }
    
    @Override
    public void queueFellowshipNotification(final IChatComponent message) {
        LOTRTickHandlerClient.notificationDisplay.queueFellowshipNotification(message);
    }
    
    @Override
    public void queueConquestNotification(final LOTRFaction fac, final float conq, final boolean isCleansing) {
        LOTRTickHandlerClient.notificationDisplay.queueConquest(fac, conq, isCleansing);
    }
    
    @Override
    public void displayMessage(final LOTRGuiMessageTypes message) {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new LOTRGuiMessage(message));
    }
    
    @Override
    public void openHiredNPCGui(final LOTREntityNPC npc) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
            mc.displayGuiScreen((GuiScreen)new LOTRGuiHiredWarrior(npc));
        }
        else if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
            mc.displayGuiScreen((GuiScreen)new LOTRGuiHiredFarmer(npc));
        }
    }
    
    @Override
    public void setMapIsOp(final boolean isOp) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiMap) {
            final LOTRGuiMap map = (LOTRGuiMap)gui;
            map.isPlayerOp = isOp;
        }
    }
    
    @Override
    public void displayFTScreen(final LOTRAbstractWaypoint waypoint, final int startX, final int startZ) {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen((GuiScreen)new LOTRGuiFastTravel(waypoint, startX, startZ));
    }
    
    @Override
    public void showFrostOverlay() {
        LOTRClientProxy.tickHandler.onFrostDamage();
    }
    
    @Override
    public void showBurnOverlay() {
        LOTRClientProxy.tickHandler.onBurnDamage();
    }
    
    @Override
    public void clearMapPlayerLocations() {
        LOTRGuiMap.clearPlayerLocations();
    }
    
    @Override
    public void addMapPlayerLocation(final GameProfile player, final double posX, final double posZ) {
        LOTRGuiMap.addPlayerLocationInfo(player, posX, posZ);
    }
    
    @Override
    public void setMapCWPProtectionMessage(final IChatComponent message) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiMap) {
            ((LOTRGuiMap)gui).setCWPProtectionMessage(message);
        }
    }
    
    @Override
    public void displayBannerGui(final LOTREntityBanner banner) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = new LOTRGuiBanner(banner);
        mc.displayGuiScreen(gui);
    }
    
    @Override
    public void invalidateBannerUsername(final LOTREntityBanner banner, final int slot, final String prevText) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiBanner) {
            final LOTRGuiBanner guiBanner = (LOTRGuiBanner)gui;
            if (guiBanner.theBanner == banner) {
                guiBanner.setUsernameInvalid(slot, prevText);
            }
        }
    }
    
    @Override
    public void clientReceiveSpeech(final LOTREntityNPC npc, final String speech) {
        LOTRSpeechClient.receiveSpeech(npc, speech);
    }
    
    @Override
    public void displayNewDate() {
        LOTRClientProxy.tickHandler.updateDate();
    }
    
    @Override
    public void displayMiniquestOffer(final LOTRMiniQuest quest, final LOTREntityNPC npc) {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen((GuiScreen)new LOTRGuiMiniquestOffer(quest, npc));
    }
    
    @Override
    public void setTrackedQuest(final LOTRMiniQuest quest) {
        LOTRTickHandlerClient.miniquestTracker.setTrackedQuest(quest);
    }
    
    @Override
    public void displayAlignmentSee(final String username, final Map<LOTRFaction, Float> alignments) {
        final LOTRGuiFactions gui = new LOTRGuiFactions();
        gui.setOtherPlayer(username, alignments);
        final Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen((GuiScreen)gui);
    }
    
    @Override
    public void displayAlignmentChoice() {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen((GuiScreen)new LOTRGuiAlignmentChoices());
    }
    
    @Override
    public void cancelItemHighlight() {
        LOTRClientProxy.tickHandler.cancelItemHighlight = true;
    }
    
    @Override
    public void receiveConquestGrid(final LOTRFaction conqFac, final List<LOTRConquestZone> allZones) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiMap) {
            ((LOTRGuiMap)gui).receiveConquestGrid(conqFac, allZones);
        }
    }
    
    @Override
    public void setInPortal(final EntityPlayer entityplayer) {
        if (!LOTRTickHandlerClient.playersInPortals.containsKey(entityplayer)) {
            LOTRTickHandlerClient.playersInPortals.put(entityplayer, 0);
        }
        if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
            LOTRTickHandlerServer.playersInPortals.put(entityplayer, 0);
        }
    }
    
    @Override
    public void setInElvenPortal(final EntityPlayer entityplayer) {
        if (!LOTRTickHandlerClient.playersInElvenPortals.containsKey(entityplayer)) {
            LOTRTickHandlerClient.playersInElvenPortals.put(entityplayer, 0);
        }
        if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInElvenPortals.containsKey(entityplayer)) {
            LOTRTickHandlerServer.playersInElvenPortals.put(entityplayer, 0);
        }
    }
    
    @Override
    public void setInMorgulPortal(final EntityPlayer entityplayer) {
        if (!LOTRTickHandlerClient.playersInMorgulPortals.containsKey(entityplayer)) {
            LOTRTickHandlerClient.playersInMorgulPortals.put(entityplayer, 0);
        }
        if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInMorgulPortals.containsKey(entityplayer)) {
            LOTRTickHandlerServer.playersInMorgulPortals.put(entityplayer, 0);
        }
    }
    
    @Override
    public void setInUtumnoReturnPortal(final EntityPlayer entityplayer) {
        if (entityplayer == Minecraft.getMinecraft().thePlayer) {
            LOTRClientProxy.tickHandler.inUtumnoReturnPortal = true;
        }
    }
    
    @Override
    public void setUtumnoReturnPortalCoords(final EntityPlayer entityplayer, final int x, final int z) {
        if (entityplayer == Minecraft.getMinecraft().thePlayer) {
            LOTRClientProxy.tickHandler.inUtumnoReturnPortal = true;
            LOTRClientProxy.tickHandler.utumnoReturnX = x;
            LOTRClientProxy.tickHandler.utumnoReturnZ = z;
        }
    }
    
    @Override
    public void spawnParticle(final String type, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.renderViewEntity != null && mc.theWorld != null) {
            final World world = (World)mc.theWorld;
            int i = mc.gameSettings.particleSetting;
            if (i == 1 && world.rand.nextInt(3) == 0) {
                i = 2;
            }
            if (i > 1) {
                return;
            }
            if (type.equals("blueFlame")) {
                LOTRClientProxy.customEffectRenderer.addEffect((EntityFX)new LOTREntityBlueFlameFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("chill")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityChillFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.startsWith("elvenGlow")) {
                final LOTREntityElvenGlowFX fx = new LOTREntityElvenGlowFX(world, d, d1, d2, d3, d4, d5);
                final int subIndex = type.indexOf("_");
                if (subIndex > -1) {
                    final String hex = type.substring(subIndex + 1);
                    final int color = Integer.parseInt(hex, 16);
                    fx.setElvenGlowColor(color);
                }
                mc.effectRenderer.addEffect((EntityFX)fx);
            }
            else if (type.equals("gandalfFireball")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityGandalfFireballExplodeFX(world, d, d1, d2));
            }
            else if (type.equals("largeStone")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.stone, 0));
            }
            else if (type.startsWith("leaf")) {
                final String s = type.substring("leaf".length());
                int textureIndex = 0;
                if (s.startsWith("Gold")) {
                    textureIndex = 16 + world.rand.nextInt(2);
                }
                else if (s.startsWith("Red")) {
                    textureIndex = 18 + world.rand.nextInt(2);
                }
                else if (s.startsWith("Mirk")) {
                    textureIndex = 20 + world.rand.nextInt(2);
                }
                else if (s.startsWith("Green")) {
                    textureIndex = 22 + world.rand.nextInt(2);
                }
                if (textureIndex > 0) {
                    if (type.indexOf("_") > -1) {
                        final int age = Integer.parseInt(type.substring(type.indexOf("_") + 1));
                        LOTRClientProxy.customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, textureIndex, age));
                    }
                    else {
                        LOTRClientProxy.customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, textureIndex));
                    }
                }
            }
            else if (type.equals("marshFlame")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityMarshFlameFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("marshLight")) {
                LOTRClientProxy.customEffectRenderer.addEffect((EntityFX)new LOTREntityMarshLightFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.startsWith("mEntHeal")) {
                final String[] args = type.split("_", 3);
                final Block block = Block.getBlockById(Integer.parseInt(args[1]));
                final int meta = Integer.parseInt(args[2]);
                final int color = block.getRenderColor(meta);
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityMallornEntHealFX(world, d, d1, d2, d3, d4, d5, block, meta, color));
            }
            else if (type.equals("mEntJumpSmash")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, LOTRMod.wood, 1));
            }
            else if (type.equals("mEntSpawn")) {
                Block block2 = null;
                int meta2 = 0;
                if (world.rand.nextBoolean()) {
                    block2 = Blocks.dirt;
                    meta2 = 0;
                }
                else {
                    block2 = LOTRMod.wood;
                    meta2 = 1;
                }
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block2, meta2));
            }
            else if (type.startsWith("mEntSummon")) {
                final String[] args = type.split("_", 6);
                final int summonerID = Integer.parseInt(args[1]);
                final int summonedID = Integer.parseInt(args[2]);
                final float arcParam = Float.parseFloat(args[3]);
                final Block block3 = Block.getBlockById(Integer.parseInt(args[4]));
                final int meta3 = Integer.parseInt(args[5]);
                final int color2 = block3.getRenderColor(meta3);
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityMallornEntSummonFX(world, d, d1, d2, d3, d4, d5, summonerID, summonedID, arcParam, block3, meta3, color2));
            }
            else if (type.equals("mirkwoodWater")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.mirkwoodCorrupted.getWaterColorMultiplier()));
            }
            else if (type.equals("morgulPortal")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityMorgulPortalFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("morgulWater")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.morgulVale.getWaterColorMultiplier()));
            }
            else if (type.equals("mtcArmor")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.iron_block, 0));
            }
            else if (type.equals("mtcHeal")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityMTCHealFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("mtcSpawn")) {
                Block block2 = null;
                int meta2 = 0;
                if (world.rand.nextBoolean()) {
                    block2 = Blocks.stone;
                    meta2 = 0;
                }
                else if (world.rand.nextBoolean()) {
                    block2 = Blocks.dirt;
                    meta2 = 0;
                }
                else if (world.rand.nextBoolean()) {
                    block2 = Blocks.gravel;
                    meta2 = 0;
                }
                else {
                    block2 = (Block)Blocks.sand;
                    meta2 = 0;
                }
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block2, meta2));
            }
            else if (type.equals("music")) {
                final double pitch = world.rand.nextDouble();
                final EntityFX note = (EntityFX)new LOTREntityMusicFX(world, d, d1, d2, d3, d4, d5, pitch);
                mc.effectRenderer.addEffect(note);
            }
            else if (type.equals("quenditeSmoke")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityQuenditeSmokeFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("utumnoKill")) {
                LOTRClientProxy.customEffectRenderer.addEffect((EntityFX)new LOTREntityUtumnoKillFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("wave")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityWaveFX(world, d, d1, d2, d3, d4, d5));
            }
            else if (type.equals("whiteSmoke")) {
                mc.effectRenderer.addEffect((EntityFX)new LOTREntityWhiteSmokeFX(world, d, d1, d2, d3, d4, d5));
            }
        }
    }
    
    @Override
    public void placeFlowerInPot(final World world, final int i, final int j, final int k, final int side, final ItemStack itemstack) {
        if (!world.isClient) {
            super.placeFlowerInPot(world, i, j, k, side, itemstack);
        }
        else {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
        }
    }
    
    @Override
    public void fillMugFromCauldron(final World world, final int i, final int j, final int k, final int side, final ItemStack itemstack) {
        if (!world.isClient) {
            super.fillMugFromCauldron(world, i, j, k, side, itemstack);
        }
        else {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
        }
    }
    
    @Override
    public void renderCustomPotionEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc) {
        final Potion potion = Potion.potionTypes[effect.getPotionID()];
        mc.getTextureManager().bindTexture(LOTRClientProxy.customEffectsTexture);
        final int l = potion.getStatusIconIndex();
        final GuiScreen screen = mc.currentScreen;
        if (screen != null) {
            screen.drawTexturedModalRect(x + 6, y + 7, 0 + l % 8 * 18, 0 + l / 8 * 18, 18, 18);
        }
    }
    
    @Override
    public int getBeaconRenderID() {
        return this.beaconRenderID;
    }
    
    @Override
    public int getBarrelRenderID() {
        return this.barrelRenderID;
    }
    
    @Override
    public int getOrcBombRenderID() {
        return this.orcBombRenderID;
    }
    
    @Override
    public int getDoubleTorchRenderID() {
        return this.doubleTorchRenderID;
    }
    
    @Override
    public int getMobSpawnerRenderID() {
        return this.mobSpawnerRenderID;
    }
    
    @Override
    public int getPlateRenderID() {
        return this.plateRenderID;
    }
    
    @Override
    public int getStalactiteRenderID() {
        return this.stalactiteRenderID;
    }
    
    @Override
    public int getFlowerPotRenderID() {
        return this.flowerPotRenderID;
    }
    
    @Override
    public int getCloverRenderID() {
        return this.cloverRenderID;
    }
    
    @Override
    public int getEntJarRenderID() {
        return this.entJarRenderID;
    }
    
    @Override
    public int getTrollTotemRenderID() {
        return this.trollTotemRenderID;
    }
    
    @Override
    public int getFenceRenderID() {
        return this.fenceRenderID;
    }
    
    @Override
    public int getGrassRenderID() {
        return this.grassRenderID;
    }
    
    @Override
    public int getFallenLeavesRenderID() {
        return this.fallenLeavesRenderID;
    }
    
    @Override
    public int getCommandTableRenderID() {
        return this.commandTableRenderID;
    }
    
    @Override
    public int getButterflyJarRenderID() {
        return this.butterflyJarRenderID;
    }
    
    @Override
    public int getUnsmelteryRenderID() {
        return this.unsmelteryRenderID;
    }
    
    @Override
    public int getChestRenderID() {
        return this.chestRenderID;
    }
    
    @Override
    public int getReedsRenderID() {
        return this.reedsRenderID;
    }
    
    @Override
    public int getWasteRenderID() {
        return this.wasteRenderID;
    }
    
    @Override
    public int getBeamRenderID() {
        return this.beamRenderID;
    }
    
    @Override
    public int getVCauldronRenderID() {
        return this.vCauldronRenderID;
    }
    
    @Override
    public int getGrapevineRenderID() {
        return this.grapevineRenderID;
    }
    
    @Override
    public int getThatchFloorRenderID() {
        return this.thatchFloorRenderID;
    }
    
    @Override
    public int getTreasureRenderID() {
        return this.treasureRenderID;
    }
    
    @Override
    public int getFlowerRenderID() {
        return this.flowerRenderID;
    }
    
    @Override
    public int getDoublePlantRenderID() {
        return this.doublePlantRenderID;
    }
    
    @Override
    public int getBirdCageRenderID() {
        return this.birdCageRenderID;
    }
    
    @Override
    public int getRhunFireJarRenderID() {
        return this.rhunFireJarRenderID;
    }
    
    @Override
    public int getCoralRenderID() {
        return this.coralRenderID;
    }
    
    @Override
    public int getDoorRenderID() {
        return this.doorRenderID;
    }
    
    @Override
    public int getRopeRenderID() {
        return this.ropeRenderID;
    }
    
    @Override
    public int getOrcChainRenderID() {
        return this.orcChainRenderID;
    }
    
    @Override
    public int getGuldurilRenderID() {
        return this.guldurilRenderID;
    }
    
    static {
        LOTRClientProxy.enchantmentTexture = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        LOTRClientProxy.alignmentTexture = new ResourceLocation("lotr:gui/alignment.png");
        LOTRClientProxy.particlesTexture = new ResourceLocation("lotr:misc/particles.png");
        LOTRClientProxy.customEffectsTexture = new ResourceLocation("lotr:gui/effects.png");
        LOTRClientProxy.TESSELLATOR_MAX_BRIGHTNESS = 15728880;
        LOTRClientProxy.FONTRENDERER_ALPHA_MIN = 4;
        LOTRClientProxy.specialPlayerRenderer = new LOTRRenderPlayer();
        LOTRClientProxy.swingHandler = new LOTRSwingHandler();
        LOTRClientProxy.tickHandler = new LOTRTickHandlerClient();
        LOTRClientProxy.keyHandler = new LOTRKeyHandler();
        LOTRClientProxy.guiHandler = new LOTRGuiHandler();
    }
}
