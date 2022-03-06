// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.util.ResourceLocation;

public class LOTRCapes
{
    public static ResourceLocation GONDOR;
    public static ResourceLocation TOWER_GUARD;
    public static ResourceLocation RANGER;
    public static ResourceLocation RANGER_ITHILIEN;
    public static ResourceLocation LOSSARNACH;
    public static ResourceLocation PELARGIR;
    public static ResourceLocation BLACKROOT;
    public static ResourceLocation PINNATH_GELIN;
    public static ResourceLocation LAMEDON;
    public static ResourceLocation ROHAN;
    public static ResourceLocation DALE;
    public static ResourceLocation DUNLENDING_BERSERKER;
    public static ResourceLocation GALADHRIM;
    public static ResourceLocation GALADHRIM_TRADER;
    public static ResourceLocation WOOD_ELF;
    public static ResourceLocation HIGH_ELF;
    public static ResourceLocation RIVENDELL;
    public static ResourceLocation RIVENDELL_TRADER;
    public static ResourceLocation NEAR_HARAD;
    public static ResourceLocation SOUTHRON_CHAMPION;
    public static ResourceLocation GULF_HARAD;
    public static ResourceLocation TAURETHRIM;
    public static ResourceLocation GALADHRIM_SMITH;
    public static ResourceLocation DORWINION_CAPTAIN;
    public static ResourceLocation DORWINION_ELF_CAPTAIN;
    public static ResourceLocation GANDALF;
    
    private static ResourceLocation forName(final String s) {
        return new ResourceLocation("lotr:cape/" + s + ".png");
    }
    
    static {
        LOTRCapes.GONDOR = forName("gondor");
        LOTRCapes.TOWER_GUARD = forName("gondorTowerGuard");
        LOTRCapes.RANGER = forName("ranger");
        LOTRCapes.RANGER_ITHILIEN = forName("ranger_ithilien");
        LOTRCapes.LOSSARNACH = forName("lossarnach");
        LOTRCapes.PELARGIR = forName("pelargir");
        LOTRCapes.BLACKROOT = forName("blackroot");
        LOTRCapes.PINNATH_GELIN = forName("pinnathGelin");
        LOTRCapes.LAMEDON = forName("lamedon");
        LOTRCapes.ROHAN = forName("rohan");
        LOTRCapes.DALE = forName("dale");
        LOTRCapes.DUNLENDING_BERSERKER = forName("dunlendingBerserker");
        LOTRCapes.GALADHRIM = forName("galadhrim");
        LOTRCapes.GALADHRIM_TRADER = forName("galadhrimTrader");
        LOTRCapes.WOOD_ELF = forName("woodElf");
        LOTRCapes.HIGH_ELF = forName("highElf");
        LOTRCapes.RIVENDELL = forName("rivendell");
        LOTRCapes.RIVENDELL_TRADER = forName("rivendellTrader");
        LOTRCapes.NEAR_HARAD = forName("nearHarad");
        LOTRCapes.SOUTHRON_CHAMPION = forName("haradChampion");
        LOTRCapes.GULF_HARAD = forName("gulf");
        LOTRCapes.TAURETHRIM = forName("taurethrim");
        LOTRCapes.GALADHRIM_SMITH = forName("galadhrimSmith");
        LOTRCapes.DORWINION_CAPTAIN = forName("dorwinionCaptain");
        LOTRCapes.DORWINION_ELF_CAPTAIN = forName("dorwinionElfCaptain");
        LOTRCapes.GANDALF = forName("gandalf");
    }
}
