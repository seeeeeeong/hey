package hey.io.hey.kopis.client.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KopisPerformanceRequest {

    private String stdate;
    private String eddate;
    private int cpage;
    private int rows;
    private String shcate;

}
