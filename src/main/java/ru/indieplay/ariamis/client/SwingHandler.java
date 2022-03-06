package ru.indieplay.ariamis.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import ru.indieplay.ariamis.common.WeaponStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingHandler {
    private static Map<EntityLivingBase, SwingTime> entitySwings;
    private static float swingFactor;

    public SwingHandler() {
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        final World world = ((Entity)entity).worldObj;
        if (!world.isRemote) {
            SwingTime swt = SwingHandler.entitySwings.get(entity);
            if (swt == null && entity.isSwingInProgress && entity.swingProgressInt == 0) {
                final ItemStack item = entity.getHeldItem();
                if (WeaponStats.isMeleeWeapon(item)) {
                    int time = 0;
                    if (entity instanceof EntityPlayer) {
                        time = WeaponStats.getAttackTimePlayer(item);
                    }
                    else {
                        time = WeaponStats.getAttackTimePlayer(item);
                    }
                    time = Math.round(time * SwingHandler.swingFactor);
                    swt = new SwingTime();
                    swt.swing = 1;
                    swt.swingPrev = 0;
                    swt.swingMax = time;
                    SwingHandler.entitySwings.put(entity, swt);
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc.theWorld == null) {
                SwingHandler.entitySwings.clear();
            }
            else if (!mc.isGamePaused()) {
                final List<EntityLivingBase> removes = new ArrayList<EntityLivingBase>();
                for (final Map.Entry<EntityLivingBase, SwingTime> e : SwingHandler.entitySwings.entrySet()) {
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
                    SwingHandler.entitySwings.remove(entity2);
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
            if (AttackTiming.fullAttackTime > 0) {
                final float max = (float)AttackTiming.fullAttackTime;
                float swing = (max - AttackTiming.attackTime) / max;
                float pre = (max - AttackTiming.prevAttackTime) / max;
                swing /= SwingHandler.swingFactor;
                pre /= SwingHandler.swingFactor;
                if (swing <= 1.0f) {
                    entity.swingProgress = swing;
                    entity.prevSwingProgress = pre;
                }
            }
        }
        else {
            final SwingTime swt = SwingHandler.entitySwings.get(entity);
            if (swt != null) {
                entity.swingProgress = swt.swing / (float)swt.swingMax;
                entity.prevSwingProgress = swt.swingPrev / (float)swt.swingMax;
            }
        }
    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            //tickEnd(event.player);
        }
    }

    public void tickEnd(EntityPlayer entityPlayer) {

        // If we JUST swung an Item
        if (entityPlayer.swingProgressInt == 1) {
            ItemStack mainhand = entityPlayer.getCurrentEquippedItem();
            if (mainhand != null && WeaponStats.getMeleeReachFactor(mainhand) != 1) {
                float extendedReach = WeaponStats.getMeleeReachFactor(mainhand);
                float range =  4*extendedReach -4 ;
                AxisAlignedBB bb = entityPlayer.boundingBox;
                bb = entityPlayer.boundingBox.expand(range, range, range);
                System.out.println(range);
                if (extendedReach > 0) {
                    MovingObjectPosition mouseOver = getMouseOver(0, extendedReach * 4);
                    if (mouseOver != null && mouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                        Entity target = mouseOver.entityHit;
                        if (target instanceof EntityLiving && target != entityPlayer) {
                            if (target.hurtResistantTime != ((EntityLiving) target).maxHurtResistantTime) {
                                FMLClientHandler.instance().getClient().playerController.attackEntity(entityPlayer, target);
                            }
                        }
                    }
                }
            }
        }
    }

    public static MovingObjectPosition getMouseOver(float tickPart, float maxDist) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                mc.pointedEntity = null;
                double maxDist1 = maxDist;
                MovingObjectPosition objectMouseOver = mc.renderViewEntity.rayTrace(maxDist1, tickPart);
                double maxDist11 = maxDist1;
                Vec3 vec3 = mc.renderViewEntity.getPosition(tickPart);

                if (objectMouseOver != null) {
                    maxDist11 = objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = mc.renderViewEntity.getLook(tickPart);
                Vec3 vec32 = vec3.addVector(vec31.xCoord * maxDist1, vec31.yCoord * maxDist1, vec31.zCoord * maxDist1);
                Entity pointedEntity = null;
                float f1 = 1.0F;
                List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity,
                        mc.renderViewEntity.boundingBox
                                .addCoord(vec31.xCoord * maxDist1, vec31.yCoord * maxDist1, vec31.zCoord * maxDist1).expand(f1, f1, f1));
                double d2 = maxDist11;

                for (int i = 0; i < list.size(); ++i) {
                    Entity entity = (Entity) list.get(i);

                    if (entity.canBeCollidedWith()) {
                        float f2 = entity.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.isVecInside(vec3)) {
                            if (0.0D < d2 || d2 == 0.0D) {
                                pointedEntity = entity;
                                d2 = 0.0D;
                            }
                        } else if (movingobjectposition != null) {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D) {
                                pointedEntity = entity;
                                d2 = d3;
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d2 < maxDist11 || objectMouseOver == null)) {
                    objectMouseOver = new MovingObjectPosition(pointedEntity);
                }

                return objectMouseOver;
            }
        }
        return null;
    }

    static {
        SwingHandler.entitySwings = new HashMap<EntityLivingBase, SwingTime>();
        SwingHandler.swingFactor = 0.8f;
    }

    private static class SwingTime
    {
        public int swingPrev;
        public int swing;
        public int swingMax;
    }
}
