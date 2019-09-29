package amweb.beans;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MainBean
{
  private String name;
  private Date time;

  public Date getTime()
  {
      return time;
  }

  public void setName(String v)
  {
      name = v;
  }

  public String getName()
  {
      return name;
  }

  public String actionOk()
  {
    time = new Date();
    return null;
  }
}
