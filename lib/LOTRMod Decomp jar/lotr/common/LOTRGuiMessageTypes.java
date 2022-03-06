// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.util.StatCollector;

public enum LOTRGuiMessageTypes
{
    FRIENDLY_FIRE("friendlyFire"), 
    UTUMNO_WARN("utumnoWarn"), 
    ENCHANTING("enchanting"), 
    ALIGN_DRAIN("alignDrain");
    
    public final String messageName;
    
    private LOTRGuiMessageTypes(final String s) {
        this.messageName = s;
    }
    
    public String getMessage() {
        return StatCollector.translateToLocal("lotr.gui.message." + this.messageName);
    }
    
    public String getSaveName() {
        return this.messageName;
    }
    
    public static LOTRGuiMessageTypes forSaveName(final String name) {
        for (final LOTRGuiMessageTypes message : values()) {
            if (message.getSaveName().equals(name)) {
                return message;
            }
        }
        return null;
    }
}
