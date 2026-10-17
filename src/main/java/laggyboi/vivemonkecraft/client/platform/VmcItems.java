package laggyboi.vivemonkecraft.client.platform;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

// =====================================================================
// MOD ITEMS                                                [FABRIC BODY]
// =====================================================================
//
// HOVERBOARD — hold it in either hand (hotbar or off-hand slot) and it works
// like a joystick: tilt the board and it pushes you in the direction its
// underside thrusts. All the riding logic lives in HoverboardHandler; the item
// itself is a plain, unstackable marker item.
//
// Registered from the client entry point, which runs in the same JVM as the
// integrated server (singleplayer / LAN host), so that server knows it too.
// Dedicated servers need the companion server mod to register the same id
// before players can hold one there.
//
// The NeoForge and Forge branches replace this file with a DeferredRegister.
// Shared code only ever calls hoverboard().
// =====================================================================
public final class VmcItems {

    private static final ResourceKey<Item> HOVERBOARD_KEY = ResourceKey.create(Registries.ITEM,
            Identifier.fromNamespaceAndPath("vivemonkecraft", "hoverboard"));

    private static Item hoverboard;

    public static void init() {
        hoverboard = Registry.register(BuiltInRegistries.ITEM, HOVERBOARD_KEY,
                new Item(new Item.Properties().setId(HOVERBOARD_KEY).stacksTo(1)));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(hoverboard));
    }

    public static Item hoverboard() {
        return hoverboard;
    }

    private VmcItems() {}
}
