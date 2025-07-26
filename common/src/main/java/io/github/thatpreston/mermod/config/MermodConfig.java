package io.github.thatpreston.mermod.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MermodConfig {
    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;
    public static final Client CLIENT;
    public static final ModConfigSpec CLIENT_SPEC;
    static {
        final Pair<Server, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();
        final Pair<Client, ModConfigSpec> clientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }
    public static class Server {
        public ModConfigSpec.DoubleValue swimSpeedMultiplier;
        public ModConfigSpec.BooleanValue waterBreathing;
        public ModConfigSpec.BooleanValue nightVision;
        public ModConfigSpec.BooleanValue aquaAffinity;
        Server(ModConfigSpec.Builder builder) {
            builder.push("Server");
            swimSpeedMultiplier = builder.comment("Swim speed multiplier").defineInRange("swimSpeedMultiplier", 1.5D, 1.0D, 10.0D);
            waterBreathing = builder.comment("Water breathing").define("waterBreathing", true);
            nightVision = builder.comment("Night vision").define("nightVision", true);
            aquaAffinity = builder.comment("Aqua affinity (underwater mining speed)").define("aquaAffinity", true);
            builder.pop();
        }
    }
    public static class Client {
        public ModConfigSpec.BooleanValue disableNightVisionFlashing;
        public ModConfigSpec.BooleanValue replaceSwimAnimation;
        Client(ModConfigSpec.Builder builder) {
            builder.push("Client");
            disableNightVisionFlashing = builder.comment("Disable night vision flashing").define("disableNightVisionFlashing", true);
            replaceSwimAnimation = builder.comment("Replace swim animation").define("replaceSwimAnimation", true);
            builder.pop();
        }
    }
    public static double getSwimSpeedMultiplier() {
        return SERVER.swimSpeedMultiplier.get();
    }
    public static boolean isWaterBreathingEnabled() {
        return SERVER.waterBreathing.get();
    }
    public static boolean isNightVisionEnabled() {
        return SERVER.nightVision.get();
    }
    public static boolean isAquaAffinityEnabled() {
        return SERVER.aquaAffinity.get();
    }
    public static boolean shouldDisableNightVisionFlashing() {
        return CLIENT.disableNightVisionFlashing.get();
    }
    public static boolean shouldReplaceSwimAnimation() {
        return CLIENT.replaceSwimAnimation.get();
    }
}