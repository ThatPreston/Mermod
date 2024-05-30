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
            swimSpeedMultiplier = builder.comment("Swim speed multiplier").defineInRange("swimSpeedMultiplier", 2.0D, 1.0D, 10.0D);
            waterBreathing = builder.comment("Water breathing").define("waterBreathing", true);
            nightVision = builder.comment("Night vision").define("nightVision", true);
            aquaAffinity = builder.comment("Aqua affinity").define("aquaAffinity", true);
            builder.pop();
        }
    }
    public static class Client {
        public ModConfigSpec.BooleanValue nightVisionFlashingFix;
        public ModConfigSpec.BooleanValue replaceSwimAnimation;
        Client(ModConfigSpec.Builder builder) {
            builder.push("Client");
            nightVisionFlashingFix = builder.comment("Night vision flashing fix").define("nightVisionFlashingFix", true);
            replaceSwimAnimation = builder.comment("Replace swim animation").define("replaceSwimAnimation", true);
            builder.pop();
        }
    }
    public static double getSwimSpeedMultiplier() {
        return SERVER.swimSpeedMultiplier.get();
    }
    public static boolean getWaterBreathing() {
        return SERVER.waterBreathing.get();
    }
    public static boolean getNightVision() {
        return SERVER.nightVision.get();
    }
    public static boolean getAquaAffinity() {
        return SERVER.aquaAffinity.get();
    }
    public static boolean getNightVisionFlashingFix() {
        return CLIENT.nightVisionFlashingFix.get();
    }
    public static boolean getReplaceSwimAnimation() {
        return CLIENT.replaceSwimAnimation.get();
    }
    public static void setNightVisionFlashingFix(boolean value) {
        CLIENT.nightVisionFlashingFix.set(value);
    }
    public static void setReplaceSwimAnimation(boolean value) {
        CLIENT.replaceSwimAnimation.set(value);
    }
}