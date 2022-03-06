// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class LOTRPoisonedDrinks
{
    private static final int POISON_DURATION = 300;
    public static Potion killingPoison;
    
    public static void registerPotion() {
        LOTRPoisonedDrinks.killingPoison = new LOTRPotionPoisonKilling();
    }
    
    public static void addPoisonEffect(final EntityPlayer entityplayer, final ItemStack itemstack) {
        final int duration = 300;
        entityplayer.addPotionEffect(new PotionEffect(LOTRPoisonedDrinks.killingPoison.id, duration));
    }
    
    public static boolean canPoison(final ItemStack itemstack) {
        return itemstack != null && LOTRItemMug.isItemFullDrink(itemstack);
    }
    
    public static boolean isDrinkPoisoned(final ItemStack itemstack) {
        return itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PoisonDrink") && itemstack.getTagCompound().getBoolean("PoisonDrink");
    }
    
    public static void setDrinkPoisoned(final ItemStack itemstack, final boolean flag) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("PoisonDrink", flag);
    }
    
    public static UUID getPoisonerUUID(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PoisonerUUID")) {
            final String s = itemstack.getTagCompound().getString("PoisonerUUID");
            return UUID.fromString(s);
        }
        return null;
    }
    
    public static void setPoisonerPlayer(final ItemStack itemstack, final EntityPlayer entityplayer) {
        setPoisonerUUID(itemstack, entityplayer.getUniqueID());
    }
    
    public static void setPoisonerUUID(final ItemStack itemstack, final UUID uuid) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setString("PoisonerUUID", uuid.toString());
    }
    
    public static boolean canPlayerSeePoisoned(final ItemStack itemstack, final EntityPlayer entityplayer) {
        final UUID uuid = getPoisonerUUID(itemstack);
        return uuid == null || entityplayer.getUniqueID().equals(uuid) || entityplayer.capabilities.isCreativeMode;
    }
}
