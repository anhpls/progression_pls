package com.anhpls.progressionpls.client.vfx;

import com.anhpls.progressionpls.item.HeartAuraOptions;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;

public class HeartAuraParticle extends SingleQuadParticle {

    // The pace at outwardSpeed == 1.0 ("default"): 12 ticks to travel the full distance to
    // its target point. Size and speed both now arrive per-spawn via HeartAuraOptions,
    // baked in from whichever item's HeartSpec triggered this particle — see HeartAuraOptions
    // for why that couldn't be a constructor default here.
    private static final int BASE_LIFETIME_TICKS = 12;

    private final double startX, startY, startZ;
    private final double targetX, targetY, targetZ;

    protected HeartAuraParticle(ClientLevel level, double x, double y, double z,
                                 double targetOffsetX, double targetOffsetY, double targetOffsetZ,
                                 TextureAtlasSprite sprite, float size, double speed) {
        super(level, x, y, z, sprite);

        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.targetX = x + targetOffsetX;
        this.targetY = y + targetOffsetY;
        this.targetZ = z + targetOffsetZ;

        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        this.hasPhysics = false;
        this.lifetime = Math.max(1, Math.round(BASE_LIFETIME_TICKS / (float) speed));
        this.quadSize = size;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        float t = (float) this.age / (float) this.lifetime;
        float eased = 1f - (1f - t) * (1f - t); // ease-out

        this.x = lerp(startX, targetX, eased);
        this.y = lerp(startY, targetY, eased);
        this.z = lerp(startZ, targetZ, eased);

        this.alpha = 1f - t;
    }

    private static double lerp(double from, double to, float t) {
        return from + (to - from) * t;
    }

    @Override
    protected Layer getLayer() {
        return Layer.bySprite(this.sprite);
    }

    public static class Provider implements ParticleProvider<HeartAuraOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public SingleQuadParticle createParticle(HeartAuraOptions options, ClientLevel level,
                                                   double x, double y, double z,
                                                   double dx, double dy, double dz,
                                                   RandomSource random) {
            return new HeartAuraParticle(level, x, y, z, dx, dy, dz, sprites.get(0, 1), options.size(), options.speed());
        }
    }
}
