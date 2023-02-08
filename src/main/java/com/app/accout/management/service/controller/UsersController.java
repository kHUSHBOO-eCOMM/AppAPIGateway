package com.app.accout.management.service.controller;

import com.app.accout.management.service.dto.UserDto;
import com.app.accout.management.service.model.CreateUserResponseModel;
import com.app.accout.management.service.model.CreateUsersRequestModel;
import com.app.accout.management.service.model.UserResponseModel;
import com.app.accout.management.service.service.UserService;
import net.bytebuddy.matcher.StringMatcher;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {
    private UserService userService;
    @Autowired
    private Environment environment;

    @Autowired
    public UsersController(UserService userService, Environment environment) {

        this.userService = userService;
        this.environment = environment;
    }

    @GetMapping("/status")
    public String getUserStatus() {

        return "working on port : " + environment.getProperty("server.port") + " with token : " + environment.getProperty("token.secret");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUsers(@RequestBody CreateUsersRequestModel createUsersRequestModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(createUsersRequestModel, UserDto.class);
        UserDto serviceResponse = userService.create(userDto);
        CreateUserResponseModel createUserResponseModel = modelMapper.map(serviceResponse, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> getUsers(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
    }
}
