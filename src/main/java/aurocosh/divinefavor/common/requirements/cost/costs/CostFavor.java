package aurocosh.divinefavor.common.requirements.cost.costs;

import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.spell.base.SpellContext;

public class CostFavor extends Cost {
    private int favorCount;
    private ModFavor favor;

    public CostFavor(ModFavor favor, int favorCount)
    {
        this.favor = favor;
        this.favorCount= favorCount;
    }

    @Override
    public boolean canClaim(SpellContext context)
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        return data.getSpellCharge(favor.getId()) >= favorCount;
    }

    @Override
    public boolean claim(SpellContext context)
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        return data.consumeSpellCharge(favor.getId(),favorCount);
    }

    @Override
    public String getUsageInfo(SpellContext context) {
        int usesLeft = getUseCount(context);
        if(favorCount == -1)
            return "Unlimited use";
        return "Uses left: " + usesLeft;
    }

    @Override
    public int getUseCount(SpellContext context) {
        if(favorCount == 0)
            return -1;
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        int favorsLeft = data.getSpellCharge(favor.getId());
        return favorsLeft / favorCount;
    }
}