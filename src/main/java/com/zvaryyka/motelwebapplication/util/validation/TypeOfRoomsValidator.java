package com.zvaryyka.motelwebapplication.util.validation;

import com.zvaryyka.motelwebapplication.models.TypeOfRooms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class TypeOfRoomsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TypeOfRooms.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TypeOfRooms typeOfRooms = (TypeOfRooms) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "NotEmpty.typeOfRooms.roomType", "Название типа комнаты не должно быть пустым");

        if (typeOfRooms.getOneDayCost() <= 0) {
            errors.rejectValue("oneDayCost", "Min.typeOfRooms.oneDayCost", "Стоимость за сутки не может быть меньше 0");
            log.info("Стоимость за сутки не может быть меньше 0 для типа комнаты с id: {}", typeOfRooms.getTypeId());
        }
    }
}
