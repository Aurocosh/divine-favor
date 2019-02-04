package aurocosh.divinefavor.common.custom_data.player.data.burnt_smell;

import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class BurntSmellData extends SimpleCounter {
    private static final int FIRES_TO_IGNITE = 100;

    public BurntSmellData() {
        super(FIRES_TO_IGNITE);
    }

    @Override
    public void setRequired(int required) {
        throw new UnsupportedOperationException();
    }
}
