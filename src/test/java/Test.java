import cn.banxx.file.TencentCosUtil;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * .
 *
 * @author : Ban
 * @createTime: 2023-12-09  14:44
 * @version : 1.0
 * @since : 1.0
 */
public class Test {

    @org.junit.Test
    public void play() throws UnsupportedEncodingException {
        TencentCosUtil.getTemporaryAccessCredentials();
    }
}
