package laggyboi.vivemonkecraft;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

// =====================================================================
// MOD ITEMS
// =====================================================================
//
// HOVERBOARD — hold it in either hand (hotbar or off-hand slot) and it works
// like a joystick: tilt the board and it pushes you in the direction its
// underside thrusts. All the riding logic lives client-side in
// HoverboardHandler; the item itself is a plain, unstackable marker item.
//
// Registered from the COMMON entrypoint so the integrated server (singleplayer
// / LAN host) knows it too — otherwise the server would drop it from your
// inventory. Dedicated servers need the companion server mod to register the
// same id before players can hold one there.
// =====================================================================
public final class ModItems {

    public static final ResourceKey<Item> HOVERBOARD_KEY = ResourceKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("vivemonkecraft", "hoverboard"));

    public static final Item HOVERBOARD = Registry.register(BuiltInRegistries.ITEM, HOVERBOARD_KEY,
            new Item(new Item.Properties().setId(HOVERBOARD_KEY).stacksTo(1)));

    public static void register() {
        // Touching the class above runs the static registration; also list it in
        // the Tools & Utilities creative tab.
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(HOVERBOARD));
    }

    private ModItems() {}
}
