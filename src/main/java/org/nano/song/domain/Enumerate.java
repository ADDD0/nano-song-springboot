package org.nano.song.domain;

public class Enumerate {

    // 完成度
    public enum COMPLETION {

        KANBIKI("01", "完璧"),
        NYA_HALF("02", "nya半首"),
        NYA_SEGMENT("03", "nya一段"),
        NYANO("04", "nyano出没");

        private final String code;
        private final String value;

        COMPLETION(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    // 乐器
    public enum INSTRUMENT {

        PIANO("01", "钢琴"),
        GUITAR("02", "吉他"),
        KALIMBA("03", "吉他"),
        DRUM("04", "鼓");

        private final String code;
        private final String value;

        INSTRUMENT(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    // 类型
    public enum TYPE {

        NORMAL("01", "普通弹唱"),
        OYASUMI("02", "晚安弹唱"),
        SONG("03", "歌回");

        private final String code;
        private final String value;

        TYPE(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    // 场景
    public enum SCENE {

        B_NOMI("01", "B限"),
        Y_NOMI("02", "Y限"),
        B_AND_Y("03", "双推"),
        FANBOX("04", "fanbox"),
        FANTIA("05", "fantia");

        private final String code;
        private final String value;

        SCENE(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}
