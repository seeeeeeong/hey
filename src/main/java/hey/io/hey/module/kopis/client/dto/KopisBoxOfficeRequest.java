package hey.io.hey.module.kopis.client.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KopisBoxOfficeRequest {

    private String ststype;
    private String date;
    private String catecode;

}
