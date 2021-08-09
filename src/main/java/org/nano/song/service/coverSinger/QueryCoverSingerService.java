package org.nano.song.service.coverSinger;

import org.nano.song.info.bean.coverSinger.QueryCoverSingerBean;
import org.nano.song.info.bean.coverSinger.ReturnCoverSingerBean;

public interface QueryCoverSingerService {

    /**
     * 查询翻唱歌手
     *
     * @param queryCoverSingerBean 查询翻唱歌手参数
     * @return 返回原唱歌手参数
     */
    ReturnCoverSingerBean queryCoverSinger(QueryCoverSingerBean queryCoverSingerBean);
}
