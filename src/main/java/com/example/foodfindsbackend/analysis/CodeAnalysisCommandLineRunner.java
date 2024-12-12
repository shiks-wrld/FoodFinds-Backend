package com.example.foodfindsbackend.analysis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CodeAnalysisCommandLineRunner implements CommandLineRunner {

    private final OpenAIService openAIService;

    public CodeAnalysisCommandLineRunner(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please provide the path to the code file as an argument.");
            return;
        }

        String filePath = args[0];
        String codeSnippet = readCodeFromFile(filePath);

        if (codeSnippet != null) {
            String analysisResult = openAIService.analyzeCode(codeSnippet);
            System.out.println("Code Analysis Result: ");
            System.out.println(analysisResult);
        }
    }

    private String readCodeFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readString(path);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
