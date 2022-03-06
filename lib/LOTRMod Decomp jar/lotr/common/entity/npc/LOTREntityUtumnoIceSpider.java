// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRDamage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceSpider extends LOTREntitySpiderBase
{
    public LOTREntityUtumnoIceSpider(final World world) {
        super(world);
        super.isImmuneToFrost = true;
        super.isChilly = true;
    }
    
    @Override
    protected int getRandomSpiderScale() {
        return ((Entity)this).rand.nextInt(4);
    }
    
    @Override
    protected int getRandomSpiderType() {
        return LOTREntitySpiderBase.VENOM_SLOWNESS;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }
    
    @Override
    protected boolean canRideSpider() {
        return false;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityPlayerMP) {
                LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
            }
            return true;
        }
        return false;
    }
}
