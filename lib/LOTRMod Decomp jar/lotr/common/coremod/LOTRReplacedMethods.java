// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.coremod;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import lotr.common.util.LOTRLog;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import io.netty.buffer.Unpooled;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.network.play.server.S14PacketEntity;
import lotr.common.entity.LOTRMountFunctions;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureAttribute;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.block.BlockDoor;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.world.WorldServer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRConfig;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRReflection;
import net.minecraft.entity.Entity;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFence;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRReplacedMethods
{
    public static class Grass
    {
        public static final int MIN_GRASS_LIGHT = 4;
        public static final int MAX_GRASS_OPACITY = 2;
        public static final int MIN_SPREAD_LIGHT = 9;
        
        public static void updateTick(final World world, final int i, final int j, final int k, final Random random) {
            if (!world.isClient) {
                final int checkRange = 1;
                if (!world.checkChunksExist(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
                    return;
                }
                if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlockLightOpacity(i, j + 1, k) > 2) {
                    final Block block = world.getBlock(i, j, k);
                    if (block == Blocks.grass) {
                        world.setBlock(i, j, k, Blocks.dirt);
                    }
                    if (block == LOTRMod.mudGrass) {
                        world.setBlock(i, j, k, LOTRMod.mud);
                    }
                }
                else if (world.getBlockLightValue(i, j + 1, k) >= 9) {
                    for (int l = 0; l < 4; ++l) {
                        final int i2 = i + random.nextInt(3) - 1;
                        final int j2 = j + random.nextInt(5) - 3;
                        final int k2 = k + random.nextInt(3) - 1;
                        if (world.blockExists(i2, j2, k2) && world.checkChunksExist(i2 - checkRange, j2 - checkRange, k2 - checkRange, i2 + checkRange, j2 + checkRange, k2 + checkRange) && world.getBlockLightValue(i2, j2 + 1, k2) >= 4 && world.getBlockLightOpacity(i2, j2 + 1, k2) <= 2) {
                            final Block block2 = world.getBlock(i2, j2, k2);
                            final int meta = world.getBlockMetadata(i2, j2, k2);
                            if (block2 == Blocks.dirt && meta == 0) {
                                world.setBlock(i2, j2, k2, (Block)Blocks.grass, 0, 3);
                            }
                            if (block2 == LOTRMod.mud && meta == 0) {
                                world.setBlock(i2, j2, k2, LOTRMod.mudGrass, 0, 3);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static class Dirt
    {
        public static String nameIndex1;
        
        public static int damageDropped(final int i) {
            if (i == 1) {
                return 1;
            }
            return 0;
        }
        
        public static ItemStack createStackedBlock(final Block thisBlock, final int i) {
            final Item item = Item.getItemFromBlock(thisBlock);
            return new ItemStack(item, 1, i);
        }
        
        public static void getSubBlocks(final Block thisBlock, final Item item, final CreativeTabs tab, final List list) {
            list.add(new ItemStack(thisBlock, 1, 0));
            list.add(new ItemStack(thisBlock, 1, 1));
            list.add(new ItemStack(thisBlock, 1, 2));
        }
        
        public static int getDamageValue(final World world, final int i, final int j, final int k) {
            final int meta = world.getBlockMetadata(i, j, k);
            return meta;
        }
        
        static {
            Dirt.nameIndex1 = "coarse";
        }
    }
    
    public static class StaticLiquid
    {
        public static void updateTick(final Block thisBlock, final World world, int i, int j, int k, final Random random) {
            if (thisBlock.getMaterial() == Material.lava) {
                final int tries = random.nextInt(3);
                for (int l = 0; l < tries; ++l) {
                    i += random.nextInt(3) - 1;
                    k += random.nextInt(3) - 1;
                    ++j;
                    if (!world.blockExists(i, j, k)) {
                        return;
                    }
                    final Block block = world.getBlock(i, j, k);
                    if (block.getMaterial() == Material.air) {
                        if (isFlammable(world, i - 1, j, k) || isFlammable(world, i + 1, j, k) || isFlammable(world, i, j, k - 1) || isFlammable(world, i, j, k + 1) || isFlammable(world, i, j - 1, k) || isFlammable(world, i, j + 1, k)) {
                            world.setBlock(i, j, k, (Block)Blocks.fire);
                            return;
                        }
                    }
                    else if (block.getMaterial().blocksMovement()) {
                        return;
                    }
                }
                if (tries == 0) {
                    final int i2 = i;
                    final int k2 = k;
                    for (int m = 0; m < 3; ++m) {
                        i = i2 + random.nextInt(3) - 1;
                        k = k2 + random.nextInt(3) - 1;
                        if (world.blockExists(i, j, k) && world.isAirBlock(i, j + 1, k) && isFlammable(world, i, j, k)) {
                            world.setBlock(i, j + 1, k, (Block)Blocks.fire);
                        }
                    }
                }
            }
        }
        
        private static boolean isFlammable(final World world, final int i, final int j, final int k) {
            return world.blockExists(i, j, k) && world.getBlock(i, j, k).getMaterial().getCanBurn();
        }
    }
    
    public static class Fence
    {
        public static boolean canConnectFenceTo(final IBlockAccess world, final int i, final int j, final int k) {
            final Block block = world.getBlock(i, j, k);
            return block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall || (block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.field_151572_C);
        }
        
        public static boolean canPlacePressurePlate(final Block block) {
            return block instanceof BlockFence;
        }
    }
    
    public static class Wall
    {
        public static boolean canConnectWallTo(final IBlockAccess world, final int i, final int j, final int k) {
            return Fence.canConnectFenceTo(world, i, j, k);
        }
    }
    
    public static class Piston
    {
        public static boolean canPushBlock(final Block block, final World world, final int i, final int j, final int k, final boolean flag) {
            final AxisAlignedBB bannerSearchBox = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 4), (double)(k + 1));
            final List banners = world.selectEntitiesWithinAABB((Class)LOTREntityBanner.class, bannerSearchBox, (IEntitySelector)new IEntitySelector() {
                public boolean isEntityApplicable(final Entity entity) {
                    final LOTREntityBanner banner = (LOTREntityBanner)entity;
                    return !banner.isDead && banner.isProtectingTerritory();
                }
            });
            return banners.isEmpty() && LOTRReflection.canPistonPushBlock(block, world, i, j, k, flag);
        }
    }
    
    public static class Cauldron
    {
        public static int getRenderType() {
            if (LOTRMod.proxy == null) {
                return 24;
            }
            return LOTRMod.proxy.getVCauldronRenderID();
        }
    }
    
    public static class Anvil
    {
        public static AxisAlignedBB getCollisionBoundingBoxFromPool(final Block block, final World world, final int i, final int j, final int k) {
            block.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
            return AxisAlignedBB.getBoundingBox(i + block.getBlockBoundsMinX(), j + block.getBlockBoundsMinY(), k + block.getBlockBoundsMinZ(), i + block.getBlockBoundsMaxX(), j + block.getBlockBoundsMaxY(), k + block.getBlockBoundsMaxZ());
        }
    }
    
    public static class Player
    {
        public static boolean canEat(final EntityPlayer entityplayer, final boolean forced) {
            if (entityplayer.capabilities.disableDamage) {
                return false;
            }
            if (forced) {
                return true;
            }
            if (entityplayer.getFoodStats().needFood()) {
                return true;
            }
            boolean feastMode = LOTRConfig.canAlwaysEat;
            if (((Entity)entityplayer).worldObj.isClient) {
                feastMode = LOTRLevelData.clientside_thisServer_feastMode;
            }
            return feastMode && ((Entity)entityplayer).ridingEntity == null;
        }
    }
    
    public static class Food
    {
        public static float getExhaustionFactor() {
            if (LOTRConfig.changedHunger) {
                return 0.3f;
            }
            return 1.0f;
        }
    }
    
    public static class Spawner
    {
        public static int performSpawning(final WorldServer world, final boolean hostiles, final boolean peacefuls, final boolean rareTick) {
            return LOTRSpawnerAnimals.performSpawning(world, hostiles, peacefuls, rareTick);
        }
    }
    
    public static class PathFinder
    {
        public static boolean isWoodenDoor(final Block block) {
            return block instanceof BlockDoor && block.getMaterial() == Material.wood;
        }
        
        public static boolean isFenceGate(final Block block) {
            return block instanceof BlockFenceGate;
        }
    }
    
    public static class Enchants
    {
        public static boolean isPlayerMeleeKill(final Entity entity, final DamageSource source) {
            final boolean flag = entity instanceof EntityPlayer && source.getSourceOfDamage() == entity;
            return flag;
        }
        
        public static float getEnchantmentModifierLiving(final float base, final EntityLivingBase attacker, final EntityLivingBase target) {
            float f = base;
            f += LOTREnchantmentHelper.calcEntitySpecificDamage(attacker.getHeldItem(), target);
            return f;
        }
        
        public static float func_152377_a(final float base, final ItemStack itemstack, final EnumCreatureAttribute creatureAttribute) {
            float f = base;
            f += LOTREnchantmentHelper.calcBaseMeleeDamageBoost(itemstack);
            return f;
        }
        
        public static boolean attemptDamageItem(final ItemStack itemstack, int damages, final Random random) {
            if (!itemstack.isItemStackDamageable()) {
                return false;
            }
            if (damages > 0) {
                final int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemstack);
                int negated = 0;
                if (unbreaking > 0) {
                    for (int l = 0; l < damages; ++l) {
                        if (EnchantmentDurability.negateDamage(itemstack, unbreaking, random)) {
                            ++negated;
                        }
                    }
                }
                for (int l = 0; l < damages; ++l) {
                    if (LOTREnchantmentHelper.negateDamage(itemstack, random)) {
                        ++negated;
                    }
                }
                damages -= negated;
                if (damages <= 0) {
                    return false;
                }
            }
            itemstack.setItemDamage(itemstack.getItemDamage() + damages);
            return itemstack.getItemDamage() > itemstack.getMaxDamage();
        }
        
        public static int c_attemptDamageItem(final int unmodified, final ItemStack stack, final int damages, final Random random, final EntityLivingBase elb) {
            int ret = unmodified;
            for (int i = 0; i < damages; ++i) {
                if (LOTREnchantmentHelper.negateDamage(stack, random)) {
                    --ret;
                }
            }
            return ret;
        }
        
        public static boolean getSilkTouchModifier(final boolean base, final EntityLivingBase entity) {
            boolean flag = base;
            if (LOTREnchantmentHelper.isSilkTouch(entity.getHeldItem())) {
                flag = true;
            }
            return flag;
        }
        
        public static int getKnockbackModifier(final int base, final EntityLivingBase attacker, final EntityLivingBase target) {
            int i = base;
            i += LOTRWeaponStats.getBaseExtraKnockback(attacker.getHeldItem());
            i += LOTREnchantmentHelper.calcExtraKnockback(attacker.getHeldItem());
            return i;
        }
        
        public static int getFortuneModifier(final int base, final EntityLivingBase entity) {
            int i = base;
            i += LOTREnchantmentHelper.calcLootingLevel(entity.getHeldItem());
            return i;
        }
        
        public static int getLootingModifier(final int base, final EntityLivingBase entity) {
            int i = base;
            i += LOTREnchantmentHelper.calcLootingLevel(entity.getHeldItem());
            return i;
        }
        
        public static int getSpecialArmorProtection(final int base, final ItemStack[] armor, final DamageSource source) {
            int i = base;
            i += LOTREnchantmentHelper.calcSpecialArmorSetProtection(armor, source);
            i = MathHelper.clamp_int(i, 0, 25);
            return i;
        }
        
        public static int getMaxFireProtectionLevel(final int base, final Entity entity) {
            int i = base;
            i = Math.max(i, LOTREnchantmentHelper.getMaxFireProtectionLevel(entity.getInventory()));
            return i;
        }
        
        public static int getFireAspectModifier(final int base, final EntityLivingBase entity) {
            int i = base;
            i += LOTREnchantmentHelper.calcFireAspectForMelee(entity.getHeldItem());
            return i;
        }
        
        public static int getDamageReduceAmount(final ItemStack itemstack) {
            return LOTRWeaponStats.getArmorProtection(itemstack);
        }
    }
    
    public static class Potions
    {
        public static double getStrengthModifier(final Potion thisPotion, final int level, final AttributeModifier modifier) {
            if (thisPotion.id == Potion.weakness.id) {
                return -0.5 * (level + 1);
            }
            return 0.5 * (level + 1);
        }
    }
    
    public static class ClientPlayer
    {
        public static void horseJump(final EntityClientPlayerMP entityplayer) {
            final int jump = (int)(entityplayer.getHorseJumpPower() * 100.0f);
            final Entity mount = ((Entity)entityplayer).ridingEntity;
            if (mount instanceof EntityHorse) {
                ((EntityHorse)mount).setJumpPower(jump);
            }
            entityplayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)entityplayer, 6, jump));
        }
    }
    
    public static class NetHandlerClient
    {
        public static void handleEntityTeleport(final NetHandlerPlayClient handler, final S18PacketEntityTeleport packet) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.func_149451_c());
            if (entity != null) {
                entity.serverPosX = packet.func_149449_d();
                entity.serverPosY = packet.func_149448_e();
                entity.serverPosZ = packet.func_149446_f();
                if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
                    final double d0 = entity.serverPosX / 32.0;
                    final double d2 = entity.serverPosY / 32.0 + 0.015625;
                    final double d3 = entity.serverPosZ / 32.0;
                    final float f = packet.func_149450_g() * 360 / 256.0f;
                    final float f2 = packet.func_149447_h() * 360 / 256.0f;
                    entity.setPositionAndRotation2(d0, d2, d3, f, f2, 3);
                }
            }
        }
        
        public static void handleEntityMovement(final NetHandlerPlayClient handler, final S14PacketEntity packet) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = packet.func_149065_a(world);
            if (entity != null) {
                final Entity entity2 = entity;
                entity2.serverPosX += packet.func_149062_c();
                final Entity entity3 = entity;
                entity3.serverPosY += packet.func_149061_d();
                final Entity entity4 = entity;
                entity4.serverPosZ += packet.func_149064_e();
                if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
                    final double d0 = entity.serverPosX / 32.0;
                    final double d2 = entity.serverPosY / 32.0;
                    final double d3 = entity.serverPosZ / 32.0;
                    final float f = packet.func_149060_h() ? (packet.func_149066_f() * 360 / 256.0f) : entity.rotationYaw;
                    final float f2 = packet.func_149060_h() ? (packet.func_149063_g() * 360 / 256.0f) : entity.rotationPitch;
                    entity.setPositionAndRotation2(d0, d2, d3, f, f2, 3);
                }
            }
        }
    }
    
    public static class EntityPackets
    {
        public static Packet getMobSpawnPacket(final Entity entity) {
            final EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn((Class)entity.getClass(), false);
            if (er == null) {
                return null;
            }
            if (er.usesVanillaSpawning()) {
                return null;
            }
            final FMLMessage msg = (FMLMessage)new FMLMessage.EntitySpawnMessage(er, entity, er.getContainer());
            final ByteBuf data = Unpooled.buffer();
            data.writeByte(2);
            try {
                new FMLRuntimeCodec().encodeInto((ChannelHandlerContext)null, msg, data);
            }
            catch (Exception e) {
                LOTRLog.logger.error("***********************************************");
                LOTRLog.logger.error("LOTR: ERROR sending mob spawn packet to client!");
                LOTRLog.logger.error("***********************************************");
            }
            return (Packet)new FMLProxyPacket(data, "FML");
        }
    }
}
