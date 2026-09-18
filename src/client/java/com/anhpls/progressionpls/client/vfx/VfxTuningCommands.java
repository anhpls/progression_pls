package com.anhpls.progressionpls.client.vfx;

import java.util.function.UnaryOperator;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public final class VfxTuningCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommands.literal("vfxtune")
                        .then(ClientCommands.literal("help").executes(ctx -> help(ctx.getSource())))
                        .then(ClientCommands.argument("item", StringArgumentType.word())
                                .then(ClientCommands.literal("help").executes(ctx -> help(ctx.getSource())))
                                .then(ClientCommands.literal("show")
                                        .executes(ctx -> show(ctx.getSource(), StringArgumentType.getString(ctx, "item"))))
                                .then(ClientCommands.literal("particle")
                                        .then(ClientCommands.argument("value", StringArgumentType.word())
                                                .executes(ctx -> setParticle(ctx.getSource(),
                                                        StringArgumentType.getString(ctx, "item"),
                                                        StringArgumentType.getString(ctx, "value")))))
                                .then(ClientCommands.literal("enabled")
                                        .then(ClientCommands.argument("value", BoolArgumentType.bool())
                                                .executes(ctx -> setEnabled(ctx.getSource(),
                                                        StringArgumentType.getString(ctx, "item"),
                                                        BoolArgumentType.getBool(ctx, "value")))))
                                .then(ClientCommands.literal("trigger")
                                        .then(ClientCommands.argument("value", StringArgumentType.word())
                                                .executes(ctx -> setTrigger(ctx.getSource(),
                                                        StringArgumentType.getString(ctx, "item"),
                                                        StringArgumentType.getString(ctx, "value")))))
                                .then(ClientCommands.literal("cycleTicks")
                                        .then(ClientCommands.argument("value", IntegerArgumentType.integer(1))
                                                .executes(ctx -> setCycleTicks(ctx.getSource(),
                                                        StringArgumentType.getString(ctx, "item"),
                                                        IntegerArgumentType.getInteger(ctx, "value")))))
                                .then(ClientCommands.literal("radius")
                                        .then(ClientCommands.argument("value", DoubleArgumentType.doubleArg(0))
                                                .executes(ctx -> setSpiralField(ctx.getSource(), StringArgumentType.getString(ctx, "item"),
                                                        spec -> new SpiralAuraRegistry.SpiralSpec(spec.particle(),
                                                                DoubleArgumentType.getDouble(ctx, "value"), spec.heightMin(), spec.heightMax(),
                                                                spec.cycleTicks(), spec.revolutions(), spec.particlesPerTick(), spec.enabled(), spec.trigger())))))
                                .then(ClientCommands.literal("heightMin")
                                        .then(ClientCommands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(ctx -> setSpiralField(ctx.getSource(), StringArgumentType.getString(ctx, "item"),
                                                        spec -> new SpiralAuraRegistry.SpiralSpec(spec.particle(), spec.radius(),
                                                                DoubleArgumentType.getDouble(ctx, "value"), spec.heightMax(),
                                                                spec.cycleTicks(), spec.revolutions(), spec.particlesPerTick(), spec.enabled(), spec.trigger())))))
                                .then(ClientCommands.literal("heightMax")
                                        .then(ClientCommands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(ctx -> setSpiralField(ctx.getSource(), StringArgumentType.getString(ctx, "item"),
                                                        spec -> new SpiralAuraRegistry.SpiralSpec(spec.particle(), spec.radius(), spec.heightMin(),
                                                                DoubleArgumentType.getDouble(ctx, "value"),
                                                                spec.cycleTicks(), spec.revolutions(), spec.particlesPerTick(), spec.enabled(), spec.trigger())))))
                                .then(ClientCommands.literal("revolutions")
                                        .then(ClientCommands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(ctx -> setSpiralField(ctx.getSource(), StringArgumentType.getString(ctx, "item"),
                                                        spec -> new SpiralAuraRegistry.SpiralSpec(spec.particle(), spec.radius(), spec.heightMin(), spec.heightMax(),
                                                                spec.cycleTicks(), DoubleArgumentType.getDouble(ctx, "value"), spec.particlesPerTick(), spec.enabled(), spec.trigger())))))
                                .then(ClientCommands.literal("particlesPerTick")
                                        .then(ClientCommands.argument("value", IntegerArgumentType.integer(1))
                                                .executes(ctx -> setSpiralField(ctx.getSource(), StringArgumentType.getString(ctx, "item"),
                                                        spec -> new SpiralAuraRegistry.SpiralSpec(spec.particle(), spec.radius(), spec.heightMin(), spec.heightMax(),
                                                                spec.cycleTicks(), spec.revolutions(), IntegerArgumentType.getInteger(ctx, "value"), spec.enabled(), spec.trigger())))))
                        )));
    }

    private static Item resolveItem(String name) {
        Identifier id = Identifier.fromNamespaceAndPath("progression_pls", name);
        return BuiltInRegistries.ITEM.getOptional(id).orElse(null);
    }

    private static ParticleOptions resolveParticle(String name) {
        Identifier id = name.contains(":")
                ? Identifier.parse(name)
                : Identifier.fromNamespaceAndPath("minecraft", name);
        var particleType = BuiltInRegistries.PARTICLE_TYPE.getOptional(id).orElse(null);
        return (particleType instanceof SimpleParticleType simple) ? simple : null;
    }

    private static boolean isSpiralItem(Item item) { return SpiralAuraRegistry.all().containsKey(item); }

    private static int setSpiralField(FabricClientCommandSource source, String itemName,
                                       UnaryOperator<SpiralAuraRegistry.SpiralSpec> updater) {
        Item item = resolveItem(itemName);
        if (item == null) { source.sendError(Component.literal("Unknown item: " + itemName)); return 0; }
        if (!isSpiralItem(item)) { source.sendError(Component.literal(itemName + " doesn't use the spiral aura effect.")); return 0; }
        SpiralAuraRegistry.register(item, updater.apply(SpiralAuraRegistry.get(item)));
        source.sendFeedback(Component.literal("Updated " + itemName + "'s spiral VFX — run /vfxtune " + itemName + " show to see all values."));
        return 1;
    }

    private static int setParticle(FabricClientCommandSource source, String itemName, String particleName) {
        Item item = resolveItem(itemName);
        if (item == null) { source.sendError(Component.literal("Unknown item: " + itemName)); return 0; }
        ParticleOptions particle = resolveParticle(particleName);
        if (particle == null) {
            source.sendError(Component.literal("Unknown or unsupported particle: " + particleName
                    + " (dust/trail/shriek/vibration need extra data this command doesn't support)"));
            return 0;
        }
        if (isSpiralItem(item)) {
            var s = SpiralAuraRegistry.get(item);
            SpiralAuraRegistry.register(item, new SpiralAuraRegistry.SpiralSpec(particle, s.radius(), s.heightMin(), s.heightMax(),
                    s.cycleTicks(), s.revolutions(), s.particlesPerTick(), s.enabled(), s.trigger()));
        } else {
            source.sendError(Component.literal(itemName + " has no aura VFX registered yet.")); return 0;
        }
        source.sendFeedback(Component.literal("Set " + itemName + "'s VFX particle to " + particleName));
        return 1;
    }

    private static int setEnabled(FabricClientCommandSource source, String itemName, boolean enabled) {
        Item item = resolveItem(itemName);
        if (item == null) { source.sendError(Component.literal("Unknown item: " + itemName)); return 0; }
        if (isSpiralItem(item)) {
            var s = SpiralAuraRegistry.get(item);
            SpiralAuraRegistry.register(item, new SpiralAuraRegistry.SpiralSpec(s.particle(), s.radius(), s.heightMin(), s.heightMax(),
                    s.cycleTicks(), s.revolutions(), s.particlesPerTick(), enabled, s.trigger()));
        } else {
            source.sendError(Component.literal(itemName + " has no aura VFX registered yet.")); return 0;
        }
                source.sendFeedback(Component.literal((enabled ? "Enabled" : "Disabled") + " VFX for " + itemName));
        return 1;
    }

    private static int setTrigger(FabricClientCommandSource source, String itemName, String triggerName) {
        Item item = resolveItem(itemName);
        if (item == null) { source.sendError(Component.literal("Unknown item: " + itemName)); return 0; }
        VfxTrigger trigger;
        try {
            trigger = VfxTrigger.valueOf(triggerName.toUpperCase());
        } catch (IllegalArgumentException e) {
            source.sendError(Component.literal("Unknown trigger: " + triggerName + " (valid: always, standing, jumping, moving)"));
            return 0;
        }
        if (isSpiralItem(item)) {
            var s = SpiralAuraRegistry.get(item);
            SpiralAuraRegistry.register(item, new SpiralAuraRegistry.SpiralSpec(s.particle(), s.radius(), s.heightMin(), s.heightMax(),
                    s.cycleTicks(), s.revolutions(), s.particlesPerTick(), s.enabled(), trigger));
        } else {
            source.sendError(Component.literal(itemName + " has no aura VFX registered yet.")); return 0;
        }
        source.sendFeedback(Component.literal("Set " + itemName + "'s VFX trigger to " + triggerName));
        return 1;
    }

    private static int setCycleTicks(FabricClientCommandSource source, String itemName, int value) {
        Item item = resolveItem(itemName);
        if (item == null) { source.sendError(Component.literal("Unknown item: " + itemName)); return 0; }
        if (isSpiralItem(item)) {
            var s = SpiralAuraRegistry.get(item);
            SpiralAuraRegistry.register(item, new SpiralAuraRegistry.SpiralSpec(s.particle(), s.radius(), s.heightMin(), s.heightMax(),
                    value, s.revolutions(), s.particlesPerTick(), s.enabled(), s.trigger()));
        } else {
            source.sendError(Component.literal(itemName + " has no aura VFX registered yet.")); return 0;
        }
        source.sendFeedback(Component.literal("Set " + itemName + "'s cycleTicks to " + value));
        return 1;
    }

    private static int show(FabricClientCommandSource source, String itemName) {
        Item item = resolveItem(itemName);
        if (item == null) { source.sendError(Component.literal("Unknown item: " + itemName)); return 0; }
        if (isSpiralItem(item)) {
            var s = SpiralAuraRegistry.get(item);
            source.sendFeedback(Component.literal(itemName + " (spiral) — enabled: " + s.enabled() + ", trigger: " + s.trigger()
                    + ", radius: " + s.radius() + ", heightMin: " + s.heightMin() + ", heightMax: " + s.heightMax()
                    + ", cycleTicks: " + s.cycleTicks() + ", revolutions: " + s.revolutions() + ", particlesPerTick: " + s.particlesPerTick()));
        } else {
            source.sendError(Component.literal(itemName + " has no aura VFX registered yet.")); return 0;
        }
        return 1;
    }

    private static int help(FabricClientCommandSource source) {
        source.sendFeedback(Component.literal("--- /vfxtune <item> <subcommand> ---"));
        source.sendFeedback(Component.literal("show / enabled <true|false> / trigger <always|standing|jumping|moving> / particle <name> / cycleTicks <int>"));
        source.sendFeedback(Component.literal("spiral-only (e.g. sprout): radius, heightMin, heightMax, revolutions, particlesPerTick"));
        return 1;
    }
}
