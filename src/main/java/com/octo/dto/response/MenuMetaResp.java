package com.octo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author zms
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuMetaResp {
    private String locale;

    private String icon;

    private Long order;

    private Boolean requiresAuth;
}
