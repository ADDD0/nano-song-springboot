package org.nano.song.info.bean.coverSinger;

import lombok.Data;

/**
 * 查询翻唱歌手参数
 */
@Data
public class QueryCoverSingerBean {
    // 歌曲id
    private Integer songId;
    // 歌手id
    private Integer singerId;
}
