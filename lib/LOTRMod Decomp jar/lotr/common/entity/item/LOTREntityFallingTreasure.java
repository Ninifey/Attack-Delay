// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.block.material.Material;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.Entity;

public class LOTREntityFallingTreasure extends Entity implements IEntityAdditionalSpawnData
{
    public Block theBlock;
    public int theBlockMeta;
    private int ticksFalling;
    
    public LOTREntityFallingTreasure(final World world) {
        super(world);
    }
    
    public LOTREntityFallingTreasure(final World world, final double d, final double d1, final double d2, final Block block) {
        this(world, d, d1, d2, block, 0);
    }
    
    public LOTREntityFallingTreasure(final World world, final double d, final double d1, final double d2, final Block block, final int meta) {
        super(world);
        this.blockMetaConstructor(d, d1, d2, block, meta);
    }
    
    private void blockMetaConstructor(final double d, final double d1, final double d2, final Block block, final int meta) {
        this.theBlock = block;
        this.theBlockMeta = meta;
        super.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        super.yOffset = super.height / 2.0f;
        this.setPosition(d, d1, d2);
        super.motionX = 0.0;
        super.motionY = 0.0;
        super.motionZ = 0.0;
        super.prevPosX = d;
        super.prevPosY = d1;
        super.prevPosZ = d2;
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeDouble(super.prevPosX);
        data.writeDouble(super.prevPosY);
        data.writeDouble(super.prevPosZ);
        data.writeInt(Block.getIdFromBlock(this.theBlock));
        data.writeByte(this.theBlockMeta);
    }
    
    public void readSpawnData(final ByteBuf data) {
        final double x = data.readDouble();
        final double y = data.readDouble();
        final double z = data.readDouble();
        final Block block = Block.getBlockById(data.readInt());
        final int meta = data.readByte();
        this.blockMetaConstructor(x, y, z, block, meta);
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    protected void entityInit() {
    }
    
    public boolean canBeCollidedWith() {
        return !super.isDead;
    }
    
    public void onUpdate() {
        if (this.theBlock.getMaterial() == Material.air) {
            this.setDead();
        }
        else {
            super.prevPosX = super.posX;
            super.prevPosY = super.posY;
            super.prevPosZ = super.posZ;
            ++this.ticksFalling;
            super.motionY -= 0.04;
            this.moveEntity(super.motionX, super.motionY, super.motionZ);
            super.motionX *= 0.98;
            super.motionY *= 0.98;
            super.motionZ *= 0.98;
            if (!super.worldObj.isClient) {
                final int i = MathHelper.floor_double(super.posX);
                int j = MathHelper.floor_double(super.posY);
                final int k = MathHelper.floor_double(super.posZ);
                final Block block = super.worldObj.getBlock(i, j, k);
                int meta = super.worldObj.getBlockMetadata(i, j, k);
                if (this.ticksFalling == 1) {
                    if (block != this.theBlock) {
                        this.setDead();
                        return;
                    }
                    super.worldObj.setBlockToAir(i, j, k);
                }
                if (super.onGround) {
                    super.motionX *= 0.7;
                    super.motionZ *= 0.7;
                    super.motionY *= -0.5;
                    if (block != Blocks.piston_extension) {
                        this.setDead();
                        boolean placedTreasure = false;
                        if (block == this.theBlock && meta < 7) {
                            while (this.theBlockMeta >= 0 && meta < 7) {
                                --this.theBlockMeta;
                                ++meta;
                            }
                            super.worldObj.setBlockMetadata(i, j, k, meta, 3);
                            placedTreasure = true;
                            ++j;
                        }
                        if (this.theBlockMeta >= 0) {
                            if (super.worldObj.canPlaceEntityOnSide(this.theBlock, i, j, k, true, 1, (Entity)null, (ItemStack)null) && super.worldObj.setBlock(i, j, k, this.theBlock, this.theBlockMeta, 3)) {
                                placedTreasure = true;
                            }
                            else {
                                this.entityDropItem(new ItemStack(this.theBlock, this.theBlock.quantityDropped(this.theBlockMeta, 0, super.rand), this.theBlock.damageDropped(this.theBlockMeta)), 0.0f);
                            }
                        }
                        if (placedTreasure) {
                            final Block.SoundType stepSound = this.theBlock.stepSound;
                            super.worldObj.playSoundEffect((double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), stepSound.func_150496_b(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getFrequency() * 0.8f);
                        }
                    }
                }
                else if (this.ticksFalling > 100 && !super.worldObj.isClient && (j < 1 || j > 256 || this.ticksFalling > 600)) {
                    this.entityDropItem(new ItemStack(this.theBlock, this.theBlock.quantityDropped(this.theBlockMeta, 0, super.rand), this.theBlock.damageDropped(this.theBlockMeta)), 0.0f);
                    this.setDead();
                }
            }
        }
    }
    
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("TileID", Block.getIdFromBlock(this.theBlock));
        nbt.setByte("Data", (byte)this.theBlockMeta);
        nbt.setByte("Time", (byte)this.ticksFalling);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
        this.theBlock = Block.getBlockById(nbt.getInteger("TileID"));
        this.theBlockMeta = (nbt.getByte("Data") & 0xFF);
        this.ticksFalling = (nbt.getByte("Time") & 0xFF);
        if (this.theBlock.getMaterial() == Material.air) {
            this.theBlock = (Block)Blocks.sand;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }
}
