package com.ssm.mybatis.plus.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ssm.mybatis.plus.type.ChargeEnum;
import lombok.*;

/**
 * @author caimeng
 * @date 2024/12/23 10:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@TableName  // 此时表名与类名一致
public class Project extends Model<Project> {
    /**
     * 项目ID
     */
//    @TableId(type = IdType.AUTO)
    @TableId(type = IdType.ASSIGN_ID)
    private Long pid;
    /**
     * 项目名称
     */
    @TableField("name")
    private String name;
    /**
     * 项目主管
     * @date 2024-12-25 将 String 类型替换为枚举
     */
    @TableField("charge")
    private ChargeEnum charge;
    /**
     * 项目描述
     */
    @TableField("note")
    private String note;
    /**
     * 项目状态
     * 逻辑删除标记，0表示正常，1表示删除
     */
    @TableLogic     // 标记为逻辑删除字段
    @TableField(value = "status", fill = FieldFill.INSERT_UPDATE)       // 新增和修改都会触发填充器逻辑
    private Integer status;
    /**
     * 乐观锁
     */
    @Version
    @TableField("version")
    private Integer version;
    /**
     * 租户
     * <p>
     *     租户的列名有默认值
     *     {@link com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler#getTenantIdColumn()}
     */
    @TableField("tenant_id")
    private String tenantId;

    public void setCharge(String charge) {
        this.charge = ChargeEnum.of(charge);
    }

    public static ProjectBuilder builder() {
        return new ProjectBuilder();
    }

    public Project(Long pid, String name, String charge, String note, Integer status, Integer version, String tenantId) {
        this.pid = pid;
        this.name = name;
        this.charge = ChargeEnum.of(charge);
        this.note = note;
        this.status = status;
        this.version = version;
        this.tenantId = tenantId;
    }

    public static class ProjectBuilder {
        private Long pid;
        private String name;
        private String charge;
        private String note;
        private Integer status;
        private Integer version;
        @SuppressWarnings("unused")
        private String tenantId;

        ProjectBuilder() {
        }

        public ProjectBuilder pid(Long pid) {
            this.pid = pid;
            return this;
        }

        public ProjectBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProjectBuilder charge(String charge) {
            this.charge = charge;
            return this;
        }

        public ProjectBuilder note(String note) {
            this.note = note;
            return this;
        }

        public ProjectBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ProjectBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public Project build() {
            return new Project(this.pid, this.name, this.charge, this.note, this.status, this.version, this.tenantId);
        }

        public String toString() {
            return "Project.ProjectBuilder(pid=" + this.pid + ", name=" + this.name + ", charge=" + this.charge + ", note=" + this.note + ", status=" + this.status + ", version=" + this.version + ", tenantId=" + this.tenantId + ")";
        }
    }
}
