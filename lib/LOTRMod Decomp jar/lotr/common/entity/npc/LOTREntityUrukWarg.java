// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTREntityUrukWarg extends LOTREntityWarg
{
    public LOTREntityUrukWarg(final World world) {
        super(world);
    }
    
    @Override
    public LOTREntityNPC createWargRider() {
        if (((Entity)this).rand.nextBoolean()) {
            this.setWargArmor(new ItemStack(LOTRMod.wargArmorUruk));
        }
        return ((Entity)this).worldObj.rand.nextBoolean() ? new LOTREntityIsengardSnagaArcher(((Entity)this).worldObj) : new LOTREntityIsengardSnaga(((Entity)this).worldObj);
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.URUK_HAI;
    }
    
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
