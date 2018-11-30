package aurocosh.divinefavor.common.requirements.cost.costs;

public enum DayTimeEnum {
    DAY(0,12000),
    NIGHT(12000,24000);

    private final int start;
    private final int stop;
    DayTimeEnum(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }
    public int getStart() {
        return start;
    }
    public int getStop() {
        return stop;
    }

    public boolean isTimeInPeriod(int time) {return start <= time && time <= stop;}
}
