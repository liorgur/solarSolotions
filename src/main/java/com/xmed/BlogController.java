package com.xmed;

import com.xmed.Objects.Responses.QuestionOptionsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogController {

    @RequestMapping("/")
    public String index() {
        return "Congratulations from BlogController.java";
    }

    @PostMapping("/blog/{id}")
    public ResponseEntity<QuestionOptionsResponse> methodName(@PathVariable String id)
    {
        return ResponseEntity.ok()
                .body(new QuestionOptionsResponse(1,true, "a"));
    }


    //@GetMapping("/blog/{id}")
    //public String methodName()
    //{
    //    return "1";
    //}

//    @GetMapping("/blog")
//    public String methodNamea()
//    {
//        return "1";
//    }
}