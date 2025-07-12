package xyz.iwolfking.createfiltersanywhere.api.attributes.impl.sophisticatedbackpacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.iwolfking.createfiltersanywhere.api.CreateAttributeRegistry;
import xyz.iwolfking.createfiltersanywhere.api.attributes.impl.sophisticatedbackpacks.util.SophisticatedBackpackUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BackpackHasUpgradeAttribute implements ItemAttribute {

    private String upgrade;

    public BackpackHasUpgradeAttribute(@Nullable String upgrade) {
        this.upgrade = upgrade;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack, Level level) {
        if(itemStack.getItem() instanceof BackpackItem) {
            return SophisticatedBackpackUtil.getUpgrades(itemStack).contains(upgrade);
        }

        return false;
    }



    @Override
    public ItemAttributeType getType() {
        return SophisticatedBackpackAttributes.HAS_BACKPACK_UPGRADE;
    }

    @Override
    public void save(CompoundTag compoundTag) {
        compoundTag.putString("upgrade", this.upgrade);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        this.upgrade = compoundTag.getString("upgrade");
    }

    @Override
    public String getTranslationKey() {
        return "has_backpack_upgrade";
    }

    @Override
    public Object[] getTranslationParameters() {
        String modifiedItemName = this.upgrade.replace("[", "").replace("]", "").trim();
        return new Object[]{modifiedItemName};
    }

    public static class Type implements ItemAttributeType {
        @Override
        public @NotNull ItemAttribute createAttribute() {
            return new BackpackHasUpgradeAttribute("dummy");
        }

        @Override
        public List<ItemAttribute> getAllAttributes(ItemStack stack, Level level) {
            List<ItemAttribute> list = new ArrayList<>();
            List<String> upgrades = SophisticatedBackpackUtil.getUpgrades(stack);
            if (!upgrades.isEmpty()) {
                for(String upgrade : upgrades) {
                    list.add(new BackpackHasUpgradeAttribute(upgrade));
                }
            }

            return list;
        }
    }
}
