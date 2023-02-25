package com.oli.HometownPolitician.domain.search.input;

import com.oli.HometownPolitician.domain.committee.input.CommitteeInput;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchFilterInput {
    private CommitteeInput committee;
    private TagInput tag;
}
