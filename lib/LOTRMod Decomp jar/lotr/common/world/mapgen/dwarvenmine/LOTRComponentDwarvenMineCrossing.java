// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import java.util.Random;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineCrossing extends StructureComponent
{
    private int corridorDirection;
    private boolean isMultipleFloors;
    private boolean ruined;
    
    public LOTRComponentDwarvenMineCrossing() {
    }
    
    public LOTRComponentDwarvenMineCrossing(final int i, final Random random, final StructureBoundingBox structureBoundingBox, final int j, final boolean r) {
        super(i);
        this.corridorDirection = j;
        super.boundingBox = structureBoundingBox;
        this.isMultipleFloors = (super.boundingBox.getYSize() > 3);
        this.ruined = r;
    }
    
    protected void func_143012_a(final NBTTagCompound nbt) {
        nbt.setInteger("Direction", this.corridorDirection);
        nbt.setBoolean("Multiple", this.isMultipleFloors);
        nbt.setBoolean("Ruined", this.ruined);
    }
    
    protected void func_143011_b(final NBTTagCompound nbt) {
        this.corridorDirection = nbt.getInteger("Direction");
        this.isMultipleFloors = nbt.getBoolean("Multiple");
        this.ruined = nbt.getBoolean("Ruined");
    }
    
    public static StructureBoundingBox findValidPlacement(final List list, final Random random, final int i, final int j, final int k, final int l) {
        final StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 2, k);
        if (random.nextInt(4) == 0) {
            final StructureBoundingBox structureBoundingBox = structureboundingbox;
            structureBoundingBox.maxY += 4;
        }
        switch (l) {
            case 0: {
                structureboundingbox.minX = i - 1;
                structureboundingbox.maxX = i + 3;
                structureboundingbox.maxZ = k + 4;
                break;
            }
            case 1: {
                structureboundingbox.minX = i - 4;
                structureboundingbox.minZ = k - 1;
                structureboundingbox.maxZ = k + 3;
                break;
            }
            case 2: {
                structureboundingbox.minX = i - 1;
                structureboundingbox.maxX = i + 3;
                structureboundingbox.minZ = k - 4;
                break;
            }
            case 3: {
                structureboundingbox.maxX = i + 4;
                structureboundingbox.minZ = k - 1;
                structureboundingbox.maxZ = k + 3;
                break;
            }
        }
        return (StructureComponent.findIntersecting(list, structureboundingbox) != null) ? null : structureboundingbox;
    }
    
    public void buildComponent(final StructureComponent component, final List list, final Random random) {
        final int i = this.getComponentType();
        switch (this.corridorDirection) {
            case 0: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ + 1, 0, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, 1, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, 3, i, this.ruined);
                break;
            }
            case 1: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ - 1, 2, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ + 1, 0, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, 1, i, this.ruined);
                break;
            }
            case 2: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ - 1, 2, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, 1, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, 3, i, this.ruined);
                break;
            }
            case 3: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ - 1, 2, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ + 1, 0, i, this.ruined);
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, 3, i, this.ruined);
                break;
            }
        }
        if (this.isMultipleFloors) {
            if (random.nextBoolean()) {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY + 3 + 1, super.boundingBox.minZ - 1, 2, i, this.ruined);
            }
            if (random.nextBoolean()) {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY + 3 + 1, super.boundingBox.minZ + 1, 1, i, this.ruined);
            }
            if (random.nextBoolean()) {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY + 3 + 1, super.boundingBox.minZ + 1, 3, i, this.ruined);
            }
            if (random.nextBoolean()) {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX + 1, super.boundingBox.minY + 3 + 1, super.boundingBox.maxZ + 1, 0, i, this.ruined);
            }
        }
    }
    
    public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ, super.boundingBox.maxX - 1, super.boundingBox.maxY, super.boundingBox.maxZ, Blocks.air, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.minX, super.boundingBox.minY, super.boundingBox.minZ + 1, super.boundingBox.maxX, super.boundingBox.maxY, super.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, super.boundingBox.minX + 1, super.boundingBox.maxY, super.boundingBox.minZ + 1, LOTRMod.pillar, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ - 1, super.boundingBox.minX + 1, super.boundingBox.maxY, super.boundingBox.maxZ - 1, LOTRMod.pillar, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.maxX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, super.boundingBox.maxX - 1, super.boundingBox.maxY, super.boundingBox.minZ + 1, LOTRMod.pillar, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.maxX - 1, super.boundingBox.minY, super.boundingBox.maxZ - 1, super.boundingBox.maxX - 1, super.boundingBox.maxY, super.boundingBox.maxZ - 1, LOTRMod.pillar, Blocks.air, false);
        for (int i = super.boundingBox.minX; i <= super.boundingBox.maxX; ++i) {
            for (int j = super.boundingBox.minZ; j <= super.boundingBox.maxZ; ++j) {
                Block block = this.func_151548_a(world, i, super.boundingBox.minY - 1, j, structureBoundingBox);
                if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
                    this.func_151550_a(world, Blocks.stone, 0, i, super.boundingBox.minY - 1, j, structureBoundingBox);
                }
                block = this.func_151548_a(world, i, super.boundingBox.maxY + 1, j, structureBoundingBox);
                if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
                    this.func_151550_a(world, Blocks.stone, 0, i, super.boundingBox.maxY + 1, j, structureBoundingBox);
                }
            }
        }
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.minX + 2, super.boundingBox.minY - 1, super.boundingBox.minZ - 1, super.boundingBox.minX + 2, super.boundingBox.minY - 1, super.boundingBox.maxZ + 1, LOTRMod.pillar, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, super.boundingBox.minX - 1, super.boundingBox.minY - 1, super.boundingBox.minZ + 2, super.boundingBox.maxX + 1, super.boundingBox.minY - 1, super.boundingBox.minZ + 2, LOTRMod.pillar, Blocks.air, false);
        if (!this.ruined) {
            this.func_151550_a(world, LOTRMod.brick3, 12, super.boundingBox.minX + 2, super.boundingBox.minY - 1, super.boundingBox.minZ + 2, structureBoundingBox);
        }
        return true;
    }
}
