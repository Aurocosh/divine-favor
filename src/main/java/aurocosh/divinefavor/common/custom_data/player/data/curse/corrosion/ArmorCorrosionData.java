package aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.lib.LoopedCounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class ArmorCorrosionData {
    private final List<Integer> corrodedSlots;
    private final LoopedCounter loopedCounter;

    public ArmorCorrosionData() {
        corrodedSlots = new ArrayList<>();
        loopedCounter = new LoopedCounter(ConfigArrow.armorCorrosion.corrosionRate);
    }

    public void removeAllCorrosion() {
        corrodedSlots.clear();
    }

    public void addCorrosionToArmorSlot(int slot) {
        assert slot > 0 && slot < 3;
        for (Integer corrodedSlot : corrodedSlots)
            if (corrodedSlot == slot)
                return;
        corrodedSlots.add(slot);
    }

    public void removeCorrosionFromArmorSlot(int slot) {
        corrodedSlots.remove(Integer.valueOf(slot));
    }

    public List<Integer> getCorrodedArmorSlots() {
        return Collections.unmodifiableList(corrodedSlots);
    }

    public void setCorrodedArmorSlots(List<Integer> slots) {
        corrodedSlots.clear();
        corrodedSlots.addAll(slots);
    }

    public boolean nothingToCorrode() {
        return corrodedSlots.size() == 0;
    }

    public boolean isCorrosionNeeded() {
        return loopedCounter.tick();
    }
}
