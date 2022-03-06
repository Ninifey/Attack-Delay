// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemSword;

public class LOTRItemSword extends ItemSword
{
    @SideOnly(Side.CLIENT)
    public IIcon glowingIcon;
    private boolean isElvenBlade;
    protected float lotrWeaponDamage;
    
    public LOTRItemSword(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemSword(final Item.ToolMaterial material) {
        super(material);
        this.isElvenBlade = false;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.lotrWeaponDamage = material.getDamageVsEntity() + 4.0f;
    }
    
    public LOTRItemSword addWeaponDamage(final float f) {
        this.lotrWeaponDamage += f;
        return this;
    }
    
    public float getLOTRWeaponDamage() {
        return this.lotrWeaponDamage;
    }
    
    public LOTRItemSword setIsElvenBlade() {
        this.isElvenBlade = true;
        return this;
    }
    
    public boolean isElvenBlade() {
        return this.isElvenBlade;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        super.registerIcons(iconregister);
        if (this.isElvenBlade) {
            this.glowingIcon = iconregister.registerIcon(this.getIconString() + "_glowing");
        }
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (this.getItemUseAction(itemstack) == EnumAction.none) {
            return itemstack;
        }
        return super.onItemRightClick(itemstack, world, entityplayer);
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = super.getItemAttributeModifiers();
        multimap.removeAll((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(Item.field_111210_e, "LOTR Weapon modifier", (double)this.lotrWeaponDamage, 0));
        return multimap;
    }
    
    public static UUID accessWeaponDamageModifier() {
        return Item.field_111210_e;
    }
}
