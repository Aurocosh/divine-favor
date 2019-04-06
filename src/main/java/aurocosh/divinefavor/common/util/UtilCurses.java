package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.config.common.ConfigCurses;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class UtilCurses {
    public static void applyCurse(EntityLivingBase victim, EntityLivingBase caster, ModEffect effect) {
        effect.setIsCurse();
        int resistance = getCurseResistance(victim);
        if (UtilRandom.rollDice(resistance))
            caster.addPotionEffect(effect);
        else
            victim.addPotionEffect(effect);
    }

    public static int getCurseResistance(EntityLivingBase victim) {
        int curseResistance = ConfigCurses.baseCurseResistance;

        String entityString = EntityList.getKey(victim).toString();
        Integer resistance = ConfigCurses.mobResistances.get(entityString);
        if (resistance != null)
            curseResistance += resistance;

        CurseData curse = LivingData.get(victim).getCurseData();
        curseResistance += curse.getCurseCount() * ConfigCurses.curseResistancePerCurse;
        if (victim instanceof EntityPlayer)
            curseResistance += ConfigCurses.playerCurseResistance;
        return curseResistance;
    }
}
