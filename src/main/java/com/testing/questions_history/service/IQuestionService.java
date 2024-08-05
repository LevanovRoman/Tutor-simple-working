package com.testing.questions_history.service;

import com.testing.questions_history.QuestionNotFoundException;
import com.testing.questions_history.model.Question;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    List<Question> getAllQuestions();
    Question updateQuestion(Question question, Integer id) throws QuestionNotFoundException;
    Optional<Question> getQuestionById(Integer id) throws QuestionNotFoundException;
    void deleteQuestion(Integer id) throws QuestionNotFoundException;

    List<Question> getQuestionsByCategoryId(int categoryId);

    void save(Question question);
}
