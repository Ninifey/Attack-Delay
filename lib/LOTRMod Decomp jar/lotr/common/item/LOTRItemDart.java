// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import lotr.common.dispenser.LOTRDispenseDart;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemDart extends Item
{
    public boolean isPoisoned;
    
    public LOTRItemDart() {
        this.isPoisoned = false;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseDart(this));
    }
    
    public LOTRItemDart setPoisoned() {
        this.isPoisoned = true;
        return this;
    }
    
    public LOTREntityDart createDart(final World world, final ItemStack itemstack, final double d, final double d1, final double d2) {
        final LOTREntityDart dart = new LOTREntityDart(world, itemstack, d, d1, d2);
        return dart;
    }
    
    public LOTREntityDart createDart(final World world, final EntityLivingBase entity, final ItemStack itemstack, final float charge) {
        final LOTREntityDart dart = new LOTREntityDart(world, entity, itemstack, charge);
        return dart;
    }
    
    public LOTREntityDart createDart(final World world, final EntityLivingBase entity, final EntityLivingBase target, final ItemStack itemstack, final float charge, final float inaccuracy) {
        final LOTREntityDart dart = new LOTREntityDart(world, entity, target, itemstack, charge, inaccuracy);
        return dart;
    }
}
