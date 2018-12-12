package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.registry.interfaces.IVariant;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CallingStone extends IForgeRegistryEntry.Impl<CallingStone> implements IVariant {
    public final String name;
    public final ModSpirit spirit;
    public final ModMultiBlock multiBlock;

    public CallingStone(String name, ModSpirit spirit, ModMultiBlock multiBlock) {
        this.name = name;
        this.spirit = spirit;
        this.multiBlock = multiBlock;
    }

    @Override
    public String getName() {
        return name;
    }
}