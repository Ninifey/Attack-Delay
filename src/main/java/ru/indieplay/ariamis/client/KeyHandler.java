package ru.indieplay.ariamis.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;

public class KeyHandler {
    private static Minecraft mc;

    static {
        KeyHandler.mc = Minecraft.getMinecraft();
    }

    public KeyHandler() {
        FMLCommonHandler.instance().bus().register((Object) this);
    }

    @SubscribeEvent
    public void MouseInputEvent(final InputEvent.MouseInputEvent event) {
        AttackTiming.doAttackTiming();
    }

    @SubscribeEvent
    public void KeyInputEvent(final InputEvent.KeyInputEvent event) {
        AttackTiming.doAttackTiming();
        boolean usedAlignmentKeys = false;
    }
}
