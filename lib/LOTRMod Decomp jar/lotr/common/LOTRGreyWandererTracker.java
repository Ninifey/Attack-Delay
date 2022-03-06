// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import java.util.HashMap;
import java.util.Random;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeEventFactory;
import lotr.common.entity.npc.LOTREntityGandalf;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.world.World;
import java.util.Set;
import java.util.HashSet;
import cpw.mods.fml.common.FMLLog;
import java.util.Iterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import java.util.UUID;
import java.util.Map;

public class LOTRGreyWandererTracker
{
    private static Map<UUID, Integer> activeGreyWanderers;
    private static final int greyWandererCooldown_MAX = 3600;
    private static final int spawnInterval = 2400;
    private static int spawnCooldown;
    
    public static void save(final NBTTagCompound levelData) {
        final NBTTagList greyWandererTags = new NBTTagList();
        for (final Map.Entry<UUID, Integer> e : LOTRGreyWandererTracker.activeGreyWanderers.entrySet()) {
            final UUID id = e.getKey();
            final int cd = e.getValue();
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("ID", id.toString());
            nbt.setInteger("CD", cd);
            greyWandererTags.appendTag((NBTBase)nbt);
        }
        levelData.setTag("GreyWanderers", (NBTBase)greyWandererTags);
        levelData.setInteger("GWSpawnTick", LOTRGreyWandererTracker.spawnCooldown);
    }
    
    public static void load(final NBTTagCompound levelData) {
        LOTRGreyWandererTracker.activeGreyWanderers.clear();
        final NBTTagList greyWandererTags = levelData.getTagList("GreyWanderers", 10);
        for (int i = 0; i < greyWandererTags.tagCount(); ++i) {
            final NBTTagCompound nbt = greyWandererTags.getCompoundTagAt(i);
            try {
                final UUID id = UUID.fromString(nbt.getString("ID"));
                final int cd = nbt.getInteger("CD");
                LOTRGreyWandererTracker.activeGreyWanderers.put(id, cd);
            }
            catch (Exception e) {
                FMLLog.severe("Error loading LOTR data: invalid Grey Wanderer", new Object[0]);
                e.printStackTrace();
            }
        }
        if (levelData.hasKey("GWSpawnTick")) {
            LOTRGreyWandererTracker.spawnCooldown = levelData.getInteger("GWSpawnTick");
        }
        else {
            LOTRGreyWandererTracker.spawnCooldown = 2400;
        }
    }
    
    private static void markDirty() {
        LOTRLevelData.markDirty();
    }
    
    public static boolean isWandererActive(final UUID id) {
        return LOTRGreyWandererTracker.activeGreyWanderers.containsKey(id) && LOTRGreyWandererTracker.activeGreyWanderers.get(id) > 0;
    }
    
    public static void addNewWanderer(final UUID id) {
        LOTRGreyWandererTracker.activeGreyWanderers.put(id, 3600);
        markDirty();
    }
    
    public static void setWandererActive(final UUID id) {
        if (LOTRGreyWandererTracker.activeGreyWanderers.containsKey(id)) {
            LOTRGreyWandererTracker.activeGreyWanderers.put(id, 3600);
            markDirty();
        }
    }
    
    public static void updateCooldowns() {
        final Set<UUID> removes = new HashSet<UUID>();
        for (final UUID id : LOTRGreyWandererTracker.activeGreyWanderers.keySet()) {
            int cd = LOTRGreyWandererTracker.activeGreyWanderers.get(id);
            --cd;
            LOTRGreyWandererTracker.activeGreyWanderers.put(id, cd);
            if (cd <= 0) {
                removes.add(id);
            }
        }
        if (!removes.isEmpty()) {
            for (final UUID id : removes) {
                LOTRGreyWandererTracker.activeGreyWanderers.remove(id);
            }
            markDirty();
        }
    }
    
    public static void performSpawning(final World world) {
        if (!LOTRGreyWandererTracker.activeGreyWanderers.isEmpty()) {
            return;
        }
        Label_0415: {
            if (!world.playerEntities.isEmpty()) {
                --LOTRGreyWandererTracker.spawnCooldown;
                if (LOTRGreyWandererTracker.spawnCooldown <= 0) {
                    LOTRGreyWandererTracker.spawnCooldown = 2400;
                    final List players = new ArrayList(world.playerEntities);
                    Collections.shuffle(players);
                    final Random rand = world.rand;
                    boolean spawned = false;
                    for (final Object obj : players) {
                        final EntityPlayer entityplayer = (EntityPlayer)obj;
                        if (!LOTRLevelData.getData(entityplayer).hasAnyGWQuest()) {
                            for (int attempts = 0; attempts < 32; ++attempts) {
                                final float angle = rand.nextFloat() * 3.1415927f * 2.0f;
                                final int r = MathHelper.getRandomIntegerInRange(rand, 4, 16);
                                final int i = MathHelper.floor_double(((Entity)entityplayer).posX + r * MathHelper.cos(angle));
                                final int k = MathHelper.floor_double(((Entity)entityplayer).posZ + r * MathHelper.sin(angle));
                                final int j = world.getHeightValue(i, k);
                                if (j > 62 && world.getBlock(i, j - 1, k).isOpaqueCube() && !world.getBlock(i, j, k).isNormalCube() && !world.getBlock(i, j + 1, k).isNormalCube()) {
                                    final LOTREntityGandalf wanderer = new LOTREntityGandalf(world);
                                    wanderer.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
                                    wanderer.liftSpawnRestrictions = true;
                                    wanderer.liftBannerRestrictions = true;
                                    final Event.Result canSpawn = ForgeEventFactory.canEntitySpawn((EntityLiving)wanderer, world, (float)((Entity)wanderer).posX, (float)((Entity)wanderer).posY, (float)((Entity)wanderer).posZ);
                                    if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && wanderer.getCanSpawnHere())) {
                                        wanderer.liftSpawnRestrictions = false;
                                        wanderer.liftBannerRestrictions = false;
                                        world.spawnEntityInWorld((Entity)wanderer);
                                        wanderer.onSpawnWithEgg(null);
                                        addNewWanderer(wanderer.getUniqueID());
                                        wanderer.arriveAt(entityplayer);
                                        spawned = true;
                                        break Label_0415;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    static {
        LOTRGreyWandererTracker.activeGreyWanderers = new HashMap<UUID, Integer>();
    }
}
