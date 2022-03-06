// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import java.util.Iterator;
import net.minecraft.util.MathHelper;
import net.minecraft.client.gui.FontRenderer;
import java.util.ArrayList;
import java.util.List;

public class LOTRTextBody
{
    private List<TextColor> list;
    private int defaultColor;
    private int textWidth;
    private static final String LINEBREAK = "<BR>";
    
    public LOTRTextBody(final int c) {
        this.list = new ArrayList<TextColor>();
        this.defaultColor = c;
        this.textWidth = 100;
    }
    
    public void add(final String s) {
        this.add(s, this.defaultColor);
    }
    
    public void add(final String s, final int c) {
        this.list.add(new TextColor(s, c));
    }
    
    public void addLinebreak() {
        this.add("<BR>");
    }
    
    public void set(final int i, final String s) {
        this.list.get(i).text = s;
    }
    
    public void set(final int i, final String s, final int c) {
        this.list.get(i).text = s;
        this.list.get(i).color = c;
    }
    
    public String getText(final int i) {
        return this.list.get(i).text;
    }
    
    public int getColor(final int i) {
        return this.list.get(i).color;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public void setTextWidth(final int w) {
        this.textWidth = w;
    }
    
    public void render(final FontRenderer fr, final int x, final int y) {
        this.renderAndReturnScroll(fr, x, y, Integer.MAX_VALUE, Float.MAX_VALUE);
    }
    
    public float renderAndReturnScroll(final FontRenderer fr, final int x, final int yTop, final int yBottom, float scroll) {
        final int ySize = yBottom - yTop;
        final int numLines = this.getTotalLines(fr);
        final int lineHeight = fr.FONT_HEIGHT;
        scroll = Math.max(scroll, 0.0f);
        scroll = Math.min(scroll, (float)(numLines - MathHelper.floor_double((double)(ySize / (float)lineHeight))));
        int d1 = Math.round(scroll);
        int y = yTop;
        y += ySize / lineHeight * lineHeight;
        y -= lineHeight;
        final int maxLines = ySize / lineHeight;
        if (numLines < maxLines) {
            y -= (maxLines - numLines) * lineHeight;
        }
    Label_0302:
        for (int i = this.size() - 1; i >= 0; --i) {
            final String part = this.getText(i);
            final int color = this.getColor(i);
            final List<String> lineList = (List<String>)fr.listFormattedStringToWidth(part, this.textWidth);
            for (int l = lineList.size() - 1; l >= 0; --l) {
                String line = lineList.get(l);
                if (d1 > 0) {
                    --d1;
                }
                else {
                    if (y < yTop) {
                        break Label_0302;
                    }
                    if (line.equals("<BR>")) {
                        line = "";
                        for (char br = '-'; fr.getStringWidth(line + br) < this.textWidth; line += br) {}
                    }
                    fr.drawString(line, x, y, color);
                    y -= lineHeight;
                }
            }
        }
        return scroll;
    }
    
    public int getTotalLines(final FontRenderer fr) {
        int lines = 0;
        for (int i = 0; i < this.size(); ++i) {
            final String part = this.getText(i);
            final List<String> lineList = (List<String>)fr.listFormattedStringToWidth(part, this.textWidth);
            for (final String line : lineList) {
                ++lines;
            }
        }
        return lines;
    }
    
    private static class TextColor
    {
        public String text;
        public int color;
        
        public TextColor(final String s, final int c) {
            this.text = s;
            this.color = c;
        }
    }
}
