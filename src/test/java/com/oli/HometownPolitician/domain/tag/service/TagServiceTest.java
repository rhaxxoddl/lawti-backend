package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.entityRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
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
class TagServiceTest {
    private final String USER_UUID = "asfasd-asdfasd-fasdf-qwe";
    static private final int INTERESTS_SIZE = 9;
    static private final int FOLLOWED_TAGS_SIZE = 5;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private TagService tagService;

    @BeforeEach
    private void initialData() {
        insertTagData();
        insertUserData();
        Optional<User> user = userRepository.qFindByUuid(USER_UUID);
        if (user.isEmpty())
            throw new ExceptionInInitializerError("UUID에 해당");
        List<Tag> tags = tagRepository.findAll().subList(0, FOLLOWED_TAGS_SIZE);
        List<UserTagRelation> userTagRelationList = new ArrayList<>();
        tags.forEach(e -> {
            UserTagRelation userTagRelation = new UserTagRelation(user.get(), e);
            em.persist(userTagRelation);
        });
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("queryTags에서 InterestsDto를 잘 반환하는지 확인")
    public void queryTags_well_test() {
        TagsDto tagsDto = tagService.queryTags();
        assertThat(tagsDto).isNotNull();
        assertThat(tagsDto.getList()).isNotNull();
        assertThat(tagsDto.getList().size()).isEqualTo(INTERESTS_SIZE);
        tagsDto.getList().forEach(e -> {
            assertThat(e.getId()).isNotNull();
            assertThat(e.getName()).isNotBlank();
        });
    }

    @Test
    @DisplayName("queryFollowedTagsByUserUuid에서 TagsDto를 잘 반환하는지 확인")
    void queryFollowedTagsByUserUuid_well_test() {
        TagsDto tagsDto = tagService.queryFollowedTagsByUserUuid(USER_UUID);
        assertThat(tagsDto).isNotNull();
        assertThat(tagsDto.getList()).isNotNull();
        assertThat(tagsDto.getList().size()).isEqualTo(FOLLOWED_TAGS_SIZE);
        tagsDto.getList().forEach(e -> {
            assertThat(e.getId()).isNotNull();
            assertThat(e.getName()).isNotBlank();
        });
    }

    private void insertUserData() {
        User user = new User(USER_UUID);
        userRepository.save(user);
    }

    private void insertTagData() {
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
        tagList.add(new Tag(INTEREST1));
        tagList.add(new Tag(INTEREST2));
        tagList.add(new Tag(INTEREST3));
        tagList.add(new Tag(INTEREST4));
        tagList.add(new Tag(INTEREST5));
        tagList.add(new Tag(INTEREST6));
        tagList.add(new Tag(INTEREST7));
        tagList.add(new Tag(INTEREST8));
        tagList.add(new Tag(INTEREST9));
        tagRepository.saveAll(tagList);
    }
}