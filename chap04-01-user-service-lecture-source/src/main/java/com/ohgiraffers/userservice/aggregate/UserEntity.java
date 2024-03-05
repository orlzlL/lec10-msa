package com.ohgiraffers.userservice.aggregate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //메모. UserDTO의 userId => 시리얼 넘버를 Mapper가 여기다가 넣으려고 함 (STANDARD상태를 STRICT로 바꿔주어야함)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String email;

    @Column(nullable = false, length = 21)
    private String name;

    @Column(nullable = false, unique = true)
    private String userId;


    @Column(nullable = false, unique = true)
    private String encryptedPwd;

}
