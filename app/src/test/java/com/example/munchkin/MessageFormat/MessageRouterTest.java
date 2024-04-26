package com.example.munchkin.MessageFormat;

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
public class MessageRouterTest {


    private MessageRouter messageRouter;
    @Mock
    private BaseController mockController;



    @BeforeEach
    public void setup() {
        messageRouter = new MessageRouter();
        // Register the mock controller with a specific message type
        messageRouter.registerController("testType", mockController);
    }



    @Test
    public void testRouteMessageToRegisteredController() throws Exception {
        // Create a JSON message with the type "testType"
        String message = new JSONObject()
                .put("type", "testType")
                .put("data", "example data")
                .toString();

        // Route the message
        MessageRouter.routeMessage(message);


        // Verify that the correct controller method was called with the message
        verify(mockController, times(1)).handleMessage(message);
    }



    @Test
    public void testRouteMessageWithInvalidJson() {
        // Simulate an invalid JSON message
        String message = "this is not a valid json";


        // This should throw IllegalArgumentException because of invalid JSON
        assertThrows(IllegalArgumentException.class, () -> MessageRouter.routeMessage(message));
    }
}




