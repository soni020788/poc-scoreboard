package localserver;

import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class StartTomcat {
    private static final int DEFAULT_PORT = 9090;
    private Tomcat tomcat;

    protected StartTomcat(int port) {
        tomcat = new Tomcat();
        tomcat.setPort(port);
    }

    public static void main(String[] args) {
        StartTomcat startTomcat = new StartTomcat(DEFAULT_PORT);
        startTomcat.start().await();
    }

    private Server start() {
        try {
            tomcat.setBaseDir(".");
            tomcat.getHost().setAppBase(".");
            tomcat.getServer().addLifecycleListener(new AprLifecycleListener());

            File configFile = new File("target");
            Context context = tomcat.addWebapp("/", configFile.getAbsolutePath());
            String webXmlPath = new File("src/main/webapp/WEB-INF/web.xml").getAbsolutePath();
            ContextConfig ctxCfg = new ContextConfig();
            ctxCfg.setDefaultWebXml(webXmlPath);
            context.addLifecycleListener(ctxCfg);
            tomcat.start();
            return tomcat.getServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
