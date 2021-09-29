package org.nano.song.service.coverSinger;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.UnbindCoverSingerBean;

/**
 * 解绑翻唱歌手服务接口类
 */
public interface UnbindCoverSingerService {

    /**
     * 解绑翻唱歌手
     *
     * @param unbindCoverSingerBean 解绑翻唱歌手参数
     * @throws ResourceNotFoundException 资源未找到
     */
    void unbindCoverSinger(UnbindCoverSingerBean unbindCoverSingerBean) throws ResourceNotFoundException;
}
