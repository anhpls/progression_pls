package com.anhpls.progressionpls.loot;

import java.util.List;
import java.util.Set;

import com.anhpls.progressionpls.item.ModItems;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModLootTables {

    private record DungeonLootEntry(Item item, float chance) {
    }

    private static final List<DungeonLootEntry> DUNGEON_LOOT_ITEMS = List.of(
            new DungeonLootEntry(ModItems.EGG_TOAST, 0.15f),
            new DungeonLootEntry(ModItems.BROKEN_GLASSES, 0.15f),
            new DungeonLootEntry(ModItems.SPROUT, 0.08f),
            new DungeonLootEntry(ModItems.GOLDEN_CARROT_EMBLEM, 0.03f),
            new DungeonLootEntry(ModItems.HEART_RING, 0.03f)
    );

    private static final Set<Identifier> DUNGEON_CHEST_TABLES = Set.of(
            Identifier.fromNamespaceAndPath("minecraft", "chests/simple_dungeon"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/abandoned_mineshaft"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/stronghold_corridor"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/stronghold_crossing"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/stronghold_library"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/desert_pyramid"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/jungle_temple"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/woodland_mansion"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/ancient_city"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/buried_treasure"),
            Identifier.fromNamespaceAndPath("minecraft", "chests/shipwreck_supply")
    );

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, holder) -> {
            Identifier id = key.identifier();

            if (DUNGEON_CHEST_TABLES.contains(id)) {
                for (DungeonLootEntry entry : DUNGEON_LOOT_ITEMS) {
                    tableBuilder.withPool(
                            LootPool.lootPool()
                                    .add(LootItem.lootTableItem(entry.item()))
                                    .when(LootItemRandomChanceCondition.randomChance(entry.chance()))
                    );
                }
            }
        });
    }
}