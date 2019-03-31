package aurocosh.divinefavor.common.custom_data.player.data.aura.charred;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class CharredAuraData extends SimpleCounter {
    public CharredAuraData() {
        super(ConfigAura.charredAura.firesToIgnite);
    }

    @Override
    public void setRequired(int required) {
        throw new UnsupportedOperationException();
    }
}
