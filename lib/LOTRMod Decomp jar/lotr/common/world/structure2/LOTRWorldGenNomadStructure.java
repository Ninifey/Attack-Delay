// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenNomadStructure extends LOTRWorldGenStructureBase2
{
    protected Block tentBlock;
    protected int tentMeta;
    protected Block tent2Block;
    protected int tent2Meta;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block carpet2Block;
    protected int carpet2Meta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    protected Block beamBlock;
    protected int beamMeta;
    protected Block bedBlock;
    
    public LOTRWorldGenNomadStructure(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.tentBlock = Blocks.wool;
        this.tentMeta = 0;
        this.tent2Block = Blocks.wool;
        this.tent2Meta = 12;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 0;
        this.carpet2Block = Blocks.carpet;
        this.carpet2Meta = 12;
        final int randomWood = random.nextInt(3);
        if (randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.fenceGateBlock = Blocks.fence_gate;
            this.beamBlock = LOTRMod.woodBeamV1;
            this.beamMeta = 0;
        }
        else if (randomWood == 1) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 2;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCedar;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 2;
            this.fenceGateBlock = LOTRMod.fenceGateCedar;
            this.beamBlock = LOTRMod.woodBeam4;
            this.beamMeta = 2;
        }
        else if (randomWood == 2) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 14;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 6;
            this.plankStairBlock = LOTRMod.stairsDatePalm;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 14;
            this.fenceGateBlock = LOTRMod.fenceGateDatePalm;
            this.beamBlock = LOTRMod.woodBeam3;
            this.beamMeta = 2;
        }
        this.bedBlock = LOTRMod.strawBed;
    }
    
    protected void laySandBase(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, (Block)Blocks.sand, 0);
        for (int j2 = j - 1; this.getY(j2) >= 0 && !this.isOpaque(world, i, j2, k); --j2) {
            if (this.isOpaque(world, i, j2 - 1, k)) {
                this.setBlockAndMetadata(world, i, j2, k, Blocks.sandstone, 0);
            }
            else {
                this.setBlockAndMetadata(world, i, j2, k, (Block)Blocks.sand, 0);
            }
            this.setGrassToDirt(world, i, j2 - 1, k);
        }
    }
    
    protected ItemStack getRandomNomadWeapon(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad) };
        return items[random.nextInt(items.length)].copy();
    }
    
    protected ItemStack getRandomUmbarWeapon(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad) };
        return items[random.nextInt(items.length)].copy();
    }
}
