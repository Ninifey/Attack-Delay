// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockGate extends Block implements LOTRConnectedBlock
{
    protected static final int MAX_GATE_RANGE = 16;
    private boolean hasConnectedTextures;
    public boolean fullBlockGate;
    
    protected LOTRBlockGate(final Material material, final boolean ct) {
        super(material);
        this.fullBlockGate = false;
        this.hasConnectedTextures = ct;
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
    }
    
    public static LOTRBlockGate createWooden(final boolean ct) {
        final LOTRBlockGate block = new LOTRBlockGate(Material.wood, ct);
        block.setHardness(4.0f);
        block.setResistance(5.0f);
        block.setStepSound(Block.soundTypeWood);
        return block;
    }
    
    public static LOTRBlockGate createStone(final boolean ct) {
        final LOTRBlockGate block = new LOTRBlockGate(Material.rock, ct);
        block.setHardness(4.0f);
        block.setResistance(10.0f);
        block.setStepSound(Block.soundTypeStone);
        return block;
    }
    
    public static LOTRBlockGate createMetal(final boolean ct) {
        final LOTRBlockGate block = new LOTRBlockGate(Material.iron, ct);
        block.setHardness(4.0f);
        block.setResistance(10.0f);
        block.setStepSound(Block.soundTypeMetal);
        return block;
    }
    
    public LOTRBlockGate setFullBlock() {
        this.fullBlockGate = true;
        super.lightOpacity = 255;
        super.field_149783_u = true;
        return this;
    }
    
    public static boolean isGateOpen(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return isGateOpen(meta);
    }
    
    protected static boolean isGateOpen(final int meta) {
        return (meta & 0x8) != 0x0;
    }
    
    protected static void setGateOpen(final World world, final int i, final int j, final int k, final boolean flag) {
        int meta = world.getBlockMetadata(i, j, k);
        if (flag) {
            meta |= 0x8;
        }
        else {
            meta &= 0x7;
        }
        world.setBlockMetadata(i, j, k, meta, 3);
    }
    
    protected static int getGateDirection(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return getGateDirection(meta);
    }
    
    protected static int getGateDirection(final int meta) {
        return meta & 0x7;
    }
    
    protected boolean directionsMatch(final int dir1, final int dir2) {
        if (dir1 == 0 || dir1 == 1) {
            return dir1 == dir2;
        }
        if (dir1 == 2 || dir1 == 3) {
            return dir2 == 2 || dir2 == 3;
        }
        return (dir1 == 4 || dir1 == 5) && (dir2 == 4 || dir2 == 5);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        if (this.hasConnectedTextures) {
            LOTRConnectedTextures.registerConnectedIcons(iconregister, this, 0, true);
        }
        else {
            super.registerBlockIcons(iconregister);
            LOTRConnectedTextures.registerNonConnectedGateIcons(iconregister, this, 0);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final boolean open = isGateOpen(world, i, j, k);
        if (this.hasConnectedTextures) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, open);
        }
        if (open) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
        }
        return super.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (this.hasConnectedTextures) {
            return LOTRConnectedTextures.getConnectedIconItem(this, j);
        }
        return super.blockIcon;
    }
    
    public String getConnectedName(final int meta) {
        return super.textureName;
    }
    
    public boolean areBlocksConnected(final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
        final int meta = world.getBlockMetadata(i, j, k);
        final Block otherBlock = world.getBlock(i1, j1, k1);
        final int otherMeta = world.getBlockMetadata(i1, j1, k1);
        final int dir = getGateDirection(meta);
        final boolean open = isGateOpen(meta);
        final int otherDir = getGateDirection(otherMeta);
        final boolean otherOpen = isGateOpen(otherMeta);
        if ((dir == 0 || dir == 1) && j1 != j) {
            return false;
        }
        if ((dir == 2 || dir == 3) && k1 != k) {
            return false;
        }
        if ((dir == 4 || dir == 5) && i1 != i) {
            return false;
        }
        if (open && j1 == j - 1 && dir != 0 && dir != 1 && !(otherBlock instanceof LOTRBlockGate)) {
            return true;
        }
        final boolean connected = open ? (otherBlock instanceof LOTRBlockGate) : (otherBlock == this);
        return connected && this.directionsMatch(dir, otherDir) && ((LOTRBlockGate)otherBlock).directionsMatch(dir, otherDir) && open == otherOpen;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final int i2 = i - Facing.offsetsXForSide[side];
        final int j2 = j - Facing.offsetsYForSide[side];
        final int k2 = k - Facing.offsetsZForSide[side];
        final Block otherBlock = world.getBlock(i, j, k);
        if (otherBlock instanceof LOTRBlockGate) {
            final int metaThis = world.getBlockMetadata(i2, j2, k2);
            final int metaOther = world.getBlockMetadata(i, j, k);
            final int dirThis = getGateDirection(metaThis);
            final boolean openThis = isGateOpen(metaThis);
            final int dirOther = getGateDirection(metaOther);
            final boolean openOther = isGateOpen(metaOther);
            if (!this.fullBlockGate || openThis) {
                final boolean connect = !this.directionsMatch(dirThis, side);
                if (connect) {
                    return openThis != openOther || !this.directionsMatch(dirThis, dirOther) || !((LOTRBlockGate)otherBlock).directionsMatch(dirThis, dirOther);
                }
            }
        }
        return super.shouldSideBeRendered(world, i, j, k, side);
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final int dir = getGateDirection(world, i, j, k);
        this.setBlockBoundsForDirection(dir);
    }
    
    private void setBlockBoundsForDirection(final int dir) {
        if (this.fullBlockGate) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final float width = 0.25f;
            final float halfWidth = width / 2.0f;
            if (dir == 0) {
                this.setBlockBounds(0.0f, 1.0f - width, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            else if (dir == 1) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, width, 1.0f);
            }
            else if (dir == 2 || dir == 3) {
                this.setBlockBounds(0.0f, 0.0f, 0.5f - halfWidth, 1.0f, 1.0f, 0.5f + halfWidth);
            }
            else if (dir == 4 || dir == 5) {
                this.setBlockBounds(0.5f - halfWidth, 0.0f, 0.0f, 0.5f + halfWidth, 1.0f, 1.0f);
            }
        }
    }
    
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsForDirection(4);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        if (isGateOpen((IBlockAccess)world, i, j, k)) {
            return null;
        }
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    
    public boolean getBlocksMovement(final IBlockAccess world, final int i, final int j, final int k) {
        return isGateOpen(world, i, j, k);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getHeldItem();
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (Block.getBlockFromItem(item) instanceof LOTRBlockGate) {
                return false;
            }
            if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
                return false;
            }
        }
        if (!world.isClient) {
            this.activateGate(world, i, j, k);
        }
        return true;
    }
    
    private void activateGate(final World world, final int i, final int j, final int k) {
        final boolean wasOpen = isGateOpen((IBlockAccess)world, i, j, k);
        final boolean isOpen = !wasOpen;
        final List<ChunkCoordinates> gates = this.getConnectedGates(world, i, j, k);
        for (final ChunkCoordinates coords : gates) {
            setGateOpen(world, coords.posX, coords.posY, coords.posZ, isOpen);
        }
        String soundEffect = "";
        final boolean stone = this.getMaterial() == Material.rock;
        if (stone) {
            soundEffect = (isOpen ? "lotr:block.gate.stone_open" : "lotr:block.gate.stone_close");
        }
        else {
            soundEffect = (isOpen ? "lotr:block.gate.open" : "lotr:block.gate.close");
        }
        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, soundEffect, 1.0f, 0.8f + world.rand.nextFloat() * 0.4f);
    }
    
    private List<ChunkCoordinates> getConnectedGates(final World world, final int i, final int j, final int k) {
        final boolean open = isGateOpen((IBlockAccess)world, i, j, k);
        final int dir = getGateDirection((IBlockAccess)world, i, j, k);
        final Map<ChunkCoordinates, Integer> coordsList = new HashMap<ChunkCoordinates, Integer>();
        final int iter = 0;
        this.recursiveAddGates(world, i, j, k, dir, open, iter, coordsList);
        return new ArrayList<ChunkCoordinates>(coordsList.keySet());
    }
    
    private void recursiveAddGates(final World world, final int i, final int j, final int k, final int dir, final boolean open, int iter, final Map<ChunkCoordinates, Integer> coordsList) {
        final ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
        if (coordsList.containsKey(coords) && coordsList.get(coords) < iter) {
            return;
        }
        final Block otherBlock = world.getBlock(i, j, k);
        if (otherBlock instanceof LOTRBlockGate) {
            final boolean otherOpen = isGateOpen((IBlockAccess)world, i, j, k);
            final int otherDir = getGateDirection((IBlockAccess)world, i, j, k);
            if (otherOpen == open && this.directionsMatch(dir, otherDir) && ((LOTRBlockGate)otherBlock).directionsMatch(dir, otherDir)) {
                coordsList.put(coords, iter);
                if (++iter >= 16) {
                    return;
                }
                this.addAdjacentGates(world, i, j, k, dir, open, iter, coordsList);
            }
        }
    }
    
    private void addAdjacentGates(final World world, final int i, final int j, final int k, final int dir, final boolean open, final int iter, final Map<ChunkCoordinates, Integer> coordsList) {
        if (dir != 0 && dir != 1) {
            this.recursiveAddGates(world, i, j - 1, k, dir, open, iter, coordsList);
            this.recursiveAddGates(world, i, j + 1, k, dir, open, iter, coordsList);
        }
        if (dir != 2 && dir != 3) {
            this.recursiveAddGates(world, i, j, k - 1, dir, open, iter, coordsList);
            this.recursiveAddGates(world, i, j, k + 1, dir, open, iter, coordsList);
        }
        if (dir != 4 && dir != 5) {
            this.recursiveAddGates(world, i - 1, j, k, dir, open, iter, coordsList);
            this.recursiveAddGates(world, i + 1, j, k, dir, open, iter, coordsList);
        }
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!world.isClient && !(block instanceof LOTRBlockGate)) {
            final boolean open = isGateOpen((IBlockAccess)world, i, j, k);
            boolean powered = false;
            final List<ChunkCoordinates> gates = this.getConnectedGates(world, i, j, k);
            for (final ChunkCoordinates coords : gates) {
                final int i2 = coords.posX;
                final int j2 = coords.posY;
                final int k2 = coords.posZ;
                if (world.isBlockIndirectlyGettingPowered(i2, j2, k2)) {
                    powered = true;
                    break;
                }
            }
            if (powered || block.canProvidePower()) {
                if (powered && !open) {
                    this.activateGate(world, i, j, k);
                }
                else if (!powered && open) {
                    this.activateGate(world, i, j, k);
                }
            }
        }
    }
}
