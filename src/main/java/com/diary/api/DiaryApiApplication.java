package com.diary.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiaryApiApplication {

    public static void main(String[] args) {
        // .envファイルの読み込み
        try {
            Dotenv dotenv = Dotenv.configure()
                    .filename(".env")
                    .ignoreIfMissing()
                    .load();
            
            // 環境変数をシステムプロパティとして設定
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
        } catch (Exception e) {
            System.out.println("Note: .env file not found or could not be loaded. Using default configuration.");
        }
        
        SpringApplication.run(DiaryApiApplication.class, args);
    }
}