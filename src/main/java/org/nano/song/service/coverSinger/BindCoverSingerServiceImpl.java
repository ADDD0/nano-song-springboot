package org.nano.song.service.coverSinger;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.BindCoverSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BindCoverSingerServiceImpl implements BindCoverSingerService {

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public void bindCoverSinger(BindCoverSingerBean bindCoverSingerBean)
            throws ResourceNotFoundException, BindRelationExistException {

        int singerId = bindCoverSingerBean.getSingerId();
        int songId = bindCoverSingerBean.getSongId();
        // 通过歌曲id查找歌曲 若不存在 报400
        Song song = songRepository.findBySongIdAndLogicalDeleteFlag(songId, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG, "id=" + songId));
        // 通过歌手id查找歌手 若不存在 报400
        Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(singerId, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + singerId));

        // 检查关联关系是否存在
        if (coverSingerRepository.findBySongIdAndSingerId(songId, singerId).isPresent()) {
            // 若关联关系存在 报400
            throw new BindRelationExistException(Constant.SHOW_SONG, song.getSongName(), Constant.SHOW_SINGER, singer.getSingerName());
        }

        CoverSinger coverSinger = new CoverSinger();
        // 设置实体属性
        coverSinger.setSingerId(singerId);
        coverSinger.setSongId(songId);
        // 数据持久化
        coverSingerRepository.save(coverSinger);
    }
}
