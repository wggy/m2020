package org.wangep.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/***
 * created by wange on 2020/4/28 18:45
 */
@ToString
@Setter
@Getter
@Entity
@Table(name = "x_backup")
@SQLDelete(sql = "Update App set isDeleted = 1 where id = ?")
@Where(clause = "isDeleted = 0")
public class Backup extends BaseEntity {

    @NotBlank(message = "project cannot be blank")
    @Column(name = "project", nullable = false)
    private String project;

    @NotBlank(message = "tableName cannot be blank")
    @Column(name = "table_name", nullable = false)
    private String tableName;

    @NotBlank(message = "env cannot be blank")
    @Column(name = "env", nullable = false)
    private String env;

    @NotBlank(message = "dateHour cannot be blank")
    @Column(name = "date_hour", nullable = false)
    private String dateHour;

    @Column(name = "backup_hit", nullable = false)
    private long backupHit;

}
