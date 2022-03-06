// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import java.util.Random;

public class LOTRDrunkenSpeech
{
    private static Random rand;
    
    public static String getDrunkenSpeech(final String speech, final float chance) {
        String newSpeech = "";
        for (int i = 0; i < speech.length(); ++i) {
            String s = speech.substring(i, i + 1);
            if (LOTRDrunkenSpeech.rand.nextFloat() < chance) {
                s = "";
            }
            else if (LOTRDrunkenSpeech.rand.nextFloat() < chance * 0.4f) {
                s = " *hic* ";
            }
            newSpeech += s;
        }
        return newSpeech;
    }
    
    static {
        LOTRDrunkenSpeech.rand = new Random();
    }
}
