package io.github.jython234.matrix.appservice.event.message;

import com.google.gson.annotations.SerializedName;

public class MessageContent {
    public String body;
    public String msgtype;

    public static class ThumbnailInfo {
        @SerializedName("h") public int height;
        @SerializedName("w") public int width;

        public String mimetype;
        public int size;
    }

    public static class TextMessageContent extends MessageContent {
        transient public static final String TYPE = "m.text";

        public TextMessageContent() {
            this.msgtype = TYPE;
        }
    }

    public static class NoticeMessageContent extends MessageContent {
        transient public static final String TYPE = "m.notice";

        public NoticeMessageContent() {
            this.msgtype = TYPE;
        }
    }

    public static class EmoteMessageContent extends MessageContent {
        transient public static final String TYPE = "m.emote";

        public EmoteMessageContent() {
            this.msgtype = TYPE;
        }
    }

    public static class VideoMessageContent extends MessageContent {
        transient public static final String TYPE = "m.video";

        public String url;
        public Info info;

        public VideoMessageContent() {
            this.msgtype = TYPE;
        }

        public static class Info {
            public long duration;

            @SerializedName("h") public int height;
            @SerializedName("w") public int width;

            public String mimetype;
            public int size;

            @SerializedName("thumbnail_info")
            public ThumbnailInfo thumbnailInfo;
            @SerializedName("thumbnail_url")
            public String thumbnailUrl;
        }
    }

    public static class AudioMessageContent extends MessageContent {
        transient public static final String TYPE = "m.audio";

        public String url;
        public Info info;

        public AudioMessageContent() {
            this.msgtype = TYPE;
        }

        public static class Info {
            public long duration;
            public String mimetype;
            public int size;
        }
    }

    public static class ImageMessageContent extends MessageContent {
        transient public static final String TYPE = "m.image";

        public String url;
        public Info info;

        public ImageMessageContent() {
            this.msgtype = TYPE;
        }

        public static class Info {
            @SerializedName("h") public int height;
            @SerializedName("w") public int width;

            public String mimetype;
            public int size;
        }
    }

    public static class FileMessageContent extends MessageContent {
        transient public static final String TYPE = "m.file";

        public String filename;
        public String url;
        public Info info;

        public FileMessageContent() {
            this.msgtype = TYPE;
        }

        public static class Info {
            public String mimetype;
            public int size;
        }
    }

    public static class LocationMessageContent extends MessageContent {
        transient public static final String TYPE = "m.location";

        @SerializedName("geo_uri")
        public String geoUri;
        public Info info;

        public LocationMessageContent() {
            this.msgtype = TYPE;
        }

        public static class Info {
            @SerializedName("thumbnail_info")
            public ThumbnailInfo thumbnailInfo;
            @SerializedName("thumbnail_url")
            public String thumbnailUrl;
        }
    }
}
