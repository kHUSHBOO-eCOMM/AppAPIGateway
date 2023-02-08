package com.app.accout.management.service.service.impl;

import com.app.accout.management.service.data.AlbumServiceClient;
import com.app.accout.management.service.data.UserEntity;
import com.app.accout.management.service.dto.UserDto;
import com.app.accout.management.service.model.AlbumResponseModel;
import com.app.accout.management.service.repo.UserRepository;
import com.app.accout.management.service.service.UserService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    //    private RestTemplate restTemplate;
    private AlbumServiceClient albumServiceClient;


    private Environment environment;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
//                           RestTemplate restTemplate,
                           AlbumServiceClient albumServiceClient,
                           Environment environment) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.restTemplate = restTemplate;
        this.albumServiceClient = albumServiceClient;
        this.environment = environment;
    }

    @Override
    public UserDto create(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public UserDto getUserDetailByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found : " + userId);
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        //Microservice communication using restClient
       /* String albumUrl = String.format(environment.getProperty("album.url"), userId);
        ResponseEntity<List<AlbumResponseModel>> albumResponseModel = restTemplate.exchange(albumUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AlbumResponseModel>>() {
                });
*/
       /* List<AlbumResponseModel> albumList = null;
        try {
            albumList = albumServiceClient.getAlbums(userId);
        }catch (FeignException feignException){
            LOGGER.debug(feignException.getLocalizedMessage());
        }
*/
        List<AlbumResponseModel> albumList = albumServiceClient.getAlbums(userId);
        userDto.setAlbums(albumList);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
