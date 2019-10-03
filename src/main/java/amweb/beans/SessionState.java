package amweb.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import am.filesystem.model.Volume;

@Named
@SessionScoped
public class SessionState implements Serializable
{
  private static final long serialVersionUID = 5907105291461155384L;
  private static final Logger LOGGER = LoggerFactory.getLogger(SessionState.class);
  private Volume volume;

  @PostConstruct
  public void initialize()
  {
    LOGGER.info("Initialized session state.");
  }

  public String actionNavigateToMain()
  {
    setVolume(null);
    final String dest = "index.xhtml";
    LOGGER.info("Going to " + dest);
    return dest;
  }

  public String actionNavigateToVolume(Volume vol)
  {
    setVolume(vol);
    final String dest = "volume.xhtml";
    LOGGER.info("Going to " + dest);
    return dest;
  }

  public Volume getVolume()
  {
    return volume;
  }

  public void setVolume(Volume volume)
  {
    this.volume = volume;
  }
}
