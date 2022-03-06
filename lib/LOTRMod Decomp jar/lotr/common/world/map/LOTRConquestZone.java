// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import net.minecraft.world.World;
import lotr.common.fac.LOTRFaction;
import java.util.List;

public class LOTRConquestZone
{
    public final int gridX;
    public final int gridZ;
    public boolean isDummyZone;
    private float[] conquestStrengths;
    public static List<LOTRFaction> allPlayableFacs;
    private long lastChangeTime;
    private long isEmptyKey;
    private boolean isLoaded;
    private boolean clientSide;
    
    public LOTRConquestZone(final int i, final int k) {
        this.isDummyZone = false;
        this.isEmptyKey = 0L;
        this.isLoaded = true;
        this.clientSide = false;
        this.gridX = i;
        this.gridZ = k;
        if (LOTRConquestZone.allPlayableFacs == null) {
            LOTRConquestZone.allPlayableFacs = LOTRFaction.getPlayableAlignmentFactions();
            if (LOTRConquestZone.allPlayableFacs.size() >= 62) {
                throw new RuntimeException("Too many factions! Need to upgrade LOTRConquestZone data format.");
            }
        }
        this.conquestStrengths = new float[LOTRConquestZone.allPlayableFacs.size()];
    }
    
    public LOTRConquestZone setClientSide() {
        this.clientSide = true;
        return this;
    }
    
    public LOTRConquestZone setDummyZone() {
        this.isDummyZone = true;
        return this;
    }
    
    public float getConquestStrength(final LOTRFaction fac, final World world) {
        return this.getConquestStrength(fac, world.getTotalWorldTime());
    }
    
    public float getConquestStrength(final LOTRFaction fac, final long worldTime) {
        float str = this.getConquestStrengthRaw(fac);
        str -= this.calcTimeStrReduction(worldTime);
        str = Math.max(str, 0.0f);
        return str;
    }
    
    public float getConquestStrengthRaw(final LOTRFaction fac) {
        if (!fac.isPlayableAlignmentFaction()) {
            return 0.0f;
        }
        final int index = LOTRConquestZone.allPlayableFacs.indexOf(fac);
        final float str = this.conquestStrengths[index];
        return str;
    }
    
    public void setConquestStrengthRaw(final LOTRFaction fac, float str) {
        if (!fac.isPlayableAlignmentFaction()) {
            return;
        }
        if (str < 0.0f) {
            str = 0.0f;
        }
        final int index = LOTRConquestZone.allPlayableFacs.indexOf(fac);
        this.conquestStrengths[index] = str;
        if (str == 0.0f) {
            this.isEmptyKey &= ~(1L << index);
        }
        else {
            this.isEmptyKey |= 1L << index;
        }
        this.markDirty();
    }
    
    public void setConquestStrength(final LOTRFaction fac, final float str, final World world) {
        this.setConquestStrengthRaw(fac, str);
        this.updateAllOtherFactions(fac, world);
        this.lastChangeTime = world.getTotalWorldTime();
        this.markDirty();
    }
    
    public void addConquestStrength(final LOTRFaction fac, final float add, final World world) {
        float str = this.getConquestStrength(fac, world);
        str += add;
        this.setConquestStrength(fac, str, world);
    }
    
    private void updateAllOtherFactions(final LOTRFaction fac, final World world) {
        for (int i = 0; i < this.conquestStrengths.length; ++i) {
            final LOTRFaction otherFac = LOTRConquestZone.allPlayableFacs.get(i);
            if (otherFac != fac && this.conquestStrengths[i] > 0.0f) {
                final float otherStr = this.getConquestStrength(otherFac, world);
                this.setConquestStrengthRaw(otherFac, otherStr);
            }
        }
    }
    
    public void checkForEmptiness(final World world) {
        boolean emptyCheck = true;
        for (final LOTRFaction fac : LOTRConquestZone.allPlayableFacs) {
            final float str = this.getConquestStrength(fac, world);
            if (str != 0.0f) {
                emptyCheck = false;
                break;
            }
        }
        if (emptyCheck) {
            this.conquestStrengths = new float[LOTRConquestZone.allPlayableFacs.size()];
            this.isEmptyKey = 0L;
            this.markDirty();
        }
    }
    
    public void clearAllFactions(final World world) {
        for (final LOTRFaction fac : LOTRConquestZone.allPlayableFacs) {
            this.setConquestStrengthRaw(fac, 0.0f);
        }
        this.lastChangeTime = world.getTotalWorldTime();
        this.markDirty();
    }
    
    public long getLastChangeTime() {
        return this.lastChangeTime;
    }
    
    public void setLastChangeTime(final long l) {
        this.lastChangeTime = l;
        this.markDirty();
    }
    
    private float calcTimeStrReduction(final long worldTime) {
        final int dl = (int)(worldTime - this.lastChangeTime);
        float s = dl / 20.0f;
        final float graceCap = 1800.0f;
        if (s > graceCap) {
            s -= graceCap;
            final float decayRate = 900.0f;
            return s / decayRate;
        }
        return 0.0f;
    }
    
    public boolean isEmpty() {
        return this.isEmptyKey == 0L;
    }
    
    public void markDirty() {
        if (this.isLoaded && !this.clientSide) {
            LOTRConquestGrid.markZoneDirty(this);
        }
    }
    
    @Override
    public String toString() {
        return "LOTRConquestZone: " + this.gridX + ", " + this.gridZ;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setShort("X", (short)this.gridX);
        nbt.setShort("Z", (short)this.gridZ);
        nbt.setLong("Time", this.lastChangeTime);
        for (int i = 0; i < this.conquestStrengths.length; ++i) {
            final LOTRFaction fac = LOTRConquestZone.allPlayableFacs.get(i);
            final String name = fac.codeName() + "_str";
            final float str = this.conquestStrengths[i];
            if (str != 0.0f) {
                nbt.setFloat(name, str);
            }
        }
    }
    
    public static LOTRConquestZone readFromNBT(final NBTTagCompound nbt) {
        final int x = nbt.getShort("X");
        final int z = nbt.getShort("Z");
        final long time = nbt.getLong("Time");
        final LOTRConquestZone zone = new LOTRConquestZone(x, z);
        zone.isLoaded = false;
        zone.lastChangeTime = time;
        for (int i = 0; i < LOTRConquestZone.allPlayableFacs.size(); ++i) {
            final LOTRFaction fac = LOTRConquestZone.allPlayableFacs.get(i);
            final String name = fac.codeName() + "_str";
            if (nbt.hasKey(name)) {
                final float str = nbt.getFloat(name);
                zone.setConquestStrengthRaw(fac, str);
            }
        }
        zone.isLoaded = true;
        return zone;
    }
    
    static {
        LOTRConquestZone.allPlayableFacs = null;
    }
}
