// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import java.util.HashMap;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import java.util.Iterator;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.common.util.FakePlayerFactory;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import java.util.List;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.item.ItemArmor;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityFurnace;
import lotr.common.item.LOTRStoryItem;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;
import java.util.Random;

public class LOTRTileEntityUnsmeltery extends LOTRTileEntityForgeBase
{
    private static Random unsmeltingRand;
    private static Map<Pair<Item, Integer>, Integer> unsmeltableCraftingCounts;
    private float prevRocking;
    private float rocking;
    private float prevRockingPhase;
    private float rockingPhase;
    private boolean prevServerActive;
    private boolean serverActive;
    private boolean clientActive;
    
    public LOTRTileEntityUnsmeltery() {
        this.rockingPhase = LOTRTileEntityUnsmeltery.unsmeltingRand.nextFloat() * 3.1415927f * 2.0f;
    }
    
    @Override
    public int getForgeInvSize() {
        return 3;
    }
    
    @Override
    public void setupForgeSlots() {
        super.inputSlots = new int[] { 0 };
        super.fuelSlot = 1;
        super.outputSlots = new int[] { 2 };
    }
    
    @Override
    protected boolean canMachineInsertInput(final ItemStack itemstack) {
        return itemstack != null && this.getLargestUnsmeltingResult(itemstack) != null;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!super.worldObj.isClient) {
            this.prevServerActive = this.serverActive;
            this.serverActive = this.isSmelting();
            if (this.serverActive != this.prevServerActive) {
                super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            }
        }
        else {
            this.prevRocking = this.rocking;
            this.prevRockingPhase = this.rockingPhase;
            this.rockingPhase += 0.1f;
            if (this.clientActive) {
                this.rocking += 0.05f;
            }
            else {
                this.rocking -= 0.01f;
            }
            this.rocking = MathHelper.clamp_float(this.rocking, 0.0f, 1.0f);
        }
    }
    
    public float getRockingAmount(final float tick) {
        final float mag = this.prevRocking + (this.rocking - this.prevRocking) * tick;
        final float phase = this.prevRockingPhase + (this.rockingPhase - this.prevRockingPhase) * tick;
        return mag * MathHelper.sin(phase);
    }
    
    @Override
    public int getSmeltingDuration() {
        return 400;
    }
    
    @Override
    protected boolean canDoSmelting() {
        final ItemStack input = super.inventory[super.inputSlots[0]];
        if (input == null) {
            return false;
        }
        final ItemStack result = this.getLargestUnsmeltingResult(input);
        if (result == null) {
            return false;
        }
        final ItemStack output = super.inventory[super.outputSlots[0]];
        if (output == null) {
            return true;
        }
        if (!output.isItemEqual(result)) {
            return false;
        }
        final int resultSize = output.stackSize + result.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
    }
    
    @Override
    protected void doSmelt() {
        if (this.canDoSmelting()) {
            final ItemStack input = super.inventory[super.inputSlots[0]];
            final ItemStack result = this.getRandomUnsmeltingResult(input);
            if (result != null) {
                final int slot = super.outputSlots[0];
                if (super.inventory[super.outputSlots[0]] == null) {
                    super.inventory[super.outputSlots[0]] = result.copy();
                }
                else if (super.inventory[super.outputSlots[0]].isItemEqual(result)) {
                    final ItemStack itemStack = super.inventory[super.outputSlots[0]];
                    itemStack.stackSize += result.stackSize;
                }
            }
            final ItemStack itemStack2 = super.inventory[super.inputSlots[0]];
            --itemStack2.stackSize;
            if (super.inventory[super.inputSlots[0]].stackSize <= 0) {
                super.inventory[super.inputSlots[0]] = null;
            }
        }
    }
    
    @Override
    public String getForgeName() {
        return StatCollector.translateToLocal("container.lotr.unsmeltery");
    }
    
    public boolean canBeUnsmelted(final ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        if (itemstack.getItem() instanceof LOTRStoryItem) {
            return false;
        }
        final ItemStack material = getEquipmentMaterial(itemstack);
        return material != null && TileEntityFurnace.func_145952_a(material) == 0 && (!(itemstack.getItem() instanceof ItemBlock) || !Block.getBlockFromItem(itemstack.getItem()).getMaterial().getCanBurn()) && this.determineResourcesUsed(itemstack, material) > 0;
    }
    
    private ItemStack getLargestUnsmeltingResult(final ItemStack itemstack) {
        if (itemstack == null || !this.canBeUnsmelted(itemstack)) {
            return null;
        }
        final ItemStack material = getEquipmentMaterial(itemstack);
        final int items = this.determineResourcesUsed(itemstack, material);
        int meta = material.getItemDamage();
        if (meta == 32767) {
            meta = 0;
        }
        return new ItemStack(material.getItem(), items, meta);
    }
    
    private ItemStack getRandomUnsmeltingResult(final ItemStack itemstack) {
        final ItemStack result = this.getLargestUnsmeltingResult(itemstack);
        if (result == null) {
            return null;
        }
        float items = (float)result.stackSize;
        items *= 0.8f;
        if (itemstack.isItemStackDamageable()) {
            items *= (itemstack.getMaxDamage() - itemstack.getItemDamage()) / (float)itemstack.getMaxDamage();
        }
        items *= MathHelper.randomFloatClamp(LOTRTileEntityUnsmeltery.unsmeltingRand, 0.7f, 1.0f);
        final int items_int = Math.round(items);
        if (items_int <= 0) {
            return null;
        }
        return new ItemStack(result.getItem(), items_int, result.getItemDamage());
    }
    
    private static ItemStack getEquipmentMaterial(final ItemStack itemstack) {
        if (itemstack == null) {
            return null;
        }
        final Item item = itemstack.getItem();
        ItemStack material = null;
        if (item instanceof ItemTool) {
            material = ((ItemTool)item).func_150913_i().getRepairItemStack();
        }
        else if (item instanceof ItemSword) {
            material = LOTRMaterial.getToolMaterialByName(((ItemSword)item).func_150932_j()).getRepairItemStack();
        }
        else if (item instanceof LOTRItemCrossbow) {
            material = ((LOTRItemCrossbow)item).getCrossbowMaterial().getRepairItemStack();
        }
        else if (item instanceof LOTRItemThrowingAxe) {
            material = ((LOTRItemThrowingAxe)item).getAxeMaterial().getRepairItemStack();
        }
        else if (item instanceof ItemArmor) {
            material = new ItemStack(((ItemArmor)item).getArmorMaterial().func_151685_b());
        }
        else if (item instanceof LOTRItemMountArmor) {
            material = new ItemStack(((LOTRItemMountArmor)item).getMountArmorMaterial().func_151685_b());
        }
        if (material != null) {
            if (item.getIsRepairable(itemstack, material)) {
                return material;
            }
        }
        else {
            if (item instanceof ItemHoe) {
                return LOTRMaterial.getToolMaterialByName(((ItemHoe)item).getMaterialName()).getRepairItemStack();
            }
            if (item == Items.bucket) {
                return new ItemStack(Items.iron_ingot);
            }
            if (item == LOTRMod.silverRing) {
                return new ItemStack(LOTRMod.silverNugget);
            }
            if (item == LOTRMod.goldRing) {
                return new ItemStack(Items.gold_nugget);
            }
            if (item == LOTRMod.mithrilRing) {
                return new ItemStack(LOTRMod.mithrilNugget);
            }
            if (item == LOTRMod.gobletGold) {
                return new ItemStack(Items.gold_ingot);
            }
            if (item == LOTRMod.gobletSilver) {
                return new ItemStack(LOTRMod.silver);
            }
            if (item == LOTRMod.gobletCopper) {
                return new ItemStack(LOTRMod.copper);
            }
        }
        return null;
    }
    
    private int determineResourcesUsed(final ItemStack itemstack, final ItemStack material) {
        return this.determineResourcesUsed(itemstack, material, null);
    }
    
    private int determineResourcesUsed(final ItemStack itemstack, final ItemStack material, final List<IRecipe> recursiveCheckedRecipes) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_1         /* itemstack */
        //     7: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    10: aload_1         /* itemstack */
        //    11: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //    14: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    17: invokestatic    org/apache/commons/lang3/tuple/Pair.of:(Ljava/lang/Object;Ljava/lang/Object;)Lorg/apache/commons/lang3/tuple/Pair;
        //    20: astore          key
        //    22: getstatic       lotr/common/tileentity/LOTRTileEntityUnsmeltery.unsmeltableCraftingCounts:Ljava/util/Map;
        //    25: aload           key
        //    27: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //    32: ifeq            52
        //    35: getstatic       lotr/common/tileentity/LOTRTileEntityUnsmeltery.unsmeltableCraftingCounts:Ljava/util/Map;
        //    38: aload           key
        //    40: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    45: checkcast       Ljava/lang/Integer;
        //    48: invokevirtual   java/lang/Integer.intValue:()I
        //    51: ireturn        
        //    52: iconst_0       
        //    53: istore          count
        //    55: new             Ljava/util/ArrayList;
        //    58: dup            
        //    59: invokespecial   java/util/ArrayList.<init>:()V
        //    62: astore          allRecipeLists
        //    64: aload           allRecipeLists
        //    66: invokestatic    net/minecraft/item/crafting/CraftingManager.getInstance:()Lnet/minecraft/item/crafting/CraftingManager;
        //    69: invokevirtual   net/minecraft/item/crafting/CraftingManager.getRecipeList:()Ljava/util/List;
        //    72: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    77: pop            
        //    78: aload_0         /* this */
        //    79: invokespecial   lotr/common/tileentity/LOTRTileEntityUnsmeltery.getProxyPlayer:()Lnet/minecraft/entity/player/EntityPlayer;
        //    82: astore          player
        //    84: getstatic       lotr/common/block/LOTRBlockCraftingTable.allCraftingTables:Ljava/util/List;
        //    87: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    92: astore          8
        //    94: aload           8
        //    96: invokeinterface java/util/Iterator.hasNext:()Z
        //   101: ifeq            169
        //   104: aload           8
        //   106: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   111: checkcast       Llotr/common/block/LOTRBlockCraftingTable;
        //   114: astore          table
        //   116: getstatic       lotr/common/LOTRMod.proxy:Llotr/common/LOTRCommonProxy;
        //   119: aload           table
        //   121: getfield        lotr/common/block/LOTRBlockCraftingTable.tableGUIID:I
        //   124: aload           player
        //   126: aload_0         /* this */
        //   127: getfield        net/minecraft/tileentity/TileEntity.worldObj:Lnet/minecraft/world/World;
        //   130: iconst_0       
        //   131: iconst_0       
        //   132: iconst_0       
        //   133: invokevirtual   lotr/common/LOTRCommonProxy.getServerGuiElement:(ILnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;III)Ljava/lang/Object;
        //   136: astore          container
        //   138: aload           container
        //   140: instanceof      Llotr/common/inventory/LOTRContainerCraftingTable;
        //   143: ifeq            166
        //   146: aload           container
        //   148: checkcast       Llotr/common/inventory/LOTRContainerCraftingTable;
        //   151: astore          containerTable
        //   153: aload           allRecipeLists
        //   155: aload           containerTable
        //   157: getfield        lotr/common/inventory/LOTRContainerCraftingTable.recipeList:Ljava/util/List;
        //   160: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   165: pop            
        //   166: goto            94
        //   169: aload           allRecipeLists
        //   171: getstatic       lotr/common/recipe/LOTRRecipes.uncraftableUnsmeltingRecipes:Ljava/util/List;
        //   174: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   179: pop            
        //   180: aload_3         /* recursiveCheckedRecipes */
        //   181: ifnonnull       192
        //   184: new             Ljava/util/ArrayList;
        //   187: dup            
        //   188: invokespecial   java/util/ArrayList.<init>:()V
        //   191: astore_3        /* recursiveCheckedRecipes */
        //   192: aload           allRecipeLists
        //   194: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   199: astore          8
        //   201: aload           8
        //   203: invokeinterface java/util/Iterator.hasNext:()Z
        //   208: ifeq            618
        //   211: aload           8
        //   213: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   218: checkcast       Ljava/util/List;
        //   221: astore          recipes
        //   223: aload           recipes
        //   225: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   230: astore          10
        //   232: aload           10
        //   234: invokeinterface java/util/Iterator.hasNext:()Z
        //   239: ifeq            615
        //   242: aload           10
        //   244: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   249: astore          recipesObj
        //   251: aload           recipesObj
        //   253: checkcast       Lnet/minecraft/item/crafting/IRecipe;
        //   256: astore          irecipe
        //   258: aload_3         /* recursiveCheckedRecipes */
        //   259: aload           irecipe
        //   261: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   266: ifeq            272
        //   269: goto            232
        //   272: aload           irecipe
        //   274: invokeinterface net/minecraft/item/crafting/IRecipe.getRecipeOutput:()Lnet/minecraft/item/ItemStack;
        //   279: astore          result
        //   281: aload           result
        //   283: ifnull          612
        //   286: aload           result
        //   288: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   291: aload_1         /* itemstack */
        //   292: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   295: if_acmpne       612
        //   298: aload_1         /* itemstack */
        //   299: invokevirtual   net/minecraft/item/ItemStack.isItemStackDamageable:()Z
        //   302: ifne            317
        //   305: aload           result
        //   307: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   310: aload_1         /* itemstack */
        //   311: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   314: if_icmpne       612
        //   317: aload_3         /* recursiveCheckedRecipes */
        //   318: aload           irecipe
        //   320: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   325: pop            
        //   326: aload           irecipe
        //   328: instanceof      Lnet/minecraft/item/crafting/ShapedRecipes;
        //   331: ifeq            383
        //   334: aload           irecipe
        //   336: checkcast       Lnet/minecraft/item/crafting/ShapedRecipes;
        //   339: astore          shaped
        //   341: aload           shaped
        //   343: getfield        net/minecraft/item/crafting/ShapedRecipes.recipeItems:[Lnet/minecraft/item/ItemStack;
        //   346: astore          ingredients
        //   348: aload_0         /* this */
        //   349: aload_2         /* material */
        //   350: aload           ingredients
        //   352: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   355: aload_3         /* recursiveCheckedRecipes */
        //   356: invokespecial   lotr/common/tileentity/LOTRTileEntityUnsmeltery.countMatchingIngredients:(Lnet/minecraft/item/ItemStack;Ljava/util/List;Ljava/util/List;)I
        //   359: istore          i
        //   361: iload           i
        //   363: aload           result
        //   365: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   368: idiv           
        //   369: istore          i
        //   371: iload           i
        //   373: ifle            383
        //   376: iload           i
        //   378: istore          count
        //   380: goto            618
        //   383: aload           irecipe
        //   385: instanceof      Lnet/minecraft/item/crafting/ShapelessRecipes;
        //   388: ifeq            437
        //   391: aload           irecipe
        //   393: checkcast       Lnet/minecraft/item/crafting/ShapelessRecipes;
        //   396: astore          shapeless
        //   398: aload           shapeless
        //   400: getfield        net/minecraft/item/crafting/ShapelessRecipes.recipeItems:Ljava/util/List;
        //   403: astore          ingredients
        //   405: aload_0         /* this */
        //   406: aload_2         /* material */
        //   407: aload           ingredients
        //   409: aload_3         /* recursiveCheckedRecipes */
        //   410: invokespecial   lotr/common/tileentity/LOTRTileEntityUnsmeltery.countMatchingIngredients:(Lnet/minecraft/item/ItemStack;Ljava/util/List;Ljava/util/List;)I
        //   413: istore          i
        //   415: iload           i
        //   417: aload           result
        //   419: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   422: idiv           
        //   423: istore          i
        //   425: iload           i
        //   427: ifle            437
        //   430: iload           i
        //   432: istore          count
        //   434: goto            618
        //   437: aload           irecipe
        //   439: instanceof      Lnet/minecraftforge/oredict/ShapedOreRecipe;
        //   442: ifeq            494
        //   445: aload           irecipe
        //   447: checkcast       Lnet/minecraftforge/oredict/ShapedOreRecipe;
        //   450: astore          shaped
        //   452: aload           shaped
        //   454: invokevirtual   net/minecraftforge/oredict/ShapedOreRecipe.getInput:()[Ljava/lang/Object;
        //   457: astore          ingredients
        //   459: aload_0         /* this */
        //   460: aload_2         /* material */
        //   461: aload           ingredients
        //   463: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   466: aload_3         /* recursiveCheckedRecipes */
        //   467: invokespecial   lotr/common/tileentity/LOTRTileEntityUnsmeltery.countMatchingIngredients:(Lnet/minecraft/item/ItemStack;Ljava/util/List;Ljava/util/List;)I
        //   470: istore          i
        //   472: iload           i
        //   474: aload           result
        //   476: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   479: idiv           
        //   480: istore          i
        //   482: iload           i
        //   484: ifle            494
        //   487: iload           i
        //   489: istore          count
        //   491: goto            618
        //   494: aload           irecipe
        //   496: instanceof      Lnet/minecraftforge/oredict/ShapelessOreRecipe;
        //   499: ifeq            548
        //   502: aload           irecipe
        //   504: checkcast       Lnet/minecraftforge/oredict/ShapelessOreRecipe;
        //   507: astore          shapeless
        //   509: aload           shapeless
        //   511: invokevirtual   net/minecraftforge/oredict/ShapelessOreRecipe.getInput:()Ljava/util/ArrayList;
        //   514: astore          ingredients
        //   516: aload_0         /* this */
        //   517: aload_2         /* material */
        //   518: aload           ingredients
        //   520: aload_3         /* recursiveCheckedRecipes */
        //   521: invokespecial   lotr/common/tileentity/LOTRTileEntityUnsmeltery.countMatchingIngredients:(Lnet/minecraft/item/ItemStack;Ljava/util/List;Ljava/util/List;)I
        //   524: istore          i
        //   526: iload           i
        //   528: aload           result
        //   530: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   533: idiv           
        //   534: istore          i
        //   536: iload           i
        //   538: ifle            548
        //   541: iload           i
        //   543: istore          count
        //   545: goto            618
        //   548: aload           irecipe
        //   550: instanceof      Llotr/common/recipe/LOTRRecipePoisonWeapon;
        //   553: ifeq            612
        //   556: aload           irecipe
        //   558: checkcast       Llotr/common/recipe/LOTRRecipePoisonWeapon;
        //   561: astore          poison
        //   563: iconst_1       
        //   564: anewarray       Ljava/lang/Object;
        //   567: dup            
        //   568: iconst_0       
        //   569: aload           poison
        //   571: invokevirtual   lotr/common/recipe/LOTRRecipePoisonWeapon.getInputItem:()Lnet/minecraft/item/ItemStack;
        //   574: aastore        
        //   575: astore          ingredients
        //   577: aload_0         /* this */
        //   578: aload_2         /* material */
        //   579: aload           ingredients
        //   581: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   584: aload_3         /* recursiveCheckedRecipes */
        //   585: invokespecial   lotr/common/tileentity/LOTRTileEntityUnsmeltery.countMatchingIngredients:(Lnet/minecraft/item/ItemStack;Ljava/util/List;Ljava/util/List;)I
        //   588: istore          i
        //   590: iload           i
        //   592: aload           result
        //   594: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   597: idiv           
        //   598: istore          i
        //   600: iload           i
        //   602: ifle            612
        //   605: iload           i
        //   607: istore          count
        //   609: goto            618
        //   612: goto            232
        //   615: goto            201
        //   618: getstatic       lotr/common/tileentity/LOTRTileEntityUnsmeltery.unsmeltableCraftingCounts:Ljava/util/Map;
        //   621: aload           key
        //   623: iload           count
        //   625: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   628: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   633: pop            
        //   634: iload           count
        //   636: ireturn        
        //    Signature:
        //  (Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Ljava/util/List<Lnet/minecraft/item/crafting/IRecipe;>;)I
        //    StackMapTable: 00 11 06 FC 00 2D 07 01 61 FF 00 29 00 09 07 00 02 07 00 88 07 00 88 07 01 83 07 01 61 01 07 01 83 07 01 96 07 01 98 00 00 FB 00 47 FA 00 02 16 FC 00 08 07 01 98 FD 00 1E 07 01 83 07 01 98 FD 00 27 07 01 BD 07 01 B8 FC 00 2C 07 00 88 FB 00 41 35 38 35 F8 00 3F F9 00 02 FA 00 02
        // 
        // The error that occurred was:
        // 
        // java.lang.UnsupportedOperationException: The requested operation is not supported.
        //     at com.strobel.util.ContractUtils.unsupported(ContractUtils.java:27)
        //     at com.strobel.assembler.metadata.TypeReference.getRawType(TypeReference.java:276)
        //     at com.strobel.assembler.metadata.TypeReference.getRawType(TypeReference.java:271)
        //     at com.strobel.assembler.metadata.TypeReference.makeGenericType(TypeReference.java:150)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitParameterizedType(TypeSubstitutionVisitor.java:187)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitParameterizedType(TypeSubstitutionVisitor.java:25)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedGenericType.accept(CoreMetadataFactory.java:653)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visit(TypeSubstitutionVisitor.java:39)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitParameterizedType(TypeSubstitutionVisitor.java:173)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitParameterizedType(TypeSubstitutionVisitor.java:25)
        //     at com.strobel.assembler.metadata.ParameterizedType.accept(ParameterizedType.java:103)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visit(TypeSubstitutionVisitor.java:39)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitParameterizedType(TypeSubstitutionVisitor.java:173)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitParameterizedType(TypeSubstitutionVisitor.java:25)
        //     at com.strobel.assembler.metadata.ParameterizedType.accept(ParameterizedType.java:103)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visit(TypeSubstitutionVisitor.java:39)
        //     at com.strobel.assembler.metadata.TypeSubstitutionVisitor.visitMethod(TypeSubstitutionVisitor.java:276)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2591)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:881)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypesForVariables(TypeAnalysis.java:586)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:397)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private EntityPlayer getProxyPlayer() {
        if (!super.worldObj.isClient) {
            return (EntityPlayer)FakePlayerFactory.get((WorldServer)super.worldObj, new GameProfile((UUID)null, "LOTRUnsmeltery"));
        }
        return LOTRMod.proxy.getClientPlayer();
    }
    
    private int countMatchingIngredients(final ItemStack material, final List ingredientList, final List<IRecipe> recursiveCheckedRecipes) {
        int i = 0;
        for (final Object obj : ingredientList) {
            if (obj instanceof ItemStack) {
                final ItemStack ingredient = (ItemStack)obj;
                if (OreDictionary.itemMatches(material, ingredient, false)) {
                    ++i;
                }
                else {
                    final int sub = this.determineResourcesUsed(ingredient, material, recursiveCheckedRecipes);
                    if (sub <= 0) {
                        continue;
                    }
                    i += sub;
                }
            }
            else {
                if (!(obj instanceof List)) {
                    continue;
                }
                final List<ItemStack> oreIngredients = (List<ItemStack>)obj;
                boolean matched = false;
                for (final ItemStack ingredient2 : oreIngredients) {
                    if (OreDictionary.itemMatches(material, ingredient2, false)) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    ++i;
                }
                else {
                    for (final ItemStack ingredient2 : oreIngredients) {
                        final int sub2 = this.determineResourcesUsed(ingredient2, material, recursiveCheckedRecipes);
                        if (sub2 > 0) {
                            i += sub2;
                            break;
                        }
                    }
                }
            }
        }
        return i;
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        data.setBoolean("Active", this.serverActive);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    @Override
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.clientActive = data.getBoolean("Active");
    }
    
    static {
        LOTRTileEntityUnsmeltery.unsmeltingRand = new Random();
        LOTRTileEntityUnsmeltery.unsmeltableCraftingCounts = new HashMap<Pair<Item, Integer>, Integer>();
    }
}
