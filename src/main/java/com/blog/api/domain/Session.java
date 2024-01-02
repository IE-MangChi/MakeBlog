package com.blog.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // MyBatis로 쿼리넣고있어서 이거 해줄필요없긴한데... JPA나중에 써야징...
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    @ManyToOne
    private Users users;

    @Builder
    public Session(Users users) {
        this.accessToken = UUID.randomUUID().toString();
        this.users = users;
    }
}
