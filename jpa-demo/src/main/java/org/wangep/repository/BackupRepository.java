package org.wangep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.wangep.entity.Backup;

import java.util.List;

/***
 * created by wange on 2020/4/28 18:51
 */
public interface BackupRepository extends PagingAndSortingRepository<Backup, Long> {

    List<Backup> findByProjectAndTableNameAndDateHour(String project, String tableName, String dateHour);

    @Modifying
    @Query("UPDATE Backup SET IsDeleted=1,DataChange_LastModifiedBy = ?2 WHERE id=?1")
    int deleteBackup(long id, String operator);


    Page<Backup> findByTableNameOrderByDataChangeCreatedTime(String tableName, Pageable pageable);
}
