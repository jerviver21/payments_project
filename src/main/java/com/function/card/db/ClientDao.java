package com.function.card.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import com.function.card.model.Client;

public class ClientDao {
    
    private Logger log = Logger.getLogger(ClientDao.class.getName());

    public Client findClientById(Integer id, Connection connection) {
        Client client = null;
        try {
            log.info("Read client");
            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM client WHERE id = ?;");
            readStatement.setInt(1, id);
            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                log.info("There is no data in the database!");
                return null;
            }
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setIdn(resultSet.getString("idn"));
            client.setFirstName(resultSet.getString("first_name"));
            client.setLastName(resultSet.getString("last_name"));
            client.setAddress(resultSet.getString("client_address"));
        } catch (Exception e) {
            log.severe("error quering Client by Id" + e.getLocalizedMessage());
        }
        return client;
    }

    public Client findClientByIdn(String idn, Connection connection) {
        Client client = null;
        try {
            log.info("Read client");
            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM client WHERE idn = ?;");
            readStatement.setString(1, idn);
            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                log.info("There is no data in the database!");
                return null;
            }
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setIdn(resultSet.getString("idn"));
            client.setFirstName(resultSet.getString("first_name"));
            client.setLastName(resultSet.getString("last_name"));
            client.setAddress(resultSet.getString("client_address"));
            
        } catch (Exception e) {
            log.severe("error quering Client by Idn" + e.getLocalizedMessage());
        }
        return client;
    }

    public boolean insertClient(Client client, Connection connection) {
        log.info("Insert client");

        try {
            PreparedStatement insertStatement = connection
                .prepareStatement(
                    """
                        INSERT INTO client (idn, first_name, last_name, client_address) 
                        VALUES 
                        (?, ?, ?, ?)   
                    """
                    );

        

            insertStatement.setString(1, client.getIdn());
            insertStatement.setString(2, client.getFirstName());
            insertStatement.setString(3, client.getLastName());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            log.severe("error inserting creadit card " + e.getLocalizedMessage());
        }
        return false;
    }


}
