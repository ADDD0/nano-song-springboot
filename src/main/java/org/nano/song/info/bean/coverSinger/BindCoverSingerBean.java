package org.nano.song.info.bean.coverSinger;

import lombok.Data;

/**
 * 绑定翻唱歌手参数
 */
@Data
public class BindCoverSingerBean {
    // 歌手id
    private int singerId;
    // 歌曲id
    private int songId;
}
