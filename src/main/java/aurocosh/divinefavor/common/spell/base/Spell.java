package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;
import java.util.regex.Pattern;

public abstract class Spell extends IForgeRegistryEntry.Impl<Spell> {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");
    protected static Random spellRand = new Random();

    public String name;
    public SpellType type;

    public Spell(SpellType type) {
        this.name = type.toString();
        this.type = type;
        setRegistryName(LibMisc.MOD_ID,"spell." + name);
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