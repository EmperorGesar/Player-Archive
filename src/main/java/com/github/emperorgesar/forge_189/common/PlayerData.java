package com.github.emperorgesar.forge_189.common;

import amidst.documentation.NotNull;
import amidst.logging.AmidstLogger;
import amidst.mojangapi.file.directory.SaveDirectory;
import amidst.mojangapi.file.json.player.*;
import amidst.mojangapi.file.nbt.NBTTagKeys;
import amidst.mojangapi.file.nbt.NBTUtils;
import amidst.mojangapi.file.nbt.player.PlayerNbt;
import amidst.mojangapi.file.service.SaveDirectoryService;
import amidst.parsing.FormatException;
import amidst.parsing.json.JsonReader;
import net.minecraftforge.common.DimensionManager;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.NumberTag;
import net.querz.nbt.tag.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class PlayerData {
    private final String UUID_TO_PROFILE = "https://sessionserver.mojang.com/session/minecraft/profile/";

    private Path file;
    private UUID uuid;
    private String uuidClean;
    private String name;
    private String skinURL;
    private CompoundTag tags;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private int dimension;

    public PlayerData(File fileEntry) {
        this.file = fileEntry.toPath();
        this.uuid = UUID.fromString(fileEntry.getName().split("\\.")[0]);
        this.uuidClean = fileEntry.getName().split("\\.")[0].replace("-", "");
        this.name = tryGetPlayerJsonByUUID(this.uuidClean).get().getName();
        this.skinURL = tryGetSkinUrl(tryGetPlayerJsonByUUID(this.uuidClean).get()).get();

        this.tags = tryGetTags();
        assert this.tags != null;

        this.posX = ((NumberTag<?>) this.tags.get(NBTTagKeys.TAG_KEY_POS, ListTag.class).get(0)).asDouble();
        this.posY = ((NumberTag<?>) this.tags.get(NBTTagKeys.TAG_KEY_POS, ListTag.class).get(1)).asDouble();
        this.posZ = ((NumberTag<?>) this.tags.get(NBTTagKeys.TAG_KEY_POS, ListTag.class).get(2)).asDouble();

        this.yaw = ((NumberTag<?>) this.tags.get(NBTTagKeys.TAG_KEY_ROTATION, ListTag.class).get(0)).asFloat();
        this.pitch = ((NumberTag<?>) this.tags.get(NBTTagKeys.TAG_KEY_ROTATION, ListTag.class).get(1)).asFloat();

        this.dimension = ((NumberTag<?>) this.tags.get(NBTTagKeys.TAG_KEY_DIMENSION)).asInt();
    }

    @NotNull
    private CompoundTag tryGetTags() {
        try {
            return getTags();
        } catch (IOException | NullPointerException | ClassCastException e) {
            AmidstLogger.warn(e, "cannot read tags from file: {}", file);
            return null;
        }
    }

    @NotNull
    private CompoundTag getTags() throws IOException {
        return NBTUtils.readTagFromFile(this.file);
    }

    @NotNull
    private Optional<PlayerJson> tryGetPlayerJsonByUUID(String uuid) {
        try {
            return Optional.of(getPlayerJsonByUUID(uuid));
        } catch (IOException | FormatException | NullPointerException e) {
            AmidstLogger.warn("unable to load player information by uuid: {}", uuid);
            return Optional.empty();
        }
    }

    @NotNull
    private PlayerJson getPlayerJsonByUUID(String uuid) throws FormatException, IOException {
        return JsonReader.readLocation(UUID_TO_PROFILE + uuid, PlayerJson.class);
    }

    @NotNull
    private Optional<String> tryGetSkinUrl(PlayerJson playerJson) {
        return tryReadTexturesProperty(playerJson)
                .map(TexturesPropertyJson::getTextures)
                .map(TexturesJson::getSKIN)
                .map(SKINJson::getUrl);
    }
    @NotNull
    private Optional<TexturesPropertyJson> tryReadTexturesProperty(PlayerJson playerJson) {
        try {
            for (PropertyJson property : playerJson.getProperties()) {
                if (isTexturesProperty(property)) {
                    return Optional.of(JsonReader.readString(getDecodedValue(property), TexturesPropertyJson.class));
                }
            }
            return Optional.empty();
        } catch (FormatException e) {
            return Optional.empty();
        }
    }

    private boolean isTexturesProperty(PropertyJson propertyJson) throws FormatException {
        String name = propertyJson.getName();
        if (name == null) {
            throw new FormatException("property has no name");
        } else {
            return name.equals("textures");
        }
    }

    @NotNull
    private String getDecodedValue(PropertyJson propertyJson) throws FormatException {
        String value = propertyJson.getValue();
        if (value == null) {
            throw new FormatException("unable to decode property value");
        } else {
            return new String(
                    Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)),
                    StandardCharsets.UTF_8);
        }
    }

    public String getName() {
        return this.name;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int getDimension() {
        return this.dimension;
    }

    public String getSkinURL() {
        return this.skinURL;
    }
}
