package xyz.iwolfking.createfiltersanywhere.mixin.compat.botania;

import com.simibubi.create.content.logistics.filter.FilterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.impl.corporea.CorporeaItemStackMatcher;
import xyz.iwolfking.createfiltersanywhere.api.core.CFATests;

@Restriction(
        require = {
                @Condition("botania")
        }
)
@Mixin(value = CorporeaItemStackMatcher.class, remap = false)
public class MixinCorporeaItemStackMatcher {
    @Shadow @Final private ItemStack match;

    @Inject(method = "test(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void testCreateAttributeFilter(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(match.getItem() instanceof FilterItem && !stack.isEmpty()) {
            cir.setReturnValue(CFATests.checkFilter(stack, match, true, null));
        }
    }
}
