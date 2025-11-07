import cn.hutool.core.io.resource.ResourceUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FreeMakerTest
 * @Package: PACKAGE_NAME
 * @Description:
 * @Author HGL
 * @Create: 2025/11/5 15:42
 */
public class FreeMakerTest {
    @Test
    public void test() throws IOException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);

        // 指定模板文件所在的路径
        /*
         1、不推荐使用绝对路径
        File dir = new File("src/main/resources/templates");
        System.out.println(dir.getAbsoluteFile());
        */

        // 2、推荐使用相对路径(通过类加载器来获取模板文件路径)
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("templates");
        System.out.println(resource);
        assert resource != null;
        System.out.println(resource.getPath());
//        configuration.setClassLoaderForTemplateLoading(classLoader, "/templates");
        configuration.setDirectoryForTemplateLoading(new File(resource.getPath()));

        /*
        3、使用hutool工具 来获取模板文件路径
        String path = ResourceUtil.getResource("templates").getPath();
        configuration.setDirectoryForTemplateLoading(new File(path));
         */

        configuration.setNumberFormat("0.######");

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("myweb.html.ftl");

        // 创建数据模型
        Map<String, Object> dataModel = createDataModel();
        String projectRootPath = System.getProperty("user.dir");
        String outPath = String.format("%s/temp/myweb.html", projectRootPath);
        try (Writer out = new FileWriter(outPath)) {
            template.process(dataModel, out);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

    }

    private static Map<String, Object> createDataModel() {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("currentYear", 2025);
        List<Map<String, Object>> menuItems = new ArrayList<>();
        Map<String, Object> menuItem1 = new HashMap<>();
        menuItem1.put("url", "https://github.com/hglgh");
        menuItem1.put("label", "PIDOG的GitHub地址");
        Map<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("url", "https://codefather.cn");
        menuItem2.put("label", "编程导航");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        dataModel.put("menuItems", menuItems);
        return dataModel;
    }
}
