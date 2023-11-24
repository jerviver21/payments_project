package com.function.card;

import java.sql.Connection;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.function.card.db.CreditCardDao;
import com.function.card.db.DaoManager;
import com.function.card.model.CreditCard;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetCardBalanceFunction {

    @FunctionName("GetCardBalance")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",
                        methods = {HttpMethod.GET}, 
                        authLevel = AuthorizationLevel.ANONYMOUS) 
                HttpRequestMessage<Optional<String>> request,
                final ExecutionContext context) {

        context.getLogger().info("GetCardBalance ...");

        String dbConnectionString = System.getenv("db_cardcompany");

        Connection conn =  DaoManager.createConnection(dbConnectionString);
        CreditCardDao cardDao = new CreditCardDao();

        // Parse query parameter
        String cardNumber = request.getQueryParameters().get("card_number");

        if (cardNumber == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Wrong request, card_number should be provided").build();
        }

        CreditCard card = cardDao.findCredirtCard(cardNumber, conn);

        if (card == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Card doesn't exist!").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).body(card).build();
    }
}
