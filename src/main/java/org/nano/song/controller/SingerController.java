package org.nano.song.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.controller.handler.exception.ResourceExistException;
import org.nano.song.domain.repository.SingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@CrossOrigin
@RequestMapping(Constant.URL_SINGER)
@RestController
@Slf4j
@Transactional
public class SingerController {

    @Autowired
    private SingerRepository singerRepository;

    @Data
    public static class AddSingerRequest {
        // 歌手名
        @NotBlank(message = Constant.ERR_MSG_SINGER_NAME_EMPTY)
        private String singerName;
    }

    @PostMapping(Constant.OPERATION_ADD)
    @ResponseBody
    public ResponseEntity<?> addSinger(@RequestBody @Validated @NotEmpty AddSingerRequest request) throws ResourceExistException {

        String singerName = request.getSingerName();

        // 检查歌手是否存在
        if (null != singerRepository
                .findBySingerNameAndLogicalDeleteFlag(singerName, false)
                .orElse(null)) {
            // 若歌手存在 报400
            throw new ResourceExistException(Constant.SHOW_SINGER, singerName);
        }

        Singer singer = new Singer();
        // 设置实体属性
        singer.setSingerName(singerName);
        singer.setLogicalDeleteFlag(false);

        singerRepository.save(singer);
        log.info(Constant.LOG_ADD + singer);

        return new ResponseEntity<>(Constant.MSG_ADD_SUCCESS, HttpStatus.OK);
    }
}
