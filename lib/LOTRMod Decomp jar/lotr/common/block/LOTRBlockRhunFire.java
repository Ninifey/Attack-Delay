// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.block.Block;
import java.util.HashMap;
import lotr.common.LOTRMod;
import java.util.Random;
import lotr.common.LOTRBannerProtection;
import net.minecraft.world.World;
import net.minecraft.block.BlockFire;

public class LOTRBlockRhunFire extends BlockFire
{
    public LOTRBlockRhunFire() {
        this.setLightLevel(1.0f);
    }
    
    private boolean isBannered(final World world, final int i, final int j, final int k) {
        return LOTRBannerProtection.isProtectedByBanner(world, i, j, k, LOTRBannerProtection.anyBanner(), false);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (LOTRMod.doFireTick(world)) {
            if (this.isBannered(world, i, j, k)) {
                world.setBlockToAir(i, j, k);
            }
            else {
                final Map<Block, Pair<Integer, Integer>> infos = new HashMap<Block, Pair<Integer, Integer>>();
                final boolean canBurnStone = random.nextFloat() < 0.9f;
                if (canBurnStone) {
                    for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                        final Block block = world.getBlock(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
                        final Material material = block.getMaterial();
                        if ((material == Material.rock || material == Material.field_151571_B || block instanceof LOTRBlockGate) && block.getExplosionResistance((Entity)null) < 100.0f) {
                            final int enco = this.getEncouragement(block);
                            final int flam = this.getFlammability(block);
                            infos.put(block, (Pair<Integer, Integer>)Pair.of((Object)enco, (Object)flam));
                            Blocks.fire.setFireInfo(block, 30, 30);
                        }
                    }
                }
                if (random.nextInt(12) == 0) {
                    world.setBlockToAir(i, j, k);
                }
                else {
                    this.runBaseFireUpdate(world, i, j, k, random);
                }
                if (!infos.isEmpty()) {
                    for (final Map.Entry<Block, Pair<Integer, Integer>> e : infos.entrySet()) {
                        Blocks.fire.setFireInfo((Block)e.getKey(), (int)e.getValue().getLeft(), (int)e.getValue().getRight());
                    }
                }
            }
        }
    }
    
    private void runBaseFireUpdate(final World world, final int i, final int j, final int k, final Random random) {
        if (LOTRMod.doFireTick(world)) {
            final boolean isFireplace = world.getBlock(i, j - 1, k).isFireSource(world, i, j - 1, k, ForgeDirection.UP);
            if (!this.canPlaceBlockAt(world, i, j, k)) {
                world.setBlockToAir(i, j, k);
            }
            if (!isFireplace && world.isRaining() && (world.canLightningStrikeAt(i, j, k) || world.canLightningStrikeAt(i - 1, j, k) || world.canLightningStrikeAt(i + 1, j, k) || world.canLightningStrikeAt(i, j, k - 1) || world.canLightningStrikeAt(i, j, k + 1))) {
                world.setBlockToAir(i, j, k);
            }
            else {
                final int meta = world.getBlockMetadata(i, j, k);
                if (meta < 15) {
                    world.setBlockMetadata(i, j, k, meta + random.nextInt(3) / 2, 4);
                }
                world.scheduleBlockUpdate(i, j, k, (Block)this, this.func_149738_a(world) + random.nextInt(10));
                if (!isFireplace && !this.canNeighborBurn(world, i, j, k)) {
                    if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)world, i, j - 1, k) || meta > 3) {
                        world.setBlockToAir(i, j, k);
                    }
                }
                else if (!isFireplace && !this.canCatchFire((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) && meta == 15 && random.nextInt(4) == 0) {
                    world.setBlockToAir(i, j, k);
                }
                else {
                    int extraChance = 0;
                    final boolean humid = world.isBlockHighHumidity(i, j, k);
                    if (humid) {
                        extraChance = -50;
                    }
                    final int hChance = 300 + extraChance;
                    final int vChance = 250 + extraChance;
                    for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                        this.tryCatchFire(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, (dir.offsetY == 0) ? hChance : vChance, random, meta, dir);
                    }
                    final int xzRange = 1;
                    final int yMin = -1;
                    final int yMax = 4;
                    for (int i2 = i - xzRange; i2 <= i + xzRange; ++i2) {
                        for (int k2 = k - xzRange; k2 <= k + xzRange; ++k2) {
                            for (int j2 = j + yMin; j2 <= j + yMax; ++j2) {
                                if ((i2 != i || j2 != j || k2 != k) && !this.isBannered(world, i2, j2, k2)) {
                                    int totalChance = 100;
                                    if (j2 > j + 1) {
                                        totalChance += (j2 - (j + 1)) * 100;
                                    }
                                    final int encourage = this.getChanceOfNeighborsEncouragingFire(world, i2, j2, k2);
                                    if (encourage > 0) {
                                        int chance = (encourage + 40 + world.difficultySetting.getDifficultyId() * 7) / (meta + 30);
                                        if (humid) {
                                            chance /= 2;
                                        }
                                        if (chance > 0 && random.nextInt(totalChance) <= chance && (!world.isRaining() || !world.canLightningStrikeAt(i2, j2, k2)) && !world.canLightningStrikeAt(i2 - 1, j2, k) && !world.canLightningStrikeAt(i2 + 1, j2, k2) && !world.canLightningStrikeAt(i2, j2, k2 - 1) && !world.canLightningStrikeAt(i2, j2, k2 + 1)) {
                                            int newMeta = meta + random.nextInt(5) / 4;
                                            if (newMeta > 15) {
                                                newMeta = 15;
                                            }
                                            world.setBlock(i2, j2, k2, (Block)this, newMeta, 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void tryCatchFire(final World world, final int i, final int j, final int k, final int chance, final Random random, final int meta, final ForgeDirection face) {
        if (this.isBannered(world, i, j, k)) {
            return;
        }
        final int flamm = world.getBlock(i, j, k).getFlammability((IBlockAccess)world, i, j, k, face);
        if (random.nextInt(chance) < flamm) {
            final boolean isTNT = world.getBlock(i, j, k) == Blocks.tnt;
            if (random.nextInt(meta + 10) < 5 && !world.canLightningStrikeAt(i, j, k)) {
                int newMeta = meta + random.nextInt(5) / 4;
                if (newMeta > 15) {
                    newMeta = 15;
                }
                world.setBlock(i, j, k, (Block)this, newMeta, 3);
            }
            else {
                world.setBlockToAir(i, j, k);
            }
            if (isTNT) {
                Blocks.tnt.onBlockDestroyedByPlayer(world, i, j, k, 1);
            }
        }
    }
    
    private int getChanceOfNeighborsEncouragingFire(final World world, final int i, final int j, final int k) {
        if (!world.isAirBlock(i, j, k)) {
            return 0;
        }
        int chance = 0;
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            chance = this.getChanceToEncourageFire((IBlockAccess)world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, chance, dir);
        }
        return chance;
    }
    
    private boolean canNeighborBurn(final World world, final int i, final int j, final int k) {
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (this.canCatchFireNotBannered(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, dir)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canCatchFireNotBannered(final World world, final int i, final int j, final int k, final ForgeDirection face) {
        return !this.isBannered(world, i, j, k) && this.canCatchFire((IBlockAccess)world, i, j, k, face);
    }
    
    public int getChanceToEncourageFire(final IBlockAccess world, final int i, final int j, final int k, final int oldChance, final ForgeDirection face) {
        final int chance = super.getChanceToEncourageFire(world, i, j, k, oldChance, face);
        return (int)(chance * 1.25f);
    }
    
    public int func_149738_a(final World world) {
        return 2;
    }
    
    public boolean isBurning(final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
}
