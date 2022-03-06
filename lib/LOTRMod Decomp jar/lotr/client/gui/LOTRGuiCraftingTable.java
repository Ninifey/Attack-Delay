// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.World;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerCraftingTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public abstract class LOTRGuiCraftingTable extends GuiContainer
{
    private static ResourceLocation craftingTexture;
    private String unlocalizedName;
    
    public LOTRGuiCraftingTable(final LOTRContainerCraftingTable container, final String s) {
        super((Container)container);
        this.unlocalizedName = s;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String title = StatCollector.translateToLocal("container.lotr.crafting." + this.unlocalizedName);
        ((GuiScreen)this).fontRendererObj.drawString(title, 28, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, super.field_147000_g - 96 + 2, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiCraftingTable.craftingTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
    }
    
    static {
        LOTRGuiCraftingTable.craftingTexture = new ResourceLocation("textures/gui/container/crafting_table.png");
    }
    
    public static class Morgul extends LOTRGuiCraftingTable
    {
        public Morgul(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Morgul(inv, world, i, j, k), "morgul");
        }
    }
    
    public static class Elven extends LOTRGuiCraftingTable
    {
        public Elven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Elven(inv, world, i, j, k), "elven");
        }
    }
    
    public static class Dwarven extends LOTRGuiCraftingTable
    {
        public Dwarven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Dwarven(inv, world, i, j, k), "dwarven");
        }
    }
    
    public static class Uruk extends LOTRGuiCraftingTable
    {
        public Uruk(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Uruk(inv, world, i, j, k), "uruk");
        }
    }
    
    public static class Gondorian extends LOTRGuiCraftingTable
    {
        public Gondorian(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Gondorian(inv, world, i, j, k), "gondorian");
        }
    }
    
    public static class Rohirric extends LOTRGuiCraftingTable
    {
        public Rohirric(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Rohirric(inv, world, i, j, k), "rohirric");
        }
    }
    
    public static class WoodElven extends LOTRGuiCraftingTable
    {
        public WoodElven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.WoodElven(inv, world, i, j, k), "woodElven");
        }
    }
    
    public static class Dunlending extends LOTRGuiCraftingTable
    {
        public Dunlending(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Dunlending(inv, world, i, j, k), "dunlending");
        }
    }
    
    public static class Angmar extends LOTRGuiCraftingTable
    {
        public Angmar(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Angmar(inv, world, i, j, k), "angmar");
        }
    }
    
    public static class NearHarad extends LOTRGuiCraftingTable
    {
        public NearHarad(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.NearHarad(inv, world, i, j, k), "nearHarad");
        }
    }
    
    public static class HighElven extends LOTRGuiCraftingTable
    {
        public HighElven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.HighElven(inv, world, i, j, k), "highElven");
        }
    }
    
    public static class BlueDwarven extends LOTRGuiCraftingTable
    {
        public BlueDwarven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.BlueDwarven(inv, world, i, j, k), "blueDwarven");
        }
    }
    
    public static class Ranger extends LOTRGuiCraftingTable
    {
        public Ranger(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Ranger(inv, world, i, j, k), "ranger");
        }
    }
    
    public static class DolGuldur extends LOTRGuiCraftingTable
    {
        public DolGuldur(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.DolGuldur(inv, world, i, j, k), "dolGuldur");
        }
    }
    
    public static class Gundabad extends LOTRGuiCraftingTable
    {
        public Gundabad(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Gundabad(inv, world, i, j, k), "gundabad");
        }
    }
    
    public static class HalfTroll extends LOTRGuiCraftingTable
    {
        public HalfTroll(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.HalfTroll(inv, world, i, j, k), "halfTroll");
        }
    }
    
    public static class DolAmroth extends LOTRGuiCraftingTable
    {
        public DolAmroth(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.DolAmroth(inv, world, i, j, k), "dolAmroth");
        }
    }
    
    public static class Moredain extends LOTRGuiCraftingTable
    {
        public Moredain(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Moredain(inv, world, i, j, k), "moredain");
        }
    }
    
    public static class Tauredain extends LOTRGuiCraftingTable
    {
        public Tauredain(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Tauredain(inv, world, i, j, k), "tauredain");
        }
    }
    
    public static class Dale extends LOTRGuiCraftingTable
    {
        public Dale(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Dale(inv, world, i, j, k), "dale");
        }
    }
    
    public static class Dorwinion extends LOTRGuiCraftingTable
    {
        public Dorwinion(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Dorwinion(inv, world, i, j, k), "dorwinion");
        }
    }
    
    public static class Hobbit extends LOTRGuiCraftingTable
    {
        public Hobbit(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Hobbit(inv, world, i, j, k), "hobbit");
        }
    }
    
    public static class Rhun extends LOTRGuiCraftingTable
    {
        public Rhun(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Rhun(inv, world, i, j, k), "rhun");
        }
    }
    
    public static class Rivendell extends LOTRGuiCraftingTable
    {
        public Rivendell(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Rivendell(inv, world, i, j, k), "rivendell");
        }
    }
    
    public static class Umbar extends LOTRGuiCraftingTable
    {
        public Umbar(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Umbar(inv, world, i, j, k), "umbar");
        }
    }
    
    public static class Gulf extends LOTRGuiCraftingTable
    {
        public Gulf(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(new LOTRContainerCraftingTable.Gulf(inv, world, i, j, k), "gulf");
        }
    }
}
