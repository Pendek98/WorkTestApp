package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.json.JsonParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.ui.Model;

import com.example.demo.Repository;
import com.example.demo.Branch;
import com.example.demo.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RepositoriesController {
    private static final String template = "https://api.github.com/users/%s/repos";

    @PostMapping(value = "/repositories", produces = {"application/json","application/xml"})
    public ResponseEntity<Object> GetRepositories(@RequestBody User user, @RequestHeader Map<String,String> headers) {
        
        
        if(!headers.get("accept").equals("application/json"))return generateResponse("Wrong type", HttpStatus.NOT_ACCEPTABLE);

        List<Repository> result = downloadRepositories(user.getUsername());
        if (result.isEmpty()) {
            return generateResponse("Not found", HttpStatus.NOT_FOUND);
        }
        result.forEach(repo -> {
            repo.setBranch(GetBranches(user.getUsername(), repo.getName()));
        });
        return new ResponseEntity<Object>(result.stream().filter(repo -> !repo.isFork()).toList(), HttpStatus.OK);
    }

    private ResponseEntity<Object> generateResponse(String message, HttpStatusCode status){
        Map<String, String> error = new HashMap<String,String>();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        error.put("Message", message);
        error.put("Status", status+"");
        return new ResponseEntity<Object>(error,responseHeaders,status);
    }

    private List<Repository> downloadRepositories(String username) {
        List<Repository> result = new ArrayList<Repository>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(template, username);
            ResponseEntity<List<Repository>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Repository>>() {
                    });
            result = responseEntity.getBody();
        } catch (Exception e) {
        }

        return result;
    }

    public List<Branch> GetBranches(String username, String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.github.com/repos/%s/%s/branches", username, name);
        ResponseEntity<List<Branch>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Branch>>() {
                });
        List<Branch> result = responseEntity.getBody();
        return result;

    }

}
