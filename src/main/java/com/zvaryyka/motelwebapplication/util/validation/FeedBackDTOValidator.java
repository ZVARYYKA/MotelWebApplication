package com.zvaryyka.motelwebapplication.util.validation;

import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class FeedBackDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return FeedBackDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FeedBackDTO feedbackDTO = (FeedBackDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "NotEmpty.feedBackDTO.message", "Данное поле не должно быть пустым.");

        if (feedbackDTO.getMark() < 0 || feedbackDTO.getMark() >= 6) {
            errors.rejectValue("mark", "Range.feedBackDTO.mark", "Оценка должна быть больше 0 и меньше 6");
            log.info("Оценка должна быть больше 0 и меньше 6 для пользователя: {}", feedbackDTO.getUserName());
        }

        if (feedbackDTO.getMessage().length() < 10 || feedbackDTO.getMessage().length() > 300) {
            errors.rejectValue("message", "Size.feedBackDTO.message", "Ваш отзыв должен состоять от 10 до 300 символов");
            log.info("Ваш отзыв должен состоять от 10 до 300 символов для пользователя: {}", feedbackDTO.getUserName());
        }
    }
}
