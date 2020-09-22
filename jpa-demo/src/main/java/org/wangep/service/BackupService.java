package org.wangep.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wangep.entity.Backup;
import org.wangep.repository.BackupRepository;

import java.util.List;

/***
 * created by wange on 2020/4/28 18:54
 */
@Service
public class BackupService {

    private final BackupRepository backupRepository;

    public BackupService(final BackupRepository backupRepository) {
        this.backupRepository = backupRepository;
    }

    public List<Backup> searchListByProjectTableNameDateHour(String project, String tableName, String dateHour) {
        return this.backupRepository.findByProjectAndTableNameAndDateHour(project, tableName, dateHour);
    }

    @Transactional
    public void save(Backup backup) {
        this.backupRepository.save(backup);
    }

    @Transactional
    public void delete(long id, String operator) {
        this.backupRepository.deleteBackup(id, operator);
    }

    @Transactional
    public void updateById(Backup backup) {
        this.backupRepository.save(backup);
    }

    public Page<Backup> findListPage(String tableName, Pageable pageable) {
        return backupRepository.findByTableNameOrderByDataChangeCreatedTime(tableName, pageable);
    }
}
