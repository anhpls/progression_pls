package com.anhpls.progressionpls.client.vfx;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public final class SpiralAuraRegistry {

    public record SpiralSpec(
            ParticleOptions particle,
            double radius,
            double heightMin,
            double heightMax,
            int cycleTicks,
            double revolutions,
            int particlesPerTick,
            boolean enabled,
            VfxTrigger trigger
    ) {}

    public static final SpiralSpec DEFAULT = new SpiralSpec(
            ParticleTypes.END_ROD, 0.7, 0.0, 2.1, 80, 2.0, 2, true, VfxTrigger.ALWAYS
    );

    private static final Map<Item, SpiralSpec> REGISTRY = new HashMap<>();

    public static void register(Item item, SpiralSpec spec) {
        REGISTRY.put(item, spec);
    }

    public static SpiralSpec get(Item item) {
        return REGISTRY.getOrDefault(item, DEFAULT);
    }

    public static Map<Item, SpiralSpec> all() {
        return REGISTRY;
    }
}