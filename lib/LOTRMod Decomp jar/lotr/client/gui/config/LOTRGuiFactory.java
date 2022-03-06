// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui.config;

import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.IModGuiFactory;

public class LOTRGuiFactory implements IModGuiFactory
{
    public void initialize(final Minecraft mc) {
    }
    
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return (Class<? extends GuiScreen>)LOTRGuiConfig.class;
    }
    
    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
    
    public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(final IModGuiFactory.RuntimeOptionCategoryElement element) {
        return null;
    }
}
