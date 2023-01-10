package com.book.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "work_category", indexes = { @Index(name = "booking_category_index", columnList = "name")})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PersistentWorkCategoryEntity extends AbstractEntity{

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
}
