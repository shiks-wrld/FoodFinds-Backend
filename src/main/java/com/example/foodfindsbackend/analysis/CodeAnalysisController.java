package com.example.foodfindsbackend.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analysis")
public class CodeAnalysisController {
    private final OpenAIService openAIService;

    @Autowired
    public CodeAnalysisController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping
    public String analyzeCode(@RequestBody String code) {
        return openAIService.analyzeCode(code);
    }
}
