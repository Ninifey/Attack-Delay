// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Items;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.ai.LOTREntityAIFarm;
import net.minecraft.world.World;
import net.minecraft.item.Item;

public class LOTREntityTauredainFarmhand extends LOTREntityTauredain implements LOTRFarmhand
{
    public Item seedsItem;
    
    public LOTREntityTauredainFarmhand(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFarm(this, 1.0, 1.2f));
        ((EntityLiving)this).targetTasks.taskEntries.clear();
        this.addTargetTasks(false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeTauredain));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
    }
    
    @Override
    public IPlantable getUnhiredSeeds() {
        if (this.seedsItem == null) {
            return (IPlantable)Items.potato;
        }
        return (IPlantable)this.seedsItem;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.seedsItem != null) {
            nbt.setInteger("SeedsID", Item.getIdFromItem(this.seedsItem));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("SeedsID")) {
            final Item item = Item.getItemById(nbt.getInteger("SeedsID"));
            if (item != null && item instanceof IPlantable) {
                this.seedsItem = item;
            }
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "tauredain/farmhand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}
