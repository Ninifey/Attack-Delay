// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import lotr.common.LOTRConfig;
import net.minecraft.command.server.CommandMessage;

public class LOTRCommandMessageFixed extends CommandMessage
{
    public boolean isUsernameIndex(final String[] args, final int i) {
        return !LOTRConfig.preventMessageExploit && super.isUsernameIndex(args, i);
    }
}
