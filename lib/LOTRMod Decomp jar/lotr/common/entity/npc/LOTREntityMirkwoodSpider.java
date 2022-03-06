// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMirkwoodSpider extends LOTREntitySpiderBase
{
    public LOTREntityMirkwoodSpider(final World world) {
        super(world);
    }
    
    @Override
    protected int getRandomSpiderScale() {
        return ((Entity)this).rand.nextInt(3);
    }
    
    @Override
    protected int getRandomSpiderType() {
        return ((Entity)this).rand.nextBoolean() ? 0 : (1 + ((Entity)this).rand.nextInt(2));
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DOL_GULDUR;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (flag && ((Entity)this).rand.nextInt(4) == 0) {
            this.func_145779_a(LOTRMod.mysteryWeb, 1);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMirkwoodSpider;
    }
}
