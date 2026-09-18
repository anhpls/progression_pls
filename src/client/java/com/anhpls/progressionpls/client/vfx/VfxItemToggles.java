package com.anhpls.progressionpls.client.vfx;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

/**
 * The player's personal "hide this one, my screen is too busy" switch for a single
 * equipped item's aura VFX — separate from {@link ParticleToggle} (the one master
 * on/off switch for all VFX) and separate from a spec's own {@code enabled} flag
 * (a dev/balance toggle set via /vfxtune, reset to defaults every launch).
 *
 * Only ever filters what a player sees on THEMSELVES: it never hides another
 * player's aura from view (see {@code AuraVfx.onClientTick}'s {@code isSelf} check),
 * so turning off Egg Toast's hearts just declutters your own character while you
 * keep seeing everyone else's.
 *
 * Persisted per item id so it survives restarts and travels with the item, not
 * the trinket slot it happens to be in.
 */
public final class VfxItemToggles {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<HashMap<String, Boolean>>() {}.getType();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("progression_pls_vfx_items.json");

    private static Map<String, Boolean> data = load();

    private VfxItemToggles() {}

    public static boolean isEnabled(Item item) {
        return data.getOrDefault(idOf(item), true);
    }

    public static void setEnabled(Item item, boolean enabled) {
        data.put(idOf(item), enabled);
        save();
    }

    public static void toggle(Item item) {
        setEnabled(item, !isEnabled(item));
    }

    private static String idOf(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    private static Map<String, Boolean> load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                Map<String, Boolean> loaded = GSON.fromJson(Files.readString(CONFIG_PATH), MAP_TYPE);
                if (loaded != null) return loaded;
            }
        } catch (IOException ignored) {
            // fall through to defaults rather than crash the client over a bad config file
        }
        return new HashMap<>();
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
