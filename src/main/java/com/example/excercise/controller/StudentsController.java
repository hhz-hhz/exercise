package com.example.excercise.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StudentsController {

  @GetMapping("/hellos")
  public String hellos(){
    return "hello spring";
  }

  @PostMapping
  public ResponseEntity<String> createStudent(@RequestBody String string){
      return ResponseEntity.created(URI.create(string)).body(string);
  }
}
