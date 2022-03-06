// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRReflection;
import net.minecraft.init.Items;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityGiraffe;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;

public class LOTRItemMountArmor extends Item
{
    private ItemArmor.ArmorMaterial armorMaterial;
    private Mount mountType;
    private int damageReduceAmount;
    private Item templateItem;
    
    public LOTRItemMountArmor(final LOTRMaterial material, final Mount mount) {
        this(material.toArmorMaterial(), mount);
    }
    
    public LOTRItemMountArmor(final ItemArmor.ArmorMaterial material, final Mount mount) {
        this.armorMaterial = material;
        this.damageReduceAmount = material.getDamageReductionAmount(1) + material.getDamageReductionAmount(2);
        this.mountType = mount;
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
    }
    
    public LOTRItemMountArmor setTemplateItem(final Item item) {
        this.templateItem = item;
        return this;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        if (this.templateItem != null) {
            return this.templateItem.getItemStackDisplayName(this.createTemplateItemStack(itemstack));
        }
        return super.getItemStackDisplayName(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(final ItemStack itemstack) {
        if (this.templateItem != null) {
            return this.templateItem.getIconIndex(this.createTemplateItemStack(itemstack));
        }
        return super.getIconIndex(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int i) {
        if (this.templateItem != null) {
            return this.templateItem.getIconFromDamage(i);
        }
        return super.getIconFromDamage(i);
    }
    
    private ItemStack createTemplateItemStack(final ItemStack source) {
        final ItemStack template = new ItemStack(this.templateItem);
        template.stackSize = source.stackSize;
        template.setItemDamage(source.getItemDamage());
        if (source.getTagCompound() != null) {
            template.setTagCompound(source.getTagCompound());
        }
        return template;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        if (this.templateItem == null) {
            super.registerIcons(iconregister);
        }
    }
    
    public boolean isValid(final LOTRNPCMount mount) {
        if (mount instanceof LOTREntityElk) {
            return this.mountType == Mount.ELK;
        }
        if (mount instanceof LOTREntityWildBoar) {
            return this.mountType == Mount.BOAR;
        }
        if (mount instanceof LOTREntityCamel) {
            return this.mountType == Mount.CAMEL;
        }
        if (mount instanceof LOTREntityWarg) {
            return this.mountType == Mount.WARG;
        }
        if (mount instanceof LOTREntityGiraffe) {
            return this.mountType == Mount.GIRAFFE;
        }
        if (mount instanceof LOTREntityRhino) {
            return this.mountType == Mount.RHINO;
        }
        return this.mountType == Mount.HORSE;
    }
    
    public int getDamageReduceAmount() {
        return this.damageReduceAmount;
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    public ItemArmor.ArmorMaterial getMountArmorMaterial() {
        return this.armorMaterial;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return this.armorMaterial.func_151685_b() == repairItem.getItem() || super.getIsRepairable(itemstack, repairItem);
    }
    
    public String getArmorTexture() {
        String path = null;
        if (this.templateItem != null) {
            int index = 0;
            if (this.templateItem == Items.iron_horse_armor) {
                index = 1;
            }
            if (this.templateItem == Items.golden_horse_armor) {
                index = 2;
            }
            if (this.templateItem == Items.diamond_horse_armor) {
                index = 3;
            }
            path = LOTRReflection.getHorseArmorTextures()[index];
        }
        else {
            final String mountName = this.mountType.textureName;
            String materialName = this.armorMaterial.name().toLowerCase();
            if (materialName.startsWith("lotr_")) {
                materialName = materialName.substring("lotr_".length());
            }
            path = "lotr:armor/mount/" + mountName + "_" + materialName + ".png";
        }
        return path;
    }
    
    public enum Mount
    {
        HORSE("horse"), 
        ELK("elk"), 
        BOAR("boar"), 
        CAMEL("camel"), 
        WARG("warg"), 
        GIRAFFE("giraffe"), 
        RHINO("rhino");
        
        public final String textureName;
        
        private Mount(final String s) {
            this.textureName = s;
        }
    }
}
