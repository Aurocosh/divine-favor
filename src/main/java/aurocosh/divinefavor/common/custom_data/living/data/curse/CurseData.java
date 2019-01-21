package aurocosh.divinefavor.common.custom_data.living.data.curse;

public class CurseData {
    int curseCount;

    public CurseData() {
        this.curseCount = 0;
    }

    public void addCurse() {
        curseCount++;
    }

    public void removeCurse() {
        curseCount--;
    }

    public boolean isCursed() {
        return curseCount > 0;
    }

    public void setCurseCount(int curseCount) {
        this.curseCount = curseCount;
    }

    public int getCurseCount() {
        return curseCount;
    }
}
