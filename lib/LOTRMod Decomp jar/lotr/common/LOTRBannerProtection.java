// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.init.Blocks;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.LOTREntityInvasionSpawner;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;

public class LOTRBannerProtection
{
    public static final int MAX_RANGE = 64;
    private static Map<Pair, Integer> protectionBlocks;
    private static Map<UUID, Integer> lastWarningTimes;
    
    public static int getProtectionRange(final Block block, final int meta) {
        final Integer i = LOTRBannerProtection.protectionBlocks.get(Pair.of((Object)block, (Object)meta));
        if (i == null) {
            return 0;
        }
        return i;
    }
    
    public static boolean isProtectedByBanner(final World world, final Entity entity, final IFilter protectFilter, final boolean sendMessage) {
        final int i = MathHelper.floor_double(entity.posX);
        final int j = MathHelper.floor_double(entity.boundingBox.minY);
        final int k = MathHelper.floor_double(entity.posZ);
        return isProtectedByBanner(world, i, j, k, protectFilter, sendMessage);
    }
    
    public static boolean isProtectedByBanner(final World world, final int i, final int j, final int k, final IFilter protectFilter, final boolean sendMessage) {
        return isProtectedByBanner(world, i, j, k, protectFilter, sendMessage, 0.0);
    }
    
    public static boolean isProtectedByBanner(final World world, final int i, final int j, final int k, final IFilter protectFilter, final boolean sendMessage, final double searchExtra) {
        if (!LOTRConfig.allowBannerProtection) {
            return false;
        }
        String protectorName = null;
        final AxisAlignedBB originCube = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).expand(searchExtra, searchExtra, searchExtra);
        final AxisAlignedBB searchCube = originCube.expand(64.0, 64.0, 64.0);
        final List banners = world.getEntitiesWithinAABB((Class)LOTREntityBanner.class, searchCube);
        if (!banners.isEmpty()) {
            for (int l = 0; l < banners.size(); ++l) {
                final LOTREntityBanner banner = banners.get(l);
                final AxisAlignedBB protectionCube = banner.createProtectionCube();
                if (banner.isProtectingTerritory() && protectionCube.intersectsWith(searchCube) && protectionCube.intersectsWith(originCube)) {
                    final ProtectType result = protectFilter.protects(banner);
                    if (result != ProtectType.NONE) {
                        if (result == ProtectType.FACTION) {
                            protectorName = banner.getBannerType().faction.factionName();
                            break;
                        }
                        if (result == ProtectType.PLAYER_SPECIFIC) {
                            final GameProfile placingPlayer = banner.getPlacingPlayer();
                            if (placingPlayer != null) {
                                if (StringUtils.isBlank((CharSequence)placingPlayer.getName())) {
                                    MinecraftServer.getServer().func_147130_as().fillProfileProperties(placingPlayer, true);
                                }
                                protectorName = placingPlayer.getName();
                                break;
                            }
                            protectorName = "?";
                            break;
                        }
                        else if (result == ProtectType.STRUCTURE) {
                            protectorName = StatCollector.translateToLocal("chat.lotr.protectedStructure");
                            break;
                        }
                    }
                }
            }
        }
        if (protectorName != null) {
            if (sendMessage) {
                protectFilter.warnProtection((IChatComponent)new ChatComponentTranslation("chat.lotr.protectedLand", new Object[] { protectorName }));
            }
            return true;
        }
        return false;
    }
    
    public static IFilter anyBanner() {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                return ProtectType.FACTION;
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
            }
        };
    }
    
    public static IFilter forPlayer(final EntityPlayer entityplayer) {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (entityplayer.capabilities.isCreativeMode) {
                    return ProtectType.NONE;
                }
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                if (banner.isPlayerSpecificProtection()) {
                    if (!banner.isPlayerWhitelisted(entityplayer)) {
                        return ProtectType.PLAYER_SPECIFIC;
                    }
                    return ProtectType.NONE;
                }
                else {
                    final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(banner.getBannerType().faction);
                    if (alignment < banner.getAlignmentProtection()) {
                        return ProtectType.FACTION;
                    }
                    return ProtectType.NONE;
                }
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
                if (entityplayer instanceof EntityPlayerMP && !((Entity)entityplayer).worldObj.isClient) {
                    final EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
                    entityplayermp.sendContainerToPlayer(entityplayer.inventoryContainer);
                    if (!hasWarningCooldown((EntityPlayer)entityplayermp)) {
                        entityplayermp.addChatMessage(message);
                        setWarningCooldown((EntityPlayer)entityplayermp);
                    }
                }
            }
        };
    }
    
    public static IFilter forPlayer_returnMessage(final EntityPlayer entityplayer, final IChatComponent[] protectionMessage) {
        return new IFilter() {
            private IFilter internalPlayerFilter = LOTRBannerProtection.forPlayer(entityplayer);
            
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                return this.internalPlayerFilter.protects(banner);
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
                this.internalPlayerFilter.warnProtection(message);
                protectionMessage[0] = message;
            }
        };
    }
    
    public static IFilter forNPC(final EntityLiving entity) {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                if (banner.getBannerType().faction.isBadRelation(LOTRMod.getNPCFaction((Entity)entity))) {
                    return ProtectType.FACTION;
                }
                return ProtectType.NONE;
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
            }
        };
    }
    
    public static IFilter forInvasionSpawner(final LOTREntityInvasionSpawner spawner) {
        return forFaction(spawner.getInvasionType().invasionFaction);
    }
    
    public static IFilter forFaction(final LOTRFaction theFaction) {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                if (banner.getBannerType().faction.isBadRelation(theFaction)) {
                    return ProtectType.FACTION;
                }
                return ProtectType.NONE;
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
            }
        };
    }
    
    public static IFilter forTNT(final EntityTNTPrimed bomb) {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                final EntityLivingBase bomber = bomb.getTntPlacedBy();
                if (bomber == null) {
                    return ProtectType.FACTION;
                }
                if (bomber instanceof EntityPlayer) {
                    return LOTRBannerProtection.forPlayer((EntityPlayer)bomber).protects(banner);
                }
                if (bomber instanceof EntityLiving) {
                    return LOTRBannerProtection.forNPC((EntityLiving)bomber).protects(banner);
                }
                return ProtectType.NONE;
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
            }
        };
    }
    
    public static IFilter forTNTMinecart(final EntityMinecartTNT minecart) {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                return ProtectType.FACTION;
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
            }
        };
    }
    
    public static IFilter forThrown(final EntityThrowable throwable) {
        return new IFilter() {
            @Override
            public ProtectType protects(final LOTREntityBanner banner) {
                if (banner.isStructureProtection()) {
                    return ProtectType.STRUCTURE;
                }
                final EntityLivingBase thrower = throwable.getThrower();
                if (thrower == null) {
                    return ProtectType.FACTION;
                }
                if (thrower instanceof EntityPlayer) {
                    return LOTRBannerProtection.forPlayer((EntityPlayer)thrower).protects(banner);
                }
                if (thrower instanceof EntityLiving) {
                    return LOTRBannerProtection.forNPC((EntityLiving)thrower).protects(banner);
                }
                return ProtectType.NONE;
            }
            
            @Override
            public void warnProtection(final IChatComponent message) {
            }
        };
    }
    
    private static void setWarningCooldown(final EntityPlayer entityplayer) {
        LOTRBannerProtection.lastWarningTimes.put(entityplayer.getUniqueID(), LOTRConfig.bannerWarningCooldown);
    }
    
    private static boolean hasWarningCooldown(final EntityPlayer entityplayer) {
        return LOTRBannerProtection.lastWarningTimes.containsKey(entityplayer.getUniqueID());
    }
    
    public static void updateWarningCooldowns() {
        final Set<UUID> removes = new HashSet<UUID>();
        for (final Map.Entry<UUID, Integer> e : LOTRBannerProtection.lastWarningTimes.entrySet()) {
            final UUID player = e.getKey();
            int time = e.getValue();
            --time;
            e.setValue(time);
            if (time <= 0) {
                removes.add(player);
            }
        }
        for (final UUID player2 : removes) {
            LOTRBannerProtection.lastWarningTimes.remove(player2);
        }
    }
    
    static {
        LOTRBannerProtection.protectionBlocks = new HashMap<Pair, Integer>();
        final Pair BRONZE = Pair.of((Object)LOTRMod.blockOreStorage, (Object)2);
        final Pair SILVER = Pair.of((Object)LOTRMod.blockOreStorage, (Object)3);
        final Pair GOLD = Pair.of((Object)Blocks.gold_block, (Object)0);
        LOTRBannerProtection.protectionBlocks.put(BRONZE, 8);
        LOTRBannerProtection.protectionBlocks.put(SILVER, 16);
        LOTRBannerProtection.protectionBlocks.put(GOLD, 32);
        LOTRBannerProtection.lastWarningTimes = new HashMap<UUID, Integer>();
    }
    
    public enum ProtectType
    {
        NONE, 
        FACTION, 
        PLAYER_SPECIFIC, 
        STRUCTURE;
    }
    
    public interface IFilter
    {
        ProtectType protects(final LOTREntityBanner p0);
        
        void warnProtection(final IChatComponent p0);
    }
}
