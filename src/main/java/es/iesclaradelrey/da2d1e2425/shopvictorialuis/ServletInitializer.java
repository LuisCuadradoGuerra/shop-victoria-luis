package es.iesclaradelrey.da2d1e2425.shopvictorialuis;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    /**
     * Se llama automáticamente cuando la aplicación se despliega en un servidor externo.
     * Aquí se especifica la clase principal que arranca la aplicación (en este caso, Application.class).
     *
     * @param application el constructor de la aplicación Spring
     * @return el mismo constructor, indicando qué clase contiene el main() de arranque
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

}
