/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import am.app.AppConfigLoader;
import am.app.LoggingHandler;
import am.db.JdbcSerialization;
import am.filesystem.model.Volume;

@Named
@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class AppState
{
  private static final Logger LOGGER = LoggerFactory.getLogger(AppState.class);
  private AppConfig config;
  private List<Volume> volumes = new ArrayList<Volume>();

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
    log.initialize(config);
    try
    {
      Class.forName("org.sqlite.JDBC");
    }
    catch (final ClassNotFoundException e)
    {
      LOGGER.error("Unable to access JDBC driver.", e);
    }
    AppConfigLoader.loadConfig(config);
    AppConfigLoader.interpretProperties(config);
    LOGGER.info("Initialization event. Server=" + payload.getServerInfo() + ". Servlet API version="
        + payload.getMajorVersion() + "." + payload.getMinorVersion());
    final JdbcSerialization io = config.getDatabaseSerializer();
    volumes = io.loadAll();
  }

  public List<Volume> getVolumes()
  {
    if (volumes.isEmpty())
    {
      Volume volume = new Volume();
      volume.setPath("/test/path/faked");
      volume.setValidator("TvSeriesValidator");
      volumes.add(volume);
      volume = new Volume();
      volume.setPath("/another/path");
      volume.setValidator("MovieValidator");
      volumes.add(volume);
    }
    return volumes;
  }
}
