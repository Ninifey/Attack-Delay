// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRLevelData;
import net.minecraft.server.MinecraftServer;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRItemSauronMace extends LOTRItemHammer implements LOTRStoryItem
{
    public LOTRItemSauronMace() {
        super(LOTRMaterial.MORDOR);
        this.setMaxDamage(1500);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
        super.lotrWeaponDamage = 8.0f;
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        itemstack.damageItem(2, (EntityLivingBase)entityplayer);
        return useSauronMace(itemstack, world, (EntityLivingBase)entityplayer);
    }
    
    public static ItemStack useSauronMace(final ItemStack itemstack, final World world, final EntityLivingBase user) {
        user.swingItem();
        world.playSoundAtEntity((Entity)user, "lotr:item.maceSauron", 2.0f, (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.2f + 1.0f);
        if (!world.isClient) {
            final List entities = world.getEntitiesWithinAABB((Class)EntityLivingBase.class, ((Entity)user).boundingBox.expand(12.0, 8.0, 12.0));
            if (!entities.isEmpty()) {
                for (int i = 0; i < entities.size(); ++i) {
                    final EntityLivingBase entity = entities.get(i);
                    if (entity != user) {
                        if (entity instanceof EntityLiving) {
                            final EntityLiving entityliving = (EntityLiving)entity;
                            if (LOTRFaction.MORDOR.isGoodRelation(LOTRMod.getNPCFaction((Entity)entityliving))) {
                                continue;
                            }
                        }
                        if (entity instanceof EntityPlayer) {
                            if (user instanceof EntityPlayer) {
                                if (!MinecraftServer.getServer().isPVPEnabled()) {
                                    continue;
                                }
                                if (LOTRLevelData.getData((EntityPlayer)entity).getAlignment(LOTRFaction.MORDOR) > 0.0f) {
                                    continue;
                                }
                            }
                            else if (user instanceof EntityLiving && ((EntityLiving)user).getAttackTarget() != entity && LOTRLevelData.getData((EntityPlayer)entity).getAlignment(LOTRFaction.MORDOR) > 0.0f) {
                                continue;
                            }
                        }
                        float strength = 6.0f - user.getDistanceToEntity((Entity)entity) * 0.75f;
                        if (strength < 1.0f) {
                            strength = 1.0f;
                        }
                        if (user instanceof EntityPlayer) {
                            entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)user), 6.0f * strength);
                        }
                        else {
                            entity.attackEntityFrom(DamageSource.causeMobDamage(user), 6.0f * strength);
                        }
                        float knockback = strength;
                        if (knockback > 4.0f) {
                            knockback = 4.0f;
                        }
                        entity.addVelocity((double)(-MathHelper.sin(((Entity)user).rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback), 0.2 + 0.12 * knockback, (double)(MathHelper.cos(((Entity)user).rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback));
                    }
                }
            }
            final LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.MACE_SAURON, (Entity)user);
            LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)user, 64.0));
        }
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 40;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
}
