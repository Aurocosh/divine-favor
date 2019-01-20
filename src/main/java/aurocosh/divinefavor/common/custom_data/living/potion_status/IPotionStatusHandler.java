package aurocosh.divinefavor.common.custom_data.living.potion_status;

public interface IPotionStatusHandler {
    void addCurse();
    void removeCurse();

    boolean isCursed();

    void setCurseCount(int curseCount);
    int getCurseCount();
}
