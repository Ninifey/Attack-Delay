// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.common.LOTRMod;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelDwarf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarf extends LOTRRenderBiped
{
    private static LOTRRandomSkins dwarfSkinsMale;
    private static LOTRRandomSkins dwarfSkinsFemale;
    private static LOTRRandomSkins blueDwarfSkinsMale;
    private static LOTRRandomSkins blueDwarfSkinsFemale;
    private static ResourceLocation ringTexture;
    protected ModelBiped standardRenderPassModel;
    
    public LOTRRenderDwarf() {
        super(new LOTRModelDwarf(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.standardRenderPassModel = new LOTRModelDwarf(0.5f, 64, 64)));
        LOTRRenderDwarf.dwarfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/dwarf_male");
        LOTRRenderDwarf.dwarfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/dwarf_female");
        LOTRRenderDwarf.blueDwarfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/blueMountains_male");
        LOTRRenderDwarf.blueDwarfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/blueMountains_female");
    }
    
    @Override
    protected void func_82421_b() {
        super.field_82423_g = new LOTRModelDwarf(1.0f);
        super.field_82425_h = new LOTRModelDwarf(0.5f);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
        if (dwarf instanceof LOTREntityBlueDwarf) {
            if (dwarf.familyInfo.isMale()) {
                return LOTRRenderDwarf.blueDwarfSkinsMale.getRandomSkin(dwarf);
            }
            return LOTRRenderDwarf.blueDwarfSkinsFemale.getRandomSkin(dwarf);
        }
        else {
            if (dwarf.familyInfo.isMale()) {
                return LOTRRenderDwarf.dwarfSkinsMale.getRandomSkin(dwarf);
            }
            return LOTRRenderDwarf.dwarfSkinsFemale.getRandomSkin(dwarf);
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
        if (pass == 1 && dwarf.getClass() == dwarf.familyInfo.marriageEntityClass && dwarf.getEquipmentInSlot(4) != null && dwarf.getEquipmentInSlot(4).getItem() == dwarf.familyInfo.marriageRing) {
            this.bindTexture(LOTRRenderDwarf.ringTexture);
            this.setRenderPassModel((ModelBase)this.standardRenderPassModel);
            ((ModelBiped)((RendererLivingEntity)this).renderPassModel).bipedRightArm.showModel = false;
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        GL11.glScalef(0.8125f, 0.8125f, 0.8125f);
        if (LOTRMod.isAprilFools()) {
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    @Override
    public float getHeldItemYTranslation() {
        return 0.125f;
    }
    
    static {
        LOTRRenderDwarf.ringTexture = new ResourceLocation("lotr:mob/dwarf/ring.png");
    }
}
