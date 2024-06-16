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
            swimSpeedMultiplier = builder.comment("Swim speed multiplier").defineInRange("swimSpeedMultiplier", 2.0D, 1.0D, 10.0D);
            waterBreathing = builder.comment("Water breathing").define("waterBreathing", true);
            nightVision = builder.comment("Night vision").define("nightVision", true);
            aquaAffinity = builder.comment("Aqua affinity").define("aquaAffinity", true);
            builder.pop();
        }
    }
    public static class Client {
        public ForgeConfigSpec.BooleanValue nightVisionFlashingFix;
        public ForgeConfigSpec.BooleanValue replaceSwimAnimation;
        Client(ForgeConfigSpec.Builder builder) {
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