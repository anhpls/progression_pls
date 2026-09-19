package com.anhpls.progressionpls.client.vfx;

import com.anhpls.progressionpls.item.HeartAuraOptions;
import com.anhpls.progressionpls.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Set;

public final class HeartAuraEffect implements AuraEffect {

    public static final HeartAuraEffect INSTANCE = new HeartAuraEffect();
    private HeartAuraEffect() {}

    public static void registerDefaults() {
        HeartAuraRegistry.register(ModItems.HEART_RING,
                new HeartAuraRegistry.HeartSpec(
                        new HeartAuraOptions(0.08f, 0.2),
                        0.15, 1.3, 80, 24,
                        true, VfxTrigger.ALWAYS
                ));
    }

    @Override
    public Set<Item> registeredItems() {
        return HeartAuraRegistry.all().keySet();
    }

    @Override
    public boolean isEnabled(Item item) {
        return HeartAuraRegistry.get(item).enabled();
    }

    @Override
    public VfxTrigger trigger(Item item) {
        return HeartAuraRegistry.get(item).trigger();
    }

    @Override
    public void spawn(ClientLevel level, Player player, Item item, long time) {
        HeartAuraRegistry.HeartSpec spec = HeartAuraRegistry.get(item);
        if (time % spec.cycleTicks() != 0) return;

        // Player's horizontal "right" vector from yaw — this is what makes the
        // heart's local X axis (left/right across the curve) track facing direction
        // instead of being locked to a fixed world axis.
        double yawRad = Math.toRadians(player.getYRot());
        double forwardX = -Math.sin(yawRad);
        double forwardZ = Math.cos(yawRad);
        double rightX = -forwardZ;
        double rightZ = forwardX;

        double anchorX = player.getX();
        double anchorY = player.getY() + spec.centerHeight();
        double anchorZ = player.getZ();

        for (int i = 0; i < spec.pointsPerPulse(); i++) {
            double t = (2 * Math.PI) * i / spec.pointsPerPulse();

            double curveX = 16 * Math.pow(Math.sin(t), 3);
            double curveY = 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);

            double localX = curveX * spec.scale();
            double localY = curveY * spec.scale();

            // Rotate the curve's local X (left/right across the heart) into the
            // player's right vector; Y stays world-up so it doesn't tilt with pitch.
            double dispX = rightX * localX;
            double dispZ = rightZ * localX;
            double dispY = localY;

            // Spawn AT the anchor (center of the character); the custom
            // HeartAuraParticle reads dispX/dispY/dispZ as the target offset
            // to lerp toward over its own lifetime, then fades near the end —
            // so this is the FULL displacement to the curve point, unscaled.
            level.addParticle(
                    spec.particle(),
                    anchorX, anchorY, anchorZ,
                    dispX,
                    dispY,
                    dispZ
            );
        }
    }
}
