package com.technoir.levelup.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    @LastModifiedDate  //какие клевые анноташки для даты есть у спринга :)
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
