// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.coremod;

import lotr.compatibility.LOTRModChecker;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import java.util.Iterator;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import net.minecraft.launchwrapper.IClassTransformer;

public class LOTRClassTransformer implements IClassTransformer
{
    private static final String cls_AABB = "net/minecraft/util/AxisAlignedBB";
    private static final String cls_AABB_obf = "azt";
    private static final String cls_AttributeModifier = "net/minecraft/entity/ai/attributes/AttributeModifier";
    private static final String cls_AttributeModifier_obf = "tj";
    private static final String cls_Block = "net/minecraft/block/Block";
    private static final String cls_Block_obf = "aji";
    private static final String cls_BlockDoor = "net/minecraft/block/BlockDoor";
    private static final String cls_BlockDoor_obf = "akn";
    private static final String cls_BlockPistonBase = "net/minecraft/block/BlockPistonBase";
    private static final String cls_BlockPistonBase_obf = "app";
    private static final String cls_Blocks = "net/minecraft/init/Blocks";
    private static final String cls_Blocks_obf = "ajn";
    private static final String cls_CreativeTabs = "net/minecraft/creativetab/CreativeTabs";
    private static final String cls_CreativeTabs_obf = "abt";
    private static final String cls_DamageSource = "net/minecraft/util/DamageSource";
    private static final String cls_DamageSource_obf = "ro";
    private static final String cls_Entity = "net/minecraft/entity/Entity";
    private static final String cls_Entity_obf = "sa";
    private static final String cls_EntityLivingBase = "net/minecraft/entity/EntityLivingBase";
    private static final String cls_EntityLivingBase_obf = "sv";
    private static final String cls_EntityPlayer = "net/minecraft/entity/player/EntityPlayer";
    private static final String cls_EntityPlayer_obf = "yz";
    private static final String cls_EnumCreatureAttribute = "net/minecraft/entity/EnumCreatureAttribute";
    private static final String cls_EnumCreatureAttribute_obf = "sz";
    private static final String cls_IBlockAccess = "net/minecraft/world/IBlockAccess";
    private static final String cls_IBlockAccess_obf = "ahl";
    private static final String cls_Item = "net/minecraft/item/Item";
    private static final String cls_Item_obf = "adb";
    private static final String cls_ItemArmor = "net/minecraft/item/ItemArmor";
    private static final String cls_ItemArmor_obf = "abb";
    private static final String cls_ItemStack = "net/minecraft/item/ItemStack";
    private static final String cls_ItemStack_obf = "add";
    private static final String cls_Packet = "net/minecraft/network/Packet";
    private static final String cls_Packet_obf = "ft";
    private static final String cls_PacketS14 = "net/minecraft/network/play/server/S14PacketEntity";
    private static final String cls_PacketS14_obf = "hf";
    private static final String cls_PacketS18 = "net/minecraft/network/play/server/S18PacketEntityTeleport";
    private static final String cls_PacketS18_obf = "ik";
    private static final String cls_PathPoint = "net/minecraft/pathfinding/PathPoint";
    private static final String cls_PathPoint_obf = "aye";
    private static final String cls_World = "net/minecraft/world/World";
    private static final String cls_World_obf = "ahb";
    private static final String cls_WorldServer = "net/minecraft/world/WorldServer";
    private static final String cls_WorldServer_obf = "mt";
    
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (name.equals("alh") || name.equals("net.minecraft.block.BlockGrass")) {
            return this.patchBlockGrass(name, basicClass);
        }
        if (name.equals("akl") || name.equals("net.minecraft.block.BlockDirt")) {
            return this.patchBlockDirt(name, basicClass);
        }
        if (name.equals("ant") || name.equals("net.minecraft.block.BlockStaticLiquid")) {
            return this.patchBlockStaticLiquid(name, basicClass);
        }
        if (name.equals("akz") || name.equals("net.minecraft.block.BlockFence")) {
            return this.patchBlockFence(name, basicClass);
        }
        if (name.equals("aoi") || name.equals("net.minecraft.block.BlockWall")) {
            return this.patchBlockWall(name, basicClass);
        }
        if (name.equals("app") || name.equals("net.minecraft.block.BlockPistonBase")) {
            return this.patchBlockPistonBase(name, basicClass);
        }
        if (name.equals("ajw") || name.equals("net.minecraft.block.BlockCauldron")) {
            return this.patchBlockCauldron(name, basicClass);
        }
        if (name.equals("ajb") || name.equals("net.minecraft.block.BlockAnvil")) {
            return this.patchBlockAnvil(name, basicClass);
        }
        if (name.equals("yz") || name.equals("net.minecraft.entity.player.EntityPlayer")) {
            return this.patchEntityPlayer(name, basicClass);
        }
        if (name.equals("sv") || name.equals("net.minecraft.entity.EntityLivingBase")) {
            return this.patchEntityLivingBase(name, basicClass);
        }
        if (name.equals("wi") || name.equals("net.minecraft.entity.passive.EntityHorse")) {
            return this.patchEntityHorse(name, basicClass);
        }
        if (name.equals("net.minecraftforge.common.ISpecialArmor$ArmorProperties")) {
            return this.patchArmorProperties(name, basicClass);
        }
        if (name.equals("zr") || name.equals("net.minecraft.util.FoodStats")) {
            return this.patchFoodStats(name, basicClass);
        }
        if (name.equals("aho") || name.equals("net.minecraft.world.SpawnerAnimals")) {
            return this.patchSpawnerAnimals(name, basicClass);
        }
        if (name.equals("ayg") || name.equals("net.minecraft.pathfinding.PathFinder")) {
            return this.patchPathFinder(name, basicClass);
        }
        if (name.equals("uc") || name.equals("net.minecraft.entity.ai.EntityAIDoorInteract")) {
            return this.patchDoorInteract(name, basicClass);
        }
        if (name.equals("afv") || name.equals("net.minecraft.enchantment.EnchantmentHelper")) {
            return this.patchEnchantmentHelper(name, basicClass);
        }
        if (name.equals("add") || name.equals("net.minecraft.item.ItemStack")) {
            return this.patchItemStack(name, basicClass);
        }
        if (name.equals("agi") || name.equals("net.minecraft.enchantment.EnchantmentProtection")) {
            return this.patchEnchantmentProtection(name, basicClass);
        }
        if (name.equals("rs") || name.equals("net.minecraft.potion.PotionAttackDamage")) {
            return this.patchPotionDamage(name, basicClass);
        }
        if (name.equals("bjk") || name.equals("net.minecraft.client.entity.EntityClientPlayerMP")) {
            return this.patchEntityClientPlayerMP(name, basicClass);
        }
        if (name.equals("bjb") || name.equals("net.minecraft.client.network.NetHandlerPlayClient")) {
            return this.patchNetHandlerClient(name, basicClass);
        }
        if (name.equals("cpw.mods.fml.common.network.internal.FMLNetworkHandler")) {
            return this.patchFMLNetworkHandler(name, basicClass);
        }
        return basicClass;
    }
    
    private byte[] patchBlockGrass(final String name, final byte[] bytes) {
        final String targetMethodName = "updateTick";
        final String targetMethodNameObf = "func_149674_a";
        final String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
        final String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 3));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 4));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 5));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Grass", "updateTick", "(Lnet/minecraft/world/World;IIILjava/util/Random;)V", false));
                newIns.add((AbstractInsnNode)new InsnNode(177));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockDirt(final String name, final byte[] bytes) {
        final String targetMethodName = "damageDropped";
        final String targetMethodNameObf = "func_149692_a";
        final String targetMethodSign = "(I)I";
        final String targetMethodName2 = "createStackedBlock";
        final String targetMethodNameObf2 = "func_149644_j";
        final String targetMethodSign2 = "(I)Lnet/minecraft/item/ItemStack;";
        final String targetMethodSignObf2 = "(I)Ladd;";
        final String targetMethodName3 = "getSubBlocks";
        final String targetMethodNameObf3 = "func_149666_a";
        final String targetMethodSign3 = "(Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V";
        final String targetMethodSignObf3 = "(Ladb;Labt;Ljava/util/List;)V";
        final String targetMethodName4 = "getDamageValue";
        final String targetMethodNameObf4 = "func_149643_k";
        final String targetMethodSign4 = "(Lnet/minecraft/world/World;III)I";
        final String targetMethodSignObf4 = "(Lahb;III)I";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if (method.name.equals("<clinit>")) {
                final LdcInsnNode nodeNameIndex1 = findNodeInMethod(method, new LdcInsnNode((Object)"default"), 1);
                method.instructions.set((AbstractInsnNode)nodeNameIndex1, (AbstractInsnNode)new LdcInsnNode((Object)LOTRReplacedMethods.Dirt.nameIndex1));
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "damageDropped", "(I)I", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "createStackedBlock", "(Lnet/minecraft/block/Block;I)Lnet/minecraft/item/ItemStack;", false));
                newIns.add((AbstractInsnNode)new InsnNode(176));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && (method.desc.equals(targetMethodSign3) || method.desc.equals(targetMethodSignObf3))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 3));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "getSubBlocks", "(Lnet/minecraft/block/Block;Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V", false));
                newIns.add((AbstractInsnNode)new InsnNode(177));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName4) || method.name.equals(targetMethodNameObf4)) && (method.desc.equals(targetMethodSign4) || method.desc.equals(targetMethodSignObf4))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 3));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 4));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "getDamageValue", "(Lnet/minecraft/world/World;III)I", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockStaticLiquid(final String name, final byte[] bytes) {
        final String targetMethodName = "updateTick";
        final String targetMethodNameObf = "func_149674_a";
        final String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
        final String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 3));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 4));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 5));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$StaticLiquid", "updateTick", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIILjava/util/Random;)V", false));
                newIns.add((AbstractInsnNode)new InsnNode(177));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockFence(final String name, final byte[] bytes) {
        final String targetMethodName = "canConnectFenceTo";
        final String targetMethodNameObf = "func_149826_e";
        final String targetMethodSign = "(Lnet/minecraft/world/IBlockAccess;III)Z";
        final String targetMethodSignObf = "(Lahl;III)Z";
        final String targetMethodNameObf2;
        final String targetMethodName2 = targetMethodNameObf2 = "func_149825_a";
        final String targetMethodSign2 = "(Lnet/minecraft/block/Block;)Z";
        final String targetMethodSignObf2 = "(Laji;)Z";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 3));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 4));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Fence", "canConnectFenceTo", "(Lnet/minecraft/world/IBlockAccess;III)Z", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Fence", "canPlacePressurePlate", "(Lnet/minecraft/block/Block;)Z", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockWall(final String name, final byte[] bytes) {
        final String targetMethodName = "canConnectWallTo";
        final String targetMethodNameObf = "func_150091_e";
        final String targetMethodSign = "(Lnet/minecraft/world/IBlockAccess;III)Z";
        final String targetMethodSignObf = "(Lahl;III)Z";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 3));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 4));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Wall", "canConnectWallTo", "(Lnet/minecraft/world/IBlockAccess;III)Z", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockPistonBase(final String name, final byte[] bytes) {
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            int skip = 0;
            while (true) {
                MethodInsnNode nodeFound = null;
            Label_0363:
                for (final boolean pistonObf : new boolean[] { false, true }) {
                    for (final boolean canPushObf : new boolean[] { false, true }) {
                        for (final boolean blockObf : new boolean[] { false, true }) {
                            for (final boolean worldObf : new boolean[] { false, true }) {
                                final String _piston = pistonObf ? "app" : "net/minecraft/block/BlockPistonBase";
                                final String _canPush = canPushObf ? "func_150080_a" : "canPushBlock";
                                final String _block = blockObf ? "aji" : "net/minecraft/block/Block";
                                final String _world = worldObf ? "ahb" : "net/minecraft/world/World";
                                final MethodInsnNode nodeInvokeCanPush = new MethodInsnNode(184, _piston, _canPush, "(L" + _block + ";L" + _world + ";IIIZ)Z", false);
                                nodeFound = findNodeInMethod(method, nodeInvokeCanPush, skip);
                                if (nodeFound != null) {
                                    break Label_0363;
                                }
                            }
                        }
                    }
                }
                if (nodeFound == null) {
                    break;
                }
                nodeFound.setOpcode(184);
                nodeFound.owner = "lotr/common/coremod/LOTRReplacedMethods$Piston";
                nodeFound.name = "canPushBlock";
                nodeFound.desc = "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIIZ)Z";
                nodeFound.itf = false;
                ++skip;
            }
            if (skip > 0) {
                System.out.println("LOTRCore: Patched method " + method.name + " " + skip + " times");
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockCauldron(final String name, final byte[] bytes) {
        final String targetMethodName = "getRenderType";
        final String targetMethodNameObf = "func_149645_b";
        final String targetMethodSign = "()I";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Cauldron", "getRenderType", "()I", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchBlockAnvil(final String name, final byte[] bytes) {
        final boolean isObf = !name.startsWith("net.minecraft");
        final String targetMethodName = "getCollisionBoundingBoxFromPool";
        final String targetMethodNameObf = "func_149668_a";
        final String targetMethodDescObf;
        final String targetMethodDesc = targetMethodDescObf = "(Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;";
        final String targetMethodSignObf;
        final String targetMethodSign = targetMethodSignObf = "Lnet/minecraft/world/World;III";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        MethodNode newMethod;
        if (isObf) {
            newMethod = new MethodNode(1, targetMethodNameObf, targetMethodDescObf, targetMethodSignObf, (String[])null);
        }
        else {
            newMethod = new MethodNode(1, targetMethodName, targetMethodDesc, targetMethodSign, (String[])null);
        }
        newMethod.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
        newMethod.instructions.add((AbstractInsnNode)new VarInsnNode(25, 1));
        newMethod.instructions.add((AbstractInsnNode)new VarInsnNode(21, 2));
        newMethod.instructions.add((AbstractInsnNode)new VarInsnNode(21, 3));
        newMethod.instructions.add((AbstractInsnNode)new VarInsnNode(21, 4));
        newMethod.instructions.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Anvil", "getCollisionBoundingBoxFromPool", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;", false));
        newMethod.instructions.add((AbstractInsnNode)new InsnNode(176));
        classNode.methods.add(newMethod);
        System.out.println("LOTRCore: Added method " + newMethod.name);
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchEntityPlayer(final String name, final byte[] bytes) {
        final String targetMethodName = "canEat";
        final String targetMethodNameObf = "func_71043_e";
        final String targetMethodSign = "(Z)Z";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Player", "canEat", "(Lnet/minecraft/entity/player/EntityPlayer;Z)Z", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchEntityLivingBase(final String name, final byte[] bytes) {
        final String targetMethodName = "getTotalArmorValue";
        final String targetMethodNameObf = "func_70658_aO";
        final String targetMethodSign = "()I";
        final String targetMethodName2 = "onDeath";
        final String targetMethodNameObf2 = "func_70645_a";
        final String targetMethodSign2 = "(Lnet/minecraft/util/DamageSource;)V";
        final String targetMethodSignObf2 = "(Lro;)V";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                final VarInsnNode nodeStore = findNodeInMethod(method, new VarInsnNode(54, 6));
                for (int l = 0; l < 3; ++l) {
                    method.instructions.remove(nodeStore.getPrevious());
                }
                final AbstractInsnNode newPrev = nodeStore.getPrevious();
                if (!(newPrev instanceof VarInsnNode) || ((VarInsnNode)newPrev).getOpcode() != 25 || ((VarInsnNode)newPrev).var != 5) {
                    System.out.println("WARNING! Expected ALOAD 5! Instead got " + newPrev);
                    System.out.println("WARNING! Things may break!");
                }
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeStore, newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
                TypeInsnNode nodeIsInstance = null;
                for (final boolean playerObf : new boolean[] { false, true }) {
                    nodeIsInstance = findNodeInMethod(method, new TypeInsnNode(193, playerObf ? "yz" : "net/minecraft/entity/player/EntityPlayer"));
                    if (nodeIsInstance != null) {
                        break;
                    }
                }
                final VarInsnNode nodeLoadEntity = (VarInsnNode)nodeIsInstance.getPrevious();
                method.instructions.remove((AbstractInsnNode)nodeIsInstance);
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "isPlayerMeleeKill", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/DamageSource;)Z", false));
                method.instructions.insert((AbstractInsnNode)nodeLoadEntity, newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchEntityHorse(final String name, final byte[] bytes) {
        final String targetMethodName = "moveEntityWithHeading";
        final String targetMethodNameObf = "func_70612_e";
        final String targetMethodSign = "(FF)V";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                FieldInsnNode nodeIsRemote = null;
            Label_0263:
                for (final boolean worldObf : new boolean[] { false, true }) {
                    for (final boolean isRemoteObf : new boolean[] { false, true }) {
                        final String _world = worldObf ? "ahb" : "net/minecraft/world/World";
                        final String _remote = isRemoteObf ? "field_72995_K" : "isRemote";
                        nodeIsRemote = findNodeInMethod(method, new FieldInsnNode(180, _world, _remote, "Z"));
                        if (nodeIsRemote != null) {
                            break Label_0263;
                        }
                    }
                }
                final VarInsnNode nodeLoadThisEntity = (VarInsnNode)nodeIsRemote.getPrevious().getPrevious();
                for (int l = 0; l < 2; ++l) {
                    method.instructions.remove(nodeLoadThisEntity.getNext());
                }
                final JumpInsnNode nodeIfTest = (JumpInsnNode)nodeLoadThisEntity.getNext();
                if (nodeIfTest.getOpcode() == 154) {
                    nodeIfTest.setOpcode(153);
                }
                else {
                    System.out.println("WARNING! Expected IFNE! Instead got " + nodeIfTest.getOpcode());
                    System.out.println("WARNING! Things may break!");
                }
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/entity/LOTRMountFunctions", "canRiderControl_elseNoMotion", "(Lnet/minecraft/entity/EntityLiving;)Z", false));
                method.instructions.insert((AbstractInsnNode)nodeLoadThisEntity, newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchArmorProperties(final String name, final byte[] bytes) {
        final boolean isCauldron = LOTRModChecker.isCauldronServer();
        String targetMethodNameObf;
        String targetMethodName = targetMethodNameObf = "ApplyArmor";
        String targetMethodSignObf;
        String targetMethodSign = targetMethodSignObf = "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;D)F";
        if (isCauldron) {
            targetMethodNameObf = (targetMethodName = "ApplyArmor");
            targetMethodSignObf = (targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;DZ)F");
        }
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                FieldInsnNode nodeFound = null;
            Label_0294:
                for (final boolean armorObf : new boolean[] { false, true }) {
                    for (int dmgObf = 0; dmgObf < 3; ++dmgObf) {
                        final String _armor = armorObf ? "abb" : "net/minecraft/item/ItemArmor";
                        final String _dmg = (new String[] { "field_77879_b", "damageReduceAmount", "c" })[dmgObf];
                        final FieldInsnNode nodeDmg = new FieldInsnNode(180, _armor, _dmg, "I");
                        nodeFound = findNodeInMethod(method, nodeDmg);
                        if (nodeFound != null) {
                            break Label_0294;
                        }
                    }
                }
                final AbstractInsnNode nodePrev = nodeFound.getPrevious();
                if (!(nodePrev instanceof VarInsnNode) || ((VarInsnNode)nodePrev).getOpcode() != 25 || ((VarInsnNode)nodePrev).var != 9) {
                    System.out.println("WARNING! Expected ALOAD 9! Instead got " + nodePrev);
                    System.out.println("WARNING! Things may break!");
                }
                method.instructions.remove(nodePrev);
                final InsnList newIns = new InsnList();
                if (!isCauldron) {
                    newIns.add((AbstractInsnNode)new VarInsnNode(25, 7));
                    newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
                }
                else {
                    newIns.add((AbstractInsnNode)new VarInsnNode(25, 8));
                    newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
                }
                method.instructions.insert((AbstractInsnNode)nodeFound, newIns);
                method.instructions.remove((AbstractInsnNode)nodeFound);
                if (!isCauldron) {
                    System.out.println("LOTRCore: Patched method " + method.name);
                }
                else {
                    System.out.println("LOTRCore: Patched method " + method.name + " for Cauldron");
                }
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchFoodStats(final String name, final byte[] bytes) {
        final String targetMethodName = "addExhaustion";
        final String targetMethodNameObf = "func_75113_a";
        final String targetMethodSign = "(F)V";
        final String targetMethodName2 = "needFood";
        final String targetMethodNameObf2 = "func_75121_c";
        final String targetMethodSign2 = "()Z";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(23, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Food", "getExhaustionFactor", "()F", false));
                newIns.add((AbstractInsnNode)new InsnNode(106));
                newIns.add((AbstractInsnNode)new VarInsnNode(56, 1));
                final VarInsnNode nodeAfter = findNodeInMethod(method, new VarInsnNode(25, 0));
                method.instructions.insertBefore((AbstractInsnNode)nodeAfter, newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchSpawnerAnimals(final String name, final byte[] bytes) {
        final String targetMethodName = "findChunksForSpawning";
        final String targetMethodNameObf = "func_77192_a";
        final String targetMethodSign = "(Lnet/minecraft/world/WorldServer;ZZZ)I";
        final String targetMethodSignObf = "(Lmt;ZZZ)I";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                method.tryCatchBlocks.clear();
                method.localVariables.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 2));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 3));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 4));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Spawner", "performSpawning", "(Lnet/minecraft/world/WorldServer;ZZZ)I", false));
                newIns.add((AbstractInsnNode)new InsnNode(172));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchPathFinder(final String name, final byte[] bytes) {
        final String targetMethodNameObf;
        final String targetMethodName = targetMethodNameObf = "func_82565_a";
        final String targetMethodSign = "(Lnet/minecraft/entity/Entity;IIILnet/minecraft/pathfinding/PathPoint;ZZZ)I";
        final String targetMethodSignObf = "(Lsa;IIILaye;ZZZ)I";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                FieldInsnNode nodeFound1 = null;
                FieldInsnNode nodeFound2 = null;
            Label_0383:
                for (int pass = 0; pass <= 1; ++pass) {
                    for (final boolean blocksObf : new boolean[] { false, true }) {
                        for (final boolean doorObf : new boolean[] { false, true }) {
                            for (final boolean blockObf : new boolean[] { false, true }) {
                                final String _blocks = blocksObf ? "ajn" : "net/minecraft/init/Blocks";
                                final String _door = doorObf ? "field_150466_ao" : "wooden_door";
                                final String _block = blockObf ? "aji" : "net/minecraft/block/Block";
                                final FieldInsnNode nodeGetDoor = new FieldInsnNode(178, _blocks, _door, "Lnet/minecraft/block/Block;");
                                if (pass == 0) {
                                    nodeFound1 = findNodeInMethod(method, nodeGetDoor, 0);
                                    if (nodeFound1 != null) {
                                        continue Label_0383;
                                    }
                                }
                                else if (pass == 1) {
                                    nodeFound2 = findNodeInMethod(method, nodeGetDoor, 1);
                                    if (nodeFound2 != null) {
                                        continue Label_0383;
                                    }
                                }
                            }
                        }
                    }
                }
                final MethodInsnNode nodeCheckDoor1 = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
                method.instructions.set((AbstractInsnNode)nodeFound1, (AbstractInsnNode)nodeCheckDoor1);
                final JumpInsnNode nodeIf1 = (JumpInsnNode)nodeCheckDoor1.getNext();
                nodeIf1.setOpcode(153);
                final MethodInsnNode nodeCheckDoor2 = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
                method.instructions.set((AbstractInsnNode)nodeFound2, (AbstractInsnNode)nodeCheckDoor2);
                final JumpInsnNode nodeIf2 = (JumpInsnNode)nodeCheckDoor2.getNext();
                if (nodeIf2.getOpcode() != 165) {
                    System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
                    System.out.println("WARNING! INSTEAD IT WAS " + nodeIf2.getOpcode());
                    if (nodeIf2.getOpcode() == 166) {
                        System.out.println("WARNING! Opcode is IF_ACMPNE instead of expected IF_ACMPEQ, so setting it to IFEQ instead of IFNE");
                        System.out.println("WARNING! Hopefully this works...");
                        nodeIf2.setOpcode(153);
                    }
                    else {
                        System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
                    }
                }
                else {
                    nodeIf2.setOpcode(154);
                }
                FieldInsnNode nodeFoundGate = null;
            Label_0805:
                for (final boolean blocksObf2 : new boolean[] { false, true }) {
                    for (final boolean gateObf : new boolean[] { false, true }) {
                        for (final boolean blockObf2 : new boolean[] { false, true }) {
                            final String _blocks2 = blocksObf2 ? "ajn" : "net/minecraft/init/Blocks";
                            final String _gate = gateObf ? "field_150396_be" : "fence_gate";
                            final String _block2 = blockObf2 ? "aji" : "net/minecraft/block/Block";
                            final FieldInsnNode nodeGetGate = new FieldInsnNode(178, _blocks2, _gate, "Lnet/minecraft/block/Block;");
                            nodeFoundGate = findNodeInMethod(method, nodeGetGate, 0);
                            if (nodeFoundGate != null) {
                                break Label_0805;
                            }
                        }
                    }
                }
                final MethodInsnNode nodeCheckGate = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isFenceGate", "(Lnet/minecraft/block/Block;)Z", false);
                method.instructions.set((AbstractInsnNode)nodeFoundGate, (AbstractInsnNode)nodeCheckGate);
                final JumpInsnNode nodeIfGate = (JumpInsnNode)nodeCheckGate.getNext();
                if (nodeIfGate.getOpcode() != 165) {
                    System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
                    System.out.println("WARNING! INSTEAD IT WAS " + nodeIfGate.getOpcode());
                    System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
                }
                else {
                    nodeIfGate.setOpcode(154);
                }
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchDoorInteract(final String name, final byte[] bytes) {
        final String targetMethodNameObf;
        final String targetMethodName = targetMethodNameObf = "func_151503_a";
        final String targetMethodSign = "(III)Lnet/minecraft/block/BlockDoor;";
        final String targetMethodSignObf = "(III)Lakn;";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                FieldInsnNode nodeFound = null;
            Label_0338:
                for (final boolean blocksObf : new boolean[] { false, true }) {
                    for (final boolean doorObf : new boolean[] { false, true }) {
                        for (final boolean blockObf : new boolean[] { false, true }) {
                            final String _blocks = blocksObf ? "ajn" : "net/minecraft/init/Blocks";
                            final String _door = doorObf ? "field_150466_ao" : "wooden_door";
                            final String _block = blockObf ? "aji" : "net/minecraft/block/Block";
                            final FieldInsnNode nodeGetDoor = new FieldInsnNode(178, _blocks, _door, "Lnet/minecraft/block/Block;");
                            nodeFound = findNodeInMethod(method, nodeGetDoor);
                            if (nodeFound != null) {
                                break Label_0338;
                            }
                        }
                    }
                }
                final MethodInsnNode nodeCheckDoor = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
                method.instructions.set((AbstractInsnNode)nodeFound, (AbstractInsnNode)nodeCheckDoor);
                final JumpInsnNode nodeIf = (JumpInsnNode)nodeCheckDoor.getNext();
                if (nodeIf.getOpcode() != 165) {
                    System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
                    System.out.println("WARNING! INSTEAD IT WAS " + nodeIf.getOpcode());
                    System.out.println("WARNING! Setting it to IF_NE anyway");
                }
                nodeIf.setOpcode(154);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchEnchantmentHelper(final String name, final byte[] bytes) {
        final String targetMethodName = "getEnchantmentModifierLiving";
        final String targetMethodNameObf = "func_77512_a";
        final String targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)F";
        final String targetMethodSignObf = "(Lsv;Lsv;)F";
        final String targetMethodNameObf2;
        final String targetMethodName2 = targetMethodNameObf2 = "func_152377_a";
        final String targetMethodSign2 = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EnumCreatureAttribute;)F";
        final String targetMethodSignObf2 = "(Ladd;Lsz;)F";
        final String targetMethodName3 = "getSilkTouchModifier";
        final String targetMethodNameObf3 = "func_77502_d";
        final String targetMethodSign3 = "(Lnet/minecraft/entity/EntityLivingBase;)Z";
        final String targetMethodSignObf3 = "(Lsv;)Z";
        final String targetMethodName4 = "getKnockbackModifier";
        final String targetMethodNameObf4 = "func_77507_b";
        final String targetMethodSign4 = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)I";
        final String targetMethodSignObf4 = "(Lsv;Lsv;)I";
        final String targetMethodName5 = "getFortuneModifier";
        final String targetMethodNameObf5 = "func_77517_e";
        final String targetMethodSign5 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
        final String targetMethodSignObf5 = "(Lsv;)I";
        final String targetMethodName6 = "getLootingModifier";
        final String targetMethodNameObf6 = "func_77519_f";
        final String targetMethodSign6 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
        final String targetMethodSignObf6 = "(Lsv;)I";
        final String targetMethodName7 = "getEnchantmentModifierDamage";
        final String targetMethodNameObf7 = "func_77508_a";
        final String targetMethodSign7 = "([Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;)I";
        final String targetMethodSignObf7 = "([Ladd;Lro;)I";
        final String targetMethodName8 = "getFireAspectModifier";
        final String targetMethodNameObf8 = "func_90036_a";
        final String targetMethodSign8 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
        final String targetMethodSignObf8 = "(Lsv;)I";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(174));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getEnchantmentModifierLiving", "(FLnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)F", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(174));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "func_152377_a", "(FLnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EnumCreatureAttribute;)F", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && (method.desc.equals(targetMethodSign3) || method.desc.equals(targetMethodSignObf3))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(172));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getSilkTouchModifier", "(ZLnet/minecraft/entity/EntityLivingBase;)Z", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName4) || method.name.equals(targetMethodNameObf4)) && (method.desc.equals(targetMethodSign4) || method.desc.equals(targetMethodSignObf4))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(172));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getKnockbackModifier", "(ILnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName5) || method.name.equals(targetMethodNameObf5)) && (method.desc.equals(targetMethodSign5) || method.desc.equals(targetMethodSignObf5))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(172));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getFortuneModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName6) || method.name.equals(targetMethodNameObf6)) && (method.desc.equals(targetMethodSign6) || method.desc.equals(targetMethodSignObf6))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(172));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getLootingModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName7) || method.name.equals(targetMethodNameObf7)) && (method.desc.equals(targetMethodSign7) || method.desc.equals(targetMethodSignObf7))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(172));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getSpecialArmorProtection", "(I[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName8) || method.name.equals(targetMethodNameObf8)) && (method.desc.equals(targetMethodSign8) || method.desc.equals(targetMethodSignObf8))) {
                final InsnNode nodeReturn = findNodeInMethod(method, new InsnNode(172));
                final InsnList extraIns = new InsnList();
                extraIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                extraIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getFireAspectModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeReturn, extraIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchItemStack(final String name, final byte[] bytes) {
        final boolean isCauldron = LOTRModChecker.isCauldronServer();
        String targetMethodName = "attemptDamageItem";
        String targetMethodNameObf = "func_96631_a";
        String targetMethodSignObf;
        String targetMethodSign = targetMethodSignObf = "(ILjava/util/Random;)Z";
        if (isCauldron) {
            targetMethodNameObf = (targetMethodName = "isDamaged");
            targetMethodSign = "(ILjava/util/Random;Lnet/minecraft/entity/EntityLivingBase;)Z";
            targetMethodSignObf = "(ILjava/util/Random;Lsv;)Z";
        }
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                if (!isCauldron) {
                    method.instructions.clear();
                    final InsnList newIns = new InsnList();
                    newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                    newIns.add((AbstractInsnNode)new VarInsnNode(21, 1));
                    newIns.add((AbstractInsnNode)new VarInsnNode(25, 2));
                    newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "attemptDamageItem", "(Lnet/minecraft/item/ItemStack;ILjava/util/Random;)Z", false));
                    newIns.add((AbstractInsnNode)new InsnNode(172));
                    method.instructions.insert(newIns);
                    System.out.println("LOTRCore: Patched method " + method.name);
                }
                else {
                    for (final AbstractInsnNode n : method.instructions.toArray()) {
                        if (n.getOpcode() == 100) {
                            final InsnList insns = new InsnList();
                            insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                            insns.add((AbstractInsnNode)new VarInsnNode(21, 1));
                            insns.add((AbstractInsnNode)new VarInsnNode(25, 2));
                            insns.add((AbstractInsnNode)new VarInsnNode(25, 3));
                            insns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "c_attemptDamageItem", "(ILnet/minecraft/item/ItemStack;ILjava/util/Random;Lnet/minecraft/entity/EntityLivingBase;)I", false));
                            method.instructions.insert(n, insns);
                            System.out.println("LOTRCore: Patched method " + method.name + " for Cauldron");
                        }
                    }
                }
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchEnchantmentProtection(final String name, final byte[] bytes) {
        final String targetMethodName = "getFireTimeForEntity";
        final String targetMethodNameObf = "func_92093_a";
        final String targetMethodSign = "(Lnet/minecraft/entity/Entity;I)I";
        final String targetMethodSignObf = "(Lsa;I)I";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                final VarInsnNode nodeIStore = findNodeInMethod(method, new VarInsnNode(54, 2));
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getMaxFireProtectionLevel", "(ILnet/minecraft/entity/Entity;)I", false));
                method.instructions.insertBefore((AbstractInsnNode)nodeIStore, newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchPotionDamage(final String name, final byte[] bytes) {
        final String targetMethodNameObf;
        final String targetMethodName = targetMethodNameObf = "func_111183_a";
        final String targetMethodSign = "(ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D";
        final String targetMethodSignObf = "(ILtj;)D";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(21, 1));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 2));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Potions", "getStrengthModifier", "(Lnet/minecraft/potion/Potion;ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D", false));
                newIns.add((AbstractInsnNode)new InsnNode(175));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchEntityClientPlayerMP(final String name, final byte[] bytes) {
        final String targetMethodNameObf;
        final String targetMethodName = targetMethodNameObf = "func_110318_g";
        final String targetMethodSign = "()V";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$ClientPlayer", "horseJump", "(Lnet/minecraft/client/entity/EntityClientPlayerMP;)V", false));
                newIns.add((AbstractInsnNode)new InsnNode(177));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchNetHandlerClient(final String name, final byte[] bytes) {
        final String targetMethodName = "handleEntityTeleport";
        final String targetMethodNameObf = "func_147275_a";
        final String targetMethodSign = "(Lnet/minecraft/network/play/server/S18PacketEntityTeleport;)V";
        final String targetMethodSignObf = "(Lik;)V";
        final String targetMethodName2 = "handleEntityMovement";
        final String targetMethodNameObf2 = "func_147259_a";
        final String targetMethodSign2 = "(Lnet/minecraft/network/play/server/S14PacketEntity;)V";
        final String targetMethodSignObf2 = "(Lhf;)V";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$NetHandlerClient", "handleEntityTeleport", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/network/play/server/S18PacketEntityTeleport;)V", false));
                newIns.add((AbstractInsnNode)new InsnNode(177));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
            if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 1));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$NetHandlerClient", "handleEntityMovement", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/network/play/server/S14PacketEntity;)V", false));
                newIns.add((AbstractInsnNode)new InsnNode(177));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private byte[] patchFMLNetworkHandler(final String name, final byte[] bytes) {
        final String targetMethodNameObf;
        final String targetMethodName = targetMethodNameObf = "getEntitySpawningPacket";
        final String targetMethodSign = "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;";
        final String targetMethodSignObf = "(Lsa;)Lft;";
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        for (final MethodNode method : classNode.methods) {
            if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
                method.instructions.clear();
                final InsnList newIns = new InsnList();
                newIns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                newIns.add((AbstractInsnNode)new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$EntityPackets", "getMobSpawnPacket", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;", false));
                newIns.add((AbstractInsnNode)new InsnNode(176));
                method.instructions.insert(newIns);
                System.out.println("LOTRCore: Patched method " + method.name);
            }
        }
        final ClassWriter writer = new ClassWriter(1);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    private static <N extends AbstractInsnNode> N findNodeInMethod(final MethodNode method, final N target) {
        return findNodeInMethod(method, target, 0);
    }
    
    private static <N extends AbstractInsnNode> N findNodeInMethod(final MethodNode method, final N targetAbstract, final int skip) {
        int skipped = 0;
        for (final AbstractInsnNode nextAbstract : method.instructions) {
            boolean matched = false;
            if (nextAbstract.getClass() == targetAbstract.getClass()) {
                if (targetAbstract.getClass() == InsnNode.class) {
                    final InsnNode next = (InsnNode)nextAbstract;
                    final InsnNode target = (InsnNode)targetAbstract;
                    if (next.getOpcode() == target.getOpcode()) {
                        matched = true;
                    }
                }
                else if (targetAbstract.getClass() == VarInsnNode.class) {
                    final VarInsnNode next2 = (VarInsnNode)nextAbstract;
                    final VarInsnNode target2 = (VarInsnNode)targetAbstract;
                    if (next2.getOpcode() == target2.getOpcode() && next2.var == target2.var) {
                        matched = true;
                    }
                }
                else if (targetAbstract.getClass() == LdcInsnNode.class) {
                    final LdcInsnNode next3 = (LdcInsnNode)nextAbstract;
                    final LdcInsnNode target3 = (LdcInsnNode)targetAbstract;
                    if (next3.cst.equals(target3.cst)) {
                        matched = true;
                    }
                }
                else if (targetAbstract.getClass() == TypeInsnNode.class) {
                    final TypeInsnNode next4 = (TypeInsnNode)nextAbstract;
                    final TypeInsnNode target4 = (TypeInsnNode)targetAbstract;
                    if (next4.getOpcode() == target4.getOpcode() && next4.desc.equals(target4.desc)) {
                        matched = true;
                    }
                }
                else if (targetAbstract.getClass() == FieldInsnNode.class) {
                    final FieldInsnNode next5 = (FieldInsnNode)nextAbstract;
                    final FieldInsnNode target5 = (FieldInsnNode)targetAbstract;
                    if (next5.getOpcode() == target5.getOpcode() && next5.owner.equals(target5.owner) && next5.name.equals(target5.name) && next5.desc.equals(target5.desc)) {
                        matched = true;
                    }
                }
                else if (targetAbstract.getClass() == MethodInsnNode.class) {
                    final MethodInsnNode next6 = (MethodInsnNode)nextAbstract;
                    final MethodInsnNode target6 = (MethodInsnNode)targetAbstract;
                    if (next6.getOpcode() == target6.getOpcode() && next6.owner.equals(target6.owner) && next6.name.equals(target6.name) && next6.desc.equals(target6.desc) && next6.itf == target6.itf) {
                        matched = true;
                    }
                }
            }
            if (matched) {
                if (skipped >= skip) {
                    return (N)nextAbstract;
                }
                ++skipped;
            }
        }
        return null;
    }
}
