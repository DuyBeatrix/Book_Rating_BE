package com.buihuuduy.book_rating.repository;

import com.buihuuduy.book_rating.entity.FollowingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowAccountRepository extends JpaRepository<FollowingAccountEntity, Integer>
{
    @Query("SELECT f.followingAccountId FROM FollowingAccountEntity f WHERE f.followerAccountId = :yourAccountId")
    List<Integer> findAllFollowingAccountIdsByYourAccountId(@Param("yourAccountId") Integer yourAccountId);

    @Query("SELECT f.followerAccountId FROM FollowingAccountEntity f WHERE f.followingAccountId = :yourAccountId")
    List<Integer> findAllFollowerAccountIdsByFollowingAccountId(@Param("yourAccountId") Integer yourAccountId);
}