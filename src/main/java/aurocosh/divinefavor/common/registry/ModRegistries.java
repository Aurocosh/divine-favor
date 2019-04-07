package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.item.ItemBlock;

public class ModRegistries {
    public static RegistryAssistant<ItemBlock> itemBlocks = new RegistryAssistant<>();
    public static RegistryAssistant<ModBlock> blocks = new RegistryAssistant<>();
    public static RegistryAssistant<ModItem> items = new RegistryAssistant<>();
    public static RegistryAssistant<ModMultiBlock> multiBlocks = new RegistryAssistant<>();
    public static RegistryAssistant<ModPotion> potions = new RegistryAssistant<>();
    public static RegistryAssistant<ModSpirit> spirits = new RegistryAssistant<>();
}
