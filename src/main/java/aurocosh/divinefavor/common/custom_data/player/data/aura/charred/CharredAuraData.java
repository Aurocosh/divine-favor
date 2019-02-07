package aurocosh.divinefavor.common.custom_data.player.data.aura.charred;

import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class CharredAuraData extends SimpleCounter {
    private static final int FIRES_TO_IGNITE = 20;

    public CharredAuraData() {
        super(FIRES_TO_IGNITE);
    }

    @Override
    public void setRequired(int required) {
        throw new UnsupportedOperationException();
    }
}
