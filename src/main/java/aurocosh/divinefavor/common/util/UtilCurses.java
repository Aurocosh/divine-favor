package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

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

        CurseData curse = LivingData.get(victim).getCurseData();
        curseResistance += curse.getCurseCount() * CURSE_RESISTANCE_PER_CURSE;
        if(victim instanceof EntityPlayer)
            curseResistance += PLAYER_CURSE_RESISTANCE;
        return curseResistance;
    }
}
