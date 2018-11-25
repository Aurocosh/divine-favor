package aurocosh.divinefavor.common.requirements;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.spell.base.SpellChargeType;
import aurocosh.divinefavor.common.spell.base.SpellContext;

public class SpellRequrementEmpowerAxe extends SpellRequirement {
    public SpellRequrementEmpowerAxe()
    {
        super(LibSpellRequirementNames.EMPOWER_AXE, SpellChargeType.SPELL_EMPOWER_AXE, false);
    }

    @Override
    protected boolean claim(SpellContext context)
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
        return data.consumeSpellCharge(chargeType,1);
    }
}
