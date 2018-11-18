package aurocosh.divinefavor.api.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public final class DummyMethodHandler implements IInternalMethodHandler {

    @Override
    public IPlayerData getDataForPlayer(EntityPlayer player) {
        return new DummyPlayerData();
    }

    @Override
    public ResourceLocation getProgrammerTexture() {
        return new ResourceLocation("");
    }
    /*
    @Override
    public ISpellCompiler getCompiler(Spell spell) {
        return null;
    }

    @Override
    public ISpellCache getSpellCache() {
        return null;
    }

    @Override
    public void delayContext(SpellContext context) {
        // NO-OP
    }
    */
}