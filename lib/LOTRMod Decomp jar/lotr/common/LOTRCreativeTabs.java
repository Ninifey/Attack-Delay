// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class LOTRCreativeTabs extends CreativeTabs
{
    public static LOTRCreativeTabs tabBlock;
    public static LOTRCreativeTabs tabUtil;
    public static LOTRCreativeTabs tabDeco;
    public static LOTRCreativeTabs tabFood;
    public static LOTRCreativeTabs tabMaterials;
    public static LOTRCreativeTabs tabMisc;
    public static LOTRCreativeTabs tabTools;
    public static LOTRCreativeTabs tabCombat;
    public static LOTRCreativeTabs tabStory;
    public static LOTRCreativeTabs tabSpawn;
    public ItemStack theIcon;
    
    public LOTRCreativeTabs(final String label) {
        super(label);
    }
    
    public static void setupIcons() {
        LOTRCreativeTabs.tabBlock.theIcon = new ItemStack(LOTRMod.brick, 1, 11);
        LOTRCreativeTabs.tabUtil.theIcon = new ItemStack(LOTRMod.dwarvenForge);
        LOTRCreativeTabs.tabDeco.theIcon = new ItemStack(LOTRMod.simbelmyne);
        LOTRCreativeTabs.tabFood.theIcon = new ItemStack(LOTRMod.lembas);
        LOTRCreativeTabs.tabMaterials.theIcon = new ItemStack(LOTRMod.mithril);
        LOTRCreativeTabs.tabMisc.theIcon = new ItemStack(LOTRMod.hobbitPipe);
        LOTRCreativeTabs.tabTools.theIcon = new ItemStack(LOTRMod.pickaxeOrc);
        LOTRCreativeTabs.tabCombat.theIcon = new ItemStack(LOTRMod.helmetGondor);
        LOTRCreativeTabs.tabStory.theIcon = new ItemStack(LOTRMod.anduril);
        LOTRCreativeTabs.tabSpawn.theIcon = new ItemStack(LOTRMod.spawnEgg, 1, 55);
    }
    
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return StatCollector.translateToLocal("lotr.creativetab." + this.getTabLabel());
    }
    
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return this.theIcon.getItem();
    }
    
    public ItemStack getIconItemStack() {
        return this.theIcon;
    }
    
    static {
        LOTRCreativeTabs.tabBlock = new LOTRCreativeTabs("blocks");
        LOTRCreativeTabs.tabUtil = new LOTRCreativeTabs("util");
        LOTRCreativeTabs.tabDeco = new LOTRCreativeTabs("decorations");
        LOTRCreativeTabs.tabFood = new LOTRCreativeTabs("food");
        LOTRCreativeTabs.tabMaterials = new LOTRCreativeTabs("materials");
        LOTRCreativeTabs.tabMisc = new LOTRCreativeTabs("misc");
        LOTRCreativeTabs.tabTools = new LOTRCreativeTabs("tools");
        LOTRCreativeTabs.tabCombat = new LOTRCreativeTabs("combat");
        LOTRCreativeTabs.tabStory = new LOTRCreativeTabs("story");
        LOTRCreativeTabs.tabSpawn = new LOTRCreativeTabs("spawning");
    }
}
