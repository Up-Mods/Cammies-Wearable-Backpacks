package dev.cammiescorner.camsbackpacks.quilt.network.c2s;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.BlockHitResult;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class QPlaceBackpackPacket {
    public static final ResourceLocation ID = CamsBackpacks.id("place_backpack");

    public static void send(BlockHitResult hitResult) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBlockHitResult(hitResult);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        BlockHitResult hitResult = buf.readBlockHitResult();

        server.execute(() -> {
            Level world = player.level();
            BlockPos pos = BackpackBlock.isStateReplaceable(world, hitResult.getBlockPos()) ? hitResult.getBlockPos() : hitResult.getBlockPos().relative(hitResult.getDirection());

            if(!world.mayInteract(player, pos)) {
                player.closeContainer();
                player.sendSystemMessage(Component.translatable("error.camsbackpacks.permission_place_at").withStyle(ChatFormatting.RED), true);
                return;
            }

            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

            if (BackpackBlock.isStateReplaceable(world, pos)) {
                world.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1F, 1F);
                world.setBlockAndUpdate(pos, ((BackpackItem) stack.getItem()).getBlock().defaultBlockState().setValue(BackpackBlock.FACING, player.getDirection()).setValue(BlockStateProperties.WATERLOGGED, !world.getFluidState(pos).isEmpty()));

                if (world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack) {
                    ContainerHelper.loadAllItems(stack.getOrCreateTag(), backpack.inventory);
                    backpack.setName(stack.getHoverName());
                }

                player.getItemBySlot(EquipmentSlot.CHEST).shrink(1);
            }
        });
    }
}
