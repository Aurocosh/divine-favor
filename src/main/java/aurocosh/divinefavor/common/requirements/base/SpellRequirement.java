package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.requirements.requirement.Cost;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.annotations.Expose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.regex.Pattern;

public class SpellRequirement {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    @Expose
    public String name;
    @Expose
    public Cost cost;

    public SpellRequirement(String name, Cost cost)
    {
        this.name = name;
        this.cost = cost;
    }

    public boolean canClaimCost(SpellContext context) {
        if(!isTruePlayer(context.playerIn))
            return false;
        return cost.canClaim(context);
    }

    public boolean claimCost(SpellContext context) {
        if(context.worldIn.isRemote)
            return false;
        if(!isTruePlayer(context.playerIn))
            return false;
        return cost.claim(context);
    }

    public String getUsageInfo(SpellContext context){
        if(!cost.canClaim(context))
            return "Unusable";
        return cost.getUsageInfo(context);
    }

    public static boolean isTruePlayer(Entity e) {
        if(!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }
}