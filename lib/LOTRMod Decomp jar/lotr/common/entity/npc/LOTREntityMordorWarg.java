// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTREntityMordorWarg extends LOTREntityWarg
{
    public LOTREntityMordorWarg(final World world) {
        super(world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.setWargType(WargType.BLACK);
    }
    
    @Override
    public LOTREntityNPC createWargRider() {
        if (((Entity)this).rand.nextBoolean()) {
            this.setWargArmor(new ItemStack(LOTRMod.wargArmorMordor));
        }
        return ((Entity)this).worldObj.rand.nextBoolean() ? new LOTREntityMordorOrcArcher(((Entity)this).worldObj) : new LOTREntityMordorOrc(((Entity)this).worldObj);
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
