package com.genesyslab.user.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.genesyslab.user.api.controller.UserConflictException;
import com.genesyslab.user.api.data.UserEntity;
import com.genesyslab.user.api.data.UserRepository;
import com.genesyslab.user.api.model.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDto) throws UserConflictException {
		
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
		
		if(user.isPresent()) {
			throw new UserConflictException("Email Already Registered..");
		}
		userDto.setUserId(UUID.randomUUID().toString());
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setLastLogin("Not Yet Login");
		
		userRepository.save(userEntity);
		
		UserDto createdUser = modelMapper.map(userEntity, UserDto.class);
		return createdUser;
		
		
	}
	
	@Override
	public UserDto getUserByUserId(String userId) {
		
		Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findByUserId(userId));
		
		if(!userEntity.isPresent())
			throw new UsernameNotFoundException("User not found "+userId);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userEntity.get(), UserDto.class);
		
		return userDto;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(username));
		
		if(!user.isPresent())
			throw new UsernameNotFoundException(username);
		
		return new User(user.get().getEmail(),user.get().getEncryptedPassword(),true,true,true,true, new ArrayList<>());
	}



	@Override
	public UserDto loadUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity==null)
			throw new UsernameNotFoundException(email);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}
	
	public UserDto updateUser(UserDto userDto)
    {
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = null;
		
		if(user.isPresent()) {
			userEntity = user.get();
			userEntity.setUserId(user.get().getUserId());
			userEntity.setLastLogin(user.get().getLastLogin());
			userEntity.setFirstname(userDto.getFirstname());
			userEntity.setLastname(userDto.getLastname());
			userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		}
		else
		{
			userDto.setUserId(UUID.randomUUID().toString());

			userEntity = modelMapper.map(userDto, UserEntity.class);
			userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			userEntity.setLastLogin("Not Yet Login");
		}
		
		userRepository.save(userEntity);
		
		UserDto createdUser = modelMapper.map(userEntity, UserDto.class);
		return createdUser;
		
    }

	@Override
	public void updateLastLogin(String email) {
		
		System.out.println("UserId :: "+email);
		userRepository.updateLastLogin(LocalDateTime.now().toString(), email);
		
	}

	@Override
	public List<UserDto> getUsers() {
		
		List<UserEntity> userEntities = new ArrayList<>();
		userRepository.findAll().forEach(userEntities::add);;
		ModelMapper modelMapper = new ModelMapper();
		List<UserDto> userDtos = userEntities.stream().map(userEntity->modelMapper.map(userEntity, UserDto.class))
				.collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUsers() {
		userRepository.deleteAll();		
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.delete(userRepository.findByUserId(userId));
		
	} 

}
