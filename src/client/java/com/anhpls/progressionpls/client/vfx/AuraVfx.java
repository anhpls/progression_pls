package com.anhpls.progressionpls.client.vfx;

import java.util.List;

import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public final class AuraVfx {

    private static final List<AuraEffect> EFFECTS = List.of(
            SpiralAuraEffect.INSTANCE
    );

    public static void register() {
        SpiralAuraEffect.registerDefaults();

        ClientTickEvents.END_CLIENT_TICK.register(AuraVfx::onClientTick);
    }

    private static void onClientTick(Minecraft client) {
    if (!ParticleToggle.isEnabled()) return;

    ClientLevel level = client.level;
    if (level == null) return;

    long time = level.getGameTime();

    for (Player player : level.players()) {
        TrinketAttachment attachment = TrinketsApi.getAttachment(player);

        for (AuraEffect effect : EFFECTS) {
            for (Item item : effect.registeredItems()) {
                if (!attachment.isEquipped(item)) continue;
                if (!effect.isEnabled(item)) continue;
                if (!effect.trigger(item).test(player)) continue;

                effect.spawn(level, player, item, time);
            }
        }
    }
}
}