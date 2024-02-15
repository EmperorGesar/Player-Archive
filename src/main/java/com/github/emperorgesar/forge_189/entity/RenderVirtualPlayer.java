package com.github.emperorgesar.forge_189.entity;

import com.github.emperorgesar.forge_189.PlayerArchive;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVirtualPlayer extends RendererLivingEntity<VirtualPlayer> {

    public RenderVirtualPlayer(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(1.0F, true), 0.5F);
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
