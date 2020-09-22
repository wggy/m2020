package org.wangep;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.wangep.entity.Backup;
import org.wangep.service.BackupService;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class JpaApplication {
    public static void main( String[] args ) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Autowired
    private BackupService backupService;

    @Bean
    public ApplicationRunner initBackups() {
        return args -> {
//            for (int i=0; i<20; i++) {
//                Backup backup = new Backup();
//                backup.setBackupHit(100L);
//                backup.setDateHour("2020043003");
//                backup.setEnv("gy");
//                backup.setProject("boe");
//                backup.setTableName("l_recharge_log");
//                backup.setDataChangeCreatedBy("wangep");
//                backupService.save(backup);
//            }

//            List<Backup> list = backupService.searchListByProjectTableNameDateHour("sultans", "l_army_log", "2020042702");
//            System.out.println(list);
//            list.get(0).setDataChangeLastModifiedBy("for_test1111");
//            this.backupService.updateById(list.get(0));

            Page<Backup> page = backupService.findListPage("l_recharge_log", PageRequest.of(1, 10));
            System.out.println(JSONObject.toJSON(page));
        };
    }
}
