package dev.cammiescorner.camsbackpacks.util;

import net.minecraft.world.item.DyeColor;

public class ColorHelper {

    public static int dyeToDecimal(DyeColor colour) {
        return packRGB(colour.getTextureDiffuseColors());
    }

    /**
     * Packs the given RGB values into a single integer value in 0x00RRGGBB format.
     */
    public static int packRGB(float[] rgb) {
        return packRGB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255));
    }

    /**
     * Packs the given RGB values into a single integer value in 0x00RRGGBB format.
     */
    private static int packRGB(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
}
