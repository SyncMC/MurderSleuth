package io.github.syncmc.murdersleuth;

import io.github.syncmc.murdersleuth.handlers.KeybindHandler;
import io.github.syncmc.murdersleuth.listeners.GameStartListener;
import io.github.syncmc.murdersleuth.listeners.HoldWeaponListener;
import io.github.syncmc.murdersleuth.listeners.KeyInputListener;
import io.github.syncmc.murdersleuth.listeners.RenderPlayerListener;
import io.github.syncmc.murdersleuth.render.GoldRenderFactory;
import io.github.syncmc.murdersleuth.util.GameHelper;
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
    public final GameHelper gameHelper = new GameHelper();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        KeybindHandler.init();
        RenderingRegistry.registerEntityRenderingHandler(EntityItem.class, new GoldRenderFactory(gameHelper));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new GameStartListener(gameHelper));
        MinecraftForge.EVENT_BUS.register(new HoldWeaponListener(gameHelper));
        MinecraftForge.EVENT_BUS.register(new KeyInputListener(gameHelper));
        MinecraftForge.EVENT_BUS.register(new RenderPlayerListener(gameHelper));
    }
}
