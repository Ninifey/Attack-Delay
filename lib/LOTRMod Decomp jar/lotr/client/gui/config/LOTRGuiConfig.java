// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui.config;

import java.util.List;
import lotr.common.LOTRConfig;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;

public class LOTRGuiConfig extends GuiConfig
{
    public LOTRGuiConfig(final GuiScreen parent) {
        super(parent, (List)LOTRConfig.getConfigElements(), "lotr", false, false, GuiConfig.getAbridgedConfigPath(LOTRConfig.config.toString()));
    }
}
