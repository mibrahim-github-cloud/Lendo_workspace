package com.genesyslab.user.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genesyslab.user.api.model.CreateUserRequestModel;
import com.genesyslab.user.api.model.CreateUserResponseModel;
import com.genesyslab.user.api.model.FailureResponseModel;
import com.genesyslab.user.api.model.UserResponseModel;
import com.genesyslab.user.api.model.shared.UserDto;
import com.genesyslab.user.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequestModel userModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userModel, UserDto.class);

		UserDto createdUser;
		try {
			createdUser = userService.createUser(userDto);
			CreateUserResponseModel responseModel = modelMapper.map(createdUser, CreateUserResponseModel.class);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
		} catch (UserConflictException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new FailureResponseModel("Email already got registered.."));
		}

	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {

		UserDto userDto = userService.getUserByUserId(userId);

		UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);

	}

	@GetMapping
	public ResponseEntity<List<UserResponseModel>> getUsers() {

		List<UserDto> userDtos = userService.getUsers();
		ModelMapper modelMapper = new ModelMapper();

		List<UserResponseModel> returnValue = userDtos.stream()
				.map(userDto -> modelMapper.map(userDto, UserResponseModel.class)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);

	}

	@DeleteMapping
	public ResponseEntity<?> deleteUsers() {

		userService.deleteUsers();

		return ResponseEntity.status(HttpStatus.OK).body(new String("All records were deleted succesfully"));

	}

	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {

		userService.deleteUser(userId);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new String("User with ID " + userId + " is deleted succesfully"));
	}

	@PutMapping
	public ResponseEntity<CreateUserResponseModel> updateUser(@RequestBody @Valid CreateUserRequestModel userModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userModel, UserDto.class);

		UserDto updatedUser = userService.updateUser(userDto);

		CreateUserResponseModel responseModel = modelMapper.map(updatedUser, CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(responseModel);

	}

}
