package ru.indieplay.ariamis.common;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public final class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel( "attackdelay" );
    public static void init() {
        INSTANCE.registerMessage(Packet.class, Packet.class, 6, Side.CLIENT );
        INSTANCE.registerMessage(Packet.class, Packet.class, 3, Side.SERVER );
        INSTANCE.registerMessage(PacketDamage.class, PacketDamage.class, 5, Side.SERVER);
    }

}

