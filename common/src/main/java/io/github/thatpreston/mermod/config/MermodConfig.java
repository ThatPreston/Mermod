package io.github.thatpreston.mermod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MermodConfig {
    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();
        final Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }
    public static class Server {
        public ForgeConfigSpec.DoubleValue swimSpeedMultiplier;
        public ForgeConfigSpec.BooleanValue waterBreathing;
        public ForgeConfigSpec.BooleanValue nightVision;
        public ForgeConfigSpec.BooleanValue aquaAffinity;
        Server(ForgeConfigSpec.Builder builder) {
            builder.push("Server");
            swimSpeedMultiplier = builder.comment("Swim speed multiplier").defineInRange("swimSpeedMultiplier", 1.5D, 1.0D, 10.0D);
            waterBreathing = builder.comment("Water breathing").define("waterBreathing", true);
            nightVision = builder.comment("Night vision").define("nightVision", true);
            aquaAffinity = builder.comment("Aqua affinity (underwater mining speed)").define("aquaAffinity", true);
            builder.pop();
        }
    }
    public static class Client {
        public ForgeConfigSpec.BooleanValue disableNightVisionFlashing;
        public ForgeConfigSpec.BooleanValue replaceSwimAnimation;
        Client(ForgeConfigSpec.Builder builder) {
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