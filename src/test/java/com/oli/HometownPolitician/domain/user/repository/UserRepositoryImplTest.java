package com.oli.HometownPolitician.domain.user.repository;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
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
class UserRepositoryImplTest {
    private final String USER_UUID = "asfasd-asdfasd-fasdf-qwe";
    private final String NOT_EXIST_USER_UUID = "ThisIs-notExist-user";
    private final int FOLLOWED_TAGS_SIZE = 5;

    @Autowired
    private UserRepository userRepository;
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
    @DisplayName("유저정보 가져오는지 확인")
    void qFindByUuid_well_test() {
        Optional<User> optionalUser = userRepository.qFindByUuid(USER_UUID);
        assertThat(optionalUser.isEmpty()).isFalse();
        User user = optionalUser.get();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUuid()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저의 경우 안 가져오는지 확인")
    void qFindByUuid_wrong_test() {
        Optional<User> optionalUser = userRepository.qFindByUuid(NOT_EXIST_USER_UUID);
        assertThat(optionalUser.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("유저를 팔로우한 태그까지 같이 가져오는지 확인")
    void qFindByUuidWithFollowedTags_well_test() {
        userFollowing5Tag();

        Optional<User> optionalUser  = userRepository.qFindByUuidWithFollowedTags(USER_UUID);
        assertThat(optionalUser.isEmpty()).isFalse();
        User user = optionalUser.get();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUuid()).isNotNull();
        assertThat(user.getUserTagRelations()).isNotNull();
        assertThat(user.getUserTagRelations().size()).isEqualTo(FOLLOWED_TAGS_SIZE);
        user.getUserTagRelations().forEach(followedTag -> {
            assertThat(followedTag).isNotNull();
            assertThat(followedTag.getId()).isNotNull();
            assertThat(followedTag.getUser()).isNotNull();
            assertThat(followedTag.getTag()).isNotNull();
            assertThat(followedTag.getTag().getId()).isNotNull();
            assertThat(followedTag.getTag().getName()).isNotNull();
        });
    }

    @Test
    @DisplayName("팔로우한 태그가 없는 유저의 팔로우한 태그가 없는지 확인")
    void qFindByUuidWithFollowedTags_followed_nothing_test() {
        Optional<User> optionalUser  = userRepository.qFindByUuidWithFollowedTags(USER_UUID);
        assertThat(optionalUser.isEmpty()).isFalse();
        User user = optionalUser.get();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUuid()).isNotNull();
        assertThat(user.getUserTagRelations()).isNotNull();
        assertThat(user.getUserTagRelations().size()).isEqualTo(0);
    }

    private void insertUserData() {
        User user = new User(USER_UUID);
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
        List<Tag> tags = tagRepository.findAll().subList(0, FOLLOWED_TAGS_SIZE);
        List<UserTagRelation> userTagRelationList = new ArrayList<>();
        tags.forEach(e -> {
            UserTagRelation userTagRelation = new UserTagRelation(user.get(), e);
            em.persist(userTagRelation);
        });
        em.flush();
        em.clear();
    }
}
