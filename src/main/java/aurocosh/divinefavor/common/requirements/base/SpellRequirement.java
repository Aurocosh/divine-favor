package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.lib.IInitiatable;
import aurocosh.divinefavor.common.requirements.cost.containers.CostUnit;
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
    private List<CostUnit> costUnits;

    public List<CostUnit> getCostUnits() {
        return Collections.unmodifiableList(costUnits);
    }

    @Override
    public void init() {
        Collections.sort(this.costUnits,new CostUnit.CostUnitComparator());
        costUnits.forEach(CostUnit::init);
    }

    public SpellRequirement()
    {
        CostUnit costUnit = new CostUnit(0, new CostFree());
        this.costUnits = Arrays.asList(costUnit);
    }

    public SpellRequirement(ArrayList<CostUnit> costUnits)
    {
        this.costUnits = costUnits;
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
        for (CostUnit costUnit: costUnits)
            if (costUnit.canClaimAllCosts(context))
                return true;
        return false;
    }

    private boolean claimInternal(SpellContext context) {
        for (CostUnit costUnit: costUnits)
            if (costUnit.claimAllCosts(context))
                return true;
        return false;
    }

    public String getUsageInfo(SpellContext context){
        if(costUnits.size() == 0)
            return "Unlimited use";
        CostUnit costUnit = costUnits.get(0);
        if(!costUnit.canClaimAllCosts(context))
            return "Unusable";
        return costUnit.getUsageInfo(context);
    }

    public static boolean isTruePlayer(Entity e) {
        if(!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }
}