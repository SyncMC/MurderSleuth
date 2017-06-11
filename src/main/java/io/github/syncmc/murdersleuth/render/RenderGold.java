package io.github.syncmc.murdersleuth.render;

import io.github.syncmc.murdersleuth.enums.GoldView;
import io.github.syncmc.murdersleuth.util.GameHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;

public class RenderGold extends RenderEntityItem
{
    public final GameHelper murderSleuthUtils;
    public RenderGold(RenderManager renderManagerIn, RenderItem renderItemIn, GameHelper murderSleuthUtils)
    {
        super(renderManagerIn, renderItemIn);
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    @Override
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (murderSleuthUtils.goldView == GoldView.ALL || entity.getEntityItem().getItem() != Items.GOLD_INGOT)
        {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
}
