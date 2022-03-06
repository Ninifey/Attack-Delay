// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.util.WeightedRandom;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import java.util.Random;

public enum LOTRTreeType
{
    OAK((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(4) == 0) {
                return new LOTRWorldGenGnarledOak(flag);
            }
            return new LOTRWorldGenSimpleTrees(flag, 4, 6, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_TALL((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(4) == 0) {
                return new LOTRWorldGenGnarledOak(flag).setMinMaxHeight(6, 10);
            }
            return new LOTRWorldGenSimpleTrees(flag, 8, 12, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_TALLER((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 12, 16, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_ITHILIEN_HIDEOUT((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 6, 6, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBigTrees(flag, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_FANGORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_FANGORN_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, (Block)Blocks.leaves, 0).setNoLeaves();
        }
    }), 
    OAK_SWAMP((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenSwamp();
        }
    }), 
    OAK_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log, 0);
        }
    }), 
    OAK_DESERT((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDesertTrees(flag, Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    OAK_SHRUB((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new LOTRWorldGenShrub(Blocks.log, 0, (Block)Blocks.leaves, 0);
        }
    }), 
    BIRCH((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(3) != 0) {
                return new LOTRWorldGenAspen(flag).setBlocks(Blocks.log, 2, (Block)Blocks.leaves, 2).setMinMaxHeight(8, 16);
            }
            return new LOTRWorldGenSimpleTrees(flag, 5, 7, Blocks.log, 2, (Block)Blocks.leaves, 2);
        }
    }), 
    BIRCH_TALL((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 8, 11, Blocks.log, 2, (Block)Blocks.leaves, 2);
        }
    }), 
    BIRCH_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBigTrees(flag, Blocks.log, 2, (Block)Blocks.leaves, 2);
        }
    }), 
    BIRCH_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(Blocks.log, 2, (Block)Blocks.leaves, 2);
        }
    }), 
    BIRCH_FANGORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 2, (Block)Blocks.leaves, 2);
        }
    }), 
    BIRCH_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log, 2);
        }
    }), 
    SPRUCE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenTaiga2(flag);
        }
    }), 
    SPRUCE_THIN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenTaiga1();
        }
    }), 
    SPRUCE_MEGA((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenMegaPineTree(flag, true);
        }
    }), 
    SPRUCE_MEGA_THIN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenMegaPineTree(flag, false);
        }
    }), 
    SPRUCE_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log, 1);
        }
    }), 
    JUNGLE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenTrees(flag, 7, 3, 3, true);
        }
    }), 
    JUNGLE_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenMegaJungle(flag, 10, 20, 3, 3);
        }
    }), 
    JUNGLE_CLOUD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenMegaJungle(flag, 30, 30, 3, 3);
        }
    }), 
    JUNGLE_SHRUB((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new LOTRWorldGenShrub(Blocks.log, 3, (Block)Blocks.leaves, 3);
        }
    }), 
    JUNGLE_FANGORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 3, (Block)Blocks.leaves, 3).setHeightFactor(1.5f);
        }
    }), 
    ACACIA((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenSavannaTree(flag);
        }
    }), 
    ACACIA_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log2, 0);
        }
    }), 
    DARK_OAK((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new WorldGenCanopyTree(flag);
        }
    }), 
    DARK_OAK_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(Blocks.log2, 1, (Block)Blocks.leaves2, 1);
        }
    }), 
    SHIRE_PINE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenShirePine(flag);
        }
    }), 
    MALLORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 6, 9, LOTRMod.wood, 1, LOTRMod.leaves, 1);
        }
    }), 
    MALLORN_BOUGHS((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMallorn(flag);
        }
    }), 
    MALLORN_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood, 1, LOTRMod.leaves, 1);
        }
    }), 
    MALLORN_EXTREME((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMallornExtreme(flag);
        }
    }), 
    MALLORN_EXTREME_SAPLING((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMallornExtreme(flag).setSaplingGrowth();
        }
    }), 
    MIRK_OAK((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 4, 7, 0, true);
        }
    }), 
    MIRK_OAK_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 16, 1, true);
        }
    }), 
    MIRK_OAK_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 4, 7, 0, true).setDead();
        }
    }), 
    RED_OAK((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 6, 9, 0, false).setRedOak();
        }
    }), 
    RED_OAK_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 17, 1, false).setRedOak();
        }
    }), 
    RED_OAK_WEIRWOOD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 20, 1, false).setBlocks(LOTRMod.wood9, 0, LOTRMod.leaves, 3);
        }
    }), 
    CHARRED((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenCharredTrees();
        }
    }), 
    CHARRED_FANGORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood, 3, Blocks.air, 0).setNoLeaves();
        }
    }), 
    APPLE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 7, LOTRMod.fruitWood, 0, LOTRMod.fruitLeaves, 0);
        }
    }), 
    PEAR((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 5, LOTRMod.fruitWood, 1, LOTRMod.fruitLeaves, 1);
        }
    }), 
    CHERRY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2);
        }
    }), 
    CHERRY_MORDOR((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2).disableRestrictions();
        }
    }), 
    MANGO((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
        }
    }), 
    LEBETHRON((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
        }
    }), 
    LEBETHRON_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
        }
    }), 
    LEBETHRON_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
        }
    }), 
    LEBETHRON_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 0);
        }
    }), 
    BEECH((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), 
    BEECH_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), 
    BEECH_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), 
    BEECH_FANGORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), 
    BEECH_FANGORN_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1).setNoLeaves();
        }
    }), 
    BEECH_DEAD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 1);
        }
    }), 
    HOLLY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenHolly(flag);
        }
    }), 
    HOLLY_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenHolly(flag).setLarge();
        }
    }), 
    BANANA((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBanana(flag);
        }
    }), 
    MAPLE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
        }
    }), 
    MAPLE_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
        }
    }), 
    MAPLE_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
        }
    }), 
    LARCH((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenLarch(flag);
        }
    }), 
    DATE_PALM((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPalm(flag, LOTRMod.wood3, 2, LOTRMod.leaves3, 2).setMinMaxHeight(5, 8).setDates();
        }
    }), 
    MANGROVE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMangrove(flag);
        }
    }), 
    CHESTNUT((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 5, 7, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
        }
    }), 
    CHESTNUT_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
        }
    }), 
    CHESTNUT_PARTY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
        }
    }), 
    BAOBAB((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenBaobab(flag);
        }
    }), 
    CEDAR((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenCedar(flag);
        }
    }), 
    CEDAR_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenCedar(flag).setMinMaxHeight(15, 30);
        }
    }), 
    FIR((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFir(flag);
        }
    }), 
    PINE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPine(flag);
        }
    }), 
    PINE_SHRUB((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return (WorldGenAbstractTree)new LOTRWorldGenShrub(LOTRMod.wood5, 0, LOTRMod.leaves5, 0);
        }
    }), 
    LEMON((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 1, LOTRMod.leaves5, 1);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 1, LOTRMod.leaves5, 1);
        }
    }), 
    ORANGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 2, LOTRMod.leaves5, 2);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 2, LOTRMod.leaves5, 2);
        }
    }), 
    LIME((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 3, LOTRMod.leaves5, 3);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 3, LOTRMod.leaves5, 3);
        }
    }), 
    MAHOGANY((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenCedar(flag).setBlocks(LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHangingLeaves();
        }
    }), 
    MAHOGANY_FANGORN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHeightFactor(1.5f);
        }
    }), 
    WILLOW((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenWillow(flag);
        }
    }), 
    WILLOW_WATER((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenWillow(flag).setNeedsWater();
        }
    }), 
    CYPRESS((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenCypress(flag);
        }
    }), 
    CYPRESS_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenCypress(flag).setLarge();
        }
    }), 
    OLIVE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenOlive(flag);
        }
    }), 
    OLIVE_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenOlive(flag).setMinMaxHeight(5, 8).setExtraTrunkWidth(1);
        }
    }), 
    ASPEN((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenAspen(flag);
        }
    }), 
    ASPEN_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenAspen(flag).setExtraTrunkWidth(1).setMinMaxHeight(14, 25);
        }
    }), 
    GREEN_OAK((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 4, 7, 0, false).setGreenOak();
        }
    }), 
    GREEN_OAK_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 16, 1, false).setGreenOak();
        }
    }), 
    GREEN_OAK_EXTREME((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenMirkOak(flag, 25, 45, 2, false).setGreenOak();
        }
    }), 
    LAIRELOSSE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenLairelosse(flag);
        }
    }), 
    LAIRELOSSE_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenLairelosse(flag).setExtraTrunkWidth(1).setMinMaxHeight(8, 12);
        }
    }), 
    ALMOND((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenAlmond(flag);
        }
    }), 
    PLUM((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 6, LOTRMod.wood8, 0, LOTRMod.leaves8, 0);
        }
    }), 
    REDWOOD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenRedwood(flag);
        }
    }), 
    REDWOOD_2((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenRedwood(flag).setExtraTrunkWidth(1);
        }
    }), 
    REDWOOD_3((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenRedwood(flag).setTrunkWidth(1);
        }
    }), 
    REDWOOD_4((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenRedwood(flag).setTrunkWidth(1).setExtraTrunkWidth(1);
        }
    }), 
    REDWOOD_5((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenRedwood(flag).setTrunkWidth(2);
        }
    }), 
    POMEGRANATE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            if (rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood8, 2, LOTRMod.leaves8, 2);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood8, 2, LOTRMod.leaves8, 2);
        }
    }), 
    PALM((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenPalm(flag, LOTRMod.wood8, 3, LOTRMod.leaves8, 3).setMinMaxHeight(6, 11);
        }
    }), 
    DRAGONBLOOD((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDragonblood(flag, 3, 7, 0);
        }
    }), 
    DRAGONBLOOD_LARGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDragonblood(flag, 6, 10, 1);
        }
    }), 
    DRAGONBLOOD_HUGE((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenDragonblood(flag, 8, 16, 2);
        }
    }), 
    KANUKA((ITreeFactory)new ITreeFactory() {
        @Override
        public WorldGenAbstractTree createTree(final boolean flag, final Random rand) {
            return new LOTRWorldGenKanuka(flag);
        }
    }), 
    NULL((ITreeFactory)null);
    
    private ITreeFactory treeFactory;
    
    private LOTRTreeType(final ITreeFactory factory) {
        this.treeFactory = factory;
    }
    
    public WorldGenAbstractTree create(final boolean flag, final Random rand) {
        return this.treeFactory.createTree(flag, rand);
    }
    
    public static class WeightedTreeType extends WeightedRandom.Item
    {
        public final LOTRTreeType treeType;
        
        public WeightedTreeType(final LOTRTreeType tree, final int i) {
            super(i);
            this.treeType = tree;
        }
    }
    
    private interface ITreeFactory
    {
        WorldGenAbstractTree createTree(final boolean p0, final Random p1);
    }
}
