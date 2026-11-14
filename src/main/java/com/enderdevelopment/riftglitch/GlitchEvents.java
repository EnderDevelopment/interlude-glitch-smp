package com.enderdevelopment.riftglitch;

import java.util.List;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;

public final class GlitchEvents {
    private static final String[] EVENTS = {"static", "echo", "moonflip", "corruption", "null"};

    private GlitchEvents() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (GlitchState.isEnabled() && GlitchState.tick() <= 0) {
                trigger(server);
            }
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                handler.getPlayer().sendSystemMessage(Component.literal("[RIFT] The world is stable. For now.")));
    }

    public static void trigger(MinecraftServer server) {
        triggerNamed(server, EVENTS[server.getTickCount() % EVENTS.length]);
    }

    public static boolean triggerNamed(MinecraftServer server, String name) {
        if (!List.of(EVENTS).contains(name)) {
            return false;
        }
        GlitchState.active(name);
        GlitchState.scheduleNext();
        Component announcement = Component.literal("[RIFT GLITCH] " + name.toUpperCase() + " has entered the simulation.");
        server.getPlayerList().broadcastSystemMessage(announcement, false);
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            apply(name, player);
        }
        return true;
    }

    private static void apply(String name, ServerPlayer player) {
        switch (name) {
            case "static" -> {
                player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 20 * 8, 0));
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 6, 0));
                player.level().levelEvent(2001, player.blockPosition(), 0);
                player.sendSystemMessage(Component.literal("[STATIC] Your coordinates are being rewritten..."));
            }
            case "echo" -> {
                player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 12, 1));
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20 * 5, 0));
                player.sendSystemMessage(Component.literal("[ECHO] Something moved where you were standing."));
            }
            case "moonflip" -> {
                Vec3 velocity = player.getDeltaMovement();
                player.setDeltaMovement(velocity.x, 0.85, velocity.z);
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 10, 0));
                player.sendSystemMessage(Component.literal("[MOONFLIP] Gravity lost the argument."));
            }
            case "corruption" -> {
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 20 * 20, 0));
                player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 20 * 20, 1));
                player.sendSystemMessage(Component.literal("[CORRUPTION] Your inventory feels one slot out of sync."));
            }
            case "null" -> {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 3, 0));
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20 * 2, 0));
                player.sendSystemMessage(Component.literal("[NULL] You have been briefly removed from reality."));
            }
            default -> throw new IllegalStateException("Unexpected glitch: " + name);
        }
    }
}
