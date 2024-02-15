package com.github.emperorgesar.forge_189.common;

import amidst.mojangapi.file.directory.SaveDirectory;
import amidst.mojangapi.file.service.SaveDirectoryService;
import amidst.parsing.FormatException;
import com.github.emperorgesar.forge_189.client.KeyLoader;


import com.github.emperorgesar.forge_189.entity.VirtualPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class EventLoader
{
    private boolean virtualPlayerSpawned = false;
    private boolean visible = false;
    private final ArrayList<VirtualPlayer> players = new ArrayList<VirtualPlayer>();

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
                Path saveDir = DimensionManager.getCurrentSaveRootDirectory().toPath().resolve("playerdata");
                File folder = new File(saveDir.toString());
                int i = 0;

                for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                    PlayerData data = new PlayerData(fileEntry);

                    if (data.getUUID() != player.getUniqueID()) {
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

            } else {
                this.players.forEach((n) -> n.setInvisible(this.visible));
                this.visible = !this.visible;
            }

            player.addChatMessage(new ChatComponentTranslation("wow"));
        }
    }
}
