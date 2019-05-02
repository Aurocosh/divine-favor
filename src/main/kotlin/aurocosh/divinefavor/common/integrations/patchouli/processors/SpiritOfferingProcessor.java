package aurocosh.divinefavor.common.integrations.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

public class SpiritOfferingProcessor implements IComponentProcessor {
    int count;
    Item offering;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String spiritName = variables.get("spirit");
        ModSpirit spirit = ModMappers.spirits.get(new ResourceLocation(spiritName));
        if(spirit == null){
            DivineFavor.logger.error("Spirit not found:" + spiritName);
            return;
        }

        offering = spirit.getOffering();
        count = spirit.getOfferingCount();
    }

    @Override
    public String process(String key) {
        if (key.startsWith("offering") && offering != null) {
            ItemStack stack = new ItemStack(offering, count);
            return ItemStackUtil.serializeStack(stack);
        }
        else if (key.equals("text")) {
            return I18n.format("divinefavor:spirit_offering", count);
        }
        return null;
    }

}