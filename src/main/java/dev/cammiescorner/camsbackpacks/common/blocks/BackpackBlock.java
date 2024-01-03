package dev.cammiescorner.camsbackpacks.common.blocks;

import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BackpackBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    private final DyeColor colour;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NORTH_SHAPE = Shapes.or(box(3.5, 0, 5, 12.5, 16, 11), box(1.5, 1, 6, 14.5, 6, 10), box(5, 4, 11, 11, 12, 13));
    public static final VoxelShape EAST_SHAPE = Shapes.or(box(5, 0, 3.5, 11, 16, 12.5), box(6, 1, 1.5, 10, 6, 14.5), box(3, 4, 5, 5, 12, 11));
    public static final VoxelShape SOUTH_SHAPE = Shapes.or(box(3.5, 0, 5, 12.5, 16, 11), box(1.5, 1, 6, 14.5, 6, 10), box(5, 4, 3, 11, 12, 5));
    public static final VoxelShape WEST_SHAPE = Shapes.or(box(5, 0, 3.5, 11, 16, 12.5), box(6, 1, 1.5, 10, 6, 14.5), box(11, 4, 5, 13, 12, 11));

    public BackpackBlock(BlockBehaviour.Properties properties, DyeColor colour) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
        this.colour = colour;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack) {
            backpack.setName(stack.getHoverName());
            ContainerHelper.loadAllItems(stack.getOrCreateTag(), backpack.inventory);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide()) {
            if(!world.mayInteract(player, pos)) {
                ((ServerPlayer) player).sendSystemMessage(Component.translatable("error.camsbackpacks.permission_use").withStyle(ChatFormatting.RED), true);
                return InteractionResult.FAIL;
            }


            if (BackpacksConfig.sneakPlaceBackpack && player.isShiftKeyDown() && player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                if (world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity) {
                    ItemStack stack = new ItemStack(this);
                    CompoundTag tag = stack.getOrCreateTag();

                    ContainerHelper.saveAllItems(tag, blockEntity.inventory);
                    blockEntity.wasPickedUp = true;

                    if (blockEntity.hasCustomName())
                        stack.setHoverName(blockEntity.getName());

                    player.setItemSlot(EquipmentSlot.CHEST, stack);
                    world.destroyBlock(pos, false, player);
                }
            } else {
                MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);

                if (screenHandlerFactory != null) {
                    world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.BLOCKS, 1F, 1F);
                    player.openMenu(screenHandlerFactory);
                }
            }
        }

        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity && !blockEntity.wasPickedUp) {
            if (!world.isClientSide() && state.getBlock() != newState.getBlock()) {
                Containers.dropContents(world, pos, blockEntity);
            }
        }

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING, ctx.getHorizontalDirection()).setValue(BlockStateProperties.WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BackpackBlockEntity(pos, state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, newState, world, pos, posFrom);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.WATERLOGGED);
    }

    public DyeColor getColour() {
        return colour;
    }
}
