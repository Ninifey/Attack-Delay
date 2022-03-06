// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import java.util.Random;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineStairs extends StructureComponent
{
    private boolean ruined;
    
    public LOTRComponentDwarvenMineStairs() {
    }
    
    public LOTRComponentDwarvenMineStairs(final int i, final Random random, final StructureBoundingBox structureBoundingBox, final int j, final boolean r) {
        super(i);
        super.coordBaseMode = j;
        super.boundingBox = structureBoundingBox;
        this.ruined = r;
    }
    
    protected void func_143012_a(final NBTTagCompound nbt) {
        nbt.setBoolean("Ruined", this.ruined);
    }
    
    protected void func_143011_b(final NBTTagCompound nbt) {
        this.ruined = nbt.getBoolean("Ruined");
    }
    
    public static StructureBoundingBox findValidPlacement(final List list, final Random random, final int i, final int j, final int k, final int l) {
        final StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j - 5, k, i, j + 2, k);
        switch (l) {
            case 0: {
                structureboundingbox.maxX = i + 2;
                structureboundingbox.maxZ = k + 8;
                break;
            }
            case 1: {
                structureboundingbox.minX = i - 8;
                structureboundingbox.maxZ = k + 2;
                break;
            }
            case 2: {
                structureboundingbox.maxX = i + 2;
                structureboundingbox.minZ = k - 8;
                break;
            }
            case 3: {
                structureboundingbox.maxX = i + 8;
                structureboundingbox.maxZ = k + 2;
                break;
            }
        }
        return (StructureComponent.findIntersecting(list, structureboundingbox) != null) ? null : structureboundingbox;
    }
    
    public void buildComponent(final StructureComponent component, final List list, final Random random) {
        final int i = this.getComponentType();
        switch (super.coordBaseMode) {
            case 0: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX, super.boundingBox.minY, super.boundingBox.maxZ + 1, 0, i, this.ruined);
                break;
            }
            case 1: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ, 1, i, this.ruined);
                break;
            }
            case 2: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.minX, super.boundingBox.minY, super.boundingBox.minZ - 1, 2, i, this.ruined);
                break;
            }
            case 3: {
                LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ, 3, i, this.ruined);
                break;
            }
        }
    }
    
    public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }
        this.func_151549_a(world, structureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.air, Blocks.air, false);
        this.func_151549_a(world, structureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.air, Blocks.air, false);
        for (int i = 0; i < 5; ++i) {
            this.func_151549_a(world, structureBoundingBox, 0, 5 - i - ((i < 4) ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.air, Blocks.air, false);
        }
        return true;
    }
}
