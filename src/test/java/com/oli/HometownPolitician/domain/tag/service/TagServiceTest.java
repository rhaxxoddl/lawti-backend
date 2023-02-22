package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.dto.TagsInput;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.userTagRelation.repository.UserTagRelationRepository;
import org.junit.jupiter.api.AfterEach;
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
    private UserTagRelationRepository userTagRelationRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private TagService tagService;

    @BeforeEach
    private void initialData() {
        insertTagData();
        insertUserData();
    }

    @AfterEach
    private void deleteData() {
        userRepository.deleteAll();
        tagRepository.deleteAll();
        userTagRelationRepository.deleteAll();
    }

    @Test
    @DisplayName("queryTags에서 InterestsDto를 잘 반환하는지 확인")
    public void queryTags_well_test() {
        userFollowing5Tag();

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
        userFollowing5Tag();

        TagsDto tagsDto = tagService.queryFollowedTagsByAuthorization("UUID-" + USER_UUID);
        assertThat(tagsDto).isNotNull();
        assertThat(tagsDto.getList()).isNotNull();
        assertThat(tagsDto.getList().size()).isEqualTo(FOLLOWED_TAGS_SIZE);
        tagsDto.getList().forEach(e -> {
            assertThat(e.getId()).isNotNull();
            assertThat(e.getName()).isNotBlank();
        });
    }

    @Test
    @DisplayName("queryFollowedTagsByUserUuid에서 DB에 없는 UUID일 경우 TagsDto에 list의 길이가 0인지 확인")
    void queryFollowedTagsByUserUuid_wrong_test() {
        userFollowing5Tag();

        TagsDto tagsDto = tagService.queryFollowedTagsByAuthorization("nothing");
        assertThat(tagsDto).isNotNull();
        assertThat(tagsDto.getList()).isNotNull();
        assertThat(tagsDto.getList().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("유저가 태그 1개를 팔로우를 할 때 잘되는지 확인")
    void followingTags_well_test() {
        List<TagInput> tagInputList = new ArrayList<>();
        tagInputList.add(TagInput.builder().name("국회, 인권").build());
        TagsInput tagsInput = TagsInput.builder().list(tagInputList).build();

        TagsDto tagsDtoBefore = tagService.queryFollowedTagsByAuthorization("UUID-" + USER_UUID);
        assertThat(tagsDtoBefore).isNotNull();
        assertThat(tagsDtoBefore.getList()).isNotNull();
        assertThat(tagsDtoBefore.getList().size()).isEqualTo(0);
        TagsDto tagsDtoAfter = tagService.followingTags(tagsInput, "UUID-" + USER_UUID);
        assertThat(tagsDtoAfter).isNotNull();
        assertThat(tagsDtoAfter.getList()).isNotNull();
        assertThat(tagsDtoAfter.getList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저가 이미 팔로우한 태그를 다시 팔로우를 할 때 잘되는지 확인")
    void followingTags_duplicate_well_test() {
        userFollowing5Tag();
        List<TagInput> tagInputList = new ArrayList<>();
        tagInputList.add(TagInput.builder().name("국회, 인권").build());
        TagsInput tagsInput = TagsInput.builder().list(tagInputList).build();

        TagsDto tagsDtoBefore = tagService.queryFollowedTagsByAuthorization("UUID-" + USER_UUID);
        assertThat(tagsDtoBefore).isNotNull();
        assertThat(tagsDtoBefore.getList()).isNotNull();
        assertThat(tagsDtoBefore.getList().size()).isEqualTo(FOLLOWED_TAGS_SIZE);
        TagsDto tagsDtoAfter = tagService.followingTags(tagsInput, "UUID-" + USER_UUID);
        assertThat(tagsDtoAfter).isNotNull();
        assertThat(tagsDtoAfter.getList()).isNotNull();
        assertThat(tagsDtoAfter.getList().size()).isEqualTo(tagsDtoBefore.getList().size());
    }

    @Test
    @DisplayName("유저가 팔로우한 태그를 언팔로우를 할 때 잘되는지 확인")
    void unfollowingTags_well_test() {
        userFollowing5Tag();
        List<TagInput> tagInputList = new ArrayList<>();
        tagInputList.add(TagInput.builder().id(1L).name("국회, 인권").build());
        TagsInput tagsInput = TagsInput.builder().list(tagInputList).build();

        TagsDto tagsDtoBefore = tagService.queryFollowedTagsByAuthorization("UUID-" + USER_UUID);
        assertThat(tagsDtoBefore).isNotNull();
        assertThat(tagsDtoBefore.getList()).isNotNull();
        assertThat(tagsDtoBefore.getList().size()).isEqualTo(FOLLOWED_TAGS_SIZE);
        TagsDto tagsDtoAfter = tagService.unfollowMyTags(tagsInput, "UUID-" + USER_UUID);
        assertThat(tagsDtoAfter).isNotNull();
        assertThat(tagsDtoAfter.getList()).isNotNull();
        assertThat(tagsDtoAfter.getList().size()).isEqualTo(tagsDtoBefore.getList().size() - 1);
    }

    @Test
    @DisplayName("유저가 팔로우하지 않은 태그를 언팔로우를 할 때 잘되는지 확인")
    void unfollowingTags_not_followed_tag_well_test() {
        List<TagInput> tagInputList = new ArrayList<>();
        tagInputList.add(TagInput.builder().id(1L).name("국회, 인권").build());
        TagsInput tagsInput = TagsInput.builder().list(tagInputList).build();

        TagsDto tagsDtoBefore = tagService.queryFollowedTagsByAuthorization("UUID-" + USER_UUID);
        assertThat(tagsDtoBefore).isNotNull();
        assertThat(tagsDtoBefore.getList()).isNotNull();
        assertThat(tagsDtoBefore.getList().size()).isEqualTo(0);
        TagsDto tagsDtoAfter = tagService.unfollowMyTags(tagsInput, "UUID-" + USER_UUID);
        assertThat(tagsDtoAfter).isNotNull();
        assertThat(tagsDtoAfter.getList()).isNotNull();
        assertThat(tagsDtoAfter.getList().size()).isEqualTo(0);
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
        List<Tag> tags = tagRepository.findAll().subList(0, FOLLOWED_TAGS_SIZE);
        List<UserTagRelation> userTagRelationList = new ArrayList<>();
        tags.forEach(e -> {
            UserTagRelation userTagRelation = new UserTagRelation(
                    userRepository.qFindByUuid(USER_UUID).orElseThrow(() -> new ExceptionInInitializerError("UUID에 해당하는 유저가 없습니다"))
                    , e);
            em.persist(userTagRelation);
        });
        em.flush();
        em.clear();
    }
}