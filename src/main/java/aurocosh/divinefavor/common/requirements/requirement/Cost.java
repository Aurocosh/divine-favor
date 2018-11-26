package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.util.FakePlayer;

import java.util.regex.Pattern;

public abstract class Cost {
    public int priority;

    public Cost(int priority)
    {
        this.priority = priority;
    }

    public abstract boolean canClaim(SpellContext context);
    public abstract boolean claim(SpellContext context);

    public abstract String toString();

    public static Cost deserialize(JsonObject json)
    {
        String type = JsonUtils.getString(json,"type");
        if(type.equals("favor"))
            return CostFavor.deserialize(json);
        else if(type.equals("multiple"))
            return CostMultipleOptions.deserialize(json);
        return null;
    }
}