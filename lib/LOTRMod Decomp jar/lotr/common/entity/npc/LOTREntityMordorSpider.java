// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityMordorSpider extends LOTREntitySpiderBase
{
    public LOTREntityMordorSpider(final World world) {
        super(world);
    }
    
    @Override
    protected int getRandomSpiderScale() {
        return 1 + ((Entity)this).rand.nextInt(3);
    }
    
    @Override
    protected int getRandomSpiderType() {
        return LOTREntitySpiderBase.VENOM_POISON;
    }
    
    @Override
    public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        return data;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (!((Entity)this).worldObj.isClient && ((Entity)this).rand.nextInt(3) == 0) {
            final LOTREntityNPC rider = ((Entity)this).rand.nextBoolean() ? new LOTREntityMordorOrcArcher(((Entity)this).worldObj) : new LOTREntityMordorOrc(((Entity)this).worldObj);
            rider.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rotationYaw, 0.0f);
            rider.onSpawnWithEgg(null);
            rider.isNPCPersistent = super.isNPCPersistent;
            ((Entity)this).worldObj.spawnEntityInWorld((Entity)rider);
            rider.mountEntity((Entity)this);
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMordorSpider;
    }
}
