// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import java.util.UUID;

public class LOTREntityWoodElfScout extends LOTREntityWoodElf
{
    private static final UUID scoutArmorSpeedBoost_id;
    public static final AttributeModifier scoutArmorSpeedBoost;
    
    public LOTREntityWoodElfScout(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(2, super.rangedAttackAI);
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 25, 35, 24.0f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElvenScout));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElvenScout));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElvenScout));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetWoodElvenScout));
        }
        return data;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.isEntityAlive() && ((Entity)this).ridingEntity == null) {
            final ItemStack currentItem = this.getEquipmentInSlot(0);
            if (currentItem != null && currentItem.getItem() instanceof ItemBow) {
                final EntityLivingBase lastAttacker = this.getAITarget();
                if (lastAttacker != null && this.getDistanceSqToEntity((Entity)lastAttacker) < 16.0 && ((Entity)this).rand.nextInt(20) == 0) {
                    for (int l = 0; l < 32; ++l) {
                        final int i = MathHelper.floor_double(((Entity)this).posX) - ((Entity)this).rand.nextInt(16) + ((Entity)this).rand.nextInt(16);
                        final int j = MathHelper.floor_double(((Entity)this).posY) - ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(3);
                        final int k = MathHelper.floor_double(((Entity)this).posZ) - ((Entity)this).rand.nextInt(16) + ((Entity)this).rand.nextInt(16);
                        if (this.getDistance((double)i, (double)j, (double)k) > 6.0 && ((Entity)this).worldObj.getBlock(i, j - 1, k).isNormalCube() && !((Entity)this).worldObj.getBlock(i, j, k).isNormalCube() && !((Entity)this).worldObj.getBlock(i, j + 1, k).isNormalCube()) {
                            final double d = i + 0.5;
                            final double d2 = j;
                            final double d3 = k + 0.5;
                            final AxisAlignedBB aabb = ((Entity)this).boundingBox.copy().offset(d - ((Entity)this).posX, d2 - ((Entity)this).posY, d3 - ((Entity)this).posZ);
                            if (((Entity)this).worldObj.checkNoEntityCollision(aabb) && ((Entity)this).worldObj.getCollidingBoundingBoxes((Entity)this, aabb).isEmpty() && !((Entity)this).worldObj.isAnyLiquid(aabb)) {
                                this.doTeleportEffects();
                                this.setPosition(d, d2, d3);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void doTeleportEffects() {
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:elf.woodElf_teleport", this.getSoundVolume(), 0.5f + ((Entity)this).rand.nextFloat());
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            for (int i = 0; i < 16; ++i) {
                final double d = ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width;
                final double d2 = ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height;
                final double d3 = ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width;
                final double d4 = -0.05 + ((Entity)this).rand.nextFloat() * 0.1f;
                final double d5 = -0.05 + ((Entity)this).rand.nextFloat() * 0.1f;
                final double d6 = -0.05 + ((Entity)this).rand.nextFloat() * 0.1f;
                LOTRMod.proxy.spawnParticle("leafGreen_" + (20 + ((Entity)this).rand.nextInt(30)), d, d2, d3, d4, d5, d6);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "woodElf/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "woodElf/elf/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= getWoodlandTrustLevel()) {
            return "woodElf/warrior/friendly";
        }
        return "woodElf/elf/neutral";
    }
    
    static {
        scoutArmorSpeedBoost_id = UUID.fromString("cf0ceb91-0f13-4788-be0e-a6c67a830308");
        scoutArmorSpeedBoost = new AttributeModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost_id, "WE Scout armor speed boost", 0.3, 2).setSaved(false);
    }
}
