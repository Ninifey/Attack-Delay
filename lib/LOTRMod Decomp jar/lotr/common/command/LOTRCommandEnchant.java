// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.server.MinecraftServer;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.ICommand;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandEnchant extends CommandBase
{
    public String getCommandName() {
        return "lotrEnchant";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.lotrEnchant.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 2) {
            final EntityPlayerMP entityplayer = getPlayer(sender, args[0]);
            final ItemStack itemstack = entityplayer.getHeldItem();
            if (itemstack == null) {
                throw new WrongUsageException("commands.lotr.lotrEnchant.noItem", new Object[0]);
            }
            final String option = args[1];
            if (option.equals("add") && args.length >= 3) {
                final String enchName = args[2];
                final LOTREnchantment ench = LOTREnchantment.getEnchantmentByName(enchName);
                if (ench == null) {
                    throw new WrongUsageException("commands.lotr.lotrEnchant.unknown", new Object[] { enchName });
                }
                if (!LOTREnchantmentHelper.hasEnchant(itemstack, ench) && ench.canApply(itemstack, false) && LOTREnchantmentHelper.checkEnchantCompatible(itemstack, ench)) {
                    LOTREnchantmentHelper.setHasEnchant(itemstack, ench);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.lotrEnchant.add", new Object[] { enchName, entityplayer.getCommandSenderName(), itemstack.getDisplayName() });
                    return;
                }
                throw new WrongUsageException("commands.lotr.lotrEnchant.cannotAdd", new Object[] { enchName, itemstack.getDisplayName() });
            }
            else if (option.equals("remove") && args.length >= 3) {
                final String enchName = args[2];
                final LOTREnchantment ench = LOTREnchantment.getEnchantmentByName(enchName);
                if (ench == null) {
                    throw new WrongUsageException("commands.lotr.lotrEnchant.unknown", new Object[] { enchName });
                }
                if (LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                    LOTREnchantmentHelper.removeEnchant(itemstack, ench);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.lotrEnchant.remove", new Object[] { enchName, entityplayer.getCommandSenderName(), itemstack.getDisplayName() });
                    return;
                }
                throw new WrongUsageException("commands.lotr.lotrEnchant.cannotRemove", new Object[] { enchName, itemstack.getDisplayName() });
            }
            else if (option.equals("clear")) {
                LOTREnchantmentHelper.clearEnchantsAndProgress(itemstack);
                func_152373_a(sender, (ICommand)this, "commands.lotr.lotrEnchant.clear", new Object[] { entityplayer.getCommandSenderName(), itemstack.getDisplayName() });
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, new String[] { "add", "remove", "clear" });
        }
        if (args.length == 3) {
            if (args[1].equals("add")) {
                final EntityPlayerMP entityplayer = getPlayer(sender, args[0]);
                final ItemStack itemstack = entityplayer.getHeldItem();
                if (itemstack != null) {
                    final List<String> enchNames = new ArrayList<String>();
                    for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
                        if (!LOTREnchantmentHelper.hasEnchant(itemstack, ench) && ench.canApply(itemstack, false) && LOTREnchantmentHelper.checkEnchantCompatible(itemstack, ench)) {
                            enchNames.add(ench.enchantName);
                        }
                    }
                    return getListOfStringsMatchingLastWord(args, (String[])enchNames.toArray(new String[0]));
                }
            }
            else if (args[1].equals("remove")) {
                final EntityPlayerMP entityplayer = getPlayer(sender, args[0]);
                final ItemStack itemstack = entityplayer.getHeldItem();
                if (itemstack != null) {
                    final List<String> enchNames = new ArrayList<String>();
                    for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
                        if (LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                            enchNames.add(ench.enchantName);
                        }
                    }
                    return getListOfStringsMatchingLastWord(args, (String[])enchNames.toArray(new String[0]));
                }
            }
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return i == 1;
    }
}
