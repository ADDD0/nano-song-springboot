package org.nano.song.service.originalSinger;

import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.BindOriginalSingerBean;

public interface BindOriginalSingerService {

    /**
     * 绑定原唱歌手
     *
     * @param bindOriginalSingerBean 绑定原唱歌手参数
     * @throws ResourceNotFoundException  查找数据不存在
     * @throws BindRelationExistException 绑定关系已存在
     */
    void bindOriginalSinger(BindOriginalSingerBean bindOriginalSingerBean)
            throws ResourceNotFoundException, BindRelationExistException;
}
