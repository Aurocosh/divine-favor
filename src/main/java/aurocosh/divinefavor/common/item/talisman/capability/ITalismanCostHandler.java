package aurocosh.divinefavor.common.item.talisman.capability;

// The basic properties for the new capability.
public interface ITalismanCostHandler {
    int getSelectedUnitIndex();
    int setSelectedUnitIndex(int index);

    int getSelectedCostIndex();
    int setSelectedCostIndex(int index);
}
