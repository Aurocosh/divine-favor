package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.core.handlers.ConfigHandler;
import aurocosh.divinefavor.common.constants.LibMisc;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiDivineFavorConfig extends GuiConfig {
    public GuiDivineFavorConfig(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), LibMisc.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
    }
}
