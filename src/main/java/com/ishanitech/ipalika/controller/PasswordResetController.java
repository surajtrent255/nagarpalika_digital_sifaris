package com.ishanitech.ipalika.controller;

import com.ishanitech.ipalika.dto.Response;
import com.ishanitech.ipalika.dto.ResponseSec;
import com.ishanitech.ipalika.exception.model.UserRegistrationException;
import com.ishanitech.ipalika.service.UserService;
import com.ishanitech.ipalika.service.VerificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController//            return new ResponseSec<>(emailExists);

@RequestMapping("/forgot-password")
public class PasswordResetController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    public PasswordResetController(UserService userService, VerificationTokenService verificationTokenService){
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }
    @GetMapping("/checkDuplicateEmail")
    public ResponseSec<Boolean> checkDuplicateEmail(@RequestParam("email") String email) {
        boolean emailExists = userService.checkEmail(email);
        if (emailExists) {

            return ResponseSec.ok(emailExists, HttpStatus.OK.value(), "Email Already Exists In Database!");
        } else {
            return ResponseSec.ok(emailExists, HttpStatus.OK.value(),
                    "Email not found in database. You can use this email.");
        }
    }

    @GetMapping("/reset-user")
    ResponseSec<?> getUserByEmail(@RequestParam("email") String email){
        String status = userService.resetPassword(email);
        return ResponseSec.ok(status, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @GetMapping("/pw-reset-token-verify")
    public ResponseSec<?> verifyResetToken(@RequestParam("token") String token){
        Integer userId = verificationTokenService.resetPasswordTokenVerification(token);
        if (userId == null) {
            throw new UserRegistrationException("Couldn't reset password cause of invalid token");
        } else {
            return ResponseSec.ok(userId, HttpStatus.OK.value(),
                    HttpStatus.OK.name());
        }
    }

    @PostMapping("/update-password/{userId}")
    public ResponseSec<?> updateUserPassword(@RequestBody String password,
                                          @PathVariable("userId") Integer userId){
        userService.updateUserPassword(userId, password);
        return ResponseSec.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
    }


}
