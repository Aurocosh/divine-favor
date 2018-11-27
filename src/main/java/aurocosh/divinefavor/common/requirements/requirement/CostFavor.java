package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import net.minecraft.util.JsonUtils;

public class CostFavor extends Cost {
    @Expose
    protected int favorType;
    @Expose
    protected int favorCount;

    public CostFavor(int priority, int favorType, int favorCount)
    {
        super(priority);
        this.favorType = favorType;
        this.favorCount= favorCount;
    }

    public int getFavorType(){
        return favorType;
    }

    @Override
    public boolean canClaim(SpellContext context)
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        return data.getSpellCharge(favorType) > favorCount;
    }

    @Override
    public boolean claim(SpellContext context)
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        return data.consumeSpellCharge(favorType,favorCount);
    }

    @Override
    public String getUsageInfo(SpellContext context) {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        int favorsLeft = data.getSpellCharge(favorType);
        int usesLeft = favorsLeft / favorCount;
        return "Uses left: " + usesLeft;
    }
}