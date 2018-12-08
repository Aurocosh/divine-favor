package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;
import java.util.regex.Pattern;

public abstract class ModSpell extends IForgeRegistryEntry.Impl<ModSpell> {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");
    protected static Random spellRand = new Random();

    protected String name;
    protected boolean isServerSide;

    public String getName() {
        return name;
    }

    public boolean isCodeSideCorrect(World world){
        return (!world.isRemote) == isServerSide;
    }

    public ModSpell(SpellType type, boolean isServerSide) {
        this.name = type.toString();
        this.isServerSide = isServerSide;

        setRegistryName(ResourceNamer.getFullName("spell",name));
    }

    public boolean cast(SpellContext context) {
        if(!isCodeSideCorrect(context.worldIn))
            return true;
        if(!isTruePlayer(context.playerIn))
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
}