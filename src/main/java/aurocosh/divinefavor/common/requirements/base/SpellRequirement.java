package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.registry.SpellRequirementRegestry;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.regex.Pattern;

public abstract class SpellRequirement extends IForgeRegistryEntry.Impl<SpellRequirement> {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    public String name;

    public SpellRequirement(String name)
    {
        this.name = name;
        setRegistryName(LibMisc.MOD_ID,"requirement." + name);
    }

    public boolean canClaimCost(SpellContext context) {
        if(context.worldIn.isRemote)
            return false;
        if(!isTruePlayer(context.playerIn))
            return false;
        return canClaimCostInternal(context);
    }

    public boolean claimCost(SpellContext context) {
        if(context.worldIn.isRemote)
            return false;
        if(!isTruePlayer(context.playerIn))
            return false;
        return claimCostInternal(context);
    }

    public abstract boolean canClaimCostInternal(SpellContext context);
    public abstract boolean claimCostInternal(SpellContext context);

    public static boolean isTruePlayer(Entity e) {
        if(!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }

    public String toString()
    {
        return "Free";
    }

    public static SpellRequirement deserialize(JsonObject json)
    {
        String type = JsonUtils.getString(json,"type");
        if(type.equals("free"))
            return SpellRequirementFree.deserialize(json);
        else if(type.equals("notFree"))
            return SpellRequirementNotFree.deserialize(json);
        return null;
    }
}