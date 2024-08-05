package com.testing.questions_history;

public class QuestionAlreadyExistsException extends RuntimeException{
    public QuestionAlreadyExistsException(String message){
        super(message);
    }
}
