package com.enderdevelopment.riftglitch;

public final class GlitchState {
    private static boolean enabled;
    private static int ticksUntilNext = 20 * 45;
    private static String active = "none";

    private GlitchState() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        if (value) {
            ticksUntilNext = 20 * 15;
        }
    }

    public static int tick() {
        return --ticksUntilNext;
    }

    public static void scheduleNext() {
        ticksUntilNext = 20 * (30 + (int) (Math.random() * 60));
    }

    public static String active() {
        return active;
    }

    public static void active(String name) {
        active = name;
    }
}
