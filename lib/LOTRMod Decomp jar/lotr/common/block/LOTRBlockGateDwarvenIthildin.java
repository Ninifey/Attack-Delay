// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import net.minecraft.util.Facing;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockGateDwarvenIthildin extends LOTRBlockGateDwarven
{
    public boolean hasTileEntity(final int meta) {
        return true;
    }
    
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new LOTRTileEntityDwarvenDoor();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        for (final DoorSize s : DoorSize.values()) {
            for (int i = 0; i < s.width; ++i) {
                for (int j = 0; j < s.height; ++j) {
                    final IIcon icon = iconregister.registerIcon(this.getTextureName() + "_glow_" + s.doorName + "_" + i + "_" + j);
                    s.icons[i][j] = icon;
                }
            }
        }
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        super.onBlockPlacedBy(world, i, j, k, entity, itemstack);
        final LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)world.getTileEntity(i, j, k);
        Label_0570: {
            if (door != null) {
                final int meta = world.getBlockMetadata(i, j, k);
                final int dir = LOTRBlockGate.getGateDirection(meta);
                door.setDoorSizeAndPos(null, 0, 0);
                door.setDoorBasePos(i, j, k);
                if (dir != 0 && dir != 1) {
                    final int xzFactorX = (dir == 2) ? 1 : ((dir == 3) ? -1 : 0);
                    final int xzFactorZ = (dir == 4) ? -1 : ((dir == 5) ? 1 : 0);
                    for (final DoorSize doorSize : DoorSize.orderedSizes) {
                        final int width = doorSize.width;
                        final int height = doorSize.height;
                        final int rangeXZ = width - 1;
                        final int rangeY = height - 1;
                        for (int y = -rangeY; y <= 0; ++y) {
                            for (int xz = -rangeXZ; xz <= 0; ++xz) {
                                final int j2 = j + y;
                                final int i2 = i + xz * xzFactorX;
                                final int k2 = k + xz * xzFactorZ;
                                boolean connected = true;
                                boolean canReplaceSize = true;
                            Label_0392:
                                for (int y2 = 0; y2 <= rangeY; ++y2) {
                                    for (int xz2 = 0; xz2 <= rangeXZ; ++xz2) {
                                        final int j3 = j2 + y2;
                                        final int i3 = i2 + xz2 * xzFactorX;
                                        final int k3 = k2 + xz2 * xzFactorZ;
                                        if (i3 != i || j3 != j || k3 != k) {
                                            if (!this.areDwarfDoorsMatching((IBlockAccess)world, i, j, k, i3, j3, k3)) {
                                                connected = false;
                                                break Label_0392;
                                            }
                                            final TileEntity te = world.getTileEntity(i3, j3, k3);
                                            if (te instanceof LOTRTileEntityDwarvenDoor) {
                                                final LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor)te;
                                                final DoorSize otherSize = otherDoor.getDoorSize();
                                                if (DoorSize.compareLarger.compare(otherSize, doorSize) != 1) {
                                                    canReplaceSize = false;
                                                    break Label_0392;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (connected && canReplaceSize) {
                                    door.setDoorSizeAndPos(doorSize, -xz, -y);
                                    door.setDoorBasePos(i, j, k);
                                    for (int y2 = 0; y2 <= rangeY; ++y2) {
                                        for (int xz2 = 0; xz2 <= rangeXZ; ++xz2) {
                                            final int j3 = j2 + y2;
                                            final int i3 = i2 + xz2 * xzFactorX;
                                            final int k3 = k2 + xz2 * xzFactorZ;
                                            if (i3 != i || j3 != j || k3 != k) {
                                                final TileEntity te = world.getTileEntity(i3, j3, k3);
                                                if (te instanceof LOTRTileEntityDwarvenDoor) {
                                                    final LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor)te;
                                                    otherDoor.setDoorSizeAndPos(doorSize, xz2, y2);
                                                    otherDoor.setDoorBasePos(i, j, k);
                                                }
                                            }
                                        }
                                    }
                                    break Label_0570;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)world.getTileEntity(i, j, k);
        if (door != null) {
            final DoorSize doorSize = door.getDoorSize();
            if (doorSize != null) {
                final int dir = LOTRBlockGate.getGateDirection(meta);
                if (dir != 0 && dir != 1) {
                    final int xzFactorX = (dir == 2) ? 1 : ((dir == 3) ? -1 : 0);
                    final int xzFactorZ = (dir == 4) ? -1 : ((dir == 5) ? 1 : 0);
                    final int width = doorSize.width;
                    final int height = doorSize.height;
                    final int rangeXZ = width - 1;
                    for (int rangeY = height - 1, y = -rangeY; y <= rangeY; ++y) {
                        for (int xz = -rangeXZ; xz <= rangeXZ; ++xz) {
                            final int j2 = j + y;
                            final int i2 = i + xz * xzFactorX;
                            final int k2 = k + xz * xzFactorZ;
                            if (i2 != i || j2 != j || k2 != k) {
                                final TileEntity te = world.getTileEntity(i2, j2, k2);
                                if (te instanceof LOTRTileEntityDwarvenDoor) {
                                    final LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor)te;
                                    if (otherDoor.isSameDoor(door)) {
                                        otherDoor.setDoorSizeAndPos(null, 0, 0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        super.breakBlock(world, i, j, k, block, meta);
    }
    
    public IIcon getGlowIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final int meta = world.getBlockMetadata(i, j, k);
        final int dir = LOTRBlockGate.getGateDirection(meta);
        final boolean open = LOTRBlockGate.isGateOpen(world, i, j, k);
        if (!open && dir == Facing.oppositeSide[side]) {
            final TileEntity te = world.getTileEntity(i, j, k);
            if (te instanceof LOTRTileEntityDwarvenDoor) {
                final LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)te;
                return door.getDoorSize().icons[door.getDoorPosX()][door.getDoorPosY()];
            }
        }
        return null;
    }
    
    @Override
    protected boolean directionsMatch(final int dir1, final int dir2) {
        return dir1 == dir2;
    }
    
    private boolean areDwarfDoorsMatching(final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
        final Block block = world.getBlock(i, j, k);
        final int meta = world.getBlockMetadata(i, j, k);
        final Block otherBlock = world.getBlock(i1, j1, k1);
        final int otherMeta = world.getBlockMetadata(i1, j1, k1);
        final int dir = LOTRBlockGate.getGateDirection(meta);
        final int otherDir = LOTRBlockGate.getGateDirection(otherMeta);
        return block == this && block == otherBlock && ((LOTRBlockGate)block).directionsMatch(dir, otherDir) && ((LOTRBlockGate)otherBlock).directionsMatch(dir, otherDir);
    }
    
    public enum DoorSize
    {
        _1x1("1x1", 1, 1), 
        _1x2("1x2", 1, 2), 
        _2x2("2x2", 2, 2), 
        _2x3("2x3", 2, 3), 
        _3x4("3x4", 3, 4);
        
        public final String doorName;
        public final int width;
        public final int height;
        public final int area;
        public final IIcon[][] icons;
        public static Comparator<DoorSize> compareLarger;
        public static List<DoorSize> orderedSizes;
        
        private DoorSize(final String s, final int i, final int j) {
            this.doorName = s;
            this.width = i;
            this.height = j;
            this.area = this.width * this.height;
            this.icons = new IIcon[this.width][this.height];
        }
        
        public static DoorSize forName(final String s) {
            for (final DoorSize size : values()) {
                if (size.doorName.equals(s)) {
                    return size;
                }
            }
            return null;
        }
        
        static {
            DoorSize.compareLarger = new Comparator<DoorSize>() {
                @Override
                public int compare(final DoorSize s1, final DoorSize s2) {
                    if (s1 == s2) {
                        return 0;
                    }
                    if (s1.area != s2.area) {
                        return -Integer.valueOf(s1.area).compareTo(s2.area);
                    }
                    if (s1.height != s2.height) {
                        return -Integer.valueOf(s1.height).compareTo(s2.height);
                    }
                    return -Integer.valueOf(s1.width).compareTo(s2.width);
                }
            };
            DoorSize.orderedSizes = new ArrayList<DoorSize>();
            for (final DoorSize s : values()) {
                DoorSize.orderedSizes.add(s);
            }
            Collections.sort(DoorSize.orderedSizes, DoorSize.compareLarger);
        }
    }
}
