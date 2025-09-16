package com.dev.syf_demo.model;

import lombok.Data;

@Data
public class ImageDto  {
    public Data data;
    public boolean success;
    public int status;

    public class Data {
        public String id;
        public String title;
        public String url_viewer;
        public String url;
        public String display_url;
        public int width;
        public int height;
        public int size;
        public int time;
        public int expiration;
        public Image image;
        public Thumb thumb;
        public String delete_url;

        public class Image {
            public String filename;
            public String name;
            public String mime;
            public String extension;
            public String url;
        }

        public class Thumb {
            public String filename;
            public String name;
            public String mime;
            public String extension;
            public String url;
        }
    }
}
