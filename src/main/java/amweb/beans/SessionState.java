/*
 * Copyright 2019, 2020, 2021, 2022 the original author or authors.
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import am.app.AppConfig;
import am.app.DatabaseService;
import am.filesystem.model.Directory;
import am.filesystem.model.File;
import am.filesystem.model.Volume;

@Named
@SessionScoped
public class SessionState implements Serializable, HttpSessionBindingListener
{
  private static final long serialVersionUID = 5907105291461155384L;
  private static final Logger LOGGER = LoggerFactory.getLogger(SessionState.class);
  private static final int PAGE_INDEX_MAIN = 0;
  private static final int PAGE_INDEX_VOLUME = 1;
  private int pageIndex;
  private transient Volume volume;
  private transient Directory dir;
  private transient List<Directory> directories = new ArrayList<>();

  @Inject
  private transient ExternalContext ec;
  @Inject
  @Named("appState")
  private transient AppState appState;

  @PostConstruct
  public void initialize()
  {
    final HttpServletRequest request = (HttpServletRequest) ec.getRequest();
    LOGGER.info("Initialized session with id=" + ec.getSessionId(false) + ", IP address=" + request.getRemoteAddr());
    ec.getSessionMap().put("sessionBindingListener", this);
    setPageIndex(PAGE_INDEX_MAIN);
  }

  public boolean isMainPageRendered()
  {
    return pageIndex == PAGE_INDEX_MAIN;
  }

  public boolean isVolumePageRendered()
  {
    return pageIndex == PAGE_INDEX_VOLUME;
  }

  public String actionNavigateToMain()
  {
    setVolume(null);
    setPageIndex(PAGE_INDEX_MAIN);
    LOGGER.info("Going to main.");
    return null;
  }

  public String actionNavigateToVolume(Volume vol)
  {
    setVolume(vol);
    setDirectory(vol.getRoot());
    if (directories == null)
    {
      directories = new ArrayList<Directory>();
    }
    else
    {
      directories.clear();
    }
    setPageIndex(PAGE_INDEX_VOLUME);
    LOGGER.info("Going to volume page.");
    return null;
  }

  public String actionSaveWikidata(File file)
  {
    LOGGER.info("Saving wikidata '" + file.getWikidataEntityId() + "' for file '" + file.getName() + "'.");
    final AppConfig config = appState.getConfig();
    new DatabaseService().updateWikidataEntityId(config, file);
    return null;
  }

  public String actionNavigateToBreadcrumbDirectory(Directory sub)
  {
    if (volume != null && volume.getRoot() != null && sub == volume.getRoot())
    {
      setDirectory(volume.getRoot());
      directories.clear();
      LOGGER.info("Going to root directory of " + volume.getPath() + " as breadcrumb directory.");
      return null;
    }
    int index = directories.size() - 1;
    while (index >= 0)
    {
      final Directory dir = directories.get(index);
      if (dir == sub)
      {
        setDirectory(sub);
        directories = directories.subList(0, index);
        break;
      }
      else
      {
        index--;
      }
    }
    LOGGER.info("Going to breadcrumb directory " + sub.getName());
    return null;
  }

  public String actionNavigateToSubDirectory(Directory sub)
  {
    LOGGER.info("Going to subdirectory page " + sub.getName());
    directories.add(sub);
    setDirectory(sub);
    return null;
  }

  private void writeObject(ObjectOutputStream stream) throws IOException
  {
    stream.defaultWriteObject();
  }

  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
  }

  public Volume getVolume()
  {
    return volume;
  }

  public void setVolume(Volume volume)
  {
    this.volume = volume;
  }

  public int getPageIndex()
  {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex)
  {
    this.pageIndex = pageIndex;
  }

  @Override
  public void valueBound(HttpSessionBindingEvent event)
  {
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event)
  {
    LOGGER.info("Session about to expire:" + event.getName() + " session=" + event.getSession().getId());
  }

  public Directory getDirectory()
  {
    return dir;
  }

  public void setDirectory(Directory dir)
  {
    this.dir = dir;
  }

  public List<Directory> getDirectories()
  {
    return directories;
  }

  public void setDirectories(List<Directory> directories)
  {
    this.directories = directories;
  }
}
