// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.ai.LOTREntityAIFarm;
import net.minecraft.world.World;
import net.minecraft.item.Item;

public class LOTREntityDorwinionVinehand extends LOTREntityDorwinionMan implements LOTRFarmhand
{
    private Item seedsItem;
    
    public LOTREntityDorwinionVinehand(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFarm(this, 1.0, 1.0f));
        ((EntityLiving)this).targetTasks.taskEntries.clear();
        this.addTargetTasks(false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        if (((Entity)this).rand.nextBoolean()) {
            this.seedsItem = LOTRMod.seedsGrapeRed;
        }
        else {
            this.seedsItem = LOTRMod.seedsGrapeWhite;
        }
        return data;
    }
    
    @Override
    public IPlantable getUnhiredSeeds() {
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
            return "dorwinion/vinehand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}
