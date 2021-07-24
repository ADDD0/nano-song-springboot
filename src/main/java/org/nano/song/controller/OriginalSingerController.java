package org.nano.song.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.controller.handler.exception.BindRelationExistException;
import org.nano.song.controller.handler.exception.ResourceNotFoundException;
import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@CrossOrigin
@RequestMapping(Constant.URL_ORIGINAL_SINGER)
@RestController
@Slf4j
@Transactional
public class OriginalSingerController {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Data
    public static class BindOriginalSingerRequest {
        // 歌手姓名
        @NotBlank(message = Constant.ERR_MSG_SINGER_NAME_EMPTY)
        private String singerName;
        // 歌曲标题
        @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
        private String songTitle;
    }

    @PostMapping(Constant.OPERATION_BIND)
    @ResponseBody
    public ResponseEntity<?> bindOriginalSinger(@RequestBody @Validated @NotEmpty BindOriginalSingerRequest request) throws ResourceNotFoundException, BindRelationExistException {

        String singerName = request.getSingerName();
        String songTitle = request.getSongTitle();

        // 检查歌曲集合是否存在 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongTitleAndLogicalDeleteFlag(songTitle, false)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, songTitle));

        // 检查歌手是否存在 若不存在 报400
        Singer singer = singerRepository.findBySingerNameAndLogicalDeleteFlag(singerName, false)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, singerName));

        // 检查关联关系是否存在
        if (null != originalSingerRepository
                .findBySongCollectionIdAndSingerId(songCollection.getSongCollectionId(), singer.getSingerId())
                .orElse(null)) {
            // 若关联关系存在 报400
            throw new BindRelationExistException(Constant.SHOW_SONG_COLLECTION, songTitle, Constant.SHOW_SINGER, singerName);
        }

        OriginalSinger originalSinger = new OriginalSinger();
        // 设置实体属性
        originalSinger.setSongCollectionId(songCollection.getSongCollectionId());
        originalSinger.setSingerId(singer.getSingerId());

        originalSingerRepository.save(originalSinger);
        log.info(Constant.LOG_ADD + originalSinger);

        return new ResponseEntity<>(Constant.MSG_BIND_SUCCESS, HttpStatus.OK);
    }
}
