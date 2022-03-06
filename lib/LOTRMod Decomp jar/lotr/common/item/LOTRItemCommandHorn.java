// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.item.EnumAction;
import java.util.List;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRSquadrons;
import net.minecraft.item.Item;

public class LOTRItemCommandHorn extends Item implements LOTRSquadrons.SquadronItem
{
    public LOTRItemCommandHorn() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!world.isClient) {
            final List entities = world.loadedEntityList;
            for (int l = 0; l < entities.size(); ++l) {
                if (entities.get(l) instanceof LOTREntityNPC) {
                    final LOTREntityNPC npc = entities.get(l);
                    if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && LOTRSquadrons.areSquadronsCompatible(npc, itemstack)) {
                        if (itemstack.getItemDamage() == 1 && npc.hiredNPCInfo.getObeyHornHaltReady()) {
                            npc.hiredNPCInfo.halt();
                        }
                        else if (itemstack.getItemDamage() == 2 && npc.hiredNPCInfo.getObeyHornHaltReady()) {
                            npc.hiredNPCInfo.ready();
                        }
                        else if (itemstack.getItemDamage() == 3 && npc.hiredNPCInfo.getObeyHornSummon()) {
                            npc.hiredNPCInfo.tryTeleportToHiringPlayer(true);
                        }
                    }
                }
            }
        }
        if (itemstack.getItemDamage() == 1) {
            itemstack.setItemDamage(2);
        }
        else if (itemstack.getItemDamage() == 2) {
            itemstack.setItemDamage(1);
        }
        world.playSoundAtEntity((Entity)entityplayer, "lotr:item.horn", 4.0f, 1.0f);
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 40;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (itemstack.getItemDamage() == 0) {
            entityplayer.openGui((Object)LOTRMod.instance, 9, world, 0, 0, 0);
        }
        else {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        String s = "";
        if (itemstack.getItemDamage() == 1) {
            s = ".halt";
        }
        else if (itemstack.getItemDamage() == 2) {
            s = ".ready";
        }
        else if (itemstack.getItemDamage() == 3) {
            s = ".summon";
        }
        return super.getUnlocalizedName(itemstack) + s;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j <= 3; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
