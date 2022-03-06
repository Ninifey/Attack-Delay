// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import java.util.List;
import java.util.Map;
import lotr.common.LOTRPlayerData;
import com.google.common.math.IntMath;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRDimension;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class LOTRKeyHandler
{
    public static KeyBinding keyBindingMenu;
    public static KeyBinding keyBindingMapTeleport;
    public static KeyBinding keyBindingFastTravel;
    public static KeyBinding keyBindingAlignmentCycleLeft;
    public static KeyBinding keyBindingAlignmentCycleRight;
    public static KeyBinding keyBindingAlignmentGroupPrev;
    public static KeyBinding keyBindingAlignmentGroupNext;
    private static Minecraft mc;
    private static int alignmentChangeTick;
    private static final int alignmentChangeTime = 2;
    
    public LOTRKeyHandler() {
        FMLCommonHandler.instance().bus().register((Object)this);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingMenu);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingMapTeleport);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingFastTravel);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingAlignmentCycleLeft);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingAlignmentCycleRight);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingAlignmentGroupPrev);
        ClientRegistry.registerKeyBinding(LOTRKeyHandler.keyBindingAlignmentGroupNext);
    }
    
    @SubscribeEvent
    public void MouseInputEvent(final InputEvent.MouseInputEvent event) {
        LOTRAttackTiming.doAttackTiming();
    }
    
    @SubscribeEvent
    public void KeyInputEvent(final InputEvent.KeyInputEvent event) {
        LOTRAttackTiming.doAttackTiming();
        if (LOTRKeyHandler.keyBindingMenu.getIsKeyPressed() && LOTRKeyHandler.mc.currentScreen == null) {
            LOTRKeyHandler.mc.thePlayer.openGui((Object)LOTRMod.instance, 11, (World)LOTRKeyHandler.mc.theWorld, 0, 0, 0);
        }
        final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)LOTRKeyHandler.mc.thePlayer);
        boolean usedAlignmentKeys = false;
        final Map<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions = new HashMap<LOTRDimension.DimensionRegion, LOTRFaction>();
        final LOTRDimension currentDimension = LOTRDimension.getCurrentDimension((World)LOTRKeyHandler.mc.theWorld);
        LOTRFaction currentFaction = pd.getViewingFaction();
        LOTRDimension.DimensionRegion currentRegion = currentFaction.factionRegion;
        final List<LOTRDimension.DimensionRegion> regionList = currentDimension.dimensionRegions;
        List<LOTRFaction> factionList = currentRegion.factionList;
        if (LOTRKeyHandler.mc.currentScreen == null && LOTRKeyHandler.alignmentChangeTick <= 0) {
            if (LOTRKeyHandler.keyBindingAlignmentCycleLeft.getIsKeyPressed()) {
                int i = factionList.indexOf(currentFaction);
                i = IntMath.mod(--i, factionList.size());
                currentFaction = factionList.get(i);
                usedAlignmentKeys = true;
            }
            if (LOTRKeyHandler.keyBindingAlignmentCycleRight.getIsKeyPressed()) {
                int i = factionList.indexOf(currentFaction);
                i = IntMath.mod(++i, factionList.size());
                currentFaction = factionList.get(i);
                usedAlignmentKeys = true;
            }
            if (regionList != null && currentRegion != null) {
                if (LOTRKeyHandler.keyBindingAlignmentGroupPrev.getIsKeyPressed()) {
                    pd.setRegionLastViewedFaction(currentRegion, currentFaction);
                    lastViewedRegions.put(currentRegion, currentFaction);
                    int i = regionList.indexOf(currentRegion);
                    i = IntMath.mod(--i, regionList.size());
                    currentRegion = regionList.get(i);
                    factionList = currentRegion.factionList;
                    currentFaction = pd.getRegionLastViewedFaction(currentRegion);
                    usedAlignmentKeys = true;
                }
                if (LOTRKeyHandler.keyBindingAlignmentGroupNext.getIsKeyPressed()) {
                    pd.setRegionLastViewedFaction(currentRegion, currentFaction);
                    lastViewedRegions.put(currentRegion, currentFaction);
                    int i = regionList.indexOf(currentRegion);
                    i = IntMath.mod(++i, regionList.size());
                    currentRegion = regionList.get(i);
                    factionList = currentRegion.factionList;
                    currentFaction = pd.getRegionLastViewedFaction(currentRegion);
                    usedAlignmentKeys = true;
                }
            }
        }
        if (usedAlignmentKeys) {
            LOTRClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
            LOTRKeyHandler.alignmentChangeTick = 2;
        }
    }
    
    public static void update() {
        if (LOTRKeyHandler.alignmentChangeTick > 0) {
            --LOTRKeyHandler.alignmentChangeTick;
        }
    }
    
    static {
        LOTRKeyHandler.keyBindingMenu = new KeyBinding("Menu", 38, "LOTR");
        LOTRKeyHandler.keyBindingMapTeleport = new KeyBinding("Map Teleport", 50, "LOTR");
        LOTRKeyHandler.keyBindingFastTravel = new KeyBinding("Fast Travel", 33, "LOTR");
        LOTRKeyHandler.keyBindingAlignmentCycleLeft = new KeyBinding("Alignment Cycle Left", 203, "LOTR");
        LOTRKeyHandler.keyBindingAlignmentCycleRight = new KeyBinding("Alignment Cycle Right", 205, "LOTR");
        LOTRKeyHandler.keyBindingAlignmentGroupPrev = new KeyBinding("Alignment Group Prev", 200, "LOTR");
        LOTRKeyHandler.keyBindingAlignmentGroupNext = new KeyBinding("Alignment Group Next", 208, "LOTR");
        LOTRKeyHandler.mc = Minecraft.getMinecraft();
    }
}
