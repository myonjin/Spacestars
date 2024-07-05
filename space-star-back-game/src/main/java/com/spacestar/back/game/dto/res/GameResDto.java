package com.spacestar.back.game.dto.res;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResDto {
    private int index;
    private long gameId;
    private String gameNameKor;
    private String gameName;
    private String gameImage;
    private String gameLogoImage;

    public GameResDto(String gameName, String gameImage, String gameNameKor,String gameLogoImage, Long gameId) {
        this.gameId = gameId;
        this.gameNameKor = gameNameKor;
        this.gameName = gameName;
        this.gameImage = gameImage;
        this.gameLogoImage = gameLogoImage;
    }
}