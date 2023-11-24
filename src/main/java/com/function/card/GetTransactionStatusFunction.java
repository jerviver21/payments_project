package com.function.card;

import java.sql.Connection;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.function.card.db.DaoManager;
import com.function.card.db.TransactionDao;
import com.function.card.model.CardTransaction;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetTransactionStatusFunction {
    /**
     * This function listens at endpoint "/api/GetTransactionStatusFunction". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/GetTransactionStatusFunction
     * 2. curl {your host}/api/GetTransactionStatusFunction?name=HTTP%20Query
     */
    @FunctionName("GetTransactionStatus")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", 
                        methods = {HttpMethod.GET}, 
                        authLevel = AuthorizationLevel.ANONYMOUS) 
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("GetTransactionStatus  ...");

        String dbConnectionString = System.getenv("db_cardcompany");

        Connection conn =  DaoManager.createConnection(dbConnectionString);
        TransactionDao transactionDao = new TransactionDao();

        // Parse query parameter
        String tid = request.getQueryParameters().get("tid");

        if (tid == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Wrong request, tid should be provided").build();
        }

        CardTransaction transaction = transactionDao.findTransaction(tid, conn);

        if (transaction == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Transaction doesn't exist!").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).body(transaction).build();
    }
}
