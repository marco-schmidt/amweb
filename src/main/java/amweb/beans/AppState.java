package amweb.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import am.app.AppConfig;
import am.app.LoggingHandler;
import am.filesystem.model.Volume;

@Named
@ApplicationScoped
@ManagedBean(eager = true)
public class AppState
{
  private static final Logger LOGGER = LoggerFactory.getLogger(AppState.class);
  private AppConfig config;
  private final List<Volume> volumes = new ArrayList<Volume>();

  public AppConfig getConfig()
  {
    return config;
  }

  public void setConfig(AppConfig config)
  {
    this.config = config;
  }

  @PostConstruct
  public void initialize()
  {
    config = new AppConfig();
    config.setLocale(Locale.ENGLISH);
    final LoggingHandler log = new LoggingHandler();
    config.setLoggingHandler(log);
    LOGGER.info("Initialized logging.");
  }

  public List<Volume> getVolumes()
  {
    if (volumes.isEmpty())
    {
      final Volume volume = new Volume();
      volume.setPath("/test/path/faked");
      volumes.add(volume);
    }
    return volumes;
  }
}
