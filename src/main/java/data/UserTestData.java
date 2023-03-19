package data;

import org.apache.commons.lang3.RandomStringUtils;

public final class UserTestData {
    //Генерация тестовых данных для пользователя
    public static final String RANDOM_STRING_EMAIL = RandomStringUtils.randomAlphanumeric(5, 15) + "@yandex.ru";
    public static final String RANDOM_STRING_PASSWORD = RandomStringUtils.randomAlphanumeric(5, 15);
    public static final String RANDOM_STRING_NAME = RandomStringUtils.randomAlphanumeric(5, 15);

    private UserTestData() {
    }
}