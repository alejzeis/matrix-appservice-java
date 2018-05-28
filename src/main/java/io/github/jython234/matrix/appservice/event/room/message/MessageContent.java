/*
 * Copyright © 2018, jython234
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.jython234.matrix.appservice.event.room.message;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the Content in a matrix message event.
 * Since there are multiple message types, there are
 * nested classes representing each type.
 *
 * @author jython234
 */
public class MessageContent {
    /**
     * The body text of the message
     */
    public String body;
    /**
     * The type of the message.
     * ex "m.text"
     */
    public String msgtype;

    /**
     * Represents the "thumbnail_info" object found on some
     * message types
     *
     * @author jython234
     */
    public static class ThumbnailInfo {
        @SerializedName("h") public int height;
        @SerializedName("w") public int width;

        public String mimetype;
        public int size;
    }

    /**
     * Represents a simple Text message
     *
     * @author jython234
     */
    public static class TextMessageContent extends MessageContent {
        transient public static final String TYPE = "m.text";

        public TextMessageContent() {
            this.msgtype = TYPE;
        }
    }

    /**
     * Represents an "m.notice" message.
     *
     * @author jython234
     */
    public static class NoticeMessageContent extends MessageContent {
        transient public static final String TYPE = "m.notice";

        public NoticeMessageContent() {
            this.msgtype = TYPE;
        }
    }

    /**
     * Represents an "m.emote" message.
     *
     * @author jython234
     */
    public static class EmoteMessageContent extends MessageContent {
        transient public static final String TYPE = "m.emote";

        public EmoteMessageContent() {
            this.msgtype = TYPE;
        }
    }

    /**
     * Represents a message containing a video.
     *
     * @author jython234
     */
    public static class VideoMessageContent extends MessageContent {
        transient public static final String TYPE = "m.video";

        /**
         * The MXC URL pointing to the video.
         */
        public String url;
        /**
         * Information about the video.
         */
        public Info info;

        public VideoMessageContent() {
            this.msgtype = TYPE;
        }

        /**
         * Information about the video.
         */
        public static class Info {
            /**
             * Length of the video in milliseconds.
             */
            public long duration;

            /**
             * Video height (pixels).
             */
            @SerializedName("h") public int height;
            /**
             * Video width (pixels).
             */
            @SerializedName("w") public int width;

            /**
             * MIME type of the video
             */
            public String mimetype;
            /**
             * Size of the video in bytes.
             */
            public int size;

            /**
             * Information about the thumbnail (may be null).
             */
            @SerializedName("thumbnail_info")
            public ThumbnailInfo thumbnailInfo;
            /**
             * MXC URL pointing to the thumbnail.
             */
            @SerializedName("thumbnail_url")
            public String thumbnailUrl;
        }
    }

    /**
     * Represents a message containing an audio file.
     *
     * @author jython234
     */
    public static class AudioMessageContent extends MessageContent {
        transient public static final String TYPE = "m.audio";

        /**
         * The MXC URL pointing to the video.
         */
        public String url;
        /**
         * Information about the video.
         */
        public Info info;

        public AudioMessageContent() {
            this.msgtype = TYPE;
        }

        /**
         * Information about the audio.
         */
        public static class Info {
            /**
             * Length of audio in milliseconds.
             */
            public long duration;
            /**
             * MIME type of audio file.
             */
            public String mimetype;
            /**
             * Size of audio file in bytes.
             */
            public int size;
        }
    }

    /**
     * Represents a message containing an image.
     *
     * @author jython234
     */
    public static class ImageMessageContent extends MessageContent {
        transient public static final String TYPE = "m.image";

        /**
         * The MXC URL pointing to the video.
         */
        public String url;
        /**
         * Information about the video.
         */
        public Info info;

        public ImageMessageContent() {
            this.msgtype = TYPE;
        }

        /**
         * Information about the image.
         */
        public static class Info {
            /**
             * Height of the image in pixels.
             */
            @SerializedName("h") public int height;
            /**
             * Width of the image in pixels.
             */
            @SerializedName("w") public int width;

            /**
             * MIME tpye of the image.
             */
            public String mimetype;
            /**
             * Size of the image in bytes.
             */
            public int size;
        }
    }

    /**
     * Represents a message containing a file.
     *
     * @author jython234
     */
    public static class FileMessageContent extends MessageContent {
        transient public static final String TYPE = "m.file";

        /**
         * The filename of the file.
         */
        public String filename;
        /**
         * MXC URL pointing to the file.
         */
        public String url;
        /**
         * Information about the file.
         */
        public Info info;

        public FileMessageContent() {
            this.msgtype = TYPE;
        }

        /**
         * Information about the file.
         */
        public static class Info {
            /**
             * MIME type of the file.
             */
            public String mimetype;
            /**
             * Size of the file in bytes.
             */
            public int size;
        }
    }

    /**
     * Represents a location message.
     *
     * @author jython234
     */
    public static class LocationMessageContent extends MessageContent {
        transient public static final String TYPE = "m.location";

        @SerializedName("geo_uri")
        public String geoUri;
        /**
         * Information about the location.
         */
        public Info info;

        public LocationMessageContent() {
            this.msgtype = TYPE;
        }

        /**
         * Information about the location.
         */
        public static class Info {
            /**
             * Thumbnail information.
             */
            @SerializedName("thumbnail_info")
            public ThumbnailInfo thumbnailInfo;
            /**
             * MXC URL pointing to the thumbnail.
             */
            @SerializedName("thumbnail_url")
            public String thumbnailUrl;
        }
    }
}
