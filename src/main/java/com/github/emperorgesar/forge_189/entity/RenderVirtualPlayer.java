package com.github.emperorgesar.forge_189.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVirtualPlayer extends RendererLivingEntity<VirtualPlayer> {

    public RenderVirtualPlayer(RenderManager renderManager)
    {
        super(renderManager, new ModelBiped(), 0.5F);
    }

    @Override
    protected void preRenderCallback(VirtualPlayer entity, float partialTickTime)
    {
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(VirtualPlayer entity)
    {
        return entity.getLocationSkin();
    }

    @Override
    public void doRender(VirtualPlayer entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
