package com.example.store.global.app;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private static String activeProfile;

    @Value("${spring.profiles.active}")
    public void setActiveProfile(String value) {
        activeProfile = value;
    }

    public static boolean isNotProd() {
        return isProd() == false;
    }

    public static boolean isProd() {
        return activeProfile.equals("prod");
    }

    @Getter
    private static String siteName;

    @Value("${custom.site.name}")
    public void setSiteName(String siteName) { this.siteName = siteName; }

    @Getter
    private static EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Getter
    private static String tempDirPath;

    @Value("${custom.temp.dirPath}")
    public void setTempDirPath(String tempDirPath) { this.tempDirPath = tempDirPath; }

    @Getter
    private static String genFileDirPath;

    @Value("${custom.genFile.dirPath}")
    public void setGenFileDirPath(String genFileDirPath) { this.genFileDirPath = genFileDirPath; }

    @Getter
    private static String tossPaymentsWidgetSecretKey;

    @Value("${custom.tossPayments.widget.secretKey}")
    public void setTossPaymentsWidgetSecretKey(String tossPaymentsWidgetSecretKey) {
        this.tossPaymentsWidgetSecretKey = tossPaymentsWidgetSecretKey;
    }

    @Getter
    private static int orderCancelableSeconds;

    @Value("${custom.order.cancelableSeconds}")
    public void setOrderCancelableSeconds(int orderCancelableSeconds) {
        this.orderCancelableSeconds = orderCancelableSeconds;
    }

    @Getter
    private static double rebateRate;

    @Value("${custom.rebate.rate}")
    public void setRebateRate(double rebateRate) {
        this.rebateRate = rebateRate;
    }
}
