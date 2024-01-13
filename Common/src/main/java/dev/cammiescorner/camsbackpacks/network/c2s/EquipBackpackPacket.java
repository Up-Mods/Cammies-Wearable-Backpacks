package dev.cammiescorner.camsbackpacks.network.c2s;

import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class EquipBackpackPacket {

    public static void send(boolean isBlockEntity, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    public static void handle(ServerPlayer player, BlockPos pos, boolean isBlockEntity) {
        Level world = player.level();

        if (!world.mayInteract(player, pos)) {
            player.closeContainer();
            MutableComponent message = isBlockEntity
                    ? Component.translatable("error.camsbackpacks.permission_pickup_at")
                    : Component.translatable("error.camsbackpacks.permission_place_at");
            player.sendSystemMessage(message.withStyle(ChatFormatting.RED), true);
            return;
        }

        if (isBlockEntity) {
            if (world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity) {
                ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock().asItem());
                CompoundTag tag = stack.getOrCreateTag();

                ContainerHelper.saveAllItems(tag, blockEntity.inventory);
                blockEntity.wasPickedUp = true;
                player.setItemSlot(EquipmentSlot.CHEST, stack);

                if (blockEntity.hasCustomName())
                    stack.setHoverName(blockEntity.getName());

                world.destroyBlock(pos, false, player);

                if (!BackpacksConfig.allowInventoryGui) {
                    player.closeContainer();
                    player.sendSystemMessage(Component.translatable("error.camsbackpacks.chest_slot_ui_disabled"));
                }

            }
        } else {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

            if (stack.getItem() instanceof BackpackItem backpackItem && BackpackBlock.isStateReplaceable(world, pos) && pos.closerThan(player.blockPosition(), 3)) {
                world.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1F, 1F);
                world.setBlockAndUpdate(pos, backpackItem.getBlock().defaultBlockState().setValue(BackpackBlock.FACING, player.getDirection()).setValue(BlockStateProperties.WATERLOGGED, !world.getFluidState(pos).isEmpty()));
                player.closeContainer();

                if (world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack) {
                    ContainerHelper.loadAllItems(stack.getOrCreateTag(), backpack.inventory);
                    backpack.setName(stack.getHoverName());
                }

                player.getItemBySlot(EquipmentSlot.CHEST).shrink(1);
            }
        }
    }

}
