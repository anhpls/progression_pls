package com.anhpls.progressionpls.client.vfx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

public final class ParticleToggle {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("progression_pls_vfx.json");

    private static final class Data {
        boolean particlesEnabled = true;
    }

    private static Data data = load();

    private ParticleToggle() {}

    public static boolean isEnabled() {
        return data.particlesEnabled;
    }

    public static void setEnabled(boolean enabled) {
        data.particlesEnabled = enabled;
        save();
    }

    public static void toggle() {
        setEnabled(!data.particlesEnabled);
    }

    private static Data load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                Data loaded = GSON.fromJson(Files.readString(CONFIG_PATH), Data.class);
                if (loaded != null) return loaded;
            }
        } catch (IOException ignored) {
            // fall through to defaults rather than crash the client over a bad config file
        }
        return new Data();
    }

    private static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(data));
        } catch (IOException ignored) {
            // worst case the toggle just doesn't persist this session
        }
    }
}