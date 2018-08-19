package com.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.learn.service.MyService;

@Controller
public class HomeController {

  @Autowired
  private MyService myService;

  @RequestMapping(value = "/")
  public ModelAndView home() {
    ModelAndView modelAndView = new ModelAndView("test");

    modelAndView.addObject("text", myService.getString());
    return modelAndView;
  }

}
