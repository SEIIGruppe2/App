package com.example.munchkin.messageformat;

import com.example.munchkin.controller.BaseController;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageRouterTest {

    private MessageRouter messageRouter;
    @Mock
    private BaseController mockController;

    @BeforeEach
    protected void setup() {
        messageRouter = new MessageRouter();
        messageRouter.registerController("testType", mockController);
    }

    @Test
    void testRouteMessageToRegisteredController() throws Exception {
        String message = new JSONObject()
                .put("type", "testType")
                .put("data", "example data")
                .toString();

        MessageRouter.routeMessage(message);

        verify(mockController, times(1)).handleMessage(message);
    }

    @Test
    void testRouteMessageWithInvalidJson() {
        String message = "this is not a valid json";

        assertThrows(IllegalArgumentException.class, () -> MessageRouter.routeMessage(message));
    }
}