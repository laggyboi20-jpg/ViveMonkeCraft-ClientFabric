package laggyboi.vivemonkecraft.client.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

// =====================================================================
// KEYBINDS                                                 [FABRIC BODY]
// =====================================================================
//
// The KeyMapping itself is constructed identically on every loader (it's vanilla);
// only how it gets REGISTERED differs, which is what init() hides.
//
// Bound to real keys by default so the Vivecraft radial menu can assign them
// immediately (Vivecraft can only put KEYBOARD keys on radial slots — an
// unbound key shows up but opens the mod settings instead of toggling).
// The user can rebind in Options -> Controls -> Miscellaneous.
// =====================================================================
public final class VmcKeybinds {

    private VmcKeybinds() {}

    // Default keys: V = toggle, B = cycle preset. Both are unused by vanilla
    // and most mods. The user can rebind freely.
    public static final KeyMapping TOGGLE = new KeyMapping(
            "key.vivemonkecraft.toggle",
            InputConstants.KEY_V,
            KeyMapping.Category.MISC);

    public static final KeyMapping CYCLE_MODE = new KeyMapping(
            "key.vivemonkecraft.cyclemode",
            InputConstants.KEY_B,
            KeyMapping.Category.MISC);

    /** Hand the keybinds to the loader. Called once from the bootstrap. */
    public static void init() {
        // Migrate stale GLFW_KEY_UNKNOWN (-1) saved in options.txt from pre-26.3;
        // -1 is not a valid SDL scancode and crashes Vivecraft's radial menu.
        InputConstants.Key staleUnbound = InputConstants.Type.KEYBOARD.getOrCreate(-1);
        if (TOGGLE.matches(staleUnbound)) {
            TOGGLE.setKey(InputConstants.UNKNOWN);
        }
        if (CYCLE_MODE.matches(staleUnbound)) {
            CYCLE_MODE.setKey(InputConstants.UNKNOWN);
        }
        KeyMappingHelper.registerKeyMapping(TOGGLE);
        KeyMappingHelper.registerKeyMapping(CYCLE_MODE);
    }
}
