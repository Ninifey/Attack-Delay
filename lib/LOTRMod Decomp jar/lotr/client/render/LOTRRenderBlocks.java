// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import lotr.common.block.LOTRBlockCoralReef;
import lotr.common.block.LOTRBlockRhunFireJar;
import lotr.common.block.LOTRBlockBirdCage;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockCauldron;
import net.minecraft.util.MathHelper;
import lotr.common.block.LOTRBlockTallGrass;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.Vec3;
import lotr.common.block.LOTRBlockClover;
import net.minecraft.item.ItemStack;
import lotr.common.block.LOTRBlockFlower;
import lotr.common.block.LOTRBlockGrass;
import lotr.common.block.LOTRBlockFlowerPot;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import lotr.common.block.LOTRBlockBeacon;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import lotr.client.render.tileentity.LOTRRenderGuldurilGlow;
import lotr.common.block.LOTRBlockTreasurePile;
import lotr.common.tileentity.LOTRTileEntityChest;
import lotr.client.render.tileentity.LOTRRenderChest;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import lotr.client.render.tileentity.LOTRRenderUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.client.render.tileentity.LOTRRenderCommandTable;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import lotr.client.render.tileentity.LOTRRenderTrollTotem;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import lotr.client.render.tileentity.LOTRRenderBeacon;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFence;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LOTRRenderBlocks implements ISimpleBlockRenderingHandler
{
    private static Random blockRand;
    private boolean renderInvIn3D;
    
    public LOTRRenderBlocks(final boolean flag) {
        this.renderInvIn3D = flag;
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int i, final int j, final int k, final Block block, final int id, final RenderBlocks renderblocks) {
        final boolean fancyGraphics = Minecraft.getMinecraft().gameSettings.fancyGraphics;
        if (id == LOTRMod.proxy.getBeaconRenderID()) {
            this.renderBeacon(world, i, j, k, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getBarrelRenderID()) {
            this.renderBarrel(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getOrcBombRenderID()) {
            this.renderOrcBomb(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getDoubleTorchRenderID()) {
            this.renderDoubleTorch(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getMobSpawnerRenderID()) {
            return renderblocks.renderStandardBlock(block, i, j, k);
        }
        if (id == LOTRMod.proxy.getPlateRenderID()) {
            this.renderPlate(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getStalactiteRenderID()) {
            this.renderStalactite(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getFlowerPotRenderID()) {
            this.renderFlowerPot(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getCloverRenderID()) {
            renderClover(world, i, j, k, block, renderblocks, (world.getBlockMetadata(i, j, k) == 1) ? 4 : 3, true);
            return true;
        }
        if (id == LOTRMod.proxy.getEntJarRenderID()) {
            this.renderEntJar(world, i, j, k, block, renderblocks);
            return true;
        }
        if (id == LOTRMod.proxy.getFenceRenderID()) {
            return renderblocks.renderBlockFence((BlockFence)block, i, j, k);
        }
        if (id == LOTRMod.proxy.getGrassRenderID()) {
            renderGrass(world, i, j, k, block, renderblocks, true);
            return true;
        }
        if (id == LOTRMod.proxy.getFallenLeavesRenderID()) {
            if (fancyGraphics) {
                this.renderFallenLeaves(world, i, j, k, block, renderblocks, new int[] { 6, 10 }, new int[] { 2, 6 }, new int[] { 2, 6 }, 0.7f);
                return true;
            }
            return renderblocks.renderStandardBlock(block, i, j, k);
        }
        else {
            if (id == LOTRMod.proxy.getCommandTableRenderID()) {
                this.renderCommandTable(world, i, j, k, block, renderblocks);
                return true;
            }
            if (id == LOTRMod.proxy.getButterflyJarRenderID()) {
                this.renderButterflyJar(world, i, j, k, block, renderblocks);
                return true;
            }
            if (id == LOTRMod.proxy.getUnsmelteryRenderID()) {
                return true;
            }
            if (id == LOTRMod.proxy.getChestRenderID()) {
                return true;
            }
            if (id == LOTRMod.proxy.getReedsRenderID()) {
                this.renderReeds(world, i, j, k, block, renderblocks);
                return true;
            }
            if (id == LOTRMod.proxy.getWasteRenderID()) {
                this.renderBlockRandomRotated(world, i, j, k, block, renderblocks, true);
                return true;
            }
            if (id == LOTRMod.proxy.getBeamRenderID()) {
                this.renderBeam(world, i, j, k, block, renderblocks);
                return true;
            }
            if (id == LOTRMod.proxy.getVCauldronRenderID()) {
                this.renderVanillaCauldron(world, i, j, k, block, renderblocks);
                return true;
            }
            if (id == LOTRMod.proxy.getGrapevineRenderID()) {
                this.renderGrapevine(world, i, j, k, block, renderblocks);
                return true;
            }
            if (id == LOTRMod.proxy.getThatchFloorRenderID()) {
                if (fancyGraphics) {
                    this.renderFallenLeaves(world, i, j, k, block, renderblocks, new int[] { 10, 16 }, new int[] { 6, 12 }, new int[] { 1, 1 }, 1.0f);
                    return true;
                }
                return renderblocks.renderStandardBlock(block, i, j, k);
            }
            else {
                if (id == LOTRMod.proxy.getTreasureRenderID()) {
                    this.renderBlockRandomRotated(world, i, j, k, block, renderblocks, false);
                    return true;
                }
                if (id == LOTRMod.proxy.getFlowerRenderID()) {
                    this.renderFlowerBlock(world, i, j, k, block, renderblocks);
                    return true;
                }
                if (id == LOTRMod.proxy.getDoublePlantRenderID()) {
                    this.renderDoublePlant(world, i, j, k, (BlockDoublePlant)block, renderblocks);
                    return true;
                }
                if (id == LOTRMod.proxy.getBirdCageRenderID()) {
                    this.renderBirdCage(world, i, j, k, block, renderblocks);
                    return true;
                }
                if (id == LOTRMod.proxy.getRhunFireJarRenderID()) {
                    this.renderRhunFireJar(world, i, j, k, block, renderblocks);
                    return true;
                }
                if (id == LOTRMod.proxy.getCoralRenderID()) {
                    this.renderCoral(world, i, j, k, block, renderblocks);
                    return true;
                }
                if (id == LOTRMod.proxy.getDoorRenderID()) {
                    return this.renderDoor(world, i, j, k, block, renderblocks);
                }
                if (id == LOTRMod.proxy.getRopeRenderID()) {
                    this.renderRope(world, i, j, k, block, renderblocks);
                    return true;
                }
                if (id == LOTRMod.proxy.getOrcChainRenderID()) {
                    final IIcon icon = renderblocks.getIconSafe(block.getIcon(world, i, j, k, 0));
                    renderblocks.setOverrideBlockTexture(icon);
                    final boolean flag = renderblocks.renderCrossedSquares(block, i, j, k);
                    renderblocks.clearOverrideBlockTexture();
                    return flag;
                }
                return id == LOTRMod.proxy.getGuldurilRenderID() && renderblocks.renderStandardBlock(block, i, j, k);
            }
        }
    }
    
    public void renderInventoryBlock(final Block block, final int meta, final int id, final RenderBlocks renderblocks) {
        if (id == LOTRMod.proxy.getBeaconRenderID()) {
            ((LOTRRenderBeacon)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityBeacon.class)).renderInvBeacon();
        }
        if (id == LOTRMod.proxy.getBarrelRenderID()) {
            this.renderInvBarrel(block, renderblocks);
        }
        if (id == LOTRMod.proxy.getOrcBombRenderID()) {
            this.renderInvOrcBomb(block, meta, renderblocks);
        }
        if (id == LOTRMod.proxy.getMobSpawnerRenderID()) {
            renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            ((LOTRTileEntityMobSpawnerRenderer)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityMobSpawner.class)).renderInvMobSpawner(meta);
        }
        if (id == LOTRMod.proxy.getStalactiteRenderID()) {
            this.renderInvStalactite(block, meta, renderblocks);
        }
        if (id == LOTRMod.proxy.getCloverRenderID()) {
            renderInvClover(block, renderblocks, (meta == 1) ? 4 : 3);
        }
        if (id == LOTRMod.proxy.getEntJarRenderID()) {
            this.renderInvEntJar(block, renderblocks);
        }
        if (id == LOTRMod.proxy.getTrollTotemRenderID()) {
            ((LOTRRenderTrollTotem)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityTrollTotem.class)).renderInvTrollTotem(meta);
        }
        if (id == LOTRMod.proxy.getFenceRenderID()) {
            this.renderInvFence(block, meta, renderblocks);
        }
        if (id == LOTRMod.proxy.getCommandTableRenderID()) {
            this.renderInvCommandTable(block, renderblocks);
            ((LOTRRenderCommandTable)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityCommandTable.class)).renderInvTable();
        }
        if (id == LOTRMod.proxy.getButterflyJarRenderID()) {
            this.renderInvButterflyJar(block, renderblocks);
        }
        if (id == LOTRMod.proxy.getUnsmelteryRenderID()) {
            ((LOTRRenderUnsmeltery)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityUnsmeltery.class)).renderInvUnsmeltery();
        }
        if (id == LOTRMod.proxy.getChestRenderID()) {
            ((LOTRRenderChest)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityChest.class)).renderInvChest(block, meta);
        }
        if (id == LOTRMod.proxy.getWasteRenderID()) {
            renderStandardInvBlock(renderblocks, block, meta);
        }
        if (id == LOTRMod.proxy.getBeamRenderID()) {
            renderStandardInvBlock(renderblocks, block, meta);
        }
        if (id == LOTRMod.proxy.getTreasureRenderID()) {
            LOTRBlockTreasurePile.setTreasureBlockBounds(block, meta);
            renderblocks.setRenderBoundsFromBlock(block);
            renderblocks.lockBlockBounds = true;
            renderStandardInvBlock(renderblocks, block, meta);
            renderblocks.unlockBlockBounds();
        }
        if (id == LOTRMod.proxy.getBirdCageRenderID()) {
            this.renderInvBirdCage(block, renderblocks, meta);
        }
        if (id == LOTRMod.proxy.getRhunFireJarRenderID()) {
            this.renderInvRhunFireJar(block, renderblocks, meta);
        }
        if (id == LOTRMod.proxy.getCoralRenderID()) {
            renderStandardInvBlock(renderblocks, block, meta);
        }
        if (id == LOTRMod.proxy.getGuldurilRenderID()) {
            renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, meta);
            ((LOTRRenderGuldurilGlow)TileEntityRendererDispatcher.instance.getSpecialRendererByClass((Class)LOTRTileEntityGulduril.class)).renderInvGlow();
        }
    }
    
    public boolean shouldRender3DInInventory(final int modelID) {
        return this.renderInvIn3D;
    }
    
    public int getRenderId() {
        return 0;
    }
    
    private void renderBeacon(final IBlockAccess world, final int i, final int j, final int k, final RenderBlocks renderblocks) {
        if (LOTRBlockBeacon.isFullyLit(world, i, j, k)) {
            renderblocks.renderBlockFire(Blocks.fire, i, j, k);
        }
    }
    
    private void renderBarrel(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        renderblocks.setRenderBounds(0.15625, 0.0625, 0.15625, 0.84375, 0.75, 0.84375);
        renderblocks.renderStandardBlock(block, i, j, k);
        for (final float f : new float[] { 0.25f, 0.5f }) {
            renderblocks.setRenderBounds(0.125, (double)f, 0.125, 0.875, (double)(f + 0.0625f), 0.875);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        for (final float f : new float[] { 0.0f, 0.6875f }) {
            renderblocks.setRenderBounds(0.125, (double)f, 0.125, 0.25, (double)(f + 0.125f), 0.875);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.75, (double)f, 0.125, 0.875, (double)(f + 0.125f), 0.875);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.25, (double)f, 0.125, 0.75, (double)(f + 0.125f), 0.25);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.25, (double)f, 0.75, 0.75, (double)(f + 0.125f), 0.875);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        renderblocks.setOverrideBlockTexture(block.getBlockTextureFromSide(-1));
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 2) {
            renderblocks.setRenderBounds(0.4375, 0.25, 0.0, 0.5625, 0.375, 0.125);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.46875, 0.125, 0.0, 0.53125, 0.25, 0.0625);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        else if (meta == 3) {
            renderblocks.setRenderBounds(0.4375, 0.25, 0.875, 0.5625, 0.375, 1.0);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.46875, 0.125, 0.9375, 0.53125, 0.25, 1.0);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        else if (meta == 4) {
            renderblocks.setRenderBounds(0.0, 0.25, 0.4375, 0.125, 0.375, 0.5625);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.0, 0.125, 0.46875, 0.0625, 0.25, 0.53125);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        else if (meta == 5) {
            renderblocks.setRenderBounds(0.875, 0.25, 0.4375, 1.0, 0.375, 0.5625);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.9375, 0.125, 0.46875, 1.0, 0.25, 0.53125);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvBarrel(final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        renderStandardInvBlock(renderblocks, block, 0.15625, 0.0625, 0.15625, 0.84375, 0.75, 0.84375);
        for (final float f : new float[] { 0.25f, 0.5f }) {
            renderStandardInvBlock(renderblocks, block, 0.125, f, 0.125, 0.875, f + 0.0625f, 0.875);
        }
        for (final float f : new float[] { 0.0f, 0.6875f }) {
            renderStandardInvBlock(renderblocks, block, 0.125, f, 0.125, 0.25, f + 0.125f, 0.875);
            renderStandardInvBlock(renderblocks, block, 0.75, f, 0.125, 0.875, f + 0.125f, 0.875);
            renderStandardInvBlock(renderblocks, block, 0.25, f, 0.125, 0.75, f + 0.125f, 0.25);
            renderStandardInvBlock(renderblocks, block, 0.25, f, 0.75, 0.75, f + 0.125f, 0.875);
        }
        renderblocks.setOverrideBlockTexture(block.getBlockTextureFromSide(-1));
        renderStandardInvBlock(renderblocks, block, 0.875, 0.25, 0.4375, 1.0, 0.375, 0.5625);
        renderStandardInvBlock(renderblocks, block, 0.9375, 0.125, 0.46875, 1.0, 0.25, 0.53125);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
    }
    
    private void renderOrcBomb(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        renderblocks.setRenderBounds(0.125, 0.1875, 0.125, 0.875, 0.9375, 0.875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.375, 0.9375, 0.375, 0.625, 1.0, 0.625);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.9375, 0.375, 0.3125, 1.0, 0.4375);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.9375, 0.5625, 0.3125, 1.0, 0.625);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.6875, 0.9375, 0.375, 0.75, 1.0, 0.4375);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.6875, 0.9375, 0.5625, 0.75, 1.0, 0.625);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.375, 0.9375, 0.25, 0.4375, 1.0, 0.3125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.5625, 0.9375, 0.25, 0.625, 1.0, 0.3125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.375, 0.9375, 0.6875, 0.4375, 1.0, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.5625, 0.9375, 0.6875, 0.625, 1.0, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.125, 0.0, 0.25, 0.1875, 0.1875, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.8125, 0.0, 0.25, 0.875, 0.1875, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.0, 0.125, 0.75, 0.1875, 0.1875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.0, 0.8125, 0.75, 0.1875, 0.875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, world.getBlockMetadata(i, j, k)));
        renderblocks.setRenderBounds(0.0, 0.625, 0.3125, 0.0625, 0.6875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.0625, 0.625, 0.3125, 0.125, 0.6875, 0.375);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.0625, 0.625, 0.625, 0.125, 0.6875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.9375, 0.625, 0.3125, 1.0, 0.6875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.875, 0.625, 0.3125, 0.9375, 0.6875, 0.375);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.875, 0.625, 0.625, 0.9375, 0.6875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.3125, 0.625, 0.0, 0.6875, 0.6875, 0.0625);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.3125, 0.625, 0.0625, 0.375, 0.6875, 0.125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.625, 0.625, 0.0625, 0.6875, 0.6875, 0.125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.3125, 0.625, 0.9375, 0.6875, 0.6875, 1.0);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.3125, 0.625, 0.875, 0.375, 0.6875, 0.9375);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.625, 0.625, 0.875, 0.6875, 0.6875, 0.9375);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvOrcBomb(final Block block, final int meta, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        renderStandardInvBlock(renderblocks, block, 0.125, 0.1875, 0.125, 0.875, 0.9375, 0.875, meta);
        renderStandardInvBlock(renderblocks, block, 0.375, 0.9375, 0.375, 0.625, 1.0, 0.625, meta);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.9375, 0.375, 0.3125, 1.0, 0.4375, meta);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.9375, 0.5625, 0.3125, 1.0, 0.625, meta);
        renderStandardInvBlock(renderblocks, block, 0.6875, 0.9375, 0.375, 0.75, 1.0, 0.4375, meta);
        renderStandardInvBlock(renderblocks, block, 0.6875, 0.9375, 0.5625, 0.75, 1.0, 0.625, meta);
        renderStandardInvBlock(renderblocks, block, 0.375, 0.9375, 0.25, 0.4375, 1.0, 0.3125, meta);
        renderStandardInvBlock(renderblocks, block, 0.5625, 0.9375, 0.25, 0.625, 1.0, 0.3125, meta);
        renderStandardInvBlock(renderblocks, block, 0.375, 0.9375, 0.6875, 0.4375, 1.0, 0.75, meta);
        renderStandardInvBlock(renderblocks, block, 0.5625, 0.9375, 0.6875, 0.625, 1.0, 0.75, meta);
        renderStandardInvBlock(renderblocks, block, 0.125, 0.0, 0.25, 0.1875, 0.1875, 0.75, meta);
        renderStandardInvBlock(renderblocks, block, 0.8125, 0.0, 0.25, 0.875, 0.1875, 0.75, meta);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.0, 0.125, 0.75, 0.1875, 0.1875, meta);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.0, 0.8125, 0.75, 0.1875, 0.875, meta);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
        renderStandardInvBlock(renderblocks, block, 0.0, 0.625, 0.3125, 0.0625, 0.6875, 0.6875, meta);
        renderStandardInvBlock(renderblocks, block, 0.0625, 0.625, 0.3125, 0.125, 0.6875, 0.375, meta);
        renderStandardInvBlock(renderblocks, block, 0.0625, 0.625, 0.625, 0.125, 0.6875, 0.6875, meta);
        renderStandardInvBlock(renderblocks, block, 0.9375, 0.625, 0.3125, 1.0, 0.6875, 0.6875, meta);
        renderStandardInvBlock(renderblocks, block, 0.875, 0.625, 0.3125, 0.9375, 0.6875, 0.375, meta);
        renderStandardInvBlock(renderblocks, block, 0.875, 0.625, 0.625, 0.9375, 0.6875, 0.6875, meta);
        renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.0, 0.6875, 0.6875, 0.0625, meta);
        renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.0625, 0.375, 0.6875, 0.125, meta);
        renderStandardInvBlock(renderblocks, block, 0.625, 0.625, 0.0625, 0.6875, 0.6875, 0.125, meta);
        renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.9375, 0.6875, 0.6875, 1.0, meta);
        renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.875, 0.375, 0.6875, 0.9375, meta);
        renderStandardInvBlock(renderblocks, block, 0.625, 0.625, 0.875, 0.6875, 0.6875, 0.9375, meta);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
    }
    
    private void renderDoubleTorch(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        block.setBlockBoundsBasedOnState(world, i, j, k);
        renderblocks.setRenderBoundsFromBlock(block);
        renderblocks.renderStandardBlock(block, i, j, k);
    }
    
    private void renderPlate(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        renderblocks.setRenderBounds(0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.125, 0.0625, 0.125, 0.875, 0.125, 0.875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    public static void renderEntityPlate(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        renderStandardInvBlock(renderblocks, block, 0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
        renderStandardInvBlock(renderblocks, block, 0.125, 0.0625, 0.125, 0.875, 0.125, 0.875);
        renderblocks.renderAllFaces = false;
    }
    
    private void renderStalactite(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        final int metadata = world.getBlockMetadata(i, j, k);
        for (int l = 0; l < 16; ++l) {
            if (metadata == 0) {
                renderblocks.setRenderBounds((double)(0.5f - l / 16.0f * 0.25f), (double)(l / 16.0f), (double)(0.5f - l / 16.0f * 0.25f), (double)(0.5f + l / 16.0f * 0.25f), (double)((l + 1) / 16.0f), (double)(0.5f + l / 16.0f * 0.25f));
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else if (metadata == 1) {
                renderblocks.setRenderBounds((double)(0.25f + l / 16.0f * 0.25f), (double)(l / 16.0f), (double)(0.25f + l / 16.0f * 0.25f), (double)(0.75f - l / 16.0f * 0.25f), (double)((l + 1) / 16.0f), (double)(0.75f - l / 16.0f * 0.25f));
                renderblocks.renderStandardBlock(block, i, j, k);
            }
        }
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvStalactite(final Block block, final int metadata, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        for (int l = 0; l < 16; ++l) {
            if (metadata == 0) {
                renderStandardInvBlock(renderblocks, block, 0.5f - l / 16.0f * 0.25f, l / 16.0f, 0.5f - l / 16.0f * 0.25f, 0.5f + l / 16.0f * 0.25f, (l + 1) / 16.0f, 0.5f + l / 16.0f * 0.25f);
            }
            else if (metadata == 1) {
                renderStandardInvBlock(renderblocks, block, 0.25f + l / 16.0f * 0.25f, l / 16.0f, 0.25f + l / 16.0f * 0.25f, 0.75f - l / 16.0f * 0.25f, (l + 1) / 16.0f, 0.75f - l / 16.0f * 0.25f);
            }
        }
        renderblocks.renderAllFaces = false;
    }
    
    private void renderFlowerPot(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderStandardBlock(block, i, j, k);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final float f = 1.0f;
        final int l = block.colorMultiplier(world, i, j, k);
        final IIcon icon = renderblocks.getBlockIconFromSide(block, 0);
        float f2 = (l >> 16 & 0xFF) / 255.0f;
        float f3 = (l >> 8 & 0xFF) / 255.0f;
        float f4 = (l & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            final float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            final float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f6;
            f4 = f7;
        }
        tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
        final float f5 = 0.1865f;
        renderblocks.renderFaceXPos(block, (double)(i - 0.5f + f5), (double)j, (double)k, icon);
        renderblocks.renderFaceXNeg(block, (double)(i + 0.5f - f5), (double)j, (double)k, icon);
        renderblocks.renderFaceZPos(block, (double)i, (double)j, (double)(k - 0.5f + f5), icon);
        renderblocks.renderFaceZNeg(block, (double)i, (double)j, (double)(k + 0.5f - f5), icon);
        renderblocks.renderFaceYPos(block, (double)i, (double)(j - 0.5f + f5 + 0.1875f), (double)k, renderblocks.getBlockIcon(Blocks.dirt));
        final ItemStack plant = LOTRBlockFlowerPot.getPlant(world, i, j, k);
        if (plant != null) {
            final Block plantBlock = Block.getBlockFromItem(plant.getItem());
            final int plantMeta = plant.getItemDamage();
            final int plantColor = plantBlock.getRenderColor(plantMeta);
            if (plantColor != 16777215) {
                final float plantR = (plantColor >> 16 & 0xFF) / 255.0f;
                final float plantG = (plantColor >> 8 & 0xFF) / 255.0f;
                final float plantB = (plantColor & 0xFF) / 255.0f;
                tessellator.setColorOpaque_F(plantR, plantG, plantB);
            }
            tessellator.addTranslation(0.0f, 0.25f, 0.0f);
            if (plantBlock == LOTRMod.shireHeather) {
                renderblocks.drawCrossedSquares(plantBlock.getIcon(2, plantMeta), (double)i, (double)j, (double)k, 0.6f);
            }
            else if (plantBlock == LOTRMod.clover) {
                renderClover(world, i, j, k, plantBlock, renderblocks, (plantMeta == 1) ? 4 : 3, false);
            }
            else if (plantBlock instanceof LOTRBlockGrass) {
                renderGrass(world, i, j, k, plantBlock, renderblocks, false);
            }
            else if (plantBlock instanceof LOTRBlockFlower) {
                renderblocks.drawCrossedSquares(plantBlock.getIcon(2, plantMeta), (double)i, (double)j, (double)k, 0.75f);
            }
            else if (plantBlock.getRenderType() == 1) {
                renderblocks.drawCrossedSquares(plantBlock.getIcon(2, plantMeta), (double)i, (double)j, (double)k, 0.75f);
            }
            else {
                renderblocks.renderBlockByRenderType(plantBlock, i, j, k);
            }
            tessellator.addTranslation(0.0f, -0.25f, 0.0f);
        }
    }
    
    private static void renderClover(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks, final int petalCount, final boolean randomTranslation) {
        final double scale = 0.5;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final int l = block.colorMultiplier(world, i, j, k);
        final float f = 1.0f;
        float f2 = (l >> 16 & 0xFF) / 255.0f;
        float f3 = (l >> 8 & 0xFF) / 255.0f;
        float f4 = (l & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            final float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            final float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f6;
            f4 = f7;
        }
        tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
        renderblocks.setOverrideBlockTexture(LOTRBlockClover.stemIcon);
        double posX = i;
        final double posY = j;
        double posZ = k;
        if (randomTranslation) {
            long seed = (long)(i * 3129871) ^ k * 116129781L ^ (long)j;
            seed = seed * seed * 42317861L + seed * 11L;
            posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
        }
        renderblocks.drawCrossedSquares(block.getIcon(2, 0), posX, posY, posZ, (float)scale);
        renderblocks.clearOverrideBlockTexture();
        for (int petal = 0; petal < petalCount; ++petal) {
            final float rotation = petal / (float)petalCount * 3.1415927f * 2.0f;
            final IIcon icon = LOTRBlockClover.petalIcon;
            final double d = icon.getMinU();
            final double d2 = icon.getMinV();
            final double d3 = icon.getMaxU();
            final double d4 = icon.getMaxV();
            final double d5 = posX + 0.5;
            final double d6 = posY + 0.5 * scale + petal * 0.0025;
            final double d7 = posZ + 0.5;
            final Vec3[] vecs = { Vec3.createVectorHelper(0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, 0.5 * scale), Vec3.createVectorHelper(0.5 * scale, 0.0, 0.5 * scale) };
            for (int l2 = 0; l2 < vecs.length; ++l2) {
                vecs[l2].rotateAroundY(rotation);
                final Vec3 vec3 = vecs[l2];
                vec3.xCoord += d5;
                final Vec3 vec4 = vecs[l2];
                vec4.yCoord += d6;
                final Vec3 vec5 = vecs[l2];
                vec5.zCoord += d7;
            }
            tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d2);
            tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d4);
            tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d3, d4);
            tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d3, d2);
            tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d3, d2);
            tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d3, d4);
            tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d4);
            tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d2);
        }
    }
    
    private static void renderInvClover(final Block block, final RenderBlocks renderblocks, final int petalCount) {
        GL11.glDisable(2896);
        final double scale = 1.0;
        final Tessellator tessellator = Tessellator.instance;
        final int l = block.getRenderColor(0);
        final float f = 1.0f;
        float f2 = (l >> 16 & 0xFF) / 255.0f;
        float f3 = (l >> 8 & 0xFF) / 255.0f;
        float f4 = (l & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            final float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            final float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f6;
            f4 = f7;
        }
        tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
        renderblocks.setOverrideBlockTexture(LOTRBlockClover.stemIcon);
        tessellator.startDrawingQuads();
        renderblocks.drawCrossedSquares(block.getIcon(2, 0), -scale * 0.5, -scale * 0.5, -scale * 0.5, (float)scale);
        tessellator.draw();
        renderblocks.clearOverrideBlockTexture();
        for (int petal = 0; petal < petalCount; ++petal) {
            final float rotation = petal / (float)petalCount * 3.1415927f * 2.0f;
            final IIcon icon = LOTRBlockClover.petalIcon;
            final double d = icon.getMinU();
            final double d2 = icon.getMinV();
            final double d3 = icon.getMaxU();
            final double d4 = icon.getMaxV();
            final double d5 = 0.0;
            final double d6 = petal * 0.0025;
            final double d7 = 0.0;
            final Vec3[] vecs = { Vec3.createVectorHelper(0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, 0.5 * scale), Vec3.createVectorHelper(0.5 * scale, 0.0, 0.5 * scale) };
            for (int l2 = 0; l2 < vecs.length; ++l2) {
                vecs[l2].rotateAroundY(rotation);
                final Vec3 vec3 = vecs[l2];
                vec3.xCoord += d5;
                final Vec3 vec4 = vecs[l2];
                vec4.yCoord += d6;
                final Vec3 vec5 = vecs[l2];
                vec5.zCoord += d7;
            }
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d2);
            tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d4);
            tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d3, d4);
            tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d3, d2);
            tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d3, d2);
            tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d3, d4);
            tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d4);
            tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d2);
            tessellator.draw();
        }
        GL11.glEnable(2896);
    }
    
    public static void renderDwarvenDoorGlow(final LOTRBlockGateDwarvenIthildin block, final RenderBlocks renderblocks, final int i, final int j, final int k) {
        final Tessellator tessellator = Tessellator.instance;
        block.setBlockBoundsBasedOnState(renderblocks.blockAccess, i, j, k);
        renderblocks.setRenderBoundsFromBlock((Block)block);
        final double d = 0.01;
        IIcon icon = block.getGlowIcon(renderblocks.blockAccess, i, j, k, 0);
        if (icon != null) {
            tessellator.startDrawingQuads();
            renderblocks.renderFaceYNeg((Block)block, 0.0, -d, 0.0, icon);
            tessellator.draw();
            renderblocks.flipTexture = false;
        }
        icon = block.getGlowIcon(renderblocks.blockAccess, i, j, k, 1);
        if (icon != null) {
            tessellator.startDrawingQuads();
            renderblocks.renderFaceYPos((Block)block, 0.0, d, 0.0, icon);
            tessellator.draw();
            renderblocks.flipTexture = false;
        }
        icon = block.getGlowIcon(renderblocks.blockAccess, i, j, k, 2);
        if (icon != null) {
            tessellator.startDrawingQuads();
            renderblocks.renderFaceZNeg((Block)block, 0.0, 0.0, -d, icon);
            tessellator.draw();
            renderblocks.flipTexture = false;
        }
        icon = block.getGlowIcon(renderblocks.blockAccess, i, j, k, 3);
        if (icon != null) {
            tessellator.startDrawingQuads();
            renderblocks.renderFaceZPos((Block)block, 0.0, 0.0, d, icon);
            tessellator.draw();
            renderblocks.flipTexture = false;
        }
        icon = block.getGlowIcon(renderblocks.blockAccess, i, j, k, 4);
        if (icon != null) {
            tessellator.startDrawingQuads();
            renderblocks.renderFaceXNeg((Block)block, -d, 0.0, 0.0, icon);
            tessellator.draw();
            renderblocks.flipTexture = false;
        }
        icon = block.getGlowIcon(renderblocks.blockAccess, i, j, k, 5);
        if (icon != null) {
            tessellator.startDrawingQuads();
            renderblocks.renderFaceXPos((Block)block, d, 0.0, 0.0, icon);
            tessellator.draw();
            renderblocks.flipTexture = false;
        }
    }
    
    private void renderEntJar(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        renderblocks.setRenderBounds(0.25, 0.0, 0.25, 0.75, 0.0625, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.875, 0.3125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.0625, 0.6875, 0.75, 0.875, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.0625, 0.3125, 0.31255000829696655, 0.875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.6875, 0.0625, 0.3125, 0.75, 0.875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvEntJar(final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        renderStandardInvBlock(renderblocks, block, 0.25, 0.0, 0.25, 0.75, 0.0625, 0.75);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.0625, 0.25, 0.75, 0.875, 0.3125);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.0625, 0.6875, 0.75, 0.875, 0.75);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.0625, 0.3125, 0.31255000829696655, 0.875, 0.6875);
        renderStandardInvBlock(renderblocks, block, 0.6875, 0.0625, 0.3125, 0.75, 0.875, 0.6875);
        renderblocks.renderAllFaces = false;
    }
    
    private void renderInvFence(final Block block, final int meta, final RenderBlocks renderblocks) {
        for (int k = 0; k < 4; ++k) {
            final float f = 0.125f;
            final float f2 = 0.0625f;
            if (k == 0) {
                renderblocks.setRenderBounds((double)(0.5f - f), 0.0, 0.0, (double)(0.5f + f), 1.0, (double)(f * 2.0f));
            }
            if (k == 1) {
                renderblocks.setRenderBounds((double)(0.5f - f), 0.0, (double)(1.0f - f * 2.0f), (double)(0.5f + f), 1.0, 1.0);
            }
            if (k == 2) {
                renderblocks.setRenderBounds((double)(0.5f - f2), (double)(1.0f - f2 * 3.0f), (double)(-f2 * 2.0f), (double)(0.5f + f2), (double)(1.0f - f2), (double)(1.0f + f2 * 2.0f));
            }
            if (k == 3) {
                renderblocks.setRenderBounds((double)(0.5f - f2), (double)(0.5f - f2 * 3.0f), (double)(-f2 * 2.0f), (double)(0.5f + f2), (double)(0.5f - f2), (double)(1.0f + f2 * 2.0f));
            }
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, -1.0f, 0.0f);
            renderblocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 0, meta));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 1.0f, 0.0f);
            renderblocks.renderFaceYPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 1, meta));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 0.0f, -1.0f);
            renderblocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 2, meta));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 0.0f, 1.0f);
            renderblocks.renderFaceZPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 3, meta));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0f, 0.0f, 0.0f);
            renderblocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 4, meta));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0f, 0.0f, 0.0f);
            renderblocks.renderFaceXPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 5, meta));
            tessellator.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
        renderblocks.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }
    
    private static void renderGrass(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks, final boolean randomTranslation) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final int meta = world.getBlockMetadata(i, j, k);
        final int l = block.colorMultiplier(world, i, j, k);
        final float f = 1.0f;
        float f2 = (l >> 16 & 0xFF) / 255.0f;
        float f3 = (l >> 8 & 0xFF) / 255.0f;
        float f4 = (l & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            final float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            final float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f6;
            f4 = f7;
        }
        tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
        double posX = i;
        double posY = j;
        double posZ = k;
        if (randomTranslation) {
            long seed = (long)(i * 3129871) ^ k * 116129781L ^ (long)j;
            seed = seed * seed * 42317861L + seed * 11L;
            posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            posY += ((seed >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
        }
        renderblocks.drawCrossedSquares(block.getIcon(2, meta), posX, posY, posZ, 1.0f);
        renderblocks.clearOverrideBlockTexture();
        if (block == LOTRMod.tallGrass && meta >= 0 && meta < LOTRBlockTallGrass.grassOverlay.length && LOTRBlockTallGrass.grassOverlay[meta]) {
            tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            renderblocks.drawCrossedSquares(block.getIcon(-1, meta), posX, posY, posZ, 1.0f);
            renderblocks.clearOverrideBlockTexture();
        }
    }
    
    private void renderFallenLeaves(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks, final int[] minMaxLeaves, final int[] minMaxXSize, final int[] minMaxZSize, final float shade) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final int meta = world.getBlockMetadata(i, j, k);
        final int color = block.colorMultiplier(world, i, j, k);
        float r = (color >> 16 & 0xFF) / 255.0f;
        float g = (color >> 8 & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        r *= shade;
        g *= shade;
        b *= shade;
        if (EntityRenderer.anaglyphEnable) {
            r = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
            g = (r * 30.0f + g * 70.0f) / 100.0f;
            b = (r * 30.0f + b * 70.0f) / 100.0f;
        }
        tessellator.setColorOpaque_F(r, g, b);
        long seed = i * 237690503L ^ k * 2689286L ^ (long)j;
        seed = seed * seed * 1732965593L + seed * 673L;
        LOTRRenderBlocks.blockRand.setSeed(seed);
        final IIcon icon = block.getIcon(world, i, j, k, 0);
        for (int leaves = MathHelper.getRandomIntegerInRange(LOTRRenderBlocks.blockRand, minMaxLeaves[0], minMaxLeaves[1]), l = 0; l < leaves; ++l) {
            final double posX = i + LOTRRenderBlocks.blockRand.nextFloat();
            final double posZ = k + LOTRRenderBlocks.blockRand.nextFloat();
            final double posY = j + 0.01f + l / (float)leaves * 0.1f;
            final float rotation = LOTRRenderBlocks.blockRand.nextFloat() * 3.1415927f * 2.0f;
            final int xSize = MathHelper.getRandomIntegerInRange(LOTRRenderBlocks.blockRand, minMaxXSize[0], minMaxXSize[1]);
            final int zSize = MathHelper.getRandomIntegerInRange(LOTRRenderBlocks.blockRand, minMaxZSize[0], minMaxZSize[1]);
            final double xSizeD = xSize / 16.0;
            final double zSizeD = zSize / 16.0;
            final int intMinU = MathHelper.getRandomIntegerInRange(LOTRRenderBlocks.blockRand, 0, 16 - xSize);
            final int intMinV = MathHelper.getRandomIntegerInRange(LOTRRenderBlocks.blockRand, 0, 16 - zSize);
            final double minU = icon.getInterpolatedU((double)intMinU);
            final double minV = icon.getInterpolatedV((double)intMinV);
            final double maxU = icon.getInterpolatedU((double)(intMinU + xSize));
            final double maxV = icon.getInterpolatedV((double)(intMinV + zSize));
            final double x2 = xSizeD / 2.0;
            final double z2 = zSizeD / 2.0;
            final Vec3[] vecs = { Vec3.createVectorHelper(-x2, 0.0, -z2), Vec3.createVectorHelper(-x2, 0.0, z2), Vec3.createVectorHelper(x2, 0.0, z2), Vec3.createVectorHelper(x2, 0.0, -z2) };
            for (int v = 0; v < vecs.length; ++v) {
                final Vec3 vec = vecs[v];
                vec.rotateAroundY(rotation);
                final Vec3 vec2 = vec;
                vec2.xCoord += posX;
                final Vec3 vec3 = vec;
                vec3.yCoord += posY;
                final Vec3 vec4 = vec;
                vec4.zCoord += posZ;
            }
            tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, minU, minV);
            tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, minU, maxV);
            tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, maxU, maxV);
            tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, maxU, minV);
        }
    }
    
    private void renderCommandTable(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        renderblocks.setRenderBoundsFromBlock(block);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setOverrideBlockTexture(Blocks.planks.getIcon(0, 0));
        renderblocks.setRenderBounds(-0.5, 1.0, -0.5, 0.5, 1.0625, 0.5);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(-0.5, 1.0, 0.5, 0.5, 1.0625, 1.5);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.5, 1.0, -0.5, 1.5, 1.0625, 0.5);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.5, 1.0, 0.5, 1.5, 1.0625, 1.5);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvCommandTable(final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        renderblocks.setOverrideBlockTexture(Blocks.planks.getIcon(0, 0));
        renderStandardInvBlock(renderblocks, block, -0.5, 1.0, -0.5, 1.5, 1.0625, 1.5);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
    }
    
    private void renderButterflyJar(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        renderblocks.setRenderBounds(0.1875, 0.0, 0.1875, 0.8125, 0.5625, 0.8125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.25, 0.5625, 0.25, 0.75, 0.6875, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, 0));
        renderblocks.setRenderBounds(0.1875, 0.6875, 0.1875, 0.8125, 0.75, 0.8125);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvButterflyJar(final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderAllFaces = true;
        renderStandardInvBlock(renderblocks, block, 0.1875, 0.0, 0.1875, 0.8125, 0.5625, 0.8125);
        renderStandardInvBlock(renderblocks, block, 0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
        renderStandardInvBlock(renderblocks, block, 0.25, 0.5625, 0.25, 0.75, 0.6875, 0.75);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, 0));
        renderStandardInvBlock(renderblocks, block, 0.1875, 0.6875, 0.1875, 0.8125, 0.75, 0.8125);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
    }
    
    private void renderReeds(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        block.setBlockBoundsBasedOnState(world, i, j, k);
        renderblocks.setRenderBoundsFromBlock(block);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final int c = block.colorMultiplier(world, i, j, k);
        float r = (c >> 16 & 0xFF) / 255.0f;
        float g = (c >> 8 & 0xFF) / 255.0f;
        float b = (c & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float r2 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
            final float g2 = (r * 30.0f + g * 70.0f) / 100.0f;
            final float b2 = (r * 30.0f + b * 70.0f) / 100.0f;
            r = r2;
            g = g2;
            b = b2;
        }
        tessellator.setColorOpaque_F(r, g, b);
        final double d = i;
        double d2 = j;
        final double d3 = k;
        if (world.getBlock(i, j - 1, k) == block) {
            final IIcon iicon = renderblocks.getBlockIcon(block, world, i, j, k, 0);
            renderblocks.drawCrossedSquares(iicon, d, d2, d3, 1.0f);
        }
        else {
            IIcon iicon = renderblocks.getBlockIcon(block, world, i, j, k, 0);
            renderblocks.drawCrossedSquares(iicon, d, d2, d3, 1.0f);
            for (int j2 = j - 1; j2 > 0; --j2) {
                --d2;
                tessellator.setBrightness(block.getBlockBrightness(world, i, j2, k));
                final boolean lower = world.getBlock(i, j2 - 1, k).isOpaqueCube();
                if (lower) {
                    iicon = renderblocks.getBlockIcon(block, world, i, j, k, -2);
                    renderblocks.drawCrossedSquares(iicon, d, d2, d3, 1.0f);
                    break;
                }
                iicon = renderblocks.getBlockIcon(block, world, i, j, k, -1);
                renderblocks.drawCrossedSquares(iicon, d, d2, d3, 1.0f);
            }
        }
    }
    
    private void renderBlockRandomRotated(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks, final boolean rotateSides) {
        final int[] sides = new int[6];
        for (int l = 0; l < sides.length; ++l) {
            int hash = i * 234890405 ^ k * 37383934 ^ j;
            hash += l * 285502;
            LOTRRenderBlocks.blockRand.setSeed(hash);
            LOTRRenderBlocks.blockRand.setSeed(LOTRRenderBlocks.blockRand.nextLong());
            sides[l] = LOTRRenderBlocks.blockRand.nextInt(4);
        }
        if (rotateSides) {
            renderblocks.uvRotateEast = sides[0];
            renderblocks.uvRotateWest = sides[1];
            renderblocks.uvRotateSouth = sides[2];
            renderblocks.uvRotateNorth = sides[3];
        }
        renderblocks.uvRotateTop = sides[4];
        renderblocks.uvRotateBottom = sides[5];
        renderblocks.renderStandardBlock(block, i, j, k);
        if (rotateSides) {
            renderblocks.uvRotateEast = 0;
            renderblocks.uvRotateWest = 0;
            renderblocks.uvRotateSouth = 0;
            renderblocks.uvRotateNorth = 0;
        }
        renderblocks.uvRotateTop = 0;
        renderblocks.uvRotateBottom = 0;
    }
    
    private void renderBeam(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int meta = world.getBlockMetadata(i, j, k);
        final int dir = meta & 0xC;
        if (dir == 0) {
            renderblocks.uvRotateEast = 3;
            renderblocks.uvRotateNorth = 3;
        }
        else if (dir == 4) {
            renderblocks.uvRotateEast = 1;
            renderblocks.uvRotateWest = 2;
            renderblocks.uvRotateTop = 2;
            renderblocks.uvRotateBottom = 1;
        }
        else if (dir == 8) {
            renderblocks.uvRotateSouth = 1;
            renderblocks.uvRotateNorth = 2;
        }
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.uvRotateSouth = 0;
        renderblocks.uvRotateEast = 0;
        renderblocks.uvRotateWest = 0;
        renderblocks.uvRotateNorth = 0;
        renderblocks.uvRotateTop = 0;
        renderblocks.uvRotateBottom = 0;
    }
    
    private void renderVanillaCauldron(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderStandardBlock(block, i, j, k);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        int color = block.colorMultiplier(world, i, j, k);
        float r = (color >> 16 & 0xFF) / 255.0f;
        float g = (color >> 8 & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float r2 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
            final float g2 = (r * 30.0f + g * 70.0f) / 100.0f;
            final float b2 = (r * 30.0f + b * 70.0f) / 100.0f;
            r = r2;
            g = g2;
            b = b2;
        }
        tessellator.setColorOpaque_F(r, g, b);
        final IIcon iconSide = block.getBlockTextureFromSide(2);
        final float w = 0.125f;
        renderblocks.renderFaceXPos(block, (double)(i - 1.0f + w), (double)j, (double)k, iconSide);
        renderblocks.renderFaceXNeg(block, (double)(i + 1.0f - w), (double)j, (double)k, iconSide);
        renderblocks.renderFaceZPos(block, (double)i, (double)j, (double)(k - 1.0f + w), iconSide);
        renderblocks.renderFaceZNeg(block, (double)i, (double)j, (double)(k + 1.0f - w), iconSide);
        final IIcon iconInner = BlockCauldron.func_150026_e("inner");
        renderblocks.renderFaceYPos(block, (double)i, (double)(j - 1.0f + 0.25f), (double)k, iconInner);
        renderblocks.renderFaceYNeg(block, (double)i, (double)(j + 1.0f - 0.75f), (double)k, iconInner);
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta > 0) {
            color = Blocks.water.colorMultiplier(world, i, j, k);
            r = (color >> 16 & 0xFF) / 255.0f;
            g = (color >> 8 & 0xFF) / 255.0f;
            b = (color & 0xFF) / 255.0f;
            tessellator.setColorOpaque_F(r, g, b);
            final IIcon iconWater = BlockLiquid.func_149803_e("water_still");
            renderblocks.renderFaceYPos(block, (double)i, (double)(j - 1.0f + BlockCauldron.func_150025_c(meta)), (double)k, iconWater);
        }
    }
    
    private void renderGrapevine(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        block.setBlockBoundsForItemRender();
        renderblocks.setRenderBoundsFromBlock(block);
        renderblocks.renderStandardBlock(block, i, j, k);
        final int meta = world.getBlockMetadata(i, j, k);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
        block.setBlockBoundsBasedOnState(world, i, j, k);
        renderblocks.setRenderBoundsFromBlock(block);
        final double d = 0.001;
        renderblocks.renderMinY += d;
        renderblocks.renderMaxY -= d;
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.clearOverrideBlockTexture();
    }
    
    private void renderFlowerBlock(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final int color = block.colorMultiplier(world, i, j, k);
        float r = (color >> 16 & 0xFF) / 255.0f;
        float g = (color >> 8 & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f3 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
            final float f4 = (r * 30.0f + g * 70.0f) / 100.0f;
            final float f5 = (r * 30.0f + b * 70.0f) / 100.0f;
            r = f3;
            g = f4;
            b = f5;
        }
        tessellator.setColorOpaque_F(r, g, b);
        double d = i;
        final double d2 = j;
        double d3 = k;
        long seed = (long)(i * 3129871) ^ k * 116129781L ^ (long)j;
        seed = seed * seed * 42317861L + seed * 11L;
        d += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
        d3 += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;
        final IIcon iicon = renderblocks.getBlockIconFromSideAndMetadata(block, 0, world.getBlockMetadata(i, j, k));
        renderblocks.drawCrossedSquares(iicon, d, d2, d3, 1.0f);
    }
    
    private void renderDoublePlant(final IBlockAccess world, final int i, final int j, final int k, final BlockDoublePlant block, final RenderBlocks renderblocks) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j, k));
        final int color = block.colorMultiplier(world, i, j, k);
        float r = (color >> 16 & 0xFF) / 255.0f;
        float g = (color >> 8 & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f3 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
            final float f4 = (r * 30.0f + g * 70.0f) / 100.0f;
            final float f5 = (r * 30.0f + b * 70.0f) / 100.0f;
            r = f3;
            g = f4;
            b = f5;
        }
        tessellator.setColorOpaque_F(r, g, b);
        double d = i;
        final double d2 = j;
        double d3 = k;
        long seed = (long)(i * 3129871) ^ k * 116129781L;
        seed = seed * seed * 42317861L + seed * 11L;
        d += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
        d3 += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;
        final int meta = world.getBlockMetadata(i, j, k);
        final boolean isTop = BlockDoublePlant.func_149887_c(meta);
        int plantType;
        if (isTop) {
            if (world.getBlock(i, j - 1, k) != block) {
                return;
            }
            plantType = BlockDoublePlant.func_149890_d(world.getBlockMetadata(i, j - 1, k));
        }
        else {
            plantType = BlockDoublePlant.func_149890_d(meta);
        }
        final IIcon icon = block.func_149888_a(isTop, plantType);
        renderblocks.drawCrossedSquares(icon, d, d2, d3, 1.0f);
    }
    
    private void renderBirdCage(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        final int meta = world.getBlockMetadata(i, j, k);
        final float d = 0.001f;
        if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i - 1, j, k)) {
            renderblocks.setRenderBounds(0.0, 0.0625, 0.0, (double)(0.0f + d), 1.0, 1.0);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i + 1, j, k)) {
            renderblocks.setRenderBounds((double)(1.0f - d), 0.0625, 0.0, 1.0, 1.0, 1.0);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i, j, k - 1)) {
            renderblocks.setRenderBounds(0.0, 0.0625, 0.0, 1.0, 1.0, (double)(0.0f + d));
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i, j, k + 1)) {
            renderblocks.setRenderBounds(0.0, 0.0625, (double)(1.0f - d), 1.0, 1.0, 1.0);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        renderblocks.setRenderBounds(0.0, (double)(1.0f - d), 0.0, 1.0, 1.0, 1.0);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
        renderblocks.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
        renderblocks.renderStandardBlock(block, i, j, k);
        final Block above = world.getBlock(i, j + 1, k);
        above.setBlockBoundsBasedOnState(world, i, j + 1, k);
        if (!above.getMaterial().isSolid() || above.getBlockBoundsMinY() > 0.0 || (!above.getMaterial().isOpaque() && above.getRenderType() == 0)) {
            renderblocks.setRenderBounds(0.375, 1.0, 0.375, 0.625, 1.0625, 0.625);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.setRenderBounds(0.46875, 1.0625, 0.46875, 0.53125, 1.1875, 0.53125);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvBirdCage(final Block block, final RenderBlocks renderblocks, final int meta) {
        renderblocks.renderAllFaces = true;
        final float d = 0.001f;
        renderStandardInvBlock(renderblocks, block, 0.0, 0.0625, 0.0, 0.0f + d, 1.0, 1.0, meta);
        renderStandardInvBlock(renderblocks, block, 1.0f - d, 0.0625, 0.0, 1.0, 1.0, 1.0, meta);
        renderStandardInvBlock(renderblocks, block, 0.0, 0.0625, 0.0, 1.0, 1.0, 0.0f + d, meta);
        renderStandardInvBlock(renderblocks, block, 0.0, 0.0625, 1.0f - d, 1.0, 1.0, 1.0, meta);
        renderStandardInvBlock(renderblocks, block, 0.0, 1.0f - d, 0.0, 1.0, 1.0, 1.0, meta);
        renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
        renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.0, 1.0, 0.0625, 1.0, meta);
        renderStandardInvBlock(renderblocks, block, 0.375, 1.0, 0.375, 0.625, 1.0625, 0.625, meta);
        renderStandardInvBlock(renderblocks, block, 0.46875, 1.0625, 0.46875, 0.53125, 1.1875, 0.53125, meta);
        renderblocks.clearOverrideBlockTexture();
        renderblocks.renderAllFaces = false;
    }
    
    private void renderRhunFireJar(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final int ao = getAO();
        setAO(0);
        renderblocks.renderAllFaces = true;
        LOTRBlockRhunFireJar.renderingStage = 1;
        renderblocks.setRenderBounds(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
        renderblocks.renderStandardBlock(block, i, j, k);
        LOTRBlockRhunFireJar.renderingStage = 2;
        renderblocks.setRenderBounds(0.3125, 0.5, 0.3125, 0.6875, 0.6875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        LOTRBlockRhunFireJar.renderingStage = 3;
        renderblocks.setRenderBounds(0.25, 0.6875, 0.25, 0.75, 0.8125, 0.75);
        renderblocks.renderStandardBlock(block, i, j, k);
        LOTRBlockRhunFireJar.renderingStage = 4;
        renderblocks.setRenderBounds(0.3125, 0.8125, 0.3125, 0.6875, 0.875, 0.6875);
        renderblocks.renderStandardBlock(block, i, j, k);
        LOTRBlockRhunFireJar.renderingStage = 5;
        renderblocks.setRenderBounds(0.375, 0.875, 0.5, 0.625, 1.0, 0.5);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.5, 0.875, 0.375, 0.5, 1.0, 0.625);
        renderblocks.renderStandardBlock(block, i, j, k);
        LOTRBlockRhunFireJar.renderingStage = 6;
        renderblocks.setRenderBounds(0.0, 0.0, 0.5, 1.0, 1.0, 0.5);
        renderblocks.renderStandardBlock(block, i, j, k);
        renderblocks.setRenderBounds(0.5, 0.0, 0.0, 0.5, 1.0, 1.0);
        renderblocks.renderStandardBlock(block, i, j, k);
        LOTRBlockRhunFireJar.renderingStage = 0;
        renderblocks.renderAllFaces = false;
        setAO(ao);
    }
    
    private void renderInvRhunFireJar(final Block block, final RenderBlocks renderblocks, final int meta) {
        renderblocks.renderAllFaces = true;
        LOTRBlockRhunFireJar.renderingStage = 1;
        renderStandardInvBlock(renderblocks, block, 0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
        LOTRBlockRhunFireJar.renderingStage = 2;
        renderStandardInvBlock(renderblocks, block, 0.3125, 0.5, 0.3125, 0.6875, 0.6875, 0.6875);
        LOTRBlockRhunFireJar.renderingStage = 3;
        renderStandardInvBlock(renderblocks, block, 0.25, 0.6875, 0.25, 0.75, 0.8125, 0.75);
        LOTRBlockRhunFireJar.renderingStage = 4;
        renderStandardInvBlock(renderblocks, block, 0.3125, 0.8125, 0.3125, 0.6875, 0.875, 0.6875);
        LOTRBlockRhunFireJar.renderingStage = 5;
        renderStandardInvBlock(renderblocks, block, 0.375, 0.875, 0.5, 0.625, 1.0, 0.5);
        renderStandardInvBlock(renderblocks, block, 0.5, 0.875, 0.375, 0.5, 1.0, 0.625);
        LOTRBlockRhunFireJar.renderingStage = 6;
        renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.5, 1.0, 1.0, 0.5);
        renderStandardInvBlock(renderblocks, block, 0.5, 0.0, 0.0, 0.5, 1.0, 1.0);
        LOTRBlockRhunFireJar.renderingStage = 0;
        renderblocks.renderAllFaces = false;
    }
    
    private void renderCoral(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        renderblocks.renderStandardBlock(block, i, j, k);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockBrightness(world, i, j + 1, k));
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final IIcon icon = ((LOTRBlockCoralReef)block).getRandomPlantIcon(i, j, k);
        renderblocks.drawCrossedSquares(icon, (double)i, (double)(j + 1), (double)k, 1.0f);
    }
    
    private boolean renderDoor(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final Tessellator tessellator = Tessellator.instance;
        final int meta = world.getBlockMetadata(i, j, k);
        final boolean topDoor = (meta & 0x8) != 0x0;
        if (topDoor) {
            if (world.getBlock(i, j - 1, k) != block) {
                return false;
            }
        }
        else if (world.getBlock(i, j + 1, k) != block) {
            return false;
        }
        boolean flag = false;
        final float f = 0.5f;
        final float f2 = 1.0f;
        final float f3 = 0.8f;
        final float f4 = 0.6f;
        final int light = block.getBlockBrightness(world, i, j, k);
        if (!topDoor || world.getBlock(i, j - 1, k) != block) {
            tessellator.setBrightness((renderblocks.renderMinY > 0.0) ? light : block.getBlockBrightness(world, i, j - 1, k));
            tessellator.setColorOpaque_F(f, f, f);
            renderblocks.renderFaceYNeg(block, (double)i, (double)j, (double)k, renderblocks.getBlockIcon(block, world, i, j, k, 0));
            flag = true;
        }
        if (topDoor || world.getBlock(i, j + 1, k) != block) {
            tessellator.setBrightness((renderblocks.renderMaxY < 1.0) ? light : block.getBlockBrightness(world, i, j + 1, k));
            tessellator.setColorOpaque_F(f2, f2, f2);
            renderblocks.renderFaceYPos(block, (double)i, (double)j, (double)k, renderblocks.getBlockIcon(block, world, i, j, k, 1));
            flag = true;
        }
        tessellator.setBrightness((renderblocks.renderMinZ > 0.0) ? light : block.getBlockBrightness(world, i, j, k - 1));
        tessellator.setColorOpaque_F(f3, f3, f3);
        IIcon iicon = renderblocks.getBlockIcon(block, world, i, j, k, 2);
        renderblocks.renderFaceZNeg(block, (double)i, (double)j, (double)k, iicon);
        flag = true;
        renderblocks.flipTexture = false;
        tessellator.setBrightness((renderblocks.renderMaxZ < 1.0) ? light : block.getBlockBrightness(world, i, j, k + 1));
        tessellator.setColorOpaque_F(f3, f3, f3);
        iicon = renderblocks.getBlockIcon(block, world, i, j, k, 3);
        renderblocks.renderFaceZPos(block, (double)i, (double)j, (double)k, iicon);
        flag = true;
        renderblocks.flipTexture = false;
        tessellator.setBrightness((renderblocks.renderMinX > 0.0) ? light : block.getBlockBrightness(world, i - 1, j, k));
        tessellator.setColorOpaque_F(f4, f4, f4);
        iicon = renderblocks.getBlockIcon(block, world, i, j, k, 4);
        renderblocks.renderFaceXNeg(block, (double)i, (double)j, (double)k, iicon);
        flag = true;
        renderblocks.flipTexture = false;
        tessellator.setBrightness((renderblocks.renderMaxX < 1.0) ? light : block.getBlockBrightness(world, i + 1, j, k));
        tessellator.setColorOpaque_F(f4, f4, f4);
        iicon = renderblocks.getBlockIcon(block, world, i, j, k, 5);
        renderblocks.renderFaceXPos(block, (double)i, (double)j, (double)k, iicon);
        flag = true;
        renderblocks.flipTexture = false;
        return flag;
    }
    
    private void renderRope(final IBlockAccess world, final int i, final int j, final int k, final Block block, final RenderBlocks renderblocks) {
        final double ropeWidth = 0.125;
        final double ropeMinX = 0.5 - ropeWidth / 2.0;
        final double ropeMaxX = 1.0 - ropeMinX;
        final double ropeOffset = 0.0625;
        final int meta = world.getBlockMetadata(i, j, k);
        final boolean top = world.getBlock(i, j + 1, k) != block || world.getBlockMetadata(i, j + 1, k) != meta;
        final double knotHeight = 0.25;
        final double knotBottom = 1.0 - knotHeight;
        final double knotWidth = 0.25;
        final double knotMinX = 0.5 - knotWidth / 2.0;
        final double knotMaxX = 1.0 - knotMinX;
        final double knotOffset = knotWidth;
        final double ropeTop = top ? (1.0 - knotHeight) : 1.0;
        if (meta == 5) {
            renderblocks.setRenderBounds(0.0, 0.0, ropeMinX, ropeOffset, ropeTop, ropeMaxX);
            renderblocks.renderStandardBlock(block, i, j, k);
            if (top) {
                renderblocks.setRenderAllFaces(true);
                renderblocks.setRenderBounds(0.0, knotBottom, knotMinX, knotOffset, 1.0, knotMaxX);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderAllFaces(false);
            }
        }
        if (meta == 4) {
            renderblocks.setRenderBounds(1.0 - ropeOffset, 0.0, ropeMinX, 1.0, ropeTop, ropeMaxX);
            renderblocks.renderStandardBlock(block, i, j, k);
            if (top) {
                renderblocks.setRenderAllFaces(true);
                renderblocks.setRenderBounds(1.0 - knotOffset, knotBottom, knotMinX, 1.0, 1.0, knotMaxX);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderAllFaces(false);
            }
        }
        if (meta == 3) {
            renderblocks.setRenderBounds(ropeMinX, 0.0, 0.0, ropeMaxX, ropeTop, ropeOffset);
            renderblocks.renderStandardBlock(block, i, j, k);
            if (top) {
                renderblocks.setRenderAllFaces(true);
                renderblocks.setRenderBounds(knotMinX, knotBottom, 0.0, knotMaxX, 1.0, knotOffset);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderAllFaces(false);
            }
        }
        if (meta == 2) {
            renderblocks.setRenderBounds(ropeMinX, 0.0, 1.0 - ropeOffset, ropeMaxX, ropeTop, 1.0);
            renderblocks.renderStandardBlock(block, i, j, k);
            if (top) {
                renderblocks.setRenderAllFaces(true);
                renderblocks.setRenderBounds(knotMinX, knotBottom, 1.0 - knotOffset, knotMaxX, 1.0, 1.0);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderAllFaces(false);
            }
        }
    }
    
    private static void renderStandardInvBlock(final RenderBlocks renderblocks, final Block block, final int meta) {
        block.setBlockBoundsForItemRender();
        renderblocks.setRenderBoundsFromBlock(block);
        final double d = renderblocks.renderMinX;
        final double d2 = renderblocks.renderMinY;
        final double d3 = renderblocks.renderMinZ;
        final double d4 = renderblocks.renderMaxX;
        final double d5 = renderblocks.renderMaxY;
        final double d6 = renderblocks.renderMaxZ;
        renderStandardInvBlock(renderblocks, block, d, d2, d3, d4, d5, d6, meta);
    }
    
    public static void renderStandardInvBlock(final RenderBlocks renderblocks, final Block block, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        renderStandardInvBlock(renderblocks, block, d, d1, d2, d3, d4, d5, 0);
    }
    
    public static void renderStandardInvBlock(final RenderBlocks renderblocks, final Block block, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final int metadata) {
        final Tessellator tessellator = Tessellator.instance;
        renderblocks.setRenderBounds(d, d1, d2, d3, d4, d5);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        renderblocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 0, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        renderblocks.renderFaceYPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        renderblocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 2, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        renderblocks.renderFaceZPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 3, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        renderblocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 4, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        renderblocks.renderFaceXPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 5, metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
    }
    
    private static int getAO() {
        return Minecraft.getMinecraft().gameSettings.ambientOcclusion;
    }
    
    private static void setAO(final int i) {
        Minecraft.getMinecraft().gameSettings.ambientOcclusion = i;
    }
    
    static {
        LOTRRenderBlocks.blockRand = new Random();
    }
}
