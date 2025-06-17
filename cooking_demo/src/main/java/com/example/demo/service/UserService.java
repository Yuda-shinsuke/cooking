package com.example.demo.service;



import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserUpdateResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public void insert(User user) {
	    if (userRepository.findByUserName(user.getUserName()).isPresent()) {
	        throw new IllegalArgumentException("ユーザー名が既に使用されています。");
	    }
		userRepository.save(user);
	}
	
    @Transactional
    public UserUpdateResponse updateUser(String userNamePath, UserUpdateRequest request) {
        User user = userRepository.getUserByUserName(userNamePath);
        if (user == null) {
            throw new RuntimeException("User not found: " + userNamePath);
        }
        
        // バージョンチェック
        if (!user.getUserVersion().equals(request.getUserVersion())) {
            throw new RuntimeException("Version mismatch: current=" + user.getUserVersion() + ", request=" + request.getUserVersion());
        }

        // 更新処理
        user.setUserName(request.getUserName());
        user.setUserPass(request.getUserPass());
        user.setUserVersion(user.getUserVersion() + 1);

        userRepository.save(user);

        return new UserUpdateResponse(user.getUserName(), user.getUserVersion());
    }

}
