package dev.cammiescorner.camsbackpacks.network.c2s;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

public record PlaceBackpackPacket(BlockHitResult hitResult) implements CustomPacketPayload {

    public static final ResourceLocation ID = CamsBackpacks.id("place_backpack");

    public static void send(BlockHitResult hitResult) {
        //noinspection DataFlowIssue
        Minecraft.getInstance().getConnection().send(new ServerboundCustomPayloadPacket(new PlaceBackpackPacket(hitResult)));
    }

    public void handle(ServerPlayer player) {
        Level world = player.level();
        var hitResult = hitResult();

        // safeguard against wrong data from clients
        if(hitResult.getType() == BlockHitResult.Type.MISS) return;

        BlockPos pos = BackpackBlock.isStateReplaceable(world, hitResult.getBlockPos()) ? hitResult.getBlockPos() : hitResult.getBlockPos().relative(hitResult.getDirection());

        if (!world.mayInteract(player, pos)) {
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
    }

    public static PlaceBackpackPacket decode(FriendlyByteBuf buf) {
        return new PlaceBackpackPacket(buf.readBlockHitResult());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockHitResult(hitResult());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
