// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.entity.item.LOTREntityBearRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import lotr.common.entity.animal.LOTREntityBear;

public class LOTRItemBearRug extends LOTRItemRugBase
{
    public LOTRItemBearRug() {
        super(LOTREntityBear.BearType.bearTypeNames());
    }
    
    @Override
    protected LOTREntityRugBase createRug(final World world, final ItemStack itemstack) {
        final LOTREntityBearRug rug = new LOTREntityBearRug(world);
        rug.setRugType(LOTREntityBear.BearType.forID(itemstack.getItemDamage()));
        return rug;
    }
}
