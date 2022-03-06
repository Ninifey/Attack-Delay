// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeEventFactory;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTRTravellingTrader;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import java.util.Random;

public class LOTRTravellingTraderSpawner
{
    private static Random rand;
    private Class theEntityClass;
    public String entityClassName;
    private int timeUntilTrader;
    
    public LOTRTravellingTraderSpawner(final Class<? extends LOTREntityNPC> entityClass) {
        this.theEntityClass = entityClass;
        this.entityClassName = LOTREntities.getStringFromClass(this.theEntityClass);
    }
    
    private static int getRandomTraderTime() {
        final float minHours = 0.8f;
        final float maxHours = 10.0f;
        return MathHelper.getRandomIntegerInRange(LOTRTravellingTraderSpawner.rand, (int)(minHours * 3600.0f) * 20, (int)(maxHours * 3600.0f) * 20);
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("TraderTime", this.timeUntilTrader);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey("TraderTime")) {
            this.timeUntilTrader = nbt.getInteger("TraderTime");
        }
        else {
            this.timeUntilTrader = getRandomTraderTime();
        }
    }
    
    public void performSpawning(final World world) {
        if (this.timeUntilTrader > 0) {
            --this.timeUntilTrader;
        }
        else if (world.rand.nextInt(1000) == 0) {
            boolean spawned = false;
            final LOTREntityNPC entityTrader = (LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.theEntityClass), world);
            final LOTRTravellingTrader trader = (LOTRTravellingTrader)entityTrader;
        Label_0454:
            for (int players = 0; players < world.playerEntities.size(); ++players) {
                final EntityPlayer entityplayer = world.playerEntities.get(players);
                if (LOTRLevelData.getData(entityplayer).getAlignment(entityTrader.getFaction()) >= 0.0f) {
                    for (int attempts = 0; attempts < 16; ++attempts) {
                        final float angle = world.rand.nextFloat() * 360.0f;
                        final int i = MathHelper.floor_double(((Entity)entityplayer).posX) + MathHelper.floor_double((double)(MathHelper.sin(angle) * (48 + world.rand.nextInt(33))));
                        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ) + MathHelper.floor_double((double)(MathHelper.cos(angle) * (48 + world.rand.nextInt(33))));
                        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                        if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnTravellingTrader(this.theEntityClass)) {
                            final int j = world.getHeightValue(i, k);
                            final Block block = world.getBlock(i, j - 1, k);
                            if (j > 62 && (block == biome.topBlock || block == biome.fillerBlock) && !world.getBlock(i, j, k).isNormalCube() && !world.getBlock(i, j + 1, k).isNormalCube()) {
                                entityTrader.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
                                entityTrader.liftSpawnRestrictions = true;
                                final Event.Result canSpawn = ForgeEventFactory.canEntitySpawn((EntityLiving)entityTrader, world, (float)((Entity)entityTrader).posX, (float)((Entity)entityTrader).posY, (float)((Entity)entityTrader).posZ);
                                if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && entityTrader.getCanSpawnHere())) {
                                    entityTrader.liftSpawnRestrictions = false;
                                    entityTrader.spawnRidingHorse = false;
                                    world.spawnEntityInWorld((Entity)entityTrader);
                                    entityTrader.onSpawnWithEgg(null);
                                    trader.startTraderVisiting(entityplayer);
                                    spawned = true;
                                    this.timeUntilTrader = getRandomTraderTime();
                                    LOTRLevelData.markDirty();
                                    break Label_0454;
                                }
                            }
                        }
                    }
                }
            }
            if (!spawned) {
                this.timeUntilTrader = 200 + world.rand.nextInt(400);
            }
        }
    }
    
    static {
        LOTRTravellingTraderSpawner.rand = new Random();
    }
}
