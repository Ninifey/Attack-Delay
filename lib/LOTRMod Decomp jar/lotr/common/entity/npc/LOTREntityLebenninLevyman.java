// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityLebenninLevyman extends LOTREntityGondorLevyman
{
    public LOTREntityLebenninLevyman(final World world) {
        super(world);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLebenninGambeson));
        return data;
    }
}
