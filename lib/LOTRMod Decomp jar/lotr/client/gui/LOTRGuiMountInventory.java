// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.client.gui.inventory.GuiContainer;
import lotr.common.inventory.LOTRContainerMountInventory;
import net.minecraft.entity.passive.EntityHorse;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;

public class LOTRGuiMountInventory extends GuiScreenHorseInventory
{
    public LOTRGuiMountInventory(final IInventory playerInv, final IInventory horseInv, final LOTREntityHorse horse) {
        super(playerInv, horseInv, (EntityHorse)horse);
        ((GuiContainer)this).field_147002_h = (Container)new LOTRContainerMountInventory(playerInv, horseInv, horse);
    }
}
