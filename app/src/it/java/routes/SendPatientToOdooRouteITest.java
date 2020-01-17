package routes;

import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openmrs.sync.app.config.TestConfig;
import org.openmrs.sync.component.camel.OpenmrsComponent;
import org.openmrs.sync.component.camel.StringToLocalDateTimeConverter;
import org.openmrs.sync.component.entity.light.UserLight;
import org.openmrs.sync.component.model.PatientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Skeleton of a test class to use to test a route file.
 * If you need to mock a call to Odoo, use MockServer: https://www.baeldung.com/mockserver
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class SendPatientToOdooRouteITest {

    @Autowired
    protected CamelContext camelContext;

    @Autowired
    private ApplicationContext applicationContext;

    @EndpointInject(uri = "mock:direct:odoo-route")
    protected MockEndpoint resultEndpoint;

    @Produce(property = "uri")
    protected ProducerTemplate template;

    public String getUri() {
        return "direct:patient-to-odoo";
    }

    @Before
    public void startServer() throws Exception {
        InputStream is = new FileInputStream(Paths.get(System.getProperty("user.dir")).getParent() + "/sample/sample_springboot_setup/sender/routes-odoo/patient-to-odoo-route.xml");
        RoutesDefinition routes = camelContext.loadRoutesDefinition(is);

        routes.getRoutes().get(0).adviceWith(camelContext,
                new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        // Use this code to mock all dependencies in the route like beans... Could not make it work yet
                        //weaveByType();
                    }
                });

        camelContext.addRouteDefinitions(routes.getRoutes());

        camelContext.addComponent("openmrs", new OpenmrsComponent(camelContext, applicationContext));
        camelContext.getTypeConverterRegistry().addTypeConverter(LocalDateTime.class, String.class, new StringToLocalDateTimeConverter());
    }

    @After
    public void teardown() {
        camelContext.removeComponent("openmrs");
        resultEndpoint.getExchanges().clear();
    }

    @Test
    public void testRoute() {
        // Given

        // When
        //template.sendBody(getPersonJson());

        // Then
        List<Exchange> result = resultEndpoint.getExchanges();
        // Test result returned by the route
    }

    private String getPersonJson() {
        return "{" +
                    "\"tableToSyncModelClass\":\"" + PatientModel.class.getName() + "\"," +
                    "\"model\":{" +
                        "\"uuid\":\"818b4ee6-8d68-4849-975d-80ab98016677\"," +
                        "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\"," +
                        "\"dateCreated\":[2019,5,28,13,42,31]," +
                        "\"changedByUuid\":null," +
                        "\"dateChanged\":null," +
                        "\"voided\":false," +
                        "\"voidedByUuid\":null," +
                        "\"dateVoided\":null," +
                        "\"voidReason\":null," +
                        "\"gender\":\"F\"," +
                        "\"birthdate\":\"1982-01-06\"," +
                        "\"birthdateEstimated\":false," +
                        "\"dead\":false," +
                        "\"deathDate\":null," +
                        "\"causeOfDeathUuid\":null," +
                        "\"deathdateEstimated\":false," +
                        "\"birthtime\":null" +
                    "}" +
                "}";
    }
}
