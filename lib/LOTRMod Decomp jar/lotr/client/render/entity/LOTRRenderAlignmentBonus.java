// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import net.minecraft.client.gui.FontRenderer;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.util.MathHelper;
import lotr.common.fac.LOTRAlignmentValues;
import java.util.Iterator;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import lotr.common.fac.LOTRFaction;
import lotr.client.fx.LOTREntityAlignmentBonus;
import lotr.common.LOTRLevelData;
import net.minecraft.client.Minecraft;
import lotr.client.LOTRClientProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderAlignmentBonus extends Render
{
    public LOTRRenderAlignmentBonus() {
        super.shadowSize = 0.0f;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRClientProxy.alignmentTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
        final LOTRFaction viewingFaction = playerData.getViewingFaction();
        final LOTREntityAlignmentBonus alignmentBonus = (LOTREntityAlignmentBonus)entity;
        final LOTRFaction mainFaction = alignmentBonus.mainFaction;
        final LOTRAlignmentBonusMap factionBonusMap = alignmentBonus.factionBonusMap;
        LOTRFaction renderFaction = null;
        boolean showConquest = false;
        if (alignmentBonus.conquestBonus > 0.0f && playerData.isPledgedTo(viewingFaction)) {
            renderFaction = viewingFaction;
            showConquest = true;
        }
        else if (alignmentBonus.conquestBonus < 0.0f && (viewingFaction == mainFaction || playerData.isPledgedTo(viewingFaction))) {
            renderFaction = viewingFaction;
            showConquest = true;
        }
        else if (!factionBonusMap.isEmpty()) {
            if (factionBonusMap.containsKey(viewingFaction)) {
                renderFaction = viewingFaction;
            }
            else if (factionBonusMap.size() == 1 && mainFaction.isPlayableAlignmentFaction()) {
                renderFaction = mainFaction;
            }
            else if (mainFaction.isPlayableAlignmentFaction() && alignmentBonus.prevMainAlignment >= 0.0f && ((HashMap<K, Float>)factionBonusMap).get(mainFaction) < 0.0f) {
                renderFaction = mainFaction;
            }
            else {
                for (final LOTRFaction faction : ((HashMap<LOTRFaction, V>)factionBonusMap).keySet()) {
                    if (faction.isPlayableAlignmentFaction()) {
                        final float bonus = ((HashMap<K, Float>)factionBonusMap).get(faction);
                        if (bonus <= 0.0f) {
                            continue;
                        }
                        final float alignment = playerData.getAlignment(faction);
                        if (renderFaction != null && alignment <= playerData.getAlignment(renderFaction)) {
                            continue;
                        }
                        renderFaction = faction;
                    }
                }
                if (renderFaction == null) {
                    if (mainFaction.isPlayableAlignmentFaction() && ((HashMap<K, Float>)factionBonusMap).get(mainFaction) < 0.0f) {
                        renderFaction = mainFaction;
                    }
                    else {
                        for (final LOTRFaction faction : ((HashMap<LOTRFaction, V>)factionBonusMap).keySet()) {
                            if (faction.isPlayableAlignmentFaction()) {
                                final float bonus = ((HashMap<K, Float>)factionBonusMap).get(faction);
                                if (bonus >= 0.0f) {
                                    continue;
                                }
                                final float alignment = playerData.getAlignment(faction);
                                if (renderFaction != null && alignment <= playerData.getAlignment(renderFaction)) {
                                    continue;
                                }
                                renderFaction = faction;
                            }
                        }
                    }
                }
            }
        }
        final float renderBonus = factionBonusMap.containsKey(renderFaction) ? ((HashMap<K, Float>)factionBonusMap).get(renderFaction) : 0.0f;
        if (renderFaction != null && (renderBonus != 0.0f || showConquest)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1, (float)d2);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-super.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(super.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-0.025f, -0.025f, 0.025f);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            final int age = alignmentBonus.particleAge;
            final float alpha = (age < 60) ? 1.0f : ((80 - age) / 20.0f);
            this.renderBonusText(alignmentBonus, playerData, viewingFaction, renderFaction, !factionBonusMap.isEmpty(), renderBonus, showConquest, alpha);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    private void renderBonusText(final LOTREntityAlignmentBonus alignmentBonus, final LOTRPlayerData playerData, final LOTRFaction viewingFaction, final LOTRFaction renderFaction, final boolean showAlign, final float align, final boolean showConquest, final float alpha) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FontRenderer fr = mc.fontRenderer;
        String strAlign = LOTRAlignmentValues.formatAlignForDisplay(align);
        final String name = alignmentBonus.name;
        final float conq = alignmentBonus.conquestBonus;
        GL11.glPushMatrix();
        final boolean isViewingFaction = renderFaction == viewingFaction;
        if (!isViewingFaction) {
            final float scale = 0.5f;
            GL11.glScalef(scale, scale, 1.0f);
            strAlign = strAlign + " (" + renderFaction.factionName() + "...)";
        }
        int x = -MathHelper.floor_double((fr.getStringWidth(strAlign) + 18) / 2.0);
        int y = -12;
        if (showAlign) {
            this.bindEntityTexture((Entity)alignmentBonus);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            LOTRTickHandlerClient.drawTexturedModalRect(x, y - 5, 0, 36, 16, 16);
            LOTRTickHandlerClient.drawAlignmentText(fr, x + 18, y, strAlign, alpha);
            y += 14;
            LOTRTickHandlerClient.drawAlignmentText(fr, -MathHelper.floor_double(fr.getStringWidth(name) / 2.0), y, name, alpha);
        }
        if (showConquest && conq != 0.0f) {
            final boolean negative = conq < 0.0f;
            final String strConq = LOTRAlignmentValues.formatConqForDisplay(conq, true);
            x = -MathHelper.floor_double((fr.getStringWidth(strConq) + 18) / 2.0);
            if (showAlign) {
                y += 16;
            }
            this.bindEntityTexture((Entity)alignmentBonus);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            LOTRTickHandlerClient.drawTexturedModalRect(x, y - 5, negative ? 16 : 0, 228, 16, 16);
            LOTRTickHandlerClient.drawConquestText(fr, x + 18, y, strConq, negative, alpha);
        }
        GL11.glPopMatrix();
    }
}
