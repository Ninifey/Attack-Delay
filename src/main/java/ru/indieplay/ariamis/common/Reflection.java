package ru.indieplay.ariamis.common;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockStem;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Reflection {
    public static void logFailure(final Exception e) {
        System.out.printf(  "LOTRReflection failed");
        throw new RuntimeException(e);
    }

    public static String[] remapMethodNames(final String className, final String... methodNames) {
        final String internalClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap(className.replace('.', '/'));
        final String[] mappedNames = new String[methodNames.length];
        int i = 0;
        for (final String mName : methodNames) {
            mappedNames[i++] = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(internalClassName, mName, (String)null);
        }
        return mappedNames;
    }

    public static <E> Method getPrivateMethod(final Class<? super E> classToAccess, final E instance, final Class[] methodClasses, final String... methodNames) {
        try {
            return ReflectionHelper.findMethod((Class)classToAccess, (Object)instance, remapMethodNames(classToAccess.getName(), methodNames), methodClasses);
        }
        catch (ReflectionHelper.UnableToFindFieldException e) {
            System.out.printf(  "Unable to locate any method %s on type %s", new Object[] { Arrays.toString(methodNames), classToAccess.getName() });
            throw e;
        }
        catch (ReflectionHelper.UnableToAccessFieldException e2) {
            System.out.printf(  "Unable to access any method %s on type %s", new Object[] { Arrays.toString(methodNames), classToAccess.getName() });
            throw e2;
        }
    }

    public static <T, E> void setFinalField(final Class<? super T> classToAccess, final T instance, final E value, String... fieldNames) throws Exception {
        try {
            fieldNames = ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames);
            final Field f = ReflectionHelper.findField((Class)classToAccess, fieldNames);
            setFinalField(classToAccess, instance, value, f);
        }
        catch (Exception e) {
            System.out.printf(  "Unable to access static final field");
            throw e;
        }
    }

    public static <T, E> void setFinalField(final Class<? super T> classToAccess, final T instance, final E value, final Field f) throws Exception {
        try {
            unlockFinalField(f);
            f.set(instance, value);
        }
        catch (Exception e) {
            System.out.printf(  "Unable to access static final field");
            throw e;
        }
    }

    public static void unlockFinalField(final Field f) throws Exception {
        f.setAccessible(true);
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
    }

    public static void testAll(final World world) {
        getHorseJumpStrength();
        getHorseArmorTextures();
        getHorseInv(new EntityHorse(world));
        setupHorseInv(new EntityHorse(world));
        getStemFruitBlock((BlockStem) Blocks.melon_stem);
        getCropItem((BlockCrops)Blocks.potatoes);
        isBadEffect(Potion.poison);
        getHoverEventMappings();
        isFishHookInGround(new EntityFishHook(world));
        getFishHookBobTime(new EntityFishHook(world));
    }

    public static void setWorldInfo(final World world, final WorldInfo newWorldInfo) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)World.class, (Object)world, (Object)newWorldInfo, new String[] { "worldInfo", "field_72986_A" });
        }
        catch (Exception e) {
            logFailure(e);
        }
    }

    public static IAttribute getHorseJumpStrength() {
        try {
            return (IAttribute)ObfuscationReflectionHelper.getPrivateValue((Class)EntityHorse.class, (Object)null, new String[] { "horseJumpStrength", "field_110271_bv" });
        }
        catch (Exception e) {
            logFailure(e);
            return null;
        }
    }

    public static String[] getHorseArmorTextures() {
        try {
            return (String[])ObfuscationReflectionHelper.getPrivateValue((Class)EntityHorse.class, (Object)null, new String[] { "horseArmorTextures", "field_110270_bw" });
        }
        catch (Exception e) {
            logFailure(e);
            return null;
        }
    }

    public static AnimalChest getHorseInv(final EntityHorse horse) {
        try {
            return (AnimalChest)ObfuscationReflectionHelper.getPrivateValue((Class)EntityHorse.class, (Object)horse, new String[] { "horseChest", "field_110296_bG" });
        }
        catch (Exception e) {
            logFailure(e);
            return null;
        }
    }

    public static void setupHorseInv(final EntityHorse horse) {
        try {
            final Method method = getPrivateMethod(EntityHorse.class, horse, new Class[0], "func_110226_cD");
            method.invoke(horse, new Object[0]);
        }
        catch (Exception e) {
            logFailure(e);
        }
    }

    public static Block getStemFruitBlock(final BlockStem block) {
        try {
            return (Block)ObfuscationReflectionHelper.getPrivateValue((Class)BlockStem.class, (Object)block, new String[] { "field_149877_a" });
        }
        catch (Exception e) {
            logFailure(e);
            return null;
        }
    }

    public static Item getCropItem(final BlockCrops block) {
        try {
            final Method method = getPrivateMethod(BlockCrops.class, block, new Class[0], "func_149865_P");
            return (Item)method.invoke(block, new Object[0]);
        }
        catch (Exception e) {
            logFailure(e);
            return null;
        }
    }

    public static boolean isBadEffect(final Potion potion) {
        try {
            return (Boolean) ObfuscationReflectionHelper.getPrivateValue((Class)Potion.class, (Object)potion, new String[] { "isBadEffect", "field_76418_K" });
        }
        catch (Exception e) {
            logFailure(e);
            return false;
        }
    }

    public static Map getHoverEventMappings() {
        try {
            return (Map)ObfuscationReflectionHelper.getPrivateValue((Class) HoverEvent.Action.class, (Object)null, new String[] { "nameMapping", "field_150690_d" });
        }
        catch (Exception e) {
            logFailure(e);
            return null;
        }
    }

    public static void removeCommand(final Class commandClass) {
        try {
            final CommandHandler handler = (CommandHandler) MinecraftServer.getServer().getCommandManager();
            final Map commandMap = handler.getCommands();
            final Set commandSet = (Set)ObfuscationReflectionHelper.getPrivateValue((Class)CommandHandler.class, (Object)handler, new String[] { "commandSet", "field_71561_b" });
            final List mapremoves = new ArrayList();
            for (final Object obj : commandMap.values()) {
                final ICommand command = (ICommand)obj;
                if (command.getClass() == commandClass) {
                    mapremoves.add(command);
                }
            }
            commandMap.values().removeAll(mapremoves);
            final List setremoves = new ArrayList();
            for (final Object obj2 : commandSet) {
                if (obj2.getClass() == commandClass) {
                    setremoves.add(obj2);
                }
            }
            commandSet.removeAll(setremoves);
        }
        catch (Exception e) {
            logFailure(e);
        }
    }

    public static boolean isFishHookInGround(final EntityFishHook fishHook) {
        try {
            return (Boolean)ObfuscationReflectionHelper.getPrivateValue((Class)EntityFishHook.class, (Object)fishHook, new String[] { "field_146051_au" });
        }
        catch (Exception e) {
            logFailure(e);
            return false;
        }
    }

    public static int getFishHookBobTime(final EntityFishHook fishHook) {
        try {
            return (Integer)ObfuscationReflectionHelper.getPrivateValue((Class)EntityFishHook.class, (Object)fishHook, new String[] { "field_146045_ax" });
        }
        catch (Exception e) {
            logFailure(e);
            return 0;
        }
    }

}
