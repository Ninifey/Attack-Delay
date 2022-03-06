// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandTime;

public class LOTRCommandTimeVanilla extends CommandTime
{
    protected void setTime(final ICommandSender sender, final int time) {
        for (final WorldServer world : MinecraftServer.getServer().worldServers) {
            if (!(((World)world).provider instanceof LOTRWorldProvider)) {
                world.setWorldTime((long)time);
            }
        }
    }
    
    protected void addTime(final ICommandSender sender, final int time) {
        for (final WorldServer world : MinecraftServer.getServer().worldServers) {
            if (!(((World)world).provider instanceof LOTRWorldProvider)) {
                world.setWorldTime(world.getWorldTime() + time);
            }
        }
    }
}
