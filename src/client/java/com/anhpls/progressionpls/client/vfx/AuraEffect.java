package com.anhpls.progressionpls.client.vfx;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Set;

/** Shared contract for any continuous/state-conditional "aura" VFX system, so AuraVfx's one tick
 *  loop can drive every effect type without knowing its internals. */
public interface AuraEffect {
    Set<Item> registeredItems();
    boolean isEnabled(Item item);
    VfxTrigger trigger(Item item);
    void spawn(ClientLevel level, Player player, Item item, long time);
}