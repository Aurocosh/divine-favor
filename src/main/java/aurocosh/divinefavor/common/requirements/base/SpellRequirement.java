package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.regex.Pattern;

public class SpellRequirement {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    protected int favorType;
    protected boolean isFreeInNether;
    public String name;

    public SpellRequirement(String name, int favorType, boolean isFreeInNether)
    {
        this.name = name;
        this.favorType = favorType;
        this.isFreeInNether = isFreeInNether;
    }

    public int getFavorType(){
        return favorType;
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

    protected boolean claim(SpellContext context)
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.playerIn);
        return data.consumeSpellCharge(favorType,1);
    }

    public String toString()
    {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
        return " current_charge: " + data.getSpellCharge(favorType);
    }
}