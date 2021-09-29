package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.DeleteSongBean;
import org.nano.song.info.bean.song.ImportSongBean;
import org.nano.song.info.request.song.DeleteSongRequest;
import org.nano.song.info.request.song.ImportSongRequest;
import org.nano.song.service.song.DeleteSongService;
import org.nano.song.service.song.ImportSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.text.ParseException;

/**
 * 歌曲相关控制器类
 */
@CrossOrigin
@RequestMapping(Constant.URL_SONG)
@RestController
public class SongController {

    @Autowired
    private ImportSongService importSongService;

    @Autowired
    private DeleteSongService deleteSongService;

    /**
     * 导入歌曲操作
     *
     * @param request 导入请求
     * @return 导入成功信息
     * @throws ResourceExistException     资源已存在
     * @throws ResourceNotFoundException  资源未找到
     * @throws ParseException             格式转换错误
     * @throws IOException                文件IO错误
     * @throws BindRelationExistException 绑定关系已存在
     */
    @PostMapping(Constant.OPERATION_IMPORT)
    @ResponseBody
    public ResponseEntity<?> importSong(@RequestBody @Validated @NotEmpty ImportSongRequest request)
            throws ResourceExistException, ResourceNotFoundException, ParseException, IOException, BindRelationExistException {

        ImportSongBean importSongBean = new ImportSongBean();
        importSongBean.setCoverSingerName("椎名なのは");
        importSongBean.setFileName(request.getFileName());
        importSongBean.setPerformanceDate(request.getPerformanceDate());
        importSongBean.setSongFileContent(request.getSongFileContent());
        importSongBean.setSongTitle(request.getSongTitle());
        // 导入歌曲
        importSongService.importSong(importSongBean);

        return new ResponseEntity<>(Constant.MSG_IMPORT_SUCCESS, HttpStatus.OK);
    }

    /**
     * 删除歌曲操作
     *
     * @param request 删除请求
     * @return 删除成功信息
     * @throws ResourceNotFoundException 资源未找到
     */
    @PostMapping(Constant.OPERATION_DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteSong(@RequestBody @Validated @NotEmpty DeleteSongRequest request)
            throws ResourceNotFoundException {

        DeleteSongBean deleteSongBean = new DeleteSongBean();
        deleteSongBean.setSongId(request.getSongId());
        // 删除歌曲
        deleteSongService.deleteSong(deleteSongBean);

        return new ResponseEntity<>(Constant.MSG_DELETE_SUCCESS, HttpStatus.OK);
    }
}
