package ru.indieplay.ariamis;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import ru.indieplay.ariamis.client.KeyHandler;
import ru.indieplay.ariamis.client.TickHandler;
import ru.indieplay.ariamis.common.PacketHandler;
import ru.indieplay.ariamis.common.Proxy;
import ru.indieplay.ariamis.common.WeaponStats;

import java.io.File;
import java.util.Map.Entry;

@Mod(
        modid = "attackdelay",
        name = "Ariamis attack delay mod",
        version = "Minecraft 1.7.10",
        dependencies = "required-after:Forge@[10.13.4.1558,)"
)

public class Delay {
    @SidedProxy(clientSide = "ru.indieplay.ariamis.client.Proxy", serverSide = "ru.indieplay.ariamis.common.Proxy")
    public static Proxy proxy;
    public static SimpleNetworkWrapper network;
    public static FMLEventChannel channel;
    static String path;
    public static Configuration config;
    ru.indieplay.ariamis.common.TickHandler t = new ru.indieplay.ariamis.common.TickHandler();

    public static void loadConfig() {
        config = new Configuration(new File(path + "/attackdelay.cfg"));
        config.load();

        WeaponStats.basePlayerMeleeTime = Integer.parseInt(config.get("base", "base", 20, "Basic value of speed").getString());


        for (Entry<String, Property> entry : config.getCategory("general").getValues().entrySet()) {
            try {
                if (null != Class.forName(entry.getKey()))
                    WeaponStats.registerMeleeSpeed(Class.forName(entry.getKey()), entry.getValue().getDouble());
            } catch (ClassNotFoundException ce) {
                System.out.print("Class not found " + entry.getValue() + " \n");
            }
        }
        for (Entry<String, Property> entry : config.getCategory("range").getValues().entrySet()) {
            try {
                if (null != Class.forName(entry.getKey()))
                    WeaponStats.registerMeleeReach(Class.forName(entry.getKey()), entry.getValue().getDouble());
            } catch (ClassNotFoundException ce) {
                System.out.print("Class not found " + entry.getValue() + " \n");
            }
        }
    }

    @EventHandler
    public void configLoad(FMLPreInitializationEvent event) {
        proxy.init();
        PacketHandler.init();
        path = event.getModConfigurationDirectory().toString();
    }

    public void registerCommands(FMLInitializationEvent event) {

    }

    @EventHandler
    public void post(FMLPostInitializationEvent event) {
        loadConfig();
    }

    @EventHandler
    public void loadServer(FMLServerStartingEvent event) {
        event.registerServerCommand(new ReloadConfig());
        //vent.registerServerCommand(new ConfigManager());
    }
}
