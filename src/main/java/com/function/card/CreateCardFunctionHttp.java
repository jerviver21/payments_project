package com.function.card;

import com.function.card.model.CreateCardRequest;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.ServiceBusTopicOutput;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class CreateCardFunctionHttp {

    @FunctionName("createcard")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<CreateCardRequest>> request,
            @ServiceBusTopicOutput(
                name = "message", 
                topicName = "newcards", 
                subscriptionName = "subs1", 
                connection = "bus_cardcompany") 
                OutputBinding<CreateCardRequest> message,    
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        final CreateCardRequest cardRequest = request.getBody().orElse(null);

        if (cardRequest == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Wrong request").build();
        } 

        message.setValue(cardRequest);
        return request.createResponseBuilder(HttpStatus.OK).body("A request was sent and will be processed soon! ").build();
    }

}
