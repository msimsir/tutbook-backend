package com.tutbook.base.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutbook.base.shared.RestResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  @ResponseBody
  public ResponseEntity<RestResponse<Object, Object>> signUp(@RequestBody SignUpRequest request) {
    try {
      return ResponseEntity.ok(RestResponse.builder().data(authenticationService.signUp(request)).build());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RestResponse.builder().message(e.getMessage()).build());
    }
  }

  @PostMapping("/signin")
  @ResponseBody
  public ResponseEntity<RestResponse<Object, Object>> signIn(@RequestBody SignInRequest request) {
    try {
      return ResponseEntity.ok(RestResponse.builder().data(authenticationService.signIn(request)).build());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RestResponse.builder().message(e.getMessage()).build());
    }
  }
}
