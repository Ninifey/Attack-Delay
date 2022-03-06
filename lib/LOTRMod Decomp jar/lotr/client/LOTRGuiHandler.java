// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryCraftResult;
import lotr.common.inventory.LOTRContainerCoinExchange;
import java.util.HashSet;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.IChatComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import java.util.Collection;
import com.google.common.collect.Lists;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRAchievement;
import lotr.client.gui.LOTRGuiAchievementHoverEvent;
import lotr.common.LOTRChatEvents;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.inventory.Slot;
import java.util.Map;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.common.item.LOTRItemCoin;
import java.util.HashMap;
import java.util.ArrayList;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import lotr.common.LOTRModInfo;
import lotr.common.LOTRMod;
import cpw.mods.fml.client.GuiModList;
import java.util.Iterator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiButton;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.client.gui.LOTRGuiButtonLock;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityClientPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountOpenInv;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.WorldProvider;
import net.minecraft.client.gui.GuiScreen;
import lotr.client.gui.LOTRGuiDownloadTerrain;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import lotr.client.gui.LOTRGuiMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import lotr.common.LOTRConfig;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import java.util.Set;
import net.minecraft.client.renderer.entity.RenderItem;

public class LOTRGuiHandler
{
    private static RenderItem itemRenderer;
    public static final Set<Class<? extends Container>> coinCount_excludedContainers;
    public static final Set<Class<? extends GuiContainer>> coinCount_excludedGUIs;
    public static final Set<Class<? extends IInventory>> coinCount_excludedInvTypes;
    private int descScrollIndex;
    
    public LOTRGuiHandler() {
        this.descScrollIndex = -1;
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onGuiOpen(final GuiOpenEvent event) {
        GuiScreen gui = event.gui;
        if (LOTRConfig.customMainMenu && gui != null && gui.getClass() == GuiMainMenu.class) {
            gui = (GuiScreen)new LOTRGuiMainMenu();
            event.gui = gui;
        }
        if (gui != null && gui.getClass() == GuiDownloadTerrain.class) {
            final Minecraft mc = Minecraft.getMinecraft();
            final WorldProvider provider = ((World)mc.theWorld).provider;
            if (provider instanceof LOTRWorldProvider) {
                gui = (GuiScreen)new LOTRGuiDownloadTerrain(mc.thePlayer.sendQueue);
                event.gui = gui;
            }
        }
    }
    
    @SubscribeEvent
    public void preInitGui(final GuiScreenEvent.InitGuiEvent.Pre event) {
        final GuiScreen gui = event.gui;
        final Minecraft mc = Minecraft.getMinecraft();
        final EntityClientPlayerMP entityplayer = mc.thePlayer;
        final World world = (World)mc.theWorld;
        if ((gui instanceof GuiInventory || gui instanceof GuiContainerCreative) && entityplayer != null && world != null && ((Entity)entityplayer).ridingEntity instanceof LOTREntityNPCRideable) {
            final LOTREntityNPCRideable mount = (LOTREntityNPCRideable)((Entity)entityplayer).ridingEntity;
            if (mount.getMountInventory() != null) {
                entityplayer.closeScreen();
                final LOTRPacketMountOpenInv packet = new LOTRPacketMountOpenInv();
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void postInitGui(final GuiScreenEvent.InitGuiEvent.Post event) {
        final GuiScreen gui = event.gui;
        final List buttons = event.buttonList;
        if (gui instanceof GuiOptions) {
            final GuiButton buttonDifficulty = this.getDifficultyButton((GuiOptions)gui, buttons);
            if (buttonDifficulty != null) {
                final GuiButton lock = new LOTRGuiButtonLock(1000000, buttonDifficulty.field_146128_h + buttonDifficulty.field_146120_f + 4, buttonDifficulty.field_146129_i);
                lock.enabled = !LOTRLevelData.isDifficultyLocked();
                buttons.add(lock);
                buttonDifficulty.enabled = !LOTRLevelData.isDifficultyLocked();
            }
        }
    }
    
    @SubscribeEvent
    public void postActionPerformed(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = event.gui;
        final List buttons = event.buttonList;
        final GuiButton button = event.button;
        if (gui instanceof GuiOptions && button instanceof LOTRGuiButtonLock && button.enabled && mc.isSingleplayer()) {
            LOTRLevelData.setSavedDifficulty(mc.gameSettings.difficulty);
            LOTRLevelData.setDifficultyLocked(true);
            button.enabled = false;
            final GuiButton buttonDifficulty = this.getDifficultyButton((GuiOptions)gui, buttons);
            if (buttonDifficulty != null) {
                buttonDifficulty.enabled = false;
            }
        }
    }
    
    private GuiButton getDifficultyButton(final GuiOptions gui, final List buttons) {
        for (final Object obj : buttons) {
            if (obj instanceof GuiOptionButton) {
                final GuiOptionButton button = (GuiOptionButton)obj;
                if (button.func_146136_c() == GameSettings.Options.DIFFICULTY) {
                    return (GuiButton)button;
                }
                continue;
            }
        }
        return null;
    }
    
    @SubscribeEvent
    public void preDrawScreen(final GuiScreenEvent.DrawScreenEvent.Pre event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final GuiScreen gui = event.gui;
        final int mouseX = event.mouseX;
        final int mouseY = event.mouseY;
        if (gui instanceof GuiModList) {
            final ModContainer mod = LOTRMod.getModContainer();
            final ModMetadata meta = mod.getMetadata();
            if (this.descScrollIndex == -1) {
                meta.description = LOTRModInfo.concatenateDescription(0);
                this.descScrollIndex = 0;
            }
            while (Mouse.next()) {
                final int dwheel = Mouse.getEventDWheel();
                if (dwheel != 0) {
                    final int scroll = Integer.signum(dwheel);
                    this.descScrollIndex -= scroll;
                    this.descScrollIndex = MathHelper.clamp_int(this.descScrollIndex, 0, LOTRModInfo.description.length - 1);
                    meta.description = LOTRModInfo.concatenateDescription(this.descScrollIndex);
                }
            }
        }
        if (gui instanceof GuiContainer && LOTRConfig.displayCoinCounts) {
            ((World)mc.theWorld).theProfiler.startSection("invCoinCount");
            final GuiContainer guiContainer = (GuiContainer)gui;
            final Container container = guiContainer.field_147002_h;
            if (!LOTRGuiHandler.coinCount_excludedContainers.contains(container.getClass()) && !LOTRGuiHandler.coinCount_excludedGUIs.contains(guiContainer.getClass())) {
                int guiLeft = -1;
                int guiTop = -1;
                int guiXSize = -1;
                final List<IInventory> differentInvs = new ArrayList<IInventory>();
                final Map<IInventory, Integer> invHighestY = new HashMap<IInventory, Integer>();
                for (int i = 0; i < container.inventorySlots.size(); ++i) {
                    final Slot slot = container.getSlot(i);
                    final IInventory inv = slot.inventory;
                    if (inv != null) {
                        if (!LOTRGuiHandler.coinCount_excludedInvTypes.contains(inv.getClass())) {
                            if (!differentInvs.contains(inv)) {
                                differentInvs.add(inv);
                            }
                            final int slotY = slot.yDisplayPosition;
                            if (!invHighestY.containsKey(inv)) {
                                invHighestY.put(inv, slotY);
                            }
                            else {
                                final int highestY = invHighestY.get(inv);
                                if (slotY < highestY) {
                                    invHighestY.put(inv, slotY);
                                }
                            }
                        }
                    }
                }
                for (final IInventory inv2 : differentInvs) {
                    final int coins = LOTRItemCoin.getContainerValue(inv2);
                    if (coins > 0) {
                        if (guiLeft == -1) {
                            guiLeft = LOTRReflectionClient.getGuiLeft(guiContainer);
                            guiTop = LOTRReflectionClient.getGuiTop(guiContainer);
                            guiXSize = LOTRReflectionClient.getGuiXSize(guiContainer);
                        }
                        final int x = gui.width / 2 + guiXSize / 2 + 8;
                        final int y = invHighestY.get(inv2) + guiTop;
                        final String sCoins = String.valueOf(coins);
                        final int border = 2;
                        final int rectX0 = x - border;
                        final int rectX2 = x + 16 + 2 + mc.fontRenderer.getStringWidth(sCoins) + border + 1;
                        final int rectY0 = y - border;
                        final int rectY2 = y + 16 + border;
                        final float a0 = 1.0f;
                        final float a2 = 0.1f;
                        GL11.glDisable(3553);
                        GL11.glDisable(3008);
                        GL11.glShadeModel(7425);
                        GL11.glPushMatrix();
                        GL11.glTranslatef(0.0f, 0.0f, -500.0f);
                        final Tessellator tessellator = Tessellator.instance;
                        tessellator.startDrawingQuads();
                        tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a2);
                        tessellator.addVertex((double)rectX2, (double)rectY0, 0.0);
                        tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a0);
                        tessellator.addVertex((double)rectX0, (double)rectY0, 0.0);
                        tessellator.addVertex((double)rectX0, (double)rectY2, 0.0);
                        tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a2);
                        tessellator.addVertex((double)rectX2, (double)rectY2, 0.0);
                        tessellator.draw();
                        GL11.glPopMatrix();
                        GL11.glShadeModel(7424);
                        GL11.glEnable(3008);
                        GL11.glEnable(3553);
                        GL11.glPushMatrix();
                        GL11.glTranslatef(0.0f, 0.0f, 500.0f);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        LOTRGuiHandler.itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(LOTRMod.silverCoin), x, y);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glDisable(2896);
                        mc.fontRenderer.drawString(sCoins, x + 16 + 2, y + (16 - mc.fontRenderer.FONT_HEIGHT + 2) / 2, 16777215);
                        GL11.glPopMatrix();
                        GL11.glDisable(2896);
                        GL11.glEnable(3008);
                        GL11.glEnable(3042);
                        GL11.glDisable(2884);
                    }
                }
                ((World)mc.theWorld).theProfiler.endSection();
            }
        }
    }
    
    @SubscribeEvent
    public void postDrawScreen(final GuiScreenEvent.DrawScreenEvent.Post event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        final GuiScreen gui = event.gui;
        final int mouseX = event.mouseX;
        final int mouseY = event.mouseY;
        if (gui instanceof GuiChat) {
            final IChatComponent component = mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());
            if (component != null && component.getChatStyle().getChatHoverEvent() != null) {
                final HoverEvent hoverevent = component.getChatStyle().getChatHoverEvent();
                if (hoverevent.getAction() == LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT) {
                    final LOTRGuiAchievementHoverEvent proxyGui = new LOTRGuiAchievementHoverEvent();
                    proxyGui.setWorldAndResolution(mc, gui.width, gui.height);
                    try {
                        final String unformattedText = hoverevent.getValue().getUnformattedText();
                        final int splitIndex = unformattedText.indexOf("$");
                        final String categoryName = unformattedText.substring(0, splitIndex);
                        final LOTRAchievement.Category category = LOTRAchievement.categoryForName(categoryName);
                        final int achievementID = Integer.parseInt(unformattedText.substring(splitIndex + 1));
                        final LOTRAchievement achievement = LOTRAchievement.achievementForCategoryAndID(category, achievementID);
                        final IChatComponent name = (IChatComponent)new ChatComponentTranslation("lotr.gui.achievements.hover.name", new Object[] { achievement.getAchievementChatComponent(entityplayer) });
                        final IChatComponent subtitle = (IChatComponent)new ChatComponentTranslation("lotr.gui.achievements.hover.subtitle", new Object[] { achievement.getDimension().getDimensionName(), category.getDisplayName() });
                        subtitle.getChatStyle().setItalic(Boolean.valueOf(true));
                        final String desc = achievement.getDescription(entityplayer);
                        final ArrayList list = Lists.newArrayList((Object[])new String[] { name.getFormattedText(), subtitle.getFormattedText() });
                        list.addAll(mc.fontRenderer.listFormattedStringToWidth(desc, 150));
                        proxyGui.func_146283_a(list, mouseX, mouseY);
                    }
                    catch (Exception e) {
                        proxyGui.func_146279_a(EnumChatFormatting.RED + "Invalid LOTRAchievement!", mouseX, mouseY);
                    }
                }
            }
        }
    }
    
    static {
        LOTRGuiHandler.itemRenderer = new RenderItem();
        coinCount_excludedContainers = new HashSet<Class<? extends Container>>();
        coinCount_excludedGUIs = new HashSet<Class<? extends GuiContainer>>();
        coinCount_excludedInvTypes = new HashSet<Class<? extends IInventory>>();
        LOTRGuiHandler.coinCount_excludedGUIs.add((Class<? extends GuiContainer>)GuiContainerCreative.class);
        LOTRGuiHandler.coinCount_excludedInvTypes.add((Class<? extends IInventory>)LOTRContainerCoinExchange.InventoryCoinExchangeSlot.class);
        LOTRGuiHandler.coinCount_excludedInvTypes.add((Class<? extends IInventory>)InventoryCraftResult.class);
    }
}
