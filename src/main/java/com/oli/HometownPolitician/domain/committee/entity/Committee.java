package com.oli.HometownPolitician.domain.committee.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.committee.responseEntity.CommitteeInfo;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import io.jsonwebtoken.lang.Assert;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "committees")
@SequenceGenerator(name = "COMMITTEE_SEQ_GENERATOR", sequenceName = "COMMITTEE_SEQ")
public class Committee extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMITTEE_SEQ")
    @Column(name = "committee_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "external_committee_id", unique = true, nullable = false)
    private String external_committee_id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "committee")
    private List<Bill> bills;

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public Committee(String external_committee_id, String name) {
        Assert.notNull(external_committee_id, "external_committee_id에 null이 들어올 수 없습니다");
        Assert.notNull(name, "name에 null이 들어올 수 없습니다");

        this.external_committee_id = external_committee_id;
        this.name = name;
    }
    public Committee from(CommitteeInfo committeeInfo) {
        if (committeeInfo == null)
            return null;
        return Committee.InitBuilder()
                .external_committee_id(committeeInfo.getHr_dept_cd())
                .name(committeeInfo.getCommittee_name())
                .build();
    }
}
