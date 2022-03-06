// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemSling;
import net.minecraft.item.ItemSword;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.item.EnumAction;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemArmor;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import lotr.common.LOTRMod;
import net.minecraftforge.common.MinecraftForge;
import java.util.HashMap;
import net.minecraft.item.Item;
import net.minecraft.client.model.ModelBiped;
import java.util.Map;

public class LOTRArmorModels
{
    public static LOTRArmorModels INSTANCE;
    private Map<ModelBiped, Map<Item, ModelBiped>> specialArmorModels;
    
    public static void setupArmorModels() {
        LOTRArmorModels.INSTANCE = new LOTRArmorModels();
    }
    
    private LOTRArmorModels() {
        this.specialArmorModels = new HashMap<ModelBiped, Map<Item, ModelBiped>>();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    private Map<Item, ModelBiped> getSpecialModels(final ModelBiped key) {
        Map<Item, ModelBiped> map = this.specialArmorModels.get(key);
        if (map == null) {
            map = new HashMap<Item, ModelBiped>();
            map.put(LOTRMod.leatherHat, new LOTRModelLeatherHat());
            map.put(LOTRMod.helmetGondor, new LOTRModelGondorHelmet(1.0f));
            map.put(LOTRMod.helmetElven, new LOTRModelGaladhrimHelmet(1.0f));
            map.put(LOTRMod.helmetGondorWinged, new LOTRModelWingedHelmet(1.0f));
            map.put(LOTRMod.helmetMorgul, new LOTRModelMorgulHelmet(1.0f));
            map.put(LOTRMod.helmetGemsbok, new LOTRModelGemsbokHelmet(1.0f));
            map.put(LOTRMod.helmetHighElven, new LOTRModelHighElvenHelmet(1.0f));
            map.put(LOTRMod.helmetBlackUruk, new LOTRModelBlackUrukHelmet(1.0f));
            map.put(LOTRMod.helmetUruk, new LOTRModelUrukHelmet(1.0f));
            map.put(LOTRMod.helmetNearHaradWarlord, new LOTRModelNearHaradWarlordHelmet(1.0f));
            map.put(LOTRMod.helmetDolAmroth, new LOTRModelSwanHelmet(1.0f));
            map.put(LOTRMod.bodyDolAmroth, new LOTRModelSwanChestplate(1.0f));
            map.put(LOTRMod.helmetMoredainLion, new LOTRModelMoredainLionHelmet(1.0f));
            map.put(LOTRMod.helmetHaradRobes, new LOTRModelHaradTurban());
            map.put(LOTRMod.bodyHaradRobes, new LOTRModelHaradRobes(1.0f));
            map.put(LOTRMod.legsHaradRobes, new LOTRModelHaradRobes(0.5f));
            map.put(LOTRMod.bootsHaradRobes, new LOTRModelHaradRobes(1.0f));
            map.put(LOTRMod.helmetGondolin, new LOTRModelGondolinHelmet(1.0f));
            map.put(LOTRMod.helmetRohanMarshal, new LOTRModelRohanMarshalHelmet(1.0f));
            map.put(LOTRMod.helmetTauredainChieftain, new LOTRModelTauredainChieftainHelmet(1.0f));
            map.put(LOTRMod.helmetTauredainGold, new LOTRModelTauredainGoldHelmet(1.0f));
            map.put(LOTRMod.helmetGundabadUruk, new LOTRModelGundabadUrukHelmet(1.0f));
            map.put(LOTRMod.helmetUrukBerserker, new LOTRModelUrukHelmet(1.0f));
            map.put(LOTRMod.helmetDorwinionElf, new LOTRModelDorwinionElfHelmet(1.0f));
            map.put(LOTRMod.partyHat, new LOTRModelPartyHat(0.6f));
            map.put(LOTRMod.helmetArnor, new LOTRModelArnorHelmet(1.0f));
            map.put(LOTRMod.helmetRhunGold, new LOTRModelEasterlingHelmet(1.0f, false));
            map.put(LOTRMod.helmetRhunWarlord, new LOTRModelEasterlingHelmet(1.0f, true));
            map.put(LOTRMod.helmetRivendell, new LOTRModelHighElvenHelmet(1.0f));
            map.put(LOTRMod.bodyGulfHarad, new LOTRModelGulfChestplate(1.0f));
            map.put(LOTRMod.helmetUmbar, new LOTRModelUmbarHelmet(1.0f));
            map.put(LOTRMod.helmetHarnedor, new LOTRModelHarnedorHelmet(1.0f));
            map.put(LOTRMod.bodyHarnedor, new LOTRModelHarnedorChestplate(1.0f));
            map.put(LOTRMod.helmetBlackNumenorean, new LOTRModelBlackNumenoreanHelmet(1.0f));
            map.put(LOTRMod.plate, new LOTRModelHeadPlate());
            map.put(LOTRMod.woodPlate, new LOTRModelHeadPlate());
            map.put(LOTRMod.ceramicPlate, new LOTRModelHeadPlate());
            for (final ModelBiped armorModel : map.values()) {
                this.copyModelRotations(armorModel, key);
            }
            this.specialArmorModels.put(key, map);
        }
        return map;
    }
    
    public ModelBiped getSpecialArmorModel(final ItemStack itemstack, final int slot, final EntityLivingBase entity, final ModelBiped mainModel) {
        if (itemstack == null) {
            return null;
        }
        final Item item = itemstack.getItem();
        final ModelBiped model = this.getSpecialModels(mainModel).get(item);
        if (model == null) {
            return null;
        }
        if (model instanceof LOTRModelLeatherHat) {
            ((LOTRModelLeatherHat)model).setHatItem(itemstack);
        }
        if (model instanceof LOTRModelHaradRobes) {
            ((LOTRModelHaradRobes)model).setRobeItem(itemstack);
        }
        if (model instanceof LOTRModelPartyHat) {
            ((LOTRModelPartyHat)model).setHatItem(itemstack);
        }
        if (model instanceof LOTRModelHeadPlate) {
            ((LOTRModelHeadPlate)model).setPlateItem(itemstack);
        }
        this.copyModelRotations(model, mainModel);
        this.setupArmorForSlot(model, slot);
        return model;
    }
    
    @SubscribeEvent
    public void getPlayerArmorModel(final RenderPlayerEvent.SetArmorModel event) {
        final RenderPlayer renderer = event.renderer;
        final ModelBiped mainModel = renderer.modelBipedMain;
        final EntityPlayer entityplayer = event.entityPlayer;
        final ItemStack armor = event.stack;
        final int slot = event.slot;
        final int result = this.getEntityArmorModel((RendererLivingEntity)renderer, mainModel, (EntityLivingBase)entityplayer, armor, 3 - slot);
        if (result > 0) {
            event.result = result;
        }
    }
    
    public int getEntityArmorModel(final RendererLivingEntity renderer, final ModelBiped mainModel, final EntityLivingBase entity, final ItemStack armor, final int slot) {
        final ModelBiped armorModel = this.getSpecialArmorModel(armor, slot, entity, mainModel);
        if (armorModel == null) {
            return 0;
        }
        final Item armorItem = (armor == null) ? null : armor.getItem();
        if (armorItem instanceof ItemArmor) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(RenderBiped.getArmorResource((Entity)entity, armor, slot, (String)null));
        }
        renderer.setRenderPassModel((ModelBase)armorModel);
        this.setupModelForRender(armorModel, mainModel, entity);
        if (armorItem instanceof ItemArmor) {
            final int color = ((ItemArmor)armorItem).getColor(armor);
            if (color != -1) {
                final float r = (color >> 16 & 0xFF) / 255.0f;
                final float g = (color >> 8 & 0xFF) / 255.0f;
                final float b = (color & 0xFF) / 255.0f;
                GL11.glColor3f(r, g, b);
                if (armor.isItemEnchanted()) {
                    return 31;
                }
                return 16;
            }
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        if (armor.isItemEnchanted()) {
            return 15;
        }
        return 1;
    }
    
    @SubscribeEvent
    public void preRenderEntity(final RenderLivingEvent.Pre event) {
        final EntityLivingBase entity = event.entity;
        final RendererLivingEntity renderer = event.renderer;
        if (entity instanceof EntityPlayer && renderer instanceof RenderPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            final RenderPlayer renderplayer = (RenderPlayer)renderer;
            final ModelBiped mainModel = renderplayer.modelBipedMain;
            final ModelBiped armorModel1 = renderplayer.modelArmorChestplate;
            final ModelBiped armorModel2 = renderplayer.modelArmor;
            this.setupModelForRender(mainModel, mainModel, (EntityLivingBase)entityplayer);
            this.setupModelForRender(armorModel1, mainModel, (EntityLivingBase)entityplayer);
            this.setupModelForRender(armorModel2, mainModel, (EntityLivingBase)entityplayer);
        }
    }
    
    public void setupModelForRender(final ModelBiped model, final ModelBiped mainModel, final EntityLivingBase entity) {
        if (mainModel != null) {
            ((ModelBase)model).onGround = ((ModelBase)mainModel).onGround;
            ((ModelBase)model).isRiding = ((ModelBase)mainModel).isRiding;
            ((ModelBase)model).isChild = ((ModelBase)mainModel).isChild;
            model.isSneak = mainModel.isSneak;
        }
        else {
            ((ModelBase)model).onGround = 0.0f;
            ((ModelBase)model).isRiding = false;
            ((ModelBase)model).isChild = false;
            model.isSneak = false;
        }
        if (entity instanceof LOTREntityNPC) {
            model.bipedHeadwear.showModel = ((LOTREntityNPC)entity).shouldRenderNPCHair();
        }
        if (entity instanceof EntityPlayer) {
            final ItemStack heldRight = (entity == null) ? null : entity.getHeldItem();
            model.aimedBow = mainModel.aimedBow;
            this.setupHeldItem(model, entity, heldRight, true);
        }
        else {
            final ItemStack heldRight = (entity == null) ? null : entity.getHeldItem();
            final ItemStack heldLeft = (entity == null) ? null : ((entity instanceof LOTREntityNPC) ? ((LOTREntityNPC)entity).getHeldItemLeft() : null);
            this.setupHeldItem(model, entity, heldRight, true);
            this.setupHeldItem(model, entity, heldLeft, false);
        }
    }
    
    private void setupHeldItem(final ModelBiped model, final EntityLivingBase entity, final ItemStack itemstack, final boolean rightArm) {
        int value = 0;
        boolean aimBow = false;
        if (itemstack != null) {
            value = 1;
            final Item item = itemstack.getItem();
            boolean isRanged = false;
            if (itemstack.getItemUseAction() == EnumAction.bow) {
                if (item instanceof LOTRItemSpear) {
                    isRanged = (entity instanceof EntityPlayer);
                }
                else {
                    isRanged = !(item instanceof ItemSword);
                }
            }
            if (item instanceof LOTRItemSling) {
                isRanged = true;
            }
            if (isRanged) {
                boolean aiming = model.aimedBow;
                if (entity instanceof EntityPlayer && LOTRItemCrossbow.isLoaded(itemstack)) {
                    aiming = true;
                }
                if (entity instanceof LOTREntityNPC) {
                    aiming = ((LOTREntityNPC)entity).clientCombatStance;
                }
                if (aiming) {
                    value = 3;
                    aimBow = true;
                }
            }
            if (item instanceof LOTRItemBanner) {
                value = 3;
            }
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)entity;
                if (entityplayer.getItemInUseCount() > 0 && itemstack.getItemUseAction() == EnumAction.block) {
                    value = 3;
                }
            }
            if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).clientIsEating) {
                value = 3;
            }
        }
        if (rightArm) {
            model.heldItemRight = value;
            model.aimedBow = aimBow;
        }
        else {
            model.heldItemLeft = value;
        }
    }
    
    public void copyModelRotations(final ModelBiped target, final ModelBiped src) {
        this.copyBoxRotations(target.bipedHead, src.bipedHead);
        this.copyBoxRotations(target.bipedHeadwear, src.bipedHeadwear);
        this.copyBoxRotations(target.bipedBody, src.bipedBody);
        this.copyBoxRotations(target.bipedRightArm, src.bipedRightArm);
        this.copyBoxRotations(target.bipedLeftArm, src.bipedLeftArm);
        this.copyBoxRotations(target.bipedRightLeg, src.bipedRightLeg);
        this.copyBoxRotations(target.bipedLeftLeg, src.bipedLeftLeg);
    }
    
    public void copyBoxRotations(final ModelRenderer target, final ModelRenderer src) {
        target.rotationPointX = src.rotationPointX;
        target.rotationPointY = src.rotationPointY;
        target.rotationPointZ = src.rotationPointZ;
        target.rotateAngleX = src.rotateAngleX;
        target.rotateAngleY = src.rotateAngleY;
        target.rotateAngleZ = src.rotateAngleZ;
    }
    
    public void setupArmorForSlot(final ModelBiped model, final int slot) {
        model.bipedHead.showModel = (slot == 0);
        model.bipedHeadwear.showModel = (slot == 0);
        model.bipedBody.showModel = (slot == 1 || slot == 2);
        model.bipedRightArm.showModel = (slot == 1);
        model.bipedLeftArm.showModel = (slot == 1);
        model.bipedRightLeg.showModel = (slot == 2 || slot == 3);
        model.bipedLeftLeg.showModel = (slot == 2 || slot == 3);
    }
}
