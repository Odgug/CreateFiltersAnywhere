package xyz.iwolfking.createfiltersanywhere.mixin.compat.refinedstorage;

import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.apiimpl.storage.externalstorage.ItemExternalStorage;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(
        require = {
                @Condition("refinedstorage")
        }
)
@Mixin(value = ItemExternalStorage.class, remap = false)
public class MixinItemExternalStorage {
    @Redirect(method="extract(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;grow(I)V",remap = true)
    )
    private void checkGrow(ItemStack instance, int pIncrement, @Local(ordinal=1) ItemStack received, @Local(ordinal = 3) ItemStack got) {
        if (ItemStack.isSameItemSameTags(received,got)) {
            received.grow(got.getCount());
        }

    }
}
