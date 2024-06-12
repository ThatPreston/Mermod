package io.github.thatpreston.mermod.compat;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.entries.FiguraAPI;
import org.figuramc.figura.entries.annotations.FiguraAPIPlugin;
import org.figuramc.figura.lua.LuaWhitelist;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.*;

@FiguraAPIPlugin
@LuaWhitelist
public class MermodFiguraAPI implements FiguraAPI {
    public static final Map<UUID, Boolean> TAIL_VISIBLE = new HashMap<>();
    private Avatar avatar;
    public MermodFiguraAPI() {}
    public MermodFiguraAPI(Avatar avatar) {
        this.avatar = avatar;
    }
    @Override
    public FiguraAPI build(Avatar avatar) {
        return new MermodFiguraAPI(avatar);
    }
    @Override
    public String getName() {
        return "mermod_tail";
    }
    @Override
    public Collection<Class<?>> getWhitelistedClasses() {
        return List.of(MermodFiguraAPI.class);
    }
    @Override
    public Collection<Class<?>> getDocsClasses() {
        return List.of();
    }
    @LuaWhitelist
    public void setVisible(boolean value) {
        TAIL_VISIBLE.put(avatar.owner, value);
    }
    @LuaWhitelist
    public LuaValue getTailStyle() {
        Entity entity = avatar.luaRuntime.getUser();
        if(entity instanceof Player player) {
            TailStyle style = Mermod.getTailStyle(player);
            if(style != null) {
                LuaTable table = new LuaTable();
                table.set("texture", LuaValue.valueOf(style.texture().toString()));
                table.set("model", LuaValue.valueOf(style.model()));
                setRGB(table, "tailColor", style.tailColor());
                table.set("hasBra", LuaValue.valueOf(style.hasBra()));
                setRGB(table, "braColor", style.braColor());
                table.set("hasGradient", LuaValue.valueOf(style.hasGradient()));
                setRGB(table, "gradientColor", style.gradientColor());
                table.set("hasGlint", LuaValue.valueOf(style.hasGlint()));
                return table;
            }
        }
        return LuaValue.NIL;
    }
    private void setRGB(LuaTable table, String name, int color) {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        table.set(name + "R", red);
        table.set(name + "G", green);
        table.set(name + "B", blue);
    }
    public static boolean isTailVisible(UUID uuid) {
        return TAIL_VISIBLE.getOrDefault(uuid, true);
    }
}