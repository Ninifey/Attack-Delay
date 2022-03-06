// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.UUID;

public abstract class LOTREntityNPCRideable extends LOTREntityNPC implements LOTRNPCMount
{
    private UUID tamingPlayer;
    private int npcTemper;
    
    public LOTREntityNPCRideable(final World world) {
        super(world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    public boolean isNPCTamed() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setNPCTamed(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    public boolean isMountArmorValid(final ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            final LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.getItem();
            return armor.isValid(this);
        }
        return false;
    }
    
    public IInventory getMountInventory() {
        return null;
    }
    
    public void openGUI(final EntityPlayer entityplayer) {
        final IInventory inv = this.getMountInventory();
        if (inv != null && !((Entity)this).worldObj.isClient && (((Entity)this).riddenByEntity == null || ((Entity)this).riddenByEntity == entityplayer) && this.isNPCTamed()) {
            entityplayer.openGui((Object)LOTRMod.instance, 29, ((Entity)this).worldObj, this.getEntityId(), inv.getSizeInventory(), 0);
        }
    }
    
    public void tameNPC(final EntityPlayer entityplayer) {
        this.setNPCTamed(true);
        this.tamingPlayer = entityplayer.getUniqueID();
    }
    
    public EntityPlayer getTamingPlayer() {
        return ((Entity)this).worldObj.func_152378_a(this.tamingPlayer);
    }
    
    @Override
    public boolean canDespawn() {
        return super.canDespawn() && !this.isNPCTamed();
    }
    
    @Override
    public boolean canRenameNPC() {
        return this.isNPCTamed() || super.canRenameNPC();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        LOTRMountFunctions.update(this);
    }
    
    public void moveEntityWithHeading(final float strafe, final float forward) {
        LOTRMountFunctions.move(this, strafe, forward);
    }
    
    @Override
    public void super_moveEntityWithHeading(final float strafe, final float forward) {
        super.moveEntityWithHeading(strafe, forward);
    }
    
    public final double getMountedYOffset() {
        double d = this.getBaseMountedYOffset();
        if (((Entity)this).riddenByEntity != null) {
            d += ((Entity)this).riddenByEntity.yOffset - ((Entity)this).riddenByEntity.getYOffset();
        }
        return d;
    }
    
    protected double getBaseMountedYOffset() {
        return ((Entity)this).height * 0.5;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("NPCTamed", this.isNPCTamed());
        if (this.tamingPlayer != null) {
            nbt.setString("NPCTamer", this.tamingPlayer.toString());
        }
        nbt.setInteger("NPCTemper", this.npcTemper);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setNPCTamed(nbt.getBoolean("NPCTamed"));
        if (nbt.hasKey("NPCTamer")) {
            this.tamingPlayer = UUID.fromString(nbt.getString("NPCTamer"));
        }
        this.npcTemper = nbt.getInteger("NPCTemper");
    }
    
    public int getMaxNPCTemper() {
        return 100;
    }
    
    public int getNPCTemper() {
        return this.npcTemper;
    }
    
    public void setNPCTemper(final int i) {
        this.npcTemper = i;
    }
    
    public int increaseNPCTemper(final int i) {
        final int temper = MathHelper.clamp_int(this.getNPCTemper() + i, 0, this.getMaxNPCTemper());
        this.setNPCTemper(temper);
        return this.getNPCTemper();
    }
    
    public void angerNPC() {
        this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch() * 1.5f);
    }
}
