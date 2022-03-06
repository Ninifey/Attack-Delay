// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.entity.player.EntityPlayer;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.event.FMLInterModComms;

public class LOTRInterModComms
{
    public static void update() {
        final ImmutableList<FMLInterModComms.IMCMessage> messages = (ImmutableList<FMLInterModComms.IMCMessage>)FMLInterModComms.fetchRuntimeMessages((Object)LOTRMod.instance);
        if (!messages.isEmpty()) {
            for (final FMLInterModComms.IMCMessage message : messages) {
                if (message.key.equals("SIEGE_ACTIVE")) {
                    final String playerName = message.getStringValue();
                    final EntityPlayer entityplayer = (EntityPlayer)MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName);
                    if (entityplayer == null) {
                        continue;
                    }
                    final int duration = 20;
                    LOTRLevelData.getData(entityplayer).setSiegeActive(duration);
                }
            }
        }
    }
}
