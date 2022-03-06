// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fac;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

public class LOTRAlignmentBonusMap extends HashMap<LOTRFaction, Float>
{
    public Set<LOTRFaction> getChangedFactions() {
        final Set<LOTRFaction> changed = new HashSet<LOTRFaction>();
        for (final LOTRFaction fac : ((HashMap<LOTRFaction, V>)this).keySet()) {
            final float bonus = ((HashMap<K, Float>)this).get(fac);
            if (bonus != 0.0f) {
                changed.add(fac);
            }
        }
        return changed;
    }
}
