package de.ykoer.examples.hello;

import de.ykoer.examples.hello.controller.HelloWorldController;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HelloWorldController unit tests
 *
 * @author ykoer
 *
 */

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceInfoControllerTest {

    @Autowired
    private HelloWorldController serviceInfoController;

    @Test
    public void testPing() throws IOException {
        String response = serviceInfoController.ping();
        assertThat(response).isEqualTo("Hello World!");
    }
}

