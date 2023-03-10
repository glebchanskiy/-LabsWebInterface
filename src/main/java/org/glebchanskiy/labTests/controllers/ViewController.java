package org.glebchanskiy.labTests.controllers;

import org.glebchanskiy.labTests.models.Message;
import org.glebchanskiy.labTests.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class ViewController {

    private final TaskService taskService;

    @Autowired
    ViewController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String view(@ModelAttribute("message") Message message) {
        return "main";
    }

    @PostMapping()
    public String input(@ModelAttribute("message") Message message, Model model) {
        model.addAttribute("answer", taskService.generateAnswer(message));
        return "main";
    }

}
