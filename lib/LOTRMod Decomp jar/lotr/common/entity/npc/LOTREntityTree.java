// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public abstract class LOTREntityTree extends LOTREntityNPC
{
    public static Block[] WOOD_BLOCKS;
    public static Block[] LEAF_BLOCKS;
    public static Block[] SAPLING_BLOCKS;
    public static int[] WOOD_META;
    public static int[] LEAF_META;
    public static int[] SAPLING_META;
    public static String[] TYPES;
    
    public LOTREntityTree(final World world) {
        super(world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
        if (((Entity)this).rand.nextInt(9) == 0) {
            this.setTreeType(2);
        }
        else if (((Entity)this).rand.nextInt(3) == 0) {
            this.setTreeType(1);
        }
        else {
            this.setTreeType(0);
        }
    }
    
    public int getTreeType() {
        int i = ((Entity)this).dataWatcher.getWatchableObjectByte(16);
        if (i < 0 || i >= LOTREntityTree.TYPES.length) {
            i = 0;
        }
        return i;
    }
    
    public void setTreeType(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)i);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("EntType", (byte)this.getTreeType());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setTreeType(nbt.getByte("EntType"));
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase target) {
        if (target instanceof LOTREntityTree) {
            return;
        }
        super.setAttackTarget(target);
    }
    
    public void knockBack(final Entity entity, final float f, final double d, final double d1) {
        super.knockBack(entity, f, d, d1);
        ((Entity)this).motionX /= 2.0;
        ((Entity)this).motionY /= 2.0;
        ((Entity)this).motionZ /= 2.0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        if (this.doTreeDamageCalculation() && !this.isTreeEffectiveDamage(damagesource)) {
            f /= 3.0f;
        }
        return super.attackEntityFrom(damagesource, f);
    }
    
    protected boolean doTreeDamageCalculation() {
        return true;
    }
    
    protected final boolean isTreeEffectiveDamage(final DamageSource damagesource) {
        if (damagesource.isFireDamage()) {
            return true;
        }
        if (damagesource.getEntity() instanceof EntityLivingBase && damagesource.getSourceOfDamage() == damagesource.getEntity()) {
            final ItemStack itemstack = ((EntityLivingBase)damagesource.getEntity()).getHeldItem();
            if (itemstack != null && ForgeHooks.canToolHarvestBlock(Blocks.log, 0, itemstack)) {
                return true;
            }
        }
        return false;
    }
    
    public void addPotionEffect(final PotionEffect effect) {
        if (effect.getPotionID() == Potion.poison.id) {
            return;
        }
        super.addPotionEffect(effect);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int logs = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 3, 10) + ((Entity)this).rand.nextInt(4 * (i + 1)), l = 0; l < logs; ++l) {
            final int treeType = this.getTreeType();
            this.entityDropItem(new ItemStack(LOTREntityTree.WOOD_BLOCKS[treeType], 1, LOTREntityTree.WOOD_META[treeType]), 0.0f);
        }
        for (int sticks = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 6, 16) + ((Entity)this).rand.nextInt(5 * (i + 1)), j = 0; j < sticks; ++j) {
            this.func_145779_a(Items.stick, 1);
        }
    }
    
    @Override
    public boolean canDropRares() {
        return false;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (!super.getCanSpawnHere()) {
            return false;
        }
        if (super.liftSpawnRestrictions) {
            return true;
        }
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final Block block = ((Entity)this).worldObj.getBlock(i, j - 1, k);
        final int meta = ((Entity)this).worldObj.getBlockMetadata(i, j - 1, k);
        return j > 62 && (block == Blocks.grass || block == Blocks.dirt);
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (this.isTreeHomeBiome(biome)) {
            f += 20.0f;
        }
        return f;
    }
    
    protected boolean isTreeHomeBiome(final BiomeGenBase biome) {
        return biome instanceof LOTRBiomeGenFangorn;
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
    
    static {
        LOTREntityTree.WOOD_BLOCKS = new Block[] { Blocks.log, LOTRMod.wood2, Blocks.log };
        LOTREntityTree.LEAF_BLOCKS = new Block[] { (Block)Blocks.leaves, LOTRMod.leaves2, (Block)Blocks.leaves };
        LOTREntityTree.SAPLING_BLOCKS = new Block[] { Blocks.sapling, LOTRMod.sapling2, Blocks.sapling };
        LOTREntityTree.WOOD_META = new int[] { 0, 1, 2 };
        LOTREntityTree.LEAF_META = new int[] { 0, 1, 2 };
        LOTREntityTree.SAPLING_META = new int[] { 0, 1, 2 };
        LOTREntityTree.TYPES = new String[] { "oak", "beech", "birch" };
    }
}
