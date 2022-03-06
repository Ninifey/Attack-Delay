// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleVillage extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenDaleVillage(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        final LOTRWorldGenDaleVillageTower tower = new LOTRWorldGenDaleVillageTower(super.notifyChanges);
        tower.restrictions = true;
        final int i2 = 0;
        final int k2 = -3;
        final int j2 = this.getTopBlock(world, i2, k2);
        final int r = 0;
        if (!tower.generateWithSetRotation(world, random, this.getX(i2, k2), this.getY(j2), this.getZ(i2, k2), (this.getRotationMode() + r) % 4)) {
            return false;
        }
        for (int smithies = MathHelper.getRandomIntegerInRange(random, 0, 1), l = 0; l < smithies; ++l) {
            final LOTRWorldGenDaleSmithy smithy = new LOTRWorldGenDaleSmithy(super.notifyChanges);
            smithy.restrictions = true;
            this.tryGenerateHouse(world, random, smithy);
        }
        for (int bakeries = MathHelper.getRandomIntegerInRange(random, 0, 1), m = 0; m < bakeries; ++m) {
            final LOTRWorldGenDaleBakery bakery = new LOTRWorldGenDaleBakery(super.notifyChanges);
            bakery.restrictions = true;
            this.tryGenerateHouse(world, random, bakery);
        }
        for (int houses = MathHelper.getRandomIntegerInRange(random, 2, 5), l2 = 0; l2 < houses; ++l2) {
            final LOTRWorldGenDaleHouse house = new LOTRWorldGenDaleHouse(super.notifyChanges);
            house.restrictions = true;
            this.tryGenerateHouse(world, random, house);
        }
        return true;
    }
    
    private boolean tryGenerateHouse(final World world, final Random random, final LOTRWorldGenStructureBase2 structure) {
        for (int attempts = 8, l = 0; l < attempts; ++l) {
            int i1 = 0;
            int k1 = 0;
            final int r = random.nextInt(4);
            if (r == 0) {
                i1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
                k1 = MathHelper.getRandomIntegerInRange(random, 7, 10);
            }
            else if (r == 1) {
                k1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
                i1 = -MathHelper.getRandomIntegerInRange(random, 7, 10);
            }
            else if (r == 2) {
                i1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
                k1 = -MathHelper.getRandomIntegerInRange(random, 7, 10);
            }
            else if (r == 3) {
                k1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
                i1 = MathHelper.getRandomIntegerInRange(random, 7, 10);
            }
            final int j1 = this.getTopBlock(world, i1, k1);
            if (structure.generateWithSetRotation(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1), (this.getRotationMode() + r) % 4)) {
                return true;
            }
        }
        return false;
    }
}
