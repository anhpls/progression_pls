package com.anhpls.progressionpls.client;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import com.anhpls.progressionpls.Progression_pls;
import com.anhpls.progressionpls.client.trinket.FaceAccessoryTuning;

public class TuningCommands {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext) -> {
            dispatcher.register(
                ClientCommands.literal("toasttune")
                    .then(ClientCommands.literal("help").executes(TuningCommands::help))
                    .then(ClientCommands.argument("item", StringArgumentType.word())
                        .then(ClientCommands.literal("show")
                            .executes(TuningCommands::showValues))
                        .then(ClientCommands.literal("help")
                            .executes(TuningCommands::help))
                        .then(ClientCommands.argument("field", StringArgumentType.word())
                            .then(ClientCommands.argument("value", FloatArgumentType.floatArg())
                                .executes(TuningCommands::setValue)))
                    )
            );
        });
    }

    private static Item resolveItem(String itemName, CommandContext<FabricClientCommandSource> ctx) {
        Identifier id = Progression_pls.id(itemName);
        Item item = BuiltInRegistries.ITEM.get(id).map(ref -> ref.value()).orElse(null);
        if (item == null) {
            ctx.getSource().sendFeedback(Component.literal("Unknown item: " + itemName));
            return null;
        }
        return item;
    }

    private static int showValues(CommandContext<FabricClientCommandSource> ctx) {
        String itemName = StringArgumentType.getString(ctx, "item");
        Item item = resolveItem(itemName, ctx);
        if (item == null) return 0;

        FaceAccessoryTuning.Values v = FaceAccessoryTuning.get(item);
        ctx.getSource().sendFeedback(Component.literal(
            itemName + " -> offset(" + v.offsetX + ", " + v.offsetY + ", " + v.offsetZ + ") "
            + "rot(" + v.rotX + ", " + v.rotY + ", " + v.rotZ + ") "
            + "scale(" + v.scaleX + ", " + v.scaleY + ", " + v.scaleZ + ")"
        ));
        return 1;
    }

    private static int setValue(CommandContext<FabricClientCommandSource> ctx) {
        String itemName = StringArgumentType.getString(ctx, "item");
        String field = StringArgumentType.getString(ctx, "field");
        float value = FloatArgumentType.getFloat(ctx, "value");

        Item item = resolveItem(itemName, ctx);
        if (item == null) return 0;

        FaceAccessoryTuning.Values v = FaceAccessoryTuning.get(item);
        switch (field) {
            case "offsetX" -> v.offsetX = value;
            case "offsetY" -> v.offsetY = value;
            case "offsetZ" -> v.offsetZ = value;
            case "rotX" -> v.rotX = value;
            case "rotY" -> v.rotY = value;
            case "rotZ" -> v.rotZ = value;
            case "scaleX" -> v.scaleX = value;
            case "scaleY" -> v.scaleY = value;
            case "scaleZ" -> v.scaleZ = value;
            default -> {
                ctx.getSource().sendFeedback(Component.literal("Unknown field: " + field));
                return 0;
            }
        }
        ctx.getSource().sendFeedback(Component.literal(itemName + " " + field + " = " + value));
        return 1;
    }

    private static int help(CommandContext<FabricClientCommandSource> ctx) {
        ctx.getSource().sendFeedback(Component.literal("--- /toasttune <item> <subcommand> ---"));
        ctx.getSource().sendFeedback(Component.literal("show — print this item's current offset/rotation/scale"));
        ctx.getSource().sendFeedback(Component.literal("<field> <value> — e.g. /toasttune sprout offsetY 0.3"));
        ctx.getSource().sendFeedback(Component.literal("valid fields: offsetX, offsetY, offsetZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ"));
        return 1;
    }
}