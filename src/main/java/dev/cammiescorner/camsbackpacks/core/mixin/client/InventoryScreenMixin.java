package dev.cammiescorner.camsbackpacks.core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.OpenBackpackScreenPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends EffectRenderingInventoryScreen<InventoryMenu> {

    @Unique
    protected ItemStack playerInvIcon;
    @Unique
    protected ItemStack equippedStack;

    private InventoryScreenMixin(InventoryMenu screenHandler, Inventory playerInventory, Component text) {
        super(screenHandler, playerInventory, text);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "renderBg", at = @At("TAIL"))
    private void camsbackpacks$renderBg(GuiGraphics gui, float delta, int mouseX, int mouseY, CallbackInfo info) {
        if (equippedStack.getItem() instanceof BackpackItem) {
            RenderSystem.setShaderTexture(0, BackpackScreen.TEXTURE);
            gui.blit(BackpackScreen.TEXTURE, leftPos + 1, topPos - 27, 0, 0, 190, 60, 30, 322, 220);
            gui.renderItem(playerInvIcon, leftPos + 8, topPos - 20);
            gui.renderItem(equippedStack, leftPos + 38, topPos - 20);
            RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void camsbackpacks$render(GuiGraphics gui, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (equippedStack.getItem() instanceof BackpackItem) {
            if (isHovering(3, -27, 26, 28, mouseX, mouseY))
                gui.renderTooltip(font, Component.translatable("container.camsbackpacks.player_inv"), mouseX, mouseY);
            else if (isHovering(32, -27, 26, 28, mouseX, mouseY))
                gui.renderTooltip(font, Component.translatable("container.camsbackpacks.backpack_inv"), mouseX, mouseY);
        }
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void camsbackpacks$init(CallbackInfo info) {
        playerInvIcon = BackpackScreen.getPlayerHead(minecraft.player);
        equippedStack = minecraft.player.getItemBySlot(EquipmentSlot.CHEST);

        if (equippedStack.getItem() instanceof BackpackItem) {
            this.addWidget(new Button.Builder(Component.empty(), this::openBackpackScreen)
                    .bounds(this.leftPos + 31, this.topPos - 27, 28, 28).build());
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void camsbackpacks$constructor(Player player, CallbackInfo ci) {
        playerInvIcon = ItemStack.EMPTY;
        equippedStack = ItemStack.EMPTY;
    }

    @Unique
    private void openBackpackScreen(Button button) {
        CamsBackpacksClient.backpackScreenIsOpen = true;
        OpenBackpackScreenPacket.send();
    }
}
