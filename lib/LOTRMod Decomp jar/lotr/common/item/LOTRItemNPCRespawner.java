// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemNPCRespawner extends Item
{
    public LOTRItemNPCRespawner() {
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, final int side, final float f, final float f1, final float f2) {
        if (entityplayer.capabilities.isCreativeMode) {
            if (!world.isClient) {
                i += Facing.offsetsXForSide[side];
                j += Facing.offsetsYForSide[side];
                k += Facing.offsetsZForSide[side];
                this.placeSpawnerAt(world, i, j, k);
            }
            return true;
        }
        return false;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode && !world.isClient) {
            final Vec3 eyePos = Vec3.createVectorHelper(((Entity)entityplayer).posX, ((Entity)entityplayer).posY + entityplayer.getEyeHeight(), ((Entity)entityplayer).posZ);
            final Vec3 look = entityplayer.getLook(1.0f);
            final double range = ((EntityPlayerMP)entityplayer).theItemInWorldManager.getBlockReachDistance();
            final double d = eyePos.xCoord + look.xCoord * range;
            final double d2 = eyePos.yCoord + look.yCoord * range;
            final double d3 = eyePos.zCoord + look.zCoord * range;
            final int i = MathHelper.floor_double(d);
            final int j = MathHelper.floor_double(d2);
            final int k = MathHelper.floor_double(d3);
            this.placeSpawnerAt(world, i, j, k);
        }
        return itemstack;
    }
    
    private boolean placeSpawnerAt(final World world, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner spawner = new LOTREntityNPCRespawner(world);
        final double f = 0.1;
        final double f2 = 1.0 - f;
        final List entities = world.getEntitiesWithinAABB((Class)LOTREntityNPCRespawner.class, AxisAlignedBB.getBoundingBox(i + f, j + f, k + f, i + f2, j + f2, k + f2));
        if (entities.isEmpty()) {
            spawner.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
            final double c = 0.01;
            if (world.getCollidingBoundingBoxes((Entity)spawner, spawner.boundingBox.contract(c, c, c)).isEmpty()) {
                world.spawnEntityInWorld((Entity)spawner);
                return true;
            }
        }
        return false;
    }
}
