package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye.EvilEyeData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.MessageSyncEvilEye;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import java.util.EnumSet;

public class SpellTalismanEvilEye extends ItemSpellTalisman {
    public SpellTalismanEvilEye(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected boolean validate(TalismanContext context) {
        return context.target != null;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;

        EvilEyeData evilEyeData = PlayerData.get(player).getEvilEyeData();
        if (player.isPotionActive(ModCurses.evil_eye))
            evilEyeData.increaseSeverity(ConfigSpells.evilEye.severityIncrease);
        else
            evilEyeData.setSeverity(ConfigSpells.evilEye.startingSeverity);

        int severity = evilEyeData.getSeverity();
        EntityLivingBase target = context.target;
        target.attackEntityFrom(DamageSource.causePlayerDamage(player), ConfigSpells.evilEye.damagePerSeverity * severity);

        target.addPotionEffect(new PotionEffect(MobEffects.HASTE, ConfigSpells.evilEye.slownessTime, ConfigSpells.evilEye.slownessLevel));
        player.addPotionEffect(new ModEffect(ModCurses.evil_eye, ConfigSpells.evilEye.evilEyeTime).setIsCurse());

        new MessageSyncEvilEye(severity).sendTo(player);
    }
}
