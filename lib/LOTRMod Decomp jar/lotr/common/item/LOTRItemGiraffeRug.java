// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.entity.item.LOTREntityGiraffeRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemGiraffeRug extends LOTRItemRugBase
{
    public LOTRItemGiraffeRug() {
        super(new String[] { "giraffe" });
    }
    
    @Override
    protected LOTREntityRugBase createRug(final World world, final ItemStack itemstack) {
        final LOTREntityGiraffeRug rug = new LOTREntityGiraffeRug(world);
        return rug;
    }
}
