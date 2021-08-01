package org.nano.song.info.request.songCollection;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;

@Data
public class AddSongCollectionRequest {
    // 歌曲标题
    @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
    private String songTitle;
    // 中文标题
    private String chineseTitle;
    // 英文标题
    private String englishTitle;
}
