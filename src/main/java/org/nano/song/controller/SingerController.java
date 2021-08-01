package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.info.bean.singer.AddSingerBean;
import org.nano.song.info.request.singer.AddSingerRequest;
import org.nano.song.service.singer.AddSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@CrossOrigin
@RequestMapping(Constant.URL_SINGER)
@RestController
public class SingerController {

    @Autowired
    private AddSingerService addSingerService;

    @PostMapping(Constant.OPERATION_ADD)
    @ResponseBody
    public ResponseEntity<?> addSinger(@RequestBody @Validated @NotEmpty AddSingerRequest request)
            throws ResourceExistException {

        AddSingerBean addSingerBean = new AddSingerBean();
        addSingerBean.setSingerName(request.getSingerName());
        // 新增歌手
        addSingerService.addSinger(addSingerBean);

        return new ResponseEntity<>(Constant.MSG_ADD_SUCCESS, HttpStatus.OK);
    }
}
