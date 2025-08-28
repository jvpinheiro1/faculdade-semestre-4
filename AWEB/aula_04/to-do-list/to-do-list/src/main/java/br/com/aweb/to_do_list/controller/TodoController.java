package br.com.aweb.to_do_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.to_do_list.repository.TodoRepository;


@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    
    // @GetMapping("/home")
    // public ModelAndView home (){
    //     var modelAndView = new ModelAndView("home");
    //     modelAndView.addObject("professor", "André Roberto da Silva");

    //     var alunos = List.of(
    //         "Isaac newton",
    //         "Albert Einstein",
    //         "Maria Curie"
    //     );
    //     modelAndView.addObject("alunos", alunos);
    //     modelAndView.addObject("ehVerdade", true);
    //     return modelAndView;
    // }
    @GetMapping
    public ModelAndView list() {
        var modelAndView = new ModelAndView("list");
        modelAndView.addObject("todos", todoRepository.findAll());
        return modelAndView;
    }
    
}
