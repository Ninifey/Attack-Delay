// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import lotr.common.LOTRShields;
import lotr.client.render.LOTRRenderShield;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.util.StringUtils;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Items;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.client.model.LOTRArmorModels;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelBiped;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;

public abstract class LOTRRenderBiped extends RenderBiped
{
    public static final float PLAYER_SCALE = 0.9375f;
    private ModelBiped capeModel;
    public ModelBiped npcRenderPassModel;
    
    public LOTRRenderBiped(final ModelBiped model, final float f) {
        super(model, f);
        this.capeModel = new LOTRModelBiped();
    }
    
    protected void func_82421_b() {
        super.field_82423_g = new LOTRModelBiped(1.0f);
        super.field_82425_h = new LOTRModelBiped(0.5f);
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        return super.getEntityTexture(entity);
    }
    
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && entity instanceof LOTREntityNPC) {
            final LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (npc.hiredNPCInfo.getHiringPlayer() == ((Render)this).renderManager.livingPlayer) {
                LOTRNPCRendering.renderHiredIcon((EntityLivingBase)npc, d, d1 + 0.5, d2);
                LOTRNPCRendering.renderNPCHealthBar((EntityLivingBase)npc, d, d1 + 0.5, d2);
            }
            LOTRNPCRendering.renderQuestBook(npc, d, d1, d2);
            LOTRNPCRendering.renderQuestOffer(npc, d, d1, d2);
        }
    }
    
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final ItemStack armor = entity.getEquipmentInSlot(3 - pass + 1);
        final int specialArmorResult = LOTRArmorModels.INSTANCE.getEntityArmorModel((RendererLivingEntity)this, super.modelBipedMain, (EntityLivingBase)entity, armor, pass);
        if (specialArmorResult > 0) {
            return specialArmorResult;
        }
        final int result = super.shouldRenderPass(entity, pass, f);
        return result;
    }
    
    public void setRenderPassModel(final ModelBase model) {
        super.setRenderPassModel(model);
        if (model instanceof ModelBiped) {
            this.npcRenderPassModel = (ModelBiped)model;
        }
    }
    
    public void func_82408_c(final EntityLiving entity, final int pass, final float f) {
        super.func_82408_c(entity, pass, f);
    }
    
    protected void func_82420_a(final EntityLiving entity, final ItemStack itemstack) {
        LOTRArmorModels.INSTANCE.setupModelForRender(super.modelBipedMain, super.modelBipedMain, (EntityLivingBase)entity);
        LOTRArmorModels.INSTANCE.setupModelForRender(super.field_82425_h, super.modelBipedMain, (EntityLivingBase)entity);
        LOTRArmorModels.INSTANCE.setupModelForRender(super.field_82423_g, super.modelBipedMain, (EntityLivingBase)entity);
        if (this.npcRenderPassModel != null) {
            LOTRArmorModels.INSTANCE.setupModelForRender(this.npcRenderPassModel, super.modelBipedMain, (EntityLivingBase)entity);
        }
    }
    
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final ItemStack headItem = entity.getEquipmentInSlot(4);
        if (headItem != null) {
            GL11.glPushMatrix();
            super.modelBipedMain.bipedHead.postRender(0.0625f);
            final IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(headItem, IItemRenderer.ItemRenderType.EQUIPPED);
            final boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, headItem, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (headItem.getItem() instanceof ItemBlock) {
                if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(headItem.getItem()).getRenderType())) {
                    final float f2 = 0.625f;
                    GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glScalef(f2, -f2, -f2);
                }
                ((Render)this).renderManager.itemRenderer.renderItem(entity, headItem, 0);
            }
            else if (headItem.getItem() == Items.skull) {
                final float f2 = 1.0625f;
                GL11.glScalef(f2, -f2, -f2);
                GameProfile gameprofile = null;
                if (headItem.hasTagCompound()) {
                    final NBTTagCompound nbttagcompound = headItem.getTagCompound();
                    if (nbttagcompound.func_150297_b("SkullOwner", (int)new NBTTagCompound().getId())) {
                        gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
                    }
                    else if (nbttagcompound.func_150297_b("SkullOwner", (int)new NBTTagString().getId()) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner"))) {
                        gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
                    }
                }
                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, headItem.getItemDamage(), gameprofile);
            }
            GL11.glPopMatrix();
        }
        final ItemStack heldItem = entity.getHeldItem();
        if (heldItem != null) {
            GL11.glPushMatrix();
            if (((RendererLivingEntity)this).mainModel.isChild) {
                final float f3 = 0.5f;
                GL11.glTranslatef(0.0f, 0.625f, 0.0f);
                GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
                GL11.glScalef(f3, f3, f3);
            }
            super.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            final IItemRenderer customRenderer2 = MinecraftForgeClient.getItemRenderer(heldItem, IItemRenderer.ItemRenderType.EQUIPPED);
            final boolean is3D2 = customRenderer2 != null && customRenderer2.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, heldItem, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (heldItem.getItem() instanceof ItemBlock && (is3D2 || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(heldItem.getItem()).getRenderType()))) {
                float f4 = 0.5f;
                GL11.glTranslatef(0.0f, this.getHeldItemYTranslation(), -0.3125f);
                f4 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-f4, -f4, f4);
            }
            else if (heldItem.getItem() == Items.bow) {
                final float f4 = 0.625f;
                GL11.glTranslatef(0.0f, this.getHeldItemYTranslation(), 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(f4, -f4, f4);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (heldItem.getItem().isFull3D()) {
                final float f4 = 0.625f;
                if (heldItem.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -this.getHeldItemYTranslation(), 0.0f);
                }
                GL11.glTranslatef(0.0f, this.getHeldItemYTranslation(), 0.0f);
                GL11.glScalef(f4, -f4, f4);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float f4 = 0.375f;
                GL11.glTranslatef(0.25f, this.getHeldItemYTranslation(), -0.1875f);
                GL11.glScalef(f4, f4, f4);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItem, 0);
            if (heldItem.getItem().requiresMultipleRenderPasses()) {
                for (int x = 1; x < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); ++x) {
                    ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItem, x);
                }
            }
            GL11.glPopMatrix();
        }
        final ItemStack heldItemLeft = ((LOTREntityNPC)entity).getHeldItemLeft();
        if (heldItemLeft != null) {
            GL11.glPushMatrix();
            if (((RendererLivingEntity)this).mainModel.isChild) {
                final float f2 = 0.5f;
                GL11.glTranslatef(0.0f, 0.625f, 0.0f);
                GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
                GL11.glScalef(f2, f2, f2);
            }
            super.modelBipedMain.bipedLeftArm.postRender(0.0625f);
            GL11.glTranslatef(0.0625f, 0.4375f, 0.0625f);
            final IItemRenderer customRenderer3 = MinecraftForgeClient.getItemRenderer(heldItemLeft, IItemRenderer.ItemRenderType.EQUIPPED);
            final boolean is3D3 = customRenderer3 != null && customRenderer3.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, heldItemLeft, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (heldItemLeft.getItem() instanceof ItemBlock && (is3D3 || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(heldItemLeft.getItem()).getRenderType()))) {
                float f5 = 0.5f;
                GL11.glTranslatef(0.0f, this.getHeldItemYTranslation(), -0.3125f);
                f5 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-f5, -f5, f5);
            }
            else if (heldItemLeft.getItem().isFull3D()) {
                final float f5 = 0.625f;
                if (heldItemLeft.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -this.getHeldItemYTranslation(), 0.0f);
                }
                GL11.glTranslatef(0.0f, this.getHeldItemYTranslation(), 0.0f);
                GL11.glScalef(f5, -f5, f5);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float f5 = 0.3175f;
                GL11.glTranslatef(0.0f, this.getHeldItemYTranslation(), 0.0f);
                GL11.glScalef(f5, -f5, f5);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItemLeft, 0);
            if (heldItemLeft.getItem().requiresMultipleRenderPasses()) {
                for (int i = 1; i < heldItemLeft.getItem().getRenderPasses(heldItemLeft.getItemDamage()); ++i) {
                    ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItemLeft, i);
                }
            }
            GL11.glPopMatrix();
        }
        this.renderNPCCape((LOTREntityNPC)entity);
        this.renderNPCShield((LOTREntityNPC)entity);
    }
    
    protected void renderNPCCape(final LOTREntityNPC entity) {
        final ResourceLocation capeTexture = entity.npcCape;
        if (capeTexture != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.125f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
            this.bindTexture(capeTexture);
            this.capeModel.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
    }
    
    protected void renderNPCShield(final LOTREntityNPC entity) {
        final LOTRShields shield = entity.npcShield;
        if (shield != null) {
            LOTRRenderShield.renderShield(shield, (EntityLivingBase)entity, super.modelBipedMain);
        }
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float f2 = 0.9375f;
        GL11.glScalef(f2, f2, f2);
    }
    
    public float getHeldItemYTranslation() {
        return 0.1875f;
    }
}
