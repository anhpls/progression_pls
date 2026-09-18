- - - - - - # ProgressionPLS — New Item Workflow

            Reference for adding a new vanilla+ accessory to the `progression_pls` Fabric mod (mod ID `progression_pls`, package `com.anhpls.progressionpls`). Based on the Egg Toast, Broken Glasses, and Oak Sapling Earrings build-outs.

            Placeholders: `<id>` = item's snake_case id (e.g. `egg_toast`), `<profession>` = vanilla villager profession, `<level>` = trade tier 1–5.

            ------

            ## Reference — read once, keep in mind

            **Design philosophy:** vanilla+ only, no dependency on Jewelry — but "no dependency" has layers, worth keeping straight:

            - **Java code:** zero. No Jewelry classes imported or referenced anywhere. Confirmed since the mod was scaffolded.
            - **Crafting ingredients:** zero. No Jewelry item (gems, rings, etc.) is ever used as a recipe ingredient — items are built from vanilla ingredients only. This was reaffirmed explicitly: even though it wouldn't crash anything (a recipe referencing a missing item id just fails to load quietly), it would make that item permanently uncraftable without Jewelry installed, which defeats the point.
            - **Villager trades / dungeon loot:** the one place a *soft*, data-only coupling is fine — e.g. the earlier Jewelry Jeweler-trade piggyback (since replaced with vanilla Butcher trades instead) or referencing Jewelry's own loot tables. These degrade gracefully (just don't fire) if Jewelry isn't installed, so they don't count as a real dependency the way a recipe ingredient would.

            **Villager profession per accessory category** (spreads trades across underused vanilla villagers):

            | Profession    | Level      | Category                        | Status                             |
            | ------------- | ---------- | ------------------------------- | ---------------------------------- |
            | Butcher       | 5 (master) | Face/head slot                  | In use (Egg Toast, Broken Glasses) |
            | Leatherworker | —          | Rings / hand slot               | Planned                            |
            | Mason         | —          | Necklaces / chest slot          | Planned                            |
            | —             | —          | Earrings (`accessory/earrings`) | Not yet assigned a profession      |

            **Trinkets default slots** (built into Trinkets Updated — no other mod needed):

            | Group     | Slots                                        |
            | --------- | -------------------------------------------- |
            | `hand`    | `hand/ring`, `hand/glove`                    |
            | `offhand` | `offhand/ring`, `offhand/glove`              |
            | `head`    | `head/face`, `head/hat`                      |
            | `chest`   | `chest/back`, `chest/cape`, `chest/necklace` |
            | `legs`    | `legs/belt`                                  |
            | `feet`    | `feet/shoes`, `feet/aglet`                   |

            All 12 of Trinkets' built-in slots are granted to the player (`data/trinkets/entities/progression_pls.json`), confirmed directly from Trinkets' own `#trinkets:all` tag file. `head/hat` is already granted — a new hat item needs **no entities-file change**, just the item itself plus the renderer registration described below.

            **Our own custom slots** (defined by us, zero dependency on any other mod — replaces what used to be Spell Engine's `spell/trinket`):

            | Slot                 | Icon source                                                  | Claimed by                                                   |
            | -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
            | `accessory/earrings` | our own `earrings.png`, `progression_pls:container/slots/earrings` | Oak Sapling Earrings                                         |
            | `accessory/emblem`   | reuses Trinkets' own unused `charm` icon, `trinkets:container/slots/charm` | Planned (was "quiver")                                       |
            | `accessory/ring`     | reuses Trinkets' own `ring` icon, `trinkets:container/slots/ring` | Planned (was "spellbook") — lets a player wear 2 rings total alongside `hand/ring` |

            These are defined via `data/trinkets/slots/accessory/<name>.json` (each just `{ "icon": "<namespace>:container/slots/<name>" }`) and granted in `data/trinkets/entities/progression_pls.json` alongside the 12 built-ins. **Icon path gotcha:** the correct prefix is `container/slots/<name>` (matching Minecraft's real GUI sprite-atlas folder layout, `textures/gui/sprites/container/slots/...` with the `textures/gui/sprites/` part dropped) — **not** `gui/slots/<name>`, which is what Trinkets' own wiki example shows and is wrong. Using the wrong prefix renders as a purple/black missing-texture checkerboard.

            **Jewelry, Spell Engine, and their dependency chain have been fully removed.** Nothing in `progression_pls` depends on Jewelry, Spell Engine, `spell_power`, `ranged_weapon_api`, `structure_pool_api`, `playerAnimationLibMerged`, or Cloth Config — not in Java, not in `fabric.mod.json`, not in any data file. The mod's real dependency list is just: Fabric Loader, Fabric API, Trinkets Updated, and `progression_pls.jar` itself, required identically on the server and every connecting client (Fabric disconnects on any mod-set mismatch when real Items are registered).

            **Rarity tier system** — every item belongs to one of four tiers *so far*, which sets both its dungeon loot odds and its stat budget. Anchor point: Common loot odds ≈ a vanilla Golden Apple (~15-20% per qualifying chest); OP loot odds ≈ a vanilla Enchanted Golden Apple (~1-2% per chest). Stats roughly double in potency each tier up. An item picks 2-3 of the stat lines below, not necessarily all of them — and since multiple accessories can be worn at once (different Trinkets slots), keep an eye on what a realistic *combination* of 2 items adds up to, not just one item in isolation (rule of thumb used throughout: two Common items worn together should feel like roughly one Rare item, not two Rares).

            **The table below is a set of estimates/starting points, not exact numbers to paste in every time.** Pick the nearest tier column as an anchor, then nudge individual values up or down to fit the specific item's theme — an item doesn't need to hit the table's number precisely, just land in the right neighborhood for its tier. More rarity tiers than the current four (Common/Uncommon/Rare/OP) are planned; when a new tier gets added, it slots into the same "roughly double per tier" progression rather than needing the whole table re-derived from scratch.

            | Tier     | Loot odds | Health         | Speed | Attack Damage | Luck | Armor Toughness | Sneaking Speed | Block Break Speed |
            | -------- | --------- | -------------- | ----- | ------------- | ---- | --------------- | -------------- | ----------------- |
            | Common   | ~15%      | +2 (1 heart)   | +0.02 | +0.5          | +0.4 | +0.5            | +0.06          | +0.2              |
            | Uncommon | ~8%       | +4 (2 hearts)  | +0.04 | +1.5          | +0.8 | +1.0            | +0.12          | +0.4              |
            | Rare     | ~3%       | +6 (3 hearts)  | +0.07 | +3            | +1.5 | +2.0            | +0.22          | +0.75             |
            | OP       | ~1-2%     | +10 (5 hearts) | +0.12 | +6            | +3   | +3.5            | +0.4           | +1.4              |

            Items so far: Egg Toast, Broken Glasses, and Oak Sapling Earrings are all Common tier — these first few were built mainly to fill out the mod and test slots, so they were deliberately leveled down to Common rather than kept at the higher numbers they started with. **Open TODO:** Broken Glasses' dungeon loot chance in `ModLootTables.java` is still `0.03f` (3%, Rare-tier odds) left over from before it was reclassified as Common — worth bumping to `0.15f` to match, unless a deliberate "rare drop, modest power" design is wanted instead. OP-tier attack damage (+6) is intentionally kept just under a netherite sword's own +7 bonus, so even the strongest accessory doesn't single-handedly trivialize combat on its own.

            Armor toughness is intentionally modest at every tier and assumes the player already has at least leather/iron armor equipped — the attribute does nothing on an unarmored player (toughness only affects the damage-reduction formula in combination with the separate `armor` attribute value from actual armor pieces), so it's meant as a "helps your real armor scale better" progression nudge, not standalone protection.

            **AttributeModifier operations — ADD_VALUE vs ADD_MULTIPLIED_BASE vs ADD_MULTIPLIED_TOTAL:** every stat picks one of three math modes, set as the third argument to `new AttributeModifier(id, amount, operation)`. **Every number in every table in this doc uses `ADD_VALUE`** — that's a deliberate, consistent choice, explained below so it's clear why and when the other two would matter instead.

            - **`ADD_VALUE`** (the default for this mod — what all the numbers below assume): `amount` is added straight onto the attribute, no conversion needed. The table says `+0.5` attack damage → the player gets exactly `+0.5` attack damage. Multiple items using `ADD_VALUE` on the same attribute just add together normally (two `+0.5` items = `+1.0` total) — predictable, and it's why every item in this mod uses it.
            - **`ADD_MULTIPLIED_BASE`**: `amount` is a decimal fraction of that attribute's own **vanilla default value**, not the final bonus you want. It computes `amount × vanilla_base` once and adds *that*. The catch, and the source of most of the confusion: vanilla's base is different for every attribute — `1` for attack damage, `0.1` for movement speed, `20` for max health, `0.3` for sneaking speed, `1.0` for block break speed, and so on — so typing the same number into two different attributes gives two very different real effects. To hit a target flat bonus with this operation, work backwards: `amount = target_bonus ÷ vanilla_base`. Example: wanting a real `+0.02` movement speed bonus (vanilla base `0.1`) means the `amount` you type is `0.2`, not `0.02`. The one place this operation happens to *look* identical to `ADD_VALUE` is `attack_damage` specifically, and only because a player's base attack damage is exactly `1` (`amount × 1 = amount`) — that coincidence does not carry over to any other attribute, which is exactly the trap to avoid.
            - **`ADD_MULTIPLIED_TOTAL`** (not used anywhere in this mod so far): multiplies whatever the attribute's running total already is — after every other modifier from every currently-equipped item has been summed — by `(1 + amount)`, and it compounds with any other `ADD_MULTIPLIED_TOTAL` modifiers on that same attribute rather than adding to them. This is the one to watch for the "don't let two items combine into OP" concern from earlier: two separate `+20%` `ADD_MULTIPLIED_TOTAL` modifiers on the same stat don't add up to `+40%`, they compound to `1.2 × 1.2 = +44%`. Best avoided for accessories unless that compounding is specifically wanted, since it makes the effect of combining items much harder to predict at a glance.

            **Bottom line:** stick with `ADD_VALUE` for everything, which is already the convention for every item and every number in the tables below — it needs no percentage math, and stacking multiple items stays simple addition, matching exactly how the tier tables were designed to combine.

            **No durability** on any item by default (no `.enchantable()` or `.durability()` set) — would need explicit opt-in work per item, and Trinkets slots aren't part of vanilla's automatic "armor takes damage when hit" system anyway.

            **Attribute options with tier-scaled numbers** — any `net.minecraft.world.entity.ai.attributes.Attributes` constant can go in an item's `trinkets:attribute_modifiers` component. The tables below cover every attribute worth considering for a player accessory, with a rough Common → OP progression using the same "~2x per tier, OP capped short of trivializing the game" logic as the main table above. Only the seven in the main table above (Health, Speed, Attack Damage, Luck, Armor Toughness, Sneaking Speed, Block Break Speed) have actually been compiled and tested in-game so far — everything else here is a calibrated starting point for when you build an item that needs it, not a verified number. Treat anything marked ⚠️ as extra-uncertain (vanilla's own base value/unit for that attribute wasn't fully pinned down) and sanity-check it in-game before committing to it. **Operation: every number below is written as `ADD_VALUE`** — plug it straight into the `amount` argument with no conversion, per the explanation above.

            *Combat:*

            | Attribute               | Vanilla base | Common       | Uncommon      | Rare          | OP             | Notes                                                        |
            | ----------------------- | ------------ | ------------ | ------------- | ------------- | -------------- | ------------------------------------------------------------ |
            | `attack_damage`         | 1            | +0.5         | +1.5          | +3            | +6             | OP kept just under netherite sword's own +7                  |
            | `attack_speed`          | 4.0          | +0.1         | +0.2          | +0.4          | +0.7           | Attacks-per-second bonus; weapons already apply their own modifiers on top |
            | `attack_knockback`      | 0            | +0.2         | +0.4          | +0.7          | +1.2           | Roughly Knockback I–II enchant range at the top end          |
            | `armor`                 | 0 (max 30)   | +1           | +2            | +3            | +4             | Each point ≈ 4% extra damage reduction; kept below one real armor piece even at OP |
            | `armor_toughness`       | 0 (max 20)   | +0.5         | +1.0          | +2.0          | +3.5           | No-op without real armor equipped — see note above           |
            | `knockback_resistance`  | 0 (0–1)      | +0.02        | +0.05         | +0.1          | +0.2           | Full netherite armor set totals 0.4; OP stays well under that alone |
            | `max_absorption`        | 0            | +2 (1 heart) | +4 (2 hearts) | +6 (3 hearts) | +10 (5 hearts) | Same unit as health (2 per heart)                            |
            | `sweeping_damage_ratio` | 0            | +0.05        | +0.1          | +0.2          | +0.35          | Passive partial-Sweeping-Edge-like AoE bleed                 |

            *Survival/mobility:*

            | Attribute                   | Vanilla base | Common       | Uncommon      | Rare          | OP             | Notes                                                        |
            | --------------------------- | ------------ | ------------ | ------------- | ------------- | -------------- | ------------------------------------------------------------ |
            | `max_health`                | 20           | +2 (1 heart) | +4 (2 hearts) | +6 (3 hearts) | +10 (5 hearts) |                                                              |
            | `movement_speed`            | 0.1          | +0.02        | +0.04         | +0.07         | +0.12          |                                                              |
            | `luck`                      | 0            | +0.4         | +0.8          | +1.5          | +3             | Only affects fishing loot tables currently                   |
            | `safe_fall_distance`        | 3 blocks     | +1           | +2            | +4            | +7             | Extra blocks before fall damage starts                       |
            | `fall_damage_multiplier`    | 1.0          | -0.1         | -0.2          | -0.4          | -0.7           | Reduces the fall-damage multiplier itself; OP stays short of full immunity (-1.0) |
            | `jump_strength`             | 0.42         | +0.05        | +0.1          | +0.2          | +0.35          | Noticeably higher jumps at OP, short of Jump Boost V         |
            | `step_height`               | 0.6          | +0.1         | +0.4          | +0.9          | +1.4           | Uncommon (1.0) matches horse-level auto-step; OP (2.0) auto-steps nearly 2 blocks |
            | `oxygen_bonus` ⚠️            | 0            | +5           | +10           | +20           | +40            | Unit/scale not fully pinned down — test underwater before relying on this |
            | `sneaking_speed`            | 0.3          | +0.06        | +0.12         | +0.22         | +0.4           |                                                              |
            | `movement_efficiency`       | 0 (0–1)      | +0.1         | +0.25         | +0.5          | +0.85          | Reduces cobweb/soul sand-type slowdown; kept short of full 1.0 |
            | `water_movement_efficiency` | 0 (0–1)      | +0.1         | +0.25         | +0.5          | +0.85          | Same idea, underwater movement penalty instead               |

            *Mining/interaction:*

            | Attribute                  | Vanilla base | Common | Uncommon | Rare  | OP   | Notes                                                        |
            | -------------------------- | ------------ | ------ | -------- | ----- | ---- | ------------------------------------------------------------ |
            | `mining_efficiency`        | 0            | +1     | +2       | +4    | +7   | Roughly a couple Efficiency-enchant-levels' worth            |
            | `submerged_mining_speed`   | 0.2          | +0.1   | +0.2     | +0.4  | +0.8 | OP nears full speed underwater without a real Aqua Affinity helmet |
            | `block_interaction_range`  | 4.5 blocks   | +0.5   | +1.0     | +1.5  | +2.5 | Reach for placing/breaking blocks                            |
            | `entity_interaction_range` | 3.0 blocks   | +0.5   | +1.0     | +1.75 | +2.5 | Kept modest at OP so it doesn't read as a PvP reach-hack     |
            | `block_break_speed`        | 1.0          | +0.2   | +0.4     | +0.75 | +1.4 |                                                              |

            *Oddball but real:*

            | Attribute                        | Vanilla base      | Common | Uncommon | Rare   | OP     | Notes                                                        |
            | -------------------------------- | ----------------- | ------ | -------- | ------ | ------ | ------------------------------------------------------------ |
            | `scale`                          | 1.0               | +0.02  | +0.05    | +0.08  | +0.12  | Subtle — large changes distort hitbox/collision oddly        |
            | `gravity`                        | 0.08 blocks/tick² | -0.004 | -0.008   | -0.016 | -0.028 | Roughly -5%/-10%/-20%/-35% of vanilla base; floatier jumps/falls, themed for a "feather"-type item |
            | `burning_time`                   | 1.0 (multiplier)  | -0.1   | -0.2     | -0.4   | -0.7   | Shorter fire duration when set alight; not full fire immunity |
            | `explosion_knockback_resistance` | 0 (0–1)           | +0.05  | +0.1     | +0.2   | +0.35  |                                                              |
            | `waypoint_transmit_range` ⚠️      | uncertain         | +500   | +1000    | +2500  | +5000  | Locator-bar visibility distance in blocks; vanilla's own default wasn't confirmed — test in-game |
            | `waypoint_receive_range` ⚠️       | uncertain         | +500   | +1000    | +2500  | +5000  | Same caveat as above                                         |

            *Skip these for player accessories:* `tempt_range` and `follow_range` are mob-AI only; `camera_distance` is a spectator/dev value. None of these do anything useful on a player, so no tier numbers are given for them.

            **Multi-slot id collision gotcha:** every `AttributeModifier`'s id (the `Progression_pls.id("<item>/<stat>")` string) must be unique **per item**, not reused across items — even copy-pasting between items that use different equip slots. Two items with the same modifier id on the same attribute can both be worn simultaneously (different Trinkets slots), and since the game tracks active modifiers by id, a collision means one silently doesn't stack the way you'd expect instead of both applying. Always rename the id when copy-pasting a new item's Java block.

            **Creative tab per slot category** — currently "Face Accessories" (`FACE_ACCESSORIES_KEY`) and "Earrings" (`EARRINGS_KEY`) in `ModCreativeTabs.java`. A new category (e.g. first ring) needs its own tab added there, following the same pattern.

            **Building the on-character model in Blockbench** (art step, separate from the Java rendering step below):

            - `File > New Project > Generic Model` — this is the project type that exports plain Minecraft Java block/item model JSON (cuboid `elements` + face UVs), which is what a custom item model needs instead of the flat `item/generated` parent.
            - Right-click in the **Textures** panel → load the existing PNG (the Procreate export) as the model's texture, or build a separate texture for a pure 3D visualization model (see Egg Toast — its 3D model deliberately does *not* reuse the same texture path as the flat inventory icon, since it's a placement/shape preview rather than a repaint).
            - Add cuboid(s) shaped roughly like the item (`Add Cube`) — flat items (glasses, a leaf earring, a toast slice) usually only need 1-2 thin cuboids.
            - **Texturing without repainting** — two options: (a) **Per-Face UV**, set manually per cuboid — double-click a face in the UV editor and drag/resize its UV rectangle to point at existing art; reliable for a single flat icon. (b) **Box UV** — auto-derives all 6 faces from one texture region using vanilla's block-style layout; only correct if the texture was laid out for that scheme.
            - **Common export bugs, check every time:** Blockbench often leaves texture keys pointing at its own placeholders (`"0": "block/texture"`) — must be manually corrected to the real `progression_pls:item/<id>` path. Separately, any face left unassigned exports as `"texture": "#missing"`, which renders as the black/magenta checkerboard in-game. Blockbench's own viewport can look completely fine while the exported JSON is broken in both these ways — always grep the exported file for `#missing` and for leftover `block/texture`/`texture` placeholder strings before using it.
            - Export via `File > Export > Export Java Block/Item Model`, save to `assets/progression_pls/models/item/<id>.json` (or a distinctly-named file like `<id>_3d.json` if it's a separate visualization model, not meant to replace the flat inventory icon).
            - **Set a `head` display entry in Blockbench's Display panel** (rotation/translation/scale) even though it has no effect on vanilla — this is reused directly by the Java rendering system below via `ItemDisplayContext.HEAD`, so tuning it visually in Blockbench first gives a good starting point instead of tuning purely blind in-game.

            **Important — this model file is separate from the item's real inventory icon unless you want it to be.** Egg Toast's on-character 3D model intentionally uses its own texture, different from `assets/progression_pls/textures/item/egg_toast.png` used by the flat inventory model — confirmed explicitly: "i didnt model using my procreate png in blockbench, i just used blockbench to make a 3d version... so i can show how it'll sit on the mouth." Reusing the same texture for both is fine too, just a deliberate choice either way, not an accident.

            ------

            ## On-character rendering through Trinkets custom slots (head/face, head/hat)

            **Corrects earlier guidance in this doc:** there is no purely data-driven/JSON way to position an item on a player through a Trinkets *custom* slot (`head/face`, `accessory/earrings`, etc.) — confirmed against Trinkets Updated's actual decompiled API. A `display.head` block in the model JSON only ever affects vanilla's own real helmet slot; a custom Trinkets slot has no vanilla code path rendering it automatically at all. Positioning for a custom slot is Java-only, via the `TrinketRenderer` interface (`eu.pb4.trinkets.api.client.TrinketRenderer`) registered client-side through `TrinketRendererRegistry`.

            **The system now in place (head-anchored slots only — `head/face` and `head/hat` — see scope note at the bottom):**

            - **`src/client/java/com/anhpls/progressionpls/client/trinket/FaceAccessoryTrinketRenderer.java`** — one shared `TrinketRenderer` implementation used by every head-anchored item, in **any** head slot (`head/face` or `head/hat`). Casts the context model to `HeadedModel`, calls `TrinketRenderer.translateToHead(...)` to anchor to the head bone, applies that item's tuning values (offset → rotation → scale, in that order), then resolves and submits the item's own model via `Minecraft.getInstance().getItemModelResolver().updateForTopItem(...)` with `ItemDisplayContext.HEAD` — which is what pulls in the `head` display block you set in Blockbench. **This file is keyed purely by `stack.getItem()` via `FaceAccessoryTuning.get(...)` — it has no per-item branches or item-specific code at all, so it never needs to be touched when adding a new item, whether that item is a `head/face` or a `head/hat` accessory.**

            - **`src/client/java/com/anhpls/progressionpls/client/trinket/FaceAccessoryTuning.java`** — a `Map<Item, Values>` holding 9 live-tunable floats per item (`offsetX/Y/Z`, `rotX/Y/Z`, `scaleX/Y/Z`), defaulting to `offset(0, 0.25, 0)`, `rotation(0,0,0)`, `scale(1,1,1)`. Also never needs per-item edits — `get(Item)` auto-creates a default entry the first time an item is looked up.

            - **`src/client/java/com/anhpls/progressionpls/client/TuningCommands.java`** — registers a client-side `/toasttune` command (via Fabric API's `net.fabricmc.fabric.api.client.command.v2.ClientCommands` / `ClientCommandRegistrationCallback` — note the class is `ClientCommands`, not the similarly-named older `ClientCommandManager`) that edits `FaceAccessoryTuning` live, no rebuild needed:

              - `/toasttune <item> <field> <value>` — sets one of the 9 fields (`offsetX`, `offsetY`, `offsetZ`, `rotX`, `rotY`, `rotZ`, `scaleX`, `scaleY`, `scaleZ`) on that item, e.g. `/toasttune egg_toast offsetY 0.3`
              - `/toasttune <item> show` — prints that item's current 9 values to chat
              - `/toasttune <item> reset` — snaps that item back to the defaults above
              - `<item>` is just the item's own registered path (`egg_toast`, `broken_glasses`, etc.) — resolved automatically against `progression_pls:<item>` via the registry, so **no code change is ever needed in this file to support a new item name.**

            - **`Progression_plsClient.java`** (`onInitializeClient()`) — creates one shared `FaceAccessoryTrinketRenderer` instance, registers it per item, and (once tuning is finalized) bakes in each item's permanent values:

              ```java
              FaceAccessoryTrinketRenderer faceRenderer = new FaceAccessoryTrinketRenderer();TrinketRendererRegistry.registerRenderer(ModItems.EGG_TOAST, faceRenderer);TrinketRendererRegistry.registerRenderer(ModItems.BROKEN_GLASSES, faceRenderer);TuningCommands.register();// Baked-in final positioning, copied from "/toasttune <item> show" output:FaceAccessoryTuning.setDefault(ModItems.EGG_TOAST,    new FaceAccessoryTuning.Values(        -0.01f, 0.3f, 0.28f, // offsetX, offsetY, offsetZ (final, tuned)        40f, 0f, 0f,         // rotX, rotY, rotZ (degrees)        0.8f, 1.5f, 0.8f     // scaleX, scaleY, scaleZ    ));
              ```

              Egg Toast's values above are confirmed final (baked in). When you paste in a new item's values from 

              ```
              /toasttune <item> show
              ```

              , drop the "replace with your real final numbers" style comment once you've actually plugged in the real numbers — a leftover placeholder-style comment next to real values is easy to mistake for still-unfinished tuning later.

            **Why the `setDefault` step exists, and why it's easy to miss:** `/toasttune` only edits `FaceAccessoryTuning`'s in-memory `Map<Item, Values>` on **your own client**, live, for as long as your game session runs — it is never saved to disk and is never sent to or seen by anyone else. If you tune Egg Toast's position with `/toasttune egg_toast offsetY 0.3` and then just ship the mod jar as-is, every other player's client falls back to the untouched defaults (`offset(0, 0.25, 0)`, no rotation, scale `1,1,1`) — flat, untuned, wrong-looking — because their client never ran your `/toasttune` commands. `setDefault(...)` is what turns a live in-game tweak into a permanent, compiled-in value that ships with the jar and applies identically on every client, tuned or not.

            **Per-item setup checklist (one-time, needs a rebuild):**

            1. Build the model in Blockbench per the section above, including a `head` display entry as a starting-point reference.
            2. Add **one line** to `Progression_plsClient.java`: `TrinketRendererRegistry.registerRenderer(ModItems.<NEW_ITEM>, faceRenderer);` (reuse the same shared `faceRenderer` — no new Java class per item, and **`FaceAccessoryTrinketRenderer.java` itself is never edited** — see above).
            3. Rebuild once.
            4. Tune purely in-game from there: `/toasttune <new_item_id> offsetY 0.3`, etc. — instant, no rebuild, no restart, no relog. Iterate until it looks right.
            5. **Bake it in:** run `/toasttune <new_item_id> show`, copy the 9 printed values into a new `FaceAccessoryTuning.setDefault(ModItems.<NEW_ITEM>, new FaceAccessoryTuning.Values(...))` call in `Progression_plsClient.java` (see the example above), and rebuild once more. Skipping this step means the item looks right only on your own client, in your current game session — everyone else (and you, next time you relaunch) sees the flat default instead.

            **Adding a `head/hat` item next — confirmed, needs nothing new:** `head/hat` uses the exact same head anchor as `head/face` (both go through `TrinketRenderer.translateToHead(...)`), it's already granted to the player in `data/trinkets/entities/progression_pls.json` (part of the full `#trinkets:all` grant added earlier), and `FaceAccessoryTrinketRenderer` doesn't care which head slot an item is equipped in — it only cares that the model is a `HeadedModel`. So a hat item follows the identical 5-step checklist above: build the model with a `head` display entry, add one `registerRenderer(ModItems.<HAT_ITEM>, faceRenderer)` line, rebuild, tune with `/toasttune`, bake with `setDefault`. The only genuinely new thing about a hat (vs. a face accessory) is the "hide the underlying vanilla helmet while a hat trinket is worn" question — that's a separate, optional feature (see the Armor Hider mod note / a future equip-state hide-armor system), not a requirement for the hat to render.

            **Scope note:** this whole system only works for slots anchored to the head (`head/face`, `head/hat`). Other slot groups — `hand/ring`, `offhand/*`, `chest/*`, `legs/belt`, `feet/*`, and our own custom `accessory/*` slots (`earrings`, `emblem`, `ring` — these are worn on the hand/ear conceptually but still need their own anchor, not the head one) — are not covered yet. They need a different anchor call from the `TrinketRenderer` interface (`translateToChest`, `translateToRightArm`/`translateToLeftArm`, `translateToRightLeg`/`translateToLeftLeg`) and a different model cast (`HumanoidModel<?>` instead of `HeadedModel`), meaning a second renderer class (or a generalized version of this one with a slot-type parameter) when that becomes the next thing to build.

            **Superseded:** the loose-resource-pack live-tuning workflow below is no longer the way to tune on-character positioning specifically (it was written assuming a JSON trinkets-render file that turned out not to exist) — `/toasttune` replaces it for that purpose entirely. The resource pack technique itself is still valid for live-tuning *other* loose assets (textures, lang files, the model JSON's own Blockbench-exported display blocks, etc.), just not this particular positioning step anymore.

            **Live-tuning workflow with a loose resource pack** (still useful for textures/lang/model assets, not for Trinkets custom-slot positioning — see note above):

            1. Find the right `resourcepacks` folder — `run/resourcepacks/` inside the project if testing via `gradlew runClient`, or `%appdata%\.minecraft\resourcepacks\` if testing through the real Minecraft Launcher with the built jar in `mods`.

            2. Make a new folder there, e.g. `progression_pls_test`, with a `pack.mcmeta` at its root. Minecraft 26.2 uses the newer `min_format`/`max_format` schema (format `88`):

               ```json
               { "pack": { "description": "progression_pls live-tuning pack", "min_format": 88, "max_format": 88 } }
               ```

            3. Mirror the exact same asset path(s) from the mod's own source underneath `assets/progression_pls/...` in that folder. An enabled resource pack always overrides a mod's bundled assets when namespace + path match exactly, so this loose copy silently takes priority over what's in the built jar.

            4. Enable it — Options > Resource Packs > drag it into Selected.

            5. Edit the loose file, save, press F3+T in-game to reload live. No rebuild, no restart.

            6. Once the numbers look right, copy them back into the real file under `src/main/resources/assets/progression_pls/...` and do one proper `.\gradlew.bat build` — the test pack can then be disabled/deleted.

            ------

            ## Common bugs already hit

            **Lang key showing raw (e.g. `item.progression_pls.oak_sapling_earrings` in the creative menu/JEI instead of "Oak Sapling Earrings"):** means there's no matching entry in `assets/progression_pls/lang/en_us.json` for that exact key — Minecraft falls back to printing the raw translation key when it can't find a match. Fix: add `"item.progression_pls.<id>": "Display Name"` to that file, double-checking the key matches the registered id exactly. If adding the line doesn't fix it, check for a JSON syntax error elsewhere in the same file (missing/trailing comma) — a broken lang file can silently fail to load entirely, which shows up as *every* item's name falling back to raw keys, not just one. The same applies to slot names — `trinkets.slot.accessory.<name>` entries are needed for the three custom slots (`earrings`, `emblem`, `ring`) or their labels show raw in the Trinkets inventory UI.

            **Blockbench-exported model still pointing at placeholder textures:** a freshly exported item/block model JSON can come out with texture keys like `"0": "block/texture"` (Blockbench's own default placeholder) instead of the real texture — these need to be manually changed to `"progression_pls:item/<id>"` (or wherever the actual PNG lives) before the model works. Separately, any face left unassigned during modeling exports with `"texture": "#missing"` — that renders in-game as the black/magenta missing-texture checkerboard, not a fallback of any kind. Check every element's faces for `#missing` and reassign them to a real texture key (`#0`, `#1`, etc.) before dropping the model into the mod. Both of these are easy to miss since Blockbench's own viewport still shows something (its internal preview textures), so the model can look fine in the editor while being broken in-game.

            **Trinkets custom-slot icon showing as purple/black checkerboard:** the slot's `icon` field in `data/trinkets/slots/<group>/<name>.json` must use the prefix `<namespace>:container/slots/<name>` — **not** `<namespace>:gui/slots/<name>`, which is what Trinkets' own wiki example shows but does not match the real folder layout inside the Trinkets jar (`textures/gui/sprites/container/slots/...`). Confirmed by directly inspecting the jar's asset folders.

            **Trinkets custom-slot icon rendering as the \*wrong\* icon (not missing, just a different existing icon):** if a slot icon renders as some other slot's icon instead of missing/blank, suspect an enabled loose resource pack overriding the asset path — resource packs always win over a mod jar's bundled assets on an exact namespace+path match, so a stale test pack from the live-tuning workflow above (see the "Live-tuning workflow with a loose resource pack" section) can silently keep serving an old icon at that path even after the mod's own files are fixed and rebuilt. Check Options > Resource Packs for anything still enabled under a namespace this mod uses, and confirm a genuine full game restart happened after the fix (not just F3+T, which only reloads loose resource pack files, not the mod jar itself).

            **"Cannot find symbol" for a Fabric/Minecraft API class or method that looks right:** Minecraft 26.2's rendering internals changed significantly from older tutorials/docs (the "submit node" rendering rewrite — `SubmitNodeCollector` replacing direct `MultiBufferSource` writes, `ItemStackRenderState`/`ItemModelResolver` for resolving item models, `ClientCommands` replacing the older `ClientCommandManager` name for Fabric API's client command builder). When an import or method name from older guidance doesn't compile, trust the compiler error over any remembered API shape — it usually names the real class/return type directly (e.g. an "incompatible types" error revealing the actual return type), which is faster than guessing again.

            ------

            ## Step-by-step: adding a new item

            ### 1. Register it — `ModItems.java`

            - A `ResourceKey<Item>` constant
            - The `Item` field: `Item.Properties().setId(KEY)`, stack size, `trinkets:equipment` component (slot), `trinkets:attribute_modifiers` component (stats — pick a tier from the table above, and give every modifier id a name unique to this item)
            - A `Registry.register(...)` call inside `register()`
            - An `entries.accept(...)` line inside the right `CreativeModeTabEvents.modifyOutputEvent(...)` block

            ### 2. Give it a look — new asset files

            - Item model definition — `assets/progression_pls/items/<id>.json`

              ```json
              { "model": { "type": "minecraft:model", "model": "progression_pls:item/<id>" } }
              ```

            - Model file — `assets/progression_pls/models/item/<id>.json`

              ```json
              { "parent": "minecraft:item/generated", "textures": { "layer0": "progression_pls:item/<id>" } }
              ```

            - Texture — `assets/progression_pls/textures/item/<id>.png` (16x16 pixel art)

            - Lang entry — add one line to `assets/progression_pls/lang/en_us.json`: `"item.progression_pls.<id>": "Display Name"`

            - **If it's a `head/face` or `head/hat` accessory that should render on the character:** also build the Blockbench 3D model (see the Blockbench section above), do the one-line `TrinketRendererRegistry.registerRenderer(...)` + rebuild, tune with `/toasttune`, and finish with the `setDefault(...)` bake-in step — the full 5-step checklist is in the on-character rendering section above. `head/hat` needs nothing different from `head/face` here.

            ### 3. Decide how it's obtained

            Pick one or more:

            **Craftable** — create both:

            - `data/progression_pls/recipe/<id>.json` (shaped/shapeless, vanilla ingredients only)

            - `data/progression_pls/advancement/recipes/<id>.json` (unlocks the recipe)

              ⚠️ **Schema gotcha** (bit us on the original egg_toast.json — check this every time): each criterion's `conditions.items[].items` field must be an **array**, even for one item — `{ "items": ["minecraft:egg"] }`, never a bare string. And `requirements` entries must each be their own quoted array element — `["has_a", "has_b"]`, never a comma-joined string like `"has_a, has_b"`. A broken advancement fails silently (data pack loader logs a warning and skips just that file) — it does **not** block crafting, since crafting only needs the recipe file to be valid, which is exactly why this kind of bug is easy to miss.

            **Villager trade** — create `data/progression_pls/villager_trade/<profession>/<level>/<id>.json`:

            ```json
            {
              "gives": { "id": "progression_pls:<id>" },
              "max_uses": 5,
              "reputation_discount": 0.05,
              "wants": { "count": 20, "id": "minecraft:emerald" },
              "xp": 10
            }
            ```

            Then add `"progression_pls:<profession>/<level>/<id>"` to `data/minecraft/tags/villager_trade/<profession>/level_<level>.json` (append to the existing `values` array if that profession/level combo is already in use; create the file only the first time).

            **Dungeon loot** — add one line to the `DUNGEON_LOOT_ITEMS` list in `ModLootTables.java`, using the chance from the item's rarity tier:

            ```java
            new DungeonLootEntry(ModItems.<ID>, <tier_chance>f)
            ```

            The loop in `register()` handles the rest automatically — no other code changes needed per item.

            **Not craftable at all?** Just skip the recipe + advancement files entirely — no code or flag involved, an item simply can't be crafted if no recipe ever references it. Just make sure it has at least one of trade/loot wired up, or it's only obtainable via `/give`.

            ### 4. One-time setup — only for a *new category*, not every item

            - **New Trinkets slot grant** — add to `data/trinkets/entities/progression_pls.json` only if the item needs a slot not already granted. All 12 of Trinkets' built-ins (including `head/hat`) plus our 3 custom `accessory/*` slots are already granted — this step is only needed again if a brand-new custom slot is created.
            - **New creative tab** — new `ResourceKey<CreativeModeTab>` + `CreativeModeTab` + `register()` call in `ModCreativeTabs.java`, following the `FACE_ACCESSORIES` pattern.
            - **New villager trade tag file** — only the first time a profession/level pairing is used.
            - **New slot group for on-character rendering** (e.g. first ring/necklace/earrings item that actually needs its own anchor) — needs a new `TrinketRenderer` implementation, since `FaceAccessoryTrinketRenderer` only handles head-anchored slots (`head/face`, `head/hat`). See the scope note in the on-character rendering section above.

            ### 5. Build & deploy

            - `.\gradlew.bat build` — needed for both resource-only changes (fast, no recompile) and Java changes.
            - On-character rendering (`FaceAccessoryTrinketRenderer` and friends) is client-only Java — only your own client's jar needs rebuilding to see it; once built, positioning itself tunes live via `/toasttune` with no further rebuilds. **But the live-tuned values are per-client and non-persistent** — always finish with the `setDefault(...)` bake-in step (see the on-character rendering section) and one more rebuild before distributing the jar to other players, or they'll see the untuned flat default instead of your tuned position.
            - After swapping in a new jar, do a full restart rather than relying on F3+T — mod jars load once at launch, unlike loose resource packs.



hearts 