// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import java.util.HashMap;
import lotr.client.LOTRAttackTiming;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRWeaponStats;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.EntityLivingBase;
import java.util.Map;

public class LOTRSwingHandler
{
    private static Map<EntityLivingBase, SwingTime> entitySwings;
    private static float swingFactor;
    
    public LOTRSwingHandler() {
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final World world = ((Entity)entity).worldObj;
        if (world.isClient) {
            SwingTime swt = LOTRSwingHandler.entitySwings.get(entity);
            if (swt == null && entity.isSwingInProgress && entity.swingProgressInt == 0) {
                final ItemStack item = entity.getHeldItem();
                if (LOTRWeaponStats.isMeleeWeapon(item)) {
                    int time = 0;
                    if (entity instanceof EntityPlayer) {
                        time = LOTRWeaponStats.getAttackTimePlayer(item);
                    }
                    else {
                        time = LOTRWeaponStats.getAttackTimePlayer(item);
                    }
                    time = Math.round(time * LOTRSwingHandler.swingFactor);
                    swt = new SwingTime();
                    swt.swing = 1;
                    swt.swingPrev = 0;
                    swt.swingMax = time;
                    LOTRSwingHandler.entitySwings.put(entity, swt);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc.theWorld == null) {
                LOTRSwingHandler.entitySwings.clear();
            }
            else if (!mc.func_147113_T()) {
                final List<EntityLivingBase> removes = new ArrayList<EntityLivingBase>();
                for (final Map.Entry<EntityLivingBase, SwingTime> e : LOTRSwingHandler.entitySwings.entrySet()) {
                    final EntityLivingBase entity = e.getKey();
                    final SwingTime swt = e.getValue();
                    swt.swingPrev = swt.swing;
                    final SwingTime swingTime = swt;
                    ++swingTime.swing;
                    if (swt.swing > swt.swingMax) {
                        removes.add(entity);
                    }
                }
                for (final EntityLivingBase entity2 : removes) {
                    LOTRSwingHandler.entitySwings.remove(entity2);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            final EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
            if (entityplayer != null) {
                this.tryUpdateSwing((EntityLivingBase)entityplayer);
            }
        }
    }
    
    @SubscribeEvent
    public void preRenderPlayer(final RenderPlayerEvent.Pre event) {
        this.tryUpdateSwing((EntityLivingBase)event.entityPlayer);
    }
    
    @SubscribeEvent
    public void preRenderLiving(final RenderLivingEvent.Pre event) {
        this.tryUpdateSwing(event.entity);
    }
    
    private void tryUpdateSwing(final EntityLivingBase entity) {
        if (entity == Minecraft.getMinecraft().thePlayer) {
            if (LOTRAttackTiming.fullAttackTime > 0) {
                final float max = (float)LOTRAttackTiming.fullAttackTime;
                float swing = (max - LOTRAttackTiming.attackTime) / max;
                float pre = (max - LOTRAttackTiming.prevAttackTime) / max;
                swing /= LOTRSwingHandler.swingFactor;
                pre /= LOTRSwingHandler.swingFactor;
                if (swing <= 1.0f) {
                    entity.swingProgress = swing;
                    entity.prevSwingProgress = pre;
                }
            }
        }
        else {
            final SwingTime swt = LOTRSwingHandler.entitySwings.get(entity);
            if (swt != null) {
                entity.swingProgress = swt.swing / (float)swt.swingMax;
                entity.prevSwingProgress = swt.swingPrev / (float)swt.swingMax;
            }
        }
    }
    
    static {
        LOTRSwingHandler.entitySwings = new HashMap<EntityLivingBase, SwingTime>();
        LOTRSwingHandler.swingFactor = 0.8f;
    }
    
    private static class SwingTime
    {
        public int swingPrev;
        public int swing;
        public int swingMax;
    }
}
