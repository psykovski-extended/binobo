package htlstp.diplomarbeit.binobo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootTest
class BinoboApplicationTests {

    @Test
    void contextLoads() throws IOException, NullPointerException {
        String file = new ClassPathResource("pythonScripts/websocketServer.py").getFile().getPath();
        System.out.println(file);
    }

}
