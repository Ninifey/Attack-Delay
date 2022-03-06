// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.SimpleResource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Set;
import java.util.HashMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import net.minecraft.client.resources.IResourceManager;

public class LOTRMusicResourceManager implements IResourceManager
{
    private Map<ResourceLocation, IResource> resourceMap;
    
    public LOTRMusicResourceManager() {
        this.resourceMap = new HashMap<ResourceLocation, IResource>();
    }
    
    public Set getResourceDomains() {
        return this.resourceMap.entrySet();
    }
    
    public IResource getResource(final ResourceLocation resource) throws IOException {
        return this.resourceMap.get(resource);
    }
    
    public List getAllResources(final ResourceLocation resource) throws IOException {
        final List list = new ArrayList();
        list.add(this.getResource(resource));
        return list;
    }
    
    public void registerMusicResources(final ResourceLocation resource, final InputStream in) {
        final IResource ires = (IResource)new SimpleResource(resource, in, (InputStream)null, (IMetadataSerializer)null);
        this.resourceMap.put(resource, ires);
    }
}
