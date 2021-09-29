package org.nano.song.service.originalSinger;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.UnbindOriginalSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 解绑原唱歌手服务接口实现类
 */
@Service
@Transactional
public class UnbindOriginalSingerServiceImpl implements UnbindOriginalSingerService {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    /**
     * 解绑原唱歌手
     *
     * @param unbindOriginalSingerBean 解绑原唱歌手参数
     * @throws ResourceNotFoundException 资源未找到
     */
    @Override
    public void unbindOriginalSinger(UnbindOriginalSingerBean unbindOriginalSingerBean) throws ResourceNotFoundException {

        int songCollectionId = unbindOriginalSingerBean.getSongCollectionId();
        // 通过歌曲集合id查询所有原唱歌手
        List<OriginalSinger> originalSingerList = originalSingerRepository.findAllBySongCollectionId(songCollectionId)
                .orElse(Collections.emptyList());

        for (OriginalSinger originalSinger : originalSingerList) {
            // 通过歌手id查询歌手 若不存在 报400
            singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
            // 歌曲集合与原唱歌手解绑（物理删除）
            originalSingerRepository.delete(originalSinger);
        }
    }
}
