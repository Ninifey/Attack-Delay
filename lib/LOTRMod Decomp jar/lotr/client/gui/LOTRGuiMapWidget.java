// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.util.StatCollector;

public class LOTRGuiMapWidget
{
    private int xPos;
    private int yPos;
    public final int width;
    private String name;
    private final int texUBase;
    private final int texVBase;
    private int texVIndex;
    public boolean visible;
    
    public LOTRGuiMapWidget(final int x, final int y, final int w, final String s, final int u, final int v) {
        this.visible = true;
        this.xPos = x;
        this.yPos = y;
        this.width = w;
        this.name = s;
        this.texUBase = u;
        this.texVBase = v;
    }
    
    public String getTranslatedName() {
        return StatCollector.translateToLocal("lotr.gui.map.widget." + this.name);
    }
    
    public void setTexVIndex(final int i) {
        this.texVIndex = i;
    }
    
    public int getTexU() {
        return this.texUBase;
    }
    
    public int getTexV() {
        return this.texVBase + this.texVIndex * this.width;
    }
    
    public int getMapXPos(final int mapWidth) {
        return (this.xPos < 0) ? (mapWidth + this.xPos) : this.xPos;
    }
    
    public int getMapYPos(final int mapHeight) {
        return (this.yPos < 0) ? (mapHeight + this.yPos) : this.yPos;
    }
    
    public boolean isMouseOver(final int mouseX, final int mouseY, final int mapWidth, final int mapHeight) {
        return this.visible && mouseX >= this.getMapXPos(mapWidth) && mouseX < this.getMapXPos(mapWidth) + this.width && mouseY >= this.getMapYPos(mapHeight) && mouseY < this.getMapYPos(mapHeight) + this.width;
    }
}
