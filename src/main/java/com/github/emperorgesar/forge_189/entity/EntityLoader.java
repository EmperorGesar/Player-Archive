package com.github.emperorgesar.forge_189.entity;

import com.github.emperorgesar.forge_189.PlayerArchive;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.Render;

public class EntityLoader {
    private static int nextID = 0;

    public EntityLoader()
    {
        registerEntity(VirtualPlayer.class, "VirtualPlayer", 80, 3, true);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
                                       int updateFrequency, boolean sendsVelocityUpdates)
    {
        EntityRegistry.registerModEntity(entityClass, name, nextID++, PlayerArchive.instance, trackingRange, updateFrequency,
                sendsVelocityUpdates);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerEntityRender(VirtualPlayer.class, RenderVirtualPlayer.class);
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }
}
