package org.nano.song.info.bean.songCollection;

import lombok.Data;

/**
 * 新增歌曲集合参数
 */
@Data
public class AddSongCollectionBean {
    // 歌曲标题
    private String songTitle;
    // 中文标题
    private String chineseTitle;
    // 英文标题
    private String englishTitle;
}
