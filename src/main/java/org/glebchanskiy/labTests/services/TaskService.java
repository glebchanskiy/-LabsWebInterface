package org.glebchanskiy.labTests.services;

import org.glebchanskiy.labTests.models.Answer;
import org.glebchanskiy.labTests.models.Message;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {
    public Answer generateAnswer(Message message) {
        Answer answer = new Answer();
        String expression = message.getContent();

        try {
            answer.setContent(validatePdnf(expression));
        } catch (RuntimeException e) {
            answer.setContent(e.getMessage());
        }
        return answer;
    }

    private String validatePdnf(String expression) {
        if (PdnfValidator.validate(expression)){
            return "Формула является СДНФ";
        } else {
            return "Формула не является СДНФ";
        }
    }


}
