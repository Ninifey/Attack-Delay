// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.item.EntityFallingBlock;

public class LOTREntityFallingFireJar extends EntityFallingBlock implements IEntityAdditionalSpawnData
{
    public LOTREntityFallingFireJar(final World world) {
        super(world);
    }
    
    public LOTREntityFallingFireJar(final World world, final double d, final double d1, final double d2, final Block block) {
        super(world, d, d1, d2, block);
    }
    
    public LOTREntityFallingFireJar(final World world, final double d, final double d1, final double d2, final Block block, final int meta) {
        super(world, d, d1, d2, block, meta);
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeDouble(((Entity)this).prevPosX);
        data.writeDouble(((Entity)this).prevPosY);
        data.writeDouble(((Entity)this).prevPosZ);
        data.writeInt(Block.getIdFromBlock(this.func_145805_f()));
        data.writeByte(super.field_145814_a);
    }
    
    public void readSpawnData(final ByteBuf data) {
        final double x = data.readDouble();
        final double y = data.readDouble();
        final double z = data.readDouble();
        final Block block = Block.getBlockById(data.readInt());
        final int meta = data.readByte();
        final Entity proxy = (Entity)new EntityFallingBlock(((Entity)this).worldObj, x, y, z, block, meta);
        final NBTTagCompound nbt = new NBTTagCompound();
        proxy.writeToNBT(nbt);
        this.readFromNBT(nbt);
    }
}
