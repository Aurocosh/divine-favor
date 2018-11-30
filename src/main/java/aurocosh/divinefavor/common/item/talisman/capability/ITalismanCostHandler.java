package aurocosh.divinefavor.common.item.talisman.capability;

// The basic properties for the new capability.
public interface ITalismanCostHandler {
    int getSelectedUnitIndex();
    int setUnitIndex(int index);

    int getSelectedCostIndex();
    int setCostIndex(int index);
}
