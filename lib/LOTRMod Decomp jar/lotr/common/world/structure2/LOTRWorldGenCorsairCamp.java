// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTREntityCorsairSlaver;
import lotr.common.entity.npc.LOTREntityCorsairCaptain;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import java.util.Random;

public class LOTRWorldGenCorsairCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenCorsairCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        return new LOTRWorldGenCorsairTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        return null;
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityCorsair.class);
        respawner.setCheckRanges(24, -12, 12, 10);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
        for (int corsairs = 3 + random.nextInt(5), l = 0; l < corsairs; ++l) {
            LOTREntityCorsair corsair = new LOTREntityCorsair(world);
            if (l == 0) {
                corsair = (LOTREntityCorsair)(random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world));
            }
            final int r = 4;
            final float ang = random.nextFloat() * 3.1415927f * 2.0f;
            final int i2 = (int)(r * MathHelper.cos(ang));
            final int k2 = (int)(r * MathHelper.sin(ang));
            final int j2 = this.getTopBlock(world, i2, k2);
            this.spawnNPCAndSetHome(corsair, world, i2, j2, k2, 16);
        }
    }
    
    @Override
    protected boolean generateFarm() {
        return false;
    }
    
    @Override
    protected void generateCentrepiece(final World world, final Random random, final int i, final int j, final int k) {
        this.loadStrScan("corsair_camp_centre");
        this.generateStrScan(world, random, 0, 0, 0);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            for (int att = 0; att < 16; ++att) {
                final int r = MathHelper.getRandomIntegerInRange(random, 8, 20);
                final float ang = random.nextFloat() * 3.1415927f * 2.0f;
                final int i2 = (int)(r * MathHelper.cos(ang));
                final int k2 = (int)(r * MathHelper.sin(ang));
                final int j2 = this.getTopBlock(world, i2, k2);
                final int rot = random.nextInt(4);
                if (this.generateSubstructureWithRestrictionFlag(new LOTRWorldGenCorsairCampCage(super.notifyChanges), world, random, i2, j2, k2, rot, true)) {
                    break;
                }
            }
            for (int chestPiles = 1 + random.nextInt(2), l = 0; l < chestPiles; ++l) {
                for (int att2 = 0; att2 < 16; ++att2) {
                    final int r2 = MathHelper.getRandomIntegerInRange(random, 8, 20);
                    final float ang2 = random.nextFloat() * 3.1415927f * 2.0f;
                    final int i3 = (int)(r2 * MathHelper.cos(ang2));
                    final int k3 = (int)(r2 * MathHelper.sin(ang2));
                    final int j3 = this.getTopBlock(world, i3, k3);
                    if (this.isOpaque(world, i3, j3 - 1, k3) && this.isAir(world, i3, j3, k3) && this.isAir(world, i3, j3 + 1, k3)) {
                        this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.wood8, 3);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                        this.placeChest(world, random, i3, j3 + 1, k3, LOTRMod.chestBasket, 2, LOTRChestContents.CORSAIR, 3 + random.nextInt(3));
                        this.tryPlaceSideChest(world, random, i3 - 1, j3, k3, 5);
                        this.tryPlaceSideChest(world, random, i3 + 1, j3, k3, 4);
                        this.tryPlaceSideChest(world, random, i3, j3, k3 - 1, 2);
                        this.tryPlaceSideChest(world, random, i3, j3, k3 + 1, 3);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void tryPlaceSideChest(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (this.isOpaque(world, i, j - 1, k) && this.isAir(world, i, j, k)) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.chestBasket, meta);
            }
            else {
                this.placeChest(world, random, i, j, k, LOTRMod.chestBasket, meta, LOTRChestContents.CORSAIR, 1);
            }
        }
    }
}
