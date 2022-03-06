// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRItemHaradTurban extends LOTRItemHaradRobes
{
    @SideOnly(Side.CLIENT)
    private IIcon ornamentIcon;
    
    public LOTRItemHaradTurban() {
        super(0);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        super.registerIcons(iconregister);
        this.ornamentIcon = iconregister.registerIcon(this.getIconString() + "_ornament");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final ItemStack itemstack, final int pass) {
        if (pass == 1 && hasOrnament(itemstack)) {
            return this.ornamentIcon;
        }
        return ((Item)this).itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        if (pass == 1 && hasOrnament(itemstack)) {
            return 16777215;
        }
        return super.getColorFromItemStack(itemstack, pass);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        super.addInformation(itemstack, entityplayer, list, flag);
        if (hasOrnament(itemstack)) {
            list.add(StatCollector.translateToLocal("item.lotr.haradRobes.ornament"));
        }
    }
    
    public static boolean hasOrnament(final ItemStack itemstack) {
        return itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("TurbanOrnament") && itemstack.getTagCompound().getBoolean("TurbanOrnament");
    }
    
    public static void setHasOrnament(final ItemStack itemstack, final boolean flag) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("TurbanOrnament", flag);
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        return "lotr:armor/harad_turban.png";
    }
}
