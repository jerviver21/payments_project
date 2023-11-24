package com.function.card.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import com.function.card.model.CreateCardRequest;
import com.function.card.model.CreditCard;

public class CreditCardDao {

    private Logger log = Logger.getLogger(CreditCardDao.class.getName());


    public boolean updateBalance(Integer cardId, Integer newBalance, Connection connection) {
        log.info("Update credit card balance");

        try {
            PreparedStatement insertStatement = connection
                .prepareStatement(
                    """
                        UPDATE credit_card SET balance = ? WHERE id = ?;
                    """
                    );

            insertStatement.setInt(1, newBalance);
            insertStatement.setInt(2, cardId);
            insertStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            log.severe("error updating creadit card balance " + e.getLocalizedMessage());
        }
        return false;
    }


    public boolean insertCreditCard(CreateCardRequest request, Connection connection) {
        log.info("Insert credit card");

        try {
            PreparedStatement insertStatement = connection
                .prepareStatement(
                    """
                        INSERT INTO credit_card (card_number, expiration_date, ccv, maximun_balance, balance, client_id, bank_id) 
                        VALUES 
                        (?, ?, ?, ?, ?, ?, ?)   
                    """
                    );

        

            insertStatement.setString(1, generateCardNumber());
            insertStatement.setString(2, "2028-12-31");
            insertStatement.setInt(3, generateCcv());
            insertStatement.setInt(4, request.getMaximumBalance());
            insertStatement.setInt(5, 0);
            insertStatement.setInt(6, request.getClient().getId());
            insertStatement.setInt(7, request.getBankId());
            log.info("****" + request.getClient().getId() + " ****");
            insertStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            log.severe("error inserting creadit card " + e.getLocalizedMessage());
        }
        return false;
    }

    public CreditCard findCredirtCard(String number, String edate, Integer code, Connection connection) {
        CreditCard card = null;
        try {
            log.info("Read credit card");
            PreparedStatement readStatement = connection
            .prepareStatement("SELECT * FROM credit_card WHERE card_number = ? and expiration_date=? and ccv=?;");
            readStatement.setString(1, number);
            readStatement.setString(2, edate);
            readStatement.setInt(3, code);
            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                log.info("There is no data in the database!");
                return null;
            }
            card = new CreditCard();
            card.setId(resultSet.getInt("id"));
            card.setCardNumber(resultSet.getString("card_number"));
            card.setExpirationDate(resultSet.getString("expiration_date"));
            card.setCcv(resultSet.getInt("ccv"));
            card.setMaximumBalance(resultSet.getInt("maximun_balance"));
            card.setBalance(resultSet.getInt("balance"));
            card.setBankId(resultSet.getInt("bank_id"));
            card.setClientId(resultSet.getInt("client_id"));
            
        } catch (Exception e) {
            log.severe("error quering Card" + e.getLocalizedMessage());
        }
        return card;
    }

    public CreditCard findCredirtCard(String number,  Connection connection) {
        CreditCard card = null;
        try {
            log.info("Read credit card");
            PreparedStatement readStatement = connection
            .prepareStatement("SELECT * FROM credit_card WHERE card_number = ?;");
            readStatement.setString(1, number);
            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                log.info("There is no data in the database!");
                return null;
            }
            card = new CreditCard();
            card.setId(resultSet.getInt("id"));
            card.setCardNumber(resultSet.getString("card_number"));
            card.setExpirationDate(resultSet.getString("expiration_date"));
            card.setCcv(resultSet.getInt("ccv"));
            card.setMaximumBalance(resultSet.getInt("maximun_balance"));
            card.setBalance(resultSet.getInt("balance"));
            card.setBankId(resultSet.getInt("bank_id"));
            card.setClientId(resultSet.getInt("client_id"));
            
        } catch (Exception e) {
            log.severe("error quering Card" + e.getLocalizedMessage());
        }
        return card;
    }

    private String generateCardNumber() {
        Double first =  Math.random()*10000;
        Double second =  Math.random()*10000;
        Double third =  Math.random()*10000;
        Double forth =  Math.random()*10000;

        return String.format("%04d", first.intValue()) + " " +
                String.format("%04d", second.intValue()) + " " +
                String.format("%04d", third.intValue()) + " " +
                String.format("%04d", forth.intValue()) + " ";
    }

    private Integer generateCcv() {
        Integer ccv =  (int)Math.random()*1000;

        return ccv;
    }

    
}
