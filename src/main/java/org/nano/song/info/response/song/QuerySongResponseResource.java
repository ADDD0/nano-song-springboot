package org.nano.song.info.response.song;

import lombok.Data;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;

import java.util.ArrayList;

@Data
public class QuerySongResponseResource {
    // 歌曲
    private Song song;
    // 各歌曲翻唱列表
    private ArrayList<Singer> singerArrayList;
}
