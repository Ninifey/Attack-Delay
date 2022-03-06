// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionBath extends LOTRWorldGenGondorBath
{
    private LOTRWorldGenDorwinionHouse houseGenForBlocks;
    
    public LOTRWorldGenDorwinionBath(final boolean flag) {
        super(flag);
        this.houseGenForBlocks = new LOTRWorldGenDorwinionHouse(flag);
    }
    
    @Override
    protected LOTREntityNPC createBather(final World world) {
        return new LOTREntityDorwinionMan(world);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.houseGenForBlocks.setupRandomBlocks(random);
        super.brickBlock = this.houseGenForBlocks.brickBlock;
        super.brickMeta = this.houseGenForBlocks.brickMeta;
        super.brickSlabBlock = this.houseGenForBlocks.brickSlabBlock;
        super.brickSlabMeta = this.houseGenForBlocks.brickSlabMeta;
        super.brickStairBlock = this.houseGenForBlocks.brickStairBlock;
        super.brickWallBlock = this.houseGenForBlocks.brickWallBlock;
        super.brickWallMeta = this.houseGenForBlocks.brickWallMeta;
        super.pillarBlock = this.houseGenForBlocks.pillarBlock;
        super.pillarMeta = this.houseGenForBlocks.pillarMeta;
        super.brick2Block = this.houseGenForBlocks.clayBlock;
        super.brick2Meta = this.houseGenForBlocks.clayMeta;
        super.brick2SlabBlock = this.houseGenForBlocks.claySlabBlock;
        super.brick2SlabMeta = this.houseGenForBlocks.claySlabMeta;
        super.brick2StairBlock = this.houseGenForBlocks.clayStairBlock;
    }
}
