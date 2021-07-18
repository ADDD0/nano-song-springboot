package org.nano.song.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.nano.song.controller.handler.exception.ResourceNotFoundException;
import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.domain.repository.entity.SongCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Base64;

@CrossOrigin
@RequestMapping(Constant.URL_SONG)
@RestController
@Slf4j
@Transactional
public class SongController {

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private SongRepository songRepository;

    @Data
    public static class ImportSongRequest {
        // 文件名
        @NotBlank(message = Constant.ERR_MSG_FILE_NOT_UPLOAD)
        private String fileName;
        // 弹唱日期
        @NotBlank(message = Constant.ERR_MSG_PERFORMANCE_DATE_EMPTY)
        @Pattern(regexp = "^\\d{4}/\\d{1,2}/\\d{1,2}$", message = Constant.ERR_MSG_DATE_FOMAT_WRONG)
        private String performanceDate;
        // 歌曲文件内容
        @NotBlank(message = Constant.ERR_MSG_FILE_CONTENT_EMPTY)
        private String songFileContent;
        // 所属歌曲集合
        @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
        private String songTitle;
    }

    @PostMapping(Constant.OPERATION_IMPORT)
    @ResponseBody
    public ResponseEntity<?> importSong(@RequestBody @Validated @NotEmpty ImportSongRequest request) throws ResourceNotFoundException {

        final String coverSingerName = "椎名なのは";
        String fileName = request.getFileName();
        String performanceDate = request.getPerformanceDate();
        String songFileContent = request.getSongFileContent();
        String songTitle = request.getSongTitle();

        // 检查翻唱歌手是否存在 若不存在 报400
        Singer singer = singerRepository.findBySingerNameAndLogicalDeleteFlag(coverSingerName, false)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, coverSingerName));

        // 检查歌曲集合是否存在 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongTitleAndLogicalDeleteFlag(songTitle, false)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, songTitle));

        // 设置实体属性
        Song song = new Song();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            song.setPerformanceDate(simpleDateFormat.parse(performanceDate));
        } catch (ParseException e) {
            // 格式化日期失败 报400
            return new ResponseEntity<>(Constant.ERR_MSG_DATE_FOMAT_WRONG, HttpStatus.BAD_REQUEST);
        }
        song.setPath(performanceDate + "/" + fileName);
        song.setSongCollectionId(songCollection.getSongCollectionId());
        // 去除文件扩展名
        song.setSongName(fileName.substring(0, fileName.length() - 4));
        song.setLogicalDeleteFlag(false);

        try {
            // todo 获取base64编码后的字节流 正则实现
            songFileContent = songFileContent.replaceFirst("data:audio/mpeg;base64,", "");
            // 创建新的文件夹路径
            String newDirPath = Constant.RESOURCES_STATIC_ROOT_PATH + performanceDate + Constant.SLASH;
            File fileDir = new File(newDirPath);
            if (!fileDir.exists()) {
                // 文件夹不存在时创建文件夹
                if (!fileDir.mkdirs()) {
                    // 新建文件夹失败 报500
                    return new ResponseEntity<>("新建文件夹失败", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            // 创建新的音频文件
            String newSongPath = newDirPath + fileName;
            if (new File(newSongPath).exists()) {
                // 歌曲文件已存在
                log.warn(Constant.SHOW_SONG + "[" + fileName + "]" + Constant.MSG_EXIST);
            }
            // todo 使用AudioFileWriter创建音频
            // Base64解码
            byte[] buffer = Base64.getDecoder().decode(songFileContent);
            FileOutputStream fileOutputStream = new FileOutputStream(newSongPath);
            fileOutputStream.write(buffer);
            fileOutputStream.close();
        } catch (IOException e) {
            return new ResponseEntity<>("文件IO错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        songRepository.save(song);
        log.info(Constant.LOG_ADD + song);

        // 建立翻唱歌手关系
        CoverSinger coverSinger = new CoverSinger();
        coverSinger.setSingerId(singer.getSingerId());
        coverSinger.setSongId(song.getSongId());

        coverSingerRepository.save(coverSinger);
        log.info(Constant.LOG_ADD + coverSinger);

        return new ResponseEntity<>(Constant.SHOW_SONG + Constant.MSG_IMPORT_SUCCESS, HttpStatus.OK);
    }

    @Data
    public static class DeleteSongRequest {
        // 歌曲id
        @NotNull
        private int songId;
    }

    @PostMapping(Constant.OPERATION_DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteSong(@RequestBody @Validated @NotEmpty DeleteSongRequest request) throws ResourceNotFoundException {

        int songId = request.getSongId();

        // 查找歌曲 若不存在 报400
        Song song = songRepository.findBySongIdAndLogicalDeleteFlag(songId, false)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG, "id=" + songId));

        // 查找歌曲翻唱
        ArrayList<CoverSinger> coverSingerArrayList = coverSingerRepository.findAllBySongId(song.getSongId())
                .orElse(new ArrayList<>());
        for (CoverSinger coverSinger : coverSingerArrayList) {
            // 检查各位歌手是否存在 若不存在 报400
            singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), false)
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
            // 歌曲与翻唱歌手解绑（物理删除）
            coverSingerRepository.delete(coverSinger);
        }

        // 设置逻辑删除标志为true
        song.setLogicalDeleteFlag(true);
        songRepository.save(song);
        log.info(Constant.LOG_DELETE + song);

        return new ResponseEntity<>(Constant.MSG_DELETE_SUCCESS, HttpStatus.OK);
    }
}
