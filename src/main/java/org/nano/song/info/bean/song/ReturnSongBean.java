package org.nano.song.info.bean.song;

import lombok.Data;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;

import java.util.List;

/**
 * 返回歌曲参数
 */
@Data
public class ReturnSongBean {
    // 歌曲
    private Song song;
    // 对应歌曲翻唱歌手列表
    private List<Singer> singerList;
}
