package com.tooolan.ddd.domain.common.exception;

/**
 * 错误码接口，所有错误码枚举都需要实现此接口
 * <p>
 * 错误码格式：{来源}-{模块}-{HTTP状态码}-{序号}
 * - 来源（1位）：1=本地，2=远程
 * - 模块（3位）：001=通用, 002=用户, 003=小组, 004=部门, 005=会话, 006=日志
 * - HTTP状态码（3位）：标准HTTP状态码，仅作参考
 * - 序号（3位）：模块内全局递增，不重复
 * <p>
 * 新增模块时，在此分配下一个可用编号，并同步更新模块编码列表
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    String getMessage();

}
