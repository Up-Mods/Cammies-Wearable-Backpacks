package dev.cammiescorner.camsbackpacks.common.items;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.core.mixin.client.KeyBindingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class BackpackItem extends BlockItem {
	public BackpackItem(BackpackBlock block) {
		super(block, new Settings().group(ItemGroup.TOOLS).maxCount(1));
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(!BackpacksConfig.get().hideToolTip) {
			MinecraftClient client = MinecraftClient.getInstance();
			InputUtil.Key sneakKey = ((KeyBindingAccessor) client.options.keySneak).getKey();
			MutableText sneakName = ((MutableText) sneakKey.getLocalizedText()).formatted(Formatting.AQUA);
			MutableText useName = new TranslatableText(client.options.keyUse.getBoundKeyTranslationKey()).formatted(Formatting.AQUA);
			long handle = client.getWindow().getHandle();

			if(sneakKey.getCategory() == InputUtil.Type.MOUSE ? GLFW.glfwGetMouseButton(handle, sneakKey.getCode()) == 1 : GLFW.glfwGetKey(handle, sneakKey.getCode()) == 1) {
				tooltip.add(new LiteralText("-------------------------------").formatted(Formatting.GOLD));

				String[] instructions = new TranslatableText("info." + CamsBackpacks.MOD_ID + ".instructions", sneakName, useName).getString().split("\n");

				for(String instruction : instructions) {
					int counter = 0;

					while(instruction.length() > 30) {
						String[] e = instruction.substring(30).split("\\W+", 2);
						MutableText sub = new LiteralText(instruction.substring(0, 30) + e[0]);

						if(counter++ > 0)
							sub = new LiteralText("    ").append(sub);

						tooltip.add(sub);
						instruction = e.length > 1 ? e[1] : "";
					}

					if(!instruction.isBlank()) {
						MutableText sub = new LiteralText(instruction);

						if(counter > 0)
							sub = new LiteralText("    ").append(sub);

						tooltip.add(sub);
					}
				}

				tooltip.add(new LiteralText("-------------------------------").formatted(Formatting.GOLD));
			}
			else {
				tooltip.add(new TranslatableText("info." + CamsBackpacks.MOD_ID + ".more_info", sneakName).formatted(Formatting.YELLOW));
			}
		}
	}

	public DyeColor getColour() {
		return ((BackpackBlock) getBlock()).getColour();
	}
}
