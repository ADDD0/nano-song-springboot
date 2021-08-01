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

import java.util.ArrayList;

@Service
@Transactional
public class UnbindCoverSingerServiceImpl implements UnbindCoverSingerService {

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Override
    public void unbindCoverSinger(UnbindCoverSingerBean unbindCoverSingerBean) throws ResourceNotFoundException {

        int songId = unbindCoverSingerBean.getSongId();
        // 通过歌曲id查找所有翻唱歌手
        ArrayList<CoverSinger> coverSingerArrayList = coverSingerRepository.findAllBySongId(songId)
                .orElse(new ArrayList<>());

        for (CoverSinger coverSinger : coverSingerArrayList) {
            // 通过歌手id查找歌手 若不存在 报400
            singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
            // 歌曲与翻唱歌手解绑（物理删除）
            coverSingerRepository.delete(coverSinger);
        }
    }
}
