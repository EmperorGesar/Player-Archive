package com.github.emperorgesar.forge_189;

import com.github.emperorgesar.forge_189.common.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PlayerArchive.MODID,
        name = PlayerArchive.NAME,
        version = PlayerArchive.VERSION,
        acceptedMinecraftVersions = "1.8.9",
        useMetadata=true)
public class PlayerArchive {
    public static final String MODID = "playerarchive";
    public static final String NAME = "Player Archive";
    public static final String VERSION = "1.0.0";

    @SidedProxy(clientSide = "com.github.emperorgesar.forge_189.client.ClientProxy",
            serverSide = "com.github.emperorgesar.forge_189.common.CommonProxy")
    public static CommonProxy proxy;

    @Instance(PlayerArchive.MODID)
    public static PlayerArchive instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
