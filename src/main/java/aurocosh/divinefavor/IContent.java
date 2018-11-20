package aurocosh.divinefavor;

import aurocosh.divinefavor.common.config.IHasConfig;

public interface IContent extends IHasConfig {

  public void register();

  public boolean enabled();
}
