package org.nano.song.info.bean.originalSinger;

import lombok.Data;
import org.nano.song.domain.repository.entity.OriginalSinger;

import java.util.ArrayList;

/**
 * 返回原唱歌手参数
 */
@Data
public class ReturnOriginalSingerBean {
    // 原唱歌手列表
    private ArrayList<OriginalSinger> originalSingerArrayList;
}
