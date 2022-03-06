// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.network.LOTRPacketHiredGui;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.StringUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRMod;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.inventory.LOTRInventoryNPC;
import java.util.UUID;

public class LOTRHiredNPCInfo
{
    private LOTREntityNPC theEntity;
    private UUID hiringPlayerUUID;
    public boolean isActive;
    public float alignmentRequiredToCommand;
    public LOTRUnitTradeEntry.PledgeType pledgeType;
    private Task hiredTask;
    private boolean canMove;
    public int mobKills;
    public boolean teleportAutomatically;
    private String hiredSquadron;
    public boolean guardMode;
    public static int GUARD_RANGE_MIN;
    public static int GUARD_RANGE_DEFAULT;
    public static int GUARD_RANGE_MAX;
    private int guardRange;
    private LOTRInventoryNPC hiredInventory;
    public boolean inCombat;
    private boolean prevInCombat;
    public boolean isGuiOpen;
    private boolean targetFromCommandSword;
    public boolean wasAttackCommanded;
    private boolean doneFirstUpdate;
    private boolean resendData;
    
    public LOTRHiredNPCInfo(final LOTREntityNPC npc) {
        this.pledgeType = LOTRUnitTradeEntry.PledgeType.NONE;
        this.hiredTask = Task.WARRIOR;
        this.canMove = true;
        this.teleportAutomatically = true;
        this.guardRange = LOTRHiredNPCInfo.GUARD_RANGE_DEFAULT;
        this.wasAttackCommanded = false;
        this.doneFirstUpdate = false;
        this.resendData = true;
        this.theEntity = npc;
    }
    
    public void hireUnit(final EntityPlayer entityplayer, final boolean setLocation, final LOTRFaction hiringFaction, final LOTRUnitTradeEntry tradeEntry, final String squadron, final Entity mount) {
        final float alignment = tradeEntry.alignmentRequired;
        final LOTRUnitTradeEntry.PledgeType pledge = tradeEntry.getPledgeType();
        final Task task = tradeEntry.task;
        if (setLocation) {
            this.theEntity.setLocationAndAngles(((Entity)entityplayer).posX, ((Entity)entityplayer).boundingBox.minY, ((Entity)entityplayer).posZ, this.theEntity.getRNG().nextFloat() * 360.0f, 0.0f);
        }
        this.isActive = true;
        this.alignmentRequiredToCommand = alignment;
        this.pledgeType = pledge;
        this.setHiringPlayer(entityplayer);
        this.setTask(task);
        this.setSquadron(squadron);
        if (hiringFaction != null) {
            LOTRLevelData.getData(entityplayer).getFactionData(hiringFaction).addHire();
        }
        if (mount != null) {
            mount.setLocationAndAngles(((Entity)this.theEntity).posX, ((Entity)this.theEntity).boundingBox.minY, ((Entity)this.theEntity).posZ, ((Entity)this.theEntity).rotationYaw, 0.0f);
            if (mount instanceof LOTREntityNPC) {
                final LOTREntityNPC hiredMountNPC = (LOTREntityNPC)mount;
                hiredMountNPC.hiredNPCInfo.hireUnit(entityplayer, setLocation, hiringFaction, tradeEntry, squadron, null);
            }
            this.theEntity.mountEntity(mount);
            if (mount instanceof LOTRNPCMount && !(mount instanceof LOTREntityNPC)) {
                this.theEntity.setRidingHorse(true);
                final LOTRNPCMount hiredHorse = (LOTRNPCMount)mount;
                hiredHorse.setBelongsToNPC(true);
                LOTRMountFunctions.setNavigatorRangeFromNPC(hiredHorse, this.theEntity);
            }
        }
    }
    
    public void setHiringPlayer(final EntityPlayer entityplayer) {
        if (entityplayer == null) {
            this.hiringPlayerUUID = null;
        }
        else {
            this.hiringPlayerUUID = entityplayer.getUniqueID();
        }
        this.markDirty();
    }
    
    public EntityPlayer getHiringPlayer() {
        if (this.hiringPlayerUUID == null) {
            return null;
        }
        return ((Entity)this.theEntity).worldObj.func_152378_a(this.hiringPlayerUUID);
    }
    
    public UUID getHiringPlayerUUID() {
        return this.hiringPlayerUUID;
    }
    
    public Task getTask() {
        return this.hiredTask;
    }
    
    public void setTask(final Task t) {
        this.hiredTask = t;
        if (this.hiredTask == Task.FARMER) {
            this.hiredInventory = new LOTRInventoryNPC("HiredInventory", this.theEntity, 4);
        }
    }
    
    public LOTRInventoryNPC getHiredInventory() {
        return this.hiredInventory;
    }
    
    private void markDirty() {
        if (!((Entity)this.theEntity).worldObj.isClient) {
            if (((Entity)this.theEntity).ticksExisted > 0) {
                this.resendData = true;
            }
            else {
                this.sendDataToAllWatchers();
            }
        }
    }
    
    public void onUpdate() {
        if (!((Entity)this.theEntity).worldObj.isClient) {
            if (!this.doneFirstUpdate) {
                this.doneFirstUpdate = true;
            }
            if (this.resendData) {
                this.sendDataToAllWatchers();
                this.resendData = false;
            }
            if (this.isActive) {
                final EntityPlayer entityplayer = this.getHiringPlayer();
                if (entityplayer != null) {
                    final LOTRFaction fac = this.theEntity.getHiringFaction();
                    final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                    boolean canCommand = true;
                    if (pd.getAlignment(fac) < this.alignmentRequiredToCommand) {
                        canCommand = false;
                    }
                    if (!this.pledgeType.canAcceptPlayer(entityplayer, fac)) {
                        canCommand = false;
                    }
                    if (!canCommand) {
                        this.dismissUnit(true);
                    }
                }
            }
            this.inCombat = (this.theEntity.getAttackTarget() != null);
            if (this.inCombat != this.prevInCombat) {
                this.sendClientPacket(false);
            }
            this.prevInCombat = this.inCombat;
            if (this.getTask() == Task.WARRIOR && !this.inCombat && this.shouldFollowPlayer() && this.theEntity.getRNG().nextInt(4000) == 0) {
                final EntityPlayer hiringPlayer = this.getHiringPlayer();
                final double range = 16.0;
                if (hiringPlayer != null && this.theEntity.getDistanceSqToEntity((Entity)hiringPlayer) < range * range) {
                    this.theEntity.sendSpeechBank(hiringPlayer, this.theEntity.getSpeechBank(hiringPlayer));
                }
            }
        }
    }
    
    public void dismissUnit(final boolean isDesertion) {
        if (isDesertion) {
            this.getHiringPlayer().addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.hiredNPC.desert", new Object[] { this.theEntity.getCommandSenderName() }));
        }
        else {
            this.getHiringPlayer().addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.hiredNPC.dismiss", new Object[] { this.theEntity.getCommandSenderName() }));
        }
        if (this.hiredTask == Task.FARMER && this.hiredInventory != null) {
            this.hiredInventory.dropAllItems();
        }
        this.isActive = false;
        this.canMove = true;
        this.sendClientPacket(false);
        this.setHiringPlayer(null);
    }
    
    public void onDeath(final DamageSource damagesource) {
        if (!((Entity)this.theEntity).worldObj.isClient && this.isActive && this.getHiringPlayer() != null) {
            final EntityPlayer hiringPlayer = this.getHiringPlayer();
            if (LOTRLevelData.getData(hiringPlayer).getEnableHiredDeathMessages()) {
                hiringPlayer.addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.hiredNPC.death", new Object[] { this.theEntity.func_110142_aN().func_151521_b() }));
            }
        }
        if (!((Entity)this.theEntity).worldObj.isClient && this.hiredInventory != null) {
            this.hiredInventory.dropAllItems();
        }
    }
    
    public void halt() {
        this.canMove = false;
        this.theEntity.setAttackTarget(null);
        this.sendClientPacket(false);
    }
    
    public void ready() {
        this.canMove = true;
        this.sendClientPacket(false);
    }
    
    public boolean isHalted() {
        return !this.guardMode && !this.canMove;
    }
    
    public boolean shouldFollowPlayer() {
        return !this.guardMode && this.canMove;
    }
    
    public boolean getObeyHornHaltReady() {
        return this.hiredTask == Task.WARRIOR && !this.guardMode;
    }
    
    public boolean getObeyHornSummon() {
        return this.hiredTask == Task.WARRIOR && !this.guardMode;
    }
    
    public boolean getObeyCommandSword() {
        return this.hiredTask == Task.WARRIOR && !this.guardMode;
    }
    
    public boolean isGuardMode() {
        return this.guardMode;
    }
    
    public void setGuardMode(final boolean flag) {
        this.guardMode = flag;
        if (flag) {
            final int i = MathHelper.floor_double(((Entity)this.theEntity).posX);
            final int j = MathHelper.floor_double(((Entity)this.theEntity).posY);
            final int k = MathHelper.floor_double(((Entity)this.theEntity).posZ);
            this.theEntity.setHomeArea(i, j, k, this.guardRange);
        }
        else {
            this.theEntity.detachHome();
        }
    }
    
    public int getGuardRange() {
        return this.guardRange;
    }
    
    public void setGuardRange(final int range) {
        this.guardRange = MathHelper.clamp_int(range, LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
        if (this.guardMode) {
            final int i = MathHelper.floor_double(((Entity)this.theEntity).posX);
            final int j = MathHelper.floor_double(((Entity)this.theEntity).posY);
            final int k = MathHelper.floor_double(((Entity)this.theEntity).posZ);
            this.theEntity.setHomeArea(i, j, k, this.guardRange);
        }
    }
    
    public String getSquadron() {
        return this.hiredSquadron;
    }
    
    public void setSquadron(final String s) {
        this.hiredSquadron = s;
        this.markDirty();
    }
    
    public String getStatusString() {
        String status = "";
        if (this.hiredTask == Task.WARRIOR) {
            if (this.inCombat) {
                status = StatCollector.translateToLocal("lotr.hiredNPC.status.combat");
            }
            else if (this.isHalted()) {
                status = StatCollector.translateToLocal("lotr.hiredNPC.status.halted");
            }
            else if (this.guardMode) {
                status = StatCollector.translateToLocal("lotr.hiredNPC.status.guard");
            }
            else {
                status = StatCollector.translateToLocal("lotr.hiredNPC.status.ready");
            }
        }
        else if (this.hiredTask == Task.FARMER) {
            if (this.guardMode) {
                status = StatCollector.translateToLocal("lotr.hiredNPC.status.farming");
            }
            else {
                status = StatCollector.translateToLocal("lotr.hiredNPC.status.following");
            }
        }
        final String s = StatCollector.translateToLocalFormatted("lotr.hiredNPC.status", new Object[] { status });
        return s;
    }
    
    public void onSetTarget(final EntityLivingBase newTarget, final EntityLivingBase prevTarget) {
        if (newTarget == null || newTarget != prevTarget) {
            this.targetFromCommandSword = false;
            this.wasAttackCommanded = false;
        }
    }
    
    public void commandSwordAttack(final EntityLivingBase target) {
        if (target != null && LOTRMod.canNPCAttackEntity(this.theEntity, target, true)) {
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.setRevengeTarget(target);
            this.theEntity.setAttackTarget(target);
            this.targetFromCommandSword = true;
        }
    }
    
    public void commandSwordCancel() {
        if (this.targetFromCommandSword) {
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.setRevengeTarget((EntityLivingBase)null);
            this.theEntity.setAttackTarget(null);
            this.targetFromCommandSword = false;
        }
    }
    
    public void onKillEntity(final EntityLivingBase target) {
        if (!((Entity)this.theEntity).worldObj.isClient && this.isActive) {
            ++this.mobKills;
            this.sendClientPacket(false);
            if (this.getTask() == Task.WARRIOR) {
                boolean wasEnemy = false;
                final LOTRFaction unitFaction = this.theEntity.getFaction();
                if (target instanceof EntityPlayer && LOTRLevelData.getData((EntityPlayer)target).getAlignment(unitFaction) < 0.0f) {
                    wasEnemy = true;
                }
                if (LOTRMod.getNPCFaction((Entity)target).isBadRelation(unitFaction)) {
                    wasEnemy = true;
                }
                if (wasEnemy && this.theEntity.getRNG().nextInt(3) == 0) {
                    final EntityPlayer hiringPlayer = this.getHiringPlayer();
                    final double range = 16.0;
                    if (hiringPlayer != null && this.theEntity.getDistanceSqToEntity((Entity)hiringPlayer) < range * range) {
                        this.theEntity.sendSpeechBank(hiringPlayer, this.theEntity.getSpeechBank(hiringPlayer));
                    }
                }
            }
        }
    }
    
    public boolean tryTeleportToHiringPlayer(final boolean failsafe) {
        final World world = ((Entity)this.theEntity).worldObj;
        if (!world.isClient) {
            final EntityPlayer entityplayer = this.getHiringPlayer();
            if (this.isActive && entityplayer != null && ((Entity)this.theEntity).riddenByEntity == null) {
                final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
                final int j = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
                final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
                float minDist = 3.0f;
                float maxDist = 6.0f;
                float extraDist = ((Entity)this.theEntity).width / 2.0f;
                if (((Entity)this.theEntity).ridingEntity instanceof EntityLiving) {
                    extraDist = Math.max(((Entity)this.theEntity).width, ((Entity)this.theEntity).ridingEntity.width) / 2.0f;
                }
                minDist += extraDist;
                maxDist += extraDist;
                for (int attempts = 120, l = 0; l < attempts; ++l) {
                    final float angle = world.rand.nextFloat() * 3.1415927f * 2.0f;
                    final float sin = MathHelper.sin(angle);
                    final float cos = MathHelper.cos(angle);
                    final float r = MathHelper.randomFloatClamp(world.rand, minDist, maxDist);
                    final int i2 = MathHelper.floor_double(i + 0.5 + cos * r);
                    final int k2 = MathHelper.floor_double(k + 0.5 + sin * r);
                    final int j2 = MathHelper.getRandomIntegerInRange(world.rand, j - 4, j + 4);
                    final double d = i2 + 0.5;
                    final double d2 = j2;
                    final double d3 = k2 + 0.5;
                    final float halfWidth = ((Entity)this.theEntity).width / 2.0f;
                    final float height = ((Entity)this.theEntity).height;
                    final float yExtra = -((Entity)this.theEntity).yOffset + ((Entity)this.theEntity).ySize;
                    final AxisAlignedBB npcBB = AxisAlignedBB.getBoundingBox(d - halfWidth, d2 + yExtra, d3 - halfWidth, d + halfWidth, d2 + yExtra + height, d3 + halfWidth);
                    if (world.func_147461_a(npcBB).isEmpty() && world.getBlock(i2, j2 - 1, k2).isSideSolid((IBlockAccess)world, i2, j2 - 1, k2, ForgeDirection.UP)) {
                        if (!(((Entity)this.theEntity).ridingEntity instanceof EntityLiving)) {
                            this.theEntity.setLocationAndAngles(d, d2, d3, ((Entity)this.theEntity).rotationYaw, ((Entity)this.theEntity).rotationPitch);
                            ((Entity)this.theEntity).fallDistance = 0.0f;
                            this.theEntity.getNavigator().clearPathEntity();
                            this.theEntity.setAttackTarget(null);
                            return true;
                        }
                        final EntityLiving mount = (EntityLiving)((Entity)this.theEntity).ridingEntity;
                        final float mHalfWidth = ((Entity)mount).width / 2.0f;
                        final float mHeight = ((Entity)mount).height;
                        final float mYExtra = -((Entity)mount).yOffset + ((Entity)mount).ySize;
                        final AxisAlignedBB mountBB = AxisAlignedBB.getBoundingBox(d - mHalfWidth, d2 + mYExtra, d3 - mHalfWidth, d + mHalfWidth, d2 + mYExtra + mHeight, d3 + mHalfWidth);
                        if (world.func_147461_a(mountBB).isEmpty()) {
                            mount.setLocationAndAngles(d, d2, d3, ((Entity)this.theEntity).rotationYaw, ((Entity)this.theEntity).rotationPitch);
                            ((Entity)mount).fallDistance = 0.0f;
                            mount.getNavigator().clearPathEntity();
                            mount.setAttackTarget((EntityLivingBase)null);
                            ((Entity)this.theEntity).fallDistance = 0.0f;
                            this.theEntity.getNavigator().clearPathEntity();
                            this.theEntity.setAttackTarget(null);
                            return true;
                        }
                    }
                }
                if (failsafe) {
                    final double d4 = i + 0.5;
                    final double d5 = j;
                    final double d6 = k + 0.5;
                    if (world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP)) {
                        if (((Entity)this.theEntity).ridingEntity instanceof EntityLiving) {
                            final EntityLiving mount2 = (EntityLiving)((Entity)this.theEntity).ridingEntity;
                            mount2.setLocationAndAngles(d4, d5, d6, ((Entity)this.theEntity).rotationYaw, ((Entity)this.theEntity).rotationPitch);
                            ((Entity)mount2).fallDistance = 0.0f;
                            mount2.getNavigator().clearPathEntity();
                            mount2.setAttackTarget((EntityLivingBase)null);
                            ((Entity)this.theEntity).fallDistance = 0.0f;
                            this.theEntity.getNavigator().clearPathEntity();
                            this.theEntity.setAttackTarget(null);
                            return true;
                        }
                        this.theEntity.setLocationAndAngles(d4, d5, d6, ((Entity)this.theEntity).rotationYaw, ((Entity)this.theEntity).rotationPitch);
                        ((Entity)this.theEntity).fallDistance = 0.0f;
                        this.theEntity.getNavigator().clearPathEntity();
                        this.theEntity.setAttackTarget(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        final NBTTagCompound data = new NBTTagCompound();
        data.setBoolean("IsActive", this.isActive);
        if (this.hiringPlayerUUID != null) {
            data.setString("HiringPlayerUUID", this.hiringPlayerUUID.toString());
        }
        data.setFloat("AlignReqF", this.alignmentRequiredToCommand);
        data.setByte("PledgeType", (byte)this.pledgeType.typeID);
        data.setBoolean("CanMove", this.canMove);
        data.setBoolean("TeleportAutomatically", this.teleportAutomatically);
        data.setInteger("MobKills", this.mobKills);
        data.setBoolean("GuardMode", this.guardMode);
        data.setInteger("GuardRange", this.guardRange);
        data.setInteger("Task", this.hiredTask.ordinal());
        if (!StringUtils.isNullOrEmpty(this.hiredSquadron)) {
            data.setString("Squadron", this.hiredSquadron);
        }
        if (this.hiredInventory != null) {
            this.hiredInventory.writeToNBT(data);
        }
        nbt.setTag("HiredNPCInfo", (NBTBase)data);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        final NBTTagCompound data = nbt.getCompoundTag("HiredNPCInfo");
        if (data != null) {
            if (data.hasKey("HiringPlayerName")) {
                final String name = data.getString("HiringPlayerName");
                this.hiringPlayerUUID = UUID.fromString(PreYggdrasilConverter.func_152719_a(name));
            }
            else if (data.hasKey("HiringPlayerUUID")) {
                final String savedUUID = data.getString("HiringPlayerUUID");
                if (!StringUtils.isNullOrEmpty(savedUUID)) {
                    this.hiringPlayerUUID = UUID.fromString(savedUUID);
                }
            }
            this.isActive = data.getBoolean("IsActive");
            if (data.hasKey("AlignmentRequired")) {
                this.alignmentRequiredToCommand = (float)data.getInteger("AlignmentRequired");
            }
            else {
                this.alignmentRequiredToCommand = data.getFloat("AlignReqF");
            }
            if (data.hasKey("PledgeType")) {
                final int pledgeID = data.getByte("PledgeType");
                this.pledgeType = LOTRUnitTradeEntry.PledgeType.forID(pledgeID);
            }
            this.canMove = data.getBoolean("CanMove");
            if (data.hasKey("TeleportAutomatically")) {
                this.teleportAutomatically = data.getBoolean("TeleportAutomatically");
                this.mobKills = data.getInteger("MobKills");
                this.setGuardMode(data.getBoolean("GuardMode"));
                this.setGuardRange(data.getInteger("GuardRange"));
            }
            this.setTask(Task.forID(data.getInteger("Task")));
            if (data.hasKey("Squadron")) {
                this.hiredSquadron = data.getString("Squadron");
            }
            if (this.hiredInventory != null) {
                this.hiredInventory.readFromNBT(data);
            }
        }
    }
    
    public void sendData(final EntityPlayerMP entityplayer) {
        final LOTRPacketHiredInfo packet = new LOTRPacketHiredInfo(this.theEntity.getEntityId(), this.hiringPlayerUUID, this.getSquadron());
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    private void sendDataToAllWatchers() {
        final int x = MathHelper.floor_double(((Entity)this.theEntity).posX) >> 4;
        final int z = MathHelper.floor_double(((Entity)this.theEntity).posZ) >> 4;
        final PlayerManager playermanager = ((WorldServer)((Entity)this.theEntity).worldObj).getPlayerManager();
        final List players = ((Entity)this.theEntity).worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                this.sendData(entityplayer);
            }
        }
    }
    
    public void receiveData(final LOTRPacketHiredInfo packet) {
        this.hiringPlayerUUID = packet.hiringPlayer;
        this.setSquadron(packet.squadron);
    }
    
    public void sendClientPacket(final boolean shouldOpenGui) {
        if (((Entity)this.theEntity).worldObj.isClient || this.getHiringPlayer() == null) {
            return;
        }
        final LOTRPacketHiredGui packet = new LOTRPacketHiredGui(this.theEntity.getEntityId(), shouldOpenGui);
        packet.isActive = this.isActive;
        packet.canMove = this.canMove;
        packet.teleportAutomatically = this.teleportAutomatically;
        packet.mobKills = this.mobKills;
        packet.alignmentRequired = this.alignmentRequiredToCommand;
        packet.pledgeType = this.pledgeType;
        packet.inCombat = this.inCombat;
        packet.guardMode = this.guardMode;
        packet.guardRange = this.guardRange;
        packet.task = this.hiredTask;
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)this.getHiringPlayer());
        if (shouldOpenGui) {
            this.isGuiOpen = true;
        }
    }
    
    public void receiveClientPacket(final LOTRPacketHiredGui packet) {
        this.isActive = packet.isActive;
        this.canMove = packet.canMove;
        this.teleportAutomatically = packet.teleportAutomatically;
        this.mobKills = packet.mobKills;
        this.alignmentRequiredToCommand = packet.alignmentRequired;
        this.pledgeType = packet.pledgeType;
        this.inCombat = packet.inCombat;
        this.guardMode = packet.guardMode;
        this.guardRange = packet.guardRange;
        this.setTask(packet.task);
    }
    
    static {
        LOTRHiredNPCInfo.GUARD_RANGE_MIN = 1;
        LOTRHiredNPCInfo.GUARD_RANGE_DEFAULT = 8;
        LOTRHiredNPCInfo.GUARD_RANGE_MAX = 64;
    }
    
    public enum Task
    {
        WARRIOR, 
        FARMER;
        
        public static Task forID(final int id) {
            for (final Task task : values()) {
                if (task.ordinal() == id) {
                    return task;
                }
            }
            return Task.WARRIOR;
        }
    }
}
