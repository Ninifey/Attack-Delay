// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketEnvironmentOverlay;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class LOTRDamage
{
    public static DamageSource frost;
    public static DamageSource poisonDrink;
    public static DamageSource plantHurt;
    
    public static void doFrostDamage(final EntityPlayerMP entityplayer) {
        final LOTRPacketEnvironmentOverlay packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.FROST);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public static void doBurnDamage(final EntityPlayerMP entityplayer) {
        final LOTRPacketEnvironmentOverlay packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.BURN);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    static {
        LOTRDamage.frost = new DamageSource("lotr.frost").setDamageBypassesArmor();
        LOTRDamage.poisonDrink = new DamageSource("lotr.poisonDrink").setDamageBypassesArmor().setMagicDamage();
        LOTRDamage.plantHurt = new DamageSource("lotr.plantHurt").setDamageBypassesArmor();
    }
}
