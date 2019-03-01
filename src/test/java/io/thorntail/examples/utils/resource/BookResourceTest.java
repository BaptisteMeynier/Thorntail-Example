package io.thorntail.examples.utils.resource;


import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.thorntail.examples.book.store.model.dto.BookDto;
import io.thorntail.examples.book.store.rs.param.SortParam;
import io.thorntail.examples.book.store.rs.param.converter.SortParamConverter;
import io.thorntail.examples.book.store.rs.param.converter.SortParamConverterProvider;
import io.thorntail.examples.book.store.rs.param.utils.NumberedPage;
import io.thorntail.examples.utils.PlayCli;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.staxmapper.XMLElementReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;
import java.util.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RunWith(Arquillian.class)
@ServerSetup({BookResourceTest.BookStoreSetup.class})
public class BookResourceTest {

    @ArquillianResource
    private URI baseURL;


    private Client client;
    private WebTarget artifactTarget;

    @Deployment
    public static WebArchive createDeployment() {
        File[] compileAndRuntime = Maven.resolver().loadPomFromFile("pom.xml")
                .importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "io.thorntail.examples.book.store")
                .addClass(XMLElementReader.class)
                .addClass(ModelNode.class)
                .addClass(ServerSetupTask.class)
                .addClass(SortParamConverterProvider.class)
                .addClass(SortParam.class)
                .addClass(SortParamConverter.class)
                .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("WEB-INF/jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
                .addAsResource("sql/insert.sql", "insert.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(compileAndRuntime);
    }

    static class CockpitSetup extends PlayCli {
        @Override
        public void setup(ManagementClient managementClient, String containerId) throws Exception {
            this.cliCommands = Arrays.asList(
                    "/subsystem=naming/binding=java\\:global\\/book\\/X-Max-Per-Page:add(binding-type=simple, type=int, value=10)",
                    "/subsystem=naming/binding=java\\:global\\/book\\/X-Default-Per-Page:add(binding-type=simple, type=int, value=5)",
                    "/subsystem=naming/binding=java\\:global\\/db\\/max-entries:add(binding-type=simple, type=int, value=100)"
            );
            this.initWildfly(managementClient);
        }
    }

    @Before
    public void initWebTarget() {
        client = ClientBuilder.newClient();
        artifactTarget = client.target(baseURL).path("rest").path("v1").path("book");
    }

    @Test
    public void should1() {
        final Response response = artifactTarget.request(APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void should() {
        final Response response = artifactTarget
                .register(JacksonJsonProvider.class)
                .request(APPLICATION_JSON)
                .get();
        final NumberedPage<BookDto> bookDtoNumberedPage =
                response.readEntity(
                        new GenericType<NumberedPage<BookDto>>() {
                        });
        final Collection<BookDto> entities = bookDtoNumberedPage.getEntities();
        Assert.assertTrue(entities.size() > 0);
    }


}