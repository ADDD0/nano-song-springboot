package org.nano.song.service.coverSinger;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.UnbindCoverSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 解绑翻唱歌手服务接口实现类
 */
@Service
@Transactional
public class UnbindCoverSingerServiceImpl implements UnbindCoverSingerService {

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    /**
     * 解绑翻唱歌手
     *
     * @param unbindCoverSingerBean 解绑翻唱歌手参数
     * @throws ResourceNotFoundException 资源未找到
     */
    @Override
    public void unbindCoverSinger(UnbindCoverSingerBean unbindCoverSingerBean) throws ResourceNotFoundException {

        int songId = unbindCoverSingerBean.getSongId();
        // 通过歌曲id查询所有翻唱歌手
        List<CoverSinger> coverSingerList = coverSingerRepository.findAllBySongId(songId)
                .orElse(Collections.emptyList());

        for (CoverSinger coverSinger : coverSingerList) {
            // 通过歌手id查询歌手 若不存在 报400
            singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
            // 歌曲与翻唱歌手解绑（物理删除）
            coverSingerRepository.delete(coverSinger);
        }
    }
}
