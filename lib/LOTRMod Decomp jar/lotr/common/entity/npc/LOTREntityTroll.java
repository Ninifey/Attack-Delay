// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetTroll;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAITrollFleeSun;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityTroll extends LOTREntityNPC
{
    private int sneeze;
    public int sniffTime;
    public boolean trollImmuneToSun;
    
    public LOTREntityTroll(final World world) {
        super(world);
        this.trollImmuneToSun = false;
        final float f = this.getTrollScale();
        this.setSize(1.6f * f, 3.2f * f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIRestrictSun((EntityCreature)this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAITrollFleeSun(this, 2.5));
        ((EntityLiving)this).tasks.addTask(4, this.getTrollAttackAI());
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f, 0.01f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetTroll.class);
        super.spawnsInDarkness = true;
    }
    
    public float getTrollScale() {
        return 1.0f;
    }
    
    public EntityAIBase getTrollAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)(byte)((Entity)this).rand.nextInt(3));
        ((Entity)this).dataWatcher.addObject(17, (Object)(-1));
        ((Entity)this).dataWatcher.addObject(18, (Object)0);
        ((Entity)this).dataWatcher.addObject(19, (Object)0);
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getTrollName(((Entity)this).rand));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(5.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(10) == 0) {
            this.setHasTwoHeads(true);
            double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
            maxHealth *= 1.5;
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
            this.setHealth(this.getMaxHealth());
            double attack = this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getBaseValue();
            attack += 3.0;
            this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(attack);
            double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
            speed *= 1.4;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
        }
        return data;
    }
    
    public int getTotalArmorValue() {
        return 8;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }
    
    protected boolean hasTrollName() {
        return true;
    }
    
    @Override
    public String getNPCName() {
        if (this.hasTrollName()) {
            return super.familyInfo.getName();
        }
        return super.getNPCName();
    }
    
    public int getTrollOutfit() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setTrollOutfit(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)i);
    }
    
    public int getTrollBurnTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(17);
    }
    
    public void setTrollBurnTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(short)i);
    }
    
    public int getSneezingTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(18);
    }
    
    public void setSneezingTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)i);
    }
    
    public boolean hasTwoHeads() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    public void setHasTwoHeads(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(19, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("TrollOutfit", (byte)this.getTrollOutfit());
        nbt.setInteger("TrollBurnTime", this.getTrollBurnTime());
        nbt.setInteger("Sneeze", this.sneeze);
        nbt.setInteger("SneezeTime", this.getSneezingTime());
        nbt.setBoolean("ImmuneToSun", this.trollImmuneToSun);
        nbt.setBoolean("TwoHeads", this.hasTwoHeads());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setTrollOutfit(nbt.getByte("TrollOutfit"));
        this.setTrollBurnTime(nbt.getInteger("TrollBurnTime"));
        this.sneeze = nbt.getInteger("Sneeze");
        this.setSneezingTime(nbt.getInteger("SneezeTime"));
        this.trollImmuneToSun = nbt.getBoolean("ImmuneToSun");
        this.setHasTwoHeads(nbt.getBoolean("TwoHeads"));
        if (nbt.hasKey("TrollName")) {
            super.familyInfo.setName(nbt.getString("TrollName"));
        }
    }
    
    @Override
    protected boolean conquestSpawnIgnoresDarkness() {
        return this.trollImmuneToSun;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getTrollBurnTime() >= 0 && this.isEntityAlive()) {
            if (!((Entity)this).worldObj.isClient) {
                final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).posZ));
                if (this.trollImmuneToSun || (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) || !((Entity)this).worldObj.isDaytime() || !((Entity)this).worldObj.canBlockSeeTheSky(MathHelper.floor_double(((Entity)this).posX), (int)((Entity)this).boundingBox.minY, MathHelper.floor_double(((Entity)this).posZ))) {
                    this.setTrollBurnTime(-1);
                }
                else {
                    this.setTrollBurnTime(this.getTrollBurnTime() - 1);
                    if (this.getTrollBurnTime() == 0) {
                        this.onTrollDeathBySun();
                        if (super.hiredNPCInfo.isActive && super.hiredNPCInfo.getHiringPlayer() != null) {
                            super.hiredNPCInfo.getHiringPlayer().addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.hiredNPC.trollStone", new Object[] { this.getCommandSenderName() }));
                        }
                    }
                }
            }
            else {
                ((Entity)this).worldObj.spawnParticle("largesmoke", ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, 0.0, 0.0, 0.0);
            }
        }
        if (this.sniffTime > 0) {
            --this.sniffTime;
        }
        if (!((Entity)this).worldObj.isClient && this.getSneezingTime() > 0) {
            this.setSneezingTime(this.getSneezingTime() - 1);
            if (this.getSneezingTime() == 8) {
                ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.sneeze", this.getSoundVolume() * 1.5f, this.getSoundPitch());
            }
            if (this.getSneezingTime() == 4) {
                for (int slimes = 2 + ((Entity)this).rand.nextInt(3), i = 0; i < slimes; ++i) {
                    final EntityItem entityitem = new EntityItem(((Entity)this).worldObj, ((Entity)this).posX, ((Entity)this).posY + this.getEyeHeight(), ((Entity)this).posZ, new ItemStack(Items.slime_ball));
                    entityitem.delayBeforeCanPickup = 40;
                    float f = 1.0f;
                    ((Entity)entityitem).motionX = -MathHelper.sin(((EntityLivingBase)this).rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(((Entity)this).rotationPitch / 180.0f * 3.1415927f) * f;
                    ((Entity)entityitem).motionZ = MathHelper.cos(((EntityLivingBase)this).rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(((Entity)this).rotationPitch / 180.0f * 3.1415927f) * f;
                    ((Entity)entityitem).motionY = -MathHelper.sin(((Entity)this).rotationPitch / 180.0f * 3.1415927f) * f + 0.1f;
                    f = 0.02f;
                    final float f2 = ((Entity)this).rand.nextFloat() * 3.1415927f * 2.0f;
                    f *= ((Entity)this).rand.nextFloat();
                    final EntityItem entityItem = entityitem;
                    ((Entity)entityItem).motionX += Math.cos(f2) * f;
                    final EntityItem entityItem2 = entityitem;
                    ((Entity)entityItem2).motionY += (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.1f;
                    final EntityItem entityItem3 = entityitem;
                    ((Entity)entityItem3).motionZ += Math.sin(f2) * f;
                    ((Entity)this).worldObj.spawnEntityInWorld((Entity)entityitem);
                }
            }
            if (this.getSneezingTime() == 0) {
                this.sneeze = 0;
            }
        }
    }
    
    public void onTrollDeathBySun() {
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
        this.setDead();
        final LOTREntityStoneTroll stoneTroll = new LOTREntityStoneTroll(((Entity)this).worldObj);
        stoneTroll.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rotationYaw, 0.0f);
        stoneTroll.setTrollOutfit(this.getTrollOutfit());
        stoneTroll.setHasTwoHeads(this.hasTwoHeads());
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)stoneTroll);
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            this.spawnExplosionParticle();
        }
        else if (b == 16) {
            this.sniffTime = 16;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        if (!((Entity)this).worldObj.isClient && this.canTrollBeTickled(entityplayer)) {
            final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "feather") && this.getSneezingTime() == 0) {
                if (((Entity)this).rand.nextBoolean()) {
                    ++this.sneeze;
                }
                if (!entityplayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = itemstack;
                    --itemStack.stackSize;
                }
                if (itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                }
                super.npcTalkTick = this.getNPCTalkInterval() / 2;
                if (this.sneeze >= 3) {
                    this.setSneezingTime(16);
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.makeTrollSneeze);
                }
                else {
                    LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "troll/tickle", entityplayer));
                    ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.sniff", this.getSoundVolume(), this.getSoundPitch());
                    ((Entity)this).worldObj.setEntityState((Entity)this, (byte)16);
                }
            }
        }
        return super.interact(entityplayer);
    }
    
    protected boolean canTrollBeTickled(final EntityPlayer entityplayer) {
        return this.canNPCTalk() && this.isFriendly(entityplayer) && LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f && this.getAttackTarget() == null && this.getTrollBurnTime() == -1;
    }
    
    public void knockBack(final Entity entity, final float f, final double d, final double d1) {
        super.knockBack(entity, f, d, d1);
        ((Entity)this).motionX /= 2.0;
        ((Entity)this).motionY /= 2.0;
        ((Entity)this).motionZ /= 2.0;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            final float attackDamage = (float)this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            final float knockbackModifier = 0.25f * attackDamage;
            entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f), knockbackModifier * 0.1, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f));
            return true;
        }
        return false;
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer && this.getTrollBurnTime() >= 0) {
            LOTRLevelData.getData((EntityPlayer)damagesource.getEntity()).addAchievement(LOTRAchievement.killTrollFleeingSun);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killTroll;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 4 + ((Entity)this).rand.nextInt(5);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = 2 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(LOTRMod.trollBone, 1);
        }
        this.dropTrollItems(flag, i);
    }
    
    public void dropTrollItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(3) == 0) {
            for (int j = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), k = 0; k < j; ++k) {
                this.func_145779_a(Items.slime_ball, 1);
            }
        }
        for (int animalDrops = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < animalDrops; ++l) {
            final int drop = ((Entity)this).rand.nextInt(10);
            switch (drop) {
                case 0: {
                    this.entityDropItem(new ItemStack(Items.leather, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 1: {
                    this.entityDropItem(new ItemStack(Items.beef, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 2: {
                    this.entityDropItem(new ItemStack(Items.chicken, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.feather, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 4: {
                    this.entityDropItem(new ItemStack(Items.porkchop, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 5: {
                    this.entityDropItem(new ItemStack(Blocks.wool, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 6: {
                    this.entityDropItem(new ItemStack(Items.rotten_flesh, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 7: {
                    this.entityDropItem(new ItemStack(LOTRMod.rabbitRaw, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 8: {
                    this.entityDropItem(new ItemStack(LOTRMod.muttonRaw, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 9: {
                    this.entityDropItem(new ItemStack(LOTRMod.deerRaw, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
            }
        }
    }
    
    public String getLivingSound() {
        return "lotr:troll.say";
    }
    
    public String getHurtSound() {
        return "lotr:troll.say";
    }
    
    public String getDeathSound() {
        return "lotr:troll.say";
    }
    
    protected float getSoundVolume() {
        return 1.5f;
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        this.playSound("lotr:troll.step", 0.75f, this.getSoundPitch());
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.getTrollBurnTime() >= 0) {
            return null;
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) < 100.0f || !this.isFriendly(entityplayer)) {
            return "troll/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "troll/hired";
        }
        return "troll/friendly";
    }
    
    public boolean shouldRenderHeadHurt() {
        return ((EntityLivingBase)this).hurtTime > 0 || this.getSneezingTime() > 0;
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
}
