package xyz.iwolfking.createfiltersanywhere.mixin.compat.refinedstorage;

import com.google.common.collect.Multimap;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.storage.disk.ItemStorageDisk;
import com.simibubi.create.content.logistics.filter.FilterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;

@Restriction(
        require = {
                @Condition("refinedstorage")
        }
)
@Mixin(value = ItemStorageDisk.class, remap = false)
public abstract class MixinItemStorageDisk implements IStorageDisk<ItemStack> {
    @Redirect(method = "extract(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;get(Ljava/lang/Object;)Ljava/util/Collection;"))
    private Collection<ItemStack> getAllItemsIfFilter(Multimap<Item, ItemStack> instance, Object item) {
        return item instanceof FilterItem
                ? new ArrayList<>(instance.values())
                : instance.get((Item) item);
    }
}
