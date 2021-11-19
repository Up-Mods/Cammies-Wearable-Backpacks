package dev.cammiescorner.camsbackpacks.common.network;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EquipBackpackPacket {
	public static final Identifier ID = new Identifier(CamsBackpacks.MOD_ID, "equip_backpack");

	public static void send(boolean isBlockEntity, BlockPos pos) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBlockPos(pos);
		buf.writeBoolean(isBlockEntity);
		ClientPlayNetworking.send(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		BlockPos pos = buf.readBlockPos();
		boolean isBlockEntity = buf.readBoolean();

		server.execute(() -> {
			World world = player.world;

			if(isBlockEntity) {
				if(world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity) {
					ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock().asItem());
					NbtCompound tag = stack.getOrCreateNbt();

					Inventories.writeNbt(tag, blockEntity.inventory);
					blockEntity.wasPickedUp = true;
					player.equipStack(EquipmentSlot.CHEST, stack);
					world.breakBlock(pos, false, player);
					player.closeHandledScreen();
				}
			}
			else {
				ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

				if(stack.getItem() instanceof BackpackItem backpackItem && BackpackHelper.isReplaceable(world, pos) && pos.isWithinDistance(player.getBlockPos(), 3)) {
					world.playSound(null, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
					world.setBlockState(pos, backpackItem.getBlock().getDefaultState().with(BackpackBlock.FACING, player.getHorizontalFacing()).with(Properties.WATERLOGGED, !world.getFluidState(pos).isEmpty()));
					player.closeHandledScreen();

					if(world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack)
						Inventories.readNbt(stack.getOrCreateNbt(), backpack.inventory);

					player.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
				}
			}
		});
	}
}
