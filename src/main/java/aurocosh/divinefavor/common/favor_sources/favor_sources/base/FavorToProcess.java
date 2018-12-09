package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import net.minecraft.entity.player.EntityPlayer;

public class FavorToProcess {
    public final EntityPlayer player;
    public final FavorSource favorSource;

    public FavorToProcess(EntityPlayer player, FavorSource favorSource) {
        this.player = player;
        this.favorSource = favorSource;
    }
}
