package amweb.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import am.app.AppConfig;
import am.app.LoggingHandler;
import am.filesystem.model.Volume;

@Named
@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
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

  public void processApplicationScopedInit(@Observes @Initialized(ApplicationScoped.class) ServletContext payload)
  {
    config = new AppConfig();
    config.setLocale(Locale.ENGLISH);
    final LoggingHandler log = new LoggingHandler();
    config.setLoggingHandler(log);
    LOGGER.info("Initialization event. Server=" + payload.getServerInfo() + " Servlet API version="
        + payload.getMajorVersion() + "." + payload.getMinorVersion());
  }

  public List<Volume> getVolumes()
  {
    if (volumes.isEmpty())
    {
      final Volume volume = new Volume();
      volume.setPath("/test/path/faked");
      volume.setValidator("TvSeriesValidator");
      volumes.add(volume);
    }
    return volumes;
  }
}
