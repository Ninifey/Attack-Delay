// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.world.World;
import java.util.Iterator;
import java.util.Collection;
import net.minecraftforge.common.config.ConfigElement;
import java.util.ArrayList;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.FMLLog;
import lotr.compatibility.LOTRModChecker;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.util.List;
import net.minecraftforge.common.config.Configuration;

public class LOTRConfig
{
    public static Configuration config;
    private static List<String> allCategories;
    private static String CATEGORY_DIMENSION;
    private static String CATEGORY_GAMEPLAY;
    private static String CATEGORY_GUI;
    private static String CATEGORY_ENVIRONMENT;
    private static String CATEGORY_MISC;
    public static boolean allowBannerProtection;
    public static boolean allowSelfProtectingBanners;
    public static boolean allowMiniquests;
    public static boolean allowBountyQuests;
    public static boolean enableTitles;
    public static boolean enableFastTravel;
    public static boolean alignmentDrain;
    public static boolean enableConquest;
    public static boolean removeGoldenAppleRecipes;
    public static boolean enablePortals;
    public static boolean enableOrcSkirmish;
    public static boolean enchantingVanilla;
    public static boolean enchantingLOTR;
    public static boolean enchantingAutoRemoveVanilla;
    public static boolean enablePotionBrewing;
    public static int bannerWarningCooldown;
    public static boolean dropMutton;
    public static boolean drunkMessages;
    public static boolean middleEarthRespawning;
    public static int MERBedRespawnThreshold;
    public static int MERWorldRespawnThreshold;
    public static int MERMinRespawn;
    public static int MERMaxRespawn;
    public static boolean generateMapFeatures;
    public static boolean changedHunger;
    public static boolean canAlwaysEat;
    public static int forceMapLocations;
    public static boolean enableBandits;
    public static boolean enableInvasions;
    public static boolean removeDiamondArmorRecipes;
    public static boolean disableEnderChestsUtumno;
    public static int preventTraderKidnap;
    public static boolean alwaysShowAlignment;
    public static int alignmentXOffset;
    public static int alignmentYOffset;
    public static boolean displayAlignmentAboveHead;
    public static boolean enableSepiaMap;
    public static boolean osrsMap;
    public static boolean enableOnscreenCompass;
    public static boolean compassExtraInfo;
    public static boolean hiredUnitHealthBars;
    public static boolean hiredUnitIcons;
    public static boolean elvenBladeGlow;
    public static boolean immersiveSpeech;
    public static boolean immersiveSpeechChatLog;
    public static boolean meleeAttackMeter;
    public static boolean mapLabels;
    public static boolean mapLabelsConquest;
    public static boolean enableQuestTracker;
    public static boolean trackingQuestRight;
    public static boolean customMainMenu;
    public static boolean fellowPlayerHealthBars;
    public static boolean displayCoinCounts;
    public static boolean balrogWings;
    public static boolean enableLOTRSky;
    public static boolean enableMistyMountainsMist;
    public static boolean enableAmbience;
    public static boolean enableSunFlare;
    public static int cloudRange;
    public static boolean newRain;
    public static boolean updateLangFiles;
    public static boolean checkUpdates;
    public static boolean strTimelapse;
    public static int strTimelapseInterval;
    public static boolean protectHobbitKillers;
    public static boolean fixMobSpawning;
    public static int mobSpawnInterval;
    public static int musicIntervalMin;
    public static int musicIntervalMax;
    public static boolean displayMusicTrack;
    public static int musicIntervalMenuMin;
    public static int musicIntervalMenuMax;
    public static boolean fixRenderDistance;
    public static boolean preventMessageExploit;
    
    private static String getCategory(final String category) {
        LOTRConfig.allCategories.add(category);
        return category;
    }
    
    public static void setupAndLoad(final FMLPreInitializationEvent event) {
        LOTRConfig.config = new Configuration(event.getSuggestedConfigurationFile());
        load();
    }
    
    public static void load() {
        LOTRDimension.configureDimensions(LOTRConfig.config, LOTRConfig.CATEGORY_DIMENSION);
        LOTRConfig.allowBannerProtection = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Allow Banner Protection", true).getBoolean();
        LOTRConfig.allowSelfProtectingBanners = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Allow Self-Protecting Banners", true).getBoolean();
        LOTRConfig.allowMiniquests = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "NPCs give mini-quests", true).getBoolean();
        LOTRConfig.allowBountyQuests = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "NPCs give bounty mini-quests", true, "Allow NPCs to generate mini-quests to kill enemy players").getBoolean();
        LOTRConfig.enableTitles = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Titles", true).getBoolean();
        LOTRConfig.enableFastTravel = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Fast Travel", true).getBoolean();
        LOTRConfig.alignmentDrain = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable alignment drain", true, "Factions dislike if a player has + alignment with enemy factions").getBoolean();
        LOTRConfig.enableConquest = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Conquest", true).getBoolean();
        LOTRConfig.removeGoldenAppleRecipes = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Remove Golden Apple recipes", true).getBoolean();
        LOTRConfig.enablePortals = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Middle-earth Portals", true, "Enable or disable the buildable Middle-earth portals (excluding the Ring Portal). If disabled, portals can still be made, but will not function").getBoolean();
        LOTRConfig.enableOrcSkirmish = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Orc Skirmishes", true).getBoolean();
        LOTRConfig.enchantingVanilla = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enchanting: Vanilla System", false, "Enable the vanilla enchanting system: if disabled, prevents players from enchanting items, but does not affect existing enchanted items").getBoolean();
        LOTRConfig.enchantingLOTR = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enchanting: LOTR System", true, "Enable the LOTR enchanting system: if disabled, prevents newly crafted items, loot chest items, etc. from having modifiers applied, but does not affect existing modified items").getBoolean();
        LOTRConfig.enchantingAutoRemoveVanilla = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enchanting: Auto-remove vanilla enchants", false, "Intended for servers. If enabled, enchantments will be automatically removed from items").getBoolean();
        LOTRConfig.enablePotionBrewing = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Potion Brewing", true, "Mainly intended for servers. Disable the vanilla potion brewing system, as it is not 'lore-friendly'").getBoolean();
        LOTRConfig.bannerWarningCooldown = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Protection Warning Cooldown", 20, "Cooldown time (in ticks) between appearances of the warning message for banner-protected land").getInt();
        LOTRConfig.dropMutton = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Mutton Drops", true, "Enable or disable sheep dropping the mod's mutton items").getBoolean();
        LOTRConfig.drunkMessages = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Drunken Messages", true).getBoolean();
        LOTRConfig.middleEarthRespawning = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Middle-earth Respawning: Enable", true, "If enabled, when a player dies in Middle-earth far from their spawn point, they will respawn somewhere near their death point instead").getBoolean();
        LOTRConfig.MERBedRespawnThreshold = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Middle-earth Respawning: Bed Threshold", 5000, "Threshold distance from spawn for applying Middle-earth Respawning when the player's spawn point is a bed").getInt();
        LOTRConfig.MERWorldRespawnThreshold = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Middle-earth Respawning: World Threshold", 2000, "Threshold distance from spawn for applying Middle-earth respawning when the player's spawn point is the world spawn (no bed)").getInt();
        LOTRConfig.MERMinRespawn = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Middle-earth Respawning: Min Respawn Range", 500, "Minimum possible range to place the player from their death point").getInt();
        LOTRConfig.MERMaxRespawn = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Middle-earth Respawning: Max Respawn Range", 1500, "Maximum possible range to place the player from their death point").getInt();
        LOTRConfig.generateMapFeatures = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Generate map features", true).getBoolean();
        LOTRConfig.changedHunger = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Hunger changes", true, "Food meter decreases more slowly").getBoolean();
        LOTRConfig.canAlwaysEat = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Feast Mode", true, "Food can always be eaten regardless of hunger").getBoolean();
        LOTRConfig.forceMapLocations = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Force Hide/Show Map Locations", 0, "Force hide or show players' map locations. 0 = per-player (default), 1 = force hide, 2 = force show").getInt();
        LOTRConfig.enableBandits = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Bandits", true).getBoolean();
        LOTRConfig.enableInvasions = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Enable Invasions", true).getBoolean();
        LOTRConfig.removeDiamondArmorRecipes = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Remove diamond armour recipes", false).getBoolean();
        LOTRConfig.disableEnderChestsUtumno = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Disable ender chests in Utumno", false).getBoolean();
        LOTRConfig.preventTraderKidnap = LOTRConfig.config.get(LOTRConfig.CATEGORY_GAMEPLAY, "Prevent trader transport range", 0, "Prevent transport of structure-bound traders beyond this distance outside their initial home range (0 = disabled)").getInt();
        LOTRConfig.alwaysShowAlignment = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Always show alignment", false, "If set to false, the alignment bar will only be shown in Middle-earth. If set to true, it will be shown in all dimensions").getBoolean();
        LOTRConfig.alignmentXOffset = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Alignment x-offset", 0, "Configure the x-position of the alignment bar on-screen. Negative values move it left, positive values right").getInt();
        LOTRConfig.alignmentYOffset = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Alignment y-offset", 0, "Configure the y-position of the alignment bar on-screen. Negative values move it up, positive values down").getInt();
        LOTRConfig.displayAlignmentAboveHead = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Display alignment above head", true, "Enable or disable the rendering of other players' alignment values above their heads").getBoolean();
        LOTRConfig.enableSepiaMap = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Sepia Map", false, "Display the Middle-earth map in sepia colours").getBoolean();
        LOTRConfig.osrsMap = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "OSRS Map", false, "It's throwback time. (Requires game restart)").getBoolean();
        LOTRConfig.enableOnscreenCompass = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "On-screen Compass", true).getBoolean();
        LOTRConfig.compassExtraInfo = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "On-screen Compass Extra Info", true, "Display co-ordinates and biome below compass").getBoolean();
        LOTRConfig.hiredUnitHealthBars = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Hired NPC Health Bars", true).getBoolean();
        LOTRConfig.hiredUnitIcons = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Hired NPC Icons", true).getBoolean();
        LOTRConfig.elvenBladeGlow = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Animated Elven blade glow", true).getBoolean();
        LOTRConfig.immersiveSpeech = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Immersive Speech", true, "If set to true, NPC speech will appear on-screen with the NPC. If set to false, it will be sent to the chat box").getBoolean();
        LOTRConfig.immersiveSpeechChatLog = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Immersive Speech Chat Logs", false, "Toggle whether speech still shows in the chat box when Immersive Speech is enabled").getBoolean();
        LOTRConfig.meleeAttackMeter = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Melee attack meter", true).getBoolean();
        LOTRConfig.mapLabels = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Map Labels", true).getBoolean();
        LOTRConfig.mapLabelsConquest = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Map Labels - Conquest", true).getBoolean();
        LOTRConfig.enableQuestTracker = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Enable quest tracker", true).getBoolean();
        LOTRConfig.trackingQuestRight = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Flip quest tracker", false, "Display the quest tracker on the right-hand side of the screen instead of the left").getBoolean();
        LOTRConfig.customMainMenu = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Custom main menu", true, "Use the mod's custom main menu screen").getBoolean();
        LOTRConfig.fellowPlayerHealthBars = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Fellow Player Health Bars", true).getBoolean();
        LOTRConfig.displayCoinCounts = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Inventory coin counts", true).getBoolean();
        LOTRConfig.balrogWings = LOTRConfig.config.get(LOTRConfig.CATEGORY_GUI, "Balrog Wings", true, "Choose your side in the legendary debate...").getBoolean();
        LOTRConfig.enableLOTRSky = LOTRConfig.config.get(LOTRConfig.CATEGORY_ENVIRONMENT, "Middle-earth sky", true, "Toggle the new Middle-earth sky").getBoolean();
        LOTRConfig.enableMistyMountainsMist = LOTRConfig.config.get(LOTRConfig.CATEGORY_ENVIRONMENT, "Misty Misty Mountains", true, "Toggle mist overlay in the Misty Mountains").getBoolean();
        LOTRConfig.enableAmbience = LOTRConfig.config.get(LOTRConfig.CATEGORY_ENVIRONMENT, "Ambience", true).getBoolean();
        LOTRConfig.enableSunFlare = LOTRConfig.config.get(LOTRConfig.CATEGORY_ENVIRONMENT, "Sun flare", true).getBoolean();
        LOTRConfig.cloudRange = LOTRConfig.config.get(LOTRConfig.CATEGORY_ENVIRONMENT, "Cloud range", 1024, "Middle-earth cloud rendering range. To use vanilla clouds, set this to a non-positive value").getInt();
        LOTRConfig.newRain = LOTRConfig.config.get(LOTRConfig.CATEGORY_ENVIRONMENT, "New rain", true, "New rain sounds and mist during rain").getBoolean();
        LOTRConfig.updateLangFiles = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Run language update helper", true, "Run the mod's language file update helper on launch - see .minecraft/mods/LOTR_UpdatedLangFiles/readme.txt").getBoolean();
        LOTRConfig.checkUpdates = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Check for updates", true, "Disable this if you will be playing offline").getBoolean();
        LOTRConfig.strTimelapse = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Structure Timelapse", false, "Structure spawners generate as a timelapse instead of instantly. WARNING: May be buggy. See also the command /strTimelapse").getBoolean();
        LOTRConfig.strTimelapseInterval = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Structure Timelapse Interval", 5, "Structure timelapse interval (in ms) between each block placement").getInt();
        if (LOTRConfig.strTimelapseInterval < 0) {
            setStructureTimelapseInterval(0);
        }
        LOTRConfig.protectHobbitKillers = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Protect Hobbit Killers", false, "For servers: Disable broadcasting of the 'Hobbit Slayer' achievement, to protect new evil players from being persecuted").getBoolean();
        LOTRConfig.fixMobSpawning = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Fix mob spawning lag", true, "Fix a major source of server lag caused by the vanilla mob spawning system").getBoolean();
        LOTRConfig.mobSpawnInterval = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Mob spawn interval", 0, "Tick interval between mob spawn cycles (which are then run multiple times to compensate). Higher values may reduce server lag").getInt();
        LOTRConfig.musicIntervalMin = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Music Interval: Min.", 30, "Minimum time (seconds) between LOTR music tracks").getInt();
        LOTRConfig.musicIntervalMax = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Music Interval: Max.", 150, "Maximum time (seconds) between LOTR music tracks").getInt();
        LOTRConfig.displayMusicTrack = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Display music track", false, "Display the name of a LOTR music track when it begins playing").getBoolean();
        LOTRConfig.musicIntervalMenuMin = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Menu Music Interval: Min.", 10, "Minimum time (seconds) between LOTR menu music tracks").getInt();
        LOTRConfig.musicIntervalMenuMax = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Menu Music Interval: Max.", 20, "Maximum time (seconds) between LOTR menu music tracks").getInt();
        LOTRConfig.fixRenderDistance = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Fix render distance", true, "Fix a vanilla crash caused by having render distance > 16 in the options.txt. NOTE: This will not run if Optifine is installed").getBoolean();
        LOTRConfig.preventMessageExploit = LOTRConfig.config.get(LOTRConfig.CATEGORY_MISC, "Fix /msg exploit", true, "Disable usage of @a, @r, etc. in the /msg command, to prevent exploiting it as a player locator").getBoolean();
        if (LOTRModChecker.isCauldronServer()) {
            FMLLog.info("LOTR: Successfully detected Cauldron server and disabled: nothing! (Thanks, ASM!)", new Object[0]);
        }
        if (LOTRConfig.config.hasChanged()) {
            LOTRConfig.config.save();
        }
    }
    
    public static void toggleSepia() {
        LOTRConfig.enableSepiaMap = !LOTRConfig.enableSepiaMap;
        LOTRConfig.config.getCategory(LOTRConfig.CATEGORY_GUI).get("Sepia Map").set(LOTRConfig.enableSepiaMap);
        LOTRConfig.config.save();
    }
    
    public static void toggleMapLabels() {
        LOTRConfig.mapLabels = !LOTRConfig.mapLabels;
        LOTRConfig.config.getCategory(LOTRConfig.CATEGORY_GUI).get("Map Labels").set(LOTRConfig.mapLabels);
        LOTRConfig.config.save();
    }
    
    public static void toggleMapLabelsConquest() {
        LOTRConfig.mapLabelsConquest = !LOTRConfig.mapLabelsConquest;
        LOTRConfig.config.getCategory(LOTRConfig.CATEGORY_GUI).get("Map Labels - Conquest").set(LOTRConfig.mapLabelsConquest);
        LOTRConfig.config.save();
    }
    
    public static void setStructureTimelapse(final boolean flag) {
        LOTRConfig.strTimelapse = flag;
        LOTRConfig.config.getCategory(LOTRConfig.CATEGORY_MISC).get("Structure Timelapse").set(LOTRConfig.strTimelapse);
        LOTRConfig.config.save();
    }
    
    public static void setStructureTimelapseInterval(int i) {
        i = (LOTRConfig.strTimelapseInterval = Math.max(i, 0));
        LOTRConfig.config.getCategory(LOTRConfig.CATEGORY_MISC).get("Structure Timelapse Interval").set(LOTRConfig.strTimelapseInterval);
        LOTRConfig.config.save();
    }
    
    public static List<IConfigElement> getConfigElements() {
        final List<IConfigElement> list = new ArrayList<IConfigElement>();
        for (final String category : LOTRConfig.allCategories) {
            list.addAll(new ConfigElement(LOTRConfig.config.getCategory(category)).getChildElements());
        }
        return list;
    }
    
    public static boolean isEnchantingEnabled(final World world) {
        if (!world.isClient) {
            return LOTRConfig.enchantingVanilla;
        }
        return LOTRLevelData.clientside_thisServer_enchanting;
    }
    
    public static boolean isLOTREnchantingEnabled(final World world) {
        if (!world.isClient) {
            return LOTRConfig.enchantingLOTR;
        }
        return LOTRLevelData.clientside_thisServer_enchantingLOTR;
    }
    
    static {
        LOTRConfig.allCategories = new ArrayList<String>();
        LOTRConfig.CATEGORY_DIMENSION = getCategory("dimension");
        LOTRConfig.CATEGORY_GAMEPLAY = getCategory("gameplay");
        LOTRConfig.CATEGORY_GUI = getCategory("gui");
        LOTRConfig.CATEGORY_ENVIRONMENT = getCategory("environment");
        LOTRConfig.CATEGORY_MISC = getCategory("misc");
    }
}
