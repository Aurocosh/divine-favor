package aurocosh.divinefavor.common.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;

public class ContractStatProcessor implements IComponentProcessor {
    private String text;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String contractName = variables.get("contract");
        Item item = Item.REGISTRY.getObject(new ResourceLocation(contractName));
        if (item instanceof ItemContract) {
            ItemContract contract = (ItemContract) item;
            text = I18n.format("item.divinefavor:contract.effects") + "$(br)";

            String effects = "";
            int regen = contract.getRegen();
            if (regen != 0)
                effects += "$(li)" + I18n.format("item.divinefavor:contract.regen", regen) + "$(br)";
            int min = contract.getMin();
            if (min != 0)
                effects += "$(li)" + I18n.format("item.divinefavor:contract.min", min) + "$(br)";
            int max = contract.getMax();
            if (max != 0)
                effects += "$(li)" + I18n.format("item.divinefavor:contract.max", max) + "$(br)";
            if (effects.isEmpty())
                effects += "$(li)" + I18n.format("item.divinefavor:contract.no_effects") + "$(br)";
            text += effects;
        }
        else
            DivineFavor.logger.error("Contract not found:" + contractName);
    }

    @Override
    public String process(String key) {
        if (key.equals("text"))
            return text;
        return null;
    }
}