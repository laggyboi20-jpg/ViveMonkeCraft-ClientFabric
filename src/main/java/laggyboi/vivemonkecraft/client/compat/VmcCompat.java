package laggyboi.vivemonkecraft.client.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
//? if >=26.3
import net.minecraft.world.item.component.SwingAnimation;

// =====================================================================
// VERSION COMPAT — small vanilla calls that differ between Minecraft versions
// =====================================================================
//
// Stonecutter keeps the right branch of each //? block for the version being
// built. Shared code calls these instead of the vanilla method, so the version
// differences live here instead of being scattered through the mod.
// =====================================================================
public final class VmcCompat {

    private VmcCompat() {}

    /** Shows a message above the hotbar (the action bar). */
    public static void overlay(Minecraft mc, Component message, boolean animate) {
        //? if >=26.2 {
        mc.gui.hud.setOverlayMessage(message, animate);
        //?} else {
        /*mc.gui.setOverlayMessage(message, animate);
        *///?}
    }

    /** The open screen, or null in game. */
    public static Screen screen(Minecraft mc) {
        //? if >=26.2 {
        return mc.gui.screen();
        //?} else {
        /*return mc.screen;
        *///?}
    }

    /** Swings the main hand (and tells the server). */
    public static void swingMainHand(LocalPlayer player) {
        //? if >=26.3 {
        player.swing(InteractionHand.MAIN_HAND, SwingAnimation.DEFAULT, true);
        //?} else {
        /*player.swing(InteractionHand.MAIN_HAND);
        *///?}
    }
}
