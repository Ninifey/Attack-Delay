// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;

public class LOTREntityAlignmentBonus extends Entity
{
    public int particleAge;
    public String name;
    public LOTRFaction mainFaction;
    public float prevMainAlignment;
    public LOTRAlignmentBonusMap factionBonusMap;
    public boolean isKill;
    public float conquestBonus;
    
    public LOTREntityAlignmentBonus(final World world, final double d, final double d1, final double d2, final String s, final LOTRFaction f, final float pre, final LOTRAlignmentBonusMap fMap, final boolean kill, final float conqBonus) {
        super(world);
        this.setSize(0.5f, 0.5f);
        super.yOffset = super.height / 2.0f;
        this.setPosition(d, d1, d2);
        this.particleAge = 0;
        this.name = s;
        this.mainFaction = f;
        this.prevMainAlignment = pre;
        this.factionBonusMap = fMap;
        this.isKill = kill;
        this.conquestBonus = conqBonus;
    }
    
    protected void entityInit() {
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
    }
    
    public void onUpdate() {
        super.onUpdate();
        ++this.particleAge;
        if (this.particleAge >= 80) {
            this.setDead();
        }
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public boolean isEntityInvulnerable() {
        return true;
    }
    
    public boolean canBePushed() {
        return false;
    }
}
