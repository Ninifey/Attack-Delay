// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;

public class LOTRWorldGenGulfVillageSign extends LOTRWorldGenGulfStructure
{
    private String[] signText;
    
    public LOTRWorldGenGulfVillageSign(final boolean flag) {
        super(flag);
        this.signText = LOTRNames.getHaradVillageName(new Random());
    }
    
    public LOTRWorldGenGulfVillageSign setSignText(final String[] s) {
        this.signText = s;
        return this;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            final int i2 = 0;
            final int k2 = 0;
            final int j2 = this.getTopBlock(world, i2, k2) - 1;
            if (!this.isSurface(world, i2, j2, k2)) {
                return false;
            }
        }
        for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, 0, j3, 0)) && this.getY(j3) >= 0; --j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, super.boneBlock, super.boneMeta);
            this.setGrassToDirt(world, 0, j3 - 1, 0);
        }
        this.setBlockAndMetadata(world, 0, 1, 0, super.boneBlock, super.boneMeta);
        this.setBlockAndMetadata(world, 0, 2, 0, super.boneBlock, super.boneMeta);
        this.setBlockAndMetadata(world, 0, 3, 0, super.boneBlock, super.boneMeta);
        this.setBlockAndMetadata(world, 0, 4, 0, super.boneBlock, super.boneMeta);
        this.setBlockAndMetadata(world, -1, 4, 0, super.boneWallBlock, super.boneWallMeta);
        this.setBlockAndMetadata(world, 1, 4, 0, super.boneWallBlock, super.boneWallMeta);
        this.setBlockAndMetadata(world, 0, 4, -1, super.boneWallBlock, super.boneWallMeta);
        this.setBlockAndMetadata(world, 0, 4, 1, super.boneWallBlock, super.boneWallMeta);
        this.placeSkull(world, random, -1, 5, 0);
        this.placeSkull(world, random, 1, 5, 0);
        this.placeSkull(world, random, 0, 5, -1);
        this.placeSkull(world, random, 0, 5, 1);
        this.setBlockAndMetadata(world, -1, 3, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 1, 3, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
        if (this.signText != null) {
            this.placeSign(world, -1, 2, 0, Blocks.wall_sign, 5, this.signText);
            this.placeSign(world, 1, 2, 0, Blocks.wall_sign, 4, this.signText);
            this.placeSign(world, 0, 2, -1, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 0, 2, 1, Blocks.wall_sign, 3, this.signText);
        }
        return true;
    }
}
