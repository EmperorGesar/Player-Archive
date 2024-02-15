package com.github.emperorgesar.forge_189.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class VirtualPlayer extends AbstractClientPlayer {
    ResourceLocation locationSkin;
    GameProfile gameProfile;

    public VirtualPlayer(World world, GameProfile gameProfile) {
        super(world, gameProfile);
        this.gameProfile = gameProfile;
        SkinManager skinManager = Minecraft.getMinecraft().getSkinManager();
    }

//    @Override
//    public ResourceLocation getLocationSkin() {
//        return this.locationSkin;
//    }

    public NetworkPlayerInfo getInfo() {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.gameProfile.getId());
    }
}
