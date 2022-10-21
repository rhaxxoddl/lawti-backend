package com.pivot.hp.hometownpolitician.repository;

import com.pivot.hp.hometownpolitician.annotation.EnableQueryLog;
import com.pivot.hp.hometownpolitician.entity.QSample;
import com.pivot.hp.hometownpolitician.entity.Sample;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@EnableQueryLog
class SampleRepositoryTest {

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    EntityManager em;

    @Test
    public void springDataJpa() {
        Sample sample = new Sample();
        Sample savedSample = sampleRepository.save(sample);
        assertThat(sample).isEqualTo(savedSample);
        assertThat(sample.getId()).isEqualTo(savedSample.getId());
    }

    @Test
    public void queryDsl() {
        JPAQueryFactory query = new JPAQueryFactory(em);
        Sample sample = new Sample();
        em.persist(sample);
        em.flush();
        Sample fetchedSample = query.selectFrom(QSample.sample).fetchOne();
        assertThat(sample).isEqualTo(fetchedSample);
        assertThat(sample.getId()).isEqualTo(fetchedSample.getId());
    }

}
