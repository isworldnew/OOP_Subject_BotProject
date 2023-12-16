package org.example;

import org.example.Validations.*;

public enum Command {
    START("/start", null) {
        @Override
        String textToChat() { return ""; }
    }, //filled

    INFO("/info", null) {
        @Override
        String textToChat() { return "/willbefree - Внести информацию в бот, где припарковали автомобиль, и когда уедете. Данная информация будет доступна пользователям бота до тех пор, пока не наступит указанное время. Введённые данные в любой момент можно удалить командой /deletemeta\n" +
                "\n" +
                "/places - Получите изображение с указанием, где вообще можно встать в шаговой доступности от Политеха\n" +
                "\n" +
                "/wheretopark - Узнаете, где в скором времени освободится парковочное место\n" +
                "\n" +
                "/trafficpolicealarm - Некоторые недобросовестные водители оставляют автомобиль вторым рядом на Политехнической улице. Владелец этого бота крайне не поддерживает такое нарушение ПДД. Если же Вы по какой-то причине солидарны с такими автовладельцами, можете попытаться успеть предупредить их о приезде ДПС\n" +
                "\n" +
                "/blocked - Попробуйте уведомить владельца авто, заблокировавшего вас на парковке\n" +
                "\n" +
                "/deletemeta - Удалить данные, которые вы вводили. В последующие разы сможете ввести их заново."; }
    }, //filled

    WILLBEFREE("/willbefree", new ValidForWBF()) {
        @Override
        String textToChat() {
            return "Мы попросим вас ввести данные. В любой момент сохранённые данные можно удалить командой /delete.\n" +
                    "Введите последовательность данных по образцу в формате через запятая+пробел.\n" +
                    "<b>Улица, 12:34, Марка, Модель, Цвет, Ъ000ЪЪ000 (или Ъ000ЪЪ00)</b>\n" +
                    "\n" +
                    "Список улиц для ввода:\n" +
                    "- Беговая\n" +
                    "- Политехническая\n" +
                    "- Миротворцева\n" +
                    "- Большая Садовая\n" +
                    "- 2-я Садовая\n" +
                    "- KFC";
        }
    }, //filled

    PLACES("/places", null) {
        @Override
        String textToChat() { return ""; }
    }, //filled

    WHERETOPARK("/wheretopark", null) {
        @Override
        String textToChat() {
            return "";
        }
    }, //filled

    TRAFFICPOLICEALARM("/trafficpolicealarm", null) {
        @Override
        String textToChat() { return "\uD83D\uDEA8 На ул. Политехническая работает ДПС \uD83D\uDEA8"; }
    }, //filled

    BLOCKED("/blocked", new ValidForBlocked()) {
        @Override
        String textToChat() {
            return "Введите номер автомобиля, заблокировавшего вас. Мы попытаемся отыскать владельца. Вводите номер в следующем формате: Ъ000ЪЪ000 (или Ъ000ЪЪ00)";
        }
    }, //filled

    DELETEMETA("/deletemeta", null) {
        @Override
        String textToChat() { return ""; }
    }; //filled

    final Validatable rule;
    final String commandText;
    private Command(String commandText, Validatable rule) {
        this.commandText = commandText;
        this.rule = rule;
    }

    abstract String textToChat();
}
