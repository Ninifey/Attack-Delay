// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.entity.npc.LOTREntityHobbitShirriffChief;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHobbitBartender;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.item.ItemStack;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;

public class LOTRWorldGenHobbitTavern extends LOTRWorldGenHobbitStructure
{
    private String[] tavernName;
    private String[] tavernNameSign;
    private String tavernNameNPC;
    
    public LOTRWorldGenHobbitTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.tavernName = LOTRNames.getHobbitTavernName(random);
        this.tavernNameSign = new String[] { "", this.tavernName[0], this.tavernName[1], "" };
        this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 12);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -18; i2 <= 18; ++i2) {
                for (int k2 = -12; k2 <= 19; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -16; i3 <= 16; ++i3) {
            for (int k3 = -12; k3 <= 18; ++k3) {
                final int i4 = Math.abs(i3);
                int grassHeight = -1;
                if (i4 <= 14) {
                    if (k3 <= -6) {
                        if ((k3 == -7 && i4 <= 1) || (k3 == -6 && i4 <= 3)) {
                            grassHeight = 1;
                        }
                        else {
                            grassHeight = 0;
                        }
                    }
                    else if (k3 <= -5 && (i4 == 11 || i4 <= 5)) {
                        grassHeight = 1;
                    }
                    else if (k3 <= -4 && i4 <= 11) {
                        grassHeight = 1;
                    }
                    else if (k3 <= -3 && i4 <= 3) {
                        grassHeight = 1;
                    }
                }
                if (grassHeight >= 0) {
                    for (int j2 = grassHeight; (j2 >= -1 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        if (j2 == grassHeight) {
                            this.setBlockAndMetadata(world, i3, j2, k3, (Block)Blocks.grass, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                        }
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = grassHeight + 1; j2 <= 12; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                else {
                    boolean wood = false;
                    boolean beam = false;
                    if (k3 >= -5 && k3 <= 17) {
                        wood = (i4 < 15 || (k3 > -4 && k3 < 16));
                    }
                    if (i4 == 15 && (k3 == -4 || k3 == 16)) {
                        beam = true;
                    }
                    if (k3 == 18 && i4 <= 14 && IntMath.mod(i3, 5) == 0) {
                        beam = true;
                    }
                    if (beam || wood) {
                        for (int j3 = 5; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            if (beam) {
                                this.setBlockAndMetadata(world, i3, j3, k3, super.beamBlock, super.beamMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i3, j3, k3, super.plankBlock, super.plankMeta);
                            }
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        this.setBlockAndMetadata(world, i3, 6, k3, super.plankBlock, super.plankMeta);
                        for (int j3 = 8; j3 <= 12; ++j3) {
                            this.setAir(world, i3, j3, k3);
                        }
                    }
                }
            }
        }
        for (int i3 = -16; i3 <= 16; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 <= 4) {
                this.setBlockAndMetadata(world, i3, 1, -10, super.outFenceBlock, super.outFenceMeta);
            }
            if (i5 >= 4 && i5 <= 11) {
                this.setBlockAndMetadata(world, i3, 1, -9, super.outFenceBlock, super.outFenceMeta);
            }
            if (i5 >= 11 && i5 <= 13) {
                this.setBlockAndMetadata(world, i3, 1, -8, super.outFenceBlock, super.outFenceMeta);
            }
            if (i5 == 13) {
                this.setBlockAndMetadata(world, i3, 1, -7, super.outFenceBlock, super.outFenceMeta);
                this.setBlockAndMetadata(world, i3, 1, -6, super.outFenceBlock, super.outFenceMeta);
            }
            if (i5 == 0) {
                this.setBlockAndMetadata(world, i3, 1, -10, super.outFenceGateBlock, 0);
            }
            if (i5 == 4) {
                this.setBlockAndMetadata(world, i3, 2, -10, Blocks.torch, 5);
            }
            if (i5 <= 1) {
                if (i3 == 0) {
                    this.setBlockAndMetadata(world, i3, 0, -12, super.pathBlock, super.pathMeta);
                    this.setBlockAndMetadata(world, i3, 0, -11, super.pathBlock, super.pathMeta);
                    this.setBlockAndMetadata(world, i3, 0, -10, super.pathBlock, super.pathMeta);
                }
                this.setBlockAndMetadata(world, i3, 0, -9, super.pathBlock, super.pathMeta);
                this.setBlockAndMetadata(world, i3, 0, -8, super.pathBlock, super.pathMeta);
                this.setBlockAndMetadata(world, i3, 1, -7, super.pathSlabBlock, super.pathSlabMeta);
                this.setBlockAndMetadata(world, i3, 1, -6, super.pathSlabBlock, super.pathSlabMeta);
                for (int k4 = -5; k4 <= -2; ++k4) {
                    this.setBlockAndMetadata(world, i3, 1, k4, super.pathBlock, super.pathMeta);
                }
            }
            if (i5 == 5 || i5 == 11) {
                this.setGrassToDirt(world, i3, 0, -4);
                for (int j4 = 1; j4 <= 5; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, -4, super.beamBlock, super.beamMeta);
                }
            }
            if (i5 == 6 || i5 == 10) {
                this.setBlockAndMetadata(world, i3, 3, -4, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 2, -4, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 1, -5, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 0, -5, (Block)Blocks.grass, 0);
            }
            if (i5 >= 7 && i5 <= 9) {
                this.setBlockAndMetadata(world, i3, 2, -5, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 1, -5, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 0, -5, (Block)Blocks.grass, 0);
                this.setBlockAndMetadata(world, i3, 1, -6, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 2, -4, super.brickBlock, super.brickMeta);
                this.setGrassToDirt(world, i3, 1, -4);
                this.setBlockAndMetadata(world, i3, 3, -3, LOTRMod.glassPane, 0);
                this.setBlockAndMetadata(world, i3, 4, -3, LOTRMod.glassPane, 0);
                if (i5 == 7 || i5 == 9) {
                    this.placeFlowerPot(world, i3, 3, -4, this.getRandomFlower(world, random));
                }
            }
            if (i5 >= 6 && i5 <= 10) {
                this.setBlockAndMetadata(world, i3, 5, -4, super.plankStairBlock, 6);
            }
            if (i5 >= 5 && i5 <= 11) {
                this.setBlockAndMetadata(world, i3, 6, -4, super.plankBlock, super.plankMeta);
            }
            if (i5 == 13) {
                this.setBlockAndMetadata(world, i3, 3, -6, super.fence2Block, super.fence2Meta);
                this.setBlockAndMetadata(world, i3, 4, -6, Blocks.torch, 5);
            }
            if (i5 == 4) {
                this.setBlockAndMetadata(world, i3, 2, -4, super.hedgeBlock, super.hedgeMeta);
            }
            if (i5 == 3) {
                this.setBlockAndMetadata(world, i3, 2, -4, super.hedgeBlock, super.hedgeMeta);
                this.setBlockAndMetadata(world, i3, 2, -3, super.hedgeBlock, super.hedgeMeta);
            }
        }
        for (int i3 = -12; i3 <= 12; ++i3) {
            for (int k3 = -8; k3 <= -2; ++k3) {
                for (int j4 = 0; j4 <= 1; ++j4) {
                    if (this.getBlock(world, i3, j4, k3) == Blocks.grass && this.isAir(world, i3, j4 + 1, k3) && random.nextInt(12) == 0) {
                        this.plantFlower(world, random, i3, j4 + 1, k3);
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int j5 = 2; j5 <= 4; ++j5) {
                this.setAir(world, i3, j5, -2);
            }
        }
        this.setBlockAndMetadata(world, -2, 2, -2, super.plankStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 4, -2, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 2, -2, super.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 4, -2, super.plankStairBlock, 5);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int j5 = 2; j5 <= 4; ++j5) {
                this.setAir(world, i3, j5, -1);
                this.setBlockAndMetadata(world, i3, j5, 0, super.brickBlock, super.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -1, 2, -1, super.plankStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 4, -1, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 2, -1, super.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 4, -1, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 1, -1, super.pathBlock, super.pathMeta);
        this.setBlockAndMetadata(world, 0, 1, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 2, 0, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 3, 0, super.doorBlock, 8);
        this.placeSign(world, 0, 4, -1, Blocks.wall_sign, 2, this.tavernNameSign);
        this.setBlockAndMetadata(world, -2, 3, -2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, -2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 4, -3, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 5, -3, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, -1, 5, -3, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 5, -3, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 5, -3, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 5, -3, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, 3, 4, -3, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        if (random.nextBoolean()) {
            this.placeSign(world, -2, 2, -10, Blocks.standing_sign, MathHelper.getRandomIntegerInRange(random, 7, 9), this.tavernNameSign);
        }
        else {
            this.placeSign(world, 2, 2, -10, Blocks.standing_sign, MathHelper.getRandomIntegerInRange(random, 7, 9), this.tavernNameSign);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -3, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -3, 6, -4, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -4, 6, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 6, -5, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 6, -4, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 4, 6, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 4, 6, -5, super.roofStairBlock, 1);
        for (int i3 = -11; i3 <= -5; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -5, super.roofStairBlock, 2);
        }
        for (int i3 = 5; i3 <= 11; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -5, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -11, 6, -6, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 11, 6, -6, super.roofStairBlock, 1);
        for (int i3 = -14; i3 <= -12; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -6, super.roofStairBlock, 2);
        }
        for (int i3 = 12; i3 <= 14; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -6, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -15, 6, -6, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -15, 6, -5, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -16, 6, -5, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -16, 6, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 15, 6, -6, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 15, 6, -5, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 16, 6, -5, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 16, 6, -4, super.roofStairBlock, 2);
        for (int k5 = -4; k5 <= 16; ++k5) {
            this.setBlockAndMetadata(world, -17, 6, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 17, 6, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -16, 6, 16, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -16, 6, 17, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -15, 6, 17, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -15, 6, 18, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 16, 6, 16, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 16, 6, 17, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 15, 6, 17, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 15, 6, 18, super.roofStairBlock, 0);
        for (int i3 = -14; i3 <= -11; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 18, super.roofStairBlock, 3);
        }
        for (int i3 = 11; i3 <= 14; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 18, super.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -11, 6, 19, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 11, 6, 19, super.roofStairBlock, 0);
        for (int i3 = -10; i3 <= 10; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 18, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 6, 19, super.roofStairBlock, 3);
            final int i5 = IntMath.mod(i3, 5);
            if (IntMath.mod(i3, 5) != 0) {
                this.setBlockAndMetadata(world, i3, 5, 18, super.plankStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 1, 18, super.brickBlock, super.brickMeta);
                this.setGrassToDirt(world, i3, 0, 18);
                if (i5 == 1 || i5 == 4) {
                    this.setBlockAndMetadata(world, i3, 2, 18, super.hedgeBlock, super.hedgeMeta);
                }
                else {
                    this.placeFlowerPot(world, i3, 2, 18, this.getRandomFlower(world, random));
                }
                if (!this.isOpaque(world, i3, 0, 18)) {
                    this.setBlockAndMetadata(world, i3, 0, 18, super.plankStairBlock, 7);
                }
            }
        }
        for (int i7 : new int[] { -15, 12 }) {
            int i6;
            for (i6 = i7; i7 <= i6 + 3; ++i7) {
                this.setBlockAndMetadata(world, i7, 11, 6, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i7, 11, 8, super.brickStairBlock, 3);
            }
            this.setBlockAndMetadata(world, i6, 11, 7, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i6 + 3, 11, 7, super.brickStairBlock, 0);
            for (i7 = i6 + 1; i7 <= i6 + 2; ++i7) {
                this.setBlockAndMetadata(world, i7, 11, 7, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i7, 12, 7, Blocks.flower_pot, 0);
            }
        }
        final int[] array2 = { -16, 16 };
        for (int length2 = array2.length, n2 = 0; n2 < length2; ++n2) {
            final int i6 = array2[n2];
            for (int k6 = 3; k6 <= 4; ++k6) {
                for (int j6 = 2; j6 <= 3; ++j6) {
                    this.setBlockAndMetadata(world, i6, j6, k6, LOTRMod.glassPane, 0);
                }
            }
        }
        final int[] array3 = { -17, 17 };
        for (int length3 = array3.length, n3 = 0; n3 < length3; ++n3) {
            final int i6 = array3[n3];
            for (int k6 = 2; k6 <= 10; ++k6) {
                if (k6 != 6) {
                    this.setBlockAndMetadata(world, i6, 1, k6, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i6, 0, k6);
                    if (k6 == 2 || k6 == 5 || k6 == 7 || k6 == 10) {
                        this.setBlockAndMetadata(world, i6, 2, k6, super.hedgeBlock, super.hedgeMeta);
                    }
                    else {
                        this.placeFlowerPot(world, i6, 2, k6, this.getRandomFlower(world, random));
                    }
                }
            }
            for (final int k7 : new int[] { 1, 6, 11 }) {
                for (int j7 = 5; (j7 >= 0 || !this.isOpaque(world, i6, j7, k7)) && this.getY(j7) >= 0; --j7) {
                    this.setBlockAndMetadata(world, i6, j7, k7, super.beamBlock, super.beamMeta);
                    this.setGrassToDirt(world, i6, j7, k7);
                }
            }
            for (int k6 = 1; k6 <= 11; ++k6) {
                this.setBlockAndMetadata(world, i6, 6, k6, super.roofBlock, super.roofMeta);
            }
        }
        for (int k5 = 2; k5 <= 10; ++k5) {
            if (k5 != 6) {
                if (!this.isOpaque(world, -17, 0, k5)) {
                    this.setBlockAndMetadata(world, -17, 0, k5, super.plankStairBlock, 5);
                }
                this.setBlockAndMetadata(world, -17, 5, k5, super.plankStairBlock, 5);
                if (!this.isOpaque(world, 17, 0, k5)) {
                    this.setBlockAndMetadata(world, 17, 0, k5, super.plankStairBlock, 4);
                }
                this.setBlockAndMetadata(world, 17, 5, k5, super.plankStairBlock, 4);
            }
        }
        for (int k5 = 7; k5 <= 10; ++k5) {
            this.setBlockAndMetadata(world, -17, 5, k5, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 17, 5, k5, super.plankStairBlock, 4);
        }
        for (int k5 = 1; k5 <= 11; ++k5) {
            this.setBlockAndMetadata(world, -18, 6, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 18, 6, k5, super.roofStairBlock, 0);
        }
        final int[] array5 = { -18, 18 };
        for (int length5 = array5.length, n5 = 0; n5 < length5; ++n5) {
            final int i6 = array5[n5];
            this.setBlockAndMetadata(world, i6, 6, 0, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i6, 6, 12, super.roofStairBlock, 3);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, -2, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -4, 7, -3, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -5, 7, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -5, 7, -4, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 7, -3, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 5, 7, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 5, 7, -4, super.roofStairBlock, 1);
        for (int i3 = -12; i3 <= -6; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, -4, super.roofStairBlock, 2);
        }
        for (int i3 = 6; i3 <= 12; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, -4, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -12, 7, -5, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -13, 7, -5, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -14, 7, -5, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -14, 7, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -15, 7, -4, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -15, 7, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 12, 7, -5, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 13, 7, -5, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 14, 7, -5, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 14, 7, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 15, 7, -4, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 15, 7, -3, super.roofStairBlock, 2);
        for (int k5 = -3; k5 <= 0; ++k5) {
            this.setBlockAndMetadata(world, -16, 7, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 16, 7, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -16, 7, 1, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 16, 7, 1, super.roofStairBlock, 2);
        for (int k5 = 1; k5 <= 11; ++k5) {
            this.setBlockAndMetadata(world, -17, 7, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 17, 7, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -16, 7, 11, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 16, 7, 11, super.roofStairBlock, 3);
        for (int k5 = 12; k5 <= 15; ++k5) {
            this.setBlockAndMetadata(world, -16, 7, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 16, 7, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -15, 7, 15, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -15, 7, 16, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -14, 7, 16, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -14, 7, 17, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 15, 7, 15, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 15, 7, 16, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 14, 7, 16, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 14, 7, 17, super.roofStairBlock, 0);
        for (int i3 = -13; i3 <= -11; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, 17, super.roofStairBlock, 3);
        }
        for (int i3 = 11; i3 <= 13; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, 17, super.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -10, 7, 17, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 10, 7, 17, super.roofStairBlock, 0);
        for (int i3 = -10; i3 <= 10; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, 18, super.roofStairBlock, 3);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -1, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -5, 8, -2, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -6, 8, -2, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -6, 8, -3, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 5, 8, -2, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 6, 8, -2, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 6, 8, -3, super.roofStairBlock, 1);
        for (int i3 = -13; i3 <= -7; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -3, super.roofStairBlock, 2);
        }
        for (int i3 = 7; i3 <= 13; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -3, super.roofStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -13, 8, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 13, 8, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -14, 8, -3, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -14, 8, -2, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 14, 8, -3, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 14, 8, -2, super.roofStairBlock, 2);
        for (int k5 = -2; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -15, 8, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 15, 8, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -15, 8, 2, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 15, 8, 2, super.roofStairBlock, 2);
        for (int k5 = 2; k5 <= 10; ++k5) {
            this.setBlockAndMetadata(world, -16, 8, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 16, 8, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -15, 8, 10, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 15, 8, 10, super.roofStairBlock, 3);
        for (int k5 = 11; k5 <= 14; ++k5) {
            this.setBlockAndMetadata(world, -15, 8, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 15, 8, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -14, 8, 14, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -14, 8, 15, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -13, 8, 15, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -13, 8, 16, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 14, 8, 14, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 14, 8, 15, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 13, 8, 15, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 13, 8, 16, super.roofStairBlock, 0);
        for (int i3 = -12; i3 <= -10; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, 16, super.roofStairBlock, 3);
        }
        for (int i3 = 10; i3 <= 12; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, 16, super.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -9, 8, 16, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 9, 8, 16, super.roofStairBlock, 0);
        for (int i3 = -9; i3 <= 9; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, 17, super.roofStairBlock, 3);
        }
        for (int i3 = -16; i3 <= 16; ++i3) {
            final int i5 = Math.abs(i3);
            for (int k4 = -4; k4 <= 17; ++k4) {
                boolean roof = false;
                if (k4 == -4) {
                    roof = (i5 == 13);
                }
                if (k4 == -3) {
                    roof = (i5 >= 6 && i5 <= 14);
                }
                if (k4 == -2) {
                    roof = (i5 >= 5 && i5 <= 15);
                }
                if (k4 >= -1 && k4 <= 1) {
                    roof = (i5 <= 15);
                }
                if (k4 >= 2 && k4 <= 10) {
                    roof = (i5 <= 16);
                }
                if (k4 >= 11 && k4 <= 14) {
                    roof = (i5 <= 15);
                }
                if (k4 == 15) {
                    roof = (i5 <= 14);
                }
                if (k4 == 16) {
                    roof = (i5 <= 13);
                }
                if (k4 == 17) {
                    roof = (i5 <= 9);
                }
                if (roof) {
                    this.setBlockAndMetadata(world, i3, 7, k4, super.roofBlock, super.roofMeta);
                }
            }
            for (int k4 = -2; k4 <= 16; ++k4) {
                boolean roof = false;
                if (k4 == -2) {
                    roof = (i5 >= 7 && i5 <= 13);
                }
                if (k4 == -1) {
                    roof = (i5 >= 6 && i5 <= 14);
                }
                if (k4 >= 0 && k4 <= 2) {
                    roof = (i5 <= 14);
                }
                if (k4 >= 3 && k4 <= 9) {
                    roof = (i5 <= 15);
                }
                if (k4 >= 10 && k4 <= 13) {
                    roof = (i5 <= 14);
                }
                if (k4 == 14) {
                    roof = (i5 <= 13);
                }
                if (k4 == 15) {
                    roof = (i5 <= 12);
                }
                if (k4 == 16) {
                    roof = (i5 <= 8);
                }
                if (roof) {
                    this.setBlockAndMetadata(world, i3, 8, k4, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i3, 9, k4, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            final int i5 = Math.abs(i3);
            for (int k4 = 1; k4 <= 15; ++k4) {
                boolean room = false;
                if (k4 == 1 && i5 <= 1) {
                    room = true;
                }
                if (k4 == 2 && i5 <= 2) {
                    room = true;
                }
                if (k4 == 3 && i5 <= 3) {
                    room = true;
                }
                if (k4 == 4 && i5 <= 4) {
                    room = true;
                }
                if (k4 == 5 && i5 <= 5) {
                    room = true;
                }
                if (k4 >= 6 && k4 <= 10 && i5 <= 6) {
                    room = true;
                }
                if (k4 >= 11 && k4 <= 12 && i5 <= 5) {
                    room = true;
                }
                if (k4 == 13 && i5 <= 4) {
                    room = true;
                }
                if (k4 >= 14 && k4 <= 15 && i5 <= 2) {
                    room = true;
                }
                if (room) {
                    this.setBlockAndMetadata(world, i3, 1, k4, super.floorBlock, super.floorMeta);
                    for (int j2 = 2; j2 <= 5; ++j2) {
                        this.setAir(world, i3, j2, k4);
                    }
                }
            }
            for (int j4 = 2; j4 <= 4; ++j4) {
                if (i5 == 2) {
                    this.setBlockAndMetadata(world, i3, j4, 1, super.brickBlock, super.brickMeta);
                }
                if (i5 == 4) {
                    this.setBlockAndMetadata(world, i3, j4, 3, super.beamBlock, super.beamMeta);
                }
                if (i5 == 6) {
                    this.setBlockAndMetadata(world, i3, j4, 5, super.beamBlock, super.beamMeta);
                    this.setBlockAndMetadata(world, i3, j4, 11, super.beamBlock, super.beamMeta);
                }
                if (i5 == 5) {
                    this.setBlockAndMetadata(world, i3, j4, 13, super.beamBlock, super.beamMeta);
                }
                if (i5 == 3) {
                    this.setBlockAndMetadata(world, i3, j4, 14, super.beamBlock, super.beamMeta);
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = 11; k3 <= 15; ++k3) {
                this.setBlockAndMetadata(world, i3, 5, k3, super.plankBlock, super.plankMeta);
            }
            this.setBlockAndMetadata(world, i3, 5, 10, super.plankStairBlock, 6);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 1, super.plankBlock, super.plankMeta);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 2, super.plankStairBlock, 7);
        }
        this.setBlockAndMetadata(world, -2, 5, 3, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 5, 3, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 5, 3, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 5, 3, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 5, 4, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 5, 4, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 5, 4, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 4, 5, 4, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 5, 5, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 4, 5, 5, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -5, 5, 5, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 5, 5, 5, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -5, 5, 6, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 5, 5, 6, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -6, 5, 6, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 6, 5, 6, super.plankStairBlock, 7);
        for (int k5 = 7; k5 <= 10; ++k5) {
            this.setBlockAndMetadata(world, -6, 5, k5, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 6, 5, k5, super.plankStairBlock, 5);
        }
        this.setBlockAndMetadata(world, 0, 4, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -6, 3, 6, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 6, 3, 6, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -6, 3, 10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 6, 3, 10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, 5, super.chandelierBlock, super.chandelierMeta);
        this.setBlockAndMetadata(world, -4, 5, 8, super.chandelierBlock, super.chandelierMeta);
        this.setBlockAndMetadata(world, 4, 5, 8, super.chandelierBlock, super.chandelierMeta);
        this.setBlockAndMetadata(world, -2, 3, 2, (Block)Blocks.tripwire_hook, 0);
        this.setBlockAndMetadata(world, 2, 3, 2, (Block)Blocks.tripwire_hook, 0);
        this.setBlockAndMetadata(world, -3, 3, 3, (Block)Blocks.tripwire_hook, 0);
        this.setBlockAndMetadata(world, 3, 3, 3, (Block)Blocks.tripwire_hook, 0);
        this.setBlockAndMetadata(world, -4, 3, 4, (Block)Blocks.tripwire_hook, 1);
        this.setBlockAndMetadata(world, 4, 3, 4, (Block)Blocks.tripwire_hook, 3);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 1; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 2, k3, super.carpetBlock, super.carpetMeta);
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = 5; k3 <= 7; ++k3) {
                this.setBlockAndMetadata(world, i3, 2, k3, super.carpetBlock, super.carpetMeta);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            final int i5 = Math.abs(i3);
            this.setBlockAndMetadata(world, i3, 2, 11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, i3, 4, 11, super.fence2Block, super.fence2Meta);
            if (IntMath.mod(i3, 2) == 1) {
                this.setBlockAndMetadata(world, i3, 2, 9, super.fence2Block, super.fence2Meta);
            }
            if (i5 == 2) {
                this.placeBarrel(world, random, i3, 3, 11, 3, LOTRFoods.HOBBIT_DRINK);
            }
            if (i5 == 1) {
                this.placeMug(world, random, i3, 3, 11, 0, LOTRFoods.HOBBIT_DRINK);
            }
        }
        for (int k5 = 12; k5 <= 13; ++k5) {
            for (final int i8 : new int[] { -3, 3 }) {
                this.setBlockAndMetadata(world, i8, 2, k5, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, i8, 4, k5, super.fence2Block, super.fence2Meta);
            }
        }
        this.setBlockAndMetadata(world, 3, 2, 12, super.fenceGate2Block, 1);
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, 15, super.plankStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -2, 4, 14, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 4, 14, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 2, 15, Blocks.crafting_table, 0);
        this.placeChest(world, random, -1, 2, 15, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.setBlockAndMetadata(world, 0, 2, 15, super.plankBlock, super.plankMeta);
        this.placeFlowerPot(world, 0, 3, 15, new ItemStack(LOTRMod.shireHeather));
        this.setBlockAndMetadata(world, 1, 2, 15, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 2, 2, 15, LOTRMod.hobbitOven, 2);
        final int[] array7 = { -7, 7 };
        for (int length7 = array7.length, n7 = 0; n7 < length7; ++n7) {
            final int i6 = array7[n7];
            this.setBlockAndMetadata(world, i6, 1, 8, super.floorBlock, super.floorMeta);
            this.setBlockAndMetadata(world, i6, 2, 8, super.carpetBlock, super.carpetMeta);
            this.setAir(world, i6, 3, 8);
            this.setBlockAndMetadata(world, i6, 2, 7, super.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i6, 3, 7, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 2, 9, super.plankStairBlock, 2);
            this.setBlockAndMetadata(world, i6, 3, 9, super.plankStairBlock, 6);
        }
        for (int k5 = 7; k5 <= 9; ++k5) {
            this.setBlockAndMetadata(world, -6, 2, k5, super.carpetBlock, super.carpetMeta);
            this.setBlockAndMetadata(world, -5, 2, k5, super.carpetBlock, super.carpetMeta);
            this.setBlockAndMetadata(world, 5, 2, k5, super.carpetBlock, super.carpetMeta);
            this.setBlockAndMetadata(world, 6, 2, k5, super.carpetBlock, super.carpetMeta);
        }
        for (int i3 = -15; i3 <= -8; ++i3) {
            for (int k3 = 3; k3 <= 14; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                for (int j4 = 1; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int i3 = -15; i3 <= -11; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                for (int j4 = 1; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int i3 = -10; i3 <= -5; ++i3) {
            for (int k3 = -2; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.floorBlock, super.floorMeta);
                for (int j4 = 2; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int j8 = 1; j8 <= 5; ++j8) {
            this.setBlockAndMetadata(world, -15, j8, 14, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -9, j8, 14, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -8, j8, 14, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -8, j8, 11, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -8, j8, 5, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -8, j8, 4, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -9, j8, 3, super.beamBlock, super.beamMeta);
        }
        this.setBlockAndMetadata(world, -8, 3, 6, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -8, 3, 10, Blocks.torch, 4);
        for (int k5 = 6; k5 <= 10; ++k5) {
            this.setBlockAndMetadata(world, -8, 1, k5, super.floorStairBlock, 1);
            this.setBlockAndMetadata(world, -8, 5, k5, super.plankStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -9, 1, 11, super.plank2Block, super.plank2Meta);
        for (int j8 = 2; j8 <= 4; ++j8) {
            this.setBlockAndMetadata(world, -9, j8, 11, super.fence2Block, super.fence2Meta);
        }
        this.setBlockAndMetadata(world, -9, 5, 11, super.plank2Block, super.plank2Meta);
        for (int k5 = 12; k5 <= 13; ++k5) {
            this.setBlockAndMetadata(world, -9, 1, k5, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -8, 1, k5, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -8, 2, k5, super.plankStairBlock, 5);
            this.placeFlowerPot(world, -8, 3, k5, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, -8, 4, k5, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, -8, 5, k5, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -9, 5, k5, super.plank2StairBlock, 5);
        }
        for (int i3 = -14; i3 <= -10; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 14, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 14, super.plank2StairBlock, 6);
        }
        for (int i3 = -13; i3 <= -11; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, 15, super.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 3, 15, LOTRMod.barrel, 2);
            this.setBlockAndMetadata(world, i3, 4, 15, super.plankStairBlock, 6);
        }
        for (int k5 = 9; k5 <= 13; ++k5) {
            this.setBlockAndMetadata(world, -15, 1, k5, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -15, 5, k5, super.plank2StairBlock, 4);
        }
        for (int k5 = 10; k5 <= 12; ++k5) {
            this.spawnItemFrame(world, -16, 3, k5, 1, this.getTavernFramedItem(random));
        }
        for (int i3 = -13; i3 <= -11; ++i3) {
            for (int k3 = 10; k3 <= 12; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.plank2Block, super.plank2Meta);
                this.placePlateOrMug(world, random, i3, 2, k3);
            }
        }
        this.setBlockAndMetadata(world, -12, 5, 11, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, -13, 5, 11, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, -11, 5, 11, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, -12, 5, 10, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, -12, 5, 12, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, -12, 4, 11, super.chandelierBlock, super.chandelierMeta);
        for (int i3 = -15; i3 <= -12; ++i3) {
            for (int k3 = 6; k3 <= 8; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.stonebrick, 0);
                for (int j4 = 2; j4 <= 4; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
                this.setBlockAndMetadata(world, i3, 5, k3, Blocks.stonebrick, 0);
                for (int j4 = 6; j4 <= 10; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int i3 = -14; i3 <= -13; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 7, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, i3, 1, 7, (Block)Blocks.fire, 0);
            for (int j5 = 2; j5 <= 10; ++j5) {
                this.setAir(world, i3, j5, 7);
            }
        }
        for (int j8 = 1; j8 <= 3; ++j8) {
            this.setBlockAndMetadata(world, -12, j8, 7, super.barsBlock, 0);
        }
        this.setBlockAndMetadata(world, -10, 5, 7, super.chandelierBlock, super.chandelierMeta);
        for (int k5 = 2; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, -15, 1, k5, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -15, 5, k5, super.plank2StairBlock, 4);
        }
        for (int j8 = 1; j8 <= 5; ++j8) {
            this.setBlockAndMetadata(world, -15, j8, 1, super.beamBlock, super.beamMeta);
        }
        this.setBlockAndMetadata(world, -14, 1, 1, super.plank2Block, super.plank2Meta);
        for (int j8 = 2; j8 <= 4; ++j8) {
            this.setBlockAndMetadata(world, -14, j8, 1, super.fence2Block, super.fence2Meta);
        }
        this.setBlockAndMetadata(world, -14, 5, 1, super.plank2Block, super.plank2Meta);
        for (int k5 = 3; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, -13, 1, k5, super.plank2Block, super.plank2Meta);
            this.placePlateOrMug(world, random, -13, 2, k5);
        }
        this.setBlockAndMetadata(world, -13, 5, 4, super.chandelierBlock, super.chandelierMeta);
        for (int k5 = -3; k5 <= 0; ++k5) {
            this.setBlockAndMetadata(world, -15, 1, k5, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -15, 5, k5, super.plank2StairBlock, 4);
        }
        for (int k5 = -2; k5 <= -1; ++k5) {
            for (int i9 = -13; i9 <= -12; ++i9) {
                this.setBlockAndMetadata(world, i9, 1, k5, super.plank2Block, super.plank2Meta);
                this.placePlateOrMug(world, random, i9, 2, k5);
            }
            this.spawnItemFrame(world, -16, 3, k5, 1, this.getTavernFramedItem(random));
        }
        for (int i3 = -14; i3 <= -12; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -4, super.plank2StairBlock, 3);
            for (int j5 = 2; j5 <= 4; ++j5) {
                this.setAir(world, i3, j5, -4);
            }
            this.setBlockAndMetadata(world, i3, 5, -4, super.plank2StairBlock, 7);
        }
        this.spawnItemFrame(world, -13, 3, -5, 0, this.getTavernFramedItem(random));
        this.setBlockAndMetadata(world, -12, 5, -1, super.chandelierBlock, super.chandelierMeta);
        for (int k5 = -1; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -10, 1, k5, super.floorStairBlock, 1);
        }
        this.setBlockAndMetadata(world, -10, 1, 3, super.floorStairBlock, 3);
        for (int j8 = 2; j8 <= 5; ++j8) {
            this.setBlockAndMetadata(world, -5, j8, 3, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -5, j8, -2, super.beamBlock, super.beamMeta);
        }
        for (int i3 = -8; i3 <= -6; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, 3, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 3, super.plank2StairBlock, 6);
        }
        this.setBlockAndMetadata(world, -7, 3, 4, LOTRMod.barrel, 2);
        this.setBlockAndMetadata(world, -7, 4, 4, super.plankStairBlock, 6);
        for (int k5 = -1; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -5, 2, k5, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -5, 5, k5, super.plank2StairBlock, 5);
        }
        this.setBlockAndMetadata(world, -4, 3, 2, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 4, 2, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 3, -1, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 4, -1, super.plankStairBlock, 7);
        this.placeFlowerPot(world, -4, 3, 1, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -4, 3, 0, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -4, 4, 1, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -4, 4, 0, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        for (int i3 = -9; i3 <= -6; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, -2, super.plank2StairBlock, 3);
            this.setBlockAndMetadata(world, i3, 5, -2, super.plank2StairBlock, 7);
        }
        this.setBlockAndMetadata(world, -10, 1, -2, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, -10, 2, -2, super.plank2Block, super.plank2Meta);
        for (int j8 = 3; j8 <= 4; ++j8) {
            this.setBlockAndMetadata(world, -10, j8, -2, super.fence2Block, super.fence2Meta);
        }
        this.setBlockAndMetadata(world, -10, 5, -2, super.plank2Block, super.plank2Meta);
        for (int i3 = -8; i3 <= -7; ++i3) {
            for (int k3 = 0; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, i3, 2, k3, super.plank2Block, super.plank2Meta);
                this.placePlateOrMug(world, random, i3, 3, k3);
            }
        }
        this.setBlockAndMetadata(world, -8, 5, 1, super.chandelierBlock, super.chandelierMeta);
        for (int i3 = 8; i3 <= 15; ++i3) {
            for (int k3 = 3; k3 <= 14; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                for (int j4 = 1; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int i3 = 11; i3 <= 15; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                for (int j4 = 1; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int i3 = 5; i3 <= 10; ++i3) {
            for (int k3 = -2; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.floorBlock, super.floorMeta);
                for (int j4 = 2; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int j8 = 1; j8 <= 5; ++j8) {
            this.setBlockAndMetadata(world, 15, j8, 14, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 9, j8, 14, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 8, j8, 14, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 8, j8, 11, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 8, j8, 5, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 8, j8, 4, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 9, j8, 3, super.beamBlock, super.beamMeta);
        }
        this.setBlockAndMetadata(world, 8, 3, 6, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 8, 3, 10, Blocks.torch, 4);
        for (int k5 = 6; k5 <= 10; ++k5) {
            this.setBlockAndMetadata(world, 8, 1, k5, super.floorStairBlock, 0);
            this.setBlockAndMetadata(world, 8, 5, k5, super.plankStairBlock, 4);
        }
        this.setBlockAndMetadata(world, 9, 1, 11, super.plank2Block, super.plank2Meta);
        for (int j8 = 2; j8 <= 4; ++j8) {
            this.setBlockAndMetadata(world, 9, j8, 11, super.fence2Block, super.fence2Meta);
        }
        this.setBlockAndMetadata(world, 9, 5, 11, super.plank2Block, super.plank2Meta);
        for (int k5 = 12; k5 <= 13; ++k5) {
            this.setBlockAndMetadata(world, 9, 1, k5, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 8, 1, k5, super.plankBlock, super.plankMeta);
            for (int j5 = 2; j5 <= 4; ++j5) {
                this.setBlockAndMetadata(world, 8, j5, k5, Blocks.bookshelf, 0);
            }
            this.setBlockAndMetadata(world, 8, 5, k5, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 9, 5, k5, super.plank2StairBlock, 4);
        }
        for (int i3 = 10; i3 <= 14; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 14, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 14, super.plank2StairBlock, 6);
        }
        for (int i3 = 10; i3 <= 14; ++i3) {
            for (int j5 = 2; j5 <= 4; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, 15, Blocks.bookshelf, 0);
            }
        }
        for (int k5 = 9; k5 <= 13; ++k5) {
            this.setBlockAndMetadata(world, 15, 1, k5, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 15, 5, k5, super.plank2StairBlock, 5);
        }
        this.placeWallBanner(world, 16, 4, 11, LOTRItemBanner.BannerType.HOBBIT, 3);
        for (int i3 = 11; i3 <= 13; ++i3) {
            for (int k3 = 10; k3 <= 12; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.plank2Block, super.plank2Meta);
                this.placePlateOrMug(world, random, i3, 2, k3);
            }
        }
        this.setBlockAndMetadata(world, 12, 5, 11, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, 13, 5, 11, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, 11, 5, 11, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, 12, 5, 10, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, 12, 5, 12, super.fence2Block, super.fence2Meta);
        this.setBlockAndMetadata(world, 12, 4, 11, super.chandelierBlock, super.chandelierMeta);
        for (int i3 = 12; i3 <= 15; ++i3) {
            for (int k3 = 6; k3 <= 8; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.stonebrick, 0);
                for (int j4 = 2; j4 <= 4; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
                this.setBlockAndMetadata(world, i3, 5, k3, Blocks.stonebrick, 0);
                for (int j4 = 6; j4 <= 10; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int i3 = 13; i3 <= 14; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 7, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, i3, 1, 7, (Block)Blocks.fire, 0);
            for (int j5 = 2; j5 <= 10; ++j5) {
                this.setAir(world, i3, j5, 7);
            }
        }
        for (int j8 = 1; j8 <= 3; ++j8) {
            this.setBlockAndMetadata(world, 12, j8, 7, super.barsBlock, 0);
        }
        this.setBlockAndMetadata(world, 10, 5, 7, super.chandelierBlock, super.chandelierMeta);
        for (int k5 = 2; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, 15, 1, k5, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 15, 5, k5, super.plank2StairBlock, 5);
        }
        for (int j8 = 1; j8 <= 5; ++j8) {
            this.setBlockAndMetadata(world, 15, j8, 1, super.beamBlock, super.beamMeta);
        }
        this.setBlockAndMetadata(world, 14, 1, 1, super.plank2Block, super.plank2Meta);
        for (int j8 = 2; j8 <= 4; ++j8) {
            this.setBlockAndMetadata(world, 14, j8, 1, super.fence2Block, super.fence2Meta);
        }
        this.setBlockAndMetadata(world, 14, 5, 1, super.plank2Block, super.plank2Meta);
        for (int k5 = 3; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, 13, 1, k5, super.plank2Block, super.plank2Meta);
            this.placePlateOrMug(world, random, 13, 2, k5);
        }
        this.setBlockAndMetadata(world, 13, 5, 4, super.chandelierBlock, super.chandelierMeta);
        for (int k5 = -3; k5 <= 0; ++k5) {
            this.setBlockAndMetadata(world, 15, 1, k5, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 15, 5, k5, super.plank2StairBlock, 5);
        }
        for (int k5 = -2; k5 <= -1; ++k5) {
            for (int i9 = 12; i9 <= 13; ++i9) {
                this.setBlockAndMetadata(world, i9, 1, k5, super.plank2Block, super.plank2Meta);
                this.placePlateOrMug(world, random, i9, 2, k5);
            }
        }
        this.placeWallBanner(world, 16, 4, -2, LOTRItemBanner.BannerType.HOBBIT, 3);
        for (int i3 = 12; i3 <= 14; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -4, super.plank2StairBlock, 3);
            for (int j5 = 2; j5 <= 4; ++j5) {
                this.setAir(world, i3, j5, -4);
            }
            this.setBlockAndMetadata(world, i3, 5, -4, super.plank2StairBlock, 7);
        }
        this.placeWallBanner(world, 13, 4, -5, LOTRItemBanner.BannerType.HOBBIT, 0);
        this.setBlockAndMetadata(world, 12, 5, -1, super.chandelierBlock, super.chandelierMeta);
        for (int k5 = -1; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, 10, 1, k5, super.floorStairBlock, 0);
        }
        this.setBlockAndMetadata(world, 10, 1, 3, super.floorStairBlock, 3);
        for (int j8 = 2; j8 <= 5; ++j8) {
            this.setBlockAndMetadata(world, 5, j8, 3, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 5, j8, -2, super.beamBlock, super.beamMeta);
        }
        for (int i3 = 6; i3 <= 8; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, 3, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 3, super.plank2StairBlock, 6);
        }
        this.placeWallBanner(world, 7, 4, 4, LOTRItemBanner.BannerType.HOBBIT, 2);
        for (int k5 = -1; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, 5, 2, k5, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 4, 3, k5, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, 4, 4, k5, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, 5, 5, k5, super.plank2StairBlock, 4);
        }
        for (int i3 = 6; i3 <= 9; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, -2, super.plank2StairBlock, 3);
            this.setBlockAndMetadata(world, i3, 5, -2, super.plank2StairBlock, 7);
        }
        this.setBlockAndMetadata(world, 10, 1, -2, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, 10, 2, -2, super.plank2Block, super.plank2Meta);
        for (int j8 = 3; j8 <= 4; ++j8) {
            this.setBlockAndMetadata(world, 10, j8, -2, super.fence2Block, super.fence2Meta);
        }
        this.setBlockAndMetadata(world, 10, 5, -2, super.plank2Block, super.plank2Meta);
        for (int i3 = 7; i3 <= 8; ++i3) {
            for (int k3 = 0; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, i3, 2, k3, super.plank2Block, super.plank2Meta);
                this.placePlateOrMug(world, random, i3, 3, k3);
            }
        }
        this.setBlockAndMetadata(world, 8, 5, 1, super.chandelierBlock, super.chandelierMeta);
        for (int i3 = -3; i3 <= 4; ++i3) {
            for (int k3 = 11; k3 <= 15; ++k3) {
                this.setBlockAndMetadata(world, i3, -4, k3, super.floorBlock, super.floorMeta);
                for (int j4 = -3; j4 <= 0; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int i3 = -3; i3 <= 4; ++i3) {
            for (final int k6 : new int[] { 10, 16 }) {
                this.setBlockAndMetadata(world, i3, -3, k6, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i3, -2, k6, super.beamBlock, super.beamMeta | 0x4);
                this.setBlockAndMetadata(world, i3, -1, k6, super.plankBlock, super.plankMeta);
            }
            for (int k3 = 11; k3 <= 13; ++k3) {
                if (i3 >= 0) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.beamBlock, super.beamMeta | 0x4);
                }
            }
            for (int k3 = 14; k3 <= 15; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.beamBlock, super.beamMeta | 0x4);
            }
        }
        for (int k5 = 11; k5 <= 15; ++k5) {
            for (final int i8 : new int[] { -4, 5 }) {
                this.setBlockAndMetadata(world, i8, -3, k5, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i8, -2, k5, super.beamBlock, super.beamMeta | 0x8);
                this.setBlockAndMetadata(world, i8, -1, k5, super.plankBlock, super.plankMeta);
            }
        }
        for (int j8 = -3; j8 <= -1; ++j8) {
            this.setBlockAndMetadata(world, -3, j8, 15, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 4, j8, 15, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 4, j8, 11, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 0, j8, 11, super.beamBlock, super.beamMeta);
        }
        this.placeBarrel(world, random, 4, -3, 14, 5, LOTRFoods.HOBBIT_DRINK);
        for (int k5 = 12; k5 <= 13; ++k5) {
            this.placeChest(world, random, 4, -3, k5, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
        }
        for (int k5 = 12; k5 <= 14; ++k5) {
            this.setBlockAndMetadata(world, 4, -2, k5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.placeBarrel(world, random, 4, -1, k5, 5, LOTRFoods.HOBBIT_DRINK);
        }
        this.placeBarrel(world, random, 1, -3, 11, 3, LOTRFoods.HOBBIT_DRINK);
        for (int i3 = 2; i3 <= 3; ++i3) {
            this.placeChest(world, random, i3, -3, 11, 3, LOTRChestContents.HOBBIT_HOLE_LARDER);
        }
        for (int i3 = 1; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, -2, 11, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            final Block cakeBlock = LOTRWorldGenHobbitStructure.getRandomCakeBlock(random);
            this.setBlockAndMetadata(world, i3, -1, 11, cakeBlock, 0);
        }
        for (int k5 = 11; k5 <= 13; ++k5) {
            this.setAir(world, -2, 1, k5);
            this.setAir(world, -3, 1, k5);
            this.setAir(world, -3, 0, k5);
        }
        for (int k5 = 10; k5 <= 12; ++k5) {
            this.setAir(world, -3, 0, k5);
        }
        this.setBlockAndMetadata(world, -3, 1, 14, super.floorBlock, super.floorMeta);
        for (int i3 = -3; i3 <= -1; ++i3) {
            for (int k3 = 11; k3 <= 12; ++k3) {
                for (int j4 = -3; j4 <= -1; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -2, 1 - step, 14 - step, super.floorStairBlock, 2);
        }
        for (int i3 = -3; i3 <= -2; ++i3) {
            this.setAir(world, i3, -1, 11);
            this.setBlockAndMetadata(world, i3, -2, 11, super.floorBlock, super.floorMeta);
        }
        this.setAir(world, -3, -1, 12);
        this.setBlockAndMetadata(world, -3, -2, 12, super.floorStairBlock, 3);
        for (int i3 = -2; i3 <= -1; ++i3) {
            this.setBlockAndMetadata(world, i3, -1, 13, super.floorStairBlock, 7);
        }
        for (int k5 = 13; k5 <= 14; ++k5) {
            this.setBlockAndMetadata(world, -3, -3, k5, super.floorBlock, super.floorMeta);
        }
        for (int k5 = 13; k5 <= 15; ++k5) {
            this.setBlockAndMetadata(world, -2, -3, k5, super.floorStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -2, -1, 15, Blocks.torch, 2);
        for (int k5 = 11; k5 <= 13; ++k5) {
            this.setBlockAndMetadata(world, -4, 0, k5, super.beamBlock, super.beamMeta | 0x8);
            this.setBlockAndMetadata(world, -1, 0, k5, super.beamBlock, super.beamMeta | 0x8);
        }
        for (int i3 = -3; i3 <= -2; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 10, super.beamBlock, super.beamMeta | 0x4);
        }
        final LOTREntityHobbitBartender bartender = new LOTREntityHobbitBartender(world);
        bartender.setSpecificLocationName(this.tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, 1, 2, 13, 2);
        for (final int i8 : new int[] { -10, 10 }) {
            final int j6 = 1;
            final int k8 = 7;
            for (int hobbits = 3 + random.nextInt(6), l = 0; l < hobbits; ++l) {
                final LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
                this.spawnNPCAndSetHome(hobbit, world, i8, j6, k8, 16);
            }
            if (random.nextInt(4) == 0) {
                final LOTREntityHobbit shirriffChief = new LOTREntityHobbitShirriffChief(world);
                shirriffChief.spawnRidingHorse = false;
                this.spawnNPCAndSetHome(shirriffChief, world, i8, j6, k8, 16);
            }
        }
        this.placeSign(world, -8, 4, 8, Blocks.wall_sign, 5, LOTRNames.getHobbitTavernQuote(random));
        this.placeSign(world, 8, 4, 8, Blocks.wall_sign, 4, LOTRNames.getHobbitTavernQuote(random));
        return true;
    }
    
    private void placePlateOrMug(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
        }
        else {
            this.placePlate(world, random, i, j, k, super.plateBlock, LOTRFoods.HOBBIT);
        }
    }
    
    private ItemStack getTavernFramedItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.leatherHat), LOTRItemLeatherHat.setFeatherColor(new ItemStack(LOTRMod.leatherHat), 16777215), LOTRItemLeatherHat.setHatColor(new ItemStack(LOTRMod.leatherHat), 2301981), LOTRItemLeatherHat.setFeatherColor(LOTRItemLeatherHat.setHatColor(new ItemStack(LOTRMod.leatherHat), 2301981), 3381529), new ItemStack(LOTRMod.hobbitPipe), new ItemStack(Items.book), new ItemStack(Items.feather), new ItemStack(Items.wooden_sword), new ItemStack((Item)Items.bow), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.glass_bottle), new ItemStack(Items.arrow), new ItemStack(LOTRMod.shireHeather), new ItemStack(LOTRMod.bluebell), new ItemStack((Block)Blocks.yellow_flower, 1, 0), new ItemStack((Block)Blocks.red_flower, 1, 0), new ItemStack((Block)Blocks.red_flower, 1, 3) };
        return items[random.nextInt(items.length)].copy();
    }
}
