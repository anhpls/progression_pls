package com.anhpls.progressionpls.item;

import com.anhpls.progressionpls.Progression_pls;
import eu.pb4.trinkets.api.component.TrinketDataComponents;
import eu.pb4.trinkets.api.component.TrinketsAttributeModifiersComponent;
import eu.pb4.trinkets.impl.component.TrinketEquippableImpl;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

public class ModItems {

    /*
    * EGG TOAST ATTRIBUTES
    */
    private static final ResourceKey<Item> EGG_TOAST_KEY =
            ResourceKey.create(Registries.ITEM, Progression_pls.id("egg_toast"));

    public static final Item EGG_TOAST = new Item(new Item.Properties()
            .setId(EGG_TOAST_KEY)
            .stacksTo(1)
            .component(TrinketDataComponents.EQUIPMENT,
                    TrinketEquippableImpl.DEFAULT.withSlots("head/face"))
            .component(TrinketDataComponents.ATTRIBUTE_MODIFIERS,
                    TrinketsAttributeModifiersComponent.builder()
                            .add(Attributes.MAX_HEALTH,
                                    new AttributeModifier(Progression_pls.id("egg_toast/max_health"), 2.0, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(Progression_pls.id("egg_toast/movement_speed"), 0.02, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.LUCK,
                                    new AttributeModifier(Progression_pls.id("egg_toast/luck"), 0.4, AttributeModifier.Operation.ADD_VALUE))
                            .build()));

    /**
    * BROKEN GLASSES ATTRIBUTES
    */
    private static final ResourceKey<Item> BROKEN_GLASSES_KEY =
            ResourceKey.create(Registries.ITEM, Progression_pls.id("broken_glasses"));

    public static final Item BROKEN_GLASSES = new Item(new Item.Properties()
            .setId(BROKEN_GLASSES_KEY)
            .stacksTo(1)
            .component(TrinketDataComponents.EQUIPMENT,
                    TrinketEquippableImpl.DEFAULT.withSlots("head/face"))
            .component(TrinketDataComponents.ATTRIBUTE_MODIFIERS,
                    TrinketsAttributeModifiersComponent.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Progression_pls.id("broken_glasses/attack_damage"), 0.5, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(Progression_pls.id("broken_glasses/movement_speed"), 0.02, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.LUCK,
                                    new AttributeModifier(Progression_pls.id("broken_glasses/luck"), 0.4, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.ARMOR,
                                    new AttributeModifier(Progression_pls.id("broken_glasses/armor"), 1, AttributeModifier.Operation.ADD_VALUE))
                            .build()));
    /**
 * OAK SAPLING EARRINGS ATTRIBUTES
 */
private static final ResourceKey<Item> OAK_SAPLING_EARRINGS_KEY =
        ResourceKey.create(Registries.ITEM, Progression_pls.id("oak_sapling_earrings"));

public static final Item OAK_SAPLING_EARRINGS = new Item(new Item.Properties()
        .setId(OAK_SAPLING_EARRINGS_KEY)
        .stacksTo(1)
        .component(TrinketDataComponents.EQUIPMENT, TrinketEquippableImpl.DEFAULT.withSlots("accessory/earrings"))
        .component(TrinketDataComponents.ATTRIBUTE_MODIFIERS,
            TrinketsAttributeModifiersComponent.builder()
                    .add(Attributes.ARMOR_TOUGHNESS,
                            new AttributeModifier(Progression_pls.id("oak_sapling_earrings/armor_toughness"), 0.5, AttributeModifier.Operation.ADD_VALUE))
                    .add(Attributes.MAX_HEALTH,
                            new AttributeModifier(Progression_pls.id("oak_sapling_earrings/max_health"), 2.0, AttributeModifier.Operation.ADD_VALUE))
                    .add(Attributes.SNEAKING_SPEED,
                            new AttributeModifier(Progression_pls.id("oak_sapling_earrings/sneaking_speed"), 0.4, AttributeModifier.Operation.ADD_VALUE))
                    .add(Attributes.BLOCK_BREAK_SPEED,
                            new AttributeModifier(Progression_pls.id("oak_sapling_earrings/block_break_speed"), 0.2, AttributeModifier.Operation.ADD_VALUE))
                    .build()));

    /*
    * SPROUT ATTRIBUTES
    */
    private static final ResourceKey<Item> SPROUT_KEY =
            ResourceKey.create(Registries.ITEM, Progression_pls.id("sprout"));

    public static final Item SPROUT = new Item(new Item.Properties()
            .setId(SPROUT_KEY)
            .stacksTo(1)
            .component(TrinketDataComponents.EQUIPMENT,
                    TrinketEquippableImpl.DEFAULT.withSlots("head/hat"))
            .component(TrinketDataComponents.ATTRIBUTE_MODIFIERS,
                    TrinketsAttributeModifiersComponent.builder()
                            .add(Attributes.MAX_HEALTH,
                                    new AttributeModifier(Progression_pls.id("sprout/max_health"), 6.0, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.LUCK,
                                    new AttributeModifier(Progression_pls.id("sprout/luck"), 1.5, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.JUMP_STRENGTH,
                                    new AttributeModifier(Progression_pls.id("sprout/jump_strength"), 0.1, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.ARMOR_TOUGHNESS,
                                    new AttributeModifier(Progression_pls.id("sprout/armor_toughness"), 0.5, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.SAFE_FALL_DISTANCE,
                                    new AttributeModifier(Progression_pls.id("sprout/safe_fall_distance"), 4.0, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.MOVEMENT_EFFICIENCY,
                                    new AttributeModifier(Progression_pls.id("sprout/movement_efficiency"), 0.5, AttributeModifier.Operation.ADD_VALUE))
                            .build()));

    /*
    * GOLDEN_CARROT_EMBLEM ATTRIBUTES
    */
    private static final ResourceKey<Item> GOLDEN_CARROT_EMBLEM_KEY =
            ResourceKey.create(Registries.ITEM, Progression_pls.id("golden_carrot_emblem"));

    public static final Item GOLDEN_CARROT_EMBLEM = new Item(new Item.Properties()
            .setId(GOLDEN_CARROT_EMBLEM_KEY)
            .stacksTo(1)
            .component(TrinketDataComponents.EQUIPMENT,
                    TrinketEquippableImpl.DEFAULT.withSlots("accessory/emblem"))
            .component(TrinketDataComponents.ATTRIBUTE_MODIFIERS,
                    TrinketsAttributeModifiersComponent.builder()
                            .add(Attributes.MAX_HEALTH,
                                    new AttributeModifier(Progression_pls.id("golden_carrot_emblem/max_health"), 6.0, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.LUCK,
                                    new AttributeModifier(Progression_pls.id("golden_carrot_emblem/luck"), 1.5, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.JUMP_STRENGTH,
                                    new AttributeModifier(Progression_pls.id("golden_carrot_emblem/jump_strength"), 0.1, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.ARMOR_TOUGHNESS,
                                    new AttributeModifier(Progression_pls.id("golden_carrot_emblem/armor_toughness"), 0.5, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.SAFE_FALL_DISTANCE,
                                    new AttributeModifier(Progression_pls.id("golden_carrot_emblem/safe_fall_distance"), 4.0, AttributeModifier.Operation.ADD_VALUE))
                            .add(Attributes.MOVEMENT_EFFICIENCY,
                                    new AttributeModifier(Progression_pls.id("golden_carrot_emblem/movement_efficiency"), 0.5, AttributeModifier.Operation.ADD_VALUE))
                            .build()));
                            


    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, EGG_TOAST_KEY, EGG_TOAST);
        Registry.register(BuiltInRegistries.ITEM, BROKEN_GLASSES_KEY, BROKEN_GLASSES);
        Registry.register(BuiltInRegistries.ITEM, OAK_SAPLING_EARRINGS_KEY, OAK_SAPLING_EARRINGS);
        Registry.register(BuiltInRegistries.ITEM, SPROUT_KEY, SPROUT);
        Registry.register(BuiltInRegistries.ITEM, GOLDEN_CARROT_EMBLEM_KEY, GOLDEN_CARROT_EMBLEM);

        CreativeModeTabEvents.modifyOutputEvent(ModCreativeTabs.FACE_ACCESSORIES_KEY)
                .register(entries -> {
                    entries.accept(EGG_TOAST);
                    entries.accept(BROKEN_GLASSES);
                });
        CreativeModeTabEvents.modifyOutputEvent(ModCreativeTabs.EARRINGS_KEY)
                .register(entries -> {
                    entries.accept(OAK_SAPLING_EARRINGS);
                });
        CreativeModeTabEvents.modifyOutputEvent(ModCreativeTabs.HEADWEAR_KEY)
                .register(entries -> {
                    entries.accept(SPROUT);
                });
        CreativeModeTabEvents.modifyOutputEvent(ModCreativeTabs.EMBLEM_KEY)
                .register(entries -> {
                    entries.accept(GOLDEN_CARROT_EMBLEM);
                });
    }
}