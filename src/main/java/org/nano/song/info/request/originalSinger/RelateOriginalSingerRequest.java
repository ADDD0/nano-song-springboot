package org.nano.song.info.request.originalSinger;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;

@Data
public class RelateOriginalSingerRequest {
    // 歌手姓名
    @NotBlank(message = Constant.ERR_MSG_SINGER_NAME_EMPTY)
    private String singerName;
    // 歌曲标题
    @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
    private String songTitle;
}
