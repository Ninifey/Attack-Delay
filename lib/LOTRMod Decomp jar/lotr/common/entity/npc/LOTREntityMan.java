// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.Item;
import java.util.Iterator;
import lotr.common.LOTRPlayerData;
import java.util.List;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemThrowingAxe;
import net.minecraft.item.ItemTool;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.item.LOTRItemManFlesh;
import lotr.common.LOTRMod;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityMan extends LOTREntityNPC
{
    public LOTREntityMan(final World world) {
        super(world);
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && LOTRMod.canDropLoot(((Entity)this).worldObj) && ((Entity)this).rand.nextInt(5) == 0) {
            final List<LOTRFaction> manFleshFactions = LOTRItemManFlesh.getManFleshFactions();
            final Entity damager = damagesource.getSourceOfDamage();
            if (damager instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)damager;
                boolean isAligned = false;
                if (entity instanceof EntityPlayer) {
                    final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entity);
                    for (final LOTRFaction f : manFleshFactions) {
                        if (playerData.getAlignment(f) > 0.0f) {
                            isAligned = true;
                        }
                    }
                }
                else {
                    final LOTRFaction npcFaction = LOTRMod.getNPCFaction((Entity)entity);
                    isAligned = manFleshFactions.contains(npcFaction);
                }
                if (isAligned) {
                    final ItemStack itemstack = entity.getHeldItem();
                    if (itemstack != null) {
                        final Item item = itemstack.getItem();
                        Item.ToolMaterial material = null;
                        if (item instanceof ItemSword) {
                            final ItemSword sword = (ItemSword)item;
                            material = LOTRMaterial.getToolMaterialByName(sword.func_150932_j());
                        }
                        else if (item instanceof ItemTool) {
                            final ItemTool tool = (ItemTool)item;
                            material = tool.func_150913_i();
                        }
                        else if (item instanceof LOTRItemThrowingAxe) {
                            final LOTRItemThrowingAxe axe = (LOTRItemThrowingAxe)item;
                            material = axe.getAxeMaterial();
                        }
                        if (material != null) {
                            boolean canHarvest = false;
                            for (final LOTRMaterial lotrMaterial : LOTRMaterial.allLOTRMaterials) {
                                if (lotrMaterial.toToolMaterial() == material && lotrMaterial.canHarvestManFlesh()) {
                                    canHarvest = true;
                                    break;
                                }
                            }
                            if (canHarvest) {
                                final ItemStack flesh = new ItemStack(LOTRMod.manFlesh, 1 + ((Entity)this).rand.nextInt(2));
                                this.entityDropItem(flesh, 0.0f);
                            }
                        }
                    }
                }
            }
        }
    }
}
