package io.github.syncmc.murdersleuth;

import io.github.syncmc.murdersleuth.listeners.ClientChatReceivedListener;
import io.github.syncmc.murdersleuth.listeners.ClientTickListener;
import io.github.syncmc.murdersleuth.listeners.GamePlayerListener;
import io.github.syncmc.murdersleuth.listeners.KeyInputListener;
import io.github.syncmc.murdersleuth.listeners.PreRenderPlayerListener;
import io.github.syncmc.murdersleuth.render.GoldRenderFactory;
import io.github.syncmc.murdersleuth.util.GameHelper;
import io.github.syncmc.murdersleuth.util.KeybindHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MurderSleuth.MODID, useMetadata = true, clientSideOnly = true)
public class MurderSleuth
{
    public static final String MODID = "murdersleuth";

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        GameHelper.init();
        KeybindHandler.init();
        RenderingRegistry.registerEntityRenderingHandler(EntityItem.class, new GoldRenderFactory());
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ClientChatReceivedListener());
        MinecraftForge.EVENT_BUS.register(new ClientTickListener());
        MinecraftForge.EVENT_BUS.register(new GamePlayerListener());
        MinecraftForge.EVENT_BUS.register(new KeyInputListener());
        MinecraftForge.EVENT_BUS.register(new PreRenderPlayerListener());
    }
}
