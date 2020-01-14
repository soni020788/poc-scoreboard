package scoreboard.api.di;

import com.google.inject.Module;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ScoreboardContext {
    DEVELOPMENT {
        @Override
        public List<Module> createModules() {
            List<Module> modules = new ArrayList<>(getCommonModules());
            modules.add(new ServiceModule());
            return modules;
        }
    };

    static List<Module> getCommonModules() {
        return Arrays.asList(new CloseableModule(), new Jsr250Module(), new PropertiesModule("development.properties"), new ScoreboardServletModule());
    }

    public abstract List<Module> createModules();
}
