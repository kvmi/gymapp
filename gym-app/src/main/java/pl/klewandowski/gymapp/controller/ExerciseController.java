package pl.klewandowski.gymapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.klewandowski.gymapp.model.Exercise;
import pl.klewandowski.gymapp.service.ExerciseService;

import java.security.Principal;

@Controller
public class ExerciseController {

    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercises")
    public String exercises(Model model){
        model.addAttribute("exerciseList", exerciseService.getAllExercises());
        return "exercises";
    }

    @GetMapping("/addExercise")
    public ModelAndView addExercise() {
        return new ModelAndView("add_exercise", "exercise", new Exercise());
    }

    @RequestMapping(value = "/save-exercise", method = RequestMethod.POST)
    public ModelAndView saveExercise(Exercise exercise, Principal principal) {
        exerciseService.addExercise(exercise, principal.getName());
        return new ModelAndView("redirect:/exercises");
    }

    @GetMapping("/exercise/edit/{id}")
    public String editExercise(@PathVariable(value = "id") long id, Model model)
    {
        Exercise exercise = exerciseService.getExerciseById(id);
        model.addAttribute("exercise", exercise);
        return "update_exercise";
    }


    @GetMapping("/exercise/delete/{id}")
    public String deleteExercise(@PathVariable(value = "id") long id){
        exerciseService.deleteExercise(id);
        return "redirect:/exercises";
    }




}
