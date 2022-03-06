// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.entity.EnumCreatureAttribute;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityElf;
import java.util.HashMap;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

public abstract class LOTREnchantment
{
    public static List<LOTREnchantment> allEnchantments;
    private static Map<String, LOTREnchantment> enchantsByName;
    public static final LOTREnchantment strong1;
    public static final LOTREnchantment strong2;
    public static final LOTREnchantment strong3;
    public static final LOTREnchantment strong4;
    public static final LOTREnchantment weak1;
    public static final LOTREnchantment weak2;
    public static final LOTREnchantment weak3;
    public static final LOTREnchantment baneElf;
    public static final LOTREnchantment baneOrc;
    public static final LOTREnchantment baneDwarf;
    public static final LOTREnchantment baneWarg;
    public static final LOTREnchantment baneTroll;
    public static final LOTREnchantment baneSpider;
    public static final LOTREnchantment baneWight;
    public static final LOTREnchantment baneWraith;
    public static final LOTREnchantment durable1;
    public static final LOTREnchantment durable2;
    public static final LOTREnchantment durable3;
    public static final LOTREnchantment meleeSpeed1;
    public static final LOTREnchantment meleeSlow1;
    public static final LOTREnchantment meleeReach1;
    public static final LOTREnchantment meleeUnreach1;
    public static final LOTREnchantment knockback1;
    public static final LOTREnchantment knockback2;
    public static final LOTREnchantment toolSpeed1;
    public static final LOTREnchantment toolSpeed2;
    public static final LOTREnchantment toolSpeed3;
    public static final LOTREnchantment toolSpeed4;
    public static final LOTREnchantment toolSlow1;
    public static final LOTREnchantment toolSilk;
    public static final LOTREnchantment looting1;
    public static final LOTREnchantment looting2;
    public static final LOTREnchantment looting3;
    public static final LOTREnchantment protect1;
    public static final LOTREnchantment protect2;
    public static final LOTREnchantment protectWeak1;
    public static final LOTREnchantment protectWeak2;
    public static final LOTREnchantment protectFire1;
    public static final LOTREnchantment protectFire2;
    public static final LOTREnchantment protectFire3;
    public static final LOTREnchantment protectFall1;
    public static final LOTREnchantment protectFall2;
    public static final LOTREnchantment protectFall3;
    public static final LOTREnchantment protectRanged1;
    public static final LOTREnchantment protectRanged2;
    public static final LOTREnchantment protectRanged3;
    public static final LOTREnchantment protectMithril;
    public static final LOTREnchantment rangedStrong1;
    public static final LOTREnchantment rangedStrong2;
    public static final LOTREnchantment rangedStrong3;
    public static final LOTREnchantment rangedWeak1;
    public static final LOTREnchantment rangedWeak2;
    public static final LOTREnchantment rangedKnockback1;
    public static final LOTREnchantment rangedKnockback2;
    public static final LOTREnchantment fire;
    public static final LOTREnchantment chill;
    public static final LOTREnchantment headhunting;
    public final String enchantName;
    public final List<LOTREnchantmentType> itemTypes;
    private int enchantWeight;
    private float valueModifier;
    private boolean skilful;
    private boolean persistsReforge;
    private boolean bypassAnvilLimit;
    private boolean applyToProjectile;
    
    public LOTREnchantment(final String s, final LOTREnchantmentType type) {
        this(s, new LOTREnchantmentType[] { type });
    }
    
    public LOTREnchantment(final String s, final LOTREnchantmentType[] types) {
        this.enchantWeight = 0;
        this.valueModifier = 1.0f;
        this.skilful = false;
        this.persistsReforge = false;
        this.bypassAnvilLimit = false;
        this.applyToProjectile = false;
        this.enchantName = s;
        this.itemTypes = Arrays.asList(types);
        LOTREnchantment.allEnchantments.add(this);
        LOTREnchantment.enchantsByName.put(this.enchantName, this);
    }
    
    public int getEnchantWeight() {
        return this.enchantWeight;
    }
    
    public LOTREnchantment setEnchantWeight(final int i) {
        this.enchantWeight = i;
        return this;
    }
    
    public float getValueModifier() {
        return this.valueModifier;
    }
    
    public LOTREnchantment setValueModifier(final float f) {
        this.valueModifier = f;
        return this;
    }
    
    public boolean isSkilful() {
        return this.skilful;
    }
    
    public LOTREnchantment setSkilful() {
        this.skilful = true;
        return this;
    }
    
    public boolean persistsReforge() {
        return this.persistsReforge;
    }
    
    public LOTREnchantment setPersistsReforge() {
        this.persistsReforge = true;
        return this;
    }
    
    public boolean bypassAnvilLimit() {
        return this.bypassAnvilLimit;
    }
    
    public LOTREnchantment setBypassAnvilLimit() {
        this.bypassAnvilLimit = true;
        return this;
    }
    
    public boolean applyToProjectile() {
        return this.applyToProjectile;
    }
    
    public LOTREnchantment setApplyToProjectile() {
        this.applyToProjectile = true;
        return this;
    }
    
    public String getDisplayName() {
        return StatCollector.translateToLocal("lotr.enchant." + this.enchantName);
    }
    
    public abstract String getDescription(final ItemStack p0);
    
    public final String getNamedFormattedDescription(final ItemStack itemstack) {
        String s = StatCollector.translateToLocalFormatted("lotr.enchant.descFormat", new Object[] { this.getDisplayName(), this.getDescription(itemstack) });
        if (this.isBeneficial()) {
            s = EnumChatFormatting.GRAY + s;
        }
        else {
            s = EnumChatFormatting.DARK_GRAY + s;
        }
        return s;
    }
    
    public abstract boolean isBeneficial();
    
    public IChatComponent getEarnMessage(final ItemStack itemstack) {
        final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("lotr.enchant." + this.enchantName + ".earn", new Object[] { itemstack.getDisplayName() });
        msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        return msg;
    }
    
    public IChatComponent getEarnMessageWithName(final EntityPlayer entityplayer, final ItemStack itemstack) {
        final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("lotr.enchant." + this.enchantName + ".earnName", new Object[] { entityplayer.getCommandSenderName(), itemstack.getDisplayName() });
        msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        return msg;
    }
    
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        for (final LOTREnchantmentType type : this.itemTypes) {
            if (type.canApply(itemstack, considering)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCompatibleWith(final LOTREnchantment other) {
        return this.getClass() != other.getClass();
    }
    
    public boolean hasTemplateItem() {
        return this.getEnchantWeight() > 0 && this.isBeneficial();
    }
    
    public static LOTREnchantment getEnchantmentByName(final String s) {
        return LOTREnchantment.enchantsByName.get(s);
    }
    
    protected final String formatAdditiveInt(final int i) {
        String s = String.valueOf(i);
        if (i >= 0) {
            s = "+" + s;
        }
        return s;
    }
    
    protected final String formatAdditive(final float f) {
        String s = this.formatDecimalNumber(f);
        if (f >= 0.0f) {
            s = "+" + s;
        }
        return s;
    }
    
    protected final String formatMultiplicative(final float f) {
        String s = this.formatDecimalNumber(f);
        s = "x" + s;
        return s;
    }
    
    private final String formatDecimalNumber(final float f) {
        final DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(1);
        final String s = df.format(f);
        return s;
    }
    
    static {
        LOTREnchantment.allEnchantments = new ArrayList<LOTREnchantment>();
        LOTREnchantment.enchantsByName = new HashMap<String, LOTREnchantment>();
        strong1 = new LOTREnchantmentDamage("strong1", 0.5f).setEnchantWeight(10);
        strong2 = new LOTREnchantmentDamage("strong2", 1.0f).setEnchantWeight(5);
        strong3 = new LOTREnchantmentDamage("strong3", 2.0f).setEnchantWeight(2).setSkilful();
        strong4 = new LOTREnchantmentDamage("strong4", 3.0f).setEnchantWeight(1).setSkilful();
        weak1 = new LOTREnchantmentDamage("weak1", -0.5f).setEnchantWeight(6);
        weak2 = new LOTREnchantmentDamage("weak2", -1.0f).setEnchantWeight(4);
        weak3 = new LOTREnchantmentDamage("weak3", -2.0f).setEnchantWeight(2);
        baneElf = new LOTREnchantmentBane("baneElf", 4.0f, (Class<? extends EntityLivingBase>[])new Class[] { LOTREntityElf.class }).setEnchantWeight(0);
        baneOrc = new LOTREnchantmentBane("baneOrc", 4.0f, (Class<? extends EntityLivingBase>[])new Class[] { LOTREntityOrc.class }).setEnchantWeight(0);
        baneDwarf = new LOTREnchantmentBane("baneDwarf", 4.0f, (Class<? extends EntityLivingBase>[])new Class[] { LOTREntityDwarf.class }).setEnchantWeight(0);
        baneWarg = new LOTREnchantmentBane("baneWarg", 4.0f, (Class<? extends EntityLivingBase>[])new Class[] { LOTREntityWarg.class }).setEnchantWeight(0);
        baneTroll = new LOTREnchantmentBane("baneTroll", 4.0f, (Class<? extends EntityLivingBase>[])new Class[] { LOTREntityTroll.class, LOTREntityHalfTroll.class }).setEnchantWeight(0);
        baneSpider = new LOTREnchantmentBane("baneSpider", 4.0f, EnumCreatureAttribute.ARTHROPOD).setEnchantWeight(0);
        baneWight = new LOTREnchantmentBane("baneWight", 4.0f, EnumCreatureAttribute.UNDEAD).setEnchantWeight(0);
        baneWraith = new LOTREnchantmentBane("baneWraith", 0.0f, (Class<? extends EntityLivingBase>[])new Class[] { LOTREntityMarshWraith.class }).setUnachievable().setEnchantWeight(0);
        durable1 = new LOTREnchantmentDurability("durable1", 1.25f).setEnchantWeight(15);
        durable2 = new LOTREnchantmentDurability("durable2", 1.5f).setEnchantWeight(8);
        durable3 = new LOTREnchantmentDurability("durable3", 2.0f).setEnchantWeight(4).setSkilful();
        meleeSpeed1 = new LOTREnchantmentMeleeSpeed("meleeSpeed1", 1.25f).setEnchantWeight(6);
        meleeSlow1 = new LOTREnchantmentMeleeSpeed("meleeSlow1", 0.75f).setEnchantWeight(4);
        meleeReach1 = new LOTREnchantmentMeleeReach("meleeReach1", 1.25f).setEnchantWeight(6);
        meleeUnreach1 = new LOTREnchantmentMeleeReach("meleeUnreach1", 0.75f).setEnchantWeight(4);
        knockback1 = new LOTREnchantmentKnockback("knockback1", 1).setEnchantWeight(6);
        knockback2 = new LOTREnchantmentKnockback("knockback2", 2).setEnchantWeight(2).setSkilful();
        toolSpeed1 = new LOTREnchantmentToolSpeed("toolSpeed1", 1.5f).setEnchantWeight(20);
        toolSpeed2 = new LOTREnchantmentToolSpeed("toolSpeed2", 2.0f).setEnchantWeight(10);
        toolSpeed3 = new LOTREnchantmentToolSpeed("toolSpeed3", 3.0f).setEnchantWeight(5).setSkilful();
        toolSpeed4 = new LOTREnchantmentToolSpeed("toolSpeed4", 4.0f).setEnchantWeight(2).setSkilful();
        toolSlow1 = new LOTREnchantmentToolSpeed("toolSlow1", 0.75f).setEnchantWeight(10);
        toolSilk = new LOTREnchantmentSilkTouch("toolSilk").setEnchantWeight(10).setSkilful();
        looting1 = new LOTREnchantmentLooting("looting1", 1).setEnchantWeight(6);
        looting2 = new LOTREnchantmentLooting("looting2", 2).setEnchantWeight(2).setSkilful();
        looting3 = new LOTREnchantmentLooting("looting3", 3).setEnchantWeight(1).setSkilful();
        protect1 = new LOTREnchantmentProtection("protect1", 1).setEnchantWeight(10);
        protect2 = new LOTREnchantmentProtection("protect2", 2).setEnchantWeight(3).setSkilful();
        protectWeak1 = new LOTREnchantmentProtection("protectWeak1", -1).setEnchantWeight(5);
        protectWeak2 = new LOTREnchantmentProtection("protectWeak2", -2).setEnchantWeight(2);
        protectFire1 = new LOTREnchantmentProtectionFire("protectFire1", 1).setEnchantWeight(5);
        protectFire2 = new LOTREnchantmentProtectionFire("protectFire2", 2).setEnchantWeight(2).setSkilful();
        protectFire3 = new LOTREnchantmentProtectionFire("protectFire3", 3).setEnchantWeight(1).setSkilful();
        protectFall1 = new LOTREnchantmentProtectionFall("protectFall1", 1).setEnchantWeight(5);
        protectFall2 = new LOTREnchantmentProtectionFall("protectFall2", 2).setEnchantWeight(2).setSkilful();
        protectFall3 = new LOTREnchantmentProtectionFall("protectFall3", 3).setEnchantWeight(1).setSkilful();
        protectRanged1 = new LOTREnchantmentProtectionRanged("protectRanged1", 1).setEnchantWeight(5);
        protectRanged2 = new LOTREnchantmentProtectionRanged("protectRanged2", 2).setEnchantWeight(2).setSkilful();
        protectRanged3 = new LOTREnchantmentProtectionRanged("protectRanged3", 3).setEnchantWeight(1).setSkilful();
        protectMithril = new LOTREnchantmentProtectionMithril("protectMithril").setEnchantWeight(0);
        rangedStrong1 = new LOTREnchantmentRangedDamage("rangedStrong1", 1.1f).setEnchantWeight(10);
        rangedStrong2 = new LOTREnchantmentRangedDamage("rangedStrong2", 1.2f).setEnchantWeight(3);
        rangedStrong3 = new LOTREnchantmentRangedDamage("rangedStrong3", 1.3f).setEnchantWeight(1).setSkilful();
        rangedWeak1 = new LOTREnchantmentRangedDamage("rangedWeak1", 0.75f).setEnchantWeight(8);
        rangedWeak2 = new LOTREnchantmentRangedDamage("rangedWeak2", 0.5f).setEnchantWeight(3);
        rangedKnockback1 = new LOTREnchantmentRangedKnockback("rangedKnockback1", 1).setEnchantWeight(6);
        rangedKnockback2 = new LOTREnchantmentRangedKnockback("rangedKnockback2", 2).setEnchantWeight(2).setSkilful();
        fire = new LOTREnchantmentWeaponSpecial("fire").setEnchantWeight(0).setApplyToProjectile();
        chill = new LOTREnchantmentWeaponSpecial("chill").setEnchantWeight(0).setApplyToProjectile();
        headhunting = new LOTREnchantmentWeaponSpecial("headhunting").setCompatibleOtherSpecial().setIncompatibleBane().setEnchantWeight(0).setApplyToProjectile();
    }
}
