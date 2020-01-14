package scoreboard.api.di;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.inject.Injector;
import scoreboard.api.config.ScoreboardServletConfig;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;
import org.zapodot.jackson.java8.JavaOptionalModule;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import java.util.Optional;

public class JerseyApplicationConfig extends ResourceConfig {
    @Inject
    public JerseyApplicationConfig(@Context ServletContext servletContext, ServiceLocator serviceLocator) {
        configureBidirectionalHk2GuiceBridge(serviceLocator);
        packages("scoreboard.api.resource");
        register(new JacksonBinder());
        register(JacksonBinder.createJacksonJaxbJsonProvider());
        register(OptionalEmptyToNoContent.class);
    }

    private void configureBidirectionalHk2GuiceBridge(final ServiceLocator serviceLocator) {
        Injector injector = ScoreboardServletConfig.injectorInstance().createChildInjector(new HK2IntoGuiceBridge(serviceLocator));
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(injector);
    }

    static class JacksonBinder extends AbstractBinder {
        protected void configure() {
            bindFactory(new Factory<ObjectMapper>() {
                public ObjectMapper provide() {
                    return createObjectMapper();
                }

                public void dispose(ObjectMapper objectMapper) {
                }
            }).to(ObjectMapper.class);
        }

        static JacksonJaxbJsonProvider createJacksonJaxbJsonProvider() {
            JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
            jsonProvider.setMapper(createObjectMapper());
            return jsonProvider;
        }

        private static ObjectMapper createObjectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new ParameterNamesModule());
            mapper.registerModule(new JavaOptionalModule());
            return mapper;
        }
    }

    static class OptionalEmptyToNoContent implements ContainerResponseFilter {
        public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
            Object entity = containerResponseContext.getEntity();
            if (entity instanceof Optional<?> && !((Optional) entity).isPresent()) {
                containerResponseContext.setStatus(HttpServletResponse.SC_NO_CONTENT);
                containerResponseContext.setEntity(null);
            }
        }
    }
}