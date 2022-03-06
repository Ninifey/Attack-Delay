// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispenseRhunFireJar;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemRhunFireJar extends ItemBlock
{
    public LOTRItemRhunFireJar(final Block block) {
        super(block);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseRhunFireJar());
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        super.addInformation(itemstack, entityplayer, list, flag);
        list.add(StatCollector.translateToLocal("tile.lotr.rhunFire.warning"));
    }
}
