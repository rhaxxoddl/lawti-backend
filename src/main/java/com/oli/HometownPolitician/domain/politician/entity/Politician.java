package com.oli.HometownPolitician.domain.politician.entity;

import com.oli.HometownPolitician.domain.politician.responseEntity.PoliticianInfo;
import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import com.oli.HometownPolitician.global.tool.ListTool;
import io.jsonwebtoken.lang.Assert;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "politicians")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "POLITICIAN_SEQ_GENERATOR", sequenceName = "POLITICIAN_SEQ")
public class Politician extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POLITICIAN_SEQ")
    @Column(name = "politician_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "chinese_name", nullable = false)
    private String chineseName;
    @Column(name = "party", nullable = false)
    private String party;
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "politician")
    private List<Proposer> proposeList = new ArrayList<>();

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public Politician(String name, String chineseName, String party) {
        Assert.notNull(name, "name에 null이 들어올 수 없습니다");
        Assert.notNull(chineseName, "chineseName에 null이 들어올 수 없습니다");
        Assert.notNull(party, "party에 null이 들어올 수 없습니다");

        this.name = name;
        this.chineseName = chineseName;
        this.party = party;
    }

    static public Politician from(PoliticianInfo politicianInfo) {
        return Politician.InitBuilder()
                .name(politicianInfo.getName())
                .chineseName(politicianInfo.getName_han())
                .party(
                        ListTool.getLastElement(Arrays.asList(politicianInfo.getDae().split(" |\\)|\\s")))
                )
                .build();
    }
}
