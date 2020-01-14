package scoreboard.api.di;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

public class PropertiesModule extends AbstractModule {
    private final String fileName;

    PropertiesModule(String fileName) {
        this.fileName = fileName;
    }

    protected void configure() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load properties file: " + fileName);
        }
        bind(Properties.class).toInstance(properties);
        Names.bindProperties(binder(), properties);
    }
}
