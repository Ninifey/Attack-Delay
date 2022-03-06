// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityUtumnoFireWarg extends LOTREntityUtumnoWarg
{
    public LOTREntityUtumnoFireWarg(final World world) {
        super(world);
        ((Entity)this).isImmuneToFire = true;
    }
    
    public void entityInit() {
        super.entityInit();
        this.setWargType(WargType.FIRE);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        final String s = (((Entity)this).rand.nextInt(3) > 0) ? "flame" : "smoke";
        ((Entity)this).worldObj.spawnParticle(s, ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, 0.0, 0.0, 0.0);
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean flag = super.attackEntityAsMob(entity);
        if (!((Entity)this).worldObj.isClient && flag) {
            entity.setFire(4);
        }
        return flag;
    }
}
