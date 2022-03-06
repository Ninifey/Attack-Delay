// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRConfig;
import lotr.common.world.structure.LOTRWorldGenStructureBase;
import java.util.Collections;
import java.util.ArrayList;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.List;

public class LOTRStructureTimelapse
{
    private static List<ThreadTimelapse> allThreads;
    
    public LOTRStructureTimelapse() {
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static void start(final WorldGenerator gen, final World world, final int i, final int j, final int k) {
        new ThreadTimelapse(gen, world, i, j, k).start();
    }
    
    @SubscribeEvent
    public void onWorldUnload(final WorldEvent.Unload event) {
        final World world = event.world;
        for (final ThreadTimelapse thr : LOTRStructureTimelapse.allThreads) {
            thr.interrupt();
        }
        LOTRStructureTimelapse.allThreads.clear();
    }
    
    static {
        LOTRStructureTimelapse.allThreads = Collections.synchronizedList(new ArrayList<ThreadTimelapse>());
    }
    
    public static class ThreadTimelapse extends Thread
    {
        private WorldGenerator structureGen;
        private World theWorld;
        private int posX;
        private int posY;
        private int posZ;
        
        public ThreadTimelapse(final WorldGenerator gen, final World world, final int i, final int j, final int k) {
            this.structureGen = gen;
            this.theWorld = world;
            this.posX = i;
            this.posY = j;
            this.posZ = k;
        }
        
        @Override
        public void start() {
            this.setDaemon(true);
            super.start();
            LOTRStructureTimelapse.allThreads.add(this);
        }
        
        @Override
        public void run() {
            if (this.structureGen instanceof LOTRWorldGenStructureBase2) {
                final LOTRWorldGenStructureBase2 str2 = (LOTRWorldGenStructureBase2)this.structureGen;
                str2.threadTimelapse = this;
                str2.generateWithSetRotation(this.theWorld, this.theWorld.rand, this.posX, this.posY, this.posZ, str2.usingPlayerRotation());
            }
            else if (this.structureGen instanceof LOTRWorldGenStructureBase) {
                final LOTRWorldGenStructureBase str3 = (LOTRWorldGenStructureBase)this.structureGen;
                str3.threadTimelapse = this;
                str3.generate(this.theWorld, this.theWorld.rand, this.posX, this.posY, this.posZ);
            }
            LOTRStructureTimelapse.allThreads.remove(this);
        }
        
        public void onBlockSet() {
            if (LOTRConfig.strTimelapse) {
                try {
                    Thread.sleep(LOTRConfig.strTimelapseInterval);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
