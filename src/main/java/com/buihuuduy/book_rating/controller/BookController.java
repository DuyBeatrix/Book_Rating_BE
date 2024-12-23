package com.buihuuduy.book_rating.controller;

import com.buihuuduy.book_rating.DTO.ApiResponse;
import com.buihuuduy.book_rating.DTO.PageFilterInput;
import com.buihuuduy.book_rating.DTO.PageResponse;
import com.buihuuduy.book_rating.DTO.request.BookRequestDTO;
import com.buihuuduy.book_rating.DTO.request.ExplorePageFilter;
import com.buihuuduy.book_rating.DTO.response.BookDetailPageResponse;
import com.buihuuduy.book_rating.DTO.response.BookResponse;
import com.buihuuduy.book_rating.service.BookService;
import com.buihuuduy.book_rating.service.utils.CommonFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController
{
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/get-explore-page")
    public PageResponse<List<BookResponse>> getAllPublicDepartment(@RequestBody PageFilterInput<ExplorePageFilter> input) {
        Pageable pageable = input.getPageSize() == 0
                ? PageRequest.of(0, Integer.MAX_VALUE) // Sử dụng PageRequest với size rất lớn khi không phân trang
                : PageRequest.of(input.getPageNumber(), input.getPageSize());
        Page<BookResponse> result = bookService.getBooksInExplorePage(input, pageable);
        return new PageResponse<List<BookResponse>>().result(result.getContent()).dataCount(result.getTotalElements());
    }

    @GetMapping("/{bookId}")
    public ApiResponse<BookDetailPageResponse> getBookById(@PathVariable("bookId") Integer bookId)
    {
        ApiResponse<BookDetailPageResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(bookService.getBookDetailById(bookId));
        return apiResponse;
    }

    @GetMapping("/get-authors/{input}")
    public ApiResponse<List<String>> getAuthorsRecommendation(@PathVariable String input)
    {
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(bookService.getAuthorsRecommendation(input));
        return apiResponse;
    }

    @GetMapping("/get-posted-by-username/{username}")
    public ApiResponse<List<BookResponse>> getPostedBookByUsername(@PathVariable String username)
    {
        ApiResponse<List<BookResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(bookService.getPostedBookByUsername(username));
        return apiResponse;
    }

    @GetMapping("/search-common/{text}")
    public ApiResponse<List<BookResponse>> searchBookByText(@PathVariable String text)
    {
        ApiResponse<List<BookResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(bookService.searchBookByText(text));
        return apiResponse;
    }

    @GetMapping("/get-book-by-userId/{userId}")
    public ApiResponse<List<BookResponse>> getFavoriteBookByUserId(@PathVariable Integer userId)
    {
        ApiResponse<List<BookResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(bookService.getFavoriteBookByUserId(userId));
        return apiResponse;
    }

    @PostMapping("/up-book")
    public ApiResponse<?> upBook(@RequestHeader("Authorization") String authorizationHeader, @RequestBody BookRequestDTO bookRequestDTO)
    {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Lấy token từ header
            bookService.createBook(token, bookRequestDTO);
            apiResponse.setCode(200);
            apiResponse.setMessage("Đã đăng bài vui lòng chờ duyệt");
        } else {
            apiResponse.setCode(401);
            apiResponse.setMessage("Authorization header is invalid");
        }
        return apiResponse;
    }
}
