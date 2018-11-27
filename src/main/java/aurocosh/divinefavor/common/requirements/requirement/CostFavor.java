package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.annotations.Expose;

public class CostFavor extends Cost {
    @Expose
    private String favorName;
    @Expose
    private int favorCount;

    private ModFavor favor;

    @Override
    public void init() {
        favor = ModFavors.getByName(favorName);
    }

    public CostFavor(int priority, String favorName, int favorCount)
    {
        super(priority);
        this.favorName = favorName;
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
        if(favorCount == 0)
            return "Unlimited use";
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        int favorsLeft = data.getSpellCharge(favor.getId());
        int usesLeft = favorsLeft / favorCount;
        return "Uses left: " + usesLeft;
    }
}