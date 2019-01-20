package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.custom_data.living.potion_status.IPotionStatusHandler;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import static aurocosh.divinefavor.common.custom_data.living.potion_status.PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS;

public class UtilCurses {
    public static final int CURSE_RESISTANCE_PER_CURSE = 50;
    public static final int BASE_CURSE_RESISTANCE = 5;
    public static final int PLAYER_CURSE_RESISTANCE = 5;

    public static void applyCurse(EntityLivingBase victim, EntityLivingBase caster, ModEffect effect){
        effect.setIsCurse();
        int resistance = getCurseResistance(victim);
        if(UtilRandom.rollDice(resistance))
            caster.addPotionEffect(effect);
        else
            victim.addPotionEffect(effect);
    }

    public static int getCurseResistance(EntityLivingBase victim) {
        int curseResistance = BASE_CURSE_RESISTANCE;

        IPotionStatusHandler handler = victim.getCapability(CAPABILITY_POTION_STATUS, null);
        assert handler != null;
        curseResistance += handler.getCurseCount() * CURSE_RESISTANCE_PER_CURSE;
        if(victim instanceof EntityPlayer)
            curseResistance += PLAYER_CURSE_RESISTANCE;
        return curseResistance;
    }
}
