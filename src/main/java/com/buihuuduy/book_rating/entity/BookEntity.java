package com.buihuuduy.book_rating.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookEntity extends AuditingEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "book_name")
    String bookName;

    @Column(name = "book_description")
    String bookDescription;

    @Column(name = "book_image")
    String bookImage;

    @Column(name = "published_date")
    LocalDate publishedDate;

    @Column(name = "book_format")
    String bookFormat;

    @Column(name = "book_sale_link")
    String bookSaleLink;

    @Column(name = "language_id")
    Integer languageId;

    // User ID
    @Column(name = "book_author")
    String bookAuthor;
}