package com.ncc.verdi;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.ncc.verdi.caching.TA3CacheCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.ncc.verdi"})
public class VerdiRestApplication implements CommandLineRunner, WebMvcConfigurer {
    @Autowired
    ApplicationContext appContext;

    @Value("${ncc.ta2-graphs}")
    private String[] ta2Graphs;

    @Value("${ncc.ta3-runs}")
    private String[] ta3Runs;

    @Override
    public void run(String... args) throws IOException {
        if (args.length > 0) {
            switch(args[0]) {
                default:
                    break;
                case "clear":
                case "populate":
                    runCacheCreator(args[0]);
            }
        }
    }

    private <T> void setHandler(Class<T> c, ConsoleHandler handler) {
        Logger logger = Logger.getLogger(c.getName());
        logger.setLevel(Level.INFO);
        logger.addHandler(handler);
    }

    private void runCacheCreator(String arg) throws IOException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT][%4$-7s] %5$s %n");
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        handler.setFormatter(new SimpleFormatter());

        setHandler(RDFCacheCreator.class, handler);
        setHandler(TA3CacheCreator.class, handler);

        RDFCacheCreator ta2Cache = appContext.getBean(RDFCacheCreator.class);
        TA3CacheCreator ta3Cache = appContext.getBean(TA3CacheCreator.class);
        if ("clear".equals(arg)) {
            ta2Cache.deleteAllIndices();
            ta3Cache.deleteAllIndices();
        } else {
            ta2Cache.run(ta2Graphs);
            ta3Cache.process(ta3Runs);
        }
        SpringApplication.exit(appContext);
        System.exit(0);
    }

    public static void main(String[] args) {
        new SpringApplication(VerdiRestApplication.class).run(args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }
}
