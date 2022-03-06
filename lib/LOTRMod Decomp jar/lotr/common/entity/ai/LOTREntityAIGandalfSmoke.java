// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import lotr.common.item.LOTRItemHobbitPipe;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTREntityAIGandalfSmoke extends LOTREntityAIHobbitSmoke
{
    public LOTREntityAIGandalfSmoke(final LOTREntityNPC entity, final int chance) {
        super(entity, chance);
    }
    
    @Override
    protected ItemStack createConsumable() {
        final ItemStack pipe = new ItemStack(LOTRMod.hobbitPipe);
        LOTRItemHobbitPipe.setSmokeColor(pipe, 16);
        return pipe;
    }
}
