// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.util;

import java.lang.reflect.Field;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRReflection;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

public class LOTRLog
{
    public static Logger logger;
    
    public static void findLogger() {
        try {
            final Field[] declaredFields;
            final Field[] fields = declaredFields = MinecraftServer.class.getDeclaredFields();
            for (final Field f : declaredFields) {
                LOTRReflection.unlockFinalField(f);
                final Object obj = f.get(null);
                if (obj instanceof Logger) {
                    (LOTRLog.logger = (Logger)obj).info("LOTR: Found logger");
                    break;
                }
            }
        }
        catch (Exception e) {
            FMLLog.warning("LOTR: Failed to find logger!", new Object[0]);
            e.printStackTrace();
        }
    }
}
