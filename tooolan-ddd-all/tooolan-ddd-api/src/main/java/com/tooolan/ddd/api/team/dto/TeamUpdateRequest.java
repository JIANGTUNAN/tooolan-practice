package com.tooolan.ddd.api.team.dto;

import com.tooolan.ddd.app.team.bo.TeamUpdateBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小组更新请求 DTO
 * 继承 TeamUpdateBO
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamUpdateRequest extends TeamUpdateBO {
}
