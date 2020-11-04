/**
 * Класс в котором осуществляется проверка валидности входных данных
 * при регистрации пользователя через админ панель, при регистрации пользователя, при авторизации.
 * При наличии ошибок во входных данных происходит логирование логгером класса вызывавшего метод проверки.
 * Уровень логирования : WARN
 * @autor Карпов Василий
 * @version 1.0
 * @data 09.10.2020
 */

package com.board_of_ads.util;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class BindingResultLogs {

    public boolean checkUserFields (BindingResult bindingResult, Logger logger) {
            if (bindingResult.hasErrors()) {
                logAllErrors(bindingResult, logger);
                return false;
            }
            return true;
    }

    private void logAllErrors (BindingResult bindingResult, Logger logger) {
        bindingResult
                .getFieldErrors()
                .forEach(fieldError -> logger.warn(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
    }

}
