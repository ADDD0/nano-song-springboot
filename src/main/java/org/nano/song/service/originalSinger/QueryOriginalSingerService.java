package org.nano.song.service.originalSinger;

import org.nano.song.info.bean.originalSinger.QueryOriginalSingerBean;
import org.nano.song.info.bean.originalSinger.ReturnOriginalSingerBean;

public interface QueryOriginalSingerService {

    /**
     * 查询原唱歌手
     *
     * @param queryOriginalSingerBean 查询原唱歌手参数
     * @return 返回原唱歌手参数
     */
    ReturnOriginalSingerBean queryOriginalSinger(QueryOriginalSingerBean queryOriginalSingerBean);
}
