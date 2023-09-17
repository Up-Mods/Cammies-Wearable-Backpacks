package dev.cammiescorner.camsbackpacks.common.network;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class PlaceBackpackPacket {
	public static final Identifier ID = CamsBackpacks.id("place_backpack");

	public static void send(BlockHitResult hitResult) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBlockHitResult(hitResult);
		ClientPlayNetworking.send(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		BlockHitResult hitResult = buf.readBlockHitResult();

		server.execute(() -> {
			World world = player.getWorld();
			BlockPos pos = BackpackHelper.isReplaceable(world, hitResult.getBlockPos()) ? hitResult.getBlockPos() : hitResult.getBlockPos().offset(hitResult.getSide());
			ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

			if(BackpackHelper.isReplaceable(world, pos)) {
				world.playSound(null, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
				world.setBlockState(pos, ((BackpackItem) stack.getItem()).getBlock().getDefaultState().with(BackpackBlock.FACING, player.getHorizontalFacing()).with(Properties.WATERLOGGED, !world.getFluidState(pos).isEmpty()));

				if(world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack) {
					Inventories.readNbt(stack.getOrCreateNbt(), backpack.inventory);
					backpack.setName(stack.getName());
				}

				player.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
			}
		});
	}
}
