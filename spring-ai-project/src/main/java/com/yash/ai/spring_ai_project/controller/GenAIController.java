package com.yash.ai.spring_ai_project.controller;

import com.yash.ai.spring_ai_project.service.ChatService;
import com.yash.ai.spring_ai_project.service.ImageService;
import com.yash.ai.spring_ai_project.service.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class GenAIController {
    // https://www.youtube.com/watch?v=rgyx14CTC2w

    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt){
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

//    @GetMapping("generate-image")
//    public void generateImages(HttpServletResponse response, @RequestParam String prompt) throws IOException {
//        ImageResponse imageResponse = imageService.generateImage(prompt);
//        String imageUrl = imageResponse.getResult().getOutput().getUrl();
//        response.sendRedirect(imageUrl);
//    }

    @GetMapping("generate-image")
    public List<String> generateImages(
            HttpServletResponse response,
            @RequestParam String prompt,
            @RequestParam(defaultValue = "hd") String quality,
            @RequestParam(defaultValue = "1") int n,
            @RequestParam(defaultValue = "1024") int height,
            @RequestParam(defaultValue = "1024") int width
            ) throws IOException
    {

        ImageResponse imageResponse = imageService
                .generateImage(prompt,quality,n,height,width);
        List<String> imageUrls = imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .collect(Collectors.toList());

        return imageUrls;
    }

    @GetMapping("recipe-creator")
    public String createRecipe(
            @RequestParam String ingredients,
            @RequestParam(defaultValue = "any") String cuisine,
            @RequestParam(defaultValue = "") String dietaryRestrictions
            )
    {

        return recipeService.createRecipe(ingredients , cuisine , dietaryRestrictions);
    }

}

// gemini api key = AIzaSyDM4qQflAdvU2TPnxH9ylGx8hbmSCxYP7U