package xyz.iwolfking.createfiltersanywhere.api.integration.handlers;



import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.item.FilterItem;
import com.tom.storagemod.item.PolyFilterItem;
import com.tom.storagemod.item.TagFilterItem;
import me.desht.modularrouters.logic.settings.ModuleFlags;
import net.minecraft.world.item.ItemStack;

public class TomsFilterHandler {

    public static final ModuleFlags DEFAULT_FLAGS =  new ModuleFlags(false, true, false, false, false);

    public static boolean isSupportedFilterItem(ItemStack filterStack) {
        return filterStack.getItem() instanceof FilterItem || filterStack.getItem() instanceof TagFilterItem;
    }

    public static boolean checkFilter(ItemStack stack, ItemStack filterStack) {
        if(filterStack.getItem() instanceof FilterItem filterItem) {
            return filterItem.createFilter(null, filterStack).test(new StoredItemStack(stack));
        }
        else if (filterStack.getItem() instanceof TagFilterItem tagFilterItem) {
            return tagFilterItem.createFilter(null, filterStack).test(new StoredItemStack(stack));
        }

        return false;
    }
}
