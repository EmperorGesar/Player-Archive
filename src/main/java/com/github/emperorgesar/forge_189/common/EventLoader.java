package com.github.emperorgesar.forge_189.common;

import com.github.emperorgesar.forge_189.client.KeyLoader;
import com.github.emperorgesar.forge_189.entity.VirtualPlayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class EventLoader
{
    private boolean virtualPlayerSpawned = false;
    private boolean visible = false;
    private ArrayList<VirtualPlayer> players = new ArrayList<VirtualPlayer>();
    private int dimension;

    public EventLoader()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyLoader.showTime.isPressed())
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World world = Minecraft.getMinecraft().theWorld;

            if (!this.virtualPlayerSpawned) {
                this.spawnVirtualPlayers();
            } else {
                this.players.forEach((n) -> n.setInvisible(this.visible));
                this.visible = !this.visible;
            }

            if (this.visible) {
                player.addChatMessage(new ChatComponentTranslation("Archive Players Showed"));
            } else {
                player.addChatMessage(new ChatComponentTranslation("Archive Players Hided"));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        this.virtualPlayerSpawned = false;
        this.players = new ArrayList<VirtualPlayer>();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        this.virtualPlayerSpawned = false;
        this.players = new ArrayList<VirtualPlayer>();
    }

    private void spawnVirtualPlayers() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        World world = Minecraft.getMinecraft().theWorld;
        Path saveDir = DimensionManager.getCurrentSaveRootDirectory().toPath().resolve("playerdata");
        File folder = new File(saveDir.toString());
        int i = 0;

        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            UUID playerID = UUID.fromString(fileEntry.getName().split("\\.")[0]);

            if (!Objects.equals(playerID.toString(), player.getUniqueID().toString())) {
                PlayerData data = new PlayerData(fileEntry);
                GameProfile profile = new GameProfile(data.getUUID(), data.getName());
                this.players.add(new VirtualPlayer(world, profile, data.getSkinURL()));
                this.players.get(i).setPositionAndRotation(data.getPosX(), data.getPosY(), data.getPosZ(), data.getYaw(), data.getPitch());
                this.players.get(i).travelToDimension(data.getDimension());

                world.spawnEntityInWorld(this.players.get(i));
                ++i;
            }
        }
        this.virtualPlayerSpawned = true;
        this.visible = true;
    }
}
