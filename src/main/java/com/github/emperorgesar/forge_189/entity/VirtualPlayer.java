package com.github.emperorgesar.forge_189.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class VirtualPlayer extends AbstractClientPlayer {
    ResourceLocation locationSkin;

    public VirtualPlayer(World world, GameProfile gameProfile, String url) {
        super(world, gameProfile);
        SkinManager skinManager = Minecraft.getMinecraft().getSkinManager();
        MinecraftProfileTexture texture = new MinecraftProfileTexture(url, null);
        this.locationSkin = skinManager.loadSkin(texture, MinecraftProfileTexture.Type.SKIN);
    }

    public ResourceLocation getLocationSkin() {
        return this.locationSkin;
    }

    public String getSkinType() {
        return "default";
    }
}
