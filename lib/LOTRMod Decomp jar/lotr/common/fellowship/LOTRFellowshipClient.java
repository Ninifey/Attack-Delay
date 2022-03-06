// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fellowship;

import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import lotr.common.LOTRTitle;
import java.util.Map;
import java.util.List;
import net.minecraft.item.ItemStack;
import java.util.UUID;

public class LOTRFellowshipClient
{
    private UUID fellowshipID;
    private String fellowshipName;
    private ItemStack fellowshipIcon;
    private boolean isOwned;
    private boolean isAdminned;
    private String ownerName;
    private List<String> allPlayerNames;
    private List<String> memberNames;
    private Map<String, LOTRTitle.PlayerTitle> titleMap;
    private Set<String> adminNames;
    private boolean preventPVP;
    private boolean preventHiredFF;
    private boolean showMapLocations;
    
    public LOTRFellowshipClient(final UUID id, final String name, final boolean owned, final boolean admin, final List<String> members) {
        this.allPlayerNames = new ArrayList<String>();
        this.memberNames = new ArrayList<String>();
        this.titleMap = new HashMap<String, LOTRTitle.PlayerTitle>();
        this.adminNames = new HashSet<String>();
        this.fellowshipID = id;
        this.fellowshipName = name;
        this.isOwned = owned;
        this.isAdminned = admin;
        this.ownerName = members.get(0);
        this.allPlayerNames = members;
        (this.memberNames = new ArrayList<String>(this.allPlayerNames)).remove(this.ownerName);
    }
    
    public void setTitles(final Map<String, LOTRTitle.PlayerTitle> titles) {
        this.titleMap = titles;
    }
    
    public void setAdmins(final Set<String> admins) {
        this.adminNames = admins;
    }
    
    public void setIcon(final ItemStack itemstack) {
        this.fellowshipIcon = itemstack;
    }
    
    public void setPreventPVP(final boolean flag) {
        this.preventPVP = flag;
    }
    
    public void setPreventHiredFriendlyFire(final boolean flag) {
        this.preventHiredFF = flag;
    }
    
    public void setShowMapLocations(final boolean flag) {
        this.showMapLocations = flag;
    }
    
    public UUID getFellowshipID() {
        return this.fellowshipID;
    }
    
    public String getName() {
        return this.fellowshipName;
    }
    
    public boolean isOwned() {
        return this.isOwned;
    }
    
    public boolean isAdminned() {
        return this.isAdminned;
    }
    
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public List<String> getMemberNames() {
        return this.memberNames;
    }
    
    public List<String> getAllPlayerNames() {
        return this.allPlayerNames;
    }
    
    public boolean isPlayerIn(final String name) {
        return this.allPlayerNames.contains(name);
    }
    
    public int getMemberCount() {
        return this.allPlayerNames.size();
    }
    
    public LOTRTitle.PlayerTitle getTitleFor(final String name) {
        return this.titleMap.get(name);
    }
    
    public boolean isAdmin(final String name) {
        return this.adminNames.contains(name);
    }
    
    public ItemStack getIcon() {
        return this.fellowshipIcon;
    }
    
    public boolean getPreventPVP() {
        return this.preventPVP;
    }
    
    public boolean getPreventHiredFriendlyFire() {
        return this.preventHiredFF;
    }
    
    public boolean getShowMapLocations() {
        return this.showMapLocations;
    }
    
    public void updateDataFrom(final LOTRFellowshipClient other) {
        this.fellowshipName = other.fellowshipName;
        this.fellowshipIcon = other.fellowshipIcon;
        this.isOwned = other.isOwned;
        this.isAdminned = other.isAdminned;
        this.ownerName = other.ownerName;
        this.allPlayerNames = other.allPlayerNames;
        this.memberNames = other.memberNames;
        this.titleMap = other.titleMap;
        this.adminNames = other.adminNames;
        this.preventPVP = other.preventPVP;
        this.preventHiredFF = other.preventHiredFF;
        this.showMapLocations = other.showMapLocations;
    }
}
