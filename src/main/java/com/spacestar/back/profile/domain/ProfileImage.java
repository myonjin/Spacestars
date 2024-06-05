package com.spacestar.back.profile.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String uuid;

    @NotNull
    @Column(length = 255)
    private String profileImageUrl;

    @NotNull
    private boolean main;

    private int idx;

    @Builder
    public ProfileImage(Long id, String uuid, String profileImageUrl, boolean main, int idx) {
        this.id = id;
        this.uuid = uuid;
        this.profileImageUrl = profileImageUrl;
        this.main = main;
        this.idx = idx;
    }

    @Builder
    public ProfileImage(String uuid, String profileImageUrl, boolean main, int idx) {
        this.uuid = uuid;
        this.profileImageUrl = profileImageUrl;
        this.main = main;
        this.idx = idx;
    }
}
