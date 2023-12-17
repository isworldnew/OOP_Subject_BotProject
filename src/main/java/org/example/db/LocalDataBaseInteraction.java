package org.example.db;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;

public class LocalDataBaseInteraction {
    private final String url = "jdbc:sqlite:D:\\OOP_Project\\ParkingDB.db";
    private Connection connection;

    public LocalDataBaseInteraction() { //подключение к локальной базе данных
        try { Class.forName("org.sqlite.JDBC"); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Успешное подключение к базе данных");
        }
        catch (SQLException ex) {
            System.out.println("Подключение не удалось");
            System.out.println("пофиг, пляшем без бд");
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean registerNewChatId(long chatId) {

        deleteInvalidRecords();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT chatId FROM Users");
            ResultSet queryResult = ps.executeQuery();

            while(queryResult.next()) { //если такой chatId уже есть, не добавляем ещё раз
                long currentChatId = queryResult.getLong("chatId");

                if (chatId == currentChatId)
                    return true;
            }

            //если такого chatId ещё не было, вот тогда добавляем
            ps = connection.prepareStatement("INSERT INTO Users (chatId, metadata) VALUES(?, ?)");
            ps.setLong(1, chatId);
            ps.setInt(2, 0);

            ps.executeUpdate();
            return true;

        }

        catch (SQLException ex) {
            System.out.println("Не удалось зарегистрировать, ну и ладно");
            return false;
        }
    }

    public List<Long> getAllChatId() {

        deleteInvalidRecords();

        List<Long> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT chatId FROM Users");
            ResultSet queryResult = ps.executeQuery();

            while (queryResult.next())
                result.add(queryResult.getLong("chatId"));

        }
        catch(SQLException ex) {
            System.out.println("Не удалось извлечь");
            return result;
        }

        return result;
    }

    public boolean deleteMeta(long chatId) {

        deleteInvalidRecords();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Users SET metadata = 0, brand = NULL, model = NULL, color = NULL, numberplate = NULL, untiltime = NULL, address = NULL, ban_date = NULL WHERE chatId = " + chatId);

            ps.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println("Не удалось удалить, ну и ладно");
            ex.printStackTrace();
            return false;
        }
    }

    public Long getChatIdByNumberplate(String numberplate) {

        deleteInvalidRecords();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT chatId FROM Users WHERE numberplate = '" + numberplate + "'");
            ResultSet queryResult = ps.executeQuery();

            //Тут два случая: либо вернётся один конкретный chatId, либо запрос не вернёт ничего
            //(это в случае, если человек ботом пользуется, но данные его не хранятся)

            if (queryResult.next())
                return queryResult.getLong("chatId");

            return null;
        }
        catch (SQLException ex) {
            /*System.out.println("Не удалось извлечь данные");
            ex.printStackTrace();*/
            return null;
        }
    }

    public void insertDataByChatId(long chatId, String[] dataFromText) {

        deleteInvalidRecords();

        try {
            //Улица, 12:34, Марка, Модель, Цвет, Ъ000ЪЪ000 (или Ъ000ЪЪ00)
            PreparedStatement ps = connection.prepareStatement("UPDATE Users SET metadata = ?, address = ?, untiltime = ?, brand = ?, model = ?, color = ?, numberplate = ? WHERE chatId = " + chatId);
            ps.setInt(1, 1);
            ps.setString(2, dataFromText[0]);
            ps.setString(3, dataFromText[1]);
            ps.setString(4, dataFromText[2]);
            ps.setString(5, dataFromText[3]);
            ps.setString(6, dataFromText[4]);
            ps.setString(7, dataFromText[5]);

            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String actualInformation() {
        deleteInvalidRecords();

        try {
            String result = "Где в скором времени должно освободиться место:\n";

            PreparedStatement ps = connection.prepareStatement("SELECT address, untiltime FROM Users WHERE untiltime IS NOT NULL;");

            ResultSet queryResult = ps.executeQuery();

            while(queryResult.next()) {
                result += "- " + queryResult.getString("address") + " ";
                result += queryResult.getString("untiltime") + "\n";
            }

            return result;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteInvalidRecords() {

        try {
            LocalTime currentTime = LocalTime.now();

            String hours, minutes;

            if (currentTime.getHour() < 10)
                hours = "0" + currentTime.getHour();
            else hours = String.valueOf(currentTime.getHour());

            if (currentTime.getMinute() < 10)
                minutes = "0" + currentTime.getMinute();
            else minutes = String.valueOf(currentTime.getMinute());

            PreparedStatement ps = connection.prepareStatement("UPDATE Users SET untiltime = NULL, address = NULL WHERE CAST(SUBSTR(untiltime, 0, 3) AS INTEGER) <= " + hours + " AND CAST(SUBSTR(untiltime, 4, 3) AS INTEGER) < " + minutes);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}

