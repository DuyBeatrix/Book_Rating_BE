package com.buihuuduy.book_rating.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AuditingEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "user_DOB")
    LocalDate userDOB;

    @Column(name = "user_address")
    String userAddress;

    @Column(name = "user_gender")
    boolean userGender;

    @Column(name = "user_phone")
    String userPhone;

    @Column(name = "user_email")
    String userEmail;

    @Column(name = "user_link")
    String userLink;

    @Column(name = "is_author")
    boolean isAuthor;
}