package dev.cammiescorner.camsbackpacks.common.network;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Material;
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

public class PlaceBackpackPacket
{
	public static final Identifier ID = new Identifier(CamsBackpacks.MOD_ID, "place_backpack");

	public static void send(BlockHitResult hitResult)
	{
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

		buf.writeBlockHitResult(hitResult);

		ClientPlayNetworking.send(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender)
	{
		BlockHitResult hitResult = buf.readBlockHitResult();

		server.execute(() ->
		{
			World world = player.world;
			BlockPos pos = world.getBlockState(hitResult.getBlockPos()).getMaterial() == Material.REPLACEABLE_PLANT || world.getBlockState(hitResult.getBlockPos()).getMaterial() == Material.REPLACEABLE_UNDERWATER_PLANT ? hitResult.getBlockPos() : hitResult.getBlockPos().offset(hitResult.getSide());
			ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

			if(world.isAir(pos) || (world.getBlockState(pos).getMaterial() == Material.REPLACEABLE_PLANT || world.getBlockState(pos).getMaterial() == Material.REPLACEABLE_UNDERWATER_PLANT) || !world.getFluidState(pos).isEmpty())
			{
				world.playSound(null, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
				world.setBlockState(pos, ((BackpackItem) stack.getItem()).getBlock().getDefaultState().with(BackpackBlock.FACING, player.getHorizontalFacing()).with(Properties.WATERLOGGED, !world.getFluidState(pos).isEmpty()));
				BackpackBlockEntity be = (BackpackBlockEntity) world.getBlockEntity(pos);
				Inventories.fromTag(stack.getOrCreateTag(), be.inventory);
				player.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
			}
		});
	}
}
