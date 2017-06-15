package io.github.syncmc.murdersleuth.util;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeybindHandler
{
    public static final String CATEGORY = "key.categories.murdersleuth";
    public static KeyBinding showPlayerData;
    public static KeyBinding clearPlayerData;
    public static KeyBinding rotatePlayerView;
    public static KeyBinding toggleGoldView;

    public static void init()
    {
        KeybindHandler.showPlayerData = new KeyBinding("key.murdersleuth.showPlayerData", Keyboard.KEY_M, CATEGORY);
        KeybindHandler.clearPlayerData = new KeyBinding("key.murdersleuth.clearPlayerData", Keyboard.KEY_C, CATEGORY);
        KeybindHandler.rotatePlayerView = new KeyBinding("key.murdersleuth.rotatePlayerView", Keyboard.KEY_P, CATEGORY);
        KeybindHandler.toggleGoldView = new KeyBinding("key.murdersleuth.toggleGoldView", Keyboard.KEY_G, CATEGORY);

        ClientRegistry.registerKeyBinding(showPlayerData);
        ClientRegistry.registerKeyBinding(clearPlayerData);
        ClientRegistry.registerKeyBinding(rotatePlayerView);
        ClientRegistry.registerKeyBinding(toggleGoldView);
    }
}
