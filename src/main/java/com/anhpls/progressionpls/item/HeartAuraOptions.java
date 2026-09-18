package com.anhpls.progressionpls.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * Per-spawn size + speed for a heart-aura particle. Vanilla's addParticle(...) only ever
 * passes position and velocity through to the spawned Particle — a SimpleParticleType (a
 * bare marker with no fields of its own) can't carry anything else, which is why an earlier
 * version of this made size/speed *global* settings shared by every item wearing the effect.
 * This carries them on the particle options themselves instead, the same way vanilla's
 * DustParticleOptions carries a color and scale, so each item's HeartSpec can bake in its
 * own values and they travel with whatever spawns them.
 *
 * Lives in the common source set (not client/vfx) because particle types and their options
 * have to be registered — and be decodable — on both sides, same as any vanilla particle.
 * Only the actual rendering (HeartAuraParticle) is client-only.
 */
public record HeartAuraOptions(float size, double speed) implements ParticleOptions {

    public static final MapCodec<HeartAuraOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("size").forGetter(HeartAuraOptions::size),
            Codec.DOUBLE.fieldOf("speed").forGetter(HeartAuraOptions::speed)
    ).apply(instance, HeartAuraOptions::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, HeartAuraOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, HeartAuraOptions::size,
            ByteBufCodecs.DOUBLE, HeartAuraOptions::speed,
            HeartAuraOptions::new
    );

    @Override
    public ParticleType<HeartAuraOptions> getType() {
        return ModParticleTypes.HEART_AURA;
    }
}
