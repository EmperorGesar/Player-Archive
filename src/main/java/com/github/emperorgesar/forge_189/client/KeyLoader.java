package com.github.emperorgesar.forge_189.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyLoader
{
    public static KeyBinding showTime;
    public static KeyBinding showPlayer;

    public KeyLoader()
    {
        KeyLoader.showTime = new KeyBinding("key.forge_189.showTime", Keyboard.KEY_P, "key.categories.forge_189");
        ClientRegistry.registerKeyBinding(KeyLoader.showTime);
    }
}
