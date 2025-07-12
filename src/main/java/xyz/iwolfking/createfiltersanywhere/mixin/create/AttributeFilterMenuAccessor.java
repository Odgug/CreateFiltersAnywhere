package xyz.iwolfking.createfiltersanywhere.mixin.create;

import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Restriction(
        require = {
                @Condition("create")
        }
)
@Mixin(value = AttributeFilterMenu.class,remap = false)
public interface AttributeFilterMenuAccessor {
    @Accessor("selectedAttributes")
    List<net.createmod.catnip.data.Pair<com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute, Boolean>> getSelectedAttributes();
}