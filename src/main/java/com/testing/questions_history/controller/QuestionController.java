package com.testing.questions_history.controller;

import com.testing.questions_history.QuestionNotFoundException;
import com.testing.questions_history.model.Category;
import com.testing.questions_history.model.Question;
import com.testing.questions_history.service.CategoryService;
import com.testing.questions_history.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;
    private final CategoryService categoryService;

    public List<Category> getCategoryList(){
        return categoryService.getAllCategory();
    }

    @GetMapping
    public String questionHome(Model model){
        model.addAttribute("categoryList", getCategoryList());
        return "question-home";
    }

    @GetMapping("/all")
    public String allQuestions(Model model){
        List<Question> questionList = questionService.getAllQuestions();
        model.addAttribute("questionsList", questionList);
        model.addAttribute("categoryList", getCategoryList());
        return "question-all";
    }

    @GetMapping("/category/{categoryId}")
    public String questionsByCategory(@PathVariable("categoryId") int categoryId, Model model){
        System.out.println(categoryId);
        List<Question> questionList = questionService.getQuestionsByCategoryId(categoryId);
        model.addAttribute("questionsList", questionList);
        model.addAttribute("categoryList", getCategoryList());
        return "question-all";
    }

    @GetMapping("/new")
    public String showNewForm(Model model){
        model.addAttribute("categoryList", getCategoryList());
        model.addAttribute("question", new Question());
        model.addAttribute("pageTitle", "Add New Question");
        return "question-form";
    }

    @PostMapping("/save")
    public String saveQuestion(Question question, RedirectAttributes attributes){
        questionService.save(question);
        attributes.addFlashAttribute("message", "The question has been saved successfully.");
        return "redirect:/question/all";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes){
        try{
            Optional<Question> question = questionService.getQuestionById(id);
            model.addAttribute("categoryList", getCategoryList());
            model.addAttribute("question", question);
            model.addAttribute("pageTitle", "Edit Question (ID: " + id + ")");
            return "question-form";
        } catch (QuestionNotFoundException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/question/all";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id, RedirectAttributes attributes){
        try{
            questionService.deleteQuestion(id);
            attributes.addFlashAttribute("message", "The question ID " + id + " has been deleted.");
        } catch (QuestionNotFoundException e) {
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/question/all";
    }
}
