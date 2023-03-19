package constants;

import org.apache.commons.lang3.RandomStringUtils;

public interface ConstantsUserTestData {
    //Генерация тестовых данных для пользователя
    public final static String RANDOM_STRING_EMAIL = RandomStringUtils.randomAlphanumeric(5, 15) + "@yandex.ru";
    public final static String RANDOM_STRING_PASSWORD = RandomStringUtils.randomAlphanumeric(5, 15);
    public final static String RANDOM_STRING_NAME = RandomStringUtils.randomAlphanumeric(5, 15);
}