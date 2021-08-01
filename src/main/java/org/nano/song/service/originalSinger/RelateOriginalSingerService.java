package org.nano.song.service.originalSinger;

import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.RelateOriginalSingerBean;

public interface RelateOriginalSingerService {

    /**
     * 关联原唱歌手
     *
     * @param relateOriginalSingerBean 关联原唱歌手参数
     * @throws ResourceNotFoundException  查找数据不存在
     * @throws BindRelationExistException 绑定关系已存在
     */
    void relateOriginalSinger(RelateOriginalSingerBean relateOriginalSingerBean)
            throws ResourceNotFoundException, BindRelationExistException;
}
