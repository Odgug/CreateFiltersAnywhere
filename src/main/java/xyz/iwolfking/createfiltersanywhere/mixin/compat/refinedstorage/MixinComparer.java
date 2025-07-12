package xyz.iwolfking.createfiltersanywhere.mixin.compat.refinedstorage;

import com.refinedmods.refinedstorage.apiimpl.util.Comparer;
import com.simibubi.create.content.logistics.filter.FilterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.createfiltersanywhere.Config;
import xyz.iwolfking.createfiltersanywhere.CreateFiltersAnywhere;
import xyz.iwolfking.createfiltersanywhere.api.core.CFATests;

@Restriction(
        require = {
                @Condition("refinedstorage")
        }
)
@Mixin(value = Comparer.class, remap = false)
public class MixinComparer {
    /**
     * @author
     * @reason
     */
    @Inject(method = "isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z", at = @At("HEAD"), cancellable = true)
    public void checkFilter(ItemStack left, ItemStack right, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.RS_COMPAT.get() || !(right.getItem() instanceof FilterItem) || flags != CreateFiltersAnywhere.CHECK_FILTER_FLAG) {
            return;
        }
        cir.setReturnValue(CFATests.checkFilter(left, right, true, null));
    }
}
