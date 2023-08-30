package com.telebet.telebetauthservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.telebet.telebetauthservice.entities.UserVO;
import com.telebet.telebetauthservice.model.Result;

@FeignClient("user-service")
public interface UserServiceProxy {
	
	@GetMapping("/users/getUserDetails")
	public UserVO getUser(@RequestHeader(value = "userName") String userName, @RequestHeader(value="password") String password); 
	
	@PostMapping("/users/save")
	public ResponseEntity<UserVO> save(@RequestBody UserVO userVo);
	
	@GetMapping("/validateUser")
	public Result validateLoginDetails(@RequestBody UserVO userVo);

}
