package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.spell.base.SpellChargeType;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Random;
import java.util.regex.Pattern;

public abstract class SpellRequirement {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    protected SpellChargeType chargeType;
    protected int charge;
    protected boolean isFreeInNether;
    public String name;

    public SpellRequirement(String name, SpellChargeType chargeType, boolean isFreeInNether)
    {
        this.name = name;
        this.chargeType = chargeType;
        this.charge = 1;
        this.isFreeInNether = isFreeInNether;
    }

    public SpellChargeType getChargeType(){
        return chargeType;
    }

    public boolean claimCost(SpellContext context) {
        if(context.worldIn.isRemote)
            return false;
        if(!isTruePlayer(context.playerIn))
            return false;
        return claim(context);
    }

    public static boolean isTruePlayer(Entity e) {
        if(!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }

    protected abstract boolean claim(SpellContext context);

    public String toString()
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
        return chargeType.toString() + ": " + charge + " current_charge: " + data.getSpellCharge(chargeType);
    }
}