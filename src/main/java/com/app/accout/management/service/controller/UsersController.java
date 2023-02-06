package com.app.accout.management.service.controller;

import com.app.accout.management.service.dto.UserDto;
import com.app.accout.management.service.model.UserResponseModel;
import com.app.accout.management.service.model.UsersRequestModel;
import com.app.accout.management.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;


@RestController
@RequestMapping("/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status")
    public String getUserStatus() {
        return "working";
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> createUsers(@RequestBody UsersRequestModel usersRequestModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(usersRequestModel, UserDto.class);
        UserDto serviceResponse = userService.create(userDto);
        UserResponseModel userResponseModel = modelMapper.map(serviceResponse, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);
    }
}
