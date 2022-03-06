// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.coremod;

import java.util.Map;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions({ "lotr" })
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class LOTRLoadingPlugin implements IFMLLoadingPlugin
{
    public String[] getASMTransformerClass() {
        return new String[] { LOTRClassTransformer.class.getName() };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
