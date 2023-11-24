package com.function.card.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.logging.Logger;

import com.function.card.model.CardTransaction;
import com.function.card.model.CreateTransactionRequest;

public class TransactionDao {

    private Logger log = Logger.getLogger(TransactionDao.class.getName());

    public boolean insertTransaction(CreateTransactionRequest request, Connection connection) {
        log.info("Insert transaction");

        try {
            PreparedStatement insertStatement = connection
                .prepareStatement(
                    """
                        INSERT INTO card_transaction (tid, transaction_status, denial_reason, commerce_name, tdate, amount, card_id) 
                        VALUES 
                        (?, ?, ?, ?, ?, ?, ?)   
                    """
                    );

        
            insertStatement.setString(1, request.getTid());
            insertStatement.setInt(2, request.getStatus());
            insertStatement.setString(3, request.getDenialReason());
            insertStatement.setString(4, request.getCommerce());
            insertStatement.setDate(5, new Date(new java.util.Date().getTime()));
            insertStatement.setInt(6, request.getAmount());
            insertStatement.setInt(7, request.getCardId());
            insertStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            log.severe("error inserting creadit card " + e.getLocalizedMessage());
        }
        return false;
    }

    public CardTransaction findTransaction(String tid, Connection connection) {
        CardTransaction card = null;
        try {
            log.info("Read transaction");
            PreparedStatement readStatement = connection
            .prepareStatement("SELECT * FROM card_transaction WHERE tid = ?;");
            readStatement.setString(1, tid);
            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                log.info("There is no data in the database!");
                return null;
            }
            card = new CardTransaction();
            card.setId(resultSet.getInt("id"));
            card.setStatus(resultSet.getInt("transaction_status"));
            card.setDenialReason(resultSet.getString("denial_reason"));
            card.setCommerce(resultSet.getString("commerce_name"));
            card.setDatetime(resultSet.getDate("tdate"));
            card.setAmount(resultSet.getInt("amount"));
            card.setCardId(resultSet.getInt("card_id"));
            
        } catch (Exception e) {
            log.severe("error quering Card" + e.getLocalizedMessage());
        }
        return card;
    }
    
}
