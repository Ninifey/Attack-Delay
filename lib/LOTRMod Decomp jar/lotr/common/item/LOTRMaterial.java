// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import java.util.List;

public class LOTRMaterial
{
    private static float[] protectionBase;
    private static float maxProtection;
    public static List<LOTRMaterial> allLOTRMaterials;
    public static LOTRMaterial BRONZE;
    public static LOTRMaterial MITHRIL;
    public static LOTRMaterial FUR;
    public static LOTRMaterial GEMSBOK;
    public static LOTRMaterial BONE;
    public static LOTRMaterial GAMBESON;
    public static LOTRMaterial JACKET;
    public static LOTRMaterial GONDOR;
    public static LOTRMaterial DOL_AMROTH;
    public static LOTRMaterial ROHAN;
    public static LOTRMaterial ROHAN_MARSHAL;
    public static LOTRMaterial RANGER;
    public static LOTRMaterial RANGER_ITHILIEN;
    public static LOTRMaterial DUNLENDING;
    public static LOTRMaterial NEAR_HARAD;
    public static LOTRMaterial HARNEDOR;
    public static LOTRMaterial UMBAR;
    public static LOTRMaterial CORSAIR;
    public static LOTRMaterial GULF_HARAD;
    public static LOTRMaterial HARAD_NOMAD;
    public static LOTRMaterial ANCIENT_HARAD;
    public static LOTRMaterial MOREDAIN;
    public static LOTRMaterial MOREDAIN_SPEAR;
    public static LOTRMaterial MOREDAIN_WOOD;
    public static LOTRMaterial MOREDAIN_LION_ARMOR;
    public static LOTRMaterial MOREDAIN_BRONZE;
    public static LOTRMaterial TAUREDAIN;
    public static LOTRMaterial TAUREDAIN_GOLD;
    public static LOTRMaterial BARROW;
    public static LOTRMaterial DALE;
    public static LOTRMaterial DORWINION;
    public static LOTRMaterial LOSSARNACH;
    public static LOTRMaterial PELARGIR;
    public static LOTRMaterial PINNATH_GELIN;
    public static LOTRMaterial BLACKROOT;
    public static LOTRMaterial LAMEDON;
    public static LOTRMaterial ARNOR;
    public static LOTRMaterial RHUN;
    public static LOTRMaterial RHUN_GOLD;
    public static LOTRMaterial BLACK_NUMENOREAN;
    public static LOTRMaterial MALLORN;
    public static LOTRMaterial GALADHRIM;
    public static LOTRMaterial GALVORN;
    public static LOTRMaterial WOOD_ELVEN_SCOUT;
    public static LOTRMaterial WOOD_ELVEN;
    public static LOTRMaterial HIGH_ELVEN;
    public static LOTRMaterial GONDOLIN;
    public static LOTRMaterial MALLORN_MACE;
    public static LOTRMaterial HITHLAIN;
    public static LOTRMaterial DORWINION_ELF;
    public static LOTRMaterial RIVENDELL;
    public static LOTRMaterial DWARVEN;
    public static LOTRMaterial BLUE_DWARVEN;
    public static LOTRMaterial BLADORTHIN;
    public static LOTRMaterial MORDOR;
    public static LOTRMaterial URUK;
    public static LOTRMaterial MORGUL;
    public static LOTRMaterial GUNDABAD_URUK;
    public static LOTRMaterial ANGMAR;
    public static LOTRMaterial DOL_GULDUR;
    public static LOTRMaterial BLACK_URUK;
    public static LOTRMaterial UTUMNO;
    public static LOTRMaterial HALF_TROLL;
    public static LOTRMaterial COSMETIC;
    public static LOTRMaterial HARAD_ROBES;
    public static LOTRMaterial KAFTAN;
    private String materialName;
    private boolean undamageable;
    private int uses;
    private float damage;
    private int[] protection;
    private int harvestLevel;
    private float speed;
    private int enchantability;
    private boolean canHarvestManFlesh;
    private Item.ToolMaterial toolMaterial;
    private ItemArmor.ArmorMaterial armorMaterial;
    
    public static void setCraftingItems() {
        LOTRMaterial.BRONZE.setCraftingItem(LOTRMod.bronze);
        LOTRMaterial.MITHRIL.setCraftingItems(LOTRMod.mithril, LOTRMod.mithrilMail);
        LOTRMaterial.FUR.setCraftingItem(LOTRMod.fur);
        LOTRMaterial.GEMSBOK.setCraftingItem(LOTRMod.gemsbokHide);
        LOTRMaterial.GAMBESON.setCraftingItem(Item.getItemFromBlock(Blocks.wool));
        LOTRMaterial.JACKET.setCraftingItem(Items.leather);
        LOTRMaterial.GONDOR.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.DOL_AMROTH.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.ROHAN.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.ROHAN_MARSHAL.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.RANGER.setCraftingItems(Items.iron_ingot, Items.leather);
        LOTRMaterial.RANGER_ITHILIEN.setCraftingItems(Items.iron_ingot, Items.leather);
        LOTRMaterial.DUNLENDING.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.NEAR_HARAD.setCraftingItem(LOTRMod.bronze);
        LOTRMaterial.HARNEDOR.setCraftingItem(LOTRMod.bronze);
        LOTRMaterial.UMBAR.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.CORSAIR.setCraftingItems(Items.iron_ingot, LOTRMod.bronze);
        LOTRMaterial.GULF_HARAD.setCraftingItem(LOTRMod.bronze);
        LOTRMaterial.HARAD_NOMAD.setCraftingItem(Item.getItemFromBlock(LOTRMod.driedReeds));
        LOTRMaterial.ANCIENT_HARAD.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.MOREDAIN.setCraftingItems(LOTRMod.rhinoHorn, LOTRMod.gemsbokHide);
        LOTRMaterial.MOREDAIN_SPEAR.setCraftingItem(LOTRMod.gemsbokHorn);
        LOTRMaterial.MOREDAIN_LION_ARMOR.setCraftingItem(LOTRMod.lionFur);
        LOTRMaterial.MOREDAIN_BRONZE.setCraftingItem(LOTRMod.bronze);
        LOTRMaterial.TAUREDAIN.setCraftingItems(LOTRMod.obsidianShard, LOTRMod.bronze);
        LOTRMaterial.TAUREDAIN_GOLD.setCraftingItem(Items.gold_ingot);
        LOTRMaterial.BARROW.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.DALE.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.DORWINION.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.LOSSARNACH.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.PELARGIR.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.PINNATH_GELIN.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.BLACKROOT.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.LAMEDON.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.ARNOR.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.RHUN.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.RHUN_GOLD.setCraftingItem(LOTRMod.gildedIron);
        LOTRMaterial.BLACK_NUMENOREAN.setCraftingItem(Items.iron_ingot);
        LOTRMaterial.GALADHRIM.setCraftingItem(LOTRMod.elfSteel);
        LOTRMaterial.GALVORN.setCraftingItem(LOTRMod.galvorn);
        LOTRMaterial.WOOD_ELVEN_SCOUT.setCraftingItems(LOTRMod.elfSteel, Items.leather);
        LOTRMaterial.WOOD_ELVEN.setCraftingItem(LOTRMod.elfSteel);
        LOTRMaterial.HIGH_ELVEN.setCraftingItem(LOTRMod.elfSteel);
        LOTRMaterial.GONDOLIN.setCraftingItem(LOTRMod.elfSteel);
        LOTRMaterial.HITHLAIN.setCraftingItem(LOTRMod.hithlain);
        LOTRMaterial.DORWINION_ELF.setCraftingItem(LOTRMod.elfSteel);
        LOTRMaterial.RIVENDELL.setCraftingItem(LOTRMod.elfSteel);
        LOTRMaterial.DWARVEN.setCraftingItem(LOTRMod.dwarfSteel);
        LOTRMaterial.BLUE_DWARVEN.setCraftingItem(LOTRMod.blueDwarfSteel);
        LOTRMaterial.BLADORTHIN.setCraftingItem(LOTRMod.dwarfSteel);
        LOTRMaterial.MORDOR.setCraftingItem(LOTRMod.orcSteel);
        LOTRMaterial.URUK.setCraftingItem(LOTRMod.urukSteel);
        LOTRMaterial.MORGUL.setCraftingItem(LOTRMod.morgulSteel);
        LOTRMaterial.GUNDABAD_URUK.setCraftingItem(LOTRMod.urukSteel);
        LOTRMaterial.ANGMAR.setCraftingItem(LOTRMod.orcSteel);
        LOTRMaterial.DOL_GULDUR.setCraftingItem(LOTRMod.orcSteel);
        LOTRMaterial.BLACK_URUK.setCraftingItem(LOTRMod.blackUrukSteel);
        LOTRMaterial.UTUMNO.setCraftingItem(LOTRMod.orcSteel);
        LOTRMaterial.HALF_TROLL.setCraftingItems(Items.flint, LOTRMod.gemsbokHide);
    }
    
    private LOTRMaterial(final String name) {
        this.undamageable = false;
        this.canHarvestManFlesh = false;
        this.materialName = "LOTR_" + name;
        LOTRMaterial.allLOTRMaterials.add(this);
    }
    
    private LOTRMaterial setUndamageable() {
        this.undamageable = true;
        return this;
    }
    
    public boolean isDamageable() {
        return !this.undamageable;
    }
    
    private LOTRMaterial setUses(final int i) {
        this.uses = i;
        return this;
    }
    
    private LOTRMaterial setDamage(final float f) {
        this.damage = f;
        return this;
    }
    
    private LOTRMaterial setProtection(final float f) {
        this.protection = new int[LOTRMaterial.protectionBase.length];
        for (int i = 0; i < this.protection.length; ++i) {
            this.protection[i] = Math.round(LOTRMaterial.protectionBase[i] * f * LOTRMaterial.maxProtection);
        }
        return this;
    }
    
    private LOTRMaterial setHarvestLevel(final int i) {
        this.harvestLevel = i;
        return this;
    }
    
    private LOTRMaterial setSpeed(final float f) {
        this.speed = f;
        return this;
    }
    
    private LOTRMaterial setEnchantability(final int i) {
        this.enchantability = i;
        return this;
    }
    
    private LOTRMaterial setManFlesh() {
        this.canHarvestManFlesh = true;
        return this;
    }
    
    public boolean canHarvestManFlesh() {
        return this.canHarvestManFlesh;
    }
    
    public Item.ToolMaterial toToolMaterial() {
        if (this.toolMaterial == null) {
            this.toolMaterial = EnumHelper.addToolMaterial(this.materialName, this.harvestLevel, this.uses, this.speed, this.damage, this.enchantability);
        }
        return this.toolMaterial;
    }
    
    public ItemArmor.ArmorMaterial toArmorMaterial() {
        if (this.armorMaterial == null) {
            this.armorMaterial = EnumHelper.addArmorMaterial(this.materialName, Math.round(this.uses * 0.06f), this.protection, this.enchantability);
        }
        return this.armorMaterial;
    }
    
    private void setCraftingItem(final Item item) {
        this.setCraftingItems(item, item);
    }
    
    private void setCraftingItems(final Item toolItem, final Item armorItem) {
        this.toToolMaterial().setRepairItem(new ItemStack(toolItem));
        this.toArmorMaterial().customCraftingMaterial = armorItem;
    }
    
    public static Item.ToolMaterial getToolMaterialByName(final String name) {
        return Item.ToolMaterial.valueOf(name);
    }
    
    public static ItemArmor.ArmorMaterial getArmorMaterialByName(final String name) {
        return ItemArmor.ArmorMaterial.valueOf(name);
    }
    
    static {
        LOTRMaterial.protectionBase = new float[] { 0.14f, 0.4f, 0.32f, 0.14f };
        LOTRMaterial.maxProtection = 25.0f;
        LOTRMaterial.allLOTRMaterials = new ArrayList<LOTRMaterial>();
        LOTRMaterial.BRONZE = new LOTRMaterial("BRONZE").setUses(230).setDamage(1.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(5.0f).setEnchantability(10);
        LOTRMaterial.MITHRIL = new LOTRMaterial("MITHRIL").setUses(2400).setDamage(5.0f).setProtection(0.8f).setHarvestLevel(4).setSpeed(9.0f).setEnchantability(8);
        LOTRMaterial.FUR = new LOTRMaterial("FUR").setUses(180).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(8);
        LOTRMaterial.GEMSBOK = new LOTRMaterial("GEMSBOK").setUses(180).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(10);
        LOTRMaterial.BONE = new LOTRMaterial("BONE").setUses(150).setDamage(0.0f).setProtection(0.3f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(10);
        LOTRMaterial.GAMBESON = new LOTRMaterial("GAMBESON").setUses(200).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(10);
        LOTRMaterial.JACKET = new LOTRMaterial("JACKET").setUses(150).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(10);
        LOTRMaterial.GONDOR = new LOTRMaterial("GONDOR").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.DOL_AMROTH = new LOTRMaterial("DOL_AMROTH").setUses(500).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.ROHAN = new LOTRMaterial("ROHAN").setUses(300).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.ROHAN_MARSHAL = new LOTRMaterial("ROHAN_MARSHAL").setUses(400).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.RANGER = new LOTRMaterial("RANGER").setUses(350).setDamage(2.5f).setProtection(0.48f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(12);
        LOTRMaterial.RANGER_ITHILIEN = new LOTRMaterial("RANGER_ITHILIEN").setUses(350).setDamage(2.5f).setProtection(0.48f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(12);
        LOTRMaterial.DUNLENDING = new LOTRMaterial("DUNLENDING").setUses(250).setDamage(2.0f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(8);
        LOTRMaterial.NEAR_HARAD = new LOTRMaterial("NEAR_HARAD").setUses(300).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.HARNEDOR = new LOTRMaterial("HARNEDOR").setUses(250).setDamage(2.0f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(8);
        LOTRMaterial.UMBAR = new LOTRMaterial("UMBAR").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.CORSAIR = new LOTRMaterial("CORSAIR").setUses(300).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.GULF_HARAD = new LOTRMaterial("GULF_HARAD").setUses(350).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.HARAD_NOMAD = new LOTRMaterial("HARAD_NOMAD").setUses(200).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(8);
        LOTRMaterial.ANCIENT_HARAD = new LOTRMaterial("ANCIENT_HARAD").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.MOREDAIN = new LOTRMaterial("MOREDAIN").setUses(250).setDamage(2.0f).setProtection(0.48f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.MOREDAIN_SPEAR = new LOTRMaterial("MOREDAIN_SPEAR").setUses(250).setDamage(3.0f).setProtection(0.0f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.MOREDAIN_WOOD = new LOTRMaterial("MOREDAIN_WOOD").setUses(250).setDamage(2.0f).setProtection(0.0f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.MOREDAIN_LION_ARMOR = new LOTRMaterial("MOREDAIN_LION_ARMOR").setUses(300).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(8);
        LOTRMaterial.MOREDAIN_BRONZE = new LOTRMaterial("MOREDAIN_BRONZE").setUses(230).setDamage(1.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(5.0f).setEnchantability(10);
        LOTRMaterial.TAUREDAIN = new LOTRMaterial("TAUREDAIN").setUses(300).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(3).setSpeed(8.0f).setEnchantability(10);
        LOTRMaterial.TAUREDAIN_GOLD = new LOTRMaterial("TAUREDAIN_GOLD").setUses(400).setDamage(0.0f).setProtection(0.6f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(10);
        LOTRMaterial.BARROW = new LOTRMaterial("BARROW").setUses(600).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(8.0f).setEnchantability(10);
        LOTRMaterial.DALE = new LOTRMaterial("DALE").setUses(300).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.DORWINION = new LOTRMaterial("DORWINION").setUses(400).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.LOSSARNACH = new LOTRMaterial("LOSSARNACH").setUses(300).setDamage(2.0f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.PELARGIR = new LOTRMaterial("PELARGIR").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.PINNATH_GELIN = new LOTRMaterial("PINNATH_GELIN").setUses(400).setDamage(2.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.BLACKROOT = new LOTRMaterial("BLACKROOT").setUses(400).setDamage(2.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.LAMEDON = new LOTRMaterial("LAMEDON").setUses(300).setDamage(2.0f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.ARNOR = new LOTRMaterial("ARNOR").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.RHUN = new LOTRMaterial("RHUN").setUses(400).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.RHUN_GOLD = new LOTRMaterial("RHUN_GOLD").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.BLACK_NUMENOREAN = new LOTRMaterial("BLACK_NUMENOREAN").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10);
        LOTRMaterial.MALLORN = new LOTRMaterial("MALLORN").setUses(200).setDamage(1.5f).setProtection(0.0f).setHarvestLevel(1).setSpeed(4.0f).setEnchantability(15);
        LOTRMaterial.GALADHRIM = new LOTRMaterial("GALADHRIM").setUses(600).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(7.0f).setEnchantability(15);
        LOTRMaterial.GALVORN = new LOTRMaterial("GALVORN").setUses(600).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(7.0f).setEnchantability(15);
        LOTRMaterial.WOOD_ELVEN_SCOUT = new LOTRMaterial("WOOD_ELVEN_SCOUT").setUses(300).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(15);
        LOTRMaterial.WOOD_ELVEN = new LOTRMaterial("WOOD_ELVEN").setUses(500).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(9.0f).setEnchantability(15);
        LOTRMaterial.HIGH_ELVEN = new LOTRMaterial("HIGH_ELVEN").setUses(700).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(8.0f).setEnchantability(15);
        LOTRMaterial.GONDOLIN = new LOTRMaterial("GONDOLIN").setUses(1500).setDamage(5.0f).setProtection(0.7f).setHarvestLevel(2).setSpeed(8.0f).setEnchantability(15);
        LOTRMaterial.MALLORN_MACE = new LOTRMaterial("MALLORN_MACE").setUses(1500).setDamage(4.5f).setProtection(0.0f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(15);
        LOTRMaterial.HITHLAIN = new LOTRMaterial("HITHLAIN").setUses(300).setDamage(0.0f).setProtection(0.3f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(15);
        LOTRMaterial.DORWINION_ELF = new LOTRMaterial("DORWINION_ELF").setUses(500).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(7.0f).setEnchantability(15);
        LOTRMaterial.RIVENDELL = new LOTRMaterial("RIVENDELL").setUses(700).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(8.0f).setEnchantability(15);
        LOTRMaterial.DWARVEN = new LOTRMaterial("DWARVEN").setUses(700).setDamage(3.0f).setProtection(0.7f).setHarvestLevel(3).setSpeed(7.0f).setEnchantability(10);
        LOTRMaterial.BLUE_DWARVEN = new LOTRMaterial("BLUE_DWARVEN").setUses(650).setDamage(3.0f).setProtection(0.7f).setHarvestLevel(3).setSpeed(7.0f).setEnchantability(12);
        LOTRMaterial.BLADORTHIN = new LOTRMaterial("BLADORTHIN").setUses(600).setDamage(3.0f).setProtection(0.6f).setHarvestLevel(2).setSpeed(7.0f).setEnchantability(10);
        LOTRMaterial.MORDOR = new LOTRMaterial("MORDOR").setUses(400).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(7).setManFlesh();
        LOTRMaterial.URUK = new LOTRMaterial("URUK").setUses(550).setDamage(3.0f).setProtection(0.7f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(5).setManFlesh();
        LOTRMaterial.MORGUL = new LOTRMaterial("MORGUL").setUses(450).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10).setManFlesh();
        LOTRMaterial.GUNDABAD_URUK = new LOTRMaterial("GUNDABAD_URUK").setUses(500).setDamage(3.0f).setProtection(0.7f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(5).setManFlesh();
        LOTRMaterial.ANGMAR = new LOTRMaterial("ANGMAR").setUses(350).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(8).setManFlesh();
        LOTRMaterial.DOL_GULDUR = new LOTRMaterial("DOL_GULDUR").setUses(350).setDamage(2.5f).setProtection(0.6f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(10).setManFlesh();
        LOTRMaterial.BLACK_URUK = new LOTRMaterial("BLACK_URUK").setUses(550).setDamage(3.0f).setProtection(0.7f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(6).setManFlesh();
        LOTRMaterial.UTUMNO = new LOTRMaterial("UTUMNO").setUses(400).setDamage(3.5f).setProtection(0.7f).setHarvestLevel(2).setSpeed(6.0f).setEnchantability(12).setManFlesh();
        LOTRMaterial.HALF_TROLL = new LOTRMaterial("HALF_TROLL").setUses(300).setDamage(2.5f).setProtection(0.5f).setHarvestLevel(1).setSpeed(5.0f).setEnchantability(5).setManFlesh();
        LOTRMaterial.COSMETIC = new LOTRMaterial("COSMETIC").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
        LOTRMaterial.HARAD_ROBES = new LOTRMaterial("HARAD_ROBES").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
        LOTRMaterial.KAFTAN = new LOTRMaterial("KAFTAN").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
    }
}
