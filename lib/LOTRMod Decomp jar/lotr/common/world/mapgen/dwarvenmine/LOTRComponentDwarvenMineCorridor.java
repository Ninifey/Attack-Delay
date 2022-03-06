// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import java.util.Random;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineCorridor extends StructureComponent
{
    private int sectionCount;
    private boolean ruined;
    
    public LOTRComponentDwarvenMineCorridor() {
    }
    
    public LOTRComponentDwarvenMineCorridor(final int i, final Random random, final StructureBoundingBox structureBoundingBox, final int j, final boolean r) {
        super(i);
        super.coordBaseMode = j;
        super.boundingBox = structureBoundingBox;
        if (super.coordBaseMode != 2 && super.coordBaseMode != 0) {
            this.sectionCount = super.boundingBox.getXSize() / 4;
        }
        else {
            this.sectionCount = super.boundingBox.getZSize() / 4;
        }
        this.ruined = r;
    }
    
    protected void func_143012_a(final NBTTagCompound nbt) {
        nbt.setInteger("Sections", this.sectionCount);
        nbt.setBoolean("Ruined", this.ruined);
    }
    
    protected void func_143011_b(final NBTTagCompound nbt) {
        this.sectionCount = nbt.getInteger("Sections");
        this.ruined = nbt.getBoolean("Ruined");
    }
    
    public static StructureBoundingBox findValidPlacement(final List list, final Random random, final int i, final int j, final int k, final int l) {
        final StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 3, k);
        int i2;
        for (i2 = random.nextInt(3) + 2; i2 > 0; --i2) {
            final int j2 = i2 * 4;
            switch (l) {
                case 0: {
                    structureboundingbox.maxX = i + 2;
                    structureboundingbox.maxZ = k + (j2 - 1);
                    break;
                }
                case 1: {
                    structureboundingbox.minX = i - (j2 - 1);
                    structureboundingbox.maxZ = k + 2;
                    break;
                }
                case 2: {
                    structureboundingbox.maxX = i + 2;
                    structureboundingbox.minZ = k - (j2 - 1);
                    break;
                }
                case 3: {
                    structureboundingbox.maxX = i + (j2 - 1);
                    structureboundingbox.maxZ = k + 2;
                    break;
                }
            }
            if (StructureComponent.findIntersecting(list, structureboundingbox) == null) {
                break;
            }
        }
        return (i2 > 0) ? structureboundingbox : null;
    }
    
    public void buildComponent(final StructureComponent component, final List list, final Random random) {
        final int i = this.getComponentType();
        final int j = random.nextInt(4);
        switch (super.coordBaseMode) {
            case 0: {
                if (j <= 1) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.maxZ + 1, super.coordBaseMode, i, this.ruined);
                    break;
                }
                if (j == 2) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.maxZ - 3, 1, i, this.ruined);
                    break;
                }
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.maxZ - 3, 3, i, this.ruined);
                break;
            }
            case 1: {
                if (j <= 1) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ, super.coordBaseMode, i, this.ruined);
                    break;
                }
                if (j == 2) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ - 1, 2, i, this.ruined);
                    break;
                }
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.maxZ + 1, 0, i, this.ruined);
                break;
            }
            case 2: {
                if (j <= 1) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ - 1, super.coordBaseMode, i, this.ruined);
                    break;
                }
                if (j == 2) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ, 1, i, this.ruined);
                    break;
                }
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ, 3, i, this.ruined);
                break;
            }
            case 3: {
                if (j <= 1) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ, super.coordBaseMode, i, this.ruined);
                    break;
                }
                if (j == 2) {
                    LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX - 3, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.minZ - 1, 2, i, this.ruined);
                    break;
                }
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX - 3, super.boundingBox.minY - 1 + random.nextInt(3), super.boundingBox.maxZ + 1, 0, i, this.ruined);
                break;
            }
        }
        if (i < 12) {
            if (super.coordBaseMode != 2 && super.coordBaseMode != 0) {
                for (int k = super.boundingBox.minX + 3; k + 3 <= super.boundingBox.maxX; k += 4) {
                    final int l = random.nextInt(5);
                    if (l == 0) {
                        LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, k, super.boundingBox.minY, super.boundingBox.minZ - 1, 2, i + 1, this.ruined);
                    }
                    else if (l == 1) {
                        LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, k, super.boundingBox.minY, super.boundingBox.maxZ + 1, 0, i + 1, this.ruined);
                    }
                }
            }
            else {
                for (int k = super.boundingBox.minZ + 3; k + 3 <= super.boundingBox.maxZ; k += 4) {
                    final int l = random.nextInt(5);
                    if (l == 0) {
                        LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY, k, 1, i + 1, this.ruined);
                    }
                    else if (l == 1) {
                        LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY, k, 3, i + 1, this.ruined);
                    }
                }
            }
        }
    }
    
    public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }
        final int length = this.sectionCount * 4 - 1;
        this.func_151549_a(world, structureBoundingBox, 0, 0, 0, 2, 2, length, Blocks.air, Blocks.air, false);
        for (int l = 0; l < this.sectionCount; ++l) {
            final int k = 2 + l * 4;
            for (final int i : new int[] { 0, 2 }) {
                for (int wallHeight = this.ruined ? random.nextInt(3) : 2, j = 0; j <= wallHeight; ++j) {
                    this.func_151550_a(world, LOTRMod.wall, 7, i, j, k, structureBoundingBox);
                }
            }
            this.func_151549_a(world, structureBoundingBox, -1, 0, k, -1, 2, k, LOTRMod.pillar, Blocks.air, false);
            this.func_151549_a(world, structureBoundingBox, 3, 0, k, 3, 2, k, LOTRMod.pillar, Blocks.air, false);
            this.func_151549_a(world, structureBoundingBox, 1, -1, k - 2, 1, -1, k + 2, LOTRMod.pillar, Blocks.air, false);
            if (this.func_151548_a(world, 1, -1, k - 3, structureBoundingBox) != Blocks.air) {
                this.func_151550_a(world, LOTRMod.pillar, 0, 1, -1, k - 3, structureBoundingBox);
            }
            if (this.func_151548_a(world, 1, -1, k + 3, structureBoundingBox) != Blocks.air) {
                this.func_151550_a(world, LOTRMod.pillar, 0, 1, -1, k + 3, structureBoundingBox);
            }
            if (!this.ruined) {
                this.func_151550_a(world, LOTRMod.brick3, 12, 1, -1, k, structureBoundingBox);
                if (random.nextInt(80) == 0) {
                    this.func_151550_a(world, Blocks.crafting_table, 0, 2, 0, k - 1, structureBoundingBox);
                }
                if (random.nextInt(80) == 0) {
                    this.func_151550_a(world, Blocks.crafting_table, 0, 0, 0, k + 1, structureBoundingBox);
                }
            }
            if (random.nextInt(120) == 0) {
                this.generateStructureChestContents(world, structureBoundingBox, random, 2, 0, k - 1, LOTRChestContents.DWARVEN_MINE_CORRIDOR.items, LOTRChestContents.getRandomItemAmount(LOTRChestContents.DWARVEN_MINE_CORRIDOR, random));
            }
            if (random.nextInt(120) == 0) {
                this.generateStructureChestContents(world, structureBoundingBox, random, 0, 0, k + 1, LOTRChestContents.DWARVEN_MINE_CORRIDOR.items, LOTRChestContents.getRandomItemAmount(LOTRChestContents.DWARVEN_MINE_CORRIDOR, random));
            }
        }
        for (int m = 0; m <= length; ++m) {
            for (int i2 = -1; i2 <= 3; ++i2) {
                Block block = this.func_151548_a(world, i2, -1, m, structureBoundingBox);
                if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
                    this.func_151550_a(world, Blocks.stone, 0, i2, -1, m, structureBoundingBox);
                }
                final int j2 = 3;
                block = this.func_151548_a(world, i2, j2, m, structureBoundingBox);
                if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
                    this.func_151550_a(world, Blocks.stone, 0, i2, j2, m, structureBoundingBox);
                }
            }
            for (int j3 = 0; j3 <= 2; ++j3) {
                Block block = this.func_151548_a(world, -1, j3, m, structureBoundingBox);
                if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
                    this.func_151550_a(world, Blocks.stone, 0, -1, j3, m, structureBoundingBox);
                }
                block = this.func_151548_a(world, 3, j3, m, structureBoundingBox);
                if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
                    this.func_151550_a(world, Blocks.stone, 0, 3, j3, m, structureBoundingBox);
                }
            }
        }
        return true;
    }
}
