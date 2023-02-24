package com.oli.HometownPolitician.domain.billTagRelation.service;

import com.oli.HometownPolitician.domain.committe.entity.Committee;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.global.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillTagRelationProvider {
    private final TagRepository tagRepository;

    public Tag matchTagByCommittee(Committee committee) {
        String committeeName = committee.getName();
        switch (committeeName) {
            case "국회운영위원회":
                return tagRepository.qFindTagByName("국회, 인권").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "법제사법위원회":
                return tagRepository.qFindTagByName("형법, 민법, 범죄").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "정무위원회":
                return tagRepository.qFindTagByName("금융, 공정거래, 보훈(국가유공)").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "기획재정위원회":
                return tagRepository.qFindTagByName("관세, 세금, 통계").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "교육위원회":
                return tagRepository.qFindTagByName("교육, 교육공무원").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "과학기술정보방송통신위원회":
                return tagRepository.qFindTagByName("과학, 방송, 통신, 인터넷").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "외교통일위원회":
                return tagRepository.qFindTagByName("외교, 통일, 북한이탈주민, 재외동포, 해외").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "국방위원회":
                return tagRepository.qFindTagByName("병역, 국방").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "행정안전위원회":
                return tagRepository.qFindTagByName("경찰, 소방, 선거, 공무원, 재난, 운전").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "문화체육관광위원회":
                return tagRepository.qFindTagByName("문화재, 예술, 체육, 저작권, 게임").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "국토교통위원회":
                return tagRepository.qFindTagByName("부동산, 주택, 건설, 교통, 물류, 자동차").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "농림축산식품해양수산위원회":
                return tagRepository.qFindTagByName("농업, 임업, 축업,수산업, 산림, 식품").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "산업통상자원중소벤처기업위원회":
                return tagRepository.qFindTagByName("에너지, 특허, 중소벤처, 창업, 소상공인").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "보건복지위원회":
                return tagRepository.qFindTagByName("의료, 보건, 건강보험, 복지").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "환경노동위원회":
                return tagRepository.qFindTagByName("환경, 고용, 노동").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "정보위원회":
                return tagRepository.qFindTagByName("테러, 기술보호(방산, 산업), 국가 보안").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            case "여성가족위원회":
                return tagRepository.qFindTagByName("아동, 청소년, 여성, 가족, 양성평등").orElseThrow(()-> new NotFoundError("이름에 해당하는 관심분야를 찾지 못했습니다"));
            default:
                throw new NotFoundError("위원회의 이름이 올바르지 얂습니다");
        }
    }
}
