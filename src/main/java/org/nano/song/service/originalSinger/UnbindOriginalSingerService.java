package org.nano.song.service.originalSinger;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.UnbindOriginalSingerBean;

/**
 * 解绑原唱歌手服务接口类
 */
public interface UnbindOriginalSingerService {

    /**
     * 解绑原唱歌手
     *
     * @param unbindOriginalSingerBean 解绑原唱歌手参数
     * @throws ResourceNotFoundException 资源未找到
     */
    void unbindOriginalSinger(UnbindOriginalSingerBean unbindOriginalSingerBean) throws ResourceNotFoundException;
}
