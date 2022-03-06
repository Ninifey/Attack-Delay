// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRPlayerData;
import net.minecraft.util.StatCollector;
import java.util.List;
import lotr.common.LOTRCommonProxy;
import lotr.common.quest.LOTRMiniQuestEvent;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemRedBook extends Item
{
    @SideOnly(Side.CLIENT)
    public static IIcon questOfferIcon;
    
    public LOTRItemRedBook() {
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final LOTRMod instance = LOTRMod.instance;
        final LOTRCommonProxy proxy = LOTRMod.proxy;
        entityplayer.openGui((Object)instance, 32, world, 0, 0, 0);
        if (!world.isClient) {
            LOTRLevelData.getData(entityplayer).distributeMQEvent(new LOTRMiniQuestEvent.OpenRedBook());
        }
        return itemstack;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
        final int activeQuests = playerData.getActiveMiniQuests().size();
        list.add(StatCollector.translateToLocalFormatted("item.lotr.redBook.activeQuests", new Object[] { activeQuests }));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        super.registerIcons(iconregister);
        LOTRItemRedBook.questOfferIcon = iconregister.registerIcon("lotr:questOffer");
    }
}
