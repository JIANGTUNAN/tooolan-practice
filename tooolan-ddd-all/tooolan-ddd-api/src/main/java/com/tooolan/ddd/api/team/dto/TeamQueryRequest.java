package com.tooolan.ddd.api.team.dto;

import com.tooolan.ddd.app.team.bo.TeamQueryBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小组查询请求 DTO
 * 继承 TeamQueryBO，用于查询小组列表
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamQueryRequest extends TeamQueryBO {
}
