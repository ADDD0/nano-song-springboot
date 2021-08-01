package org.nano.song.service.songCollection;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.UnbindOriginalSingerBean;
import org.nano.song.info.bean.song.DeleteSongBean;
import org.nano.song.info.bean.songCollection.DeleteSongCollectionBean;
import org.nano.song.service.originalSinger.UnbindOriginalSingerService;
import org.nano.song.service.song.DeleteSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class DeleteSongCollectionServiceImpl implements DeleteSongCollectionService {

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private DeleteSongService deleteSongService;

    @Autowired
    private UnbindOriginalSingerService unbindOriginalSingerService;

    @Override
    public void deleteSongCollection(DeleteSongCollectionBean deleteSongCollectionBean) throws ResourceNotFoundException {

        int songCollectionId = deleteSongCollectionBean.getSongCollectionId();
        // 通过歌曲集合id查找歌曲集合 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));

        // 通过歌曲集合id查找所有歌曲
        ArrayList<Song> songArrayList = songRepository.findAllBySongCollectionIdAndLogicalDeleteFlag(songCollection.getSongCollectionId(), DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());
        // 删除该歌曲集合下的所有歌曲
        for (Song song : songArrayList) {
            DeleteSongBean deleteSongBean = new DeleteSongBean();
            deleteSongBean.setSongId(song.getSongId());
            // 删除歌曲
            deleteSongService.deleteSong(deleteSongBean);
        }

        UnbindOriginalSingerBean unbindOriginalSingerBean = new UnbindOriginalSingerBean();
        unbindOriginalSingerBean.setSongCollectionId(songCollectionId);
        // 解绑原唱歌手
        unbindOriginalSingerService.unbindOriginalSinger(unbindOriginalSingerBean);

        // 设置逻辑删除标志为true
        songCollection.setLogicalDeleteFlag(DELETE_FLAG.DELETED.getCode());
        // 数据持久化
        songCollectionRepository.save(songCollection);
    }
}
