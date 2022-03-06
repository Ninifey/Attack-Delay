// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraftforge.common.DimensionManager;
import lotr.common.LOTRDimension;
import java.util.Iterator;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.BlockContainer;

public abstract class LOTRBlockPortal extends BlockContainer
{
    private LOTRFaction[] portalFactions;
    private Class teleporterClass;
    
    public LOTRBlockPortal(final LOTRFaction[] factions, final Class c) {
        super(Material.Portal);
        this.portalFactions = factions;
        this.teleporterClass = c;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final float f = 0.0625f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        return side == 0 && super.shouldSideBeRendered(world, i, j, k, side);
    }
    
    public void addCollisionBoxesToList(final World world, final int i, final int j, final int k, final AxisAlignedBB aabb, final List list, final Entity entity) {
    }
    
    public abstract void setPlayerInPortal(final EntityPlayer p0);
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        if (!LOTRConfig.enablePortals) {
            return;
        }
        if (entity instanceof EntityPlayer) {
            for (final LOTRFaction faction : this.portalFactions) {
                if (LOTRLevelData.getData((EntityPlayer)entity).getAlignment(faction) >= 1.0f) {
                    if (entity.ridingEntity == null && entity.riddenByEntity == null) {
                        this.setPlayerInPortal((EntityPlayer)entity);
                    }
                    return;
                }
            }
        }
        else {
            for (final LOTRFaction faction : this.portalFactions) {
                if (!LOTRMod.getNPCFaction(entity).isBadRelation(faction)) {
                    if (entity.ridingEntity == null && entity.riddenByEntity == null && entity.timeUntilPortal == 0) {
                        this.transferEntity(entity, world);
                    }
                    return;
                }
            }
        }
        if (!world.isClient) {
            entity.setFire(4);
            entity.attackEntityFrom(DamageSource.inFire, 2.0f);
            world.playSoundAtEntity(entity, "random.fizz", 0.5f, 1.5f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.5f);
        }
    }
    
    public Teleporter getPortalTeleporter(final WorldServer world) {
        for (final Object obj : world.customTeleporters) {
            if (this.teleporterClass.isInstance(obj)) {
                return (Teleporter)obj;
            }
        }
        Teleporter teleporter = null;
        try {
            teleporter = this.teleporterClass.getConstructor(WorldServer.class).newInstance(world);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        world.customTeleporters.add(teleporter);
        return teleporter;
    }
    
    private void transferEntity(final Entity entity, final World world) {
        if (!world.isClient) {
            int dimension = 0;
            if (entity.dimension == 0) {
                dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
            }
            else if (entity.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                dimension = 0;
            }
            LOTRMod.transferEntityToDimension(entity, dimension, this.getPortalTeleporter(DimensionManager.getWorld(dimension)));
        }
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(i + 0.5, j + 0.5, k + 0.5, "portal.portal", 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        }
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        if (world.provider.dimensionId != 0 && world.provider.dimensionId != LOTRDimension.MIDDLE_EARTH.dimensionID) {
            world.setBlockToAir(i, j, k);
        }
    }
    
    public abstract boolean isValidPortalLocation(final World p0, final int p1, final int p2, final int p3, final boolean p4);
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return Item.getItemById(0);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.portal.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
