package xyz.iwolfking.createfiltersanywhere.mixin.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.ListFilterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Restriction(
        require = {
                @Condition("create")
        }
)
@Mixin(FilterItem.class)
public abstract class MixinClientFilterItem {

    @Inject(method = "appendHoverText", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/filter/FilterItem;makeSummary(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", remap = false))
    private void addCtrlHint(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
        if(stack.getItem() instanceof ListFilterItem) {
            ChatFormatting color = Screen.hasControlDown() ? ChatFormatting.WHITE : ChatFormatting.GRAY;
            tooltip.add(Component.literal("Hold [").append(Component.translatable("create.tooltip.keyCtrl").withStyle(color)).append("] to show nested filters.").withStyle(ChatFormatting.DARK_GRAY));
        }
    }

}
