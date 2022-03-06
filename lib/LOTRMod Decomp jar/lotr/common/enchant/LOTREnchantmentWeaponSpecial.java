// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.Entity;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.LOTRDamage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import lotr.common.item.LOTRItemBalrogWhip;
import net.minecraft.util.StatCollector;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentWeaponSpecial extends LOTREnchantment
{
    private boolean compatibleBane;
    private boolean compatibleOtherSpecial;
    
    public LOTREnchantmentWeaponSpecial(final String s) {
        super(s, new LOTREnchantmentType[] { LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE, LOTREnchantmentType.RANGED_LAUNCHER });
        this.compatibleBane = true;
        this.compatibleOtherSpecial = false;
        this.setValueModifier(3.0f);
        this.setBypassAnvilLimit();
    }
    
    public LOTREnchantmentWeaponSpecial setIncompatibleBane() {
        this.compatibleBane = false;
        return this;
    }
    
    public LOTREnchantmentWeaponSpecial setCompatibleOtherSpecial() {
        this.compatibleOtherSpecial = true;
        return this;
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
            return StatCollector.translateToLocalFormatted("lotr.enchant." + super.enchantName + ".desc.melee", new Object[0]);
        }
        return StatCollector.translateToLocalFormatted("lotr.enchant." + super.enchantName + ".desc.ranged", new Object[0]);
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (super.canApply(itemstack, considering)) {
            final Item item = itemstack.getItem();
            return !(item instanceof LOTRItemBalrogWhip) || (this != LOTREnchantment.fire && this != LOTREnchantment.chill);
        }
        return false;
    }
    
    @Override
    public boolean isCompatibleWith(final LOTREnchantment other) {
        return (this.compatibleBane || !(other instanceof LOTREnchantmentBane)) && (this.compatibleOtherSpecial || !(other instanceof LOTREnchantmentWeaponSpecial) || ((LOTREnchantmentWeaponSpecial)other).compatibleOtherSpecial);
    }
    
    public static int getFireAmount() {
        return 2;
    }
    
    public static void doChillAttack(final EntityLivingBase entity) {
        if (entity instanceof EntityPlayerMP) {
            LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
        }
        final int duration = 5;
        entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 1));
        final LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.CHILLING, (Entity)entity);
        LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)entity, 64.0));
    }
}
