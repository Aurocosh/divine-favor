package aurocosh.divinefavor.common.custom_data.living.potion_status;

// The default implementation of the capability. Holds all the logic.
public class DefaultPotionStatusHandler implements IPotionStatusHandler {
    int curseCount;

    @Override
    public void addCurse() {
        curseCount++;
    }

    @Override
    public void removeCurse() {
        curseCount--;
    }

    @Override
    public boolean isCursed() {
        return curseCount > 0;
    }

    @Override
    public void setCurseCount(int curseCount) {
        this.curseCount = curseCount;
    }

    @Override
    public int getCurseCount() {
        return curseCount;
    }
}
