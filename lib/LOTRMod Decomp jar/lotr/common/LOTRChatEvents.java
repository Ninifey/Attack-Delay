// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.event.HoverEvent;

public class LOTRChatEvents
{
    private static Class[][] hoverParams;
    public static HoverEvent.Action SHOW_LOTR_ACHIEVEMENT;
    
    public static void createEvents() {
        LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT = (HoverEvent.Action)EnumHelper.addEnum(LOTRChatEvents.hoverParams, (Class)HoverEvent.Action.class, "SHOW_LOTR_ACHIEVEMENT", new Object[] { "show_lotr_achievement", true });
        LOTRReflection.getHoverEventMappings().put(LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT.getCanonicalName(), LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT);
    }
    
    static {
        LOTRChatEvents.hoverParams = new Class[][] { { HoverEvent.Action.class, String.class, Boolean.TYPE } };
    }
}
