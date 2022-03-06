// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.util.LOTRLog;
import net.minecraft.util.StringUtils;
import lotr.common.network.LOTRPacketBannerData;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.IBlockAccess;
import lotr.common.fellowship.LOTRFellowshipClient;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.fellowship.LOTRFellowshipProfile;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import lotr.common.LOTRBannerProtection;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRConfig;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.Entity;

public class LOTREntityBanner extends Entity
{
    private NBTTagCompound protectData;
    private boolean wasEverProtecting;
    private boolean playerSpecificProtection;
    private boolean structureProtection;
    private int customRange;
    private boolean selfProtection;
    public static float ALIGNMENT_PROTECTION_MIN;
    public static float ALIGNMENT_PROTECTION_MAX;
    private float alignmentProtection;
    public static int WHITELIST_DEFAULT;
    public static int WHITELIST_MIN;
    public static int WHITELIST_MAX;
    private GameProfile[] allowedPlayers;
    
    public LOTREntityBanner(final World world) {
        super(world);
        this.wasEverProtecting = false;
        this.structureProtection = false;
        this.selfProtection = true;
        this.alignmentProtection = LOTREntityBanner.ALIGNMENT_PROTECTION_MIN;
        this.allowedPlayers = new GameProfile[LOTREntityBanner.WHITELIST_DEFAULT];
        this.setSize(1.0f, 3.0f);
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(18, (Object)0);
    }
    
    private int getBannerTypeID() {
        return super.dataWatcher.getWatchableObjectByte(18);
    }
    
    private void setBannerTypeID(final int i) {
        super.dataWatcher.updateObject(18, (Object)(byte)i);
    }
    
    public void setBannerType(final LOTRItemBanner.BannerType type) {
        this.setBannerTypeID(type.bannerID);
    }
    
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.forID(this.getBannerTypeID());
    }
    
    public int getProtectionRange() {
        if (!this.structureProtection && !LOTRConfig.allowBannerProtection) {
            return 0;
        }
        if (this.customRange > 0) {
            return this.customRange;
        }
        final int i = MathHelper.floor_double(super.posX);
        final int j = MathHelper.floor_double(super.boundingBox.minY);
        final int k = MathHelper.floor_double(super.posZ);
        final Block block = super.worldObj.getBlock(i, j - 1, k);
        final int meta = super.worldObj.getBlockMetadata(i, j - 1, k);
        return LOTRBannerProtection.getProtectionRange(block, meta);
    }
    
    public boolean isProtectingTerritory() {
        return this.getProtectionRange() > 0;
    }
    
    public AxisAlignedBB createProtectionCube() {
        final int i = MathHelper.floor_double(super.posX);
        final int j = MathHelper.floor_double(super.boundingBox.minY);
        final int k = MathHelper.floor_double(super.posZ);
        final int range = this.getProtectionRange();
        return AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).expand((double)range, (double)range, (double)range);
    }
    
    public boolean isPlayerSpecificProtection() {
        return this.playerSpecificProtection;
    }
    
    public void setPlayerSpecificProtection(final boolean flag) {
        this.playerSpecificProtection = flag;
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public boolean isSelfProtection() {
        return LOTRConfig.allowSelfProtectingBanners && this.selfProtection;
    }
    
    public void setSelfProtection(final boolean flag) {
        this.selfProtection = flag;
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public boolean isStructureProtection() {
        return this.structureProtection;
    }
    
    public void setStructureProtection(final boolean flag) {
        this.structureProtection = flag;
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public void setCustomRange(final int i) {
        this.customRange = MathHelper.clamp_int(i, 0, 64);
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public float getAlignmentProtection() {
        return this.alignmentProtection;
    }
    
    public void setAlignmentProtection(final float f) {
        this.alignmentProtection = MathHelper.clamp_float(f, LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public void setPlacingPlayer(final EntityPlayer player) {
        this.whitelistPlayer(0, player.getGameProfile());
    }
    
    public GameProfile getPlacingPlayer() {
        return this.getWhitelistedPlayer(0);
    }
    
    public GameProfile getWhitelistedPlayer(final int index) {
        return this.allowedPlayers[index];
    }
    
    public void whitelistPlayer(final int index, final GameProfile profile) {
        if (index < 0 || index >= this.allowedPlayers.length) {
            return;
        }
        this.allowedPlayers[index] = profile;
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public void whitelistFellowship(final int index, final LOTRFellowship fs) {
        if (this.isValidFellowship(fs)) {
            this.whitelistPlayer(index, new LOTRFellowshipProfile(this, fs.getFellowshipID(), ""));
        }
    }
    
    public LOTRFellowship getPlacersFellowshipByName(final String fsName) {
        final GameProfile owner = this.getPlacingPlayer();
        if (owner != null) {
            final UUID ownerID = owner.getId();
            if (ownerID != null) {
                return LOTRLevelData.getData(ownerID).getFellowshipByName(fsName);
            }
        }
        return null;
    }
    
    public boolean isPlayerWhitelisted(final EntityPlayer entityplayer) {
        if (this.playerSpecificProtection) {
            final GameProfile playerProfile = entityplayer.getGameProfile();
            if (playerProfile != null && playerProfile.getId() != null) {
                final String playerName = playerProfile.getName();
                final UUID playerID = playerProfile.getId();
                for (final GameProfile profile : this.allowedPlayers) {
                    if (profile != null) {
                        if (profile instanceof LOTRFellowshipProfile) {
                            final LOTRFellowshipProfile fsPro = (LOTRFellowshipProfile)profile;
                            if (!super.worldObj.isClient) {
                                final LOTRFellowship fs = fsPro.getFellowship();
                                if (fs != null && fs.containsPlayer(playerID)) {
                                    return true;
                                }
                            }
                            else {
                                final LOTRFellowshipClient fs2 = fsPro.getFellowshipClient();
                                if (fs2 != null && fs2.isPlayerIn(playerName)) {
                                    return true;
                                }
                            }
                        }
                        else if (profile.getId() != null && profile.getId().equals(playerID)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public int getWhitelistLength() {
        return this.allowedPlayers.length;
    }
    
    public void resizeWhitelist(int length) {
        length = MathHelper.clamp_int(length, LOTREntityBanner.WHITELIST_MIN, LOTREntityBanner.WHITELIST_MAX);
        if (length == this.allowedPlayers.length) {
            return;
        }
        final GameProfile[] resized = new GameProfile[length];
        for (int i = 0; i < length; ++i) {
            if (i < this.allowedPlayers.length) {
                resized[i] = this.allowedPlayers[i];
            }
        }
        this.allowedPlayers = resized;
        if (!super.worldObj.isClient) {
            this.updateForAllWatchers(super.worldObj);
        }
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    public void onUpdate() {
        super.onUpdate();
        final boolean protecting = this.isProtectingTerritory();
        if (!super.worldObj.isClient && protecting) {
            this.wasEverProtecting = true;
        }
        if (!super.worldObj.isClient && this.getPlacingPlayer() == null && this.playerSpecificProtection) {
            this.playerSpecificProtection = false;
        }
        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;
        this.func_145771_j(super.posX, (super.boundingBox.minY + super.boundingBox.maxY) / 2.0, super.posZ);
        final double n = 0.0;
        super.motionZ = n;
        super.motionX = n;
        super.motionY = 0.0;
        this.moveEntity(super.motionX, super.motionY, super.motionZ);
        final int i = MathHelper.floor_double(super.posX);
        final int j = MathHelper.floor_double(super.boundingBox.minY);
        final int k = MathHelper.floor_double(super.posZ);
        final boolean onSolidBlock = World.doesBlockHaveSolidTopSurface((IBlockAccess)super.worldObj, i, j - 1, k) && super.boundingBox.minY == MathHelper.ceiling_double_int(super.boundingBox.minY);
        if (!super.worldObj.isClient && !onSolidBlock) {
            this.dropAsItem(true);
        }
        super.ignoreFrustumCheck = protecting;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setByte("BannerType", (byte)this.getBannerTypeID());
        if (this.protectData == null && this.wasEverProtecting) {
            this.protectData = new NBTTagCompound();
        }
        if (this.protectData != null) {
            this.writeProtectionToNBT(this.protectData);
            nbt.setTag("ProtectData", (NBTBase)this.protectData);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setBannerTypeID(nbt.getByte("BannerType"));
        if (nbt.hasKey("PlayerProtection")) {
            this.readProtectionFromNBT(nbt);
            this.writeProtectionToNBT(this.protectData = new NBTTagCompound());
        }
        else if (nbt.hasKey("ProtectData")) {
            this.readProtectionFromNBT(nbt.getCompoundTag("ProtectData"));
        }
    }
    
    public final void writeProtectionToNBT(final NBTTagCompound nbt) {
        nbt.setBoolean("PlayerProtection", this.playerSpecificProtection);
        nbt.setBoolean("StructureProtection", this.structureProtection);
        nbt.setShort("CustomRange", (short)this.customRange);
        nbt.setBoolean("SelfProtection", this.selfProtection);
        nbt.setFloat("AlignProtectF", this.alignmentProtection);
        nbt.setInteger("WhitelistLength", this.allowedPlayers.length);
        final NBTTagList allowedPlayersTags = new NBTTagList();
        for (int i = 0; i < this.allowedPlayers.length; ++i) {
            if (this.allowedPlayers[i] != null) {
                final NBTTagCompound playerData = new NBTTagCompound();
                playerData.setInteger("Index", i);
                final GameProfile profile = this.allowedPlayers[i];
                final boolean isFellowship = profile instanceof LOTRFellowshipProfile;
                playerData.setBoolean("Fellowship", isFellowship);
                if (isFellowship) {
                    final LOTRFellowship fs = ((LOTRFellowshipProfile)profile).getFellowship();
                    if (fs != null) {
                        playerData.setString("FellowshipID", fs.getFellowshipID().toString());
                    }
                }
                else {
                    final NBTTagCompound profileData = new NBTTagCompound();
                    NBTUtil.func_152460_a(profileData, profile);
                    playerData.setTag("Profile", (NBTBase)profileData);
                }
                allowedPlayersTags.appendTag((NBTBase)playerData);
            }
        }
        nbt.setTag("AllowedPlayers", (NBTBase)allowedPlayersTags);
    }
    
    public final void readProtectionFromNBT(final NBTTagCompound nbt) {
        this.protectData = (NBTTagCompound)nbt.copy();
        this.playerSpecificProtection = nbt.getBoolean("PlayerProtection");
        this.structureProtection = nbt.getBoolean("StructureProtection");
        this.customRange = nbt.getShort("CustomRange");
        this.customRange = MathHelper.clamp_int(this.customRange, 0, 64);
        if (nbt.hasKey("SelfProtection")) {
            this.selfProtection = nbt.getBoolean("SelfProtection");
        }
        else {
            this.selfProtection = true;
        }
        if (nbt.hasKey("AlignmentProtection")) {
            this.setAlignmentProtection((float)nbt.getInteger("AlignmentProtection"));
        }
        else {
            this.setAlignmentProtection(nbt.getFloat("AlignProtectF"));
        }
        int wlength = LOTREntityBanner.WHITELIST_DEFAULT;
        if (nbt.hasKey("WhitelistLength")) {
            wlength = nbt.getInteger("WhitelistLength");
        }
        this.allowedPlayers = new GameProfile[wlength];
        final NBTTagList allowedPlayersTags = nbt.getTagList("AllowedPlayers", 10);
        for (int i = 0; i < allowedPlayersTags.tagCount(); ++i) {
            final NBTTagCompound playerData = allowedPlayersTags.getCompoundTagAt(i);
            final int index = playerData.getInteger("Index");
            if (index >= 0 && index < wlength) {
                final boolean isFellowship = playerData.getBoolean("Fellowship");
                if (isFellowship) {
                    if (playerData.hasKey("FellowshipID")) {
                        final UUID fsID = UUID.fromString(playerData.getString("FellowshipID"));
                        if (fsID != null) {
                            final LOTRFellowshipProfile profile = new LOTRFellowshipProfile(this, fsID, "");
                            if (profile.getFellowship() != null) {
                                this.allowedPlayers[index] = profile;
                            }
                        }
                    }
                }
                else if (playerData.hasKey("Profile")) {
                    final NBTTagCompound profileData = playerData.getCompoundTag("Profile");
                    final GameProfile profile2 = NBTUtil.func_152459_a(profileData);
                    this.allowedPlayers[index] = profile2;
                }
            }
        }
        this.validateWhitelistedFellowships();
    }
    
    private boolean isValidFellowship(final LOTRFellowship fs) {
        final GameProfile owner = this.getPlacingPlayer();
        return fs != null && !fs.isDisbanded() && owner != null && owner.getId() != null && fs.containsPlayer(owner.getId());
    }
    
    private void validateWhitelistedFellowships() {
        final GameProfile owner = this.getPlacingPlayer();
        for (int i = 0; i < this.allowedPlayers.length; ++i) {
            if (this.allowedPlayers[i] instanceof LOTRFellowshipProfile) {
                final LOTRFellowshipProfile fsProfile = (LOTRFellowshipProfile)this.allowedPlayers[i];
                final LOTRFellowship fs = fsProfile.getFellowship();
                if (!this.isValidFellowship(fs)) {
                    this.allowedPlayers[i] = null;
                }
            }
        }
    }
    
    public boolean canPlayerEditBanner(final EntityPlayer entityplayer) {
        final GameProfile owner = this.getPlacingPlayer();
        return (owner != null && owner.getId() != null && entityplayer.getUniqueID().equals(owner.getId())) || (!this.isStructureProtection() && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && entityplayer.capabilities.isCreativeMode);
    }
    
    public boolean interactFirst(final EntityPlayer entityplayer) {
        if (!super.worldObj.isClient && this.isProtectingTerritory() && this.canPlayerEditBanner(entityplayer)) {
            this.sendBannerToPlayer(entityplayer, true, true);
        }
        return true;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean isProtectionBanner = this.isProtectingTerritory();
        final boolean isPlayerDamage = damagesource.getEntity() instanceof EntityPlayer;
        if (isProtectionBanner && !isPlayerDamage) {
            return false;
        }
        if (!super.isDead && !super.worldObj.isClient) {
            if (isPlayerDamage) {
                final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
                if (LOTRBannerProtection.isProtectedByBanner(super.worldObj, this, LOTRBannerProtection.forPlayer(entityplayer), true)) {
                    if (!isProtectionBanner) {
                        return false;
                    }
                    if (this.selfProtection) {
                        return false;
                    }
                    if (this.structureProtection && damagesource.getEntity() != damagesource.getSourceOfDamage()) {
                        return false;
                    }
                }
                if (isProtectionBanner && this.selfProtection && !this.canPlayerEditBanner(entityplayer)) {
                    return false;
                }
            }
            this.setBeenAttacked();
            super.worldObj.playSoundAtEntity((Entity)this, Blocks.planks.stepSound.getDigResourcePath(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getFrequency() * 0.8f);
            boolean drop = true;
            if (damagesource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damagesource.getEntity()).capabilities.isCreativeMode) {
                drop = false;
            }
            this.dropAsItem(drop);
        }
        return true;
    }
    
    private void dropAsItem(final boolean drop) {
        this.setDead();
        if (drop) {
            this.entityDropItem(this.getBannerItem(), 0.0f);
        }
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return this.getBannerItem();
    }
    
    private ItemStack getBannerItem() {
        final ItemStack item = new ItemStack(LOTRMod.banner, 1, this.getBannerType().bannerID);
        if (this.wasEverProtecting && this.protectData == null) {
            this.protectData = new NBTTagCompound();
        }
        if (this.protectData != null) {
            this.writeProtectionToNBT(this.protectData);
            if (!this.structureProtection) {
                LOTRItemBanner.setProtectionData(item, this.protectData);
            }
        }
        return item;
    }
    
    public void sendBannerToPlayer(final EntityPlayer entityplayer, final boolean sendWhitelist, final boolean openGui) {
        this.sendBannerData(entityplayer, sendWhitelist, openGui);
    }
    
    private void updateForAllWatchers(final World world) {
        final int x = MathHelper.floor_double(super.posX) >> 4;
        final int z = MathHelper.floor_double(super.posZ) >> 4;
        final PlayerManager playermanager = ((WorldServer)super.worldObj).getPlayerManager();
        final List players = super.worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                this.sendBannerData((EntityPlayer)entityplayer, false, false);
            }
        }
    }
    
    private void sendBannerData(final EntityPlayer entityplayer, final boolean sendWhitelist, final boolean openGui) {
        final LOTRPacketBannerData packet = new LOTRPacketBannerData(this.getEntityId(), openGui);
        packet.playerSpecificProtection = this.playerSpecificProtection;
        packet.selfProtection = this.selfProtection;
        packet.structureProtection = this.structureProtection;
        packet.customRange = this.customRange;
        packet.alignmentProtection = this.getAlignmentProtection();
        packet.whitelistLength = this.getWhitelistLength();
        final int maxSendIndex = sendWhitelist ? this.allowedPlayers.length : 1;
        final String[] whitelistSlots = new String[maxSendIndex];
        for (int index = 0; index < maxSendIndex; ++index) {
            final GameProfile profile = this.allowedPlayers[index];
            if (profile == null) {
                whitelistSlots[index] = null;
            }
            else if (profile instanceof LOTRFellowshipProfile) {
                final LOTRFellowshipProfile fsProfile = (LOTRFellowshipProfile)profile;
                final LOTRFellowship fs = fsProfile.getFellowship();
                if (this.isValidFellowship(fs)) {
                    whitelistSlots[index] = LOTRFellowshipProfile.addFellowshipCode(fs.getName());
                }
            }
            else {
                if (StringUtils.isNullOrEmpty(profile.getName())) {
                    MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
                }
                final String username = profile.getName();
                if (StringUtils.isNullOrEmpty(username)) {
                    whitelistSlots[index] = null;
                    if (index == 0) {
                        LOTRLog.logger.info("LOTR: Banner needs to be replaced at " + MathHelper.floor_double(super.posX) + " " + MathHelper.floor_double(super.posY) + " " + MathHelper.floor_double(super.posZ) + " dim_" + super.dimension);
                    }
                }
                else {
                    whitelistSlots[index] = username;
                }
            }
        }
        packet.whitelistSlots = whitelistSlots;
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }
    
    static {
        LOTREntityBanner.ALIGNMENT_PROTECTION_MIN = 1.0f;
        LOTREntityBanner.ALIGNMENT_PROTECTION_MAX = 10000.0f;
        LOTREntityBanner.WHITELIST_DEFAULT = 16;
        LOTREntityBanner.WHITELIST_MIN = 1;
        LOTREntityBanner.WHITELIST_MAX = 4000;
    }
}
