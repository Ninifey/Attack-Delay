// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fellowship;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketFellowshipNotification;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatAllowedCharacters;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.network.Packet;
import net.minecraft.util.IChatComponent;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRLevelData;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import net.minecraft.item.ItemStack;
import java.util.UUID;

public class LOTRFellowship
{
    private boolean needsSave;
    private UUID fellowshipUUID;
    private String fellowshipName;
    private boolean disbanded;
    private ItemStack fellowshipIcon;
    private UUID ownerUUID;
    private List<UUID> memberUUIDs;
    private Set<UUID> adminUUIDs;
    private boolean preventPVP;
    private boolean preventHiredFF;
    private boolean showMapLocations;
    
    public LOTRFellowship() {
        this.needsSave = false;
        this.disbanded = false;
        this.memberUUIDs = new ArrayList<UUID>();
        this.adminUUIDs = new HashSet<UUID>();
        this.preventPVP = true;
        this.preventHiredFF = true;
        this.showMapLocations = true;
        this.fellowshipUUID = UUID.randomUUID();
    }
    
    public LOTRFellowship(final UUID fsID) {
        this.needsSave = false;
        this.disbanded = false;
        this.memberUUIDs = new ArrayList<UUID>();
        this.adminUUIDs = new HashSet<UUID>();
        this.preventPVP = true;
        this.preventHiredFF = true;
        this.showMapLocations = true;
        this.fellowshipUUID = fsID;
    }
    
    public LOTRFellowship(final UUID owner, final String name) {
        this();
        this.ownerUUID = owner;
        this.fellowshipName = name;
    }
    
    public void createAndRegister() {
        LOTRFellowshipData.addFellowship(this);
        LOTRLevelData.getData(this.ownerUUID).addFellowship(this);
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public void validate() {
        if (this.fellowshipUUID == null) {
            this.fellowshipUUID = UUID.randomUUID();
        }
        if (this.ownerUUID == null) {
            this.ownerUUID = UUID.randomUUID();
        }
    }
    
    public void markDirty() {
        this.needsSave = true;
    }
    
    public boolean needsSave() {
        return this.needsSave;
    }
    
    public void save(final NBTTagCompound fsData) {
        if (this.ownerUUID != null) {
            fsData.setString("Owner", this.ownerUUID.toString());
        }
        final NBTTagList memberTags = new NBTTagList();
        for (final UUID member : this.memberUUIDs) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Member", member.toString());
            if (this.adminUUIDs.contains(member)) {
                nbt.setBoolean("Admin", true);
            }
            memberTags.appendTag((NBTBase)nbt);
        }
        fsData.setTag("Members", (NBTBase)memberTags);
        if (this.fellowshipName != null) {
            fsData.setString("Name", this.fellowshipName);
        }
        if (this.fellowshipIcon != null) {
            final NBTTagCompound itemData = new NBTTagCompound();
            this.fellowshipIcon.writeToNBT(itemData);
            fsData.setTag("Icon", (NBTBase)itemData);
        }
        fsData.setBoolean("PreventPVP", this.preventPVP);
        fsData.setBoolean("PreventHiredFF", this.preventHiredFF);
        fsData.setBoolean("ShowMap", this.showMapLocations);
        this.needsSave = false;
    }
    
    public void load(final NBTTagCompound fsData) {
        if (fsData.hasKey("Owner")) {
            this.ownerUUID = UUID.fromString(fsData.getString("Owner"));
        }
        this.memberUUIDs.clear();
        this.adminUUIDs.clear();
        final NBTTagList memberTags = fsData.getTagList("Members", 10);
        for (int i = 0; i < memberTags.tagCount(); ++i) {
            final NBTTagCompound nbt = memberTags.getCompoundTagAt(i);
            final UUID member = UUID.fromString(nbt.getString("Member"));
            if (member != null) {
                this.memberUUIDs.add(member);
                if (nbt.hasKey("Admin")) {
                    final boolean isAdmin = nbt.getBoolean("Admin");
                    if (isAdmin) {
                        this.adminUUIDs.add(member);
                    }
                }
            }
        }
        if (fsData.hasKey("Name")) {
            this.fellowshipName = fsData.getString("Name");
        }
        if (fsData.hasKey("Icon")) {
            final NBTTagCompound itemData = fsData.getCompoundTag("Icon");
            this.fellowshipIcon = ItemStack.loadItemStackFromNBT(itemData);
        }
        if (fsData.hasKey("PreventPVP")) {
            this.preventPVP = fsData.getBoolean("PreventPVP");
        }
        if (fsData.hasKey("PreventPVP")) {
            this.preventHiredFF = fsData.getBoolean("PreventHiredFF");
        }
        if (fsData.hasKey("ShowMap")) {
            this.showMapLocations = fsData.getBoolean("ShowMap");
        }
        this.validate();
    }
    
    public UUID getFellowshipID() {
        return this.fellowshipUUID;
    }
    
    public UUID getOwner() {
        return this.ownerUUID;
    }
    
    public boolean isOwner(final UUID player) {
        return this.ownerUUID.equals(player);
    }
    
    public void setOwner(final UUID owner) {
        final UUID prevOwner = this.ownerUUID;
        if (prevOwner != null && !this.memberUUIDs.contains(prevOwner)) {
            this.memberUUIDs.add(0, prevOwner);
        }
        this.ownerUUID = owner;
        if (this.memberUUIDs.contains(owner)) {
            this.memberUUIDs.remove(owner);
        }
        if (this.adminUUIDs.contains(owner)) {
            this.adminUUIDs.remove(owner);
        }
        LOTRLevelData.getData(this.ownerUUID).addFellowship(this);
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public String getName() {
        return this.fellowshipName;
    }
    
    public void setName(final String name) {
        this.fellowshipName = name;
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public boolean containsPlayer(final UUID player) {
        return this.isOwner(player) || this.hasMember(player);
    }
    
    public boolean hasMember(final UUID player) {
        return this.memberUUIDs.contains(player);
    }
    
    public void addMember(final UUID player) {
        if (!this.isOwner(player) && !this.memberUUIDs.contains(player)) {
            this.memberUUIDs.add(player);
            LOTRLevelData.getData(player).addFellowship(this);
            this.updateForAllMembers();
            this.markDirty();
        }
    }
    
    public void removeMember(final UUID player) {
        if (this.memberUUIDs.contains(player)) {
            this.memberUUIDs.remove(player);
            if (this.adminUUIDs.contains(player)) {
                this.adminUUIDs.remove(player);
            }
            LOTRLevelData.getData(player).removeFellowship(this);
            this.updateForAllMembers();
            this.markDirty();
        }
    }
    
    public List<UUID> getMemberUUIDs() {
        return this.memberUUIDs;
    }
    
    public List<UUID> getAllPlayerUUIDs() {
        final List<UUID> list = new ArrayList<UUID>();
        list.add(this.ownerUUID);
        list.addAll(this.memberUUIDs);
        return list;
    }
    
    public boolean isAdmin(final UUID player) {
        return this.hasMember(player) && this.adminUUIDs.contains(player);
    }
    
    public void setAdmin(final UUID player, final boolean flag) {
        if (this.memberUUIDs.contains(player)) {
            if (flag && !this.adminUUIDs.contains(player)) {
                this.adminUUIDs.add(player);
                this.updateForAllMembers();
                this.markDirty();
            }
            else if (!flag && this.adminUUIDs.contains(player)) {
                this.adminUUIDs.remove(player);
                this.updateForAllMembers();
                this.markDirty();
            }
        }
    }
    
    public ItemStack getIcon() {
        return this.fellowshipIcon;
    }
    
    public void setIcon(final ItemStack itemstack) {
        this.fellowshipIcon = itemstack;
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public boolean getPreventPVP() {
        return this.preventPVP;
    }
    
    public void setPreventPVP(final boolean flag) {
        this.preventPVP = flag;
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public boolean getPreventHiredFriendlyFire() {
        return this.preventHiredFF;
    }
    
    public void setPreventHiredFriendlyFire(final boolean flag) {
        this.preventHiredFF = flag;
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public boolean getShowMapLocations() {
        return this.showMapLocations;
    }
    
    public void setShowMapLocations(final boolean flag) {
        this.showMapLocations = flag;
        this.updateForAllMembers();
        this.markDirty();
    }
    
    public void updateForAllMembers() {
        for (final UUID player : this.getAllPlayerUUIDs()) {
            LOTRLevelData.getData(player).updateFellowship(this);
        }
    }
    
    public void disband() {
        this.disbanded = true;
        final List<UUID> copyMemberIDs = new ArrayList<UUID>(this.memberUUIDs);
        for (final UUID player : copyMemberIDs) {
            this.removeMember(player);
        }
    }
    
    public boolean isDisbanded() {
        return this.disbanded;
    }
    
    public void sendFellowshipMessage(final EntityPlayerMP sender, String message) {
        if (sender.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            final ChatComponentTranslation msgCannotSend = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            msgCannotSend.getChatStyle().setColor(EnumChatFormatting.RED);
            sender.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)msgCannotSend));
        }
        else {
            sender.func_143004_u();
            message = StringUtils.normalizeSpace(message);
            if (StringUtils.isBlank((CharSequence)message)) {
                return;
            }
            for (int i = 0; i < message.length(); ++i) {
                if (!ChatAllowedCharacters.isAllowedCharacter(message.charAt(i))) {
                    sender.playerNetServerHandler.kickPlayerFromServer("Illegal characters in chat");
                    return;
                }
            }
            final ClickEvent fMsgClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fmsg \"" + this.getName() + "\" ");
            final IChatComponent msgComponent = ForgeHooks.newChatWithLinks(message);
            msgComponent.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            final IChatComponent senderComponent = sender.func_145748_c_();
            senderComponent.getChatStyle().setChatClickEvent(fMsgClickEvent);
            ChatComponentTranslation chatComponent = new ChatComponentTranslation("chat.type.text", new Object[] { senderComponent, "" });
            chatComponent = ForgeHooks.onServerChatEvent(sender.playerNetServerHandler, message, chatComponent);
            if (chatComponent == null) {
                return;
            }
            chatComponent.appendSibling(msgComponent);
            final IChatComponent fsComponent = (IChatComponent)new ChatComponentTranslation("commands.lotr.fmsg.fsPrefix", new Object[] { this.getName() });
            fsComponent.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            fsComponent.getChatStyle().setChatClickEvent(fMsgClickEvent);
            final IChatComponent fullChatComponent = (IChatComponent)new ChatComponentTranslation("%s %s", new Object[] { fsComponent, chatComponent });
            final MinecraftServer server = MinecraftServer.getServer();
            server.addChatMessage(fullChatComponent);
            final Packet packetChat = (Packet)new S02PacketChat(fullChatComponent, false);
            for (final Object player : server.getConfigurationManager().playerEntityList) {
                final EntityPlayerMP entityplayer = (EntityPlayerMP)player;
                if (this.containsPlayer(entityplayer.getUniqueID())) {
                    entityplayer.playerNetServerHandler.sendPacket(packetChat);
                }
            }
        }
    }
    
    public void sendNotification(final EntityPlayer entityplayer, final String key, final Object... args) {
        final IChatComponent message = (IChatComponent)new ChatComponentTranslation(key, args);
        message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        entityplayer.addChatMessage(message);
        final LOTRPacketFellowshipNotification packet = new LOTRPacketFellowshipNotification(message);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }
}
