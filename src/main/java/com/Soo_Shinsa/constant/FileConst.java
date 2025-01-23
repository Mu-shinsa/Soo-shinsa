package com.Soo_Shinsa.constant;

import java.util.List;

public class FileConst {

    /**
     * 이미지 파일로 허용되는 확장자
     * jpg, jpeg, png, gif, bmp 형식의 파일만 허용
     */
    public static final List<String> IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "bmp");

    /**
     * 이미지 upload 경로
     * 예: "/uploads/images/"
     */
    public static final String IMAGE_PATH = ""; //이미지 upload 경로
}
