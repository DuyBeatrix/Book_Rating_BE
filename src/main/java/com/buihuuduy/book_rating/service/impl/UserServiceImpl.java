package com.buihuuduy.book_rating.service.impl;

import com.buihuuduy.book_rating.DTO.request.UserEntityRequest;
import com.buihuuduy.book_rating.DTO.response.AccountResponse;
import com.buihuuduy.book_rating.DTO.response.UserDetailResponse;
import com.buihuuduy.book_rating.entity.FavoriteBookEntity;
import com.buihuuduy.book_rating.entity.FollowingAccountEntity;
import com.buihuuduy.book_rating.entity.UserEntity;
import com.buihuuduy.book_rating.exception.CustomException;
import com.buihuuduy.book_rating.exception.ErrorCode;
import com.buihuuduy.book_rating.mapper.UserMapper;
import com.buihuuduy.book_rating.repository.FavoriteBookRepository;
import com.buihuuduy.book_rating.repository.FollowingAccountRepository;
import com.buihuuduy.book_rating.repository.UserRepository;
import com.buihuuduy.book_rating.service.UserService;
import com.buihuuduy.book_rating.service.utils.CommonFunction;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final FollowingAccountRepository followingAccountRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FavoriteBookRepository favoriteBookRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, FollowingAccountRepository followingAccountRepository, FavoriteBookRepository favoriteBookRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.followingAccountRepository = followingAccountRepository;
        this.favoriteBookRepository = favoriteBookRepository;
    }

    @Override
    public void updateUser(Integer userId, UserEntityRequest userEntityRequest)
    {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(userEntity, userEntityRequest);
        userRepository.save(userEntity);
    }

    @Override
    public List<AccountResponse> getFollowingAccountByUser(Integer userId)
    {
        List<Integer> listFollowingAccountId = followingAccountRepository.findAllFollowingAccountIdsByYourAccountId(userId);
        List<AccountResponse> accountResponseList = new ArrayList<>();
        for(Integer followingAccountId : listFollowingAccountId)
        {
            AccountResponse accountResponse = new AccountResponse();
            UserEntity userEntity = userRepository.findById(followingAccountId).orElseThrow();

            accountResponse.setUserId(userEntity.getId());
            accountResponse.setUserName(userEntity.getUsername());
            accountResponse.setUserImage(userEntity.getUserImage());

            accountResponseList.add(accountResponse);
        }
        return accountResponseList;
    }

    @Override
    public List<AccountResponse> getFollowerAccountByUser(Integer userId)
    {
        List<Integer> listFollowerAccountId = followingAccountRepository.findAllFollowerAccountIdsByFollowingAccountId(userId);
        List<AccountResponse> accountResponseList = new ArrayList<>();
        for(Integer followingAccountId : listFollowerAccountId)
        {
            AccountResponse accountResponse = new AccountResponse();
            UserEntity userEntity = userRepository.findById(followingAccountId).orElseThrow();

            accountResponse.setUserId(userEntity.getId());
            accountResponse.setUserName(userEntity.getUsername());
            accountResponse.setUserImage(userEntity.getUserImage());

            accountResponseList.add(accountResponse);
        }
        return accountResponseList;
    }

    @Override
    // Xem thong tin ca nhan cua nguoi dung nao do
    public UserDetailResponse getUserDetailInfo(Integer userId)
    {
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        userDetailResponse.setUserName(userEntity.getUsername());
        userDetailResponse.setUserImage(userEntity.getUserImage());
        // Thieu so luong bai post
        userDetailResponse.setFollowingAccounts(getFollowingAccountByUser(userId).size());
        userDetailResponse.setFollowerAccounts(getFollowerAccountByUser(userId).size());
        return userDetailResponse;
    }

    // CHECK frontend send id or token
    @Override
    public void followUser(String token, Integer followedId)
    {
        // Get current user
        String currentUsername = CommonFunction.getUsernameFromToken(token);
        UserEntity currentUser = userRepository.findByUsername(currentUsername);
        if(currentUser == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        FollowingAccountEntity followingAccountEntity = new FollowingAccountEntity();
        // Nguoi dang di follow - nguoi duoc follow
        followingAccountEntity.setFollowerAccountId(currentUser.getId());
        followingAccountEntity.setFollowedAccountId(followedId);
        followingAccountRepository.save(followingAccountEntity);
    }

    @Override
    public void unfollowUser(String token, Integer unFollowedId)
    {
        // Get current user
        String currentUsername = CommonFunction.getUsernameFromToken(token);
        UserEntity currentUser = userRepository.findByUsername(currentUsername);
        if(currentUser == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        FollowingAccountEntity followingAccount = followingAccountRepository
                .findByFollowerAccountIdAndFollowedAccountId(currentUser.getId(), unFollowedId);
        followingAccount.setIsActive(false);
        followingAccountRepository.save(followingAccount);
    }

    @Override
    public void markAsFavorite(String token, Integer bookId)
    {
        FavoriteBookEntity favoriteBookEntity = new FavoriteBookEntity();
        favoriteBookEntity.setBookId(bookId);
        String currentUsername = CommonFunction.getUsernameFromToken(token);
        UserEntity currentUser = userRepository.findByUsername(currentUsername);
        if(currentUser == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        favoriteBookEntity.setUserId(currentUser.getId());
        favoriteBookRepository.save(favoriteBookEntity);
    }

    @Override
    public List<AccountResponse> getFollowingAccountByToken(String token) {
        String username = CommonFunction.getUsernameFromToken(token);
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        return getFollowingAccountByUser(userEntity.getId());
    }

    @Override
    public List<AccountResponse> getFollowerAccountByToken(String token) {
        String username = CommonFunction.getUsernameFromToken(token);
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        return getFollowerAccountByUser(userEntity.getId());
    }

    @Override
    public UserDetailResponse getUserDetailInfoByToken(String token) {
        String username = CommonFunction.getUsernameFromToken(token);
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        return getUserDetailInfo(userEntity.getId());
    }
}
