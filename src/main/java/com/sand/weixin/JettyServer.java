package com.sand.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyServer
{
  private Server server;
  WebAppContext context;

  public JettyServer()
    throws Exception
  {
    initialize();
  }

  protected void initialize() throws IOException, Exception
  {
    this.server = new Server();

    String confPath = getClass().getResource("/").getPath();
    System.out.println(confPath);
    File configFile = new File(confPath + "/jetty.xml");
    XmlConfiguration configuration = new XmlConfiguration(new FileInputStream(configFile));
    configuration.configure(this.server);

    String webPath = System.getProperty("user.dir") + "\\webapp";
    
    WebAppContext webAppContext = new WebAppContext(webPath, "/");
    //webAppContext.setContextPath("/pay");
    webAppContext.setContextPath("/");
    webAppContext.setConfigurationDiscovered(true);
    webAppContext.setParentLoaderPriority(true);
    this.server.setHandler(webAppContext);
  }

  public void start() throws Exception {
    if (this.server != null)
      this.server.start();
  }
}