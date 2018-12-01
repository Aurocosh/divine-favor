package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.constants.LibMisc;
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
    protected SpellType type;
    protected boolean isServerSide;

    public String getName() {
        return name;
    }

    public SpellType getType() {
        return type;
    }

    public boolean isCodeSideCorrect(World world){
        return (!world.isRemote) == isServerSide;
    }

    public ModSpell(SpellType type, boolean isServerSide) {
        this.name = type.toString();
        this.type = type;
        this.isServerSide = isServerSide;
        setRegistryName(LibMisc.MOD_ID,"spell." + name);
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