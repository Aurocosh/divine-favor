package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.util.UtilCommon;
import net.darkhax.bookshelf.util.GameUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;
import java.util.regex.Pattern;

public abstract class ModSpell extends IForgeRegistryEntry.Impl<ModSpell> {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");
    protected static Random spellRand = new Random();

    protected String name;
    protected SpellType type;
    protected Side spellSide;

    public String getName() {
        return name;
    }

    public SpellType getType() {
        return type;
    }

    public boolean isWrongSpellSide(){
        return UtilCommon.getCodeSide() != spellSide;
    }

    public ModSpell(SpellType type, Side spellSide) {
        this.name = type.toString();
        this.type = type;
        this.spellSide = spellSide;
        setRegistryName(LibMisc.MOD_ID,"spell." + name);
    }

    public boolean cast(SpellContext context) {
        if(isWrongSpellSide())
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