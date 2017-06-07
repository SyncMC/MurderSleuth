package io.github.syncmc.murdersleuth.handlers;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeybindHandler
{
    public static final String CATEGORY = "key.categories.murdersleuth";
    public static KeyBinding showWeaponHolders;
    public static KeyBinding clearWeaponHolders;
    public static KeyBinding rotatePlayerView;
    public static KeyBinding toggleGoldView;
    
    public static void init()
    {
        showWeaponHolders = new KeyBinding("key.murdersleuth.showWeaponHolders", Keyboard.KEY_M, CATEGORY);
        clearWeaponHolders = new KeyBinding("key.murdersleuth.clearWeaponHolders", Keyboard.KEY_C, CATEGORY);
        rotatePlayerView = new KeyBinding("key.murdersleuth.rotatePlayerView", Keyboard.KEY_P, CATEGORY);
        toggleGoldView = new KeyBinding("key.murdersleuth.toggleGoldView", Keyboard.KEY_G, CATEGORY);
        
        ClientRegistry.registerKeyBinding(showWeaponHolders);
        ClientRegistry.registerKeyBinding(clearWeaponHolders);
        ClientRegistry.registerKeyBinding(rotatePlayerView);
        ClientRegistry.registerKeyBinding(toggleGoldView);
    }
}
