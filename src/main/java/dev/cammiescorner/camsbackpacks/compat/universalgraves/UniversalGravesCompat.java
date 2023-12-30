package dev.cammiescorner.camsbackpacks.compat.universalgraves;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import eu.pb4.graves.event.PlayerGraveItemAddedEvent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.ActionResult;

public class UniversalGravesCompat {

    public static void load() {
        PlayerGraveItemAddedEvent.EVENT.register((player, stack) -> {
            if (stack.getItem() instanceof BackpackItem && stack == player.getEquippedStack(EquipmentSlot.CHEST))
                return ActionResult.FAIL;

            return ActionResult.PASS;
        });
    }
}
