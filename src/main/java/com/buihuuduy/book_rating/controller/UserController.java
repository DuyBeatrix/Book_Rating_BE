package com.buihuuduy.book_rating.controller;

import com.buihuuduy.book_rating.DTO.ApiResponse;
import com.buihuuduy.book_rating.DTO.request.UserEntityRequest;
import com.buihuuduy.book_rating.DTO.response.AccountResponse;
import com.buihuuduy.book_rating.DTO.response.UserDetailResponse;
import com.buihuuduy.book_rating.exception.SuccessCode;
import com.buihuuduy.book_rating.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update/{userId}")
    public ApiResponse<?> registerUser(@RequestBody @Valid UserEntityRequest user, @PathVariable int userId) {
        userService.updateUser(userId, user);
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage(SuccessCode.UPDATE_SUCCESSFULLY.getMessage());
        return apiResponse;
    }

    @GetMapping("/detail/{userId}")
    public ApiResponse<UserDetailResponse> getUserDetailInfo(@PathVariable int userId) {
        ApiResponse<UserDetailResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.getUserDetailInfo(userId));
        return apiResponse;
    }

    @GetMapping("/following-account/{userId}")
    public ApiResponse<List<AccountResponse>> getFollowingAccountByUser(@PathVariable int userId) {
        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.getFollowingAccountByUser(userId));
        return apiResponse;
    }

    @GetMapping("/follower-account/{userId}")
    public ApiResponse<List<AccountResponse>> getFollowerAccountByUser(@PathVariable int userId) {
        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.getFollowerAccountByUser(userId));
        return apiResponse;
    }

    @PostMapping("/follow-account/{followedId}")
    public ApiResponse<?> followAccount(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer followedId) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Lấy token từ header
            userService.followUser(token, followedId);
            apiResponse.setCode(200);
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }

    @PostMapping("/unfollow-account/{unfollowedId}")
    public ApiResponse<?> unFollowAccount(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer unfollowedId) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Lấy token từ header
            userService.unfollowUser(token, unfollowedId);
            apiResponse.setCode(200);
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }

    @PostMapping("/mark-favorite-book/{bookId}")
    public ApiResponse<?> markAsFavorite(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer bookId) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Lấy token từ header
            userService.markAsFavorite(token, bookId);
            apiResponse.setCode(200);
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }

    @GetMapping("/following-account-by-token")
    public ApiResponse<List<AccountResponse>> getFollowingAccountByToken(@RequestHeader("Authorization") String authorizationHeader) {
        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            apiResponse.setResult(userService.getFollowingAccountByToken(token));
            apiResponse.setCode(200);
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }

    @GetMapping("/follower-account-by-token")
    public ApiResponse<List<AccountResponse>> getFollowerAccountByToken(@RequestHeader("Authorization") String authorizationHeader) {
        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            apiResponse.setResult(userService.getFollowerAccountByToken(token));
            apiResponse.setCode(200);
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }

    @GetMapping("/detail-by-token")
    public ApiResponse<UserDetailResponse> getUserDetailInfoByToken(@RequestHeader("Authorization") String authorizationHeader) {
        ApiResponse<UserDetailResponse> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            apiResponse.setResult(userService.getUserDetailInfoByToken(token));
            apiResponse.setCode(200);
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }
}
