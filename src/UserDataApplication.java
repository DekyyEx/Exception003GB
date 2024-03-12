import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class UserDataApplication {
    private static final Logger logger = Logger.getLogger(FileWriter.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        logger.info("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        String input = scanner.nextLine();

        String[] data = input.split(" ");
        if (data.length != 6) {
            logger.warning("Ошибка: введено недостаточно или слишком много данных");
            return;
        }

        String surname = data[0];
        String firstName = data[1];
        String lastName = data[2];

        String dateOfBirthString = data[3];
        if (!isValidDateFormat(dateOfBirthString)) {
            logger.warning("Ошибка: неверный формат даты рождения");
            return;
        }

        String phoneNumberString = data[4];
        if (!isValidPhoneNumberFormat(phoneNumberString)) {
            logger.warning("Ошибка: неверный формат номера телефона");
            return;
        }

        String gender = data[5];
        if (!isValidGender(gender)) {
            logger.warning("Ошибка: неверный формат пола");
            return;
        }

        String formattedData = String.format("%s %s %s %s %s %s ", surname, firstName, lastName,
                dateOfBirthString, phoneNumberString, gender);

        try (FileWriter fileWriter = new FileWriter(surname + ".txt", true);) {
            fileWriter.append(formattedData).append("\n");

            logger.info("Данные успешно записаны в файл: " + surname + ".txt");
        } catch (IOException e) {
            logger.warning("Ошибка при записи данных в файл");
        }
    }

    private static boolean isValidDateFormat(String date) {
        String[] parts = date.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1900 && year <= 2100;
    }

    private static boolean isValidPhoneNumberFormat(String number) {
        try {
            long phoneNumber = Long.parseLong(number);
            return phoneNumber >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidGender(String gender) {
        return gender.equals("f") || gender.equals("m");
    }
}