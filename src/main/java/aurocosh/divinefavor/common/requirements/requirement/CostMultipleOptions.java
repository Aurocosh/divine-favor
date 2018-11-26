package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;

import java.util.ArrayList;

public class CostMultipleOptions extends Cost {
    protected ArrayList<Cost> costOptions;

    public CostMultipleOptions(int priority, ArrayList<Cost> costOptions)
    {
        super(priority);
        this.costOptions = costOptions;
    }

    @Override
    public boolean canClaim(SpellContext context)
    {
        for (Cost cost : costOptions)
            if (cost.canClaim(context))
                return true;
        return false;
    }

    @Override
    public boolean claim(SpellContext context)
    {
        for (Cost cost : costOptions)
            if (cost.claim(context))
                return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "multi";
    }

    public static CostMultipleOptions deserialize(JsonObject json)
    {
        int priority = JsonUtils.getInt(json, "priority", 0);
        ArrayList<Cost> costs = new ArrayList<>();

        JsonArray costArray = JsonUtils.getJsonArray(json,"costs");
        for(int i = 0; i < costArray.size(); i++)
        {
            JsonObject jsonObject = costArray.get(i).getAsJsonObject();
            costs.add(Cost.deserialize(jsonObject));
        }
        return new CostMultipleOptions(priority,costs);
    }
}