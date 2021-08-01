package org.nano.song.info.bean.song;

import lombok.Data;

/**
 * 导入歌曲参数
 */
@Data
public class ImportSongBean {
    // 翻唱歌手姓名
    private String coverSingerName;
    // 文件名
    private String fileName;
    // 弹唱日期
    private String performanceDate;
    // 歌曲文件内容
    private String songFileContent;
    // 歌曲标题
    private String songTitle;
}
