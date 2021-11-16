package dev.cammiescorner.camsbackpacks.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.network.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BackpackScreen extends AbstractInventoryScreen<BackpackScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(CamsBackpacks.MOD_ID, "textures/gui/backpack.png");
	protected PlayerInventory playerInventory;
	protected ButtonWidget equipButton;
	protected PlayerEntity player;
	protected int craftingX;
	protected int craftingY;

	public BackpackScreen(BackpackScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
		this.playerInventory = playerInventory;
		this.backgroundWidth = 322;
		this.backgroundHeight = 190;
		this.player = playerInventory.player;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - 322) / 2;
		int y = (height - 190) / 2;
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 0, 322, 190, 190, 322);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		super.drawForeground(matrices, mouseX, mouseY);
		textRenderer.draw(matrices, new TranslatableText("container.crafting"), craftingX, craftingY, 4210752);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);

		InventoryScreen.drawEntity(x + 50, y + 125, 30, (x + 50) - mouseX, (y + 125 - 50) - mouseY, player);

		if(equipButton.isHovered() && !equipButton.active) {
			if(!handler.isBlockEntity)
				renderTooltip(matrices, new TranslatableText("container.camsbackpacks.obstructed_block"), mouseX, mouseY);
			else
				renderTooltip(matrices, new TranslatableText("container.camsbackpacks.cant_equip"), mouseX, mouseY);
		}
	}

	@Override
	protected void handledScreenTick() {
		if(!handler.isBlockEntity)
			handler.blockPos = player.getBlockPos().offset(player.getHorizontalFacing());

		if(equipButton != null)
			equipButton.active = (!handler.isBlockEntity && BackpackHelper.isReplaceable(player.world, handler.blockPos)) || player.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
	}

	@Override
	protected void init() {
		super.init();
		x = (width - backgroundWidth) / 2;
		titleX = 81;
		playerInventoryTitleX = 81;
		playerInventoryTitleY = 96;
		craftingX = 255;
		craftingY = 34;
		equipButton = addDrawableChild(new ButtonWidget(width / 2 + 86, height / 2 + 58, 68, 20, new TranslatableText(handler.isBlockEntity ? "container.camsbackpacks.equip" : "container.camsbackpacks.upequip"), this::doButtonShit));

		if(handler.isBlockEntity)
			drawStatusEffects = false;
	}

	@Override
	protected void applyStatusEffectOffset() {
		super.applyStatusEffectOffset();
	}

	private void doButtonShit(ButtonWidget button) {
		if(handler.isBlockEntity && player.getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			EquipBackpackPacket.send(true, handler.blockPos);
		else if(!handler.isBlockEntity)
			EquipBackpackPacket.send(false, handler.blockPos);
	}
}
