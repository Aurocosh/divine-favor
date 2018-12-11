package aurocosh.divinefavor.common.talismans;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class Talisman extends IForgeRegistryEntry.Impl<Talisman> {
    public final String name;
    private final ModSpell spell;
    private final ModFavor favor;
    private final int favorPerUse;
    public final boolean castOnUse;
    public final boolean castOnRightClick;

    public Talisman(String name, ModSpell spell, ModFavor favor, int favorPerUse, boolean castOnUse, boolean castOnRightClick) {
        this.name = name;
        this.spell = spell;
        this.favor = favor;
        this.favorPerUse = favorPerUse;
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;
    }

    public boolean cast(SpellContext context) {
        if(!claimCost(context))
            return false;
        return spell.cast(context);
    }

    private boolean claimCost(SpellContext context) {
        if (context.world.isRemote)
            return false;
        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        if(!favorHandler.consumeFavor(favor.id,favorPerUse))
            return false;

        int favorValue = favorHandler.getFavor(favor.id);
        MessageSyncFavor message = new MessageSyncFavor(favor.id,favorValue);
        NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) context.player);
        return true;
    }

    public int getUseCount(EntityPlayer player){
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        int favorCount = favorHandler.getFavor(favor.id);
        if(favorCount == 0)
            return 0;

        return favorCount / favorPerUse;
    }
}