package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.player_data.spell_count.ISpellUsesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;
import java.util.regex.Pattern;

import static aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler.CAPABILITY_SPELL_USES;

public abstract class ModSpell extends IForgeRegistryEntry.Impl<ModSpell> {
    protected static Random spellRand = new Random();

    public ModSpell(String name) {
        setRegistryName(ResourceNamer.getFullName("spell", name));
    }

    public boolean isConsumeCharge(SpellContext context) {
        return true;
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