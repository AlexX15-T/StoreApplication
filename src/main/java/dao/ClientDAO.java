package dao;

import bll.ClientBLL;
import com.google.protobuf.StringValue;
import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.logging.Level;

public class ClientDAO extends AbstractDAO<Client>{
/*
    public ClientDAO()
    {
        super();
    }

    private String createUpdateQuery(Client t) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("schooldb.client");
        sb.append(" SET name = '" + t.getName() + "' ,");
        sb.append(" address = '" + t.getAddress() + "' ,");
        sb.append(" email = '" + t.getEmail() + "' ,");
        sb.append(" age = " + t.getAge());
        sb.append(" WHERE id = " + t.getId());
        return sb.toString();
    }

    private String createInsertQuery(Client t) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into schooldb.client(id, name, address, age, email) values ( ");
        sb.append(t.getId() + ", ");
        sb.append("'" + t.getName() + "' ,");
        sb.append("'" + t.getAddress() + "' ,");
        sb.append( t.getAge() + ",");
        sb.append("'" + t.getEmail() + "')");
        return sb.toString();
    }

    public Client update(Client t)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            int cnt = statement.executeUpdate(query);

            return t;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:Update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public Client insert(Client t)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            int cnt = statement.executeUpdate(query);

            return t;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:Update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
*/
}
