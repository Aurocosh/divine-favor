package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import net.minecraft.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;

public class CostMultipleOptions extends Cost {
    @Expose
    protected ArrayList<Cost> costOptions;

    public CostMultipleOptions(int priority, ArrayList<Cost> costOptions)
    {
        super(priority);
        this.costOptions = costOptions;
        Collections.sort(this.costOptions,new CostComparator());
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
    public String getUsageInfo(SpellContext context) {
        for (Cost cost : costOptions)
            if (cost.canClaim(context))
                return getUsageInfo(context);
        return "Unusable";
    }
}