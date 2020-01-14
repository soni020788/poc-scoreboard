package scoreboard.api.di;

import com.google.inject.servlet.ServletModule;
import scoreboard.api.filter.SessionFilter;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(ServletContainer.class).asEagerSingleton();
        filter("/*").through(SessionFilter.class);
        serve("/rest/*").with(ServletContainer.class, jerseyApplicationInitParams());
    }

    private Map<String, String> jerseyApplicationInitParams() {
        Map<String, String> params = new HashMap<>();
        params.put("javax.ws.rs.Application", JerseyApplicationConfig.class.getCanonicalName());
        params.put("jersey.config.server.wadl.disableWadl", "true");
        return params;
    }
}
