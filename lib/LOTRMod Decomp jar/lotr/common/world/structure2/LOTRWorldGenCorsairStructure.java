// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenCorsairStructure extends LOTRWorldGenStructureBase2
{
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block pillarSlabBlock;
    protected int pillarSlabMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    
    public LOTRWorldGenCorsairStructure(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.brickBlock = LOTRMod.brick6;
        this.brickMeta = 6;
        this.brickSlabBlock = LOTRMod.slabSingle13;
        this.brickSlabMeta = 2;
        this.brickStairBlock = LOTRMod.stairsUmbarBrick;
        this.brickWallBlock = LOTRMod.wall5;
        this.brickWallMeta = 0;
        this.pillarBlock = LOTRMod.pillar2;
        this.pillarMeta = 10;
        this.pillarSlabBlock = LOTRMod.slabSingle13;
        this.pillarSlabMeta = 4;
        final int randomWood = random.nextInt(2);
        if (randomWood == 0) {
            this.plankBlock = LOTRMod.planks3;
            this.plankMeta = 3;
            this.plankSlabBlock = LOTRMod.woodSlabSingle5;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsPalm;
            this.fenceBlock = LOTRMod.fence3;
            this.fenceMeta = 3;
            this.fenceGateBlock = LOTRMod.fenceGatePalm;
        }
        else {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 2;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCedar;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 2;
            this.fenceGateBlock = LOTRMod.fenceGateCedar;
        }
    }
    
    protected ItemStack getRandomCorsairWeapon(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.daggerCorsair), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.battleaxeCorsair), new ItemStack(LOTRMod.nearHaradBow) };
        return items[random.nextInt(items.length)].copy();
    }
}
