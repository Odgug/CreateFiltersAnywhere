package xyz.iwolfking.createfiltersanywhere.mixin.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.simibubi.create.content.logistics.filter.AttributeFilterItem;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.createfiltersanywhere.data.CFAComponents;

import java.util.List;

@Mixin(value = AttributeFilterItem.class, remap = false)
public class MixinAttributeFilterItem {

    @Inject(method = "makeSummary", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/item/filter/attribute/ItemAttribute$ItemAttributeEntry;attribute()Lcom/simibubi/create/content/logistics/item/filter/attribute/ItemAttribute;"), remap = false)
    private void unlimitedAttributeFilterTooltip(ItemStack filter, CallbackInfoReturnable<List<Component>> cir, @Local(ordinal = 0) LocalIntRef count){
        if (Screen.hasControlDown()) {
            count.set(1); // expand infinitely instead of limiting to 3 lines
        }
    }

}
