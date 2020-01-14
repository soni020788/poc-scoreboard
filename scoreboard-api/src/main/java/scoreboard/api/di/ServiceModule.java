package scoreboard.api.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import scoreboard.api.db.ApiDataSource;
import scoreboard.api.service.ScoreboardService;

public class ServiceModule extends AbstractModule {
    protected void configure() {
        bind(ScoreboardService.class).in(Scopes.SINGLETON);
        bind(ApiDataSource.class).in(Scopes.SINGLETON);
    }
}
