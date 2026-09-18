package com.anhpls.progressionpls.client.vfx;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public enum VfxTrigger {
    ALWAYS {
        @Override public boolean test(Player player) { return true; }
    },
    STANDING {
        @Override public boolean test(Player player) {
            return player.onGround() && isRoughlyStill(player);
        }
    },
    JUMPING {
        @Override public boolean test(Player player) {
            return !player.onGround() && player.getDeltaMovement().y > 0;
        }
    },
    MOVING {
        @Override public boolean test(Player player) {
            return player.onGround() && !isRoughlyStill(player);
        }
    };

    public abstract boolean test(Player player);

    protected static boolean isRoughlyStill(Player player) {
        Vec3 v = player.getDeltaMovement();
        return (v.x * v.x + v.z * v.z) < 0.0025;
    }
}