package org.nano.song.domain;

public class Constant {

    /**
     * 数据库表命名
     **/
    public static final String TABLE_COVER_SINGER = "COVER_SINGER";
    public static final String TABLE_ORIGINAL_SINGER = "ORIGINAL_SINGER";
    public static final String TABLE_SINGER = "SINGER";
    public static final String TABLE_SONG = "SONG";
    public static final String TABLE_SONG_COLLECTION = "SONG_COLLECTION";

    /**
     * 访问路径
     **/
    public static final String URL_ORIGINAL_SINGER = "/originalSinger";
    public static final String URL_QUERY_SONG = "/querySong";
    public static final String URL_QUERY_SONG_COLLECTION = "/querySongCollection";
    public static final String URL_SINGER = "/singer";
    public static final String URL_SONG = "/song";
    public static final String URL_SONG_COLLECTION = "/songCollection";

    /**
     * 请求操作
     **/
    public static final String OPERATION_ADD = "/add";
    public static final String OPERATION_BIND = "/bind";
    public static final String OPERATION_DELETE = "/delete";
    public static final String OPERATION_IMPORT = "/import";
    public static final String OPERATION_QUERY = "/query";

    /**
     * 查询实体
     **/
    public static final String QUERY_CONTENT_SONG_COLLECTION_ID = "/songCollectionId";
    public static final String QUERY_CONTENT_PERFORMANCE_DATE = "/performanceDate";
    public static final String QUERY_CONTENT_SINGER_NAME = "/singerName";
    public static final String QUERY_CONTENT_SONG_TITLE = "/songTitle";

    /**
     * 固定文言
     **/
    public static final String SLASH = "/";

    /**
     * 描述文言
     **/
    public static final String MSG_ADD_SUCCESS = "新增成功";
    public static final String MSG_BIND = "已关联";
    public static final String MSG_BIND_SUCCESS = "关联成功";
    public static final String MSG_DELETE_SUCCESS = "删除成功";
    public static final String MSG_EXIST = "已存在";
    public static final String MSG_IMPORT_SUCCESS = "导入成功";
    public static final String MSG_NOT_EXIST = "不存在";
    public static final String MSG_NOT_SET_SINGER = "未设定歌手";

    /**
     * 后端展示文言
     **/
    public static final String LOG_ADD = "新增对象";
    public static final String LOG_DELETE = "删除对象";
    public static final String LOG_QUERY = "查询对象";

    /**
     * 前端展示文言
     **/
    public static final String SHOW_SINGER = "歌手";
    public static final String SHOW_SONG = "单曲";
    public static final String SHOW_SONG_COLLECTION = "歌曲集合";

    /**
     * 错误信息
     **/
    public static final String ERR_MSG_SONG_COLLECTION_ID_EMPTY = "歌曲集合id不能为空";
    public static final String ERR_MSG_COVER_SINGER_EMPTY = "翻唱歌手不能为空";
    public static final String ERR_MSG_PERFORMANCE_DATE_EMPTY = "弹唱日期未选择";
    public static final String ERR_MSG_DATE_FOMAT_WRONG = "日期格式有误";
    public static final String ERR_MSG_FILE_CONTENT_EMPTY = "文件内容不能为空";
    public static final String ERR_MSG_FILE_NOT_UPLOAD = "文件未上传";
    public static final String ERR_MSG_SINGER_NAME_EMPTY = "歌手姓名不能为空";
    public static final String ERR_MSG_SONG_TITLE_EMPTY = "歌名不能为空";
    public static final String ERR_MSG_ORIGINAL_SINGER_EMPTY = "原唱歌手不能为空";

    /**
     * 静态资源保存根路径
     **/
    public static final String RESOURCES_STATIC_ROOT_PATH = "../nano-song-springboot/src/main/resources/static/";
}
