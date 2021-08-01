package org.nano.song.info.request.songCollection;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;

@Data
public class QuerySongCollectionBySingerNameRequest {
    // 歌手姓名
    @NotBlank(message = Constant.ERR_MSG_SINGER_NAME_EMPTY)
    private String singerName;
}
