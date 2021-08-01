package org.nano.song.info.request.song;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ImportSongRequest {
    // 文件名
    @NotBlank(message = Constant.ERR_MSG_FILE_NOT_UPLOAD)
    private String fileName;
    // 弹唱日期
    @NotBlank(message = Constant.ERR_MSG_PERFORMANCE_DATE_EMPTY)
    @Pattern(regexp = "^\\d{4}/\\d{1,2}/\\d{1,2}$", message = Constant.ERR_MSG_DATE_FOMAT_WRONG)
    private String performanceDate;
    // 歌曲文件内容
    @NotBlank(message = Constant.ERR_MSG_FILE_CONTENT_EMPTY)
    private String songFileContent;
    // 歌曲标题 用于查找所属歌曲集合
    @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
    private String songTitle;
}
