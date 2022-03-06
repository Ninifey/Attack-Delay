// 
// Decompiled by Procyon v0.5.36
// 

package lotr.compatibility;

import lotr.common.util.LOTRLog;

public class LOTRModChecker
{
    private static int hasNEI;
    private static int isCauldron;
    private static int hasShaders;
    
    public static boolean hasNEI() {
        if (LOTRModChecker.hasNEI == -1) {
            try {
                if (Class.forName("codechicken.nei.api.API") != null) {
                    LOTRLog.logger.info("LOTR: Found NEI installed");
                    LOTRModChecker.hasNEI = 1;
                }
                else {
                    LOTRModChecker.hasNEI = 0;
                }
            }
            catch (ClassNotFoundException e) {
                LOTRModChecker.hasNEI = 0;
            }
        }
        return LOTRModChecker.hasNEI == 1;
    }
    
    public static boolean isCauldronServer() {
        if (LOTRModChecker.isCauldron == -1) {
            try {
                if (Class.forName("kcauldron.KCauldronClassTransformer") != null) {
                    System.out.println("LOTR: Found Cauldron installed");
                    if (LOTRLog.logger != null) {
                        LOTRLog.logger.info("LOTR: Found Cauldron installed");
                    }
                    LOTRModChecker.isCauldron = 1;
                    return true;
                }
            }
            catch (ClassNotFoundException ex) {}
            try {
                if (Class.forName("thermos.ThermosClassTransformer") != null) {
                    System.out.println("LOTR: Found Thermos installed");
                    if (LOTRLog.logger != null) {
                        LOTRLog.logger.info("LOTR: Found Thermos installed");
                    }
                    LOTRModChecker.isCauldron = 1;
                    return true;
                }
            }
            catch (ClassNotFoundException ex2) {}
            LOTRModChecker.isCauldron = 0;
            return false;
        }
        return LOTRModChecker.isCauldron == 1;
    }
    
    public static boolean hasShaders() {
        if (LOTRModChecker.hasShaders == -1) {
            try {
                if (Class.forName("shadersmodcore.client.Shaders") != null) {
                    LOTRLog.logger.info("LOTR: Found shaders installed");
                    LOTRModChecker.hasShaders = 1;
                }
                else {
                    LOTRModChecker.hasShaders = 0;
                }
            }
            catch (ClassNotFoundException e) {
                LOTRModChecker.hasShaders = 0;
            }
        }
        return LOTRModChecker.hasShaders == 1;
    }
    
    static {
        LOTRModChecker.hasNEI = -1;
        LOTRModChecker.isCauldron = -1;
        LOTRModChecker.hasShaders = -1;
    }
}
