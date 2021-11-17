package dev.cammiescorner.camsbackpacks.core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.OpenBackpackScreenPacket;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {
	@Unique protected ItemStack playerInvIcon = ItemStack.EMPTY;
	@Unique protected ItemStack equippedStack = ItemStack.EMPTY;

	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) { super(screenHandler, playerInventory, text); }

	@Inject(method = "drawBackground", at = @At("TAIL"))
	public void camsbackpacks$drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
		if(equippedStack.getItem() instanceof BackpackItem) {
			RenderSystem.setShaderTexture(0, BackpackScreen.TEXTURE);
			DrawableHelper.drawTexture(matrices, x + 1, y - 27, 0, 0, 190, 60, 30, 220, 322);
			itemRenderer.renderInGui(playerInvIcon, x + 8, y - 20);
			itemRenderer.renderInGui(equippedStack, x + 38, y - 20);
			RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
		}
	}

	@Inject(method = "render", at = @At("TAIL"))
	public void camsbackpacks$render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if(equippedStack.getItem() instanceof BackpackItem) {
			if(isPointWithinBounds(3, -27, 26, 28, mouseX, mouseY))
				renderTooltip(matrices, new TranslatableText("container.camsbackpacks.player_inv"), mouseX, mouseY);
			else if(isPointWithinBounds(32, -27, 26, 28, mouseX, mouseY))
				renderTooltip(matrices, new TranslatableText("container.camsbackpacks.backpack_inv"), mouseX, mouseY);
		}
	}

	@Inject(method = "init", at = @At("TAIL"))
	public void camsbackpacks$init(CallbackInfo info) {
		playerInvIcon = BackpackScreen.getPlayerHead(client.player);
		equippedStack = client.player.getEquippedStack(EquipmentSlot.CHEST);

		if(equippedStack.getItem() instanceof BackpackItem)
			addSelectableChild(new ButtonWidget(x + 31, y - 27, 28, 28, new LiteralText(""), this::openBackpackScreen));
	}

	@Unique
	private void openBackpackScreen(ButtonWidget button) {
		CamsBackpacksClient.backpackScreenIsOpen = true;
		OpenBackpackScreenPacket.send();
	}
}
