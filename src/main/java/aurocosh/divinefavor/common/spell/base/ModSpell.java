package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;
import java.util.regex.Pattern;

public abstract class ModSpell extends IForgeRegistryEntry.Impl<ModSpell> {
    protected static Random spellRand = new Random();

    public ModSpell(String name) {
        setRegistryName(ResourceNamer.getFullName("spell", name));
    }

    public void cast(SpellContext context) {
        if (context.world.isRemote)
            performActionClient(context);
        else
            performActionServer(context);
    }

    protected void performActionServer(SpellContext context) {
    }

    protected void performActionClient(SpellContext context) {
    }
}