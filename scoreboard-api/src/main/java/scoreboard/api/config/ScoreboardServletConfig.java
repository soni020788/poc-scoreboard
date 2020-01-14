package scoreboard.api.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.mycila.guice.ext.closeable.CloseableInjector;
import scoreboard.api.di.ScoreboardContext;

public class ScoreboardServletConfig extends GuiceServletContextListener {
    private final CloseableInjector injector;
    private static ScoreboardServletConfig instance;

    public ScoreboardServletConfig() {
        this.injector = createInjector();
        instance = this;
    }

    private CloseableInjector createInjector() {
        return Guice.createInjector(ScoreboardContext.DEVELOPMENT.createModules()).getInstance(CloseableInjector.class);
    }

    private static synchronized ScoreboardServletConfig getInstance() {
        return null == instance ? new ScoreboardServletConfig() : instance;
    }

    public static Injector injectorInstance() {
        return getInstance().getInjector();
    }

    protected Injector getInjector() {
        return injector;
    }
}
