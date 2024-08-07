package hey.io.hey.domain.report.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportRequest {

    private List<String> type;

    private String content;

    public ReportRequest(List<String> type, String content) {
        this.type = type;
        this.content = content;
    }

}
