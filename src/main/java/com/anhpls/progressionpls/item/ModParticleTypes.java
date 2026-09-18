package com.anhpls.progressionpls.item;

import com.anhpls.progressionpls.Progression_pls;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class ModParticleTypes {

    // "complex" (carries HeartAuraOptions' own size/speed data per spawn) rather than
    // "simple" (a bare marker with no fields) — see HeartAuraOptions for why.
    public static final ParticleType<HeartAuraOptions> HEART_AURA =
            Registry.register(BuiltInRegistries.PARTICLE_TYPE, Progression_pls.id("heart_aura"),
                    FabricParticleTypes.complex(HeartAuraOptions.CODEC, HeartAuraOptions.STREAM_CODEC));

    public static void register() {
        // referencing this class triggers the static field above — call this once from
        // the mod's common onInitialize() just to guarantee registration actually happens
    }
}
