package dev.cammiescorner.camsbackpacks.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.menu.BackpackMenu;
import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("ConstantConditions")
public class BackpackScreen extends EffectRenderingInventoryScreen<BackpackMenu> {
    public static final ResourceLocation TEXTURE = CamsBackpacks.id("textures/gui/backpack.png");
    protected Inventory playerInventory;
    protected Button equipButton;
    protected Player player;
    protected ItemStack playerInvIcon;
    protected ItemStack backpackInvIcon;
    protected int craftingX;
    protected int craftingY;
    protected int playerNameX;
    protected int playerNameY;

    public BackpackScreen(BackpackMenu screenHandler, Inventory playerInventory, Component text) {
        super(screenHandler, playerInventory, text);
        this.playerInventory = playerInventory;
        this.imageWidth = 322;
        this.imageHeight = 220;
        this.player = playerInventory.player;
        this.playerInvIcon = getPlayerHead(player);
        this.backpackInvIcon = getBackpack();
    }

    @Override
    protected void renderBg(GuiGraphics gui, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 322) / 2;
        int y = (height - 220) / 2;
        gui.blit(TEXTURE, x, y, 0, 0, 0, 322, 190, 322, 220);

        if (!menu.isBlockEntity) {
            gui.blit(TEXTURE, x + 1, y - 1, 0, 60, 190, 60, 30, 322, 220);
            gui.renderItem(playerInvIcon, x + 8, y + 6);
            gui.renderItem(backpackInvIcon, x + 38, y + 6);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        super.renderLabels(gui, mouseX, mouseY);

        gui.drawString(font, Component.translatable("container.crafting"), craftingX, craftingY, 4210752, false);

        PoseStack matrices = gui.pose();
        matrices.pushPose();
        String name = player.getScoreboardName();
        float scale = Math.min(1F, 1 / (name.length() / 11F));
        float translate = Math.max(0F, name.length() - 11);
        matrices.scale(scale, scale, 1);
        matrices.translate(translate, translate * 4, 0);
        gui.drawString(font, player.getName(), playerNameX, playerNameY, 4210752, false);
        matrices.popPose();
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        renderBackground(gui, mouseX, mouseY, delta);
        super.render(gui, mouseX, mouseY, delta);
        renderTooltip(gui, mouseX, mouseY);

        InventoryScreen.renderEntityInInventoryFollowsMouse(gui, leftPos + 50, topPos + 125, leftPos + 80, topPos + 200, 30, 0.0625F, mouseX, mouseY, player);

        if (equipButton.isHovered() && !equipButton.active) {
            if (!menu.isBlockEntity)
                gui.renderTooltip(font, Component.translatable("container.camsbackpacks.obstructed_block"), mouseX, mouseY);
            else
                gui.renderTooltip(font, Component.translatable("container.camsbackpacks.cant_equip"), mouseX, mouseY);
        }

        if (!menu.isBlockEntity) {
            if (isHovering(3, 1, 26, 28, mouseX, mouseY))
                gui.renderTooltip(font, Component.translatable("container.camsbackpacks.player_inv"), mouseX, mouseY);
            else if (isHovering(32, 1, 26, 28, mouseX, mouseY))
                gui.renderTooltip(font, Component.translatable("container.camsbackpacks.backpack_inv"), mouseX, mouseY);
        }
    }

    @Override
    protected void containerTick() {
        if (!menu.isBlockEntity)
            menu.blockPos = player.blockPosition().relative(player.getDirection());

        if (equipButton != null)
            equipButton.active = (!menu.isBlockEntity && BackpackBlock.isStateReplaceable(player.level(), menu.blockPos)) || player.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (width - imageWidth) / 2;
        titleLabelX = 81;
        inventoryLabelX = 81;
        inventoryLabelY = 96;
        craftingX = 255;
        craftingY = 34;
        playerNameX = 8;
        playerNameY = 38;
        var text = menu.isBlockEntity ? Component.translatable("container.camsbackpacks.equip") : Component.translatable("container.camsbackpacks.unequip");
        equipButton = this.addRenderableWidget(new Button.Builder(text, this::doEquipButtonShit).bounds(leftPos + 246, topPos + 156, 69, 20).build());

        if (!menu.isBlockEntity) {
            this.addWidget(new Button.Builder(Component.empty(), this::openVanillaInventory).bounds(this.leftPos + 2, this.topPos - 1, 28, 28).build());
        }
    }

    private void doEquipButtonShit(Button button) {
        if (menu.isBlockEntity && player.getItemBySlot(EquipmentSlot.CHEST).isEmpty())
            EquipBackpackPacket.send(true, menu.blockPos);
        else if (!menu.isBlockEntity)
            EquipBackpackPacket.send(false, menu.blockPos);
    }

    private void openVanillaInventory(Button button) {
        CamsBackpacksClient.backpackScreenIsOpen = false;
        double x = minecraft.mouseHandler.xpos();
        double y = minecraft.mouseHandler.ypos();
        minecraft.player.closeContainer();
        minecraft.setScreen(new InventoryScreen(player));
        GLFW.glfwSetCursorPos(minecraft.getWindow().getWindow(), x, y);
    }

    public static ItemStack getPlayerHead(Player player) {
        ItemStack head = new ItemStack(Blocks.PLAYER_HEAD);
        head.getOrCreateTag().putString("SkullOwner", player.getGameProfile().getName());
        SkullBlockEntity.resolveGameProfile(head.getTag());

        return head;
    }

    private ItemStack getBackpack() {
        return menu.isBlockEntity ? new ItemStack(player.level().getBlockState(menu.blockPos).getBlock()) : player.getItemBySlot(EquipmentSlot.CHEST);
    }
}
