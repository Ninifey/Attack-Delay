// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import lotr.common.entity.LOTREntityInvasionSpawner;
import java.util.Collections;
import java.util.Arrays;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraft.world.Explosion;
import java.util.Collection;
import net.minecraft.world.ChunkPosition;
import java.util.ArrayList;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import java.util.Iterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTUtil;
import cpw.mods.fml.common.network.NetworkRegistry;
import lotr.common.network.LOTRPacketUtumnoKill;
import lotr.common.block.LOTRBlockUtumnoReturnPortalBase;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import lotr.common.entity.npc.LOTREntityOlogHai;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.command.IEntitySelector;
import lotr.common.fac.LOTRFactionBounties;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import lotr.common.enchant.LOTREnchantmentWeaponSpecial;
import lotr.common.network.LOTRPacketWeaponFX;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.item.LOTRItemDagger;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.network.LOTRPacketStopItemUse;
import lotr.common.item.LOTRWeaponStats;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import lotr.common.entity.projectile.LOTREntitySpear;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EntityDamageSourceIndirect;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import lotr.common.entity.npc.LOTREntityHuornBase;
import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import lotr.common.entity.npc.LOTREntityRanger;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import com.mojang.authlib.GameProfile;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.util.IChatComponent;
import java.util.UUID;
import lotr.common.item.LOTRItemBrandingIron;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentText;
import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRUnitTradeable;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.item.LOTRItemDye;
import net.minecraft.entity.passive.EntityWolf;
import lotr.common.entity.animal.LOTREntityZebra;
import net.minecraft.entity.passive.EntityCow;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.entity.EntityHanging;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import lotr.common.entity.LOTRPlateFallingInfo;
import net.minecraft.util.DamageSource;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.EnumSkyBlock;
import lotr.common.world.biome.LOTRBiomeGenForodwaith;
import lotr.common.item.LOTRItemLance;
import lotr.common.entity.npc.LOTREntityWoodElfScout;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemArmor;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityHobbitShirriff;
import lotr.common.world.biome.LOTRBiomeGenShire;
import lotr.common.world.biome.LOTRBiomeGenDeadMarshes;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraftforge.event.entity.living.LivingEvent;
import lotr.common.entity.item.LOTREntityBanner;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketEntityUUID;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.projectile.LOTREntityFishHook;
import net.minecraft.entity.projectile.EntityFishHook;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.LOTREntityRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.LOTRWorldProvider;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.item.ItemFishingRod;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import lotr.common.block.LOTRVanillaSaplings;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;
import net.minecraft.block.material.Material;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.IGrowable;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.BlockLog;
import net.minecraftforge.event.entity.player.BonemealEvent;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.item.ItemBow;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import java.util.List;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import lotr.common.entity.npc.LOTREntityEnt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.block.LOTRBlockRottenLog;
import net.minecraft.world.IBlockAccess;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.item.ItemShears;
import net.minecraft.block.BlockWeb;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.block.LOTRBlockBookshelfStorage;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.block.LOTRBlockGrapevine;
import lotr.common.item.LOTRItemPartyHat;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemFeatherDyed;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.item.LOTRItemHobbitPipe;
import lotr.common.item.LOTRItemPouch;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import net.minecraft.block.BlockCauldron;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.item.LOTRItemMug;
import lotr.common.block.LOTRBlockFlowerPot;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockDoor;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.entity.Entity;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import lotr.common.fac.LOTRFactionRelations;
import net.minecraft.server.MinecraftServer;
import lotr.common.world.LOTRTeleporter;
import net.minecraftforge.common.DimensionManager;
import lotr.common.world.LOTRWorldTypeMiddleEarth;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemTool;
import lotr.common.block.LOTRBlockSaplingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import lotr.common.inventory.LOTRContainerCraftingTable;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import lotr.common.item.LOTRItemBow;
import cpw.mods.fml.common.IFuelHandler;

public class LOTREventHandler implements IFuelHandler
{
    private LOTRItemBow proxyBowItemServer;
    private LOTRItemBow proxyBowItemClient;
    
    public LOTREventHandler() {
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.TERRAIN_GEN_BUS.register((Object)this);
        GameRegistry.registerFuelHandler((IFuelHandler)this);
        new LOTRStructureTimelapse();
    }
    
    @SubscribeEvent
    public void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals("lotr")) {
            LOTRConfig.load();
        }
    }
    
    @SubscribeEvent
    public void onCrafting(final PlayerEvent.ItemCraftedEvent event) {
        final EntityPlayer entityplayer = event.player;
        final ItemStack itemstack = event.crafting;
        final IInventory craftingInventory = event.craftMatrix;
        if (!((Entity)entityplayer).worldObj.isClient) {
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Elven) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useElvenTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Uruk) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useUrukTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Rohirric) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRohirricTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Gondorian) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGondorianTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.WoodElven) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useWoodElvenTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dwarven) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Morgul) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMorgulTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dunlending) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDunlendingTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Angmar) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useAngmarTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.NearHarad) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useNearHaradTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.HighElven) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHighElvenTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.BlueDwarven) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useBlueDwarvenTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Ranger) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRangerTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.DolGuldur) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDolGuldurTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Gundabad) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGundabadTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.HalfTroll) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHalfTrollTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.DolAmroth) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDolAmrothTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Moredain) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMoredainTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Tauredain) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useTauredainTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dale) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDaleTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dorwinion) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDorwinionTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Hobbit) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHobbitTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Rhun) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRhunTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Rivendell) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRivendellTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Umbar) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useUmbarTable);
            }
            if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Gulf) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGulfTable);
            }
            if (itemstack.getItem() == Items.saddle) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftSaddle);
            }
            if (itemstack.getItem() == LOTRMod.bronze) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftBronze);
            }
            if (itemstack.getItem() == LOTRMod.appleCrumbleItem) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAppleCrumble);
            }
            if (itemstack.getItem() == LOTRMod.rabbitStew) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftRabbitStew);
            }
            if (itemstack.getItem() == LOTRMod.saltedFlesh) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftSaltedFlesh);
            }
            if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.brick) && itemstack.getItemDamage() == 10) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftMithrilDwarvenBrick);
            }
            if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.orcBomb)) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftOrcBomb);
            }
            if (itemstack.getItem() == LOTRMod.utumnoKey) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftUtumnoKey);
            }
        }
    }
    
    @SubscribeEvent
    public void onSmelting(final PlayerEvent.ItemSmeltedEvent event) {
        final EntityPlayer entityplayer = event.player;
        final ItemStack itemstack = event.smelting;
        if (!((Entity)entityplayer).worldObj.isClient) {
            if (itemstack.getItem() == LOTRMod.bronze) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.alloyBronze);
            }
            if (itemstack.getItem() == LOTRMod.deerCooked) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.cookDeer);
            }
            if (itemstack.getItem() == LOTRMod.blueDwarfSteel) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltBlueDwarfSteel);
            }
            if (itemstack.getItem() == LOTRMod.elfSteel) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltElfSteel);
            }
            if (itemstack.getItem() == LOTRMod.dwarfSteel) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltDwarfSteel);
            }
            if (itemstack.getItem() == LOTRMod.urukSteel) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltUrukSteel);
            }
            if (itemstack.getItem() == LOTRMod.orcSteel) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltOrcSteel);
            }
            if (itemstack.getItem() == LOTRMod.blackUrukSteel) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltBlackUrukSteel);
            }
        }
    }
    
    public int getBurnTime(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        if (item instanceof ItemBlock && ((ItemBlock)item).field_150939_a instanceof LOTRBlockSaplingBase) {
            return 100;
        }
        if (item == LOTRMod.nauriteGem) {
            return 600;
        }
        if (item == Item.getItemFromBlock(LOTRMod.blockOreStorage) && itemstack.getItemDamage() == 10) {
            return 6000;
        }
        if (item == LOTRMod.mallornStick) {
            return 100;
        }
        if (item instanceof ItemTool && ((ItemTool)item).func_150913_i() == LOTRMaterial.MALLORN.toToolMaterial()) {
            return 200;
        }
        if (item instanceof ItemSword && ((ItemSword)item).func_150932_j().equals(LOTRMaterial.MALLORN.toToolMaterial().toString())) {
            return 200;
        }
        if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals(LOTRMaterial.MALLORN.toToolMaterial().toString())) {
            return 200;
        }
        if (item == Items.reeds || item == Item.getItemFromBlock(LOTRMod.reeds) || item == Item.getItemFromBlock(LOTRMod.driedReeds) || item == Item.getItemFromBlock(LOTRMod.cornStalk)) {
            return 100;
        }
        return 0;
    }
    
    @SubscribeEvent
    public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        final EntityPlayer entityplayer = event.player;
        final World world = ((Entity)entityplayer).worldObj;
        if (!world.isClient) {
            final EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
            if (world.provider.terrainType instanceof LOTRWorldTypeMiddleEarth && ((Entity)entityplayermp).dimension == 0 && !LOTRLevelData.getData((EntityPlayer)entityplayermp).getTeleportedME()) {
                final int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
                final Teleporter teleporter = new LOTRTeleporter(DimensionManager.getWorld(dimension), false);
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayermp, dimension, teleporter);
                LOTRLevelData.getData((EntityPlayer)entityplayermp).setTeleportedME(true);
            }
            LOTRLevelData.sendLoginPacket(entityplayermp);
            LOTRLevelData.sendPlayerData(entityplayermp);
            LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, world);
            LOTRLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, world);
            LOTRLevelData.sendShieldToAllPlayersInWorld((EntityPlayer)entityplayermp, world);
            LOTRLevelData.sendAllShieldsInWorldToPlayer((EntityPlayer)entityplayermp, world);
            LOTRDate.sendUpdatePacket(entityplayermp, false);
            LOTRFactionRelations.sendAllRelationsTo(entityplayermp);
            LOTRLevelData.getData((EntityPlayer)entityplayermp).send35AlignmentChoice(entityplayermp, world);
        }
    }
    
    @SubscribeEvent
    public void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        final EntityPlayer entityplayer = event.player;
        if (!((Entity)entityplayer).worldObj.isClient) {
            LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, ((Entity)entityplayer).worldObj);
            LOTRLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, ((Entity)entityplayer).worldObj);
            LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, ((Entity)entityplayer).worldObj);
            LOTRLevelData.sendAllShieldsInWorldToPlayer(entityplayer, ((Entity)entityplayer).worldObj);
        }
    }
    
    @SubscribeEvent
    public void onPlayerRespawn(final PlayerEvent.PlayerRespawnEvent event) {
        final EntityPlayer entityplayer = event.player;
        final World world = ((Entity)entityplayer).worldObj;
        if (!world.isClient && entityplayer instanceof EntityPlayerMP && world instanceof WorldServer) {
            final EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
            final WorldServer worldserver = (WorldServer)world;
            final ChunkCoordinates deathPoint = LOTRLevelData.getData((EntityPlayer)entityplayermp).getDeathPoint();
            final int deathDimension = LOTRLevelData.getData((EntityPlayer)entityplayermp).getDeathDimension();
            if (deathDimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                if (LOTRConfig.middleEarthRespawning) {
                    final ChunkCoordinates bedLocation = entityplayermp.getBedLocation(((Entity)entityplayermp).dimension);
                    boolean hasBed = bedLocation != null;
                    if (hasBed) {
                        hasBed = (EntityPlayerMP.verifyRespawnCoordinates((World)worldserver, bedLocation, entityplayermp.isSpawnForced(((Entity)entityplayermp).dimension)) != null);
                    }
                    final ChunkCoordinates spawnLocation = hasBed ? bedLocation : worldserver.getSpawnPoint();
                    final double respawnThreshold = hasBed ? LOTRConfig.MERBedRespawnThreshold : ((double)LOTRConfig.MERWorldRespawnThreshold);
                    if (deathPoint != null) {
                        final boolean flag = deathPoint.getDistanceSquaredToChunkCoordinates(spawnLocation) > respawnThreshold * respawnThreshold;
                        if (flag) {
                            final double randomDistance = MathHelper.getRandomIntegerInRange(((World)worldserver).rand, LOTRConfig.MERMinRespawn, LOTRConfig.MERMaxRespawn);
                            final float angle = ((World)worldserver).rand.nextFloat() * 3.1415927f * 2.0f;
                            final int i = deathPoint.posX + (int)(randomDistance * MathHelper.sin(angle));
                            final int k = deathPoint.posZ + (int)(randomDistance * MathHelper.cos(angle));
                            final int j = LOTRMod.getTrueTopBlock((World)worldserver, i, k);
                            entityplayermp.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, ((Entity)entityplayermp).rotationYaw, ((Entity)entityplayermp).rotationPitch);
                            entityplayermp.playerNetServerHandler.setPlayerLocation(i + 0.5, (double)j, k + 0.5, ((Entity)entityplayermp).rotationYaw, ((Entity)entityplayermp).rotationPitch);
                        }
                    }
                }
            }
            else if (deathDimension == LOTRDimension.UTUMNO.dimensionID) {
                LOTRTeleporterUtumno.newTeleporter(LOTRDimension.MIDDLE_EARTH.dimensionID).placeInPortal((Entity)entityplayermp, 0.0, 0.0, 0.0, 0.0f);
                entityplayermp.playerNetServerHandler.setPlayerLocation(((Entity)entityplayermp).posX, ((Entity)entityplayermp).posY, ((Entity)entityplayermp).posZ, ((Entity)entityplayermp).rotationYaw, ((Entity)entityplayermp).rotationPitch);
            }
        }
    }
    
    @SubscribeEvent
    public void onBlockInteract(final PlayerInteractEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = ((Entity)entityplayer).worldObj;
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        final int side = event.face;
        if (!world.canMineBlock(entityplayer, i, j, k)) {
            return;
        }
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            return;
        }
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            final Block block = world.getBlock(i, j, k);
            final int meta = world.getBlockMetadata(i, j, k);
            if (!world.isClient && LOTRBannerProtection.isProtectedByBanner(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer), true)) {
                event.setCanceled(true);
                if (block instanceof BlockDoor) {
                    world.markBlockForUpdate(i, j - 1, k);
                    world.markBlockForUpdate(i, j, k);
                    world.markBlockForUpdate(i, j + 1, k);
                }
                return;
            }
            if (block == Blocks.flower_pot && meta == 0 && itemstack != null && LOTRBlockFlowerPot.canAcceptPlant(itemstack)) {
                LOTRMod.proxy.placeFlowerInPot(world, i, j, k, side, itemstack);
                if (!entityplayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = itemstack;
                    --itemStack.stackSize;
                }
                event.setCanceled(true);
                return;
            }
            if (itemstack != null && block == Blocks.cauldron && meta > 0) {
                LOTRItemMug.Vessel drinkVessel = null;
                for (final LOTRItemMug.Vessel v : LOTRItemMug.Vessel.values()) {
                    if (v.getEmptyVesselItem() == itemstack.getItem()) {
                        drinkVessel = v;
                        break;
                    }
                }
                if (drinkVessel != null) {
                    LOTRMod.proxy.fillMugFromCauldron(world, i, j, k, side, itemstack);
                    final ItemStack itemStack2 = itemstack;
                    --itemStack2.stackSize;
                    final ItemStack mugFill = new ItemStack(LOTRMod.mugWater);
                    LOTRItemMug.setVessel(mugFill, drinkVessel, true);
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, mugFill);
                    }
                    else if (!entityplayer.inventory.addItemStackToInventory(mugFill)) {
                        entityplayer.dropPlayerItemWithRandomChoice(mugFill, false);
                    }
                    event.setCanceled(true);
                    return;
                }
            }
            if (!world.isClient && block instanceof LOTRBlockPlate && entityplayer.isSneaking()) {
                final TileEntity tileentity = world.getTileEntity(i, j, k);
                if (tileentity instanceof LOTRTileEntityPlate) {
                    final LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
                    final ItemStack plateItem = plate.getFoodItem();
                    if (plateItem != null) {
                        ((LOTRBlockPlate)block).dropOnePlateItem(plate);
                        final ItemStack itemStack3 = plateItem;
                        --itemStack3.stackSize;
                        plate.setFoodItem(plateItem);
                        event.setCanceled(true);
                        return;
                    }
                }
            }
            if (!world.isClient && block instanceof BlockCauldron && itemstack != null) {
                final int cauldronMeta = BlockCauldron.func_150027_b(meta);
                if (cauldronMeta > 0) {
                    boolean undyed = false;
                    final Item item = itemstack.getItem();
                    if (LOTRRecipePoisonWeapon.poisonedToInput.containsKey(item)) {
                        final Item inputWeapon = LOTRRecipePoisonWeapon.poisonedToInput.get(item);
                        itemstack.func_150996_a(inputWeapon);
                        undyed = true;
                    }
                    else if (item instanceof LOTRItemPouch && LOTRItemPouch.isPouchDyed(itemstack)) {
                        LOTRItemPouch.removePouchDye(itemstack);
                        undyed = true;
                    }
                    else if (item instanceof LOTRItemHobbitPipe && LOTRItemHobbitPipe.isPipeDyed(itemstack)) {
                        LOTRItemHobbitPipe.removePipeDye(itemstack);
                        undyed = true;
                    }
                    else if (item instanceof LOTRItemLeatherHat && (LOTRItemLeatherHat.isHatDyed(itemstack) || LOTRItemLeatherHat.isFeatherDyed(itemstack))) {
                        LOTRItemLeatherHat.removeHatAndFeatherDye(itemstack);
                        undyed = true;
                    }
                    else if (item instanceof LOTRItemFeatherDyed && LOTRItemFeatherDyed.isFeatherDyed(itemstack)) {
                        LOTRItemFeatherDyed.removeFeatherDye(itemstack);
                        undyed = true;
                    }
                    else if (item instanceof LOTRItemHaradRobes && LOTRItemHaradRobes.areRobesDyed(itemstack)) {
                        LOTRItemHaradRobes.removeRobeDye(itemstack);
                        undyed = true;
                    }
                    else if (item instanceof LOTRItemPartyHat && LOTRItemPartyHat.isHatDyed(itemstack)) {
                        LOTRItemPartyHat.removeHatDye(itemstack);
                        undyed = true;
                    }
                    if (undyed) {
                        ((BlockCauldron)block).func_150024_a(world, i, j, k, cauldronMeta - 1);
                        event.setCanceled(true);
                        return;
                    }
                }
            }
            if (!world.isClient && LOTRBlockGrapevine.isFullGrownGrapes(block, meta)) {
                LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j, k);
            }
            if (block == Blocks.enchanting_table && !LOTRConfig.isEnchantingEnabled(world) && !world.isClient) {
                LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.ENCHANTING);
                event.setCanceled(true);
                return;
            }
            if (block == Blocks.brewing_stand && !LOTRConfig.enablePotionBrewing) {
                event.setCanceled(true);
                return;
            }
            if (block == Blocks.ender_chest && LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO && LOTRConfig.disableEnderChestsUtumno) {
                event.setCanceled(true);
                return;
            }
            if (block == Blocks.anvil && (LOTRConfig.isLOTREnchantingEnabled(world) || !LOTRConfig.isEnchantingEnabled(world)) && !world.isClient) {
                entityplayer.openGui((Object)LOTRMod.instance, 53, world, i, j, k);
                event.setCanceled(true);
                return;
            }
            if (block == Blocks.bookshelf && !entityplayer.isSneaking() && LOTRBlockBookshelfStorage.canOpenBookshelf(world, i, j, k, entityplayer) && !world.isClient) {
                world.setBlock(i, j, k, LOTRMod.bookshelfStorage, 0, 3);
                final boolean flag = LOTRMod.bookshelfStorage.onBlockActivated(world, i, j, k, entityplayer, side, 0.5f, 0.5f, 0.5f);
                if (!flag) {
                    world.setBlock(i, j, k, Blocks.bookshelf, 0, 3);
                }
                event.setCanceled(true);
                return;
            }
        }
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            final Block block = world.getBlock(i, j, k);
            final int meta = world.getBlockMetadata(i, j, k);
            final ForgeDirection dir = ForgeDirection.getOrientation(side);
            final int i2 = i + dir.offsetX;
            final int j2 = j + dir.offsetY;
            final int k2 = k + dir.offsetZ;
            final Block block2 = world.getBlock(i2, j2, k2);
            if (!world.isClient && LOTRBannerProtection.isProtectedByBanner(world, i2, j2, k2, LOTRBannerProtection.forPlayer(entityplayer), true) && block2 instanceof BlockFire) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onAnvilUpdate(final AnvilUpdateEvent event) {
        if (!LOTRConfig.enchantingVanilla && ((event.left != null && event.left.getItem() instanceof ItemEnchantedBook) || (event.right != null && event.right.getItem() instanceof ItemEnchantedBook))) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onHarvestCheck(final net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final Block block = event.block;
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
            event.success = true;
        }
    }
    
    @SubscribeEvent
    public void getBlockDrops(final BlockEvent.HarvestDropsEvent event) {
        final EntityPlayer entityplayer = event.harvester;
        final Block block = event.block;
        if (entityplayer != null) {
            final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if (itemstack != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
                int meta = 0;
                final Item item = Item.getItemFromBlock(block);
                if (item != null && item.getHasSubtypes()) {
                    meta = event.blockMetadata;
                }
                final ItemStack silkDrop = new ItemStack(item, 1, meta);
                event.drops.clear();
                event.drops.add(silkDrop);
            }
        }
    }
    
    @SubscribeEvent
    public void onBreakingSpeed(final net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final Block block = event.block;
        final int meta = event.metadata;
        float speed = event.newSpeed;
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != null) {
            final float baseDigSpeed = itemstack.getItem().getDigSpeed(itemstack, block, meta);
            if (baseDigSpeed > 1.0f) {
                speed *= LOTREnchantmentHelper.calcToolEfficiency(itemstack);
            }
        }
        event.newSpeed = speed;
    }
    
    @SubscribeEvent
    public void onBlockBreaking(final net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = ((Entity)entityplayer).worldObj;
        final Block block = event.block;
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        if (block instanceof LOTRWorldProviderUtumno.UtumnoBlock && LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO) {
            boolean canMine = false;
            final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if (itemstack != null && itemstack.getItem() == LOTRMod.utumnoPickaxe) {
                canMine = true;
                final int levelFuzz = 2;
                for (int l = 0; l < LOTRUtumnoLevel.values().length - 1; ++l) {
                    final LOTRUtumnoLevel levelUpper = LOTRUtumnoLevel.values()[l];
                    final LOTRUtumnoLevel levelLower = LOTRUtumnoLevel.values()[l + 1];
                    if (j >= levelLower.getHighestCorridorRoof() + levelFuzz && j <= levelUpper.getLowestCorridorFloor() - levelFuzz) {
                        canMine = false;
                    }
                }
            }
            if (!canMine) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onBlockBreak(final BlockEvent.BreakEvent event) {
        final EntityPlayer entityplayer = event.getPlayer();
        final Block block = event.block;
        final int meta = event.blockMetadata;
        final World world = event.world;
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        if (!world.isClient && LOTRBannerProtection.isProtectedByBanner(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer), true)) {
            event.setCanceled(true);
            return;
        }
        if (!world.isClient && entityplayer != null && !entityplayer.capabilities.isCreativeMode && block.isWood((IBlockAccess)world, i, j, k) && !LOTRBlockRottenLog.isRottenWood(block)) {
            final List trees = world.getEntitiesWithinAABB((Class)LOTREntityTree.class, AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).expand(16.0, 16.0, 16.0));
            if (!trees.isEmpty()) {
                boolean sentMessage = false;
                boolean penalty = false;
                for (int l = 0; l < trees.size(); ++l) {
                    final LOTREntityTree tree = trees.get(l);
                    if (!tree.hiredNPCInfo.isActive || tree.hiredNPCInfo.getHiringPlayer() != entityplayer) {
                        tree.setAttackTarget((EntityLivingBase)entityplayer);
                        if (tree instanceof LOTREntityEnt && !sentMessage) {
                            tree.sendSpeechBank(entityplayer, "ent/ent/defendTrees");
                            sentMessage = true;
                        }
                        if (world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenFangorn && !penalty) {
                            LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.FANGORN_TREE_PENALTY, LOTRFaction.FANGORN, i + 0.5, j + 0.5, k + 0.5);
                            penalty = true;
                        }
                    }
                }
            }
        }
        if (!world.isClient && entityplayer != null) {
            if (LOTRBlockGrapevine.isFullGrownGrapes(block, meta)) {
                LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j, k);
            }
            else {
                boolean grapesAbove = false;
                for (int j2 = 1; j2 <= 3; ++j2) {
                    final int j3 = j + j2;
                    final Block above = world.getBlock(i, j3, k);
                    final int aboveMeta = world.getBlockMetadata(i, j3, k);
                    if (LOTRBlockGrapevine.isFullGrownGrapes(above, aboveMeta)) {
                        grapesAbove = true;
                    }
                }
                if (grapesAbove) {
                    LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j + 1, k);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onBlockPlace(final BlockEvent.PlaceEvent event) {
        final EntityPlayer entityplayer = event.player;
        final Block block = event.block;
        final World world = event.world;
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        if (world.provider instanceof LOTRWorldProviderUtumno && LOTRUtumnoLevel.forY(j) == LOTRUtumnoLevel.FIRE && block == Blocks.ice) {
            world.setBlock(i, j, k, Blocks.air, 0, 3);
            LOTRWorldProviderUtumno.doEvaporateFX(world, i, j, k);
        }
    }
    
    @SubscribeEvent
    public void onArrowNock(final ArrowNockEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = ((Entity)entityplayer).worldObj;
        final ItemStack itemstack = event.result;
        if (itemstack != null && itemstack.getItem() instanceof ItemBow && !(itemstack.getItem() instanceof LOTRItemBow) && !(itemstack.getItem() instanceof LOTRItemCrossbow)) {
            if (!world.isClient) {
                if (this.proxyBowItemServer == null) {
                    this.proxyBowItemServer = new LOTRItemBow(Item.ToolMaterial.WOOD);
                    event.result = this.proxyBowItemServer.onItemRightClick(itemstack, world, entityplayer);
                    this.proxyBowItemServer = null;
                    event.setCanceled(true);
                }
            }
            else if (this.proxyBowItemClient == null) {
                this.proxyBowItemClient = new LOTRItemBow(Item.ToolMaterial.WOOD);
                event.result = this.proxyBowItemClient.onItemRightClick(itemstack, world, entityplayer);
                this.proxyBowItemClient = null;
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onItemUseStop(final PlayerUseItemEvent.Stop event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = ((Entity)entityplayer).worldObj;
        final ItemStack itemstack = event.item;
        final int usingTick = event.duration;
        if (itemstack == null || !(itemstack.getItem() instanceof ItemBow) || itemstack.getItem() instanceof LOTRItemBow || itemstack.getItem() instanceof LOTRItemCrossbow) {
            return;
        }
        if (!world.isClient) {
            if (this.proxyBowItemServer == null) {
                this.proxyBowItemServer = new LOTRItemBow(Item.ToolMaterial.WOOD);
            }
            this.proxyBowItemServer.onPlayerStoppedUsing(itemstack, world, entityplayer, usingTick);
            this.proxyBowItemServer = null;
            event.setCanceled(true);
            return;
        }
        if (this.proxyBowItemClient == null) {
            this.proxyBowItemClient = new LOTRItemBow(Item.ToolMaterial.WOOD);
        }
        this.proxyBowItemClient.onPlayerStoppedUsing(itemstack, world, entityplayer, usingTick);
        this.proxyBowItemClient = null;
        event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onItemUseFinish(final PlayerUseItemEvent.Finish event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = ((Entity)entityplayer).worldObj;
        final ItemStack itemstack = event.item;
        final ItemStack result = event.result;
        if (!world.isClient && LOTRPoisonedDrinks.isDrinkPoisoned(itemstack)) {
            LOTRPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
        }
    }
    
    @SubscribeEvent
    public void onUseBonemeal(final BonemealEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = event.world;
        final Random rand = world.rand;
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        if (!world.isClient) {
            if (event.block instanceof BlockLog) {
                int meta = world.getBlockMetadata(i, j, k);
                if ((meta & 0xC) != 0xC) {
                    meta |= 0xC;
                    world.setBlockMetadata(i, j, k, meta, 3);
                    event.setResult(Event.Result.ALLOW);
                    return;
                }
            }
            if (event.block instanceof LOTRBlockSaplingBase) {
                final LOTRBlockSaplingBase sapling = (LOTRBlockSaplingBase)event.block;
                final int meta2 = world.getBlockMetadata(i, j, k);
                if (rand.nextFloat() < 0.45) {
                    sapling.incrementGrowth(world, i, j, k, rand);
                }
                if (sapling == LOTRMod.sapling4 && (meta2 & 0x7) == 0x1 && world.getBlock(i, j, k) == LOTRMod.wood4 && world.getBlockMetadata(i, j, k) == 1) {
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.growBaobab);
                }
                event.setResult(Event.Result.ALLOW);
                return;
            }
            if (event.block.canSustainPlant((IBlockAccess)world, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.tallgrass) && event.block instanceof IGrowable) {
                final BiomeGenBase biomegenbase = world.getBiomeGenForCoords(i, k);
                if (biomegenbase instanceof LOTRBiome) {
                    final LOTRBiome biome = (LOTRBiome)biomegenbase;
                    int attempts = 0;
                Label_0286:
                    while (attempts < 128) {
                        int i2 = i;
                        int j2 = j + 1;
                        int k2 = k;
                        while (true) {
                            for (int subAttempts = 0; subAttempts < attempts / 16; ++subAttempts) {
                                i2 += rand.nextInt(3) - 1;
                                j2 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                                k2 += rand.nextInt(3) - 1;
                                final Block below = world.getBlock(i2, j2 - 1, k2);
                                if (!(below instanceof IGrowable) || !below.canSustainPlant((IBlockAccess)world, i2, j2 - 1, k2, ForgeDirection.UP, (IPlantable)Blocks.tallgrass) || world.getBlock(i2, j2, k2).isNormalCube()) {
                                    ++attempts;
                                    continue Label_0286;
                                }
                            }
                            if (world.getBlock(i2, j2, k2).getMaterial() != Material.air) {
                                continue;
                            }
                            if (rand.nextInt(8) > 0) {
                                final LOTRBiome.GrassBlockAndMeta obj = biome.getRandomGrass(rand);
                                final Block block = obj.block;
                                final int meta3 = obj.meta;
                                if (block.canBlockStay(world, i2, j2, k2)) {
                                    world.setBlock(i2, j2, k2, block, meta3, 3);
                                }
                                continue;
                            }
                            biome.plantFlower(world, rand, i2, j2, k2);
                            continue;
                        }
                    }
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onSaplingGrow(final SaplingGrowTreeEvent event) {
        final World world = event.world;
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        final Block block = world.getBlock(i, j, k);
        if (block == Blocks.sapling) {
            LOTRVanillaSaplings.growTree(world, i, j, k, event.rand);
            event.setResult(Event.Result.DENY);
        }
    }
    
    @SubscribeEvent
    public void onUseHoe(final UseHoeEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = event.world;
        final int i = event.x;
        final int j = event.y;
        final int k = event.z;
        final Block block = world.getBlock(i, j, k);
        LOTRBlockGrapevine.hoeing = true;
        if (world.getBlock(i, j + 1, k).isAir((IBlockAccess)world, i, j + 1, k) && (block == LOTRMod.mudGrass || block == LOTRMod.mud)) {
            final Block tilled = LOTRMod.mudFarmland;
            world.playSoundEffect((double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), tilled.stepSound.getStepResourcePath(), (tilled.stepSound.getVolume() + 1.0f) / 2.0f, tilled.stepSound.getFrequency() * 0.8f);
            if (!world.isClient) {
                world.setBlock(i, j, k, tilled);
            }
            event.setResult(Event.Result.ALLOW);
            return;
        }
        LOTRBlockGrapevine.hoeing = true;
    }
    
    @SubscribeEvent
    public void onFillBucket(final FillBucketEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final ItemStack itemstack = event.current;
        final World world = event.world;
        final MovingObjectPosition target = event.target;
        if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int i = target.blockX;
            final int j = target.blockY;
            final int k = target.blockZ;
            if (!world.isClient && LOTRBannerProtection.isProtectedByBanner(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer), true)) {
                event.setCanceled(true);
                return;
            }
            if (world.provider instanceof LOTRWorldProviderUtumno && LOTRUtumnoLevel.forY(j) == LOTRUtumnoLevel.FIRE && itemstack != null && itemstack.getItem() == Items.water_bucket) {
                LOTRWorldProviderUtumno.doEvaporateFX(world, i, j, k);
                event.result = new ItemStack(Items.bucket);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
    
    @SubscribeEvent
    public void onItemPickup(final EntityItemPickupEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final ItemStack itemstack = event.item.getEntityItem();
        if (!((Entity)entityplayer).worldObj.isClient) {
            if (itemstack.stackSize > 0) {
                for (int i = 0; i < entityplayer.inventory.getSizeInventory(); ++i) {
                    final ItemStack itemInSlot = entityplayer.inventory.getStackInSlot(i);
                    if (itemInSlot != null && itemInSlot.getItem() == LOTRMod.pouch) {
                        LOTRItemPouch.tryAddItemToPouch(itemInSlot, itemstack, true);
                        if (itemstack.stackSize <= 0) {
                            break;
                        }
                    }
                }
                if (itemstack.stackSize <= 0) {
                    event.setResult(Event.Result.ALLOW);
                }
            }
            if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.athelas)) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.findAthelas);
            }
            if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.clover) && itemstack.getItemDamage() == 1) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.findFourLeafClover);
            }
            if (itemstack.getItem() == LOTRMod.kineArawHorn) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getKineArawHorn);
            }
            if (LOTRConfig.enchantingAutoRemoveVanilla) {
                dechant(itemstack, entityplayer);
            }
        }
    }
    
    private static boolean dechant(final ItemStack itemstack, final EntityPlayer entityplayer) {
        if (!entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.isItemEnchanted()) {
            final Item item = itemstack.getItem();
            if (!(item instanceof ItemFishingRod)) {
                itemstack.getTagCompound().removeTag("ench");
                return true;
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void onWorldSave(final WorldEvent.Save event) {
        final World world = event.world;
        if (!world.isClient && world.provider.dimensionId == 0) {
            LOTRTime.save();
        }
    }
    
    @SubscribeEvent
    public void onWorldUnload(final WorldEvent.Unload event) {
        final World world = event.world;
        if (world.provider instanceof LOTRWorldProvider) {
            LOTRBiomeVariantStorage.clearAllVariants(world);
        }
    }
    
    @SubscribeEvent
    public void onChunkDataLoad(final ChunkDataEvent.Load event) {
        final World world = event.world;
        final Chunk chunk = event.getChunk();
        final NBTTagCompound data = event.getData();
        if (!world.isClient && world.provider instanceof LOTRWorldProvider) {
            LOTRBiomeVariantStorage.loadChunkVariants(world, chunk, data);
        }
    }
    
    @SubscribeEvent
    public void onChunkDataSave(final ChunkDataEvent.Save event) {
        final World world = event.world;
        final Chunk chunk = event.getChunk();
        final NBTTagCompound data = event.getData();
        if (!world.isClient && world.provider instanceof LOTRWorldProvider) {
            LOTRBiomeVariantStorage.saveChunkVariants(world, chunk, data);
        }
    }
    
    @SubscribeEvent
    public void onChunkStartWatching(final ChunkWatchEvent.Watch event) {
        final EntityPlayerMP entityplayer = event.player;
        final World world = ((Entity)entityplayer).worldObj;
        final ChunkCoordIntPair chunkCoords = event.chunk;
        final Chunk chunk = world.getChunkFromChunkCoords(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
        if (!world.isClient && world.provider instanceof LOTRWorldProvider) {
            LOTRBiomeVariantStorage.sendChunkVariantsToPlayer(world, chunk, entityplayer);
        }
    }
    
    @SubscribeEvent
    public void onChunkStopWatching(final ChunkWatchEvent.UnWatch event) {
        final EntityPlayerMP entityplayer = event.player;
        final World world = ((Entity)entityplayer).worldObj;
        final ChunkCoordIntPair chunkCoords = event.chunk;
        final Chunk chunk = world.getChunkFromChunkCoords(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
        if (!world.isClient && world.provider instanceof LOTRWorldProvider) {
            LOTRBiomeVariantStorage.sendUnwatchToPlayer(world, chunk, entityplayer);
        }
    }
    
    @SubscribeEvent
    public void onEntitySpawnAttempt(final LivingSpawnEvent.CheckSpawn event) {
        final EntityLivingBase entity = event.entityLiving;
        final World world = ((Entity)entity).worldObj;
        if (!world.isClient && entity instanceof EntityMob && LOTRBannerProtection.isProtectedByBanner(world, (Entity)entity, LOTRBannerProtection.anyBanner(), false)) {
            event.setResult(Event.Result.DENY);
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        final Entity entity = event.entity;
        final World world = entity.worldObj;
        if (!world.isClient && entity instanceof EntityXPOrb && !LOTRConfig.enchantingVanilla && world.provider instanceof LOTRWorldProvider) {
            event.setCanceled(true);
            return;
        }
        if (!world.isClient && entity instanceof EntityCreature) {
            final EntityCreature entitycreature = (EntityCreature)entity;
            final String s = EntityList.getEntityString((Entity)entitycreature);
            final Object obj = LOTREntityRegistry.registeredNPCs.get(s);
            if (obj != null) {
                final LOTREntityRegistry.RegistryInfo info = (LOTREntityRegistry.RegistryInfo)obj;
                if (info.shouldTargetEnemies) {
                    LOTREntityNPC.addTargetTasks(entitycreature, 100, LOTREntityAINearestAttackableTargetBasic.class);
                }
            }
        }
        if (!world.isClient && entity.getClass() == EntityFishHook.class && world.provider instanceof LOTRWorldProvider) {
            final EntityFishHook oldFish = (EntityFishHook)entity;
            final NBTTagCompound fishData = new NBTTagCompound();
            oldFish.writeToNBT(fishData);
            oldFish.setDead();
            final LOTREntityFishHook newFish = new LOTREntityFishHook(world);
            newFish.readFromNBT(fishData);
            newFish.field_146042_b = oldFish.field_146042_b;
            if (newFish.field_146042_b != null) {
                ((LOTREntityFishHook)(newFish.field_146042_b.fishEntity = newFish)).setPlayerID(newFish.field_146042_b.getEntityId());
            }
            world.spawnEntityInWorld((Entity)newFish);
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onStartTrackingEntity(final net.minecraftforge.event.entity.player.PlayerEvent.StartTracking event) {
        final Entity entity = event.target;
        final EntityPlayer entityplayer = event.entityPlayer;
        if (!entity.worldObj.isClient && entity instanceof LOTREntityNPC) {
            final EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
            final LOTREntityNPC npc = (LOTREntityNPC)entity;
            npc.onPlayerStartTracking(entityplayermp);
        }
        if (!entity.worldObj.isClient && entity instanceof LOTRRandomSkinEntity) {
            final LOTRPacketEntityUUID packet = new LOTRPacketEntityUUID(entity.getEntityId(), entity.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        if (!entity.worldObj.isClient && entity instanceof LOTREntityBanner) {
            ((LOTREntityBanner)entity).sendBannerToPlayer(entityplayer, false, false);
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final World world = ((Entity)entity).worldObj;
        if (!world.isClient) {
            LOTREnchantmentHelper.onEntityUpdate(entity);
        }
        if (LOTRConfig.enchantingAutoRemoveVanilla && !world.isClient && entity instanceof EntityPlayer && ((Entity)entity).ticksExisted % 60 == 0) {
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            for (int l = 0; l < entityplayer.inventory.getSizeInventory(); ++l) {
                final ItemStack itemstack = entityplayer.inventory.getStackInSlot(l);
                if (itemstack != null) {
                    dechant(itemstack, entityplayer);
                }
            }
        }
        if (!world.isClient && LOTRMod.canSpawnMobs(world) && entity.isEntityAlive() && entity.isInWater() && ((Entity)entity).ridingEntity == null) {
            boolean flag = true;
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                flag = false;
            }
            if (entity instanceof EntityWaterMob || entity instanceof LOTREntityMarshWraith) {
                flag = false;
            }
            if (flag) {
                int i;
                int k;
                int j;
                for (i = MathHelper.floor_double(((Entity)entity).posX), k = MathHelper.floor_double(((Entity)entity).posZ), j = world.getTopSolidOrLiquidBlock(i, k); world.getBlock(i, j + 1, k).getMaterial().isLiquid() || world.getBlock(i, j + 1, k).getMaterial().isSolid(); ++j) {}
                if (j - ((Entity)entity).boundingBox.minY < 2.0 && world.getBlock(i, j, k).getMaterial() == Material.water && world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenDeadMarshes) {
                    final double wraithRange = 12.0;
                    final double wraithRangeSq = 144.0;
                    final double wraithCheckRange = 15.0;
                    final List nearbyWraiths = world.getEntitiesWithinAABB((Class)LOTREntityMarshWraith.class, ((Entity)entity).boundingBox.expand(15.0, 15.0, 15.0));
                    boolean anyNearbyWraiths = false;
                    for (int m = 0; m < nearbyWraiths.size(); ++m) {
                        final LOTREntityMarshWraith wraith = nearbyWraiths.get(m);
                        if (wraith.getAttackTarget() == entity && wraith.getDeathFadeTime() == 0) {
                            anyNearbyWraiths = true;
                            break;
                        }
                    }
                    if (!anyNearbyWraiths) {
                        final LOTREntityMarshWraith wraith2 = new LOTREntityMarshWraith(world);
                        final int i2 = i + MathHelper.getRandomIntegerInRange(world.rand, -3, 3);
                        final int k2 = k + MathHelper.getRandomIntegerInRange(world.rand, -3, 3);
                        final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                        wraith2.setLocationAndAngles(i2 + 0.5, (double)j2, k2 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
                        if (wraith2.getDistanceSqToEntity((Entity)entity) <= 144.0) {
                            world.spawnEntityInWorld((Entity)wraith2);
                            wraith2.setAttackTarget(entity);
                            wraith2.attackTargetUUID = entity.getUniqueID();
                            world.playSoundAtEntity((Entity)wraith2, "lotr:wraith.spawn", 1.0f, 0.7f + world.rand.nextFloat() * 0.6f);
                        }
                    }
                }
            }
        }
        if (!world.isClient && LOTRMod.canSpawnMobs(world) && entity.isEntityAlive() && world.isDaytime()) {
            float f = 0.0f;
            int shirriffs = 0;
            if (LOTRFaction.HOBBIT.isBadRelation(LOTRMod.getNPCFaction((Entity)entity))) {
                final float health = entity.getMaxHealth() + entity.getTotalArmorValue();
                f = health * 2.5f;
                final int i3 = (int)(health / 15.0f);
                shirriffs = 2 + world.rand.nextInt(i3 + 1);
            }
            else if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer2 = (EntityPlayer)entity;
                final float alignment = LOTRLevelData.getData(entityplayer2).getAlignment(LOTRFaction.HOBBIT);
                if (!entityplayer2.capabilities.isCreativeMode && alignment < 0.0f) {
                    f = -alignment;
                    final int i4 = (int)(f / 50.0f);
                    shirriffs = 2 + world.rand.nextInt(i4 + 1);
                }
            }
            if (f > 0.0f) {
                f = Math.min(f, 2000.0f);
                final int chance = (int)(2000000.0f / f);
                shirriffs = Math.min(shirriffs, 5);
                final int i3 = MathHelper.floor_double(((Entity)entity).posX);
                final int k3 = MathHelper.floor_double(((Entity)entity).posZ);
                final int j3 = world.getTopSolidOrLiquidBlock(i3, k3);
                if (world.rand.nextInt(chance) == 0 && world.getBiomeGenForCoords(i3, k3) instanceof LOTRBiomeGenShire) {
                    final List nearbyShirriffs = world.getEntitiesWithinAABB((Class)LOTREntityHobbitShirriff.class, ((Entity)entity).boundingBox.expand(12.0, 6.0, 12.0));
                    if (nearbyShirriffs.isEmpty()) {
                        boolean sentMessage = false;
                        boolean playedHorn = false;
                        for (int l2 = 0; l2 < shirriffs; ++l2) {
                            final LOTREntityHobbitShirriff shirriff = new LOTREntityHobbitShirriff(world);
                            for (int l3 = 0; l3 < 32; ++l3) {
                                final int i5 = i3 - world.rand.nextInt(12) + world.rand.nextInt(12);
                                final int k4 = k3 - world.rand.nextInt(12) + world.rand.nextInt(12);
                                final int j4 = world.getTopSolidOrLiquidBlock(i5, k4);
                                if (world.getBlock(i5, j4 - 1, k4).isSideSolid((IBlockAccess)world, i5, j4 - 1, k4, ForgeDirection.UP) && !world.getBlock(i5, j4, k4).isNormalCube() && !world.getBlock(i5, j4 + 1, k4).isNormalCube()) {
                                    shirriff.setLocationAndAngles(i5 + 0.5, (double)j4, k4 + 0.5, 0.0f, 0.0f);
                                    if (shirriff.getCanSpawnHere() && entity.getDistanceToEntity((Entity)shirriff) > 6.0) {
                                        shirriff.onSpawnWithEgg(null);
                                        world.spawnEntityInWorld((Entity)shirriff);
                                        shirriff.setAttackTarget(entity);
                                        if (!sentMessage && entity instanceof EntityPlayer) {
                                            final EntityPlayer entityplayer3 = (EntityPlayer)entity;
                                            shirriff.sendSpeechBank(entityplayer3, shirriff.getSpeechBank(entityplayer3));
                                            sentMessage = true;
                                        }
                                        if (!playedHorn) {
                                            world.playSoundAtEntity((Entity)shirriff, "lotr:item.horn", 2.0f, 2.0f);
                                            playedHorn = true;
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!world.isClient && entity.isEntityAlive() && entity.isInWater() && ((Entity)entity).ridingEntity == null && ((Entity)entity).ticksExisted % 10 == 0) {
            boolean flag = true;
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                flag = false;
            }
            if (entity instanceof LOTREntityMirkwoodSpider) {
                flag = false;
            }
            if (flag) {
                final int i = MathHelper.floor_double(((Entity)entity).posX);
                final int k = MathHelper.floor_double(((Entity)entity).posZ);
                final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                if (biome instanceof LOTRBiomeGenMirkwoodCorrupted) {
                    entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
                    entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 600, 1));
                    entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
                    entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 600));
                }
            }
        }
        if (!world.isClient && entity.isEntityAlive() && entity.isInWater() && ((Entity)entity).ridingEntity == null && ((Entity)entity).ticksExisted % 10 == 0) {
            boolean flag = true;
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer4 = (EntityPlayer)entity;
                if (entityplayer4.capabilities.isCreativeMode) {
                    flag = false;
                }
                else {
                    final float alignment2 = LOTRLevelData.getData(entityplayer4).getAlignment(LOTRFaction.MORDOR);
                    final float level = 100.0f;
                    if (alignment2 > level) {
                        flag = false;
                    }
                    else {
                        int chance2 = Math.round(level);
                        chance2 = Math.max(chance2, 1);
                        if (world.rand.nextInt(chance2) < alignment2) {
                            flag = false;
                        }
                    }
                }
            }
            if (LOTRMod.getNPCFaction((Entity)entity).isGoodRelation(LOTRFaction.MORDOR)) {
                flag = false;
            }
            if (flag) {
                final int i = MathHelper.floor_double(((Entity)entity).posX);
                final int k = MathHelper.floor_double(((Entity)entity).posZ);
                final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                if (biome instanceof LOTRBiomeGenMorgulVale) {
                    entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
                    entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 600, 1));
                    entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
                    entity.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
                }
            }
        }
        if (!world.isClient && entity.isEntityAlive() && ((Entity)entity).ticksExisted % 10 == 0) {
            boolean wearingAllWoodElvenScout = true;
            for (int i = 0; i < 4; ++i) {
                final ItemStack armour = entity.getEquipmentInSlot(i + 1);
                if (armour == null || !(armour.getItem() instanceof ItemArmor) || ((ItemArmor)armour.getItem()).getArmorMaterial() != LOTRMaterial.WOOD_ELVEN_SCOUT.toArmorMaterial()) {
                    wearingAllWoodElvenScout = false;
                    break;
                }
            }
            final IAttributeInstance speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            if (speedAttribute.getModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost.getID()) != null) {
                speedAttribute.removeModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost);
            }
            if (wearingAllWoodElvenScout) {
                speedAttribute.applyModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost);
            }
        }
        if (!world.isClient && entity.isEntityAlive()) {
            final ItemStack weapon = entity.getHeldItem();
            boolean lanceOnFoot = false;
            if (weapon != null && weapon.getItem() instanceof LOTRItemLance && ((Entity)entity).ridingEntity == null) {
                lanceOnFoot = true;
                if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                    lanceOnFoot = false;
                }
            }
            final IAttributeInstance speedAttribute2 = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            if (speedAttribute2.getModifier(LOTRItemLance.lanceSpeedBoost_id) != null) {
                speedAttribute2.removeModifier(LOTRItemLance.lanceSpeedBoost);
            }
            if (lanceOnFoot) {
                speedAttribute2.applyModifier(LOTRItemLance.lanceSpeedBoost);
            }
        }
        if (!world.isClient && entity.isEntityAlive() && ((Entity)entity).ticksExisted % 20 == 0) {
            boolean flag = true;
            if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).isImmuneToFrost) {
                flag = false;
            }
            if (entity instanceof EntityPlayer) {
                flag = !((EntityPlayer)entity).capabilities.isCreativeMode;
            }
            if (flag) {
                final int i = MathHelper.floor_double(((Entity)entity).posX);
                final int j5 = MathHelper.floor_double(((Entity)entity).boundingBox.minY);
                final int k5 = MathHelper.floor_double(((Entity)entity).posZ);
                final BiomeGenBase biome2 = world.getBiomeGenForCoords(i, k5);
                if (biome2 instanceof LOTRBiomeGenForodwaith && (world.canBlockSeeTheSky(i, j5, k5) || entity.isInWater()) && world.getSavedLightValue(EnumSkyBlock.Block, i, j5, k5) < 10) {
                    int frostChance = 50;
                    int frostProtection = 0;
                    for (int l4 = 0; l4 < 4; ++l4) {
                        final ItemStack armor = entity.getEquipmentInSlot(l4 + 1);
                        if (armor != null && armor.getItem() instanceof ItemArmor) {
                            final ItemArmor.ArmorMaterial material = ((ItemArmor)armor.getItem()).getArmorMaterial();
                            final Item materialItem = material.func_151685_b();
                            if (materialItem == Items.leather) {
                                frostProtection += 50;
                            }
                            else if (materialItem == LOTRMod.fur) {
                                frostProtection += 100;
                            }
                        }
                    }
                    frostChance += frostProtection;
                    if (world.isRaining()) {
                        frostChance /= 3;
                    }
                    if (entity.isInWater()) {
                        frostChance /= 20;
                    }
                    frostChance = Math.max(frostChance, 1);
                    if (world.rand.nextInt(frostChance) == 0) {
                        entity.attackEntityFrom(LOTRDamage.frost, 1.0f);
                    }
                }
            }
        }
        if (!world.isClient && entity.isEntityAlive() && ((Entity)entity).ticksExisted % 20 == 0) {
            boolean flag = true;
            if (entity instanceof LOTREntityNPC) {
                flag = true;
            }
            if (entity instanceof EntityPlayer) {
                flag = !((EntityPlayer)entity).capabilities.isCreativeMode;
            }
            if (entity instanceof LOTRBiomeGenNearHarad.ImmuneToHeat) {
                flag = false;
            }
            if (flag) {
                final int i = MathHelper.floor_double(((Entity)entity).posX);
                final int j5 = MathHelper.floor_double(((Entity)entity).boundingBox.minY);
                final int k5 = MathHelper.floor_double(((Entity)entity).posZ);
                final BiomeGenBase biome2 = world.getBiomeGenForCoords(i, k5);
                if (biome2 instanceof LOTRBiomeGenNearHarad && !entity.isInWater() && world.canBlockSeeTheSky(i, j5, k5) && world.isDaytime()) {
                    int burnChance = 50;
                    int burnProtection = 0;
                    for (int l4 = 0; l4 < 4; ++l4) {
                        final ItemStack armour2 = entity.getEquipmentInSlot(l4 + 1);
                        if (armour2 != null && armour2.getItem() instanceof ItemArmor) {
                            final ItemArmor.ArmorMaterial material = ((ItemArmor)armour2.getItem()).getArmorMaterial();
                            if (material.customCraftingMaterial == Items.leather) {
                                burnProtection += 50;
                            }
                            if (material == LOTRMaterial.HARAD_ROBES.toArmorMaterial()) {
                                burnProtection += 400;
                            }
                            if (material == LOTRMaterial.HARAD_NOMAD.toArmorMaterial()) {
                                burnProtection += 200;
                            }
                        }
                    }
                    burnChance += burnProtection;
                    burnChance = Math.max(burnChance, 1);
                    if (world.rand.nextInt(burnChance) == 0) {
                        final boolean attacked = entity.attackEntityFrom(DamageSource.onFire, 1.0f);
                        if (attacked && entity instanceof EntityPlayerMP) {
                            LOTRDamage.doBurnDamage((EntityPlayerMP)entity);
                        }
                    }
                }
            }
        }
        if (world.isClient) {
            LOTRPlateFallingInfo.getOrCreateFor((Entity)entity, true).update();
        }
    }
    
    @SubscribeEvent
    public void onMinecartUpdate(final MinecartUpdateEvent event) {
        final EntityMinecart minecart = event.minecart;
    }
    
    @SubscribeEvent
    public void onEntityInteract(final EntityInteractEvent event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final World world = ((Entity)entityplayer).worldObj;
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        final Entity entity = event.target;
        if (!world.isClient && (entity instanceof EntityHanging || entity instanceof LOTRBannerProtectable) && LOTRBannerProtection.isProtectedByBanner(world, entity, LOTRBannerProtection.forPlayer(entityplayer), true)) {
            event.setCanceled(true);
            return;
        }
        if ((entity instanceof EntityCow || entity instanceof LOTREntityZebra) && itemstack != null && LOTRItemMug.isItemEmptyDrink(itemstack)) {
            final LOTRItemMug.Vessel vessel = LOTRItemMug.getVessel(itemstack);
            final ItemStack milkItem = new ItemStack(LOTRMod.mugMilk);
            LOTRItemMug.setVessel(milkItem, vessel, true);
            if (!entityplayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = itemstack;
                --itemStack.stackSize;
            }
            if (itemstack.stackSize <= 0 || entityplayer.capabilities.isCreativeMode) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, milkItem);
            }
            else if (!entityplayer.inventory.addItemStackToInventory(milkItem)) {
                entityplayer.dropPlayerItemWithRandomChoice(milkItem, false);
            }
            event.setCanceled(true);
            return;
        }
        if (entity instanceof EntityWolf) {
            final EntityWolf wolf = (EntityWolf)entity;
            if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone") && itemstack.getItem() != Items.bone) {
                final Item prevItem = itemstack.getItem();
                itemstack.func_150996_a(Items.bone);
                final boolean flag = wolf.interact(entityplayer);
                itemstack.func_150996_a(prevItem);
                if (flag) {
                    event.setCanceled(true);
                    return;
                }
            }
            if (itemstack != null) {
                final int dyeType = LOTRItemDye.isItemDye(itemstack);
                if (dyeType >= 0 && itemstack.getItem() != Items.dye) {
                    final Item prevItem2 = itemstack.getItem();
                    final int prevMeta = itemstack.getItemDamage();
                    itemstack.func_150996_a(Items.dye);
                    itemstack.setItemDamage(dyeType);
                    final boolean flag2 = wolf.interact(entityplayer);
                    itemstack.func_150996_a(prevItem2);
                    itemstack.setItemDamage(prevMeta);
                    if (flag2) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
        if (entity instanceof LOTRTradeable && ((LOTRTradeable)entity).canTradeWith(entityplayer)) {
            if (entity instanceof LOTRUnitTradeable) {
                entityplayer.openGui((Object)LOTRMod.instance, 24, world, entity.getEntityId(), 0, 0);
            }
            else {
                entityplayer.openGui((Object)LOTRMod.instance, 19, world, entity.getEntityId(), 0, 0);
            }
            event.setCanceled(true);
            return;
        }
        if (entity instanceof LOTRUnitTradeable && ((LOTRUnitTradeable)entity).canTradeWith(entityplayer)) {
            entityplayer.openGui((Object)LOTRMod.instance, 20, world, entity.getEntityId(), 0, 0);
            event.setCanceled(true);
            return;
        }
        if (entity instanceof LOTRMercenary && ((LOTRMercenary)entity).canTradeWith(entityplayer) && ((LOTREntityNPC)entity).hiredNPCInfo.getHiringPlayerUUID() == null) {
            entityplayer.openGui((Object)LOTRMod.instance, 58, world, entity.getEntityId(), 0, 0);
            event.setCanceled(true);
            return;
        }
        if (entity instanceof LOTREntityNPC) {
            final LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                entityplayer.openGui((Object)LOTRMod.instance, 21, world, entity.getEntityId(), 0, 0);
                event.setCanceled(true);
                return;
            }
            if (npc.hiredNPCInfo.isActive && entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.getItem() == Items.clock) {
                if (!world.isClient && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile())) {
                    final UUID hiringUUID = npc.hiredNPCInfo.getHiringPlayerUUID();
                    if (hiringUUID != null) {
                        final String playerName = getUsernameWithoutWebservice(hiringUUID);
                        if (playerName != null) {
                            final IChatComponent msg = (IChatComponent)new ChatComponentText("Hired unit belongs to " + playerName);
                            msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
                            entityplayer.addChatMessage(msg);
                        }
                    }
                }
                event.setCanceled(true);
                return;
            }
        }
        if (!world.isClient && entityplayer.capabilities.isCreativeMode && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && itemstack != null && itemstack.getItem() == Items.clock && entity instanceof EntityLiving) {
            final UUID brandingPlayer = LOTRItemBrandingIron.getBrandingPlayer(entity);
            if (brandingPlayer != null) {
                final String playerName2 = getUsernameWithoutWebservice(brandingPlayer);
                if (playerName2 != null) {
                    final IChatComponent msg2 = (IChatComponent)new ChatComponentText("Entity was branded by " + playerName2);
                    msg2.getChatStyle().setColor(EnumChatFormatting.GREEN);
                    entityplayer.addChatMessage(msg2);
                    event.setCanceled(true);
                }
            }
        }
    }
    
    public static String getUsernameWithoutWebservice(final UUID player) {
        final GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
        if (profile != null && !StringUtils.isBlank((CharSequence)profile.getName())) {
            return profile.getName();
        }
        final String cachedName = UsernameCache.getLastKnownUsername(player);
        if (cachedName != null && !StringUtils.isBlank((CharSequence)cachedName)) {
            return cachedName;
        }
        return player.toString();
    }
    
    @SubscribeEvent
    public void onLivingSetAttackTarget(final LivingSetAttackTargetEvent event) {
        boolean sneaking = false;
        if (event.target instanceof LOTREntityRanger && ((LOTREntityRanger)event.target).isRangerSneaking()) {
            sneaking = true;
        }
        if (event.target instanceof LOTREntityGaladhrimWarden && ((LOTREntityGaladhrimWarden)event.target).isElfSneaking()) {
            sneaking = true;
        }
        if (event.target instanceof LOTREntityHuornBase && !((LOTREntityHuornBase)event.target).isHuornActive()) {
            sneaking = true;
        }
        if (event.target instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)event.target;
            final boolean cloak = LOTRLevelData.getData((EntityPlayer)event.target).isPlayerWearingFull(entityplayer) == LOTRMaterial.HITHLAIN;
            if (cloak && entityplayer.getLastAttacker() != event.entityLiving && entityplayer.getDistanceSqToEntity((Entity)event.entityLiving) >= 64.0) {
                sneaking = true;
            }
        }
        if (event.entityLiving instanceof EntityLiving && sneaking) {
            ((EntityLiving)event.entityLiving).setAttackTarget((EntityLivingBase)null);
        }
    }
    
    @SubscribeEvent
    public void onEntityAttackedByPlayer(final AttackEntityEvent event) {
        final Entity entity = event.target;
        final World world = entity.worldObj;
        final EntityPlayer entityplayer = event.entityPlayer;
        if (!world.isClient && (entity instanceof EntityHanging || entity instanceof LOTRBannerProtectable) && LOTRBannerProtection.isProtectedByBanner(world, entity, LOTRBannerProtection.forPlayer(entityplayer), true)) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onLivingAttacked(final LivingAttackEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final EntityLivingBase attacker = (event.source.getEntity() instanceof EntityLivingBase) ? event.source.getEntity() : null;
        final World world = ((Entity)entity).worldObj;
        if (entity instanceof LOTRNPCMount && ((Entity)entity).riddenByEntity != null && attacker == ((Entity)entity).riddenByEntity) {
            this.cancelAttackEvent(event);
        }
        if (attacker instanceof EntityPlayer && !LOTRMod.canPlayerAttackEntity((EntityPlayer)attacker, entity, true)) {
            this.cancelAttackEvent(event);
        }
        if (attacker instanceof EntityCreature && !LOTRMod.canNPCAttackEntity((EntityCreature)attacker, entity, false)) {
            this.cancelAttackEvent(event);
        }
        if (event.source instanceof EntityDamageSourceIndirect) {
            final Entity projectile = event.source.getSourceOfDamage();
            if (projectile instanceof EntityArrow || projectile instanceof LOTREntityCrossbowBolt || projectile instanceof LOTREntityDart) {
                boolean wearingAllGalvorn = true;
                for (int i = 0; i < 4; ++i) {
                    final ItemStack armour = entity.getEquipmentInSlot(i + 1);
                    if (armour == null || !(armour.getItem() instanceof ItemArmor) || ((ItemArmor)armour.getItem()).getArmorMaterial() != LOTRMaterial.GALVORN.toArmorMaterial()) {
                        wearingAllGalvorn = false;
                        break;
                    }
                }
                if (wearingAllGalvorn) {
                    if (!world.isClient && entity instanceof EntityPlayer) {
                        ((EntityPlayer)entity).inventory.damageArmor(event.ammount);
                    }
                    this.cancelAttackEvent(event);
                }
            }
            if (!world.isClient && entity instanceof EntityPlayer && attacker instanceof LOTREntityOrc && projectile instanceof LOTREntitySpear) {
                final ItemStack chestplate = entity.getEquipmentInSlot(3);
                if (chestplate != null && chestplate.getItem() == LOTRMod.bodyMithril) {
                    LOTRLevelData.getData((EntityPlayer)entity).addAchievement(LOTRAchievement.hitByOrcSpear);
                }
            }
        }
    }
    
    private void cancelAttackEvent(final LivingAttackEvent event) {
        event.setCanceled(true);
        final DamageSource source = event.source;
        if (source instanceof EntityDamageSourceIndirect) {
            source.getSourceOfDamage();
        }
    }
    
    @SubscribeEvent
    public void onLivingHurt(final LivingHurtEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final EntityLivingBase attacker = (event.source.getEntity() instanceof EntityLivingBase) ? event.source.getEntity() : null;
        final World world = ((Entity)entity).worldObj;
        if (entity instanceof EntityPlayerMP && event.source == LOTRDamage.frost) {
            LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
        }
        if (!world.isClient) {
            final int defaultMaxHurtResTime = 20;
            final int preMaxHurtResTime = entity.maxHurtResistantTime;
            int maxHurtResTime = 20;
            if (attacker != null) {
                final ItemStack weapon = attacker.getHeldItem();
                if (LOTRWeaponStats.isMeleeWeapon(weapon)) {
                    maxHurtResTime = LOTRWeaponStats.getAttackTimeWithBase(weapon, 20);
                }
            }
            maxHurtResTime = Math.min(maxHurtResTime, 20);
            entity.maxHurtResistantTime = maxHurtResTime;
            if (((Entity)entity).hurtResistantTime == preMaxHurtResTime) {
                ((Entity)entity).hurtResistantTime = maxHurtResTime;
            }
        }
        if (attacker != null && event.source.getSourceOfDamage() == attacker) {
            final ItemStack weapon2 = attacker.getHeldItem();
            if (!world.isClient && entity instanceof EntityPlayerMP) {
                final EntityPlayerMP entityplayer = (EntityPlayerMP)entity;
                if (entityplayer.isUsingItem()) {
                    final ItemStack usingItem = entityplayer.getHeldItem();
                    if (usingItem != null && LOTRWeaponStats.isRangedWeapon(usingItem)) {
                        entityplayer.clearItemInUse();
                        final LOTRPacketStopItemUse packet = new LOTRPacketStopItemUse();
                        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
                    }
                }
            }
            boolean wearingAllMorgul = true;
            for (int i = 0; i < 4; ++i) {
                final ItemStack armour = entity.getEquipmentInSlot(i + 1);
                if (armour == null || !(armour.getItem() instanceof ItemArmor) || ((ItemArmor)armour.getItem()).getArmorMaterial() != LOTRMaterial.MORGUL.toArmorMaterial()) {
                    wearingAllMorgul = false;
                    break;
                }
            }
            if (wearingAllMorgul && !world.isClient && weapon2 != null && weapon2.isItemStackDamageable()) {
                final int damage = weapon2.getItemDamage();
                final int maxDamage = weapon2.getMaxDamage();
                float durability = 1.0f - damage / (float)maxDamage;
                durability *= 0.9f;
                int newDamage = Math.round((1.0f - durability) * maxDamage);
                newDamage = Math.min(newDamage, maxDamage);
                weapon2.damageItem(newDamage - damage, attacker);
            }
            if (weapon2 != null) {
                Item.ToolMaterial material = null;
                if (weapon2.getItem() instanceof ItemTool) {
                    material = ((ItemTool)weapon2.getItem()).func_150913_i();
                }
                else if (weapon2.getItem() instanceof ItemSword) {
                    material = LOTRMaterial.getToolMaterialByName(((ItemSword)weapon2.getItem()).func_150932_j());
                }
                if (material != null && material == LOTRMaterial.MORGUL.toToolMaterial() && !world.isClient) {
                    entity.addPotionEffect(new PotionEffect(Potion.wither.id, 160));
                }
            }
        }
        if (event.source.getSourceOfDamage() instanceof LOTREntityArrowPoisoned && !world.isClient) {
            LOTRItemDagger.applyStandardPoison(entity);
        }
        if (!world.isClient) {
            if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(event.source, LOTREnchantment.fire)) {
                final LOTRPacketWeaponFX packet2 = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.INFERNAL, (Entity)entity);
                LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet2, LOTRPacketHandler.nearEntity((Entity)entity, 64.0));
            }
            if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(event.source, LOTREnchantment.chill)) {
                LOTREnchantmentWeaponSpecial.doChillAttack(entity);
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingDeath(final LivingDeathEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final World world = ((Entity)entity).worldObj;
        final DamageSource source = event.source;
        if (!world.isClient && entity instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
            final int j = MathHelper.floor_double(((Entity)entityplayer).posY);
            final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
            LOTRLevelData.getData(entityplayer).setDeathPoint(i, j, k);
            LOTRLevelData.getData(entityplayer).setDeathDimension(((Entity)entityplayer).dimension);
        }
        if (!world.isClient) {
            EntityPlayer entityplayer = null;
            boolean creditHiredUnit = false;
            boolean byNearbyUnit = false;
            if (source.getEntity() instanceof EntityPlayer) {
                entityplayer = (EntityPlayer)source.getEntity();
            }
            else if (entity.func_94060_bK() instanceof EntityPlayer) {
                entityplayer = (EntityPlayer)entity.func_94060_bK();
            }
            else if (source.getEntity() instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)source.getEntity();
                if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
                    final EntityPlayer hirer = entityplayer = npc.hiredNPCInfo.getHiringPlayer();
                    creditHiredUnit = true;
                    final double nearbyDist = 64.0;
                    byNearbyUnit = (npc.getDistanceSqToEntity((Entity)entityplayer) <= nearbyDist * nearbyDist);
                }
            }
            if (entityplayer != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                final LOTRFaction entityFaction = LOTRMod.getNPCFaction((Entity)entity);
                final float prevAlignment = playerData.getAlignment(entityFaction);
                List<LOTRFaction> forcedBonusFactions = null;
                if (entity instanceof LOTREntityNPC) {
                    forcedBonusFactions = ((LOTREntityNPC)entity).killBonusFactions;
                }
                boolean wasSelfDefenceAgainstAlliedUnit = false;
                if (!creditHiredUnit && prevAlignment > 0.0f && entity instanceof LOTREntityNPC) {
                    final LOTREntityNPC npc2 = (LOTREntityNPC)entity;
                    if (npc2.hiredNPCInfo.isActive && npc2.hiredNPCInfo.wasAttackCommanded) {
                        wasSelfDefenceAgainstAlliedUnit = true;
                    }
                }
                LOTRAlignmentValues.AlignmentBonus alignmentBonus = null;
                if (!wasSelfDefenceAgainstAlliedUnit) {
                    if (entity instanceof LOTREntityNPC) {
                        final LOTREntityNPC npc3 = (LOTREntityNPC)entity;
                        alignmentBonus = new LOTRAlignmentValues.AlignmentBonus(npc3.getAlignmentBonus(), npc3.getEntityClassName());
                        alignmentBonus.needsTranslation = true;
                        alignmentBonus.isCivilianKill = npc3.isCivilianNPC();
                    }
                    else {
                        final String s = EntityList.getEntityString((Entity)entity);
                        final Object obj = LOTREntityRegistry.registeredNPCs.get(s);
                        if (obj != null) {
                            final LOTREntityRegistry.RegistryInfo info = (LOTREntityRegistry.RegistryInfo)obj;
                            alignmentBonus = info.alignmentBonus;
                            alignmentBonus.isCivilianKill = false;
                        }
                    }
                }
                if (creditHiredUnit || wasSelfDefenceAgainstAlliedUnit) {}
                if (alignmentBonus != null && alignmentBonus.bonus != 0.0f && (!creditHiredUnit || (creditHiredUnit && byNearbyUnit))) {
                    alignmentBonus.isKill = true;
                    if (creditHiredUnit) {
                        alignmentBonus.killByHiredUnit = true;
                    }
                    playerData.addAlignment(entityplayer, alignmentBonus, entityFaction, forcedBonusFactions, (Entity)entity);
                }
                if (!creditHiredUnit) {
                    if (entityFaction.allowPlayer) {
                        playerData.getFactionData(entityFaction).addNPCKill();
                        final List<LOTRFaction> killBonuses = entityFaction.getBonusesForKilling();
                        for (final LOTRFaction enemy : killBonuses) {
                            playerData.getFactionData(enemy).addEnemyKill();
                        }
                        if (!entityplayer.capabilities.isCreativeMode) {
                            boolean recordBountyKill = entityFaction.inDefinedControlZone(entityplayer, Math.max(entityFaction.getControlZoneReducedRange(), 50));
                            if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).isInvasionSpawned) {
                                recordBountyKill = false;
                            }
                            if (recordBountyKill) {
                                LOTRFactionBounties.forFaction(entityFaction).forPlayer(entityplayer).recordNewKill();
                            }
                        }
                        final LOTRFaction pledgeFac = playerData.getPledgeFaction();
                        if (pledgeFac != null && (pledgeFac == entityFaction || pledgeFac.isAlly(entityFaction))) {
                            playerData.onPledgeKill(entityplayer);
                        }
                    }
                    final float newAlignment = playerData.getAlignment(entityFaction);
                    if (!wasSelfDefenceAgainstAlliedUnit && !entityplayer.capabilities.isCreativeMode && entityFaction != LOTRFaction.UNALIGNED) {
                        int sentSpeeches = 0;
                        final int maxSpeeches = 5;
                        final double range = 8.0;
                        final List nearbyAlliedNPCs = world.selectEntitiesWithinAABB((Class)EntityLiving.class, ((Entity)entity).boundingBox.expand(range, range, range), (IEntitySelector)new IEntitySelector() {
                            public boolean isEntityApplicable(final Entity entitySelect) {
                                if (entitySelect.isEntityAlive()) {
                                    final LOTRFaction fac = LOTRMod.getNPCFaction(entitySelect);
                                    return fac.isGoodRelation(entityFaction);
                                }
                                return false;
                            }
                        });
                        for (int l = 0; l < nearbyAlliedNPCs.size(); ++l) {
                            final EntityLiving npc4 = nearbyAlliedNPCs.get(l);
                            if (!(npc4 instanceof LOTREntityNPC) || !((LOTREntityNPC)npc4).hiredNPCInfo.isActive || newAlignment <= 0.0f) {
                                if (npc4.getAttackTarget() == null) {
                                    npc4.setAttackTarget((EntityLivingBase)entityplayer);
                                    if (npc4 instanceof LOTREntityNPC && sentSpeeches < maxSpeeches) {
                                        final LOTREntityNPC lotrnpc = (LOTREntityNPC)npc4;
                                        final String speech = lotrnpc.getSpeechBank(entityplayer);
                                        if (speech != null && lotrnpc.getDistanceSqToEntity((Entity)entityplayer) < range) {
                                            lotrnpc.sendSpeechBank(entityplayer, speech);
                                            ++sentSpeeches;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!playerData.isSiegeActive()) {
                        final List<LOTRMiniQuest> miniquests = playerData.getMiniQuests();
                        for (final LOTRMiniQuest quest : miniquests) {
                            quest.onKill(entityplayer, entity);
                        }
                        if (entity instanceof EntityPlayer) {
                            final EntityPlayer slainPlayer = (EntityPlayer)entity;
                            final List<LOTRMiniQuest> slainMiniquests = LOTRLevelData.getData(slainPlayer).getMiniQuests();
                            for (final LOTRMiniQuest quest2 : slainMiniquests) {
                                quest2.onKilledByPlayer(slainPlayer, entityplayer);
                            }
                        }
                    }
                }
            }
        }
        if (!world.isClient && source.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)source.getEntity();
            LOTREnchantmentHelper.onKillEntity(entityplayer, entity, source);
        }
        if (!world.isClient && source.getEntity() instanceof EntityPlayer && source.getSourceOfDamage() != null && source.getSourceOfDamage().getClass() == LOTREntitySpear.class) {
            final EntityPlayer entityplayer = (EntityPlayer)source.getEntity();
            if (entity != entityplayer && entityplayer.getDistanceSqToEntity((Entity)entity) >= 2500.0) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useSpearFromFar);
            }
        }
        if (!world.isClient && entity instanceof LOTREntityButterfly && source.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)source.getEntity();
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killButterfly);
        }
        if (!world.isClient && entity.getClass() == LOTREntityHorse.class && source.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)source.getEntity();
            if (!entityplayer.capabilities.isCreativeMode) {
                final List rohirrimList = world.getEntitiesWithinAABB((Class)LOTREntityRohanMan.class, ((Entity)entityplayer).boundingBox.expand(16.0, 16.0, 16.0));
                if (!rohirrimList.isEmpty()) {
                    boolean sentMessage = false;
                    boolean penalty = false;
                    for (int m = 0; m < rohirrimList.size(); ++m) {
                        final LOTREntityRohanMan rohirrim = rohirrimList.get(m);
                        if (!rohirrim.hiredNPCInfo.isActive || rohirrim.hiredNPCInfo.getHiringPlayer() != entityplayer) {
                            rohirrim.setAttackTarget((EntityLivingBase)entityplayer);
                            if (!sentMessage) {
                                rohirrim.sendSpeechBank(entityplayer, "rohan/warrior/avengeHorse");
                                sentMessage = true;
                            }
                            if (!penalty) {
                                LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.ROHAN_HORSE_PENALTY, LOTRFaction.ROHAN, (Entity)entity);
                                penalty = true;
                            }
                        }
                    }
                }
            }
        }
        if (!world.isClient) {
            EntityPlayer attackingPlayer = null;
            LOTREntityNPC attackingHiredUnit = null;
            if (source.getEntity() instanceof EntityPlayer) {
                attackingPlayer = (EntityPlayer)source.getEntity();
            }
            else if (source.getEntity() instanceof LOTREntityNPC) {
                final LOTREntityNPC npc5 = (LOTREntityNPC)source.getEntity();
                if (npc5.hiredNPCInfo.isActive && npc5.hiredNPCInfo.getHiringPlayer() != null) {
                    attackingPlayer = npc5.hiredNPCInfo.getHiringPlayer();
                    attackingHiredUnit = npc5;
                }
            }
            if (attackingPlayer != null) {
                final boolean isFoe = LOTRLevelData.getData(attackingPlayer).getAlignment(LOTRMod.getNPCFaction((Entity)entity)) < 0.0f;
                if (isFoe) {
                    if (attackingHiredUnit != null) {
                        if (attackingHiredUnit instanceof LOTREntityWargBombardier) {
                            LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.hireWargBombardier);
                        }
                        if (attackingHiredUnit instanceof LOTREntityOlogHai) {
                            LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.hireOlogHai);
                        }
                    }
                    else {
                        if (attackingPlayer.isPotionActive(Potion.confusion.id)) {
                            LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.killWhileDrunk);
                        }
                        if (entity instanceof LOTREntityOrc) {
                            final LOTREntityOrc orc = (LOTREntityOrc)entity;
                            if (orc.isOrcBombardier() && orc.npcItemsInv.getBomb() != null) {
                                LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.killBombardier);
                            }
                        }
                        if (source.getSourceOfDamage() instanceof LOTREntityCrossbowBolt) {
                            LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.useCrossbow);
                        }
                        if (source.getSourceOfDamage() instanceof LOTREntityThrowingAxe) {
                            final LOTREntityThrowingAxe axe = (LOTREntityThrowingAxe)source.getSourceOfDamage();
                            if (axe.getProjectileItem().getItem() == LOTRMod.throwingAxeDwarven) {
                                LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.useDwarvenThrowingAxe);
                            }
                        }
                    }
                }
            }
        }
        if (!world.isClient && LOTRMod.getNPCFaction((Entity)entity) == LOTRFaction.UTUMNO && LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO) {
            final Entity attacker = source.getEntity();
            if (attacker instanceof EntityPlayer) {
                final int i = MathHelper.floor_double(((Entity)entity).posX);
                final int j = MathHelper.floor_double(((Entity)entity).boundingBox.minY);
                final int k = MathHelper.floor_double(((Entity)entity).posZ);
                for (int range2 = LOTRBlockUtumnoReturnPortalBase.RANGE, i2 = -range2; i2 <= range2; ++i2) {
                    for (int j2 = -range2; j2 <= range2; ++j2) {
                        for (int k2 = -range2; k2 <= range2; ++k2) {
                            final int i3 = i + i2;
                            final int j3 = j + j2;
                            final int k3 = k + k2;
                            if (world.getBlock(i3, j3, k3) == LOTRMod.utumnoReturnPortalBase) {
                                int meta = world.getBlockMetadata(i3, j3, k3);
                                if (++meta >= LOTRBlockUtumnoReturnPortalBase.MAX_SACRIFICE) {
                                    world.createExplosion(attacker, i3 + 0.5, j3 + 0.5, k3 + 0.5, 0.0f, false);
                                    world.setBlock(i3, j3, k3, LOTRMod.utumnoReturnPortal, 0, 3);
                                }
                                else {
                                    world.setBlockMetadata(i3, j3, k3, meta, 3);
                                }
                                final LOTRPacketUtumnoKill packet = new LOTRPacketUtumnoKill(entity.getEntityId(), i3, j3, k3);
                                LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, new NetworkRegistry.TargetPoint(((Entity)entity).dimension, i3 + 0.5, j3 + 0.5, k3 + 0.5, 32.0));
                            }
                        }
                    }
                }
            }
        }
        if (!world.isClient && entity instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(source, LOTREnchantment.headhunting)) {
                final ItemStack playerHead = new ItemStack(Items.skull, 1, 3);
                final GameProfile profile = entityplayer.getGameProfile();
                final NBTTagCompound profileData = new NBTTagCompound();
                NBTUtil.func_152460_a(profileData, profile);
                playerHead.setTagInfo("SkullOwner", (NBTBase)profileData);
                entityplayer.entityDropItem(playerHead, 0.0f);
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingDrops(final LivingDropsEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final Random rand = entity.getRNG();
        final List drops = event.drops;
        final int i = event.lootingLevel;
        final boolean flag = event.recentlyHit;
        if (entity instanceof EntitySheep && LOTRConfig.dropMutton) {
            for (int meat = rand.nextInt(3) + rand.nextInt(1 + i), l = 0; l < meat; ++l) {
                if (entity.isBurning()) {
                    entity.func_145779_a(LOTRMod.muttonCooked, 1);
                }
                else {
                    entity.func_145779_a(LOTRMod.muttonRaw, 1);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onExplosionDetonate(final ExplosionEvent.Detonate event) {
        final Explosion expl = event.explosion;
        final World world = event.world;
        final Entity exploder = expl.exploder;
        if (!world.isClient && exploder != null) {
            LOTRBannerProtection.IFilter protectFilter = null;
            if (exploder instanceof LOTREntityNPC) {
                protectFilter = LOTRBannerProtection.anyBanner();
            }
            else if (exploder instanceof EntityMob) {
                protectFilter = LOTRBannerProtection.anyBanner();
            }
            else if (exploder instanceof EntityThrowable) {
                protectFilter = LOTRBannerProtection.forThrown((EntityThrowable)exploder);
            }
            else if (exploder instanceof EntityTNTPrimed) {
                protectFilter = LOTRBannerProtection.forTNT((EntityTNTPrimed)exploder);
            }
            else if (exploder instanceof EntityMinecartTNT) {
                protectFilter = LOTRBannerProtection.forTNTMinecart((EntityMinecartTNT)exploder);
            }
            if (protectFilter != null) {
                final List<ChunkPosition> blockList = (List<ChunkPosition>)expl.affectedBlockPositions;
                final List<ChunkPosition> removes = new ArrayList<ChunkPosition>();
                for (final ChunkPosition blockPos : blockList) {
                    final int i = blockPos.chunkPosX;
                    final int j = blockPos.chunkPosY;
                    final int k = blockPos.chunkPosZ;
                    if (LOTRBannerProtection.isProtectedByBanner(world, i, j, k, protectFilter, false)) {
                        removes.add(blockPos);
                    }
                }
                blockList.removeAll(removes);
            }
        }
    }
    
    @SubscribeEvent
    public void onServerChat(final ServerChatEvent event) {
        final EntityPlayerMP entityplayer = event.player;
        final String message = event.message;
        final String username = event.username;
        ChatComponentTranslation chatComponent = event.component;
        if (LOTRConfig.drunkMessages) {
            final PotionEffect nausea = entityplayer.getActivePotionEffect(Potion.confusion);
            if (nausea != null) {
                final int duration = nausea.getDuration();
                float chance = duration / 4800.0f;
                chance = Math.min(chance, 1.0f);
                chance *= 0.4f;
                final Random rand = entityplayer.getRNG();
                final String key = chatComponent.getKey();
                final Object[] formatArgs = chatComponent.getFormatArgs();
                for (int a = 0; a < formatArgs.length; ++a) {
                    final Object arg = formatArgs[a];
                    String chatText = null;
                    if (arg instanceof ChatComponentText) {
                        final ChatComponentText componentText = (ChatComponentText)arg;
                        chatText = componentText.getUnformattedText();
                    }
                    else if (arg instanceof String) {
                        chatText = (String)arg;
                    }
                    if (chatText != null && chatText.equals(message)) {
                        final String newText = LOTRDrunkenSpeech.getDrunkenSpeech(chatText, chance);
                        if (arg instanceof String) {
                            formatArgs[a] = newText;
                        }
                        else if (arg instanceof ChatComponentText) {
                            formatArgs[a] = new ChatComponentText(newText);
                        }
                    }
                }
                final ChatComponentTranslation newComponent = chatComponent = new ChatComponentTranslation(key, formatArgs);
            }
        }
        if (LOTRConfig.enableTitles) {
            final LOTRTitle.PlayerTitle playerTitle = LOTRLevelData.getData((EntityPlayer)entityplayer).getPlayerTitle();
            if (playerTitle != null) {
                final List newFormatArgs = new ArrayList();
                for (final Object arg2 : chatComponent.getFormatArgs()) {
                    if (arg2 instanceof ChatComponentText) {
                        final ChatComponentText componentText2 = (ChatComponentText)arg2;
                        if (componentText2.getUnformattedText().contains(username)) {
                            final IChatComponent usernameComponent = (IChatComponent)componentText2;
                            final IChatComponent titleComponent = playerTitle.getFullTitleComponent();
                            final IChatComponent fullUsernameComponent = new ChatComponentText("").appendSibling(titleComponent).appendSibling(usernameComponent);
                            newFormatArgs.add(fullUsernameComponent);
                        }
                        else {
                            newFormatArgs.add(componentText2);
                        }
                    }
                    else {
                        newFormatArgs.add(arg2);
                    }
                }
                final ChatComponentTranslation newChatComponent = new ChatComponentTranslation(chatComponent.getKey(), newFormatArgs.toArray());
                newChatComponent.setChatStyle(chatComponent.getChatStyle().createShallowCopy());
                for (final Object sibling : chatComponent.getSiblings()) {
                    newChatComponent.appendSibling((IChatComponent)sibling);
                }
                chatComponent = newChatComponent;
            }
        }
        event.component = chatComponent;
        if (MinecraftServer.getServer().isDedicatedServer() && !((EntityPlayer)entityplayer).capabilities.isCreativeMode && !LOTRLevelData.getData((EntityPlayer)entityplayer).getAskedForGandalf() && StringUtils.containsIgnoreCase((CharSequence)message, (CharSequence)"add gandalf")) {
            boolean success = false;
            LOTRInvasions[] invasions = LOTRInvasions.values();
            final List<LOTRInvasions> invasionsAsList = Arrays.asList(invasions);
            Collections.shuffle(invasionsAsList);
            final LOTRInvasions[] array;
            invasions = (array = invasionsAsList.toArray(invasions));
            for (final LOTRInvasions invasion : array) {
                if (!invasion.invasionMobs.isEmpty()) {
                    if (LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(invasion.invasionFaction) < 0.0f) {
                        final LOTREntityInvasionSpawner invasionSpawner = new LOTREntityInvasionSpawner(((Entity)entityplayer).worldObj);
                        invasionSpawner.setInvasionType(invasion);
                        final double x = ((Entity)entityplayer).posX;
                        final double y = ((Entity)entityplayer).boundingBox.minY + 3.0 + entityplayer.getRNG().nextDouble() * 2.0;
                        final double z = ((Entity)entityplayer).posZ;
                        invasionSpawner.setLocationAndAngles(x, y, z, 0.0f, 0.0f);
                        if (invasionSpawner.canInvasionSpawnHere()) {
                            ((Entity)entityplayer).worldObj.spawnEntityInWorld((Entity)invasionSpawner);
                            invasionSpawner.announceInvasionToEnemies();
                            success = true;
                            break;
                        }
                    }
                }
            }
            if (success) {
                LOTRLevelData.getData((EntityPlayer)entityplayer).setAskedForGandalf(true);
            }
        }
    }
}
