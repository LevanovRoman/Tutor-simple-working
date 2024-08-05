package com.testing.questions_history.service;

import com.testing.questions_history.QuestionAlreadyExistsException;
import com.testing.questions_history.QuestionNotFoundException;
import com.testing.questions_history.model.Question;
import com.testing.questions_history.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public void save(Question question) {
//        if (questionAlreadyExists(question.getQuestion_title())){
//            throw new QuestionAlreadyExistsException("This question already exists!");
//        }
        questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Integer id) throws QuestionNotFoundException {
        return Optional.ofNullable(questionRepository.findById(id).orElseThrow(() ->
                new QuestionNotFoundException("Sorry, no question found with the Id: \" + id")));
    }

    @Override
    public Question updateQuestion(Question question, Integer id) throws QuestionNotFoundException {
        return getQuestionById(id).map(quest -> {
            quest.setQuestion_title(question.getQuestion_title());
            quest.setOption1(question.getOption1());
            quest.setOption2(question.getOption2());
            quest.setOption3(question.getOption3());
            quest.setOption4(question.getOption4());
            quest.setRight_answer(question.getRight_answer());
            quest.setDifficulty_level(question.getDifficulty_level());
            quest.setCategory(question.getCategory());
            return questionRepository.save(quest);
        }).orElseThrow(() -> new QuestionNotFoundException("Sorry, this question not found"));
    }

    @Override
    public void deleteQuestion(Integer id) throws QuestionNotFoundException {
        if (!questionRepository.existsById(id)){
            throw new QuestionNotFoundException("Sorry, this question not found");
        }
        questionRepository.deleteById(id);
    }

//    private boolean questionAlreadyExists(String question_title) {
//        return questionRepository.findByQuestion_title(question_title).isPresent();
//    }
//    public List<Question> getAllQuestions(){
//        return questionRepository.findAll();
//    }
//
//    public void save(Question question){
//        questionRepository.save(question);
//    }
//
//    public Question getQuestionById(Integer id) throws QuestionNotFoundException {
//        Optional<Question> question = questionRepository.findById(id);
//        if (question.isPresent()){
//            return question.get();
//        }
//        throw new QuestionNotFoundException("Could not find any questions with ID " + id);
//    }
//
//    public void deleteQuestionById(Integer id) throws QuestionNotFoundException {
//        Long count = questionRepository.countById(id);
//        if (count == null || count == 0){
//            throw new QuestionNotFoundException("Could not find any questions with ID " + id);
//        }
//        questionRepository.deleteById(id);
//    }

    public List<Question> getQuestionsByCategoryId(int categoryId) {
        return questionRepository.findAllByCategory_Id(categoryId);
    }


}
