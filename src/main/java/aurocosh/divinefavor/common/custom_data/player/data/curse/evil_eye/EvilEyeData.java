package aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import net.minecraft.util.math.MathHelper;

// The default implementation of the capability. Holds all the logic.
public class EvilEyeData {
    private int severity;

    public EvilEyeData() {
        severity = 1;
    }

    public void increaseSeverity(int severity) {
        setSeverity(this.severity + severity);
    }

    public void setSeverity(int severity) {
        this.severity = MathHelper.clamp(severity, ConfigSpells.evilEye.startingSeverity, ConfigSpells.evilEye.maxSeverity);
    }

    public int getSeverity() {
        return severity;
    }
}
