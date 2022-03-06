package ru.indieplay.ariamis.attackdelay;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;
import ru.indieplay.ariamis.attackdelay.client.KeyHandler;
import ru.indieplay.ariamis.attackdelay.client.TickHandlerClient;
import ru.indieplay.ariamis.attackdelay.common.MyMessage;
import ru.indieplay.ariamis.attackdelay.common.Proxy;
import ru.indieplay.ariamis.attackdelay.common.ServerHandler;

import java.io.File;
import java.util.Set;

@Mod(
        modid = "attackdelay",
        name = "Ariamis attack delay mod",
        version = "Minecraft 1.7.10",
        dependencies = "required-after:Forge@[10.13.4.1558,)"
)
public class AttackDelay {
    @SidedProxy(clientSide = "ru.indieplay.ariamis.attackdelay.client.Proxy", serverSide = "ru.indieplay.ariamis.attackdelay.common.Proxy")
    public static Proxy proxy;
    KeyHandler a = new KeyHandler();
    TickHandlerClient b =new TickHandlerClient();
    ServerHandler c =new ServerHandler();
    public static SimpleNetworkWrapper network;

    public static FMLEventChannel channel;
    @Mod.EventHandler
    public void configLoad(FMLPreInitializationEvent event) throws ClassNotFoundException {
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + "attackdelay.cfg"));
        Set<String> categoryNames = config.getCategoryNames();
        for(String cat:categoryNames ){
            System.out.print(cat);
            if(null != Class.forName("cat"))
                System.out.print("+ \n");
            else  System.out.print("- \n");
        }

    }


    @Mod.EventHandler
    public void load(FMLPostInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("ArCHan");
        network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
        network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.SERVER);
        network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.CLIENT);


    }



}

