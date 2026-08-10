package io.github.thatpreston.mermod.client.render;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifiers;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.HashMap;

public record TailStyle(Identifier texture, int model, int tailColor, boolean hasBra, int braColor, boolean hasGradient, int gradientColor, boolean hasGlint, boolean permanent) {
    public static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(Mermod.MOD_ID, "textures/tail/tail.png");
    private static final HashMap<String, Identifier> TAIL_IDENTIFIER_CACHE = new HashMap<>();

    /**
     * Vanilla-compatible fallback for servers that don't (and structurally can't) run Mermod
     * itself — e.g. a plain Paper server tagging a vanilla item as #mermod:sea_necklace.
     * NECKLACE_MODIFIERS_COMPONENT_TYPE is a mod-registered DataComponentType: a vanilla
     * server has no way to reference it (component patches are addressed by registry id,
     * and that id only exists where the mod itself is loaded). vanilla:custom_model_data
     * is a normal synced component any server can set, so we use it as a secondary,
     * lower-priority source for the same information when the real component is absent.
     * <p>
     * Encoding (only consulted when NECKLACE_MODIFIERS_COMPONENT_TYPE is missing):
     * <ul>
     *   <li>{@code floats[0]} - tail style index: 0 = none, 1 = h2o, 2 = ariel, 3 = siren
     *       (mirrors the (texture, model) pairs of the built-in moon_rock/ursula_shell/
     *       deep_sea_crystal modifiers)</li>
     *   <li>{@code flags[0]} - bra</li>
     *   <li>{@code flags[1]} - gradient</li>
     *   <li>{@code flags[2]} - glint</li>
     *   <li>{@code flags[3]} - permanent</li>
     *   <li>{@code colors[0]} - gradient color, used when {@code flags[1]}</li>
     * </ul>
     * Base tail/bra color still comes from {@code minecraft:dyed_color}, same as before.
     */
    private static final String[] FALLBACK_TAIL_TEXTURES = {"", "h2o", "ariel", "siren"};
    private static final int[] FALLBACK_TAIL_MODELS = {0, 1, 0, 2};

    public static TailStyle fromNecklace(ItemStack necklace) {
        NecklaceModifiers component = necklace.get(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get());
        if(component == null) {
            return fromVanillaFallback(necklace);
        }
        String textureName = "";
        int model = 0;
        boolean hasBra = false;
        int braColor = -1;
        boolean hasGradient = false;
        int gradientColor = -1;
        boolean hasGlint;
        boolean permanent;
        NecklaceModifier tail = component.get("tail");
        NecklaceModifier bra = component.get("bra");
        NecklaceModifier gradient = component.get("gradient");
        if(tail != null) {
            textureName = tail.texture();
            model = tail.model();
        }
        if(bra != null) {
            hasBra = true;
            braColor = ARGB.opaque(bra.color());
        }
        if(gradient != null) {
            hasGradient = true;
            gradientColor = ARGB.opaque(gradient.color());
        }
        hasGlint = component.contains("glint");
        permanent = component.contains("permanent");
        int tailColor = DyedItemColor.getOrDefault(necklace, -1);
        Identifier texture = textureName.isEmpty() ? DEFAULT_TEXTURE : getTailIdentifier(textureName, tailColor != -1 || braColor != -1 || hasGradient);
        return new TailStyle(texture, model, tailColor, hasBra, braColor, hasGradient, gradientColor, hasGlint, permanent);
    }

    private static TailStyle fromVanillaFallback(ItemStack necklace) {
        int tailColor = DyedItemColor.getOrDefault(necklace, -1);
        CustomModelData cmd = necklace.get(DataComponents.CUSTOM_MODEL_DATA);
        if(cmd == null) {
            return new TailStyle(DEFAULT_TEXTURE, 0, tailColor, false, -1, false, -1, false, false);
        }
        int styleIndex = cmd.floats().isEmpty() ? 0 : Mth.clamp((int)(float)cmd.floats().get(0), 0, FALLBACK_TAIL_TEXTURES.length - 1);
        String textureName = FALLBACK_TAIL_TEXTURES[styleIndex];
        int model = FALLBACK_TAIL_MODELS[styleIndex];
        boolean hasBra = fallbackFlag(cmd, 0);
        boolean hasGradient = fallbackFlag(cmd, 1);
        boolean hasGlint = fallbackFlag(cmd, 2);
        boolean permanent = fallbackFlag(cmd, 3);
        int gradientColor = hasGradient && !cmd.colors().isEmpty() ? ARGB.opaque(cmd.colors().get(0)) : -1;
        // No separate bra-color channel in this compact encoding - reuse the base dye color.
        int braColor = tailColor;
        Identifier texture = textureName.isEmpty() ? DEFAULT_TEXTURE : getTailIdentifier(textureName, tailColor != -1 || hasGradient);
        return new TailStyle(texture, model, tailColor, hasBra, braColor, hasGradient, gradientColor, hasGlint, permanent);
    }

    private static boolean fallbackFlag(CustomModelData cmd, int index) {
        return cmd.flags().size() > index && cmd.flags().get(index);
    }

    private static Identifier getTailIdentifier(String name, boolean colorable) {
        String string = "textures/tail/" + name + (colorable ? "_colorable" : "") + ".png";
        return TAIL_IDENTIFIER_CACHE.computeIfAbsent(string, path -> Identifier.fromNamespaceAndPath(Mermod.MOD_ID, path));
    }
}
