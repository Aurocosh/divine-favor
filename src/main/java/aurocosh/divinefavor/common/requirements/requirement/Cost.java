package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.common.lib.IInitiatable;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Comparator;
import java.util.regex.Pattern;

public abstract class Cost implements IInitiatable {
    @Override
    public void init() {}

    public static class CostComparator implements  Comparator<Cost> {
        @Override
        public int compare(Cost o1, Cost o2) {
            if(o1.getPriority() == o2.getPriority())
                return 0;
            return o1.getPriority() > o2.getPriority() ? -1 : 1;
        }
    }

    @Expose
    private int priority;

    public int getPriority() {
        return priority;
    }

    public Cost(int priority)
    {
        this.priority = priority;
    }

    public abstract boolean canClaim(SpellContext context);
    public abstract boolean claim(SpellContext context);
    public abstract String getUsageInfo(SpellContext context);
}