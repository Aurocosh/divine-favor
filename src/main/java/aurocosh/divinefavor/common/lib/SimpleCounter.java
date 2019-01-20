package aurocosh.divinefavor.common.lib;

public class SimpleCounter {
    private int required;
    private int count;

    public SimpleCounter(int required) {
        this.required = required;
        reset();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRequired(int required) {
        this.required = required;
        reset();
    }

    public void reset() {
        count = 0;
    }

    public boolean count() {
        if(count >= required)
            return true;
        count++;
        return false;
    }

    public boolean isFinished() {
        return count >= required;
    }
}
