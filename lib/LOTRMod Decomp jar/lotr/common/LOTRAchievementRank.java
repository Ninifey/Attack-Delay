// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.util.StatCollector;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;

public class LOTRAchievementRank extends LOTRAchievement
{
    private LOTRFactionRank theRank;
    private LOTRFaction theFac;
    
    public LOTRAchievementRank(final LOTRFactionRank rank) {
        super(rank.fac.getAchieveCategory(), rank.fac.getAchieveCategory().getNextRankAchID(), LOTRMod.goldRing, "alignment_" + rank.fac.codeName() + "_" + rank.alignment);
        this.theRank = rank;
        this.theFac = this.theRank.fac;
        this.setRequiresAlly(this.theFac);
        this.setSpecial();
    }
    
    @Override
    public String getTitle(final EntityPlayer entityplayer) {
        return this.theRank.getFullNameWithGender(LOTRLevelData.getData(entityplayer));
    }
    
    @Override
    public String getDescription(final EntityPlayer entityplayer) {
        final String suffix = this.requiresPledge() ? "achieveRankPledge" : "achieveRank";
        return StatCollector.translateToLocalFormatted("lotr.faction." + this.theFac.codeName() + "." + suffix, new Object[] { LOTRAlignmentValues.formatAlignForDisplay(this.theRank.alignment) });
    }
    
    private boolean requiresPledge() {
        return this.theRank.isAbovePledgeRank();
    }
    
    @Override
    public boolean canPlayerEarn(final EntityPlayer entityplayer) {
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        final float align = pd.getAlignment(this.theFac);
        return align >= 0.0f && (!this.requiresPledge() || pd.isPledgedTo(this.theFac));
    }
    
    public boolean isPlayerRequiredRank(final EntityPlayer entityplayer) {
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        final float align = pd.getAlignment(this.theFac);
        final float rankAlign = this.theRank.alignment;
        return (!this.requiresPledge() || pd.isPledgedTo(this.theFac)) && align >= rankAlign;
    }
}
