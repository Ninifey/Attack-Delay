// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import net.minecraft.world.World;

public enum LOTRMusicCategory
{
    DAY("day"), 
    NIGHT("night"), 
    CAVE("cave");
    
    public final String categoryName;
    
    private LOTRMusicCategory(final String s) {
        this.categoryName = s;
    }
    
    public static LOTRMusicCategory forName(final String s) {
        for (final LOTRMusicCategory cat : values()) {
            if (s.equalsIgnoreCase(cat.categoryName)) {
                return cat;
            }
        }
        return null;
    }
    
    public static boolean isDay(final World world) {
        return world.calculateSkylightSubtracted(1.0f) < 5;
    }
    
    public static boolean isCave(final World world, final int i, final int j, final int k) {
        return j < 50 && !world.canBlockSeeTheSky(i, j, k);
    }
}
