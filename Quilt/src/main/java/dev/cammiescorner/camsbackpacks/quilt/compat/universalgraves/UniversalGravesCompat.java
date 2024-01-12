package dev.cammiescorner.camsbackpacks.quilt.compat.universalgraves;

import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import eu.pb4.graves.event.PlayerGraveItemAddedEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;

public class UniversalGravesCompat {

    public static void load() {
        PlayerGraveItemAddedEvent.EVENT.register((player, stack) -> {
            if (stack.getItem() instanceof BackpackItem && stack == player.getItemBySlot(EquipmentSlot.CHEST))
                return InteractionResult.FAIL;

            return InteractionResult.PASS;
        });
    }
}
