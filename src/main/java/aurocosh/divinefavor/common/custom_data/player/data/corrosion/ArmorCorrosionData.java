package aurocosh.divinefavor.common.custom_data.player.data.corrosion;

import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.util.UtilTick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class ArmorCorrosionData {
    private final int CORROSION_RATE = UtilTick.secondsToTicks(1);
    private final List<Integer> corrodedSlots;
    private final TickCounter tickCounter;

    public ArmorCorrosionData() {
        corrodedSlots = new ArrayList<>();
        tickCounter = new TickCounter(CORROSION_RATE);
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
        return tickCounter.tick();
    }
}
