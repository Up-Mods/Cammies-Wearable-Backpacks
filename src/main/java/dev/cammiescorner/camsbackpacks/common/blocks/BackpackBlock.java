package dev.cammiescorner.camsbackpacks.common.blocks;

import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class BackpackBlock extends BlockWithEntity implements Waterloggable {
	private final DyeColor colour;
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(createCuboidShape(3.5, 0, 5, 12.5, 16, 11), createCuboidShape(1.5, 1, 6, 14.5, 6, 10), createCuboidShape(5, 4, 11, 11, 12, 13));
	public static final VoxelShape EAST_SHAPE = VoxelShapes.union(createCuboidShape(5, 0, 3.5, 11, 16, 12.5), createCuboidShape(6, 1, 1.5, 10, 6, 14.5), createCuboidShape(3, 4, 5, 5, 12, 11));
	public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(createCuboidShape(3.5, 0, 5, 12.5, 16, 11), createCuboidShape(1.5, 1, 6, 14.5, 6, 10), createCuboidShape(5, 4, 3, 11, 12, 5));
	public static final VoxelShape WEST_SHAPE = VoxelShapes.union(createCuboidShape(5, 0, 3.5, 11, 16, 12.5), createCuboidShape(6, 1, 1.5, 10, 6, 14.5), createCuboidShape(11, 4, 5, 13, 12, 11));

	public BackpackBlock(DyeColor colour, Settings settings) {
		super(settings);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
		this.colour = colour;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch(state.get(FACING)) {
			default:
				return NORTH_SHAPE;
			case EAST:
				return EAST_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(!world.isClient()) {
			if(player.isSneaking() && player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
				if(world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity) {
					ItemStack stack = new ItemStack(this);
					NbtCompound tag = stack.getOrCreateNbt();

					Inventories.writeNbt(tag, blockEntity.inventory);
					blockEntity.wasPickedUp = true;
					player.equipStack(EquipmentSlot.CHEST, stack);
					world.breakBlock(pos, false, player);
				}
			}
			else {
				NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

				if(screenHandlerFactory != null) {
					world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 1F, 1F);
					player.openHandledScreen(screenHandlerFactory);
				}
			}
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if(world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity && !blockEntity.wasPickedUp) {
			if(!world.isClient() && state.getBlock() != newState.getBlock()) {
				ItemScatterer.spawn(world, pos, blockEntity);
			}
		}

		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing()).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.BLOCK;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BackpackBlockEntity(pos, state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		if(state.get(Properties.WATERLOGGED))
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

		return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, Properties.WATERLOGGED);
	}

	public DyeColor getColour() {
		return colour;
	}
}
