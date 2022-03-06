// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.entity.Entity;
import lotr.common.entity.item.LOTREntityBossTrophy;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemBossTrophy extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] trophyIcons;
    
    public LOTRItemBossTrophy() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public static TrophyType getTrophyType(final ItemStack itemstack) {
        if (itemstack.getItem() instanceof LOTRItemBossTrophy) {
            return getTrophyType(itemstack.getItemDamage());
        }
        return null;
    }
    
    public static TrophyType getTrophyType(final int i) {
        return TrophyType.forID(i);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.trophyIcons.length) {
            i = 0;
        }
        return this.trophyIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.trophyIcons = new IIcon[TrophyType.trophyTypes.size()];
        for (int i = 0; i < this.trophyIcons.length; ++i) {
            this.trophyIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + TrophyType.trophyTypes.get(i).trophyName);
        }
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + getTrophyType(itemstack).trophyName;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, int j, final int k, int side, final float f, final float f1, final float f2) {
        final TrophyType trophyType = getTrophyType(itemstack);
        final Block.SoundType blockSound = Blocks.stone.stepSound;
        if (world.getBlock(i, j, k).isReplaceable((IBlockAccess)world, i, j, k)) {
            side = 1;
        }
        else if (side == 1) {
            ++j;
        }
        if (side == 0) {
            return false;
        }
        if (side == 1) {
            if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            final Block block = world.getBlock(i, j - 1, k);
            final int meta = world.getBlockMetadata(i, j - 1, k);
            if (block.isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) && !world.isClient) {
                final LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
                trophy.setLocationAndAngles((double)(i + 0.5f), (double)j, (double)(k + 0.5f), 180.0f - ((Entity)entityplayer).rotationYaw % 360.0f, 0.0f);
                trophy.setTrophyHanging(false);
                if (world.checkNoEntityCollision(trophy.boundingBox) && world.getCollidingBoundingBoxes((Entity)trophy, trophy.boundingBox).size() == 0 && !world.isAnyLiquid(trophy.boundingBox)) {
                    trophy.setTrophyType(trophyType);
                    world.spawnEntityInWorld((Entity)trophy);
                    world.playSoundAtEntity((Entity)trophy, blockSound.func_150496_b(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getFrequency() * 0.8f);
                    --itemstack.stackSize;
                    return true;
                }
                trophy.setDead();
                return false;
            }
        }
        else {
            if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            if (!world.isClient) {
                final int direction = Direction.facingToDirection[side];
                final LOTREntityBossTrophy trophy2 = new LOTREntityBossTrophy(world);
                trophy2.setLocationAndAngles((double)(i + Direction.offsetX[direction] + 0.5f), (double)j, (double)(k + Direction.offsetZ[direction] + 0.5f), direction * 90.0f, 0.0f);
                trophy2.setTrophyHanging(true);
                trophy2.setTrophyFacing(direction);
                if (world.checkNoEntityCollision(trophy2.boundingBox) && !world.isAnyLiquid(trophy2.boundingBox) && trophy2.hangingOnValidSurface()) {
                    trophy2.setTrophyType(trophyType);
                    world.spawnEntityInWorld((Entity)trophy2);
                    world.playSoundAtEntity((Entity)trophy2, blockSound.func_150496_b(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getFrequency() * 0.8f);
                    --itemstack.stackSize;
                    return true;
                }
                trophy2.setDead();
                return false;
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final TrophyType type : TrophyType.trophyTypes) {
            list.add(new ItemStack(item, 1, type.trophyID));
        }
    }
    
    public enum TrophyType
    {
        MOUNTAIN_TROLL_CHIEFTAIN(0, "mtc"), 
        MALLORN_ENT(1, "mallornEnt");
        
        public static List<TrophyType> trophyTypes;
        private static Map<Integer, TrophyType> trophyForID;
        public final int trophyID;
        public final String trophyName;
        
        private TrophyType(final int i, final String s) {
            this.trophyID = i;
            this.trophyName = s;
        }
        
        public static TrophyType forID(final int ID) {
            return TrophyType.trophyForID.get(ID);
        }
        
        static {
            TrophyType.trophyTypes = new ArrayList<TrophyType>();
            TrophyType.trophyForID = new HashMap<Integer, TrophyType>();
            for (final TrophyType t : values()) {
                TrophyType.trophyTypes.add(t);
                TrophyType.trophyForID.put(t.trophyID, t);
            }
        }
    }
}
