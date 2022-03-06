// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.model.ModelBase;
import lotr.common.network.LOTRPacketMiniquestOffer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLiving;
import lotr.client.render.entity.LOTRRenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTRSpeech;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiButton;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiMiniquestOffer extends LOTRGuiScreenBase
{
    private static ResourceLocation guiTexture;
    private static RenderItem renderItem;
    private LOTRMiniQuest theMiniQuest;
    private LOTREntityNPC theNPC;
    private String description;
    private Random rand;
    private int openTick;
    public int xSize;
    public int ySize;
    private int guiLeft;
    private int guiTop;
    private int descriptionX;
    private int descriptionY;
    private int descriptionWidth;
    private int npcX;
    private int npcY;
    private GuiButton buttonAccept;
    private GuiButton buttonDecline;
    private boolean sentClosePacket;
    private NPCAction npcAction;
    private int actionTick;
    private int actionTime;
    private float actionSlow;
    private float headYaw;
    private float prevHeadYaw;
    private float headPitch;
    private float prevHeadPitch;
    
    public LOTRGuiMiniquestOffer(final LOTRMiniQuest quest, final LOTREntityNPC npc) {
        this.xSize = 256;
        this.ySize = 200;
        this.descriptionX = 85;
        this.descriptionY = 30;
        this.descriptionWidth = 160;
        this.npcX = 46;
        this.npcY = 90;
        this.sentClosePacket = false;
        this.actionTick = 0;
        this.theMiniQuest = quest;
        this.theNPC = npc;
        this.rand = this.theNPC.getRNG();
        this.openTick = 0;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(this.buttonAccept = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 20 - 80, this.guiTop + this.ySize - 30, 80, 20, StatCollector.translateToLocal("lotr.gui.miniquestOffer.accept")));
        super.buttonList.add(this.buttonDecline = new LOTRGuiButtonRedBook(1, this.guiLeft + this.xSize / 2 + 20, this.guiTop + this.ySize - 30, 80, 20, StatCollector.translateToLocal("lotr.gui.miniquestOffer.decline")));
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.theNPC.isEntityAlive() || super.mc.thePlayer.getDistanceToEntity((Entity)this.theNPC) > 8.0f) {
            super.mc.thePlayer.closeScreen();
        }
        this.prevHeadYaw = this.headYaw;
        this.prevHeadPitch = this.headPitch;
        if (this.npcAction == null) {
            if (this.openTick < 100) {
                this.npcAction = NPCAction.TALKING;
                this.actionTime = 100;
                this.actionSlow = 1.0f;
            }
            else if (this.rand.nextInt(200) == 0) {
                this.npcAction = NPCAction.getRandomAction(this.rand);
                if (this.npcAction == NPCAction.TALKING) {
                    this.actionTime = 40 + this.rand.nextInt(60);
                    this.actionSlow = 1.0f;
                }
                else if (this.npcAction == NPCAction.LOOKING) {
                    this.actionTime = 60 + this.rand.nextInt(60);
                    this.actionSlow = 1.0f;
                }
                else if (this.npcAction == NPCAction.SHAKING) {
                    this.actionTime = 100 + this.rand.nextInt(60);
                    this.actionSlow = 1.0f;
                }
                else if (this.npcAction == NPCAction.LOOKING_UP) {
                    this.actionTime = 30 + this.rand.nextInt(50);
                    this.actionSlow = 1.0f;
                }
            }
        }
        else {
            ++this.actionTick;
        }
        if (this.npcAction != null) {
            if (this.actionTick >= this.actionTime) {
                this.npcAction = null;
                this.actionTick = 0;
                this.actionTime = 0;
            }
            else if (this.npcAction == NPCAction.TALKING) {
                if (this.actionTick % 20 == 0) {
                    this.actionSlow = 0.7f + this.rand.nextFloat() * 1.5f;
                }
                final float slow = this.actionSlow * 2.0f;
                this.headYaw = MathHelper.sin(this.actionTick / slow) * (float)Math.toRadians(10.0);
                this.headPitch = (MathHelper.sin(this.actionTick / slow * 2.0f) + 1.0f) / 2.0f * (float)Math.toRadians(-20.0);
            }
            else if (this.npcAction == NPCAction.SHAKING) {
                this.actionSlow += 0.01f;
                this.headYaw = MathHelper.sin(this.actionTick / this.actionSlow) * (float)Math.toRadians(30.0);
                this.headPitch += (float)Math.toRadians(0.4);
            }
            else if (this.npcAction == NPCAction.LOOKING) {
                final float slow = this.actionSlow * 16.0f;
                this.headYaw = MathHelper.sin(this.actionTick / slow) * (float)Math.toRadians(60.0);
                this.headPitch = (MathHelper.sin(this.actionTick / slow * 2.0f) + 1.0f) / 2.0f * (float)Math.toRadians(-15.0);
            }
            else if (this.npcAction == NPCAction.LOOKING_UP) {
                this.headYaw = 0.0f;
                this.headPitch = (float)Math.toRadians(-20.0);
            }
        }
        else {
            this.headYaw = 0.0f;
            this.headPitch = MathHelper.sin(this.openTick * 0.07f) * (float)Math.toRadians(5.0);
        }
        ++this.openTick;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        if (this.description == null) {
            this.description = LOTRSpeech.formatSpeech(this.theMiniQuest.quoteStart, (EntityPlayer)super.mc.thePlayer, null, this.theMiniQuest.getObjectiveInSpeech());
        }
        this.drawDefaultBackground();
        super.mc.getTextureManager().bindTexture(LOTRGuiMiniquestOffer.guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final String name = this.theNPC.getNPCName();
        this.drawCenteredString(name, this.guiLeft + this.xSize / 2, this.guiTop + 8, 8019267);
        this.renderNPC(this.guiLeft + this.npcX, this.guiTop + this.npcY, (float)(this.guiLeft + this.npcX - i), (float)(this.guiTop + this.npcY - j), f);
        super.fontRendererObj.drawSplitString(this.description, this.guiLeft + this.descriptionX, this.guiTop + this.descriptionY, this.descriptionWidth, 8019267);
        final String objective = this.theMiniQuest.getQuestObjective();
        final int objWidth = super.fontRendererObj.getStringWidth(objective);
        final int objY = this.guiTop + this.ySize - 50;
        this.drawCenteredString(objective, this.guiLeft + this.xSize / 2, objY, 8019267);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int iconW = 16;
        final int iconB = 6;
        final int iconY = objY + super.fontRendererObj.FONT_HEIGHT / 2 - iconW / 2;
        LOTRGuiMiniquestOffer.renderItem.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.getTextureManager(), this.theMiniQuest.getQuestIcon(), this.guiLeft + this.xSize / 2 - objWidth / 2 - iconW - iconB, iconY);
        LOTRGuiMiniquestOffer.renderItem.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.getTextureManager(), this.theMiniQuest.getQuestIcon(), this.guiLeft + this.xSize / 2 + objWidth / 2 + iconB, iconY);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawScreen(i, j, f);
    }
    
    private void renderNPC(final int i, final int j, final float dx, final float dy, final float f) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float scale = 70.0f;
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)i, (float)j, 40.0f);
        GL11.glScalef(-scale, -scale, -scale);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)Math.atan(dx / 40.0f) * 20.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-(float)Math.atan(dy / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, ((Entity)this.theNPC).yOffset, 0.0f);
        RenderManager.instance.playerViewY = 180.0f;
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        final Render render = RenderManager.instance.getEntityRenderObject((Entity)this.theNPC);
        if (render instanceof LOTRRenderBiped) {
            final LOTRRenderBiped npcRenderer = (LOTRRenderBiped)render;
            ModelBiped model = npcRenderer.modelBipedMain;
            ((ModelBase)model).isChild = this.theNPC.isChild();
            super.mc.getTextureManager().bindTexture(npcRenderer.getEntityTexture((Entity)this.theNPC));
            GL11.glTranslatef(0.0f, -model.bipedHead.rotationPointY / 16.0f, 0.0f);
            float yaw = this.prevHeadYaw + (this.headYaw - this.prevHeadYaw) * f;
            float pitch = this.prevHeadPitch + (this.headPitch - this.prevHeadPitch) * f;
            yaw = (float)Math.toDegrees(yaw);
            pitch = (float)Math.toDegrees(pitch);
            GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            final ModelRenderer bipedHead = model.bipedHead;
            final ModelRenderer bipedHeadwear = model.bipedHeadwear;
            final float n = 0.0f;
            bipedHeadwear.rotateAngleX = n;
            bipedHead.rotateAngleX = n;
            final ModelRenderer bipedHead2 = model.bipedHead;
            final ModelRenderer bipedHeadwear2 = model.bipedHeadwear;
            final float n2 = 0.0f;
            bipedHeadwear2.rotateAngleY = n2;
            bipedHead2.rotateAngleY = n2;
            final ModelRenderer bipedHead3 = model.bipedHead;
            final ModelRenderer bipedHeadwear3 = model.bipedHeadwear;
            final float n3 = 0.0f;
            bipedHeadwear3.rotateAngleZ = n3;
            bipedHead3.rotateAngleZ = n3;
            model.bipedHead.render(0.0625f);
            model.bipedHeadwear.render(0.0625f);
            for (int pass = 0; pass < 4; ++pass) {
                final int shouldRenderPass = npcRenderer.shouldRenderPass((EntityLiving)this.theNPC, pass, 1.0f);
                if (shouldRenderPass > 0) {
                    model = npcRenderer.npcRenderPassModel;
                    ((ModelBase)model).isChild = this.theNPC.isChild();
                    final List modelParts = ((ModelBase)model).boxList;
                    final boolean[] prevShowModels = new boolean[modelParts.size()];
                    for (int l = 0; l < modelParts.size(); ++l) {
                        final ModelRenderer part = modelParts.get(l);
                        prevShowModels[l] = part.showModel;
                        boolean isHeadPart = false;
                        if (this.recursiveCheckForModel(model.bipedHead, part)) {
                            isHeadPart = true;
                        }
                        else if (this.recursiveCheckForModel(model.bipedHeadwear, part)) {
                            isHeadPart = true;
                        }
                        if (!isHeadPart) {
                            part.showModel = false;
                        }
                    }
                    model.render((Entity)this.theNPC, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                    if ((shouldRenderPass & 0xF0) == 0x10) {
                        npcRenderer.func_82408_c((EntityLiving)this.theNPC, pass, 1.0f);
                        model.render((Entity)this.theNPC, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                    }
                    for (int l = 0; l < modelParts.size(); ++l) {
                        final ModelRenderer part = modelParts.get(l);
                        part.showModel = prevShowModels[l];
                    }
                }
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    private boolean recursiveCheckForModel(final ModelRenderer base, final ModelRenderer match) {
        if (base == match) {
            return true;
        }
        if (base.childModels != null) {
            for (final Object obj : base.childModels) {
                final ModelRenderer part = (ModelRenderer)obj;
                if (this.recursiveCheckForModel(part, match)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            boolean close = false;
            if (button == this.buttonAccept) {
                LOTRPacketMiniquestOffer.sendClosePacket((EntityPlayer)super.mc.thePlayer, this.theNPC, true);
                close = true;
            }
            else if (button == this.buttonDecline) {
                LOTRPacketMiniquestOffer.sendClosePacket((EntityPlayer)super.mc.thePlayer, this.theNPC, false);
                close = true;
            }
            if (close) {
                this.sentClosePacket = true;
                super.mc.thePlayer.closeScreen();
            }
        }
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        if (!this.sentClosePacket) {
            LOTRPacketMiniquestOffer.sendClosePacket((EntityPlayer)super.mc.thePlayer, this.theNPC, false);
            this.sentClosePacket = true;
        }
    }
    
    static {
        LOTRGuiMiniquestOffer.guiTexture = new ResourceLocation("lotr:gui/quest/miniquest.png");
        LOTRGuiMiniquestOffer.renderItem = new RenderItem();
    }
    
    private enum NPCAction
    {
        TALKING(1.0f), 
        SHAKING(0.1f), 
        LOOKING(0.3f), 
        LOOKING_UP(0.4f);
        
        private static float totalWeight;
        private final float weight;
        
        private NPCAction(final float f) {
            this.weight = f;
        }
        
        public static NPCAction getRandomAction(final Random rand) {
            if (NPCAction.totalWeight <= 0.0f) {
                NPCAction.totalWeight = 0.0f;
                for (final NPCAction action : values()) {
                    NPCAction.totalWeight += action.weight;
                }
            }
            float f = rand.nextFloat();
            f *= NPCAction.totalWeight;
            NPCAction chosen = null;
            for (final NPCAction action2 : values()) {
                f -= action2.weight;
                if (f <= 0.0f) {
                    chosen = action2;
                    break;
                }
            }
            return chosen;
        }
        
        static {
            NPCAction.totalWeight = -1.0f;
        }
    }
}
