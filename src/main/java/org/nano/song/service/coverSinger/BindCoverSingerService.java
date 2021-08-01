package org.nano.song.service.coverSinger;

import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.BindCoverSingerBean;

public interface BindCoverSingerService {

    /**
     * 绑定翻唱歌手
     *
     * @param bindCoverSingerBean 绑定翻唱歌手参数
     * @throws ResourceNotFoundException  查找数据不存在
     * @throws BindRelationExistException 绑定关系已存在
     */
    void bindCoverSinger(BindCoverSingerBean bindCoverSingerBean)
            throws ResourceNotFoundException, BindRelationExistException;
}
