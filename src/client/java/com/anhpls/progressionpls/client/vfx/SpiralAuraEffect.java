package com.anhpls.progressionpls.client.vfx;

import com.anhpls.progressionpls.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Set;

public final class SpiralAuraEffect implements AuraEffect {

    public static final SpiralAuraEffect INSTANCE = new SpiralAuraEffect();
    private SpiralAuraEffect() {}

    public static void registerDefaults() {
        SpiralAuraRegistry.register(ModItems.SPROUT,
                new SpiralAuraRegistry.SpiralSpec(
                        ParticleTypes.END_ROD,
                        1.3, 0.0, 2.3, 30, 2.0, 1,
                        true, VfxTrigger.ALWAYS
                ));
    }

    @Override
    public Set<Item> registeredItems() {
        return SpiralAuraRegistry.all().keySet();
    }

    @Override
    public boolean isEnabled(Item item) {
        return SpiralAuraRegistry.get(item).enabled();
    }

    @Override
    public VfxTrigger trigger(Item item) {
        return SpiralAuraRegistry.get(item).trigger();
    }

    @Override
    public void spawn(ClientLevel level, Player player, Item item, long time) {
        SpiralAuraRegistry.SpiralSpec spec = SpiralAuraRegistry.get(item);
        int segments = spec.particlesPerTick();

        for (int i = 0; i < segments; i++) {
            double subTick = time - (i * 0.15);
            double progress = ((subTick % spec.cycleTicks()) + spec.cycleTicks()) % spec.cycleTicks() / spec.cycleTicks();

            double height = spec.heightMin() + progress * (spec.heightMax() - spec.heightMin());
            double angle = progress * spec.revolutions() * (2 * Math.PI);

            double x = player.getX() + spec.radius() * Math.cos(angle);
            double z = player.getZ() + spec.radius() * Math.sin(angle);
            double y = player.getY() + height;

            level.addParticle(spec.particle(), x, y, z, 0.0, 0.0, 0.0);
        }
    }
}