package dev.cammiescorner.camsbackpacks.core.util;

import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BackpackHelper {
	public static boolean isReplaceable(World world, BlockPos pos) {
		return world.getBlockState(pos).materialReplaceable();
	}

	public static int dyeToDecimal(DyeColor colour) {
		float[] rgb = colour.getColorComponents();

		return (((int) (rgb[0] * 255F) << 16) | ((int) (rgb[1] * 255F) << 8) | (int) (rgb[2] * 255F));
	}
}
