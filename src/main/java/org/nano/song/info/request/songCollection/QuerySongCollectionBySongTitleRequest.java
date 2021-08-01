package org.nano.song.info.request.songCollection;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;

@Data
public class QuerySongCollectionBySongTitleRequest {
    // 歌曲标题
    @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
    private String songTitle;
}
