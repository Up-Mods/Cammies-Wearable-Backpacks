package dev.cammiescorner.camsbackpacks.common.network;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class EquipBackpackPacket {
    public static final ResourceLocation ID = CamsBackpacks.id("equip_backpack");

    public static void send(boolean isBlockEntity, BlockPos pos) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        buf.writeBoolean(isBlockEntity);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        boolean isBlockEntity = buf.readBoolean();

        server.execute(() -> {
            Level world = player.level();

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
                    PlayerLookup.tracking(blockEntity).forEach(playerEntity -> {
                        if (playerEntity.containerMenu instanceof BackpackScreenHandler handler && handler.blockPos.equals(pos))
                            playerEntity.closeContainer();
                    });
                }
            } else {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

                if (stack.getItem() instanceof BackpackItem backpackItem && BackpackHelper.isReplaceable(world, pos) && pos.closerThan(player.blockPosition(), 3)) {
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
        });
    }
}
