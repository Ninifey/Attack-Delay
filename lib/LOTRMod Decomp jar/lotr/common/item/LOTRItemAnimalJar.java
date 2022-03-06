// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.util.Vec3;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.block.LOTRBlockAnimalJar;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTBase;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class LOTRItemAnimalJar extends LOTRItemBlockMetadata
{
    public LOTRItemAnimalJar(final Block block) {
        super(block);
        this.setMaxStackSize(1);
    }
    
    public static NBTTagCompound getEntityData(final ItemStack itemstack) {
        if (itemstack.hasTagCompound()) {
            if (itemstack.getTagCompound().hasKey("LOTRButterfly")) {
                final NBTTagCompound nbt = itemstack.getTagCompound().getCompoundTag("LOTRButterfly");
                if (!nbt.hasNoTags()) {
                    nbt.setString("id", LOTREntities.getStringFromClass(LOTREntityButterfly.class));
                    setEntityData(itemstack, (NBTTagCompound)nbt.copy());
                }
                itemstack.getTagCompound().removeTag("LOTRButterfly");
            }
            if (itemstack.getTagCompound().hasKey("JarEntity")) {
                final NBTTagCompound nbt = itemstack.getTagCompound().getCompoundTag("JarEntity");
                if (!nbt.hasNoTags()) {
                    return nbt;
                }
            }
        }
        return null;
    }
    
    public static void setEntityData(final ItemStack itemstack, final NBTTagCompound nbt) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        if (nbt == null) {
            itemstack.getTagCompound().removeTag("JarEntity");
        }
        else {
            itemstack.getTagCompound().setTag("JarEntity", (NBTBase)nbt);
        }
    }
    
    public static Entity getItemJarEntity(final ItemStack itemstack, final World world) {
        final NBTTagCompound nbt = getEntityData(itemstack);
        if (nbt != null) {
            final Entity jarEntity = EntityList.createEntityFromNBT(nbt, world);
            return jarEntity;
        }
        return null;
    }
    
    public boolean placeBlockAt(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2, final int metadata) {
        if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity instanceof LOTRTileEntityAnimalJar) {
                final LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)tileentity;
                jar.setEntityData(getEntityData(itemstack));
            }
            return true;
        }
        return false;
    }
    
    public boolean itemInteractionForEntity(ItemStack itemstack, final EntityPlayer entityplayer, final EntityLivingBase entity) {
        itemstack = entityplayer.getCurrentEquippedItem();
        final World world = ((Entity)entityplayer).worldObj;
        final LOTRBlockAnimalJar jarBlock = (LOTRBlockAnimalJar)super.field_150939_a;
        if (jarBlock.canCapture((Entity)entity) && getEntityData(itemstack) == null) {
            if (!world.isClient) {
                final NBTTagCompound nbt = new NBTTagCompound();
                if (entity.writeToNBTOptional(nbt)) {
                    setEntityData(itemstack, nbt);
                    entity.playSound("random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
                    entity.setDead();
                    if (entity instanceof LOTREntityButterfly) {
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.catchButterfly);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!world.isClient) {
            final Entity jarEntity = getItemJarEntity(itemstack, world);
            if (jarEntity != null) {
                double x = ((Entity)entityplayer).posX;
                double y = ((Entity)entityplayer).boundingBox.minY + entityplayer.getEyeHeight();
                double z = ((Entity)entityplayer).posZ;
                final Vec3 look = entityplayer.getLookVec();
                final float length = 2.0f;
                x += look.xCoord * length;
                y += look.yCoord * length;
                z += look.zCoord * length;
                jarEntity.setLocationAndAngles(x, y, z, world.rand.nextFloat(), 0.0f);
                world.spawnEntityInWorld(jarEntity);
                jarEntity.playSound("random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
                setEntityData(itemstack, null);
            }
        }
        return itemstack;
    }
}
