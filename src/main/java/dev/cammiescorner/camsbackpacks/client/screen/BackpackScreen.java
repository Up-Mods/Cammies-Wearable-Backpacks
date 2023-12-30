package dev.cammiescorner.camsbackpacks.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.common.network.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("ConstantConditions")
public class BackpackScreen extends AbstractInventoryScreen<BackpackScreenHandler> {
	public static final Identifier TEXTURE = CamsBackpacks.id("textures/gui/backpack.png");
	protected PlayerInventory playerInventory;
	protected ButtonWidget equipButton;
	protected PlayerEntity player;
	protected ItemStack playerInvIcon;
	protected ItemStack backpackInvIcon;
	protected int craftingX;
	protected int craftingY;
	protected int playerNameX;
	protected int playerNameY;

	public BackpackScreen(BackpackScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
		this.playerInventory = playerInventory;
		this.backgroundWidth = 322;
		this.backgroundHeight = 220;
		this.player = playerInventory.player;
		this.playerInvIcon = getPlayerHead(player);
		this.backpackInvIcon = getBackpack();
	}

	@Override
	protected void drawBackground(GuiGraphics gui, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - 322) / 2;
		int y = (height - 220) / 2;
		gui.drawTexture(TEXTURE, x, y, 0, 0, 0, 322, 190, 322, 220);

		if(!handler.isBlockEntity) {
			gui.drawTexture(TEXTURE, x + 1, y - 1, 0, 60, 190, 60, 30, 322, 220);
			gui.drawItem(playerInvIcon, x + 8, y + 6);
			gui.drawItem(backpackInvIcon, x + 38, y + 6);
		}
	}

	@Override
	protected void drawForeground(GuiGraphics gui, int mouseX, int mouseY) {
		super.drawForeground(gui, mouseX, mouseY);

		gui.drawText(textRenderer, Text.translatable("container.crafting"), craftingX, craftingY, 4210752, false);

		MatrixStack matrices = gui.getMatrices();
		matrices.push();
		String name = player.getEntityName();
		float scale = Math.min(1F, 1 / (name.length() / 11F));
		float translate = Math.max(0F, name.length() - 11);
		matrices.scale(scale, scale, 1);
		matrices.translate(translate, translate * 4, 0);
		gui.drawText(textRenderer, player.getName(), playerNameX, playerNameY, 4210752, false);
		matrices.pop();
	}

	@Override
	public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
		renderBackground(gui);
		super.render(gui, mouseX, mouseY, delta);
		drawMouseoverTooltip(gui, mouseX, mouseY);

		InventoryScreen.drawEntity(gui, x + 50, y + 125, 30, (x + 50) - mouseX, (y + 125 - 50) - mouseY, player);

		if(equipButton.isHovered() && !equipButton.active) {
			if(!handler.isBlockEntity)
				gui.drawTooltip(textRenderer, Text.translatable("container.camsbackpacks.obstructed_block"), mouseX, mouseY);
			else
				gui.drawTooltip(textRenderer, Text.translatable("container.camsbackpacks.cant_equip"), mouseX, mouseY);
		}

		if(!handler.isBlockEntity) {
			if(isPointWithinBounds(3, 1, 26, 28, mouseX, mouseY))
				gui.drawTooltip(textRenderer, Text.translatable("container.camsbackpacks.player_inv"), mouseX, mouseY);
			else if(isPointWithinBounds(32, 1, 26, 28, mouseX, mouseY))
				gui.drawTooltip(textRenderer, Text.translatable("container.camsbackpacks.backpack_inv"), mouseX, mouseY);
		}
	}

	@Override
	protected void handledScreenTick() {
		if(!handler.isBlockEntity)
			handler.blockPos = player.getBlockPos().offset(player.getHorizontalFacing());

		if(equipButton != null)
			equipButton.active = (!handler.isBlockEntity && BackpackHelper.isReplaceable(player.getWorld(), handler.blockPos)) || player.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
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
		playerNameX = 8;
		playerNameY = 38;
		var text = handler.isBlockEntity ? Text.translatable("container.camsbackpacks.equip") : Text.translatable("container.camsbackpacks.unequip");
		equipButton = this.addDrawableChild(new ButtonWidget.Builder(text, this::doEquipButtonShit).positionAndSize(x + 246, y + 156, 69, 20).build());

		if(!handler.isBlockEntity) {
			this.addSelectableChild(new ButtonWidget.Builder(Text.empty(), this::openVanillaInventory).positionAndSize(this.x + 2, this.y - 1, 28, 28).build());
		}
	}

	private void doEquipButtonShit(ButtonWidget button) {
		if(handler.isBlockEntity && player.getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			EquipBackpackPacket.send(true, handler.blockPos);
		else if(!handler.isBlockEntity)
			EquipBackpackPacket.send(false, handler.blockPos);
	}

	private void openVanillaInventory(ButtonWidget button) {
		CamsBackpacksClient.backpackScreenIsOpen = false;
		double x = client.mouse.getX();
		double y = client.mouse.getY();
		client.player.closeHandledScreen();
		client.setScreen(new InventoryScreen(player));
		GLFW.glfwSetCursorPos(client.getWindow().getHandle(), x, y);
	}

	public static ItemStack getPlayerHead(PlayerEntity player) {
		ItemStack head = new ItemStack(Blocks.PLAYER_HEAD);
		NbtCompound tag = head.getOrCreateNbt();
		SkullBlockEntity.loadProperties(player.getGameProfile(), (profile) -> tag.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), profile)));

		return head;
	}

	private ItemStack getBackpack() {
		return handler.isBlockEntity ? new ItemStack(player.getWorld().getBlockState(handler.blockPos).getBlock()) : player.getEquippedStack(EquipmentSlot.CHEST);
	}
}
