package com.anhpls.progressionpls.item;

import com.anhpls.progressionpls.Progression_pls;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

        /*
         * FACE ACCESSORIES
         */
        public static final ResourceKey<CreativeModeTab> FACE_ACCESSORIES_KEY = ResourceKey
                        .create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Progression_pls.id("face_accessories"));

        public static final CreativeModeTab FACE_ACCESSORIES = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(ModItems.EGG_TOAST))
                        .title(Component.translatable("itemGroup.progression_pls.face_accessories"))
                        .build();

        /*
         * EARRINGS
         */
        public static final ResourceKey<CreativeModeTab> EARRINGS_KEY = ResourceKey
                        .create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Progression_pls.id("earrings"));

        public static final CreativeModeTab EARRINGS = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(ModItems.OAK_SAPLING_EARRINGS))
                        .title(Component.translatable("itemGroup.progression_pls.earrings"))
                        .build();

        /*
         * HEADWEAR
         */
        public static final ResourceKey<CreativeModeTab> HEADWEAR_KEY = ResourceKey
                        .create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Progression_pls.id("headwear"));

        public static final CreativeModeTab HEADWEAR = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(ModItems.SPROUT))
                        .title(Component.translatable("itemGroup.progression_pls.headwear"))
                        .build();

        /*
         * EMBLEMS
         */
        public static final ResourceKey<CreativeModeTab> EMBLEMS_KEY = ResourceKey
                        .create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Progression_pls.id("emblems"));

        public static final CreativeModeTab EMBLEMS = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(ModItems.GOLDEN_CARROT_EMBLEM))
                        .title(Component.translatable("itemGroup.progression_pls.emblems"))
                        .build();

        /*
         * RINGS
         */
        public static final ResourceKey<CreativeModeTab> RINGS_KEY = ResourceKey
                        .create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Progression_pls.id("rings"));

        public static final CreativeModeTab RINGS = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(ModItems.HEART_RING))
                        .title(Component.translatable("itemGroup.progression_pls.rings"))
                        .build();

        public static void register() {
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FACE_ACCESSORIES_KEY, FACE_ACCESSORIES);
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, EARRINGS_KEY, EARRINGS);
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, HEADWEAR_KEY, HEADWEAR);
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, EMBLEMS_KEY, EMBLEMS);
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, RINGS_KEY, RINGS);
        }
}