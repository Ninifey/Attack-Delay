// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import lotr.common.block.LOTRBlockOrcBomb;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispenseOrcBomb;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemOrcBomb extends ItemBlock
{
    public LOTRItemOrcBomb(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseOrcBomb());
    }
    
    public int getMetadata(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final int meta = itemstack.getItemDamage();
        final int strength = LOTRBlockOrcBomb.getBombStrengthLevel(meta);
        if (strength == 1) {
            list.add(StatCollector.translateToLocal("tile.lotr.orcBomb.doubleStrength"));
        }
        if (strength == 2) {
            list.add(StatCollector.translateToLocal("tile.lotr.orcBomb.tripleStrength"));
        }
        if (LOTRBlockOrcBomb.isFireBomb(meta)) {
            list.add(StatCollector.translateToLocal("tile.lotr.orcBomb.fire"));
        }
    }
}
