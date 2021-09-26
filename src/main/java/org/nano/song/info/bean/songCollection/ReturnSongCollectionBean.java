package org.nano.song.info.bean.songCollection;

import lombok.Data;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;

import java.util.List;

/**
 * 返回歌曲集合参数
 */
@Data
public class ReturnSongCollectionBean {
    // 歌曲集合
    private SongCollection songCollection;
    // 对应歌曲集合原唱歌手列表
    private List<Singer> singerList;
}
