package org.nano.song.service.originalSinger;

import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.RelateOriginalSingerBean;

/**
 * 关联原唱歌手服务接口类
 */
public interface RelateOriginalSingerService {

    /**
     * 关联原唱歌手
     *
     * @param relateOriginalSingerBean 关联原唱歌手参数
     * @throws ResourceNotFoundException  资源未找到
     * @throws BindRelationExistException 绑定关系已存在
     */
    void relateOriginalSinger(RelateOriginalSingerBean relateOriginalSingerBean)
            throws ResourceNotFoundException, BindRelationExistException;
}
