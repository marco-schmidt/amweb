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
  private static final int PAGE_INDEX_MAIN = 0;
  private static final int PAGE_INDEX_VOLUME = 1;
  private int pageIndex;
  private transient Volume volume;

  @PostConstruct
  public void initialize()
  {
    LOGGER.info("Initialized session state.");
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
    setPageIndex(PAGE_INDEX_VOLUME);
    LOGGER.info("Going to volume page.");
    return null;
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
}
