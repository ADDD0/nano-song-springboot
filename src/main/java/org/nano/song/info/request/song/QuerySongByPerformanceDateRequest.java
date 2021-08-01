package org.nano.song.info.request.song;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class QuerySongByPerformanceDateRequest {
    // 弹唱日期
    @NotBlank(message = Constant.ERR_MSG_PERFORMANCE_DATE_EMPTY)
    @Pattern(regexp = "^\\d{4}/\\d{1,2}/\\d{1,2}$", message = Constant.ERR_MSG_DATE_FOMAT_WRONG)
    private String performanceDate;
}
