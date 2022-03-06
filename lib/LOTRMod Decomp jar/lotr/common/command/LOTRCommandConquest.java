// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.fac.LOTRFaction;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRLevelData;
import net.minecraft.command.ICommand;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.command.WrongUsageException;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandConquest extends CommandBase
{
    public String getCommandName() {
        return "conquest";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.conquest.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        final World world = sender.getEntityWorld();
        if (!LOTRConquestGrid.conquestEnabled(world)) {
            throw new WrongUsageException("commands.lotr.conquest.notEnabled", new Object[0]);
        }
        if (args.length >= 1) {
            final String function = args[0];
            if (function.equals("clear")) {
                final Object[] obj = this.parseCoordsAndZone(sender, args, 1);
                final int posX = (int)obj[0];
                final int posZ = (int)obj[1];
                final LOTRConquestZone zone = (LOTRConquestZone)obj[2];
                zone.clearAllFactions(world);
                func_152373_a(sender, (ICommand)this, "commands.lotr.conquest.clear", new Object[] { posX, posZ });
                return;
            }
            if (function.equals("rate")) {
                if (args.length >= 2) {
                    final double rate = parseDoubleBounded(sender, args[1], 0.0, 100.0);
                    LOTRLevelData.setConquestRate((float)rate);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.conquest.rateSet", new Object[] { rate });
                    return;
                }
                final float currentRate = LOTRLevelData.getConquestRate();
                sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.lotr.conquest.rateGet", new Object[] { currentRate }));
                return;
            }
            else if (args.length >= 3 && (function.equals("set") || function.equals("add") || function.equals("radial"))) {
                final LOTRFaction fac = LOTRFaction.forName(args[1]);
                if (fac == null) {
                    throw new WrongUsageException("commands.lotr.conquest.noFaction", new Object[] { args[1] });
                }
                final float amount = (float)parseDouble(sender, args[2]);
                final Object[] obj2 = this.parseCoordsAndZone(sender, args, 3);
                final int posX2 = (int)obj2[0];
                final int posZ2 = (int)obj2[1];
                final LOTRConquestZone zone2 = (LOTRConquestZone)obj2[2];
                if (function.equals("set")) {
                    if (amount < 0.0f) {
                        throw new WrongUsageException("commands.lotr.conquest.tooLow", new Object[] { 0.0f });
                    }
                    if (amount > 100000.0f) {
                        throw new WrongUsageException("commands.lotr.conquest.tooHigh", new Object[] { 100000.0f });
                    }
                    zone2.setConquestStrength(fac, amount, world);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.conquest.set", new Object[] { fac.factionName(), amount, posX2, posZ2 });
                    return;
                }
                else if (function.equals("add")) {
                    final float currentStr = zone2.getConquestStrength(fac, world);
                    final float newStr = currentStr + amount;
                    if (newStr < 0.0f) {
                        throw new WrongUsageException("commands.lotr.conquest.tooLow", new Object[] { 0.0f });
                    }
                    if (newStr > 100000.0f) {
                        throw new WrongUsageException("commands.lotr.conquest.tooHigh", new Object[] { 100000.0f });
                    }
                    zone2.addConquestStrength(fac, amount, world);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.conquest.add", new Object[] { fac.factionName(), amount, posX2, posZ2 });
                    return;
                }
                else if (function.equals("radial")) {
                    final float centralStr = zone2.getConquestStrength(fac, world);
                    if (centralStr + amount > 100000.0f) {
                        throw new WrongUsageException("commands.lotr.conquest.tooHigh", new Object[] { 100000.0f });
                    }
                    final EntityPlayerMP senderIfPlayer = (sender instanceof EntityPlayerMP) ? sender : null;
                    if (amount < 0.0f) {
                        LOTRConquestGrid.doRadialConquest(world, zone2, (EntityPlayer)senderIfPlayer, null, fac, -amount, -amount);
                    }
                    else {
                        LOTRConquestGrid.doRadialConquest(world, zone2, (EntityPlayer)senderIfPlayer, fac, null, amount, amount);
                    }
                    func_152373_a(sender, (ICommand)this, "commands.lotr.conquest.radial", new Object[] { fac.factionName(), amount, posX2, posZ2 });
                    return;
                }
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    private Object[] parseCoordsAndZone(final ICommandSender sender, final String[] args, final int specifyIndex) {
        int posX = sender.getCommandSenderPosition().posX;
        int posZ = sender.getCommandSenderPosition().posZ;
        if (args.length >= specifyIndex + 2) {
            posX = parseInt(sender, args[specifyIndex]);
            posZ = parseInt(sender, args[specifyIndex + 1]);
        }
        final LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(posX, posZ);
        if (zone.isDummyZone) {
            throw new WrongUsageException("commands.lotr.conquest.outOfBounds", new Object[] { posX, posZ });
        }
        return new Object[] { posX, posZ, zone };
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "radial", "clear", "rate" });
        }
        if (args.length == 2 && (args[0].equals("set") || args[0].equals("add") || args[0].equals("radial"))) {
            final List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
            return getListOfStringsMatchingLastWord(args, (String[])list.toArray(new String[0]));
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
