package com.anhpls.progressionpls.client.trinket;

import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.Map;

public class FaceAccessoryTuning {
    public static class Values {
        public float offsetX = 0f, offsetY = 0.25f, offsetZ = 0f;
        public float rotX = 0f, rotY = 0f, rotZ = 0f;
        public float scaleX = 1f, scaleY = 1f, scaleZ = 1f;

        public Values() {}

        public Values(float offsetX, float offsetY, float offsetZ,
                       float rotX, float rotY, float rotZ,
                       float scaleX, float scaleY, float scaleZ) {
            this.offsetX = offsetX; this.offsetY = offsetY; this.offsetZ = offsetZ;
            this.rotX = rotX; this.rotY = rotY; this.rotZ = rotZ;
            this.scaleX = scaleX; this.scaleY = scaleY; this.scaleZ = scaleZ;
        }
    }

    private static final Map<Item, Values> DATA = new HashMap<>();

    public static Values get(Item item) {
        return DATA.computeIfAbsent(item, i -> new Values());
    }

    public static void setDefault(Item item, Values values) {
        DATA.put(item, values);
    }
}