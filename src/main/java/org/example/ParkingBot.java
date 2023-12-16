package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import org.example.db.LocalDataBaseInteraction;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParkingBot extends TelegramLongPollingBot {

    private static Map<Long, String> expectation = new HashMap<>();
    private static Connection connection;
    private static LocalDataBaseInteraction db;

    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new ParkingBot());
            System.out.println("Сессия бота активна");
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }

        db = new LocalDataBaseInteraction();
        connection = db.getConnection();

    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            if (update.getMessage().getText().startsWith("/")) {
                handleMenuCommands(update);
            }

            else {
                handleUserDataInput(update);
            }
        }
    }

    public void handleMenuCommands(Update update) {
        /*в принципе, сюда если попали, уже точно знаем, что у обновления
        есть сообщение, а в нём есть текст, начинающийся с "/". Нет смысла
        тут это дополнительно проверять*/

        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (text.equals(Command.START.commandText)) {
            db.registerNewChatId(chatId);
        } //ready

        if (text.equals(Command.INFO.commandText)) {
            sendMessageToChat(chatId, Command.INFO.commandText);
        } //ready

        if (text.equals(Command.WILLBEFREE.commandText)) {

        }

        if (text.equals(Command.WHERETOPARK.commandText)) {

        }

        if (text.equals(Command.TRAFFICPOLICEALARM.commandText)) {
            List<Long> allChatId = db.getAllChatId();

            for (Long currentChatId : allChatId)
                sendMessageToChat(currentChatId, Command.TRAFFICPOLICEALARM.commandText);

        } //ready?

        if (text.equals(Command.BLOCKED.commandText)) {

        }

        if (text.equals(Command.DELETEMETA.commandText)) {
            db.deleteMeta(chatId);
        } //ready

        if (text.equals(Command.PLACES.commandText)) {
            File photo = new File("D:\\OOP_Project\\map.png");

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(new InputFile(photo));

            try { execute(sendPhoto); }
            catch (TelegramApiException ex) {
                System.out.println("ну ладно, сам разберётся");
            }
        } //ready

    }

    public void handleUserDataInput(Update update) {}

    public void sendMessageToChat(long chatId, String text) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText(text);
        msg.enableHtml(true);

        try { execute(msg); }
        catch(TelegramApiException ex) {
            System.out.println("не отправилось");
        }
    }

    @Override
    public String getBotUsername() {
        return "sstuParkingBot";
    }

    @Override
    public String getBotToken() {
        return "6592012453:AAE6k2CGoEpWwohjBIngbjECs6lOP1ny7_o";
    }
}
