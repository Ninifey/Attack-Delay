// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fellowship;

import net.minecraft.util.StatCollector;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import java.util.UUID;
import lotr.common.entity.item.LOTREntityBanner;
import com.mojang.authlib.GameProfile;

public class LOTRFellowshipProfile extends GameProfile
{
    public static final String fellowshipPrefix = "f/";
    private LOTREntityBanner theBanner;
    private String fellowshipName;
    
    public LOTRFellowshipProfile(final LOTREntityBanner banner, final UUID fsID, final String fsName) {
        super(fsID, fsName);
        this.theBanner = banner;
        this.fellowshipName = fsName;
    }
    
    public LOTRFellowship getFellowship() {
        final LOTRFellowship fs = LOTRFellowshipData.getFellowship(this.getId());
        if (fs != null && !fs.isDisbanded()) {
            return fs;
        }
        return null;
    }
    
    public LOTRFellowshipClient getFellowshipClient() {
        return LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer()).getClientFellowshipByName(this.fellowshipName);
    }
    
    public String getName() {
        return addFellowshipCode(super.getName());
    }
    
    public static boolean hasFellowshipCode(final String s) {
        return s.toLowerCase().startsWith("f/".toLowerCase());
    }
    
    public static String addFellowshipCode(final String s) {
        return "f/" + s;
    }
    
    public static String stripFellowshipCode(final String s) {
        return s.substring("f/".length());
    }
    
    public static String getFellowshipCodeHint() {
        return StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.fellowshipHint", new Object[] { "f/" });
    }
}
