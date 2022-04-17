package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM schooldb.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM schooldb.");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String createDeleteQuery(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM schooldb.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE id =" + id);
        return sb.toString();
    }

    public List<T> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(createSelectAllQuery());

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void delete(int id) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery(id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            int result = statement.executeUpdate(query);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deletedById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public T insert(T t) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM" + " schooldb." + type.getSimpleName());
        ResultSetMetaData metadata = results.getMetaData();

        int columnCount = metadata.getColumnCount();

        StringBuilder insertQuery = new StringBuilder();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        insertQuery.append("insert into schooldb." + type.getSimpleName() + " ");
        columns.append("(");
        values.append("values (");

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metadata.getColumnName(i);

            ///construct method
            String methodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            Class classT = t.getClass();
            Method getMethod = classT.getMethod(methodName);
            Class paramType = getMethod.getReturnType();
            Object rez = getMethod.invoke(t);

            if (i != columnCount)
                columns.append(columnName + ", ");
            else
                columns.append(columnName);

            if (paramType.getSimpleName() == String.class.getSimpleName()) {
                if (i == columnCount)
                    values.append("'" + rez.toString() + "' ");

                else
                    values.append("'" + rez.toString() + "' ,");
            } else {
                if (i == columnCount)
                    values.append(" " + rez.toString());

                else
                    values.append(" " + rez.toString() + " ,");
            }

            //System.out.println(columnName + " " + rez.toString());
        }

        columns.append(") ");
        values.append(") ");
        insertQuery.append(columns);
        insertQuery.append(values);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertQuery.toString());
            return t;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:Insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T update(T t) throws InvocationTargetException, IllegalAccessException, SQLException, NoSuchMethodException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM" + " schooldb." + type.getSimpleName());
        ResultSetMetaData metadata = results.getMetaData();

        int columnCount = metadata.getColumnCount();

        StringBuilder updateQuery = new StringBuilder();

        updateQuery.append("UPDATE schooldb." + type.getSimpleName() + " SET ");

        for (int i = 2; i <= columnCount; i++) {
            String columnName = metadata.getColumnName(i);

            ///construct method
            String methodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            Class classT = t.getClass();
            Method getMethod = classT.getMethod(methodName);
            Class paramType = getMethod.getReturnType();
            Object rez = getMethod.invoke(t);

            if (paramType.getSimpleName() == String.class.getSimpleName()) {
                if (i != columnCount)
                    updateQuery.append(columnName + " = '" + rez.toString() + "' ,");
                else
                    updateQuery.append(columnName + " = '" + rez.toString() + "' ");
            } else {
                if (i != columnCount)
                    updateQuery.append(columnName + " = " + rez.toString() + " ,");
                else
                    updateQuery.append(columnName + " = " + rez.toString() + " ");
            }

        }

        String columnName = metadata.getColumnName(1);
        String methodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
        Class classT = t.getClass();
        Method getMethod = classT.getMethod(methodName);
        Class paramType = getMethod.getReturnType();
        Object rez = getMethod.invoke(t);

        updateQuery.append(" WHERE " + columnName + " = " + rez.toString());

        //System.out.println(updateQuery);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(updateQuery.toString());
            return t;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:UPDATE " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public Class<T> getType() {
        return type;
    }
}
