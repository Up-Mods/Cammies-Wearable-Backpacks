package dev.cammiescorner.camsbackpacks.mixin;

import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.compat.ModCompat;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    private PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;dropAll()V"))
    private void camsbackpacks$dropInventory(CallbackInfo info) {
        ItemStack stack = getItemBySlot(EquipmentSlot.CHEST);
        BlockPos.MutableBlockPos pos = blockPosition().mutable();
        Level world = level();

        if (stack.getItem() instanceof BackpackItem item) {
            BlockState state = item.getBlock().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false);

            if (ModCompat.UNIVERSAL_GRAVES)
                pos = pos.move(getDirection());

            while (!BackpackBlock.isStateReplaceable(world, pos.immutable()) && pos.getY() < world.getMaxBuildHeight() - 1)
                pos = pos.move(0, 1, 0);

            world.setBlockAndUpdate(pos.immutable(), state);
            this.sendSystemMessage(Component.translatable("info.camsbackpacks.backpack_pos", pos.getX(), pos.getY(), pos.getZ()));

            if (world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack)
                ContainerHelper.loadAllItems(stack.getOrCreateTag(), backpack.inventory);

            setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        }
    }
}
