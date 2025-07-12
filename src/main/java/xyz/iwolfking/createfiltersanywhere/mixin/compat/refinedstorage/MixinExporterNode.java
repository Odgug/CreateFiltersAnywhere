package xyz.iwolfking.createfiltersanywhere.mixin.compat.refinedstorage;

import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.api.util.IComparer;
import com.refinedmods.refinedstorage.apiimpl.network.node.ExporterNetworkNode;
import com.simibubi.create.content.logistics.filter.FilterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.iwolfking.createfiltersanywhere.Config;
import xyz.iwolfking.createfiltersanywhere.CreateFiltersAnywhere;
import xyz.iwolfking.createfiltersanywhere.api.core.CFATests;

@Restriction(
        require = {
                @Condition("refinedstorage")
        }
)
@Mixin(value = ExporterNetworkNode.class, remap = false)
public class MixinExporterNode {
    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/util/IComparer;isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z"))
    private int modifyCompareFlag(ItemStack left, ItemStack right, int compare) {
        if (Config.RS_COMPAT.get() && right.getItem() instanceof FilterItem) {
            return CreateFiltersAnywhere.CHECK_FILTER_FLAG;
        }
        return compare;
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/network/INetwork;extractItem(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack modifyCompareFlag(INetwork instance, ItemStack stack, int size, int flags, Action action) {
        if (Config.RS_COMPAT.get() && stack.getItem() instanceof FilterItem) {
            return instance.extractItem(stack, size, CreateFiltersAnywhere.CHECK_FILTER_FLAG, action);
        }
        return instance.extractItem(stack, size, flags, action);
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/util/IComparer;isEqualNoQuantity(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean EqualsNoQty(IComparer instance, ItemStack left, ItemStack right) {
        if (Config.RS_COMPAT.get() && right.getItem() instanceof FilterItem) {
            return CFATests.checkFilter(left, right,true,null);
        }
        return instance.isEqualNoQuantity(left, right);
    }
}
