package love.simbot.example.utils;


import catcode.CatCodeUtil;
import catcode.CodeTemplate;

public enum CatImageUtil {
    INSTANCE;

    public String msg(String url){
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        CodeTemplate<String> template = util.getStringTemplate();
        String image = template.image(url);
        return image;
    }
}
