package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TokensDto {
    private String accessToken;
    private String refreshToken;

}
