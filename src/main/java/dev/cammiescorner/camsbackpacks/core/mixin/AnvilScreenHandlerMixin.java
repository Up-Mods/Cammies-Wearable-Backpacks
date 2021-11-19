package dev.cammiescorner.camsbackpacks.core.mixin;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * THIS IS TO KEEP PEOPLE FROM USING BAD SLURS ON THE BACKPACKS
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	@Shadow private String newItemName;

	@Unique private static final List<String> GAY_SLURS = List.of("bean flicker", "carpet muncher", "dyke", "kitty puncher",
			"pussy puncher", "lezzie", "lesbo", "leso", "muff diver", "donut puncher", "donut muffer", "fag", "faggot");
	@Unique private static final List<String> BI_SLURS = List.of("switch hitter", "bicon");
	@Unique private static final List<String> TRANS_SLURS = List.of("futa", "herm", "cuntboy", "dickgirl", "shemale",
			"t girl", "t-girl", "tranny", "transbian");
	@Unique private static final List<String> RACIST_SLURS = List.of("nigger", "nigga", "negro");

	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) { super(type, syncId, playerInventory, context); }

	@Inject(method = "updateResult", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;setCustomName(Lnet/minecraft/text/Text;)Lnet/minecraft/item/ItemStack;"
	))
	public void camsbackpacks$updateResult(CallbackInfo info) {
		hippityHoppityYourSlursAreNowMyProperty();
	}

	@Inject(method = "setNewItemName", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;setCustomName(Lnet/minecraft/text/Text;)Lnet/minecraft/item/ItemStack;"
	))
	public void camsbackpacks$setNewItemName(String newItemName, CallbackInfo info) {
		hippityHoppityYourSlursAreNowMyProperty();
	}

	public void hippityHoppityYourSlursAreNowMyProperty() {
		Item item = this.input.getStack(0).getItem();

		if(item instanceof BackpackItem) {
			String name = this.newItemName.toLowerCase();

			for(String string : GAY_SLURS)
				if(name.contains(string)) {
					this.newItemName = "Gay Pride!";
					return;
				}
			for(String string : BI_SLURS)
				if(name.contains(string)) {
					this.newItemName = "Bi Pride!";
					return;
				}
			for(String string : TRANS_SLURS)
				if(name.contains(string)) {
					this.newItemName = "Trans Rights!";
					return;
				}
			for(String string : RACIST_SLURS)
				if(name.contains(string)) {
					this.newItemName = "Black Lives Matter!";
					return;
				}
		}
	}
}
