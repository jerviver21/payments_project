package com.function.card;

import com.microsoft.azure.functions.annotation.*;

import java.sql.Connection;
import java.util.logging.Logger;

import com.function.card.db.CreditCardDao;
import com.function.card.db.DaoManager;
import com.function.card.db.TransactionDao;
import com.function.card.model.CreateTransactionRequest;
import com.function.card.model.CreditCard;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Topic Trigger.
 */
public class CreateTransactionFunctionTopic {

    @FunctionName("createtransaction_topic")
    public void run(
        @ServiceBusTopicTrigger(
            name = "message",
            topicName = "newtransaction",
            subscriptionName = "subs1",
            connection = "bus_cardcompany"
        )
        CreateTransactionRequest message,
        final ExecutionContext context
    ) {
        Logger log = context.getLogger();
        log.info("Java Service Bus Topic (create transaction) trigger function executed.");

        String dbConnectionString = System.getenv("db_cardcompany");

        Connection conn =  DaoManager.createConnection(dbConnectionString);
        TransactionDao transactionDao = new TransactionDao();
        CreditCardDao cardDao = new CreditCardDao();
        CreditCard card = cardDao.findCredirtCard(message.getCardNumber(), 
        message.getExpirationDate(), message.getCcv(), conn);

        if (card == null) {
            log.info("Invalid Card");
            message.setDenialReason("Invalid Card");
            message.setStatus(2);//2 - DENIED
            transactionDao.insertTransaction(message, conn);
            log.info("transaction denied");
            return;
        }

        Integer remainingBalance = card.getMaximumBalance() - card.getBalance();

        if (message.getAmount().intValue() > remainingBalance.intValue()) {
            log.info("Invalid Amount");
            message.setDenialReason("Invalid Amount");
            message.setStatus(2);//2 - DENIED
            message.setCardId(card.getId());
            transactionDao.insertTransaction(message, conn);
            log.info("transaction denied");
            return;
        }


        message.setCardId(card.getId());
        message.setStatus(1);//1 - APPROVED
        transactionDao.insertTransaction(message, conn);
        card.setBalance(card.getBalance() + message.getAmount());
        cardDao.updateBalance(card.getId(), card.getBalance(), conn);
        log.info("transaction approved");
        
    }
}