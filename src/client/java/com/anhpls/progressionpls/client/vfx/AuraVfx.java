package com.anhpls.progressionpls.client.vfx;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public final class AuraVfx {

    private static final List<AuraEffect> EFFECTS = List.of(
            SpiralAuraEffect.INSTANCE,
            HeartAuraEffect.INSTANCE
    );

    /**
     * All items that have ANY aura effect registered to them, across every effect type.
     * Used by the inventory-screen mixin to know which currently-equipped items need a
     * per-item toggle button.
     */
    public static Set<Item> allRegisteredItems() {
        Set<Item> all = new LinkedHashSet<>();
        for (AuraEffect effect : EFFECTS) {
            all.addAll(effect.registeredItems());
        }
        return all;
    }

    public static void register() {
        SpiralAuraEffect.registerDefaults();
        HeartAuraEffect.registerDefaults();

        ClientTickEvents.END_CLIENT_TICK.register(AuraVfx::onClientTick);
    }

    private static void onClientTick(Minecraft client) {
    if (!ParticleToggle.isEnabled()) return;

    ClientLevel level = client.level;
    if (level == null) return;

    long time = level.getGameTime();

    for (Player player : level.players()) {
        // The per-item toggle is a personal "my own screen is too busy" preference,
        // not a broadcast mute — it only hides an item's aura on the character YOU
        // are, never on other players wearing the same item. Each of THEIR clients
        // makes this same call independently using THEIR own toggle state.
        boolean isSelf = player == client.player;

        TrinketAttachment attachment = TrinketsApi.getAttachment(player);

        for (AuraEffect effect : EFFECTS) {
            for (Item item : effect.registeredItems()) {
                if (!attachment.isEquipped(item)) continue;
                if (!effect.isEnabled(item)) continue;
                if (isSelf && !VfxItemToggles.isEnabled(item)) continue;
                if (!effect.trigger(item).test(player)) continue;

                effect.spawn(level, player, item, time);
            }
        }
    }
}
}