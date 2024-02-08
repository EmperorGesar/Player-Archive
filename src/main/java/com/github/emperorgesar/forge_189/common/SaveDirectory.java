package com.github.emperorgesar.forge_189.common;

import net.minecraftforge.common.DimensionManager;

import java.nio.file.Path;

public class SaveDirectory {
    private Path playerdata;

    public SaveDirectory() {
        this.playerdata = DimensionManager.getCurrentSaveRootDirectory().toPath().resolve("playerdata");
    }

    public Path getPlayerdata() {
        return playerdata;
    }
}
