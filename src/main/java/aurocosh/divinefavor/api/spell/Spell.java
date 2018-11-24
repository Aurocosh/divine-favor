package aurocosh.divinefavor.api.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Random;
import java.util.regex.Pattern;

public abstract class Spell {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");
    protected static Random spellRand = new Random();

    public String name;

    public Spell(String name) {
        this.name = name;
    }

    public boolean cast(SpellContext context) {
        if(context.worldIn.isRemote)
            return true;
        if(!isTruePlayer(context.playerIn))
            return false;
        if(!claimCost(context))
            return false;

        performAction(context);
        return true;
    }

    public static boolean isTruePlayer(Entity e) {
        if(!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }

    protected abstract boolean performAction(SpellContext context);
    protected abstract boolean claimCost(SpellContext context);
}