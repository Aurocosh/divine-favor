package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.SoundEvent;

public class SpookySoundSet {
    private final SoundEvent[] sounds;

    public SpookySoundSet(SoundEvent... sounds) {
        this.sounds = sounds;
    }

    public SoundEvent getAmbient() {
        return sounds[0];
    }

    public SoundEvent getDeath() {
        return sounds[1];
    }

    public SoundEvent[] getSounds() {
        return sounds;
    }

    public SoundEvent getRandomSound() {
        return UtilRandom.getRandom(sounds);
    }
}
