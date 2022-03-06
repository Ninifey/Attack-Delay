// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTREntityGaladhrimWarrior;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRBlockWood extends LOTRBlockWoodBase
{
    public LOTRBlockWood() {
        this.setWoodNames("shirePine", "mallorn", "mirkOak", "charred");
    }
    
    public boolean removedByPlayer(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k, final boolean willHarvest) {
        if (!world.isClient && (world.getBlockMetadata(i, j, k) & 0x3) == 0x1 && world.rand.nextInt(3) == 0 && world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenLothlorien && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.GALADHRIM) < 0.0f && !entityplayer.capabilities.isCreativeMode) {
            final int elves = 4 + world.rand.nextInt(3);
            boolean sentMessage = false;
            for (int l = 0; l < elves; ++l) {
                final LOTREntityGaladhrimWarrior elfWarrior = new LOTREntityGaladhrimWarrior(world);
                final int i2 = MathHelper.floor_double(((Entity)entityplayer).posX) - 6 + world.rand.nextInt(12);
                final int k2 = MathHelper.floor_double(((Entity)entityplayer).posZ) - 6 + world.rand.nextInt(12);
                final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                if (world.getBlock(i2, j2 - 1, k2).isSideSolid((IBlockAccess)world, i2, j2 - 1, k2, ForgeDirection.UP) && !world.getBlock(i2, j2, k2).isNormalCube() && !world.getBlock(i2, j2 + 1, k2).isNormalCube()) {
                    elfWarrior.setLocationAndAngles(i2 + 0.5, (double)j2, k2 + 0.5, 0.0f, 0.0f);
                    if (elfWarrior.getCanSpawnHere()) {
                        elfWarrior.spawnRidingHorse = false;
                        elfWarrior.onSpawnWithEgg(null);
                        world.spawnEntityInWorld((Entity)elfWarrior);
                        elfWarrior.isDefendingTree = true;
                        elfWarrior.setAttackTarget((EntityLivingBase)entityplayer);
                        if (!sentMessage) {
                            elfWarrior.sendSpeechBank(entityplayer, "galadhrim/warrior/defendTrees");
                            sentMessage = true;
                        }
                    }
                }
            }
        }
        return super.removedByPlayer(world, entityplayer, i, j, k, willHarvest);
    }
}
