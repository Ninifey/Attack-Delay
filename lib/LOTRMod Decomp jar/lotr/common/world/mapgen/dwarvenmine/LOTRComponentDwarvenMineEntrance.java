// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.structure2.LOTRWorldGenDwarvenMineEntrance;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineEntrance extends StructureComponent
{
    private int posX;
    private int posY;
    private int posZ;
    private static LOTRWorldGenDwarvenMineEntrance entranceGen;
    private int direction;
    private boolean ruined;
    
    public LOTRComponentDwarvenMineEntrance() {
        this.posY = -1;
    }
    
    public LOTRComponentDwarvenMineEntrance(final World world, final int l, final Random random, final int i, final int k, final boolean r) {
        super(l);
        this.posY = -1;
        super.boundingBox = new StructureBoundingBox(i - 4, 40, k - 4, i + 4, 256, k + 4);
        this.posX = i;
        this.posZ = k;
        this.ruined = r;
    }
    
    protected void func_143012_a(final NBTTagCompound nbt) {
        nbt.setInteger("EntranceX", this.posX);
        nbt.setInteger("EntranceY", this.posY);
        nbt.setInteger("EntranceZ", this.posZ);
        nbt.setInteger("Direction", this.direction);
        nbt.setBoolean("Ruined", this.ruined);
    }
    
    protected void func_143011_b(final NBTTagCompound nbt) {
        this.posX = nbt.getInteger("EntranceX");
        this.posY = nbt.getInteger("EntranceY");
        this.posZ = nbt.getInteger("EntranceZ");
        this.direction = nbt.getInteger("Direction");
        this.ruined = nbt.getBoolean("Ruined");
    }
    
    public void buildComponent(final StructureComponent component, final List list, final Random random) {
        StructureBoundingBox structureBoundingBox = null;
        switch (this.direction = random.nextInt(4)) {
            case 0: {
                structureBoundingBox = new StructureBoundingBox(this.posX - 1, super.boundingBox.minY + 1, this.posZ + 4, this.posX + 1, super.boundingBox.minY + 4, this.posZ + 15);
                break;
            }
            case 1: {
                structureBoundingBox = new StructureBoundingBox(this.posX - 15, super.boundingBox.minY + 1, this.posZ - 1, this.posX - 4, super.boundingBox.minY + 4, this.posZ + 1);
                break;
            }
            case 2: {
                structureBoundingBox = new StructureBoundingBox(this.posX - 1, super.boundingBox.minY + 1, this.posZ - 15, this.posX + 1, super.boundingBox.minY + 4, this.posZ - 4);
                break;
            }
            case 3: {
                structureBoundingBox = new StructureBoundingBox(this.posX + 4, super.boundingBox.minY + 1, this.posZ - 1, this.posX + 15, super.boundingBox.minY + 4, this.posZ + 1);
                break;
            }
        }
        final LOTRComponentDwarvenMineCorridor corridor = new LOTRComponentDwarvenMineCorridor(0, random, structureBoundingBox, this.direction, this.ruined);
        list.add(corridor);
        corridor.buildComponent(component, list, random);
    }
    
    public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.posY == -1) {
            this.posY = world.getTopSolidOrLiquidBlock(this.posX, this.posZ);
        }
        final Block block = world.getBlock(this.posX, this.posY - 1, this.posZ);
        if (block != Blocks.grass) {
            return false;
        }
        LOTRComponentDwarvenMineEntrance.entranceGen.isRuined = this.ruined;
        LOTRComponentDwarvenMineEntrance.entranceGen.generateWithSetRotation(world, random, this.posX, this.posY, this.posZ, this.direction);
        return true;
    }
    
    static {
        LOTRComponentDwarvenMineEntrance.entranceGen = new LOTRWorldGenDwarvenMineEntrance(false);
        LOTRComponentDwarvenMineEntrance.entranceGen.restrictions = false;
    }
}
