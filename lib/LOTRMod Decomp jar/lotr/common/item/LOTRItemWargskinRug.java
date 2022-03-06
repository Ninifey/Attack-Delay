// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.entity.item.LOTREntityWargskinRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityWarg;

public class LOTRItemWargskinRug extends LOTRItemRugBase
{
    public LOTRItemWargskinRug() {
        super(LOTREntityWarg.WargType.wargTypeNames());
    }
    
    @Override
    protected LOTREntityRugBase createRug(final World world, final ItemStack itemstack) {
        final LOTREntityWargskinRug rug = new LOTREntityWargskinRug(world);
        rug.setRugType(LOTREntityWarg.WargType.forID(itemstack.getItemDamage()));
        return rug;
    }
}
