// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRSquadrons
{
    public static int SQUADRON_LENGTH_MAX;
    
    public static String checkAcceptableLength(String squadron) {
        if (squadron != null && squadron.length() > LOTRSquadrons.SQUADRON_LENGTH_MAX) {
            squadron = squadron.substring(0, LOTRSquadrons.SQUADRON_LENGTH_MAX);
        }
        return squadron;
    }
    
    public static boolean areSquadronsCompatible(final LOTREntityNPC npc, final ItemStack itemstack) {
        final String npcSquadron = npc.hiredNPCInfo.getSquadron();
        final String itemSquadron = getSquadron(itemstack);
        if (StringUtils.isNullOrEmpty(npcSquadron)) {
            return StringUtils.isNullOrEmpty(itemSquadron);
        }
        return npcSquadron.equalsIgnoreCase(itemSquadron);
    }
    
    public static String getSquadron(final ItemStack itemstack) {
        if (itemstack.getItem() instanceof SquadronItem && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRSquadron")) {
            final String squadron = itemstack.getTagCompound().getString("LOTRSquadron");
            return squadron;
        }
        return null;
    }
    
    public static void setSquadron(final ItemStack itemstack, final String squadron) {
        if (itemstack.getItem() instanceof SquadronItem) {
            if (itemstack.getTagCompound() == null) {
                itemstack.setTagCompound(new NBTTagCompound());
            }
            itemstack.getTagCompound().setString("LOTRSquadron", squadron);
        }
    }
    
    static {
        LOTRSquadrons.SQUADRON_LENGTH_MAX = 200;
    }
    
    public interface SquadronItem
    {
    }
}
