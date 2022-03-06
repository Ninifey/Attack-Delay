// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import lotr.common.network.LOTRPacketNPCFX;
import lotr.common.item.LOTRItemPartyHat;
import java.awt.Color;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.init.Blocks;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.LOTRBannerProtection;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.item.LOTRItemPouch;
import lotr.common.entity.item.LOTREntityTraderRespawn;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.common.LOTRAchievement;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.LOTRDimension;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.entity.projectile.LOTREntityPlate;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.entity.projectile.EntityArrow;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.item.LOTRItemBow;
import net.minecraft.util.DamageSource;
import net.minecraft.enchantment.EnchantmentHelper;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.entity.LOTREntities;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.network.LOTRPacketNPCIsEating;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCCombatStance;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import lotr.common.item.LOTRItemTrident;
import java.util.Iterator;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import lotr.common.inventory.LOTRContainerAnvil;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.ai.LOTREntityAIBurningPanic;
import lotr.common.LOTRConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.LOTRMountFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import java.lang.reflect.Constructor;
import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.ai.LOTRNPCTargetSelector;
import net.minecraft.entity.EntityLiving;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.ai.LOTREntityAINPCHurtByTarget;
import lotr.common.entity.ai.LOTREntityAIHiringPlayerHurtTarget;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.ai.LOTREntityAIHiringPlayerHurtByTarget;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import lotr.common.LOTRShields;
import java.util.Random;
import net.minecraft.item.ItemStack;
import java.util.UUID;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import net.minecraft.entity.ai.attributes.IAttribute;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.EntityCreature;

public abstract class LOTREntityNPC extends EntityCreature implements IRangedAttackMob, LOTRRandomSkinEntity
{
    public static IAttribute npcAttackDamage;
    public static IAttribute npcAttackDamageExtra;
    public static IAttribute npcAttackDamageDrunk;
    public static IAttribute npcRangedAccuracy;
    public static IAttribute horseAttackSpeed;
    public static float MOUNT_RANGE_BONUS;
    protected float npcWidth;
    protected float npcHeight;
    private boolean loadingFromNBT;
    public boolean isPassive;
    public boolean isImmuneToFrost;
    protected boolean isChilly;
    protected boolean spawnsInDarkness;
    public boolean isNPCPersistent;
    public boolean liftSpawnRestrictions;
    private boolean isConquestSpawning;
    public boolean liftBannerRestrictions;
    private boolean addedBurningPanic;
    public List<LOTRFaction> killBonusFactions;
    public boolean isInvasionSpawned;
    private boolean isTargetSeeker;
    public String npcLocationName;
    private boolean hasSpecificLocationName;
    public boolean spawnRidingHorse;
    protected boolean canBannerBearerSpawnRiding;
    private boolean ridingMount;
    public LOTRFamilyInfo familyInfo;
    public LOTREntityQuestInfo questInfo;
    public LOTRHiredNPCInfo hiredNPCInfo;
    public LOTRTraderNPCInfo traderNPCInfo;
    public LOTRTravellingTraderInfo travellingTraderInfo;
    public boolean isTraderEscort;
    public LOTRBossInfo bossInfo;
    private boolean setInitialHome;
    private int initHomeX;
    private int initHomeY;
    private int initHomeZ;
    private int initHomeRange;
    private AttackMode currentAttackMode;
    private boolean firstUpdatedAttackMode;
    private UUID prevAttackTarget;
    private int combatCooldown;
    private boolean combatStance;
    private boolean prevCombatStance;
    public boolean clientCombatStance;
    public boolean clientIsEating;
    public LOTRInventoryNPCItems npcItemsInv;
    public LOTRInventoryHiredReplacedItems hiredReplacedInv;
    private ItemStack[] festiveItems;
    private Random festiveRand;
    private boolean initFestiveItems;
    public LOTRShields npcShield;
    public ResourceLocation npcCape;
    public int npcTalkTick;
    private boolean hurtOnlyByPlates;
    private List<ItemStack> enpouchedDrops;
    private boolean enpouchNPCDrops;
    
    public LOTREntityNPC(final World world) {
        super(world);
        this.npcWidth = -1.0f;
        this.loadingFromNBT = false;
        this.isPassive = false;
        this.isImmuneToFrost = false;
        this.isChilly = false;
        this.spawnsInDarkness = false;
        this.isNPCPersistent = false;
        this.liftSpawnRestrictions = false;
        this.isConquestSpawning = false;
        this.liftBannerRestrictions = false;
        this.addedBurningPanic = false;
        this.killBonusFactions = new ArrayList<LOTRFaction>();
        this.isInvasionSpawned = false;
        this.isTargetSeeker = false;
        this.canBannerBearerSpawnRiding = false;
        this.isTraderEscort = false;
        this.setInitialHome = false;
        this.initHomeRange = -1;
        this.currentAttackMode = AttackMode.IDLE;
        this.firstUpdatedAttackMode = false;
        this.festiveItems = new ItemStack[5];
        this.festiveRand = new Random();
        this.initFestiveItems = false;
        this.npcTalkTick = 0;
        this.hurtOnlyByPlates = true;
        this.enpouchedDrops = new ArrayList<ItemStack>();
        this.enpouchNPCDrops = false;
        if (this instanceof LOTRBoss || this instanceof LOTRCharacter) {
            this.isNPCPersistent = true;
        }
        else if (this instanceof LOTRTradeable) {
            if (this instanceof LOTRTravellingTrader) {
                this.isNPCPersistent = true;
            }
            else {
                this.isNPCPersistent = ((LOTRTradeable)this).shouldTraderRespawn();
            }
        }
        else if (this instanceof LOTRUnitTradeable) {
            this.isNPCPersistent = ((LOTRUnitTradeable)this).shouldTraderRespawn();
        }
    }
    
    public final boolean isTrader() {
        return this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable || this instanceof LOTRMercenary;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.familyInfo = new LOTRFamilyInfo(this);
        this.questInfo = new LOTREntityQuestInfo(this);
        this.hiredNPCInfo = new LOTRHiredNPCInfo(this);
        this.traderNPCInfo = new LOTRTraderNPCInfo(this);
        if (this instanceof LOTRTravellingTrader) {
            this.travellingTraderInfo = new LOTRTravellingTraderInfo((LOTRTravellingTrader)this);
        }
        if (this instanceof LOTRBoss) {
            this.bossInfo = new LOTRBossInfo((LOTRBoss)this);
        }
        this.setupNPCGender();
        this.setupNPCName();
        this.npcItemsInv = new LOTRInventoryNPCItems(this);
        this.hiredReplacedInv = new LOTRInventoryHiredReplacedItems(this);
    }
    
    public void setupNPCGender() {
    }
    
    public void setupNPCName() {
    }
    
    public void startTraderVisiting(final EntityPlayer entityplayer) {
        this.travellingTraderInfo.startVisiting(entityplayer);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(LOTREntityNPC.npcAttackDamage);
        this.getAttributeMap().registerAttribute(LOTREntityNPC.npcAttackDamageExtra);
        this.getAttributeMap().registerAttribute(LOTREntityNPC.npcAttackDamageDrunk);
        this.getAttributeMap().registerAttribute(LOTREntityNPC.npcRangedAccuracy);
        this.getAttributeMap().registerAttribute(LOTREntityNPC.horseAttackSpeed);
    }
    
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    public int addTargetTasks(final boolean seekTargets) {
        return this.addTargetTasks(seekTargets, LOTREntityAINearestAttackableTargetBasic.class);
    }
    
    public int addTargetTasks(final boolean seekTargets, final Class<? extends LOTREntityAINearestAttackableTargetBasic> c) {
        ((EntityLiving)this).targetTasks.taskEntries.clear();
        ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new LOTREntityAIHiringPlayerHurtByTarget(this));
        ((EntityLiving)this).targetTasks.addTask(2, (EntityAIBase)new LOTREntityAIHiringPlayerHurtTarget(this));
        ((EntityLiving)this).targetTasks.addTask(3, (EntityAIBase)new LOTREntityAINPCHurtByTarget(this, false));
        this.isTargetSeeker = seekTargets;
        if (seekTargets) {
            return addTargetTasks(this, 4, c);
        }
        return 3;
    }
    
    public static int addTargetTasks(final EntityCreature entity, final int index, final Class<? extends LOTREntityAINearestAttackableTargetBasic> c) {
        try {
            final Constructor<? extends LOTREntityAINearestAttackableTargetBasic> constructor = c.getConstructor(EntityCreature.class, Class.class, Integer.TYPE, Boolean.TYPE, IEntitySelector.class);
            ((EntityLiving)entity).targetTasks.addTask(index, (EntityAIBase)constructor.newInstance(entity, EntityPlayer.class, 0, true, null));
            ((EntityLiving)entity).targetTasks.addTask(index, (EntityAIBase)constructor.newInstance(entity, EntityLiving.class, 0, true, new LOTRNPCTargetSelector((EntityLiving)entity)));
        }
        catch (Exception e) {
            FMLLog.severe("Error adding LOTR target tasks to entity " + entity.toString(), new Object[0]);
            e.printStackTrace();
        }
        return index;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(final double dist) {
        final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
        if (entityplayer != null) {
            final LOTRPlayerData data = LOTRLevelData.getData(entityplayer);
            if (!data.getMiniQuestsForEntity(this, true).isEmpty()) {
                return true;
            }
        }
        return super.isInRangeToRenderDist(dist);
    }
    
    protected boolean shouldBurningPanic() {
        return true;
    }
    
    public IEntityLivingData initCreatureForHire(final IEntityLivingData data) {
        this.spawnRidingHorse = false;
        return this.onSpawnWithEgg(data);
    }
    
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData data) {
        if (!((Entity)this).worldObj.isClient) {
            if (this.spawnRidingHorse && (!(this instanceof LOTRBannerBearer) || this.canBannerBearerSpawnRiding)) {
                final LOTRNPCMount mount = this.createMountToRide();
                final EntityCreature livingMount = (EntityCreature)mount;
                livingMount.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rotationYaw, 0.0f);
                if (((Entity)this).worldObj.func_147461_a(((Entity)livingMount).boundingBox).isEmpty()) {
                    livingMount.onSpawnWithEgg((IEntityLivingData)null);
                    ((Entity)this).worldObj.spawnEntityInWorld((Entity)livingMount);
                    this.mountEntity((Entity)livingMount);
                    if (!(mount instanceof LOTREntityNPC)) {
                        this.setRidingHorse(true);
                        mount.setBelongsToNPC(true);
                        LOTRMountFunctions.setNavigatorRangeFromNPC(mount, this);
                    }
                }
            }
            if (this.traderNPCInfo.getBuyTrades() != null && ((Entity)this).rand.nextInt(10000) == 0) {
                for (final LOTRTradeEntry trade : this.traderNPCInfo.getBuyTrades()) {
                    trade.setCost(trade.getCost() * 100);
                }
                this.familyInfo.setName("Noah");
            }
        }
        return data;
    }
    
    public LOTRNPCMount createMountToRide() {
        return new LOTREntityHorse(((Entity)this).worldObj);
    }
    
    public void setRidingHorse(final boolean flag) {
        this.ridingMount = flag;
        double d = this.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
        if (flag) {
            d *= 1.5;
        }
        else {
            d /= 1.5;
        }
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(d);
    }
    
    public void onPlayerStartTracking(final EntityPlayerMP entityplayer) {
        this.hiredNPCInfo.sendData(entityplayer);
        this.familyInfo.sendData(entityplayer);
        this.questInfo.sendData(entityplayer);
        this.sendCombatStance(entityplayer);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public final String getCommandSenderName() {
        if (this.hasCustomNameTag()) {
            return super.getCommandSenderName();
        }
        final String entityName = this.getEntityClassName();
        String npcName = this.getNPCName();
        if (LOTRMod.isAprilFools()) {
            npcName = "Gandalf";
        }
        return this.getNPCFormattedName(npcName, entityName);
    }
    
    public String getNPCFormattedName(final String npcName, final String entityName) {
        if (npcName.equals(entityName)) {
            return entityName;
        }
        return StatCollector.translateToLocalFormatted("entity.lotr.generic.entityName", new Object[] { npcName, entityName });
    }
    
    public String getEntityClassName() {
        return super.getCommandSenderName();
    }
    
    public String getNPCName() {
        return super.getCommandSenderName();
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.UNALIGNED;
    }
    
    public LOTRFaction getHiringFaction() {
        return this.getFaction();
    }
    
    public final boolean isCivilianNPC() {
        return !this.isTargetSeeker && !(this instanceof LOTRUnitTradeable) && !(this instanceof LOTRMercenary) && !(this instanceof LOTRBoss);
    }
    
    public boolean generatesControlZone() {
        return true;
    }
    
    public boolean canBeFreelyTargetedBy(final EntityLiving attacker) {
        return true;
    }
    
    public int getNPCTalkInterval() {
        return 40;
    }
    
    public boolean canNPCTalk() {
        return this.isEntityAlive() && this.npcTalkTick >= this.getNPCTalkInterval();
    }
    
    private void markNPCSpoken() {
        this.npcTalkTick = 0;
    }
    
    public void setAttackTarget(final EntityLivingBase target) {
        final EntityLivingBase prevEntityTarget = this.getAttackTarget();
        super.setAttackTarget(target);
        this.hiredNPCInfo.onSetTarget(target, prevEntityTarget);
        if (target != null && !target.getUniqueID().equals(this.prevAttackTarget)) {
            this.prevAttackTarget = target.getUniqueID();
            if (!((Entity)this).worldObj.isClient) {
                if (this.getAttackSound() != null) {
                    ((Entity)this).worldObj.playSoundAtEntity((Entity)this, this.getAttackSound(), this.getSoundVolume(), this.getSoundPitch());
                }
                if (target instanceof EntityPlayer) {
                    final EntityPlayer entityplayer = (EntityPlayer)target;
                    final String speechBank = this.getSpeechBank(entityplayer);
                    if (speechBank != null && ((Entity)this).rand.nextInt(3) == 0 && this.getEntitySenses().canSee((Entity)entityplayer)) {
                        final IEntitySelector selectorAttackingNPCs = (IEntitySelector)new IEntitySelector() {
                            public boolean isEntityApplicable(final Entity entity) {
                                if (entity instanceof LOTREntityNPC) {
                                    final LOTREntityNPC npc = (LOTREntityNPC)entity;
                                    return npc.isAIEnabled() && npc.isEntityAlive() && npc.getAttackTarget() == entityplayer;
                                }
                                return false;
                            }
                        };
                        final double range = 16.0;
                        final List nearbyMobs = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox.expand(range, range, range), selectorAttackingNPCs);
                        if (nearbyMobs.size() <= 5) {
                            this.sendSpeechBank(entityplayer, speechBank);
                        }
                    }
                }
            }
        }
    }
    
    public String getAttackSound() {
        return null;
    }
    
    public int getTalkInterval() {
        return 200;
    }
    
    public boolean isChild() {
        return this.familyInfo.getAge() < 0;
    }
    
    public void changeNPCNameForMarriage(final LOTREntityNPC spouse) {
    }
    
    public void createNPCChildName(final LOTREntityNPC maleParent, final LOTREntityNPC femaleParent) {
    }
    
    public boolean canDespawn() {
        return !this.isNPCPersistent && !this.hiredNPCInfo.isActive && !this.questInfo.anyActiveQuestPlayers();
    }
    
    protected final void setSize(final float f, final float f1) {
        final boolean flag = this.npcWidth > 0.0f;
        this.npcWidth = f;
        this.npcHeight = f1;
        if (!flag) {
            this.rescaleNPC(1.0f);
        }
    }
    
    protected void rescaleNPC(final float f) {
        super.setSize(this.npcWidth * f, this.npcHeight * f);
    }
    
    protected float getNPCScale() {
        return this.isChild() ? 0.5f : 1.0f;
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.rescaleNPC(this.getNPCScale());
        this.updateCombat();
        this.familyInfo.onUpdate();
        this.questInfo.onUpdate();
        this.hiredNPCInfo.onUpdate();
        if (this instanceof LOTRTradeable) {
            this.traderNPCInfo.onUpdate();
        }
        if (this.travellingTraderInfo != null) {
            this.travellingTraderInfo.onUpdate();
        }
        if ((this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable) && !((Entity)this).worldObj.isClient) {
            if (!this.setInitialHome) {
                if (this.hasHome()) {
                    this.initHomeX = this.getHomePosition().posX;
                    this.initHomeY = this.getHomePosition().posY;
                    this.initHomeZ = this.getHomePosition().posZ;
                    this.initHomeRange = (int)this.func_110174_bM();
                }
                this.setInitialHome = true;
            }
            final int preventKidnap = LOTRConfig.preventTraderKidnap;
            if (preventKidnap > 0 && this.setInitialHome && this.initHomeRange > 0) {
                final double dSqToInitHome = this.getDistanceSq(this.initHomeX + 0.5, this.initHomeY + 0.5, this.initHomeZ + 0.5);
                if (dSqToInitHome > preventKidnap * preventKidnap) {
                    if (((Entity)this).ridingEntity != null) {
                        this.mountEntity((Entity)null);
                    }
                    ((Entity)this).worldObj.getChunkFromBlockCoords(this.initHomeX, this.initHomeZ);
                    this.setLocationAndAngles(this.initHomeX + 0.5, (double)this.initHomeY, this.initHomeZ + 0.5, ((Entity)this).rotationYaw, ((Entity)this).rotationPitch);
                }
            }
        }
        if (this.bossInfo != null) {
            this.bossInfo.onUpdate();
        }
        if (!((Entity)this).worldObj.isClient && !this.addedBurningPanic) {
            LOTREntityUtils.removeAITask(this, LOTREntityAIBurningPanic.class);
            if (this.shouldBurningPanic()) {
                ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new LOTREntityAIBurningPanic(this, 1.5));
            }
            this.addedBurningPanic = true;
        }
        if (!((Entity)this).worldObj.isClient && this.isEntityAlive() && (this.isTrader() || this.hiredNPCInfo.isActive) && this.getAttackTarget() == null) {
            float healAmount = 0.0f;
            if (((Entity)this).ticksExisted % 40 == 0) {
                ++healAmount;
            }
            if (this.hiredNPCInfo.isActive) {
                final int banners = this.nearbyBanners();
                if (banners > 0) {
                    final int bannerInterval = 240 - banners * 40;
                    if (((Entity)this).ticksExisted % bannerInterval == 0) {
                        ++healAmount;
                    }
                }
            }
            if (healAmount > 0.0f) {
                this.heal(healAmount);
                if (((Entity)this).ridingEntity instanceof EntityLivingBase && !(((Entity)this).ridingEntity instanceof LOTREntityNPC)) {
                    ((EntityLivingBase)((Entity)this).ridingEntity).heal(healAmount);
                }
            }
        }
        if (!((Entity)this).worldObj.isClient && this.isEntityAlive() && this.getAttackTarget() == null) {
            boolean guiOpen = false;
            if (this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable || this instanceof LOTRMercenary) {
                for (int i = 0; i < ((Entity)this).worldObj.playerEntities.size(); ++i) {
                    final EntityPlayer entityplayer = ((Entity)this).worldObj.playerEntities.get(i);
                    final Container container = entityplayer.openContainer;
                    if (container instanceof LOTRContainerTrade && ((LOTRContainerTrade)container).theTraderNPC == this) {
                        guiOpen = true;
                        break;
                    }
                    if (container instanceof LOTRContainerUnitTrade && ((LOTRContainerUnitTrade)container).theLivingTrader == this) {
                        guiOpen = true;
                        break;
                    }
                    if (container instanceof LOTRContainerCoinExchange && ((LOTRContainerCoinExchange)container).theTraderNPC == this) {
                        guiOpen = true;
                        break;
                    }
                    if (container instanceof LOTRContainerAnvil && ((LOTRContainerAnvil)container).theNPC == this) {
                        guiOpen = true;
                        break;
                    }
                }
            }
            if (this.hiredNPCInfo.isActive && this.hiredNPCInfo.isGuiOpen) {
                guiOpen = true;
            }
            if (this.questInfo.anyOpenOfferPlayers()) {
                guiOpen = true;
            }
            if (guiOpen) {
                this.getNavigator().clearPathEntity();
                if (((Entity)this).ridingEntity instanceof LOTRNPCMount) {
                    ((EntityLiving)((Entity)this).ridingEntity).getNavigator().clearPathEntity();
                }
            }
        }
        this.updateArmSwingProgress();
        if (this.npcTalkTick < this.getNPCTalkInterval()) {
            ++this.npcTalkTick;
        }
        if (!((Entity)this).worldObj.isClient && this.hasHome() && !this.isWithinHomeDistanceCurrentPosition()) {
            final int homeX = this.getHomePosition().posX;
            final int homeY = this.getHomePosition().posY;
            final int homeZ = this.getHomePosition().posZ;
            final int homeRange = (int)this.func_110174_bM();
            final double maxDist = homeRange + 128.0;
            final double distToHome = this.getDistance(homeX + 0.5, homeY + 0.5, homeZ + 0.5);
            if (distToHome > maxDist) {
                this.detachHome();
            }
            else if (this.getAttackTarget() == null && this.getNavigator().noPath()) {
                this.detachHome();
                boolean goDirectlyHome = false;
                if (((Entity)this).worldObj.blockExists(homeX, homeY, homeZ) && this.hiredNPCInfo.isGuardMode()) {
                    final int guardRange = this.hiredNPCInfo.getGuardRange();
                    goDirectlyHome = (distToHome < 16.0);
                }
                final double homeSpeed = 1.3;
                if (goDirectlyHome) {
                    this.getNavigator().tryMoveToXYZ(homeX + 0.5, homeY + 0.5, homeZ + 0.5, homeSpeed);
                }
                else {
                    Vec3 path = null;
                    for (int l = 0; l < 16 && path == null; path = RandomPositionGenerator.findRandomTargetBlockTowards((EntityCreature)this, 8, 7, Vec3.createVectorHelper((double)homeX, (double)homeY, (double)homeZ)), ++l) {}
                    if (path != null) {
                        this.getNavigator().tryMoveToXYZ(path.xCoord, path.yCoord, path.zCoord, homeSpeed);
                    }
                }
                this.setHomeArea(homeX, homeY, homeZ, homeRange);
            }
        }
        if (this.isChilly) {
            final double speedSq = ((Entity)this).motionX * ((Entity)this).motionX + ((Entity)this).motionY * ((Entity)this).motionY + ((Entity)this).motionZ * ((Entity)this).motionZ;
            if (speedSq >= 0.01) {
                final double d = ((Entity)this).posX + MathHelper.randomFloatClamp(((Entity)this).rand, -0.3f, 0.3f) * ((Entity)this).width;
                final double d2 = ((Entity)this).boundingBox.minY + MathHelper.randomFloatClamp(((Entity)this).rand, 0.2f, 0.7f) * ((Entity)this).height;
                final double d3 = ((Entity)this).posZ + MathHelper.randomFloatClamp(((Entity)this).rand, -0.3f, 0.3f) * ((Entity)this).width;
                LOTRMod.proxy.spawnParticle("chill", d, d2, d3, -((Entity)this).motionX * 0.5, 0.0, -((Entity)this).motionZ * 0.5);
            }
        }
    }
    
    private void updateCombat() {
        if (!((Entity)this).worldObj.isClient && this.getAttackTarget() != null) {
            final EntityLivingBase entity = this.getAttackTarget();
            if (!entity.isEntityAlive() || (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)) {
                this.setAttackTarget(null);
            }
        }
        boolean changedMounted = false;
        boolean changedAttackMode = false;
        if (!((Entity)this).worldObj.isClient) {
            final boolean isRidingMountNow = ((Entity)this).ridingEntity instanceof EntityLiving && ((Entity)this).ridingEntity.isEntityAlive() && !(((Entity)this).ridingEntity instanceof LOTREntityNPC);
            if (this.ridingMount != isRidingMountNow) {
                this.setRidingHorse(isRidingMountNow);
                changedMounted = true;
            }
        }
        if (!((Entity)this).worldObj.isClient && !this.isChild()) {
            final ItemStack weapon = this.getEquipmentInSlot(0);
            final boolean carryingSpearWithBackup = weapon != null && weapon.getItem() instanceof LOTRItemSpear && this.npcItemsInv.getSpearBackup() != null;
            if (this.getAttackTarget() != null) {
                final double d = this.getDistanceSqToEntity((Entity)this.getAttackTarget());
                if (d < this.getMeleeRangeSq() || carryingSpearWithBackup) {
                    if (this.currentAttackMode != AttackMode.MELEE) {
                        this.currentAttackMode = AttackMode.MELEE;
                        changedAttackMode = true;
                    }
                }
                else if (d < this.getMaxCombatRangeSq() && this.currentAttackMode != AttackMode.RANGED) {
                    this.currentAttackMode = AttackMode.RANGED;
                    changedAttackMode = true;
                }
            }
            else if (this.currentAttackMode != AttackMode.IDLE) {
                this.currentAttackMode = AttackMode.IDLE;
                changedAttackMode = true;
            }
            if (!this.firstUpdatedAttackMode) {
                this.firstUpdatedAttackMode = true;
                changedAttackMode = true;
            }
        }
        if (changedAttackMode || changedMounted) {
            this.onAttackModeChange(this.currentAttackMode, this.ridingMount);
        }
        if (!((Entity)this).worldObj.isClient) {
            this.prevCombatStance = (this.combatCooldown > 0);
            if (this.getAttackTarget() != null) {
                this.combatCooldown = 40;
            }
            else if (this.combatCooldown > 0) {
                --this.combatCooldown;
            }
            this.combatStance = (this.combatCooldown > 0);
            if (this.combatStance != this.prevCombatStance) {
                final int x = MathHelper.floor_double(((Entity)this).posX) >> 4;
                final int z = MathHelper.floor_double(((Entity)this).posZ) >> 4;
                final PlayerManager playermanager = ((WorldServer)((Entity)this).worldObj).getPlayerManager();
                final List players = ((Entity)this).worldObj.playerEntities;
                for (final Object obj : players) {
                    final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
                    if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                        this.sendCombatStance(entityplayer);
                    }
                }
            }
        }
    }
    
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
    }
    
    public void refreshCurrentAttackMode() {
        this.onAttackModeChange(this.currentAttackMode, this.ridingMount);
    }
    
    protected AttackMode getCurrentAttackMode() {
        return this.currentAttackMode;
    }
    
    public double getMeleeRange() {
        double d = 4.0 + ((Entity)this).width * ((Entity)this).width;
        if (this.ridingMount) {
            d *= LOTREntityNPC.MOUNT_RANGE_BONUS;
        }
        return d;
    }
    
    public final double getMeleeRangeSq() {
        final double d = this.getMeleeRange();
        return d * d;
    }
    
    public final double getMaxCombatRange() {
        final double d = this.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
        return d * 0.95;
    }
    
    public final double getMaxCombatRangeSq() {
        final double d = this.getMaxCombatRange();
        return d * d;
    }
    
    public final boolean isAimingRanged() {
        final ItemStack itemstack = this.getHeldItem();
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (!(item instanceof LOTRItemSpear) && !(item instanceof LOTRItemTrident) && itemstack.getItemUseAction() == EnumAction.bow) {
                final EntityLivingBase target = this.getAttackTarget();
                return target != null && this.getDistanceSqToEntity((Entity)target) < this.getMaxCombatRangeSq();
            }
        }
        return false;
    }
    
    private void sendCombatStance(final EntityPlayerMP entityplayer) {
        final LOTRPacketNPCCombatStance packet = new LOTRPacketNPCCombatStance(this.getEntityId(), this.combatStance);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public void sendIsEatingToWatchers() {
        final int x = MathHelper.floor_double(((Entity)this).posX) >> 4;
        final int z = MathHelper.floor_double(((Entity)this).posZ) >> 4;
        final PlayerManager playermanager = ((WorldServer)((Entity)this).worldObj).getPlayerManager();
        final List players = ((Entity)this).worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                this.sendIsEatingPacket(entityplayer);
            }
        }
    }
    
    private void sendIsEatingPacket(final EntityPlayerMP entityplayer) {
        final LOTRPacketNPCIsEating packet = new LOTRPacketNPCIsEating(this.getEntityId(), this.npcItemsInv.getIsEating());
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    protected void fall(float f) {
        if (this.bossInfo != null) {
            f = this.bossInfo.onFall(f);
        }
        super.fall(f);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        this.familyInfo.writeToNBT(nbt);
        this.questInfo.writeToNBT(nbt);
        this.hiredNPCInfo.writeToNBT(nbt);
        this.traderNPCInfo.writeToNBT(nbt);
        if (this.travellingTraderInfo != null) {
            this.travellingTraderInfo.writeToNBT(nbt);
        }
        if (this.bossInfo != null) {
            this.bossInfo.writeToNBT(nbt);
        }
        this.npcItemsInv.writeToNBT(nbt);
        this.hiredReplacedInv.writeToNBT(nbt);
        nbt.setInteger("NPCHomeX", this.getHomePosition().posX);
        nbt.setInteger("NPCHomeY", this.getHomePosition().posY);
        nbt.setInteger("NPCHomeZ", this.getHomePosition().posZ);
        nbt.setInteger("NPCHomeRadius", (int)this.func_110174_bM());
        nbt.setBoolean("NPCPersistent", this.isNPCPersistent);
        if (this.npcLocationName != null) {
            nbt.setString("NPCLocationName", this.npcLocationName);
        }
        nbt.setBoolean("SpecificLocationName", this.hasSpecificLocationName);
        nbt.setBoolean("HurtOnlyByPlates", this.hurtOnlyByPlates);
        nbt.setBoolean("RidingHorse", this.ridingMount);
        nbt.setBoolean("NPCPassive", this.isPassive);
        nbt.setBoolean("TraderEscort", this.isTraderEscort);
        if (!this.killBonusFactions.isEmpty()) {
            final NBTTagList bonusTags = new NBTTagList();
            for (final LOTRFaction f : this.killBonusFactions) {
                final String fName = f.codeName();
                bonusTags.appendTag((NBTBase)new NBTTagString(fName));
            }
            nbt.setTag("BonusFactions", (NBTBase)bonusTags);
        }
        nbt.setBoolean("InvasionSpawned", this.isInvasionSpawned);
        nbt.setBoolean("SetInitHome", this.setInitialHome);
        nbt.setInteger("InitHomeX", this.initHomeX);
        nbt.setInteger("InitHomeY", this.initHomeY);
        nbt.setInteger("InitHomeZ", this.initHomeZ);
        nbt.setInteger("InitHomeR", this.initHomeRange);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.loadingFromNBT = true;
        super.readEntityFromNBT(nbt);
        this.familyInfo.readFromNBT(nbt);
        this.questInfo.readFromNBT(nbt);
        this.hiredNPCInfo.readFromNBT(nbt);
        this.traderNPCInfo.readFromNBT(nbt);
        if (this.travellingTraderInfo != null) {
            this.travellingTraderInfo.readFromNBT(nbt);
        }
        if (this.bossInfo != null) {
            this.bossInfo.readFromNBT(nbt);
        }
        this.npcItemsInv.readFromNBT(nbt);
        this.hiredReplacedInv.readFromNBT(nbt);
        if (nbt.hasKey("NPCHomeRadius")) {
            final int x = nbt.getInteger("NPCHomeX");
            final int y = nbt.getInteger("NPCHomeY");
            final int z = nbt.getInteger("NPCHomeZ");
            final int r = nbt.getInteger("NPCHomeRadius");
            this.setHomeArea(x, y, z, r);
        }
        if (nbt.hasKey("NPCPersistent")) {
            this.isNPCPersistent = nbt.getBoolean("NPCPersistent");
        }
        if (nbt.hasKey("NPCLocationName")) {
            this.npcLocationName = nbt.getString("NPCLocationName");
        }
        this.hasSpecificLocationName = nbt.getBoolean("SpecificLocationName");
        this.hurtOnlyByPlates = nbt.getBoolean("HurtOnlyByPlates");
        this.ridingMount = nbt.getBoolean("RidingHorse");
        this.isPassive = nbt.getBoolean("NPCPassive");
        this.isTraderEscort = nbt.getBoolean("TraderEscort");
        if (nbt.hasKey("BonusFactions")) {
            final NBTTagList bonusTags = nbt.getTagList("BonusFactions", 8);
            for (int i = 0; i < bonusTags.tagCount(); ++i) {
                final String fName = bonusTags.getStringTagAt(i);
                final LOTRFaction f = LOTRFaction.forName(fName);
                if (f != null) {
                    this.killBonusFactions.add(f);
                }
            }
        }
        this.isInvasionSpawned = nbt.getBoolean("InvasionSpawned");
        this.setInitialHome = nbt.getBoolean("SetInitHome");
        this.initHomeX = nbt.getInteger("InitHomeX");
        this.initHomeY = nbt.getInteger("InitHomeY");
        this.initHomeZ = nbt.getInteger("InitHomeZ");
        this.initHomeRange = nbt.getInteger("InitHomeR");
        this.loadingFromNBT = false;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        final int id = LOTREntities.getEntityID((Entity)this);
        if (LOTREntities.spawnEggs.containsKey(id)) {
            return new ItemStack(LOTRMod.spawnEgg, 1, id);
        }
        return null;
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        float damage = (float)this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
        float weaponDamage = 0.0f;
        final ItemStack weapon = this.getEquipmentInSlot(0);
        if (weapon != null) {
            weaponDamage = LOTRWeaponStats.getMeleeDamageBonus(weapon) * 0.75f;
        }
        if (weaponDamage > 0.0f) {
            damage = weaponDamage;
        }
        damage += (float)this.getEntityAttribute(LOTREntityNPC.npcAttackDamageExtra).getAttributeValue();
        if (this.isDrunkard()) {
            damage += (float)this.getEntityAttribute(LOTREntityNPC.npcAttackDamageDrunk).getAttributeValue();
        }
        final int banners = this.nearbyBanners();
        damage += banners * 0.5f;
        int knockbackModifier = 0;
        if (entity instanceof EntityLivingBase) {
            damage += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase)this, (EntityLivingBase)entity);
            knockbackModifier += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this, (EntityLivingBase)entity);
        }
        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), damage);
        if (flag) {
            if (weapon != null && entity instanceof EntityLivingBase) {
                final int weaponItemDamage = weapon.getItemDamage();
                weapon.getItem().hitEntity(weapon, (EntityLivingBase)entity, (EntityLivingBase)this);
                weapon.setItemDamage(weaponItemDamage);
            }
            if (knockbackModifier > 0) {
                entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f), 0.1, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f));
                ((Entity)this).motionX *= 0.6;
                ((Entity)this).motionZ *= 0.6;
            }
            final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)this);
            if (fireAspectModifier > 0) {
                entity.setFire(fireAspectModifier * 4);
            }
            if (entity instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase)entity, (Entity)this);
            }
            EnchantmentHelper.func_151385_b((EntityLivingBase)this, entity);
        }
        return flag;
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        this.npcArrowAttack(target, f);
    }
    
    protected void npcArrowAttack(final EntityLivingBase target, final float f) {
        final ItemStack heldItem = this.getHeldItem();
        float str = 1.3f + this.getDistanceToEntity((Entity)target) / 80.0f;
        str *= LOTRItemBow.getLaunchSpeedFactor(heldItem);
        final float accuracy = (float)this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).getAttributeValue();
        final float poisonChance = this.getPoisonedArrowChance();
        EntityArrow arrow;
        if (((Entity)this).rand.nextFloat() < poisonChance) {
            arrow = new LOTREntityArrowPoisoned(((Entity)this).worldObj, (EntityLivingBase)this, target, str, accuracy);
        }
        else {
            arrow = new EntityArrow(((Entity)this).worldObj, (EntityLivingBase)this, target, str, accuracy);
        }
        if (heldItem != null) {
            LOTRItemBow.applyBowModifiers(arrow, heldItem);
        }
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)arrow);
    }
    
    protected void npcCrossbowAttack(final EntityLivingBase target, final float f) {
        final ItemStack heldItem = this.getHeldItem();
        float str = 1.0f + this.getDistanceToEntity((Entity)target) / 16.0f * 0.015f;
        str *= LOTRItemCrossbow.getCrossbowLaunchSpeedFactor(heldItem);
        final boolean poison = ((Entity)this).rand.nextFloat() < this.getPoisonedArrowChance();
        final ItemStack boltItem = poison ? new ItemStack(LOTRMod.crossbowBoltPoisoned) : new ItemStack(LOTRMod.crossbowBolt);
        final LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(((Entity)this).worldObj, (EntityLivingBase)this, target, boltItem, str, 1.0f);
        if (heldItem != null) {
            LOTRItemCrossbow.applyCrossbowModifiers(bolt, heldItem);
        }
        this.playSound("lotr:item.crossbow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)bolt);
    }
    
    protected float getPoisonedArrowChance() {
        return 0.0f;
    }
    
    public void onKillEntity(final EntityLivingBase entity) {
        super.onKillEntity(entity);
        this.hiredNPCInfo.onKillEntity(entity);
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        if (((Entity)this).riddenByEntity != null && damagesource.getEntity() == ((Entity)this).riddenByEntity) {
            return false;
        }
        final int banners = this.nearbyBanners();
        if (banners > 0) {
            final int i = 12 - banners;
            final float f2 = f * i;
            f = f2 / 12.0f;
        }
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && damagesource.getEntity() instanceof LOTREntityNPC) {
            final LOTREntityNPC attacker = (LOTREntityNPC)damagesource.getEntity();
            if (attacker.hiredNPCInfo.isActive && attacker.hiredNPCInfo.getHiringPlayer() != null) {
                ((EntityLivingBase)this).recentlyHit = 100;
                ((EntityLivingBase)this).attackingPlayer = null;
            }
        }
        if (flag && !((Entity)this).worldObj.isClient && this.hurtOnlyByPlates) {
            this.hurtOnlyByPlates = (damagesource.getSourceOfDamage() instanceof LOTREntityPlate);
        }
        return flag;
    }
    
    protected void damageEntity(final DamageSource damagesource, final float f) {
        super.damageEntity(damagesource, f);
        if (this.bossInfo != null) {
            this.bossInfo.onHurt(damagesource, f);
        }
    }
    
    public final boolean canPickUpLoot() {
        return false;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        this.hiredReplacedInv.dropAllReplacedItems();
        this.dropNPCEquipment(flag, i);
        if (flag && this.canDropRares()) {
            int coinChance = 8 - i * 2;
            coinChance = Math.max(coinChance, 1);
            if (((Entity)this).rand.nextInt(coinChance) == 0) {
                int coins = this.getRandomCoinDropAmount();
                coins *= MathHelper.getRandomIntegerInRange(((Entity)this).rand, 1, i + 1);
                this.func_145779_a(LOTRMod.silverCoin, coins);
            }
            int rareChance = 50 - i * 5;
            rareChance = Math.max(rareChance, 1);
            if (((Entity)this).rand.nextInt(rareChance) == 0) {
                this.dropChestContents(LOTRChestContents.RARE_DROPS, 1, 1);
            }
        }
        if (flag && this.canDropRares()) {
            int modChance = 60;
            modChance -= i * 5;
            modChance = Math.max(modChance, 1);
            if (((Entity)this).rand.nextInt(modChance) == 0) {
                final ItemStack modItem = LOTRItemModifierTemplate.getRandomCommonTemplate(((Entity)this).rand);
                this.entityDropItem(modItem, 0.0f);
            }
        }
        if (this.getFaction() == LOTRFaction.UTUMNO && LOTRDimension.getCurrentDimension(((Entity)this).worldObj) == LOTRDimension.UTUMNO) {
            final LOTRUtumnoLevel level = LOTRUtumnoLevel.forY((int)((Entity)this).posY);
            if (((Entity)this).rand.nextInt(12) == 0) {
                if (level == LOTRUtumnoLevel.ICE) {
                    final ItemStack keypart = new ItemStack(LOTRMod.utumnoKey);
                    final int l = ((Entity)this).rand.nextInt(3);
                    if (l == 0) {
                        keypart.setItemDamage(2);
                    }
                    else if (l == 1) {
                        keypart.setItemDamage(3);
                    }
                    else if (l == 2) {
                        keypart.setItemDamage(4);
                    }
                    this.entityDropItem(keypart, 0.0f);
                }
                else if (level == LOTRUtumnoLevel.OBSIDIAN) {
                    final ItemStack keypart = new ItemStack(LOTRMod.utumnoKey);
                    final int l = ((Entity)this).rand.nextInt(3);
                    if (l == 0) {
                        keypart.setItemDamage(5);
                    }
                    else if (l == 1) {
                        keypart.setItemDamage(6);
                    }
                    else if (l == 2) {
                        keypart.setItemDamage(7);
                    }
                    this.entityDropItem(keypart, 0.0f);
                }
            }
            if (level == LOTRUtumnoLevel.ICE && this.isChilly) {
                int chillChance = 30;
                chillChance -= i * 3;
                chillChance = Math.max(chillChance, 1);
                if (((Entity)this).rand.nextInt(chillChance) == 0) {
                    int chills = 1;
                    if (i > 0) {
                        float x;
                        for (x = MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, i * 0.667f); x > 1.0f; --x, ++chills) {}
                        if (((Entity)this).rand.nextFloat() < x) {
                            ++chills;
                        }
                    }
                    for (int j = 0; j < chills; ++j) {
                        this.func_145779_a(LOTRMod.chilling, 1);
                    }
                }
            }
            if (level == LOTRUtumnoLevel.FIRE && this.canDropRares()) {
                int pickChance = 100;
                pickChance -= i * 20;
                pickChance = Math.max(pickChance, 1);
                if (((Entity)this).rand.nextInt(pickChance) == 0) {
                    this.entityDropItem(new ItemStack(LOTRMod.utumnoPickaxe), 0.0f);
                }
            }
            if (((Entity)this).rand.nextInt(20) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.mithrilNugget), 0.0f);
            }
        }
    }
    
    protected int getRandomCoinDropAmount() {
        return 1 + (int)Math.round(Math.pow(1.0 + Math.abs(((Entity)this).rand.nextGaussian()), 3.0) * 0.25);
    }
    
    public void dropNPCEquipment(final boolean flag, final int i) {
        if (flag) {
            int equipmentCount = 0;
            for (int j = 0; j < 5; ++j) {
                if (this.getEquipmentInSlot(j) != null) {
                    ++equipmentCount;
                }
            }
            if (equipmentCount > 0) {
                for (int j = 0; j < 5; ++j) {
                    final ItemStack equipmentDrop = this.getEquipmentInSlot(j);
                    if (equipmentDrop != null) {
                        final boolean dropGuaranteed = ((EntityLiving)this).equipmentDropChances[j] >= 1.0f;
                        if (!dropGuaranteed) {
                            int chance = 20 * equipmentCount - i * 4 * equipmentCount;
                            chance = Math.max(chance, 1);
                            if (((Entity)this).rand.nextInt(chance) != 0) {
                                continue;
                            }
                        }
                        if (!dropGuaranteed) {
                            final int dropDamage = MathHelper.floor_double((double)(equipmentDrop.getItem().getMaxDamage() * (0.5f + ((Entity)this).rand.nextFloat() * 0.25f)));
                            equipmentDrop.setItemDamage(dropDamage);
                        }
                        this.entityDropItem(equipmentDrop, 0.0f);
                        this.setCurrentItemOrArmor(j, (ItemStack)null);
                    }
                }
            }
        }
    }
    
    protected void dropChestContents(final LOTRChestContents itemPool, final int min, final int max) {
        final IInventory drops = (IInventory)new InventoryBasic("drops", false, max * 5);
        LOTRChestContents.fillInventory(drops, ((Entity)this).rand, itemPool, MathHelper.getRandomIntegerInRange(((Entity)this).rand, min, max), true);
        for (int i = 0; i < drops.getSizeInventory(); ++i) {
            final ItemStack item = drops.getStackInSlot(i);
            if (item != null) {
                this.entityDropItem(item, 0.0f);
            }
        }
    }
    
    protected void dropNPCArrows(final int i) {
        this.dropNPCAmmo(Items.arrow, i);
    }
    
    protected void dropNPCCrossbowBolts(final int i) {
        this.dropNPCAmmo(LOTRMod.crossbowBolt, i);
    }
    
    protected void dropNPCAmmo(final Item item, final int i) {
        for (int ammo = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < ammo; ++l) {
            this.func_145779_a(item, 1);
        }
    }
    
    public final void dropEquipment(final boolean flag, final int i) {
    }
    
    public final EntityItem entityDropItem(final ItemStack item, final float offset) {
        return this.npcDropItem(item, offset, true);
    }
    
    public final EntityItem npcDropItem(final ItemStack item, final float offset, final boolean enpouch) {
        if (enpouch) {
            if (item != null && item.getItem() != null && item.getMaxStackSize() == 1) {
                if (!item.hasTagCompound()) {
                    item.setTagCompound(new NBTTagCompound());
                }
                final NBTTagCompound nbt = item.getTagCompound();
                nbt.setString("LOTROwner", this.getCommandSenderName());
            }
            if (this.enpouchNPCDrops && item != null) {
                this.enpouchedDrops.add(item);
                return null;
            }
        }
        return super.entityDropItem(item, offset);
    }
    
    public void onDeath(final DamageSource damagesource) {
        this.enpouchNPCDrops = true;
        this.hiredNPCInfo.onDeath(damagesource);
        if (this.travellingTraderInfo != null) {
            this.travellingTraderInfo.onDeath();
        }
        if (this.bossInfo != null) {
            this.bossInfo.onDeath(damagesource);
        }
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && ((EntityLivingBase)this).recentlyHit > 0 && this.canDropRares() && LOTRMod.canDropLoot(((Entity)this).worldObj) && ((Entity)this).rand.nextInt(60) == 0) {
            final ItemStack pouch = this.createNPCPouchDrop();
            this.fillPouchFromListAndRetainUnfilled(pouch, this.enpouchedDrops);
            this.enpouchNPCDrops = false;
            this.entityDropItem(pouch, 0.0f);
        }
        this.enpouchNPCDrops = false;
        this.dropItemList(this.enpouchedDrops);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
            if (this.hurtOnlyByPlates && damagesource.getSourceOfDamage() instanceof LOTREntityPlate) {
                if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) < 0.0f) {}
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killUsingOnlyPlates);
            }
            if (damagesource.getSourceOfDamage() instanceof LOTREntityPebble) {
                final LOTREntityPebble pebble = (LOTREntityPebble)damagesource.getSourceOfDamage();
                if (pebble.isSling()) {
                    final float size = ((Entity)this).width * ((Entity)this).width * ((Entity)this).height;
                    if (size > 5.0f) {
                        final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction());
                        if (alignment < 0.0f) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killLargeMobWithSlingshot);
                        }
                    }
                }
            }
            if (this instanceof LOTREntityOrc) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killOrc);
            }
            if (this instanceof LOTREntityWarg) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killWarg);
            }
            if (this.getKillAchievement() != null) {
                LOTRLevelData.getData(entityplayer).addAchievement(this.getKillAchievement());
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            boolean flag = false;
            if (this instanceof LOTRTradeable) {
                flag = ((LOTRTradeable)this).shouldTraderRespawn();
            }
            else if (this instanceof LOTRUnitTradeable) {
                flag = ((LOTRUnitTradeable)this).shouldTraderRespawn();
            }
            if (flag) {
                final LOTREntityTraderRespawn entity = new LOTREntityTraderRespawn(((Entity)this).worldObj);
                entity.setLocationAndAngles(((Entity)this).posX, ((Entity)this).boundingBox.minY + ((Entity)this).height / 2.0f, ((Entity)this).posZ, 0.0f, 0.0f);
                entity.copyTraderDataFrom(this);
                ((Entity)this).worldObj.spawnEntityInWorld((Entity)entity);
                entity.onSpawn();
            }
        }
        this.questInfo.onDeath();
    }
    
    public ItemStack createNPCPouchDrop() {
        final ItemStack pouch = new ItemStack(LOTRMod.pouch, 1, LOTRItemPouch.getRandomPouchSize(((Entity)this).rand));
        if (((Entity)this).rand.nextBoolean()) {
            final LOTRFaction faction = this.getFaction();
            if (faction != null) {
                LOTRItemPouch.setPouchColor(pouch, faction.getFactionColor());
            }
        }
        return pouch;
    }
    
    public void fillPouchFromListAndRetainUnfilled(final ItemStack pouch, final List<ItemStack> items) {
        final List<ItemStack> pouchContents = new ArrayList<ItemStack>();
        while (!items.isEmpty()) {
            pouchContents.add(items.remove(0));
            if (pouchContents.size() >= LOTRItemPouch.getCapacity(pouch)) {
                break;
            }
        }
        for (final ItemStack itemstack : pouchContents) {
            if (!LOTRItemPouch.tryAddItemToPouch(pouch, itemstack, false)) {
                items.add(itemstack);
            }
        }
    }
    
    public void dropItemList(final List<ItemStack> items) {
        if (!items.isEmpty()) {
            for (final ItemStack item : items) {
                this.entityDropItem(item, 0.0f);
            }
        }
    }
    
    protected LOTRAchievement getKillAchievement() {
        return null;
    }
    
    public void setDead() {
        super.setDead();
        if (((EntityLivingBase)this).deathTime == 0 && ((Entity)this).ridingEntity != null) {
            ((Entity)this).ridingEntity.setDead();
        }
    }
    
    public boolean canDropRares() {
        return !this.hiredNPCInfo.isActive;
    }
    
    public float getAlignmentBonus() {
        return 0.0f;
    }
    
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 4 + ((Entity)this).rand.nextInt(3);
    }
    
    public float getBlockPathWeight(final int i, final int j, final int k) {
        if (this.liftSpawnRestrictions) {
            return 1.0f;
        }
        if (!this.isConquestSpawning || !this.conquestSpawnIgnoresDarkness()) {
            if (this.spawnsInDarkness) {
                final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
                if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
                    return 1.0f;
                }
            }
            if (this.spawnsInDarkness) {
                return 0.5f - ((Entity)this).worldObj.getLightBrightness(i, j, k);
            }
        }
        return 0.0f;
    }
    
    private boolean isValidLightLevelForDarkSpawn() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        if (this.spawnsInDarkness) {
            final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
            if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
                return true;
            }
        }
        if (((Entity)this).worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > ((Entity)this).rand.nextInt(32)) {
            return false;
        }
        int l = ((Entity)this).worldObj.getBlockLightValue(i, j, k);
        if (((Entity)this).worldObj.isThundering()) {
            final int i2 = ((Entity)this).worldObj.skylightSubtracted;
            ((Entity)this).worldObj.skylightSubtracted = 10;
            l = ((Entity)this).worldObj.getBlockLightValue(i, j, k);
            ((Entity)this).worldObj.skylightSubtracted = i2;
        }
        return l <= ((Entity)this).rand.nextInt(8);
    }
    
    public void setConquestSpawning(final boolean flag) {
        this.isConquestSpawning = flag;
    }
    
    protected boolean conquestSpawnIgnoresDarkness() {
        return true;
    }
    
    public boolean getCanSpawnHere() {
        if ((!this.spawnsInDarkness || this.liftSpawnRestrictions || (this.isConquestSpawning && this.conquestSpawnIgnoresDarkness()) || this.isValidLightLevelForDarkSpawn()) && super.getCanSpawnHere()) {
            if (!this.liftBannerRestrictions) {
                if (LOTRBannerProtection.isProtectedByBanner(((Entity)this).worldObj, (Entity)this, LOTRBannerProtection.forNPC((EntityLiving)this), false)) {
                    return false;
                }
                if (!this.isConquestSpawning && LOTREntityNPCRespawner.isSpawnBlocked(this)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public final int getSpawnCountValue() {
        if (this.isNPCPersistent || this.hiredNPCInfo.isActive) {
            return 0;
        }
        int multiplier = 1;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).posZ));
        if (biome instanceof LOTRBiome) {
            multiplier = ((LOTRBiome)biome).spawnCountMultiplier();
        }
        return multiplier;
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        if (!((Entity)this).worldObj.isClient && this.canNPCTalk()) {
            if (this.questInfo.interact(entityplayer)) {
                return true;
            }
            if (this.getAttackTarget() == null && this.speakTo(entityplayer)) {
                return true;
            }
        }
        return super.interact(entityplayer);
    }
    
    public void sendSpeechBank(final EntityPlayer entityplayer, final String speechBank) {
        this.sendSpeechBank(entityplayer, speechBank, null);
    }
    
    public void sendSpeechBank(final EntityPlayer entityplayer, final String speechBank, final LOTRMiniQuest miniquest) {
        String location = null;
        String objective = null;
        if (this.npcLocationName != null) {
            if (!this.hasSpecificLocationName) {
                location = StatCollector.translateToLocalFormatted(this.npcLocationName, new Object[] { this.getNPCName() });
            }
            else {
                location = this.npcLocationName;
            }
        }
        if (miniquest != null) {
            objective = miniquest.getProgressedObjectiveInSpeech();
        }
        this.sendSpeechBank(entityplayer, speechBank, location, objective);
    }
    
    public void sendSpeechBank(final EntityPlayer entityplayer, final String speechBank, final String location, final String objective) {
        LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, speechBank, entityplayer, location, objective));
        this.markNPCSpoken();
    }
    
    public void sendSpeechBankLine(final EntityPlayer entityplayer, final String speechBank, final int i) {
        LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getSpeechLineForPlayer(this, speechBank, i, entityplayer, null, null));
        this.markNPCSpoken();
    }
    
    public boolean isFriendly(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.getAttackTarget() != entityplayer && ((EntityLivingBase)this).attackingPlayer != entityplayer;
    }
    
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
    
    public boolean speakTo(final EntityPlayer entityplayer) {
        String speechBank = this.getSpeechBank(entityplayer);
        if (((Entity)this).rand.nextInt(8) == 0) {
            if (LOTRMod.isChristmas()) {
                speechBank = "special/christmas";
            }
            else if (LOTRMod.isNewYearsDay()) {
                speechBank = "special/newYear";
            }
            else if (LOTRMod.isAprilFools()) {
                speechBank = "special/aprilFool";
            }
            else if (LOTRMod.isHalloween()) {
                speechBank = "special/halloween";
            }
        }
        if (((Entity)this).rand.nextInt(10000) == 0) {
            speechBank = "special/smilebc";
        }
        if (speechBank != null) {
            this.sendSpeechBank(entityplayer, speechBank);
            if (this.getTalkAchievement() != null) {
                LOTRLevelData.getData(entityplayer).addAchievement(this.getTalkAchievement());
            }
            return true;
        }
        return false;
    }
    
    protected LOTRAchievement getTalkAchievement() {
        return null;
    }
    
    public LOTRMiniQuest createMiniQuest() {
        return null;
    }
    
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return null;
    }
    
    public void onArtificalSpawn() {
    }
    
    public boolean isDrunkard() {
        return this.familyInfo.isDrunk();
    }
    
    public boolean canGetDrunk() {
        return !this.isChild() && !this.isTrader() && !this.isTraderEscort && !this.hiredNPCInfo.isActive;
    }
    
    public float getDrunkenSpeechFactor() {
        if (((Entity)this).rand.nextInt(3) == 0) {
            return MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, 0.3f);
        }
        return 0.0f;
    }
    
    public boolean shouldRenderNPCHair() {
        return true;
    }
    
    public boolean shouldRenderNPCChest() {
        return !this.familyInfo.isMale() && !this.isChild() && this.getEquipmentInSlot(3) == null;
    }
    
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return true;
    }
    
    public void setSpecificLocationName(final String name) {
        this.npcLocationName = name;
        this.hasSpecificLocationName = true;
    }
    
    public boolean getHasSpecificLocationName() {
        return this.hasSpecificLocationName;
    }
    
    public final int nearbyBanners() {
        if (this.getFaction() == LOTRFaction.UNALIGNED) {
            return 0;
        }
        int banners = 0;
        final double range = 16.0;
        final List entities = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTRBannerBearer.class, ((Entity)this).boundingBox.expand(range, range, range));
        for (int i = 0; i < entities.size(); ++i) {
            final EntityLivingBase entity = entities.get(i);
            if (entity != this && entity.isEntityAlive() && LOTRMod.getNPCFaction((Entity)entity) == this.getFaction()) {
                ++banners;
            }
        }
        return Math.min(banners, 5);
    }
    
    public final ItemStack getEquipmentInSlot(final int i) {
        if (((Entity)this).worldObj.isClient) {
            if (!this.initFestiveItems) {
                this.festiveRand.setSeed(this.getEntityId() * 341873128712L);
                if (LOTRMod.isHalloween()) {
                    if (this.festiveRand.nextInt(3) == 0) {
                        this.festiveItems[4] = ((this.festiveRand.nextInt(10) == 0) ? new ItemStack(Blocks.lit_pumpkin) : new ItemStack(Blocks.pumpkin));
                    }
                }
                else if (LOTRMod.isChristmas() && this.festiveRand.nextInt(3) == 0) {
                    ItemStack hat;
                    if (((Entity)this).rand.nextBoolean()) {
                        hat = new ItemStack(LOTRMod.leatherHat);
                        LOTRItemLeatherHat.setHatColor(hat, 13378587);
                        LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
                        this.festiveItems[4] = hat;
                    }
                    else {
                        hat = new ItemStack(LOTRMod.partyHat);
                        final float hue = ((Entity)this).rand.nextFloat();
                        LOTRItemPartyHat.setHatColor(hat, Color.HSBtoRGB(hue, 1.0f, 1.0f));
                    }
                    this.festiveItems[4] = hat;
                }
                this.initFestiveItems = true;
            }
            if (this.festiveItems[i] != null) {
                return this.festiveItems[i];
            }
        }
        return super.getEquipmentInSlot(i);
    }
    
    public final ItemStack func_130225_q(final int i) {
        return this.getEquipmentInSlot(i + 1);
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    public void setCustomNameTag(final String name) {
        if (this.canRenameNPC() || this.loadingFromNBT) {
            super.setCustomNameTag(name);
        }
    }
    
    public boolean canRenameNPC() {
        return false;
    }
    
    public void func_110163_bv() {
    }
    
    public boolean shouldDismountInWater(final Entity rider) {
        return false;
    }
    
    public void spawnHearts() {
        if (!((Entity)this).worldObj.isClient) {
            final LOTRPacketNPCFX packet = new LOTRPacketNPCFX(this.getEntityId(), LOTRPacketNPCFX.FXType.HEARTS);
            LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)this, 32.0));
        }
        else {
            for (int i = 0; i < 8; ++i) {
                final double d = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d2 = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d3 = ((Entity)this).rand.nextGaussian() * 0.02;
                ((Entity)this).worldObj.spawnParticle("heart", ((Entity)this).posX + ((Entity)this).rand.nextFloat() * ((Entity)this).width * 2.0f - ((Entity)this).width, ((Entity)this).posY + 0.5 + ((Entity)this).rand.nextFloat() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextFloat() * ((Entity)this).width * 2.0f - ((Entity)this).width, d, d2, d3);
            }
        }
    }
    
    public void spawnSmokes() {
        if (!((Entity)this).worldObj.isClient) {
            final LOTRPacketNPCFX packet = new LOTRPacketNPCFX(this.getEntityId(), LOTRPacketNPCFX.FXType.SMOKE);
            LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)this, 32.0));
        }
        else {
            for (int i = 0; i < 8; ++i) {
                final double d = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d2 = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d3 = ((Entity)this).rand.nextGaussian() * 0.02;
                ((Entity)this).worldObj.spawnParticle("smoke", ((Entity)this).posX + ((Entity)this).rand.nextFloat() * ((Entity)this).width * 2.0f - ((Entity)this).width, ((Entity)this).posY + 0.5 + ((Entity)this).rand.nextFloat() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextFloat() * ((Entity)this).width * 2.0f - ((Entity)this).width, d, d2, d3);
            }
        }
    }
    
    public void spawnFoodParticles() {
        if (this.getHeldItem() == null) {
            return;
        }
        if (!((Entity)this).worldObj.isClient) {
            final LOTRPacketNPCFX packet = new LOTRPacketNPCFX(this.getEntityId(), LOTRPacketNPCFX.FXType.EATING);
            LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)this, 32.0));
        }
        else {
            for (int i = 0; i < 5; ++i) {
                final Vec3 vec1 = Vec3.createVectorHelper((((Entity)this).rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                vec1.rotateAroundX(-((Entity)this).rotationPitch * 3.1415927f / 180.0f);
                vec1.rotateAroundY(-((Entity)this).rotationYaw * 3.1415927f / 180.0f);
                Vec3 vec2 = Vec3.createVectorHelper((((Entity)this).rand.nextFloat() - 0.5) * 0.3, -((Entity)this).rand.nextFloat() * 0.6 - 0.3, 0.6);
                vec2.rotateAroundX(-((Entity)this).rotationPitch * 3.1415927f / 180.0f);
                vec2.rotateAroundY(-((Entity)this).rotationYaw * 3.1415927f / 180.0f);
                vec2 = vec2.addVector(((Entity)this).posX, ((Entity)this).posY + this.getEyeHeight(), ((Entity)this).posZ);
                ((Entity)this).worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(this.getHeldItem().getItem()), vec2.xCoord, vec2.yCoord, vec2.zCoord, vec1.xCoord, vec1.yCoord + 0.05, vec1.zCoord);
            }
        }
    }
    
    public ItemStack getHeldItemLeft() {
        if (this instanceof LOTRBannerBearer) {
            final LOTRBannerBearer bannerBearer = (LOTRBannerBearer)this;
            return new ItemStack(LOTRMod.banner, 1, bannerBearer.getBannerType().bannerID);
        }
        if (this.isTrader()) {
            boolean showCoin = false;
            if (this.npcShield == null) {
                showCoin = true;
            }
            else if (!this.clientCombatStance && this.hiredNPCInfo.getHiringPlayerUUID() == null) {
                showCoin = true;
            }
            if (showCoin) {
                return new ItemStack(LOTRMod.silverCoin);
            }
        }
        return null;
    }
    
    public void playTradeSound() {
        this.playSound("lotr:event.trade", 0.5f, 1.0f + (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.1f);
    }
    
    static {
        LOTREntityNPC.npcAttackDamage = (IAttribute)new RangedAttribute("lotr.npcAttackDamage", 2.0, 0.0, Double.MAX_VALUE).setDescription("LOTR NPC Attack Damage");
        LOTREntityNPC.npcAttackDamageExtra = (IAttribute)new RangedAttribute("lotr.npcAttackDamageExtra", 0.0, 0.0, Double.MAX_VALUE).setDescription("LOTR NPC Extra Attack Damage");
        LOTREntityNPC.npcAttackDamageDrunk = (IAttribute)new RangedAttribute("lotr.npcAttackDamageDrunk", 4.0, 0.0, Double.MAX_VALUE).setDescription("LOTR NPC Drunken Attack Damage");
        LOTREntityNPC.npcRangedAccuracy = (IAttribute)new RangedAttribute("lotr.npcRangedAccuracy", 1.0, 0.0, Double.MAX_VALUE).setDescription("LOTR NPC Ranged Accuracy");
        LOTREntityNPC.horseAttackSpeed = (IAttribute)new RangedAttribute("lotr.horseAttackSpeed", 1.7, 0.0, Double.MAX_VALUE).setDescription("LOTR Horse Attack Speed");
        LOTREntityNPC.MOUNT_RANGE_BONUS = 1.5f;
    }
    
    protected enum AttackMode
    {
        MELEE, 
        RANGED, 
        IDLE;
    }
}
