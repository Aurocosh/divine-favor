package aurocosh.divinefavor.common.integrations.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilDayTime;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;

public class SpiritInfoProcessor implements IComponentProcessor {
    private String text;
    private ModSpirit spirit;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String name = variables.get("spirit");
        ResourceLocation spiritId = new ResourceLocation(name);
        this.spirit = ModRegistries.INSTANCE.getSpirits().getValue(spiritId);
        if (this.spirit == null)
            DivineFavor.logger.error("Spirit not found:" + name);

        text = variables.get("text");
    }

    @Override
    public String process(String key) {
        if (key.equals("text")) {
            String result = text;
            if (spirit != null) {
                TimePeriod activityPeriod = spirit.getActivityPeriod();
                int start = activityPeriod.getStart() / UtilDayTime.INSTANCE.getTICKS_IN_HOUR();
                int stop = activityPeriod.getStop() / UtilDayTime.INSTANCE.getTICKS_IN_HOUR();
                result += String.format("$(br)This spirit is active from %d o'clock to %d o'clock.", start, stop);
            }
            return result;
        }
        return null;
    }

}