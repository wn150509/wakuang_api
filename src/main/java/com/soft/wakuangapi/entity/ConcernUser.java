package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ConcernUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer concernId;

    private Integer userId;
    private Integer labelId;
}
