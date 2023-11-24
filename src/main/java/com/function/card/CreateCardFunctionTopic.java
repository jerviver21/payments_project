package com.function.card;

import com.microsoft.azure.functions.annotation.*;

import java.sql.Connection;
import java.util.logging.Logger;

import com.function.card.db.ClientDao;
import com.function.card.db.CreditCardDao;
import com.function.card.db.DaoManager;
import com.function.card.model.Client;
import com.function.card.model.CreateCardRequest;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Topic Trigger.
 */
public class CreateCardFunctionTopic {
    /**
     * This function will be invoked when a new message is received at the Service Bus Topic.
     */
    @FunctionName("createcard_topic")
    public void run(
        @ServiceBusTopicTrigger(
            name = "message",
            topicName = "newcards",
            subscriptionName = "subs1",
            connection = "bus_cardcompany"
        )
        CreateCardRequest message,
        final ExecutionContext context
    ) {
        Logger log = context.getLogger();
        log.info("Java Service Bus Topic (create card) trigger function executed.");

        String dbConnectionString = System.getenv("db_cardcompany");

        Connection conn =  DaoManager.createConnection(dbConnectionString);
        ClientDao clientDao = new ClientDao();
        CreditCardDao cardDao = new CreditCardDao();
        Client client = clientDao.findClientByIdn(message.getClient().getIdn(), conn);

        if (client == null) {
            clientDao.insertClient(message.getClient(), conn);
            client = clientDao.findClientByIdn(message.getClient().getIdn(), conn);
            log.info("Client was inserted into db");
        }

        if (client == null) {
            log.severe("Is not possible to create the credit card");
        }

        CreateCardRequest newCard = new CreateCardRequest();
        newCard.setBankId(message.getBankId());
        newCard.setMaximumBalance(message.getMaximumBalance());
        newCard.setClient(client);
        if (cardDao.insertCreditCard(newCard, conn)) {
            log.info("new Credit Card is created");
        }
    }
}