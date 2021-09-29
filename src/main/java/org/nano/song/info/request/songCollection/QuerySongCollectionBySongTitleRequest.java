package org.nano.song.info.request.songCollection;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;

/**
 * 查询歌曲集合请求
 */
@Data
public class QuerySongCollectionBySongTitleRequest {
    // 歌曲标题
    @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
    private String songTitle;
}
