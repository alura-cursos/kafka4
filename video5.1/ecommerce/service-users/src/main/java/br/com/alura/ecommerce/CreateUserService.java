package br.com.alura.ecommerce;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class CreateUserService implements ConsumerService<Order> {

    private final Connection connection;

    private CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db";
        connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table Users (" +
                    "uuid varchar(200) primary key," +
                    "email varchar(200))");
        } catch(SQLException ex) {
            // be careful, the sql could be wrong, be reallllly careful
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServiceRunner<>(CreateUserService::new).start(1);
    }

    @Override
    public String getConsumerGroup() {
        return CreateUserService.class.getSimpleName();
    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_NEW_ORDER";
    }

    public void parse(ConsumerRecord<String, Message<Order>> record) throws SQLException {
        System.out.println("------------------------------------------");
        System.out.println("Processing new order, checking for new user");
        System.out.println(record.value());
        var order = record.value().getPayload();
        if(isNewUser(order.getEmail())) {
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(String email) throws SQLException {
        var insert = connection.prepareStatement("insert into Users (uuid, email) " +
                "values (?,?)");
        var uuid = UUID.randomUUID().toString();
        insert.setString(1, uuid);
        insert.setString(2, email);
        insert.execute();
        System.out.println("Usuário " + uuid + " e " + email + " adicionado");
    }

    private boolean isNewUser(String email) throws SQLException {
        var exists = connection.prepareStatement("select uuid from Users " +
                "where email = ? limit 1");
        exists.setString(1, email);
        var results = exists.executeQuery();
        return !results.next();
    }

}
