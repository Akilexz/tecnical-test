//package com.ec.tt.common.entities;
//
//import java.util.Date;
//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.PrePersist;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.SuperBuilder;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//
//@MappedSuperclass
//@SuperBuilder
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class AbstractBaseAuditable {
//    @Column(name = "status", columnDefinition = "varchar(1) default '1'", length = 1)
//    protected String status;
//
//    @CreatedBy
//    @NotNull
//    @Column(name = "created_by_user", updatable = false)
//    protected Long createByUser;
//
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date", updatable = false)
//    protected Date createdDate;
//
//    @Column(name = "created_from_ip")
//    protected String createdFromIp;
//
//    @PrePersist
//    public void prePersist() {
//        if (status == null) {
//            status = "1";
//        }
//    }
//}
