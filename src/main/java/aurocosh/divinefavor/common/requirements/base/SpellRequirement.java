package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.lib.IInitiatable;
import aurocosh.divinefavor.common.requirements.cost.costs.Cost;
import aurocosh.divinefavor.common.requirements.cost.costs.CostFree;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.annotations.Expose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class SpellRequirement implements IInitiatable {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    @Expose
    private List<Cost> costs;

    public List<Cost> getCosts() {
        return Collections.unmodifiableList(costs);
    }

    @Override
    public void init() {
        costs.forEach(Cost::init);
    }

    public SpellRequirement() {
        this.costs = Arrays.asList(new CostFree());
    }

    public SpellRequirement(List<Cost> costs)
    {
        this.costs = costs;
    }

    public boolean canClaimCost(SpellContext context) {
        if(!isTruePlayer(context.playerIn))
            return false;

        if (canClaimInternal(context))
            return true;
        return false;
    }

    public boolean claimCost(SpellContext context) {
        if(context.worldIn.isRemote)
            return false;
        if(!isTruePlayer(context.playerIn))
            return false;
        if (claimInternal(context))
            return true;
        return false;
    }

    private boolean canClaimInternal(SpellContext context) {
        for (Cost cost: costs)
            if (!cost.canClaim(context))
                return false;
        return true;
    }

    private boolean claimInternal(SpellContext context) {
        for (Cost cost: costs)
            if (!cost.claim(context))
                return false;
        return true;
    }

    public String getUsageInfo(SpellContext context){
        if(costs.size() == 0)
            return "Unlimited use";
        if(costs.size() == 1)
            return costs.get(0).getUsageInfo(context);

        int useCount = Integer.MAX_VALUE;
        boolean hasInfinite = false;
        for (Cost cost: costs) {
            int costUseCount = cost.getUseCount(context);
            hasInfinite = hasInfinite || costUseCount == -1;
            costUseCount = Integer.max(costUseCount,0);
            useCount = Integer.min(useCount, costUseCount);
        }

        if(useCount > 0)
            return "Uses left: " + useCount;
        if(hasInfinite)
            return "Unlimited use";
        return "Unusable";

    }

    public static boolean isTruePlayer(Entity e) {
        if(!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }
}