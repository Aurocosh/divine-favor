package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.requirements.requirement.Cost;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SpellRequirementNotFree extends SpellRequirement {
    public String name;
    public Cost cost;

    public SpellRequirementNotFree(String name, Cost cost)
    {
        super(name);
        this.name = name;
        this.cost = cost;
    }

    @Override
    public boolean canClaimCostInternal(SpellContext context) {
        return cost.canClaim(context);
    }

    @Override
    public boolean claimCostInternal(SpellContext context) {
        return cost.claim(context);
    }

    public String toString()
    {
        return "NotFree";
    }

    public static SpellRequirementNotFree deserialize(JsonObject json)
    {
        String name = JsonUtils.getString(json, "name", "");
        JsonObject costJson = JsonUtils.getJsonObject(json,"cost");
        Cost cost = Cost.deserialize(costJson);
        return new SpellRequirementNotFree(name,cost);
    }
}