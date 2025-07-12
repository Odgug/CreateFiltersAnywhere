package xyz.iwolfking.createfiltersanywhere.api.attributes.impl.sophisticatedbackpacks.util;

import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SophisticatedBackpackUtil {
    public static List<String> getUpgrades(ItemStack stack) {
        List<String> upgradeNames = new ArrayList<>();
        if(stack.getItem() instanceof BackpackItem) {
            stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(
                    wrapper -> {
                        for(IUpgradeWrapper upgradeWrapper : wrapper.getUpgradeHandler().getSlotWrappers().values()) {
                            upgradeNames.add(upgradeWrapper.getUpgradeStack().getDisplayName().getString());
                        }
                    }
            );

        }
        return upgradeNames;
    }

    public static String getBackpackUUID(ItemStack stack) {
        AtomicReference<String> uuid = new AtomicReference<>();
        if(stack.getItem() instanceof BackpackItem) {
            stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(
                    wrapper -> {
                        wrapper.getContentsUuid().ifPresent(uuid1 -> {
                            uuid.set(uuid1.toString());
                        });
                    }
            );

            if(uuid.get() != null) {
                return uuid.get();
            }
        }


        return null;
    }
}
