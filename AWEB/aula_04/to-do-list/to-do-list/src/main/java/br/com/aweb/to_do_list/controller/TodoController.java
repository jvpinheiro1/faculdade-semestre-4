package br.com.aweb.to_do_list.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/todo")
public class TodoController {
    @GetMapping("/home")
    public ModelAndView home (){
        var modelAndView = new ModelAndView("home");
        modelAndView.addObject("professor", "André Roberto da Silva");

        var alunos = List.of(
            "Isaac newton",
            "Albert Einstein",
            "Maria Curie"
        );
        modelAndView.addObject("alunos", alunos);
        return modelAndView;
    }
}
