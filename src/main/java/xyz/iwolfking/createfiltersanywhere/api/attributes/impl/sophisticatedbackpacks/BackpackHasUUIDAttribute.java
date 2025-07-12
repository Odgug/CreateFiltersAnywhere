package xyz.iwolfking.createfiltersanywhere.api.attributes.impl.sophisticatedbackpacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.createfiltersanywhere.api.CreateAttributeRegistry;
import xyz.iwolfking.createfiltersanywhere.api.attributes.impl.sophisticatedbackpacks.util.SophisticatedBackpackUtil;

import java.util.*;
public class BackpackHasUUIDAttribute implements ItemAttribute {

    private String UUID;

    public BackpackHasUUIDAttribute(String UUID) {
        this.UUID = UUID;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack, Level level) {
        String uuid = SophisticatedBackpackUtil.getBackpackUUID(itemStack);
        return uuid != null && uuid.equals(this.UUID);
    }

    @Override
    public ItemAttributeType getType() {
        return SophisticatedBackpackAttributes.HAS_BACKPACK_UUID;
    }

    @Override
    public void save(CompoundTag compoundTag) {
        compoundTag.putString("UUID", this.UUID);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        this.UUID = compoundTag.getString("UUID");
    }

    @Override
    public String getTranslationKey() {
        return "has_backpack_uuid";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.UUID};
    }

    public static class Type implements ItemAttributeType {
        @Override
        public @NotNull ItemAttribute createAttribute() {
            return new BackpackHasUUIDAttribute("dummy");
        }

        @Override
        public List<ItemAttribute> getAllAttributes(ItemStack stack, Level level) {
            List<ItemAttribute> list = new ArrayList<>();
            String name = SophisticatedBackpackUtil.getBackpackUUID(stack);
            if (name != null && !name.isEmpty()) {
                list.add(new BackpackHasUUIDAttribute(name));
            }

            return list;
        }
    }
}
