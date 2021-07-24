package org.nano.song.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@CrossOrigin
@RequestMapping(Constant.URL_SONG_COLLECTION)
@RestController
@Slf4j
@Transactional
public class SongCollectionController {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private SongRepository songRepository;

    @Data
    public static class AddSongCollectionRequest {
        // 歌曲标题
        @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
        private String songTitle;
        // 歌曲中文名
        private String chineseTitle;
        // 歌曲英文名
        private String englishTitle;
    }

    @PostMapping(Constant.OPERATION_ADD)
    @ResponseBody
    public ResponseEntity<?> addSongCollection(@RequestBody @Validated @NotEmpty AddSongCollectionRequest request) throws ResourceExistException {

        String songTitle = request.getSongTitle();
        String chineseTitle = request.getChineseTitle();
        String englishTitle = request.getEnglishTitle();

        // 检查歌曲集合是否存在
        if (null != songCollectionRepository
                .findBySongTitleAndLogicalDeleteFlag(songTitle, false)
                .orElse(null)) {
            // 若歌曲集合存在 报400
            throw new ResourceExistException(Constant.SHOW_SONG_COLLECTION, songTitle);
        }

        SongCollection songCollection = new SongCollection();
        // 设置实体属性
        songCollection.setSongTitle(songTitle);
        songCollection.setChineseTitle(chineseTitle);
        songCollection.setEnglishTitle(englishTitle);
        songCollection.setLogicalDeleteFlag(false);

        // 数据持久化
        songCollectionRepository.save(songCollection);
        log.info(Constant.LOG_ADD + songCollection);

        return new ResponseEntity<>(Constant.MSG_ADD_SUCCESS, HttpStatus.OK);
    }

    @Data
    public static class DeleteSongCollectionRequest {
        // 歌曲集合id
        @NotNull(message = Constant.ERR_MSG_SONG_COLLECTION_ID_EMPTY)
        private int songCollectionId;
    }

    @PostMapping(Constant.OPERATION_DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteSongCollection(@RequestBody @Validated @NotEmpty DeleteSongCollectionRequest request) throws ResourceNotFoundException {

        int songCollectionId = request.getSongCollectionId();

        // 查找歌曲集合 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, false)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));

        // 删除该歌曲集合下的所有歌曲
        SongController songController = new SongController();
        ArrayList<Song> songArrayList = songRepository.findAllBySongCollectionIdAndLogicalDeleteFlag(songCollection.getSongCollectionId(), false)
                .orElse(new ArrayList<>());
        for (Song song : songArrayList) {
            SongController.DeleteSongRequest songControllerDeleteRequest = new SongController.DeleteSongRequest();
            songControllerDeleteRequest.setSongId(song.getSongId());
            songController.deleteSong(songControllerDeleteRequest);
        }

        // 查找歌曲原唱
        ArrayList<OriginalSinger> originalSingerArrayList = originalSingerRepository.findAllBySongCollectionId(songCollection.getSongCollectionId())
                .orElse(new ArrayList<>());
        for (OriginalSinger originalSinger : originalSingerArrayList) {
            // 检查各位歌手是否存在 若不存在 报400
            singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), false)
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
            // 歌曲集合与原唱歌手解绑（物理删除）
            originalSingerRepository.delete(originalSinger);
        }

        // 设置逻辑删除标志为true
        songCollection.setLogicalDeleteFlag(true);
        songCollectionRepository.save(songCollection);
        log.info(Constant.LOG_DELETE + songCollection);

        return new ResponseEntity<>(Constant.MSG_DELETE_SUCCESS, HttpStatus.OK);
    }
}
