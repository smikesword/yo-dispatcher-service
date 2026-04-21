package yo.com.yo_dispatcher_service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class FunctionsStreamIntegrationTests {
    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void whenOrderAcceptedThenDispatched() throws IOException {
        long orderId = 121;
        Message<OrderAcceptedMessage> inputMessage = MessageBuilder
                .withPayload(new OrderAcceptedMessage(orderId)).build();
        Message<OrderDispatchedMessage> expectedOutputMessage = MessageBuilder
                .withPayload(new OrderDispatchedMessage(orderId)).build();

        this.input.send(inputMessage);
        assertThat(objectMapper.readValue(output.receive().getPayload(),
                OrderDispatchedMessage.class))
                .isEqualTo(expectedOutputMessage.getPayload());
    }

}
