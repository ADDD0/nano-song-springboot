package org.nano.song.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.nano.song.controller.handler.exception.ResourceNotFoundException;
import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@CrossOrigin
@RequestMapping(Constant.URL_QUERY_SONG)
@RestController
@Slf4j
@Transactional
public class QuerySongController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @Data
    private static class QuerySongResponse {
        // 歌曲查询结果列表
        private ArrayList<QueryResponseResource> queryResponseResourceArrayList;

        @Data
        private static class QueryResponseResource {
            // 歌曲
            private Song song;
            // 各歌曲翻唱列表
            private ArrayList<Singer> singerArrayList;
        }
    }

    @Data
    public static class QueryPerformanceDateRequest {
        // 演唱日期
        @NotBlank(message = Constant.ERR_MSG_PERFORMANCE_DATE_EMPTY)
        @Pattern(regexp = "^\\d{4}/\\d{1,2}/\\d{1,2}$", message = Constant.ERR_MSG_DATE_FOMAT_WRONG)
        private String performanceDate;
    }

    @PostMapping(Constant.QUERY_CONTENT_PERFORMANCE_DATE)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QueryPerformanceDateRequest request) throws ResourceNotFoundException {

        String performanceDate = request.getPerformanceDate();

        Date parsePerformanceDate;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            parsePerformanceDate = simpleDateFormat.parse(performanceDate);
        } catch (ParseException e) {
            // 格式化日期失败 报400
            return new ResponseEntity<>(Constant.ERR_MSG_DATE_FOMAT_WRONG, HttpStatus.BAD_REQUEST);
        }
        // 查找歌曲
        ArrayList<Song> songArrayList = songRepository.findAllByPerformanceDateAndLogicalDeleteFlag(parsePerformanceDate, false)
                .orElse(new ArrayList<>());

        ArrayList<QuerySongResponse.QueryResponseResource> queryResponseResourceArrayList = new ArrayList<>();
        for (Song song : songArrayList) {
            // 查找歌曲翻唱
            ArrayList<CoverSinger> coverSingerArrayList = coverSingerRepository.findAllBySongId(song.getSongId())
                    .orElse(new ArrayList<>());

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerArrayList) {
                // 检查歌手是否存在 若不存在 报400
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), false)
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲查询结果
            QuerySongResponse.QueryResponseResource responseResource = new QuerySongResponse.QueryResponseResource();
            responseResource.setSong(song);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲查询结果
            queryResponseResourceArrayList.add(responseResource);
        }
        QuerySongResponse response = new QuerySongResponse();
        // 歌曲查询结果按演唱日期排序
        queryResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        response.setQueryResponseResourceArrayList(queryResponseResourceArrayList);
        log.info(Constant.LOG_QUERY + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    private static class QuerySongCollectionIdRequest {
        // 歌曲集合id
        @NotNull(message = Constant.ERR_MSG_SONG_COLLECTION_ID_EMPTY)
        private int songCollectionId;
    }

    @PostMapping(Constant.QUERY_CONTENT_SONG_COLLECTION_ID)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySongCollectionIdRequest request) throws ResourceNotFoundException {

        int songCollectionId = request.getSongCollectionId();

        // 查找歌曲
        ArrayList<Song> songArrayList = songRepository.findAllBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, false)
                .orElse(new ArrayList<>());

        ArrayList<QuerySongResponse.QueryResponseResource> queryResponseResourceArrayList = new ArrayList<>();
        for (Song song : songArrayList) {
            // 查找歌曲翻唱
            ArrayList<CoverSinger> coverSingerArrayList = coverSingerRepository.findAllBySongId(song.getSongId())
                    .orElse(new ArrayList<>());

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerArrayList) {
                // 检查歌手是否存在 若不存在 报400
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), false)
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲查询结果
            QuerySongResponse.QueryResponseResource responseResource = new QuerySongResponse.QueryResponseResource();
            responseResource.setSong(song);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲查询结果
            queryResponseResourceArrayList.add(responseResource);
        }
        QuerySongResponse response = new QuerySongResponse();
        // 歌曲查询结果按演唱日期排序
        queryResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        response.setQueryResponseResourceArrayList(queryResponseResourceArrayList);
        log.info(Constant.LOG_QUERY + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
