// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRDamage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceWarg extends LOTREntityUtumnoWarg
{
    public LOTREntityUtumnoIceWarg(final World world) {
        super(world);
        super.isChilly = true;
    }
    
    public void entityInit() {
        super.entityInit();
        this.setWargType(WargType.ICE);
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityPlayerMP) {
                LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
            }
            if (entity instanceof EntityLivingBase) {
                final int difficulty = ((Entity)this).worldObj.difficultySetting.getDifficultyId();
                final int duration = difficulty * (difficulty + 5) / 2;
                if (duration > 0) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
}
