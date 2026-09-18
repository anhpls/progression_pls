package com.anhpls.progressionpls.client.vfx;

import net.minecraft.core.particles.ParticleOptions;
import com.anhpls.progressionpls.item.HeartAuraOptions;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public final class HeartAuraRegistry {

    // `particle` stays a plain ParticleOptions so /vfxtune <item> particle can still swap an
    // item onto a totally different (e.g. vanilla) particle if wanted. When it's actually a
    // HeartAuraOptions instance, that instance is where this item's own size/speed live —
    // see HeartAuraOptions for why they can't be separate fields here instead.
    public record HeartSpec(
            ParticleOptions particle,
            double scale,
            double centerHeight,
            int cycleTicks,
            int pointsPerPulse,
            boolean enabled,
            VfxTrigger trigger
    ) {}

    public static final HeartSpec DEFAULT = new HeartSpec(
        new HeartAuraOptions(0.06f, 1.0), 0.03, 1.3, 20, 24, true, VfxTrigger.ALWAYS
    );

    private static final Map<Item, HeartSpec> REGISTRY = new HashMap<>();

    public static void register(Item item, HeartSpec spec) {
        REGISTRY.put(item, spec);
    }

    public static HeartSpec get(Item item) {
        return REGISTRY.getOrDefault(item, DEFAULT);
    }

    public static Map<Item, HeartSpec> all() {
        return REGISTRY;
    }
}
