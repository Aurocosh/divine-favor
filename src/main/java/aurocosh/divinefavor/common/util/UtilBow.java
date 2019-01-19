package aurocosh.divinefavor.common.util;

public class UtilBow {
    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getArrowVelocity(int charge) {
        float velicity = (float) charge / 20.0F;
        velicity = (velicity * velicity + velicity * 2.0F) / 3.0F;

        if (velicity > 1.0F)
            velicity = 1.0F;
        return velicity;
    }
}
