package org.nano.song.service.coverSinger;

import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.BindCoverSingerBean;

/**
 * 绑定翻唱歌手服务接口类
 */
public interface BindCoverSingerService {

    /**
     * 绑定翻唱歌手
     *
     * @param bindCoverSingerBean 绑定翻唱歌手参数
     * @throws ResourceNotFoundException  资源未找到
     * @throws BindRelationExistException 绑定关系已存在
     */
    void bindCoverSinger(BindCoverSingerBean bindCoverSingerBean)
            throws ResourceNotFoundException, BindRelationExistException;
}
