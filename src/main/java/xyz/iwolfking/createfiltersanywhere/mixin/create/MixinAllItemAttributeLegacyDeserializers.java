package xyz.iwolfking.createfiltersanywhere.mixin.create;

import com.simibubi.create.content.logistics.item.filter.attribute.AllItemAttributeTypes;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import com.simibubi.create.content.logistics.item.filter.attribute.legacydeserializers.AllItemAttributeLegacyDeserializers;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.createfiltersanywhere.api.attributes.impl.sophisticatedbackpacks.SophisticatedBackpackAttributes;

@Restriction(
        require = {
                @Condition("create")
        }
)
@Mixin(value = AllItemAttributeLegacyDeserializers.class, remap = false)
public abstract class MixinAllItemAttributeLegacyDeserializers {
    @Shadow
    protected static void createLegacyDeserializer(String nbtKey, ItemAttributeType type) {
    }

    @Inject(method = "register", at = @At("TAIL"))
    private static void registerNewLegacyAttributeDeserializers(CallbackInfo ci) {
        createLegacyDeserializer("has_backpack_upgrade", SophisticatedBackpackAttributes.HAS_BACKPACK_UPGRADE);
        createLegacyDeserializer("has_backpack_uuid", SophisticatedBackpackAttributes.HAS_BACKPACK_UUID);
    }
}
