package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
}
