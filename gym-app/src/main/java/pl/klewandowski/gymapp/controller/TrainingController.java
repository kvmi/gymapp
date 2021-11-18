package pl.klewandowski.gymapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.klewandowski.gymapp.model.Exercise;
import pl.klewandowski.gymapp.model.Training;
import pl.klewandowski.gymapp.service.ExerciseService;
import pl.klewandowski.gymapp.service.TrainingService;

import java.security.Principal;


@Controller
public class TrainingController {
    private TrainingService trainingService;
    private ExerciseService exerciseService;

    @Autowired
    public TrainingController(TrainingService trainingService, ExerciseService exerciseService) {
        this.trainingService = trainingService;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/trainings")
    public String trainings(Model model){
        model.addAttribute("trainingList", trainingService.getAllTrainings());
        return "trainings";
    }

    @GetMapping("/addTraining")
    public ModelAndView addTraining() {
        return new ModelAndView("add_training", "training", new Training());
    }

    @RequestMapping(value = "/save-training", method = RequestMethod.POST)
    public ModelAndView saveTraining(Training training, Principal principal) {
        trainingService.addTraining(training, principal.getName());
        return new ModelAndView("redirect:/trainings");
    }

    @RequestMapping(value = "/update-training/{id}", method = RequestMethod.POST)
    public ModelAndView updateTraining(@PathVariable long id, Training training) {

        trainingService.addExercise(id, training.getExercises());
        return new ModelAndView("redirect:/trainings");
    }

    @GetMapping("/training/edit/{id}")
    public String editTraining(@PathVariable(value = "id") long id, Model model)
    {
        Training training = trainingService.getTrainingById(id);
        model.addAttribute("training", training);
        return "update_training";
    }

    @GetMapping("/training/delete/{id}")
    public String deleteTraining(@PathVariable(value = "id") long id){
        trainingService.deleteTraining(id);
        return "redirect:/trainings";
    }

    @GetMapping("/trainings/{id}")
    public String training(@PathVariable long id, Model model, Principal principal) {
        Training training = trainingService.getTrainingById(id);
        model.addAttribute("exerciseList", exerciseService.getAllExercises());
        model.addAttribute("training", training);
        model.addAttribute("isOwner", trainingService.checkIfOwner(id, principal.getName()));
        return "training";
    }

    @GetMapping("/training/{id}/add-exercise")
    public String addExerciseToTraining(@PathVariable long id, Model model) {
        model.addAttribute("exerciseList", exerciseService.getAllExercises());
        model.addAttribute("training", trainingService.getTrainingById(id));
        return "add_exercise_to_training";
    }
}
