package org.nano.song.info.response.songCollection;

import lombok.Data;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;

import java.util.ArrayList;

@Data
public class QuerySongCollectionResponseResource {
    // 歌曲集合
    private SongCollection songCollection;
    // 各歌曲集合原唱列表
    private ArrayList<Singer> singerArrayList;
}
