package org.nano.song.service.song;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.UnbindCoverSingerBean;
import org.nano.song.info.bean.song.DeleteSongBean;
import org.nano.song.service.coverSinger.UnbindCoverSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteSongServiceImpl implements DeleteSongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UnbindCoverSingerService unbindCoverSingerService;

    @Override
    public void deleteSong(DeleteSongBean deleteSongBean) throws ResourceNotFoundException {

        int songId = deleteSongBean.getSongId();
        // 通过歌曲id查找歌曲 若不存在 报400
        Song song = songRepository.findBySongIdAndLogicalDeleteFlag(songId, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG, "id=" + songId));

        UnbindCoverSingerBean unbindCoverSingerBean = new UnbindCoverSingerBean();
        unbindCoverSingerBean.setSongId(song.getSongId());
        // 解绑翻唱歌手
        unbindCoverSingerService.unbindCoverSinger(unbindCoverSingerBean);

        // 设置逻辑删除标志为true
        song.setLogicalDeleteFlag(DELETE_FLAG.DELETED.getCode());
        // 数据持久化
        songRepository.save(song);
    }
}
