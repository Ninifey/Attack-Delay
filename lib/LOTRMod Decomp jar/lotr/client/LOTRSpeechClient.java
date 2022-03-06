// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import lotr.common.entity.npc.LOTREntityNPC;
import java.util.Iterator;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class LOTRSpeechClient
{
    private static Map<UUID, TimedSpeech> npcSpeeches;
    private static int DISPLAY_TIME;
    
    public static void update() {
        final Map<UUID, TimedSpeech> newMap = new HashMap<UUID, TimedSpeech>();
        for (final Map.Entry<UUID, TimedSpeech> e : LOTRSpeechClient.npcSpeeches.entrySet()) {
            final UUID key = e.getKey();
            final TimedSpeech speech = e.getValue();
            speech.time--;
            if (speech.time > 0) {
                newMap.put(key, speech);
            }
        }
        LOTRSpeechClient.npcSpeeches = newMap;
    }
    
    public static void receiveSpeech(final LOTREntityNPC npc, final String speech) {
        LOTRSpeechClient.npcSpeeches.put(npc.getUniqueID(), new TimedSpeech(speech, LOTRSpeechClient.DISPLAY_TIME));
    }
    
    public static void removeSpeech(final LOTREntityNPC npc) {
        LOTRSpeechClient.npcSpeeches.remove(npc.getUniqueID());
    }
    
    public static TimedSpeech getSpeechFor(final LOTREntityNPC npc) {
        final UUID key = npc.getUniqueID();
        if (LOTRSpeechClient.npcSpeeches.containsKey(key)) {
            return LOTRSpeechClient.npcSpeeches.get(key);
        }
        return null;
    }
    
    public static boolean hasSpeech(final LOTREntityNPC npc) {
        return getSpeechFor(npc) != null;
    }
    
    public static void clearAll() {
        LOTRSpeechClient.npcSpeeches.clear();
    }
    
    static {
        LOTRSpeechClient.npcSpeeches = new HashMap<UUID, TimedSpeech>();
        LOTRSpeechClient.DISPLAY_TIME = 200;
    }
    
    public static class TimedSpeech
    {
        private String speech;
        private int time;
        
        private TimedSpeech(final String s, final int i) {
            this.speech = s;
            this.time = i;
        }
        
        public String getSpeech() {
            return this.speech;
        }
        
        public float getAge() {
            return this.time / (float)LOTRSpeechClient.DISPLAY_TIME;
        }
    }
}
