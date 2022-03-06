// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.inventory.IInventory;
import lotr.common.recipe.LOTRRecipesPouch;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.block.LOTRBlockCraftingTable;
import net.minecraft.item.crafting.IRecipe;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.inventory.ContainerWorkbench;

public abstract class LOTRContainerCraftingTable extends ContainerWorkbench
{
    private World theWorld;
    private int tablePosX;
    private int tablePosY;
    private int tablePosZ;
    public final List<IRecipe> recipeList;
    public final LOTRBlockCraftingTable tableBlock;
    
    public LOTRContainerCraftingTable(final InventoryPlayer inv, final World world, final int i, final int j, final int k, final List<IRecipe> list, final Block block) {
        super(inv, world, i, j, k);
        this.theWorld = world;
        this.tablePosX = i;
        this.tablePosY = j;
        this.tablePosZ = k;
        this.tableBlock = (LOTRBlockCraftingTable)block;
        (this.recipeList = new ArrayList<IRecipe>(list)).add((IRecipe)new LOTRRecipesPouch(this.tableBlock.tableFaction));
    }
    
    public void onCraftMatrixChanged(final IInventory inv) {
        if (this.recipeList == null) {
            return;
        }
        super.craftResult.setInventorySlotContents(0, LOTRRecipes.findMatchingRecipe(this.recipeList, super.craftMatrix, this.theWorld));
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theWorld.getBlock(this.tablePosX, this.tablePosY, this.tablePosZ) == this.tableBlock && entityplayer.getDistanceSq(this.tablePosX + 0.5, this.tablePosY + 0.5, this.tablePosZ + 0.5) <= 64.0;
    }
    
    public static class Morgul extends LOTRContainerCraftingTable
    {
        public Morgul(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.morgulRecipes, LOTRMod.morgulTable);
        }
    }
    
    public static class Elven extends LOTRContainerCraftingTable
    {
        public Elven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.elvenRecipes, LOTRMod.elvenTable);
        }
    }
    
    public static class Dwarven extends LOTRContainerCraftingTable
    {
        public Dwarven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.dwarvenRecipes, LOTRMod.dwarvenTable);
        }
    }
    
    public static class Uruk extends LOTRContainerCraftingTable
    {
        public Uruk(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.urukRecipes, LOTRMod.urukTable);
        }
    }
    
    public static class Gondorian extends LOTRContainerCraftingTable
    {
        public Gondorian(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.gondorianRecipes, LOTRMod.gondorianTable);
        }
    }
    
    public static class Rohirric extends LOTRContainerCraftingTable
    {
        public Rohirric(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.rohirricRecipes, LOTRMod.rohirricTable);
        }
    }
    
    public static class WoodElven extends LOTRContainerCraftingTable
    {
        public WoodElven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.woodElvenRecipes, LOTRMod.woodElvenTable);
        }
    }
    
    public static class Dunlending extends LOTRContainerCraftingTable
    {
        public Dunlending(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.dunlendingRecipes, LOTRMod.dunlendingTable);
        }
    }
    
    public static class Angmar extends LOTRContainerCraftingTable
    {
        public Angmar(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.angmarRecipes, LOTRMod.angmarTable);
        }
    }
    
    public static class NearHarad extends LOTRContainerCraftingTable
    {
        public NearHarad(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.nearHaradRecipes, LOTRMod.nearHaradTable);
        }
    }
    
    public static class HighElven extends LOTRContainerCraftingTable
    {
        public HighElven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.highElvenRecipes, LOTRMod.highElvenTable);
        }
    }
    
    public static class BlueDwarven extends LOTRContainerCraftingTable
    {
        public BlueDwarven(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.blueMountainsRecipes, LOTRMod.blueDwarvenTable);
        }
    }
    
    public static class Ranger extends LOTRContainerCraftingTable
    {
        public Ranger(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.rangerRecipes, LOTRMod.rangerTable);
        }
    }
    
    public static class DolGuldur extends LOTRContainerCraftingTable
    {
        public DolGuldur(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.dolGuldurRecipes, LOTRMod.dolGuldurTable);
        }
    }
    
    public static class Gundabad extends LOTRContainerCraftingTable
    {
        public Gundabad(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.gundabadRecipes, LOTRMod.gundabadTable);
        }
    }
    
    public static class HalfTroll extends LOTRContainerCraftingTable
    {
        public HalfTroll(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.halfTrollRecipes, LOTRMod.halfTrollTable);
        }
    }
    
    public static class DolAmroth extends LOTRContainerCraftingTable
    {
        public DolAmroth(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.dolAmrothRecipes, LOTRMod.dolAmrothTable);
        }
    }
    
    public static class Moredain extends LOTRContainerCraftingTable
    {
        public Moredain(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.moredainRecipes, LOTRMod.moredainTable);
        }
    }
    
    public static class Tauredain extends LOTRContainerCraftingTable
    {
        public Tauredain(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.tauredainRecipes, LOTRMod.tauredainTable);
        }
    }
    
    public static class Dale extends LOTRContainerCraftingTable
    {
        public Dale(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.daleRecipes, LOTRMod.daleTable);
        }
    }
    
    public static class Dorwinion extends LOTRContainerCraftingTable
    {
        public Dorwinion(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.dorwinionRecipes, LOTRMod.dorwinionTable);
        }
    }
    
    public static class Hobbit extends LOTRContainerCraftingTable
    {
        public Hobbit(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.hobbitRecipes, LOTRMod.hobbitTable);
        }
    }
    
    public static class Rhun extends LOTRContainerCraftingTable
    {
        public Rhun(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.rhunRecipes, LOTRMod.rhunTable);
        }
    }
    
    public static class Rivendell extends LOTRContainerCraftingTable
    {
        public Rivendell(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.rivendellRecipes, LOTRMod.rivendellTable);
        }
    }
    
    public static class Umbar extends LOTRContainerCraftingTable
    {
        public Umbar(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.umbarRecipes, LOTRMod.umbarTable);
        }
    }
    
    public static class Gulf extends LOTRContainerCraftingTable
    {
        public Gulf(final InventoryPlayer inv, final World world, final int i, final int j, final int k) {
            super(inv, world, i, j, k, LOTRRecipes.gulfRecipes, LOTRMod.gulfTable);
        }
    }
}
