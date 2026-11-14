package com.enderdevelopment.riftglitch;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;

public final class GlitchCommands {
    private GlitchCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            var root = Commands.literal("glitch").requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER));
            root.then(Commands.literal("start").executes(context -> {
                GlitchState.setEnabled(true);
                context.getSource().sendSuccess(() -> Component.literal("Glitch cycle enabled."), true);
                return 1;
            }));
            root.then(Commands.literal("stop").executes(context -> {
                GlitchState.setEnabled(false);
                GlitchState.active("none");
                context.getSource().sendSuccess(() -> Component.literal("Glitch cycle stopped."), true);
                return 1;
            }));
            root.then(Commands.literal("now").executes(context -> {
                GlitchEvents.trigger(context.getSource().getServer());
                return 1;
            }));
            root.then(Commands.literal("status").executes(context -> {
                String status = GlitchState.isEnabled() ? "enabled; current anomaly: " + GlitchState.active() : "disabled";
                context.getSource().sendSuccess(() -> Component.literal("Glitch cycle is " + status + "."), false);
                return 1;
            }));
            root.then(Commands.literal("event").then(Commands.argument("name", StringArgumentType.word())
                    .suggests((context, builder) -> {
                        builder.suggest("static");
                        builder.suggest("echo");
                        builder.suggest("moonflip");
                        builder.suggest("corruption");
                        builder.suggest("null");
                        return builder.buildFuture();
                    }).executes(context -> {
                        String event = StringArgumentType.getString(context, "name");
                        if (!GlitchEvents.triggerNamed(context.getSource().getServer(), event)) {
                            context.getSource().sendFailure(Component.literal("Unknown event. Use static, echo, moonflip, corruption, or null."));
                            return 0;
                        }
                        return 1;
                    })));
            dispatcher.register(root);
        });
    }
}
