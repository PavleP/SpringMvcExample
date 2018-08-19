package com.learn.serviceimpl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.learn.service.MyService;

@Service
@Profile("dev")
public class MyServiceDevImpl implements MyService {

  @Override
  public String getString() {
    return "This is dev string.";
  }
}
