package com.example.demo.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserUpdateResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	// ログイン処理
    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUserNameAndUserPass(
            loginRequest.getUserName(),
            loginRequest.getUserPass()
        );

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            LoginResponse response = new LoginResponse();
            response.setUserId(user.getUserId());  // IDだけセット
            return ResponseEntity.ok(response);  // status 200 と共に返す
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
	public static class LoginRequest {
	    private String userName;
	    private String userPass;

	    // getter, setter
	    public String getUserName() { return userName; }
	    public void setUserName(String userName) { this.userName = userName; }

	    public String getUserPass() { return userPass; }
	    public void setUserPass(String userPass) { this.userPass = userPass; }
	}
	
    public static class LoginResponse {
        private Integer userId;

        public Integer getUserId() {
            return userId;
        }
        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
	
	// ユーザー新規登録リクエストに対する処理
    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<String> insert(@RequestBody User user) {
        user.setUserId(null);              // 自動採番に任せる
        if (user.getUserVersion() == null) {
            user.setUserVersion(0);        // 明示的に初期値をセット
        }

        try {
            userService.insert(user);
            return ResponseEntity.ok("ユーザー登録が完了しました");
        } catch (IllegalArgumentException e) {
            // 重複ユーザー名の場合は 409 CONFLICT で返す
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // それ以外の例外は 500 で返す
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登録中にエラーが発生しました");
        }
        
    }
    
    //更新
    @PostMapping("/{userName}/user")
    @CrossOrigin
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable String userName,
            @RequestBody UserUpdateRequest request) {
        UserUpdateResponse response = userService.updateUser(userName, request);
        return ResponseEntity.ok(response);
    }

}


