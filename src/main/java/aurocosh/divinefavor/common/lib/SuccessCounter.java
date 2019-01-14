package aurocosh.divinefavor.common.lib;

public class SuccessCounter {
    private int successesNeeded;
    private int attemptsMax;

    private int successes;
    private int attempts;

    public SuccessCounter(int successesNeeded, int attemptsMax) {
        this.successesNeeded = successesNeeded;
        this.attemptsMax = attemptsMax;
        reset();
    }

    public void reset() {
        successes = 0;
        attempts = 0;
    }

    public boolean attemptOnceMore() {
        attempts++;
        if(successes >= successesNeeded)
            return false;
        return attempts < attemptsMax;
    }

    public void registerSuccess(){
        successes++;
    }

    public void registerSuccess(boolean success){
        if(success)
            successes++;
    }
}
