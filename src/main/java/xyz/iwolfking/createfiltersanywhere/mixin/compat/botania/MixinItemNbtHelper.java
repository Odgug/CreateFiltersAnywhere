package xyz.iwolfking.createfiltersanywhere.mixin.compat.botania;

import com.simibubi.create.content.logistics.filter.FilterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.helper.ItemNBTHelper;
import xyz.iwolfking.createfiltersanywhere.api.core.CFATests;

@Restriction(
        require = {
                @Condition("botania")
        }
)
@Mixin(value = ItemNBTHelper.class, remap = false)
public class MixinItemNbtHelper {
    @Inject(method = "matchTagAndManaFullness", at = @At("HEAD"), cancellable = true)
    private static void matchCreateAttributeFilter(ItemStack stack, ItemStack filterStack, CallbackInfoReturnable<Boolean> cir) {
        if(filterStack.getItem() instanceof FilterItem) {
            cir.setReturnValue(CFATests.checkFilter(stack, filterStack, true, null));
        }
    }
}
