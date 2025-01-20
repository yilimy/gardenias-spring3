package com.gardenia.web.banner;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.util.Random;

/**
 * 自定义banner的输出
 * <p>
 *     因为输出 Banner 在spring刷新上下文之前，此时，bean尚未注入进容器，
 *     所以将banner的内容定义在类的属性中，而不是从资源文件中读取。
 * <p>
 *     此前，已经加载了环境变量
 * @author caimeng
 * @date 2025/1/20 15:42
 */
public class GardeniaBanner implements Banner {
    // 所有的Banner信息如果是复合的结构，则必须使用数组的结构进行配置
    public static final String[] BANNER_1 = {
            " ________  ________  ________  ________  _______   ________   ___  ________",
            "|\\   ____\\|\\   __  \\|\\   __  \\|\\   ___ \\|\\  ___ \\ |\\   ___  \\|\\  \\|\\   __  \\",
            "\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\_|\\ \\ \\   __/|\\ \\  \\\\ \\  \\ \\  \\ \\  \\|\\  \\",
            " \\ \\  \\  __\\ \\   __  \\ \\   _  _\\ \\  \\ \\\\ \\ \\  \\_|/_\\ \\  \\\\ \\  \\ \\  \\ \\   __  \\",
            "  \\ \\  \\|\\  \\ \\  \\ \\  \\ \\  \\\\  \\\\ \\  \\_\\\\ \\ \\  \\_|\\ \\ \\  \\\\ \\  \\ \\  \\ \\  \\ \\  \\",
            "   \\ \\_______\\ \\__\\ \\__\\ \\__\\\\ _\\\\ \\_______\\ \\_______\\ \\__\\\\ \\__\\ \\__\\ \\__\\ \\__\\",
            "    \\|_______|\\|__|\\|__|\\|__|\\|__|\\|_______|\\|_______|\\|__| \\|__|\\|__|\\|__|\\|__|"
    };
    public static final String[] BANNER_2 = {
            "                        .___            .__",
            "   _________ _______  __| _/____   ____ |__|____",
            "  / ___\\__  \\\\_  __ \\/ __ |/ __ \\ /    \\|  \\__  \\",
            " / /_/  > __ \\|  | \\/ /_/ \\  ___/|   |  \\  |/ __ \\_",
            " \\___  (____  /__|  \\____ |\\___  >___|  /__(____  /",
            "/_____/     \\/           \\/    \\/     \\/        \\/"
    };
    public static final String[] BANNER_3 = {
            "                                     /$$                     /$$",
            "                                    | $$                    |__/",
            "  /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$$  /$$$$$$  /$$$$$$$  /$$  /$$$$$$",
            " /$$__  $$ |____  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$__  $$| $$ |____  $$",
            "| $$  \\ $$  /$$$$$$$| $$  \\__/| $$  | $$| $$$$$$$$| $$  \\ $$| $$  /$$$$$$$",
            "| $$  | $$ /$$__  $$| $$      | $$  | $$| $$_____/| $$  | $$| $$ /$$__  $$",
            "|  $$$$$$$|  $$$$$$$| $$      |  $$$$$$$|  $$$$$$$| $$  | $$| $$|  $$$$$$$",
            " \\____  $$ \\_______/|__/       \\_______/ \\_______/|__/  |__/|__/ \\_______/",
            " /$$  \\ $$",
            "|  $$$$$$/",
            " \\______/",
            "",
    };

    public static final String[][] BANNER_ARRAY = {BANNER_1, BANNER_2, BANNER_3};

    public static final Random random = new Random();
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        // 输出一个换行
        out.println();
        // 随机数 [0, 10)
        int r = random.nextInt(10);
        // 随机获取banner
        int m = r % BANNER_ARRAY.length;
        for (String str : BANNER_ARRAY[m]) {
            out.println(str);
        }
        // 输出一个换行
        out.println();
        // 强制清空缓存
        out.flush();
    }
}
