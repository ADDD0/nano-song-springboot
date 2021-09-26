package org.nano.song.info.bean.coverSinger;

import lombok.Data;
import org.nano.song.domain.repository.entity.CoverSinger;

import java.util.List;

/**
 * 返回翻唱歌手参数
 */
@Data
public class ReturnCoverSingerBean {
    // 翻唱歌手列表
    private List<CoverSinger> coverSingerList;
}
