package io.github.syncmc.murdersleuth.render;

import io.github.syncmc.murdersleuth.util.GameHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GoldRenderFactory implements IRenderFactory<EntityItem>
{
    public final GameHelper murderSleuthUtils;
    public GoldRenderFactory(GameHelper murderSleuthUtils)
    {
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    @Override
    public Render<? super EntityItem> createRenderFor(RenderManager manager)
    {
        return new RenderGold(manager, GameHelper.mc.getRenderItem(), murderSleuthUtils);
    }
}
