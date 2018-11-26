package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;

public class CostFavor extends Cost {
    protected int favorType;
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
    public String toString()
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
        return " current_charge: " + data.getSpellCharge(favorType);
    }

    public static CostFavor deserialize(JsonObject json)
    {
        int priority = JsonUtils.getInt(json, "priority", 0);
        int favorType = JsonUtils.getInt(json, "favorType", 0);
        int favorCount = JsonUtils.getInt(json, "favorCount", 1);
        return new CostFavor(priority,favorType,favorCount);
    }
}