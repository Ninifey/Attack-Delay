// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.ArrayList;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import net.minecraft.block.Block;

public class LOTRBlockCraftingTable extends Block
{
    public static List<LOTRBlockCraftingTable> allCraftingTables;
    public final LOTRFaction tableFaction;
    public final int tableGUIID;
    
    public LOTRBlockCraftingTable(final Material material, final LOTRFaction faction, final int guiID) {
        super(material);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(2.5f);
        this.tableFaction = faction;
        this.tableGUIID = guiID;
        LOTRBlockCraftingTable.allCraftingTables.add(this);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.tableFaction) >= 1.0f;
        if (hasRequiredAlignment) {
            if (!world.isClient) {
                entityplayer.openGui((Object)LOTRMod.instance, this.tableGUIID, world, i, j, k);
            }
        }
        else {
            for (int l = 0; l < 8; ++l) {
                final double d = i + world.rand.nextFloat();
                final double d2 = j + 1.0;
                final double d3 = k + world.rand.nextFloat();
                world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
            }
            if (!world.isClient) {
                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, this.tableFaction);
            }
        }
        return true;
    }
    
    static {
        LOTRBlockCraftingTable.allCraftingTables = new ArrayList<LOTRBlockCraftingTable>();
    }
}
