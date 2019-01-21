package aurocosh.divinefavor.common.custom_data.player.corrosion;

import java.util.List;

public interface IArmorCorrosionStatusHandler {
    void removeAllCorrosion();
    void addCorrosionToArmorSlot(int slot);
    void removeCorrosionFromArmorSlot(int slot);
    List<Integer> getCorrodedArmorSlots();
    void setCorrodedArmorSlots(List<Integer> slots);

    boolean nothingToCorrode();
    boolean isCorrosionNeeded();
}
