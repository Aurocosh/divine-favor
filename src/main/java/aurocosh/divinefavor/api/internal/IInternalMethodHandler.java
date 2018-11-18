package aurocosh.divinefavor.api.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface IInternalMethodHandler {

    /**
     * Gets the player data for a given player. Player Data contains info such as the
     * player's Psi value or level.
     */
    public IPlayerData getDataForPlayer(EntityPlayer player);

    /**
     * Gets the texture for the programmer. Used for drawing the arrows in a SpellPiece's
     * params.
     */
    public ResourceLocation getProgrammerTexture();

    /**
     * Gets an instance of a spell compiler. In most cases, you should use {@link #getSpellCache()} instead.
     */
    //public ISpellCompiler getCompiler(Spell spell);

    /**
     * Gets the singleton instance of the spell cache.
     */
    //public ISpellCache getSpellCache();

    /**
     * Delays a spell context.
     */
    //public void delayContext(SpellContext context);
}