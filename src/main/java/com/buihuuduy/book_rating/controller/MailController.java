package com.buihuuduy.book_rating.controller;

import com.buihuuduy.book_rating.DTO.ApiResponse;
import com.buihuuduy.book_rating.exception.SuccessCode;
import com.buihuuduy.book_rating.service.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController
{
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-code")
    public ApiResponse<?> sendVerificationCode(@RequestParam String email)
    {
        mailService.sendMail(email);
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(SuccessCode.EMAIL_SENT_SUCCESSFULLY.getMessage());
        apiResponse.setCode(200);
        return apiResponse;
    }

    @PostMapping("/verify-code")
    public ApiResponse<?> verifyCode(@RequestParam String email, @RequestParam String code)
    {
        mailService.verifyCode(email, code);
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        return apiResponse;
    }
}