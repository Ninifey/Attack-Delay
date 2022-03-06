// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionCamp extends LOTRWorldGenDorwinionTent
{
    public LOTRWorldGenDorwinionCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        final LOTRWorldGenDorwinionCaptainTent captainTent = new LOTRWorldGenDorwinionCaptainTent(super.notifyChanges);
        captainTent.restrictions = true;
        final int i2 = 0;
        final int k2 = -7;
        final int j2 = this.getTopBlock(world, i2, k2);
        final int r = 0;
        if (!captainTent.generateWithSetRotation(world, random, this.getX(i2, k2), this.getY(j2), this.getZ(i2, k2), (this.getRotationMode() + r) % 4)) {
            return false;
        }
        final int xMin = 8;
        final int xMax = 12;
        final int zMin = -5;
        final int zMax = 5;
        for (final int k3 : new int[] { -9, 0, 9 }) {
            this.tryGenerateTent(world, random, new int[] { -xMax, -xMin }, new int[] { k3 + zMin, k3 + zMax }, 3);
            this.tryGenerateTent(world, random, new int[] { xMin, xMax }, new int[] { k3 + zMin, k3 + zMax }, 1);
        }
        return true;
    }
    
    private boolean tryGenerateTent(final World world, final Random random, final int[] i, final int[] k, final int r) {
        final LOTRWorldGenDorwinionTent tent = new LOTRWorldGenDorwinionTent(super.notifyChanges);
        tent.restrictions = true;
        for (int attempts = 1, l = 0; l < attempts; ++l) {
            final int i2 = MathHelper.getRandomIntegerInRange(random, i[0], i[1]);
            final int k2 = MathHelper.getRandomIntegerInRange(random, k[0], k[1]);
            final int j1 = this.getTopBlock(world, i2, k2);
            if (tent.generateWithSetRotation(world, random, this.getX(i2, k2), this.getY(j1), this.getZ(i2, k2), (this.getRotationMode() + r) % 4)) {
                return true;
            }
        }
        return false;
    }
}
