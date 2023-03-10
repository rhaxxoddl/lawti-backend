package com.oli.HometownPolitician.domain.userTagRelation.repository;

import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserTagRelationRepositoryImplTest {
    private final String USER_UUID = "asfasd-asdfasd-fasdf-qwe";
    private final String NOT_EXIST_USER_UUID = "ThisIs-notExist-user";
    private final int FOLLOW_TAG_SIZE = 5;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTagRelationRepository userTagRelationRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    private void initialData() {
        insertTagData();
        insertUserData();
    }

    @Test
    @DisplayName("유저정보에서 팔로우한 태그도 포함해서 가져오는지 확인")
    void qFindByUuidWithFollowedTags_well_test() {
        userFollowing5Tag();

        List<UserTagRelation> followedTag = userTagRelationRepository.qFindFollowedTagsByUuid(USER_UUID);
        assertThat(followedTag).isNotNull();
        assertThat(followedTag.size()).isEqualTo(FOLLOW_TAG_SIZE);
        followedTag.forEach(e -> {
            assertThat(e.getId()).isNotNull();
            assertThat(e.getTag()).isNotNull();
            assertThat(e.getTag().getName().getClass()).isEqualTo(String.class);
            assertThat(e.getUser()).isNotNull();
            assertThat(e.getUser().getId().getClass()).isEqualTo(Long.class);
            assertThat(e.getUser().getUuid().getClass()).isEqualTo(String.class);
        });
    }

    @Test
    @DisplayName("저장되어 있지 않은 UUID로 유저정보를 안 가져오는지 확인")
    void qFindByUuidWithFollowedTags_not_found_test() {
        List<UserTagRelation> followedTag = userTagRelationRepository.qFindFollowedTagsByUuid(NOT_EXIST_USER_UUID);
        assertThat(followedTag).isNotNull();
        assertThat(followedTag.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("올바르지 않은 UUID로 유저정보를 안 가져오는지 확인")
    void qFindByUuidWithFollowedTags_invalid_uuid_test() {
        List<UserTagRelation> followedTag = userTagRelationRepository.qFindFollowedTagsByUuid("NOT_EXIST_USER_UUID");
        assertThat(followedTag).isNotNull();
        assertThat(followedTag.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("팔로우한 태그가 없는 유저정보 확인")
    void qFindByUuidWithFollowedTags_followed_nothing_test() {
        List<UserTagRelation> followedTag = userTagRelationRepository.qFindFollowedTagsByUuid(USER_UUID);
        assertThat(followedTag).isNotNull();
        assertThat(followedTag.size()).isEqualTo(0);
    }

    private void insertUserData() {
        User user = User.InitBuilder()
                .uuid(USER_UUID)
                .build();
        userRepository.save(user);
    }

    private void insertTagData() {
        final int INTERESTS_SIZE = 9;
        final String INTEREST1 = "국회, 인권";
        final String INTEREST2 = "형법, 민법, 범죄";
        final String INTEREST3 = "금융, 공정거래, 보훈(국가유공)";
        final String INTEREST4 = "관세, 세금, 통계";
        final String INTEREST5 = "교육, 교육공무원";
        final String INTEREST6 = "과학, 방송, 통신, 인터넷";
        final String INTEREST7 = "외교, 통일, 북한이탈주민, 재외동포, 해외";
        final String INTEREST8 = "병역, 국방";
        final String INTEREST9 = "경찰, 소방, 선거, 공무원, 재난, 운전";


        List<Tag> tagList = new ArrayList<>();
        tagList.add(Tag.builder().name(INTEREST1).build());
        tagList.add(Tag.builder().name(INTEREST2).build());
        tagList.add(Tag.builder().name(INTEREST3).build());
        tagList.add(Tag.builder().name(INTEREST4).build());
        tagList.add(Tag.builder().name(INTEREST5).build());
        tagList.add(Tag.builder().name(INTEREST6).build());
        tagList.add(Tag.builder().name(INTEREST7).build());
        tagList.add(Tag.builder().name(INTEREST8).build());
        tagList.add(Tag.builder().name(INTEREST9).build());
        tagRepository.saveAll(tagList);
    }

    private void userFollowing5Tag() {
        Optional<User> user = userRepository.qFindByUuid(USER_UUID);
        if (user.isEmpty())
            throw new ExceptionInInitializerError("UUID에 해당하는 유저가 없습니다");
        List<Tag> tags = tagRepository.findAll().subList(0, FOLLOW_TAG_SIZE);
        List<UserTagRelation> userTagRelationList = new ArrayList<>();
        tags.forEach(e -> {
            UserTagRelation userTagRelation = new UserTagRelation(user.get(), e);
            em.persist(userTagRelation);
        });
        em.flush();
        em.clear();
    }
}