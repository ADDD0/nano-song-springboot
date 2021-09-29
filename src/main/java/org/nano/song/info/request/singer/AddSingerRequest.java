package org.nano.song.info.request.singer;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;

/**
 * 新增歌手请求
 */
@Data
public class AddSingerRequest {
    // 歌手姓名
    @NotBlank(message = Constant.ERR_MSG_SINGER_NAME_EMPTY)
    private String singerName;
}
