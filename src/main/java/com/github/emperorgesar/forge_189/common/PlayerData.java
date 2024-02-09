package com.github.emperorgesar.forge_189.common;

import amidst.mojangapi.file.directory.SaveDirectory;
import amidst.mojangapi.file.nbt.player.PlayerNbt;
import amidst.mojangapi.file.service.SaveDirectoryService;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerData {

    private final SaveDirectoryService saveDirectoryService = new SaveDirectoryService();
    private final SaveDirectory dir = new SaveDirectory(DimensionManager.getCurrentSaveRootDirectory().toPath());
    private ArrayList<String> playerUUIDs = new ArrayList<String>();

    public PlayerData() {
        Path saveDir = DimensionManager.getCurrentSaveRootDirectory().toPath().resolve("playerdata");
        File folder = new File(saveDir.toString());
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            this.playerUUIDs.add(fileEntry.getName().split("\\.")[0]);
        }
    }

    public List<PlayerNbt> getData() {
        return this.saveDirectoryService.tryReadMultiplayerPlayerNbts(this.dir);
    }
}
